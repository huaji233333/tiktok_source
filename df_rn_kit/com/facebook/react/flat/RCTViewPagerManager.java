package com.facebook.react.flat;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.views.viewpager.ReactViewPager;
import com.facebook.react.views.viewpager.ReactViewPagerManager;
import java.util.List;

public class RCTViewPagerManager extends ReactViewPagerManager {
  public void addViews(ReactViewPager paramReactViewPager, List<View> paramList) {
    paramReactViewPager.setViews(paramList);
  }
  
  public void removeAllViews(ReactViewPager paramReactViewPager) {
    paramReactViewPager.removeAllViewsFromAdapter();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTViewPagerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */