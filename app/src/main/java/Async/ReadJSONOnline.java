package Async;

import JSONFiles.Location;
import android.content.Context;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ReadJSONOnline implements Runnable {

  final ObjectMapper mapper = new ObjectMapper();
  List<Location> diningMenus = null;
  Context ctx = null;

  @Override
  public void run() {
    try {
      URL url = new URL("https://west-texas-french-toast.s3.amazonaws.com/current-menu.json");
      diningMenus = mapper.readValue(url, new TypeReference<List<Location>>() {
      });
    } catch (MalformedURLException e) {
      e.printStackTrace();
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
