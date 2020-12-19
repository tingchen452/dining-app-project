package ui.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import JSONFiles.Location;
import nwtft.dinningapp.R;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    public List<Location> menuData = null;
    private GoogleMap mapAPI;
    private MapView mMapView;
    private View mView;
    private UiSettings mUISettings;
    private int locationIndex;
    private String hourString;
    private List<LatLng> locationsList;
    private List<String> locationTitle;
    private Marker mSelectedMarker;
    private String v;


    public MapFragment(){
        locationsList = new ArrayList<LatLng>();
        locationTitle = new ArrayList<String>();
        hourString = "";
    }
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            menuData = (List<Location>) bundle.getSerializable("menuData");
            locationIndex = bundle.getInt("Location", 0);
        }
        if (menuData.get(locationIndex).getLocationInfo() != null) {
            Map<String, List<String>> locationHours = menuData.get(locationIndex).getLocationInfo().getHours();
            for (Map.Entry<String, List<String>> entry : locationHours.entrySet()) {
                String k = entry.getKey();
                v = "";
                for(int i = 0; i < entry.getValue().size(); i++) {
                    if(i != 0){
                        v += ", ";
                    }
                    v += entry.getValue().get(i);

                }
                hourString += k + ": " + v + "\n";

            }
        }
        else{
            hourString = "There are no hours available for this location";
        }
        locationsList.add(new LatLng(42.131592, -72.795561));
        locationsList.add(new LatLng(42.131841, -72.792800));
        locationsList.add(new LatLng(42.131478, -72.795734));
        locationsList.add(new LatLng(42.133251, -72.796657));
        locationsList.add(new LatLng(42.130464, -72.794828));
        locationsList.add(new LatLng(42.127929, -72.784349));
        locationTitle.add(getString(R.string.Tim));
        locationTitle.add(getString(R.string.Market));
        locationTitle.add(getString(R.string.Bistro));
        locationTitle.add(getString(R.string.Ely));
        locationTitle.add(getString(R.string.Wilson));
        locationTitle.add(getString(R.string.Garden));

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView != null)
        {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }
    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mapAPI = googleMap;
        mUISettings = mapAPI.getUiSettings();
        mapAPI.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mUISettings.setZoomControlsEnabled(true);
        mUISettings.setCompassEnabled(false);
        mapAPI.addMarker(new MarkerOptions().position(locationsList.get(locationIndex)).icon(vectorToBitmap(R.drawable.ic_home, Color.parseColor("#0362fc"))).snippet(hourString).title(locationTitle.get(locationIndex)));
        CameraPosition cam = CameraPosition.builder().target(locationsList.get(locationIndex)).zoom(17).tilt(60).bearing(25).build();
        mapAPI.moveCamera(CameraUpdateFactory.newCameraPosition(cam));
        mapAPI.setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity()));
        mapAPI.setOnMarkerClickListener(this);

        // Set listener for map click event.  See the bottom of this class for its behavior.
        mapAPI.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mSelectedMarker = null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // The user has re-tapped on the marker which was already showing an info window.
        if (marker.equals(mSelectedMarker)) {
            // The showing info window has already been closed - that's the first thing to happen
            // when any marker is clicked.
            // Return true to indicate we have consumed the event and that we do not want the
            // the default behavior to occur (which is for the camera to move such that the
            // marker is centered and for the marker's info window to open, if it has one).
            mSelectedMarker = null;
            return true;
        }

        mSelectedMarker = marker;

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur.
        return false;
    }
}
