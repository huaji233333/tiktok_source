package com.tt.miniapp.route;

import com.tt.miniapp.AppbrandApplicationImpl;

public class OnAppRouteEvent implements IRouteEventHandler {
  private IRouteEvent.RouteEventBean mBean;
  
  public OnAppRouteEvent(IRouteEvent.RouteEventBean paramRouteEventBean) {
    this.mBean = paramRouteEventBean;
  }
  
  public void act() {
    RouteEventCtrl routeEventCtrl = AppbrandApplicationImpl.getInst().getRouteEventCtrl();
    if (routeEventCtrl != null)
      routeEventCtrl.onAppRoute(this.mBean); 
  }
  
  public String getName() {
    return "onAppRoute";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\route\OnAppRouteEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */