package JSONFactory;

import Async.ReadJSONOffline;
import JSONFiles.Location;
import android.content.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class GetJSONOffline extends GetJSON {

  private List<Location> diningMenu;
  final ObjectMapper mapper = new ObjectMapper();

  @Override
  public List<Location> getDiningMenu(Context ctx) {
    if (diningMenu == null) {
      ReadJSONOffline read = new ReadJSONOffline();
      read.setContext(ctx);
      Thread threadJSON = new Thread(read);
      threadJSON.start();

      try {
        threadJSON.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      diningMenu = read.getValue();
    }

    return diningMenu;
  }
}
