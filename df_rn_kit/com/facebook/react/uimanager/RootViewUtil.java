package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewParent;
import com.facebook.i.a.a;

public class RootViewUtil {
  public static RootView getRootView(View paramView) {
    while (true) {
      if (paramView instanceof RootView)
        return (RootView)paramView; 
      ViewParent viewParent = paramView.getParent();
      if (viewParent == null)
        return null; 
      a.a(viewParent instanceof View);
      View view = (View)viewParent;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\RootViewUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */