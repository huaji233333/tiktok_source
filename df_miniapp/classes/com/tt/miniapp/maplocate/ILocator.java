package com.tt.miniapp.maplocate;

public interface ILocator {
  TMALocation getLastKnwonLocation();
  
  void startLocate(LocateConfig paramLocateConfig, ILocationListener paramILocationListener);
  
  void stopLocate(ILocationListener paramILocationListener);
  
  public static interface ILocationListener {
    void onLocationChanged(TMALocation param1TMALocation);
  }
  
  public static class LocateConfig {
    public boolean isUseGps;
    
    public long timeout;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\maplocate\ILocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */