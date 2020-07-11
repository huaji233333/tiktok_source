package com.tt.miniapp.route;

import com.tt.miniapp.AppbrandApplicationImpl;

public class OnAppShowEvent implements IRouteEventHandler {
  public void act() {
    RouteEventCtrl routeEventCtrl = AppbrandApplicationImpl.getInst().getRouteEventCtrl();
    if (routeEventCtrl != null)
      routeEventCtrl.onAppShow(); 
  }
  
  public String getName() {
    return "onShow";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\route\OnAppShowEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */