package com.tt.miniapp.view.webcore;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.InputComponent;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapp.keyboarddetect.KeyboardHeightProvider;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapp.view.ScreenVisibilityDetector;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.DebugUtil;
import java.util.ArrayList;
import java.util.Collections;

public class AbsoluteLayout extends ViewGroup {
  private NestWebView mBindWebView;
  
  private SparseArray<View> mChildren = new SparseArray();
  
  private int mCurBackScrollX;
  
  private int mCurBackScrollY;
  
  private int mCurScrollX;
  
  private int mCurScrollY;
  
  private ArrayList<Integer> mIndexArray = new ArrayList<Integer>();
  
  private int mLayerType;
  
  private ScreenVisibilityDetector mScreenVisibilityDetector;
  
  private SparseArray<ViewOffset> mViewOffsets = new SparseArray();
  
  private ArrayList<IndexNode> mZIndexList = new ArrayList<IndexNode>();
  
  public AbsoluteLayout(Context paramContext, NestWebView paramNestWebView, int paramInt) {
    super(paramContext);
    this.mBindWebView = paramNestWebView;
    this.mLayerType = paramInt;
    init(paramContext);
  }
  
  private boolean canHideKeyBoard(MotionEvent paramMotionEvent) {
    return isUpLayerInRenderWithBrowser() ? (!isWebViewConsumeEventInRenderWithBrowser(paramMotionEvent)) : true;
  }
  
  private void init(Context paramContext) {
    setChildrenDrawingOrderEnabled(true);
    this.mScreenVisibilityDetector = new ScreenVisibilityDetector(this);
    this.mScreenVisibilityDetector.setOnVisibilityChangedListener(new ScreenVisibilityDetector.OnScreenVisibilityChangedListener() {
          public void onScreenVisibilityChanged(View param1View, boolean param1Boolean) {
            if (param1View instanceof ScreenVisibilityDetector.OnScreenVisibilityChangedListener)
              ((ScreenVisibilityDetector.OnScreenVisibilityChangedListener)param1View).onScreenVisibilityChanged(param1View, param1Boolean); 
          }
        });
  }
  
  private boolean isUpLayer() {
    return (this.mLayerType == 0);
  }
  
  private boolean isUpLayerInRenderWithBrowser() {
    return (isRenderInBrowserEnabled() && isUpLayer());
  }
  
  private boolean isWebViewConsumeEventInRenderWithBrowser(MotionEvent paramMotionEvent) {
    if (TTWebViewSupportWebView.isRenderInBrowserEnabled() && paramMotionEvent.getAction() == 0) {
      if (this.mBindWebView.dispatchTouchEvent(paramMotionEvent)) {
        long l = SystemClock.uptimeMillis();
        paramMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
        this.mBindWebView.dispatchTouchEvent(paramMotionEvent);
        paramMotionEvent.recycle();
        return false;
      } 
      return true;
    } 
    return false;
  }
  
