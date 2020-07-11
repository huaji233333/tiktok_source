package com.tt.miniapp.msg.wifi;

import android.content.BroadcastReceiver;
import android.net.wifi.WifiManager;

public class AppbrandWifiManager {
  private boolean mRegistered;
  
  private WifiManager mWifiManager;
  
  private WifiScanReceiver mWifiScanReceiver;
  
  static class Holder {
    static AppbrandWifiManager instance = new AppbrandWifiManager();
  }
  
  class WifiScanReceiver extends BroadcastReceiver {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\wifi\AppbrandWifiManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */