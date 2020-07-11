package com.tt.miniapp.route;

import com.tt.miniapp.AppbrandApplicationImpl;

public class OnAppLaunchEvent implements IRouteEventHandler {
  public void act() {
    RouteEventCtrl routeEventCtrl = AppbrandApplicationImpl.getInst().getRouteEventCtrl();
    if (routeEventCtrl != null)
      routeEventCtrl.onAppLaunch(); 
  }
  
  public String getName() {
    return "onAppLaunch";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\route\OnAppLaunchEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */