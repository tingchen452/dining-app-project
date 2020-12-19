package Async;

import JSONFiles.Location;
import android.content.Context;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import nwtft.dinningapp.R;

public class ReadJSONOffline implements Runnable {

  Context ctx = null;
  final ObjectMapper mapper = new ObjectMapper();
  List<Location> diningMenus = null;

  @Override
  public void run() {
    try {
      InputStream inputStream = ctx.getResources().openRawResource(R.raw.current_menu_offline);

      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      StringBuilder json = new StringBuilder();

      try {
        while ((line = bufferedReader.readLine()) != null) {
          json.append(line);
          json.append('\n');
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      diningMenus = mapper.readValue(json.toString(), new TypeReference<List<Location>>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setContext(Context ctx) {
    this.ctx = ctx;
  }

  public List<Location> getValue() {
    return diningMenus;
  }
}
