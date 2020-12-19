package JSONFactory;

import Async.ReadJSONOnline;
import JSONFiles.Location;
import android.content.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class GetJSONOnline extends GetJSON {

  private List<Location> diningMenu;
  final ObjectMapper mapper = new ObjectMapper();

  @Override
  public List<Location> getDiningMenu(Context ctx) {
    if (diningMenu == null) {
      ReadJSONOnline read = new ReadJSONOnline();
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
