package com.tt.miniapp.favorite;

import android.graphics.Rect;
import android.view.View;
import com.tt.frontendapiinterface.d;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;

public class FavoriteGuideTipView extends FavoriteGuideView {
  public FavoriteGuideTipView(FavoriteGuideModel paramFavoriteGuideModel, FavoriteGuideView.Callback paramCallback) {
    super(paramFavoriteGuideModel, paramCallback);
  }
  
  public d check() {
    return !isMoreViewVisible() ? d.a("invalid toolbar position") : super.check();
  }
  
  protected int getLayoutId() {
    return 2097676303;
  }
  
  protected View getMoreView() {
    try {
      return this.mCallback.isGame() ? this.mCallback.getActivity().findViewById(2097545408) : AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender().getRootView().findViewById(2097545408);
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("FavoriteGuideTipView", "getMoreView", exception);
      return null;
    } 
  }
  
  protected int getShowGravity() {
    return 53;
  }
  
  protected int getShowX() {
    return getTipRightMargin();
  }
  
  protected int getShowY() {
    return getTipTopMargin();
  }
  
  protected int getTipRightMargin() {
    return this.mCallback.getActivity().getResources().getDimensionPixelSize(2097414158);
  }
  
  protected int getTipTopMargin() {
    int i = 0;
    try {
      View view;
      if (this.mCallback.isGame()) {
        view = this.mCallback.getActivity().findViewById(2097545400);
      } else {
        view = AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender().getRootView().findViewById(2097545400);
      } 
      Rect rect = new Rect();
      if (view != null) {
        if (!view.getGlobalVisibleRect(rect))
          return 0; 
        int[] arrayOfInt = new int[2];
        view.getLocationOnScreen(arrayOfInt);
        i = view.getMeasuredHeight();
        int j = this.mCallback.getActivity().getResources().getDimensionPixelSize(2097414159);
        int k = arrayOfInt[1];
        i = k + i + j;
      } 
      return i;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("FavoriteGuideTipView", "getTipTopMargin", exception);
      return 0;
    } 
  }
  
  protected boolean isMoreViewVisible() {
    View view = getMoreView();
    Rect rect = new Rect();
    return (view != null && view.getGlobalVisibleRect(rect));
  }
  
  public boolean isTip() {
    return true;
  }
  
  public void show() {
    super.show();
    this.mContentView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            FavoriteGuideTipView.this.dismiss(true);
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\favorite\FavoriteGuideTipView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */