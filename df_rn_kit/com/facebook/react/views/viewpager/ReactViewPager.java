package com.facebook.react.views.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import java.util.ArrayList;
import java.util.List;

public class ReactViewPager extends ViewPager {
  public final EventDispatcher mEventDispatcher;
  
  public boolean mIsCurrentItemFromJs;
  
  private boolean mScrollEnabled = true;
  
  private final Runnable measureAndLayout = new Runnable() {
      public void run() {
        ReactViewPager reactViewPager = ReactViewPager.this;
        reactViewPager.measure(View.MeasureSpec.makeMeasureSpec(reactViewPager.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ReactViewPager.this.getHeight(), 1073741824));
        reactViewPager = ReactViewPager.this;
        reactViewPager.layout(reactViewPager.getLeft(), ReactViewPager.this.getTop(), ReactViewPager.this.getRight(), ReactViewPager.this.getBottom());
      }
    };
  
  public ReactViewPager(ReactContext paramReactContext) {
    super((Context)paramReactContext);
    this.mEventDispatcher = ((UIManagerModule)paramReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
    this.mIsCurrentItemFromJs = false;
    setOnPageChangeListener(new PageChangeListener());
    setAdapter(new Adapter());
  }
  
  void addViewToAdapter(View paramView, int paramInt) {
    getAdapter().addView(paramView, paramInt);
  }
  
  public Adapter getAdapter() {
    return (Adapter)super.getAdapter();
  }
  
  int getViewCountInAdapter() {
    return getAdapter().getCount();
  }
  
  View getViewFromAdapter(int paramInt) {
    return getAdapter().getViewAt(paramInt);
  }
  
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    requestLayout();
    post(this.measureAndLayout);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    if (!this.mScrollEnabled)
      return false; 
    try {
      if (super.onInterceptTouchEvent(paramMotionEvent)) {
        NativeGestureUtil.notifyNativeGestureStarted((View)this, paramMotionEvent);
        return true;
      } 
      return false;
    } catch (IllegalArgumentException illegalArgumentException) {
      return false;
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    return !this.mScrollEnabled ? false : super.onTouchEvent(paramMotionEvent);
  }
  
  public void removeAllViewsFromAdapter() {
    getAdapter().removeAllViewsFromAdapter(this);
  }
  
  void removeViewFromAdapter(int paramInt) {
    getAdapter().removeViewAt(paramInt);
  }
  
  public void setCurrentItemFromJs(int paramInt, boolean paramBoolean) {
    this.mIsCurrentItemFromJs = true;
    setCurrentItem(paramInt, paramBoolean);
    this.mIsCurrentItemFromJs = false;
  }
  
  public void setScrollEnabled(boolean paramBoolean) {
    this.mScrollEnabled = paramBoolean;
  }
  
  public void setViews(List<View> paramList) {
    getAdapter().setViews(paramList);
  }
  
  class Adapter extends PagerAdapter {
    private boolean mIsViewPagerInIntentionallyInconsistentState = false;
    
    private final List<View> mViews = new ArrayList<View>();
    
    private Adapter() {}
    
    void addView(View param1View, int param1Int) {
      this.mViews.add(param1Int, param1View);
      notifyDataSetChanged();
      ReactViewPager.this.setOffscreenPageLimit(this.mViews.size());
    }
    
    public void destroyItem(ViewGroup param1ViewGroup, int param1Int, Object param1Object) {
      param1ViewGroup.removeView((View)param1Object);
    }
    
    public int getCount() {
      return this.mViews.size();
    }
    
    public int getItemPosition(Object param1Object) {
      return (this.mIsViewPagerInIntentionallyInconsistentState || !this.mViews.contains(param1Object)) ? -2 : this.mViews.indexOf(param1Object);
    }
    
    View getViewAt(int param1Int) {
      return this.mViews.get(param1Int);
    }
    
    public Object instantiateItem(ViewGroup param1ViewGroup, int param1Int) {
      View view = this.mViews.get(param1Int);
      param1ViewGroup.addView(view, 0, ReactViewPager.this.generateDefaultLayoutParams());
      return view;
    }
    
    public boolean isViewFromObject(View param1View, Object param1Object) {
      return (param1View == param1Object);
    }
    
    void removeAllViewsFromAdapter(ViewPager param1ViewPager) {
      this.mViews.clear();
      param1ViewPager.removeAllViews();
      this.mIsViewPagerInIntentionallyInconsistentState = true;
    }
    
    void removeViewAt(int param1Int) {
      this.mViews.remove(param1Int);
      notifyDataSetChanged();
      ReactViewPager.this.setOffscreenPageLimit(this.mViews.size());
    }
    
    void setViews(List<View> param1List) {
      this.mViews.clear();
      this.mViews.addAll(param1List);
      notifyDataSetChanged();
      this.mIsViewPagerInIntentionallyInconsistentState = false;
    }
  }
  
  class PageChangeListener implements ViewPager.e {
    private PageChangeListener() {}
    
    public void onPageScrollStateChanged(int param1Int) {
      String str;
      if (param1Int != 0) {
        if (param1Int != 1) {
          if (param1Int == 2) {
            str = "settling";
          } else {
            throw new IllegalStateException("Unsupported pageScrollState");
          } 
        } else {
          str = "dragging";
        } 
      } else {
        str = "idle";
      } 
      ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageScrollStateChangedEvent(ReactViewPager.this.getId(), str));
    }
    
    public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {
      ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageScrollEvent(ReactViewPager.this.getId(), param1Int1, param1Float));
    }
    
    public void onPageSelected(int param1Int) {
      if (!ReactViewPager.this.mIsCurrentItemFromJs)
        ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageSelectedEvent(ReactViewPager.this.getId(), param1Int)); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\viewpager\ReactViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */