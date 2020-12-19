package Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AnalyzeNetworkConnection {

  public boolean checkNetworkConnection(Context ctx) {
    boolean connectedWifi = false;
    boolean connectedMobile = false;

    ConnectivityManager conMan = (ConnectivityManager) ctx.getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo[] netInfo = conMan.getAllNetworkInfo();

    for (NetworkInfo ni : netInfo) {
      if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
        if (ni.isConnected()) {
          connectedWifi = true;
        }
      }
      if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
        if (ni.isConnected()) {
          connectedMobile = true;
        }
      }
    }
    return connectedMobile || connectedWifi;
  }
}