  private void printIndex() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.mZIndexList != null)
      for (int i = 0; i < this.mZIndexList.size(); i++) {
        stringBuilder.append("[");
        stringBuilder.append(((IndexNode)this.mZIndexList.get(i)).toString());
        stringBuilder.append("]");
      }  
    stringBuilder.append("\n");
    if (this.mIndexArray != null)
      for (int i = 0; i < this.mIndexArray.size(); i++) {
        stringBuilder.append("(");
        Integer integer = this.mIndexArray.get(i);
        StringBuilder stringBuilder1 = new StringBuilder("key=");
        stringBuilder1.append(i);
        stringBuilder1.append(", value=");
        stringBuilder1.append(integer);
        stringBuilder.append(stringBuilder1.toString());
        stringBuilder.append(")");
      }  
    AppBrandLogger.i("tma_AbsoluteLayout", new Object[] { stringBuilder.toString() });
  }
  
  private boolean shouldHideKeyboard() {
    WebViewManager.IRender iRender = AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender();
    if (iRender != null) {
      NativeViewManager nativeViewManager = iRender.getNativeViewManager();
      if (nativeViewManager != null) {
        View view = nativeViewManager.getFocusedInput();
        if (view instanceof InputComponent)
          return ((InputComponent)view).isAutoBlur(); 
      } 
    } 
    return true;
  }
  
  private void updateZIndexInLayout() {
    int j = getChildCount();
    if (j <= 0)
      return; 
    this.mZIndexList.clear();
    this.mIndexArray.clear();
    boolean bool = false;
    int i;
    for (i = 0; i < j; i++) {
      View view = getChildAt(i);
      if (view.getLayoutParams() instanceof LayoutParams) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        this.mZIndexList.add(new IndexNode(i, layoutParams.zIndex));
      } 
    } 
    Collections.sort(this.mZIndexList);
    for (i = bool; i < j; i++) {
      IndexNode indexNode = this.mZIndexList.get(i);
      this.mIndexArray.add(Integer.valueOf(indexNode.index));
    } 
    if (DebugUtil.debug())
      printIndex(); 
  }
  
  public void addAndRegisterPlatformView(View paramView) {
    ViewOffset viewOffset = ViewOffset.obtain();
    viewOffset.curScrollX = getCurScrollX();
    viewOffset.curScrollY = getCurScrollY();
    addNativeView(paramView, viewOffset);
    NestWebView nestWebView = this.mBindWebView;
    if (nestWebView != null)
      nestWebView.registerPlatformView(paramView); 
  }
  
  public void addNativeView(View paramView, ViewOffset paramViewOffset) {
    this.mChildren.put(paramView.getId(), paramView);
    ViewOffset viewOffset = paramViewOffset;
    if (paramViewOffset == null)
      viewOffset = ViewOffset.obtain(); 
    this.mViewOffsets.put(paramView.getId(), viewOffset);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool = super.dispatchTouchEvent(paramMotionEvent);
    AppBrandLogger.i("tma_AbsoluteLayout", new Object[] { "dispatchTouchEvent: consumed = ", Boolean.valueOf(bool), " event action = ", Integer.valueOf(paramMotionEvent.getAction()) });
    return bool;
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(-2, -2, 0, 0);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return new LayoutParams(paramLayoutParams);
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2) {
    if (paramInt1 == this.mIndexArray.size()) {
      Integer integer = this.mIndexArray.get(paramInt2);
      if (integer != null)
        return integer.intValue(); 
    } 
    return super.getChildDrawingOrder(paramInt1, paramInt2);
  }
  
  public int getCurScrollX() {
    return this.mCurScrollX;
  }
  
  public int getCurScrollY() {
    return this.mCurScrollY;
  }
  
  public ViewOffset getViewOffset(int paramInt) {
    return (ViewOffset)this.mViewOffsets.get(paramInt);
  }
  
  public boolean isRenderInBrowserEnabled() {
    return TTWebViewSupportWebView.isRenderInBrowserEnabled();
  }
  
  public void onBackNativeScrollChanged(int paramInt1, int paramInt2, int paramInt3) {
    StringBuilder stringBuilder1;
    this.mCurBackScrollX = paramInt2;
    this.mCurBackScrollY = paramInt3;
    if (paramInt1 == -1)
      return; 
    View view = (View)this.mChildren.get(paramInt1);
    if (view != null && view.getLayoutParams() instanceof LayoutParams && ((LayoutParams)view.getLayoutParams()).isFullScreen)
      return; 
    ViewOffset viewOffset = (ViewOffset)this.mViewOffsets.get(paramInt1);
    if (viewOffset == null) {
      stringBuilder1 = new StringBuilder("onBackScrollChanged: viewId=");
      stringBuilder1.append(paramInt1);
      stringBuilder1.append(", offset=null");
      AppBrandLogger.i("tma_AbsoluteLayout", new Object[] { stringBuilder1.toString() });
      return;
    } 
    paramInt2 = viewOffset.getOffsetX(paramInt2);
    int i = viewOffset.getOffsetY(paramInt3);
    StringBuilder stringBuilder2 = new StringBuilder("onBackScrollChanged: viewId=");
    stringBuilder2.append(paramInt1);
    stringBuilder2.append(", scrollY=");
    stringBuilder2.append(paramInt3);
    stringBuilder2.append(", offsetY=");
    stringBuilder2.append(i);
    AppBrandLogger.i("tma_AbsoluteLayout", new Object[] { stringBuilder2.toString() });
    if (paramInt2 == 0 && i == 0)
      return; 
    if (stringBuilder1 != null && stringBuilder1.getVisibility() != 8 && stringBuilder1.getLayoutParams() instanceof LayoutParams) {
      LayoutParams layoutParams = (LayoutParams)stringBuilder1.getLayoutParams();
      paramInt1 = layoutParams.x;
      paramInt3 = layoutParams.y;
      layoutParams.x = paramInt1 - paramInt2;
      layoutParams.y = paramInt3 - i;
      stringBuilder1.offsetLeftAndRight(-paramInt2);
      stringBuilder1.offsetTopAndBottom(-i);
    } 
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    AppBrandLogger.d("tma_AbsoluteLayout", new Object[] { "onLayout" });
    updateZIndexInLayout();
    paramInt2 = getChildCount();
    if (paramInt2 <= 0)
      return; 
    for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++) {
      View view = getChildAt(paramInt1);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        paramInt3 = layoutParams.x;
        paramInt4 = layoutParams.y;
        StringBuilder stringBuilder = new StringBuilder("onLayout: viewId=");
        stringBuilder.append(view.getId());
        stringBuilder.append(", top=");
        stringBuilder.append(paramInt4);
        AppBrandLogger.i("tma_AbsoluteLayout", new Object[] { stringBuilder.toString() });
        view.layout(paramInt3, paramInt4, view.getMeasuredWidth() + paramInt3, view.getMeasuredHeight() + paramInt4);
      } 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    measureChildren(paramInt1, paramInt2);
    NestWebView nestWebView = this.mBindWebView;
    if (nestWebView != null) {
      paramInt1 = nestWebView.getMeasuredWidth();
      this.mBindWebView.getContentHeight();
      float f = (getResources().getDisplayMetrics()).density;
      setMeasuredDimension(paramInt1, 2147483647);
      return;
    } 
    setMeasuredDimension(0, 0);
  }
  
  public void onNativeScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mCurScrollX = paramInt1;
    this.mCurScrollY = paramInt2;
    paramInt3 = paramInt1 - paramInt3;
    paramInt2 -= paramInt4;
    if (paramInt3 == 0 && paramInt2 == 0)
      return; 
    paramInt4 = getChildCount();
    if (paramInt4 <= 0)
      return; 
    for (paramInt1 = 0; paramInt1 < paramInt4; paramInt1++) {
      View view = getChildAt(paramInt1);
      if (view.getVisibility() != 8 && view.getLayoutParams() instanceof LayoutParams) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.isFixed && !layoutParams.isFullScreen) {
          int i = layoutParams.x;
          int j = layoutParams.y;
          layoutParams.x = i - paramInt3;
          layoutParams.y = j - paramInt2;
          view.offsetLeftAndRight(-paramInt3);
          view.offsetTopAndBottom(-paramInt2);
        } 
      } 
    } 
    ScreenVisibilityDetector screenVisibilityDetector = this.mScreenVisibilityDetector;
    if (screenVisibilityDetector != null)
      screenVisibilityDetector.detect(); 
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    AppBrandLogger.i("tma_AbsoluteLayout", new Object[] { "onScrollChanged : " });
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (KeyboardHeightProvider.getKeyboardHeight() > 0 && shouldHideKeyboard() && canHideKeyBoard(paramMotionEvent))
      InputMethodUtil.hideSoftKeyboard((Activity)AppbrandContext.getInst().getCurrentActivity()); 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void onViewAdded(View paramView) {
    super.onViewAdded(paramView);
    ScreenVisibilityDetector screenVisibilityDetector = this.mScreenVisibilityDetector;
    if (screenVisibilityDetector != null)
      screenVisibilityDetector.onViewAdded(paramView); 
  }
  
  public void onViewRemoved(View paramView) {
    super.onViewRemoved(paramView);
    ScreenVisibilityDetector screenVisibilityDetector = this.mScreenVisibilityDetector;
    if (screenVisibilityDetector != null)
      screenVisibilityDetector.onViewRemoved(paramView); 
  }
  
  public void onWebViewScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!isRenderInBrowserEnabled())
      return; 
    this.mCurScrollX = paramInt1;
    this.mCurScrollY = paramInt2;
    paramInt4 = getChildCount();
    if (paramInt4 <= 0)
      return; 
    for (paramInt3 = 0; paramInt3 < paramInt4; paramInt3++) {
      View view = getChildAt(paramInt3);
      if (view.getLayoutParams() instanceof LayoutParams) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (view.getVisibility() == 8 || layoutParams.isFixed) {
          ViewOffset viewOffset = (ViewOffset)this.mViewOffsets.get(view.getId());
          if (viewOffset != null) {
            StringBuilder stringBuilder = new StringBuilder("onWebViewScrollChanged: viewId=");
            stringBuilder.append(view.getId());
            stringBuilder.append(", setScrollY=");
            stringBuilder.append(paramInt2);
            AppBrandLogger.i("tma_AbsoluteLayout", new Object[] { stringBuilder.toString() });
            viewOffset.setCurScrollX(paramInt1);
            viewOffset.setCurScrollY(paramInt2);
          } 
        } 
      } 
    } 
  }
  
  public void removeView(View paramView) {
    this.mChildren.delete(paramView.getId());
    this.mViewOffsets.delete(paramView.getId());
    super.removeView(paramView);
  }
  
  public boolean shouldDelayChildPressedState() {
    return false;
  }
  
  public void updateCurScrollXY(int paramInt) {
    ViewOffset viewOffset = (ViewOffset)this.mViewOffsets.get(paramInt);
    if (viewOffset != null) {
      viewOffset.setCurScrollX(getCurScrollX());
      viewOffset.setCurScrollY(getCurScrollY());
    } 
  }
  
  public void updateNativeViewOffset(View paramView, ViewOffset paramViewOffset) {
    if (paramView == null)
      return; 
    if (this.mChildren.get(paramView.getId()) == null)
      return; 
    ViewOffset viewOffset = paramViewOffset;
    if (paramViewOffset == null)
      viewOffset = ViewOffset.obtain(); 
    this.mViewOffsets.put(paramView.getId(), viewOffset);
  }
  
  static class IndexNode implements Comparable<IndexNode> {
    public int index;
    
    private int zIndex;
    
    public IndexNode(int param1Int1, int param1Int2) {
      this.index = param1Int1;
      this.zIndex = param1Int2;
    }
    
    public int compareTo(IndexNode param1IndexNode) {
      int i = this.zIndex;
      int j = param1IndexNode.zIndex;
      return (i > j) ? 1 : ((i < j) ? -1 : 0);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("IndexNode: index=");
      stringBuilder.append(this.index);
      stringBuilder.append(", zIndex=");
      stringBuilder.append(this.zIndex);
      return stringBuilder.toString();
    }
  }
  
  public static class LayoutParams extends ViewGroup.LayoutParams {
    public boolean isFixed;
    
    public boolean isFullScreen;
    
    public int x;
    
    public int y;
    
    public int zIndex;
    
    public LayoutParams(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      super(param1Int1, param1Int2);
      this.x = param1Int3;
      this.y = param1Int4;
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
  }
  
  public static class ViewOffset {
    public int curScrollX;
    
    public int curScrollY;
    
    public static ViewOffset obtain() {
      return new ViewOffset();
    }
    
    public int getOffsetX(int param1Int) {
      int i = this.curScrollX;
      this.curScrollX = param1Int;
      return param1Int - i;
    }
    
    public int getOffsetY(int param1Int) {
      int i = this.curScrollY;
      this.curScrollY = param1Int;
      return param1Int - i;
    }
    
    public void setCurScrollX(int param1Int) {
      this.curScrollX = param1Int;
    }
    
    public void setCurScrollY(int param1Int) {
      this.curScrollY = param1Int;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\AbsoluteLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */