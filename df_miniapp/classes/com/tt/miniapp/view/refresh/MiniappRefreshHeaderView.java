package com.tt.miniapp.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import com.tt.miniapp.view.loading.NewLoadingView;
import com.tt.miniapphost.AppBrandLogger;

public class MiniappRefreshHeaderView extends NewLoadingView implements SwipeRefreshTrigger, SwipeTrigger {
  private IRefreshState mRefreshState;
  
  public MiniappRefreshHeaderView(Context paramContext) {
    super(paramContext);
  }
  
  public MiniappRefreshHeaderView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public void onFingerMove(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    if (!paramBoolean1) {
      if (paramInt <= getHeight()) {
        double d1 = paramInt;
        double d2 = getHeight();
        Double.isNaN(d1);
        Double.isNaN(d2);
        beforePrepare(d1 / d2);
        return;
      } 
      beforePrepare(1.0D);
    } 
  }
  
  public void onFingerRelease() {
    AppBrandLogger.d("tma_RefreshHeaderView", new Object[] { "onRelease" });
  }
  
  public void onPageRefresh() {
    stop();
    AppBrandLogger.d("tma_RefreshHeaderView", new Object[] { "onPrepare" });
  }
  
  public void onRefreshComplete() {
    AppBrandLogger.d("tma_RefreshHeaderView", new Object[] { "onComplete" });
    IRefreshState iRefreshState = this.mRefreshState;
    if (iRefreshState != null)
      iRefreshState.onComplete(); 
  }
  
  public void onRefreshTrigger() {
    start();
    AppBrandLogger.d("tma_RefreshHeaderView", new Object[] { "onRefresh" });
    IRefreshState iRefreshState = this.mRefreshState;
    if (iRefreshState != null)
      iRefreshState.onRefresh(); 
  }
  
  public void onReset() {
    stop();
    AppBrandLogger.d("tma_RefreshHeaderView", new Object[] { "onReset" });
  }
  
  public void setRefreshState(IRefreshState paramIRefreshState) {
    this.mRefreshState = paramIRefreshState;
  }
  
  public static interface IRefreshState {
    void onComplete();
    
    void onRefresh();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\refresh\MiniappRefreshHeaderView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */