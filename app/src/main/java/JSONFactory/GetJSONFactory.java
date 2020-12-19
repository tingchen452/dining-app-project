package JSONFactory;

public class GetJSONFactory {

  public GetJSON getJSONFile(String type) {
    GetJSON getJSON = null;

    if (type == "ONLINE") {
      getJSON = new GetJSONOnline();
    } else if (type == "OFFLINE") {
      getJSON = new GetJSONOffline();
    }
    return getJSON;
  }

}
