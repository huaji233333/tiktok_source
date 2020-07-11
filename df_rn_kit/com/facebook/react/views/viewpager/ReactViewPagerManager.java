package com.facebook.react.views.viewpager;

import android.view.View;
import android.view.ViewGroup;
import com.a;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.Map;

@ReactModule(name = "AndroidViewPager")
public class ReactViewPagerManager extends ViewGroupManager<ReactViewPager> {
  public void addView(ReactViewPager paramReactViewPager, View paramView, int paramInt) {
    paramReactViewPager.addViewToAdapter(paramView, paramInt);
  }
  
  protected ReactViewPager createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactViewPager((ReactContext)paramThemedReactContext);
  }
  
  public View getChildAt(ReactViewPager paramReactViewPager, int paramInt) {
    return paramReactViewPager.getViewFromAdapter(paramInt);
  }
  
  public int getChildCount(ReactViewPager paramReactViewPager) {
    return paramReactViewPager.getViewCountInAdapter();
  }
  
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("setPage", Integer.valueOf(1), "setPageWithoutAnimation", Integer.valueOf(2));
  }
  
  public Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of("topPageScroll", MapBuilder.of("registrationName", "onPageScroll"), "topPageScrollStateChanged", MapBuilder.of("registrationName", "onPageScrollStateChanged"), "topPageSelected", MapBuilder.of("registrationName", "onPageSelected"));
  }
  
  public String getName() {
    return "AndroidViewPager";
  }
  
  public boolean needsCustomLayoutForChildren() {
    return true;
  }
  
  public void receiveCommand(ReactViewPager paramReactViewPager, int paramInt, ReadableArray paramReadableArray) {
    a.b(paramReactViewPager);
    a.b(paramReadableArray);
    if (paramInt != 1) {
      if (paramInt == 2) {
        paramReactViewPager.setCurrentItemFromJs(paramReadableArray.getInt(0), false);
        return;
      } 
      throw new IllegalArgumentException(a.a("Unsupported command %d received by %s.", new Object[] { Integer.valueOf(paramInt), getClass().getSimpleName() }));
    } 
    paramReactViewPager.setCurrentItemFromJs(paramReadableArray.getInt(0), true);
  }
  
  public void removeViewAt(ReactViewPager paramReactViewPager, int paramInt) {
    paramReactViewPager.removeViewFromAdapter(paramInt);
  }
  
  @ReactProp(defaultFloat = 0.0F, name = "pageMargin")
  public void setPageMargin(ReactViewPager paramReactViewPager, float paramFloat) {
    paramReactViewPager.setPageMargin((int)PixelUtil.toPixelFromDIP(paramFloat));
  }
  
  @ReactProp(defaultBoolean = false, name = "peekEnabled")
  public void setPeekEnabled(ReactViewPager paramReactViewPager, boolean paramBoolean) {
    paramReactViewPager.setClipToPadding(paramBoolean ^ true);
  }
  
  @ReactProp(defaultBoolean = true, name = "scrollEnabled")
  public void setScrollEnabled(ReactViewPager paramReactViewPager, boolean paramBoolean) {
    paramReactViewPager.setScrollEnabled(paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\viewpager\ReactViewPagerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */