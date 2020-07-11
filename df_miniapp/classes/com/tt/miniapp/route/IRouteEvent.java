package com.tt.miniapp.route;

public interface IRouteEvent {
  void onAppHide();
  
  void onAppLaunch();
  
  void onAppRoute(RouteEventBean paramRouteEventBean);
  
  void onAppShow();
  
  void onJsCoreReady();
  
  public static class RouteEventBean {
    private String openType;
    
    private String path;
    
    private String queryStr;
    
    private int webViewId;
    
    public RouteEventBean(int param1Int, String param1String1, String param1String2, String param1String3) {
      this.webViewId = param1Int;
      this.path = param1String1;
      this.queryStr = param1String2;
      this.openType = param1String3;
    }
    
    public String getOpenType() {
      return this.openType;
    }
    
    public String getPath() {
      return this.path;
    }
    
    public String getQueryStr() {
      return this.queryStr;
    }
    
    public int getWebViewId() {
      return this.webViewId;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\route\IRouteEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */