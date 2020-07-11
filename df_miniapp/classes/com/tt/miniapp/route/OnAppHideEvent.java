package com.tt.miniapp.route;

import com.tt.miniapp.AppbrandApplicationImpl;

public class OnAppHideEvent implements IRouteEventHandler {
  public void act() {
    RouteEventCtrl routeEventCtrl = AppbrandApplicationImpl.getInst().getRouteEventCtrl();
    if (routeEventCtrl != null)
      routeEventCtrl.onAppHide(); 
  }
  
  public String getName() {
    return "onHide";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\route\OnAppHideEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */