package com.tt.miniapp.launchcache;

public enum StatusFlagType {
  Downloading, Verified;
  
  static {
    StatusFlagType statusFlagType1 = new StatusFlagType("Downloading", 0);
    Downloading = statusFlagType1;
    StatusFlagType statusFlagType2 = new StatusFlagType("Verified", 1);
    Verified = statusFlagType2;
    $VALUES = new StatusFlagType[] { statusFlagType1, statusFlagType2 };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\StatusFlagType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */