package Async;

import Network.AnalyzeNetworkConnection;

public class ConnectionSingleton {

  private static AnalyzeNetworkConnection analyzeNetwork = null;

  private ConnectionSingleton() {
  }

  public static AnalyzeNetworkConnection getNetworkAnalzyer() {
    if (analyzeNetwork == null) {
      analyzeNetwork = new AnalyzeNetworkConnection();
    }
    return analyzeNetwork;
  }
}