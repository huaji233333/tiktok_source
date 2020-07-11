package com.tt.miniapp.favorite;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.frontendapiinterface.d;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.database.ProcessSpData;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;

public abstract class FavoriteGuideView {
  protected Callback mCallback;
  
  protected FavoriteConfig mConfig;
  
  protected View mContentView;
  
  private final Runnable mDismissTask = new Runnable() {
      public void run() {
        FavoriteGuideView.this.dismiss(false);
      }
    };
  
  private long mLastShowTime = -1L;
  
  protected FavoriteGuideModel mModel;
  
  public FavoriteGuideView(FavoriteGuideModel paramFavoriteGuideModel, Callback paramCallback) {
    this.mModel = paramFavoriteGuideModel;
    this.mCallback = paramCallback;
    this.mConfig = FavoriteConfig.get();
  }
  
  public static String formatSpDataKey(String paramString, boolean paramBoolean) {
    String str = (AppbrandApplicationImpl.getInst().getAppInfo()).appId;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(":");
    stringBuilder.append(paramString);
    stringBuilder.append(":");
    if (paramBoolean) {
      paramString = "tip";
    } else {
      paramString = "bar";
    } 
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  public d check() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity != null && !TextUtils.isEmpty(appInfoEntity.versionType) && ("current".equals(appInfoEntity.versionType) || "audit".equals(appInfoEntity.versionType))) {
      if (getShowCount() >= 2)
        return d.a("reach the maximum show count limit"); 
      if (System.currentTimeMillis() - getLastShowTime() <= getShowInterval())
        return d.a("unreach the minimum show time interval limit"); 
    } 
    return d.a();
  }
  
  public void dismiss(boolean paramBoolean) {
    View view = this.mContentView;
    if (view == null)
      return; 
    ViewParent viewParent = view.getParent();
    if (viewParent instanceof ViewGroup) {
      ((ViewGroup)viewParent).removeView(this.mContentView);
      this.mCallback.onDismiss();
      FavoriteEvent.onGuideClose(paramBoolean, isTip(), getCurrentDuration(), getShowContent());
    } 
    this.mContentView.removeCallbacks(this.mDismissTask);
  }
  
  protected String formatSpDataKey(String paramString) {
    return formatSpDataKey(paramString, isTip());
  }
  
  protected long getCurrentDuration() {
    long l1;
    if (this.mLastShowTime < 0L) {
      l1 = 0L;
    } else {
      l1 = System.currentTimeMillis() - this.mLastShowTime;
    } 
    long l2 = l1;
    if (l1 < 0L)
      l2 = 0L; 
    return l2;
  }
  
  protected long getLastShowTime() {
    return ProcessSpData.getLong("MiniAppSpData", formatSpDataKey("lastShowTime"), 0L);
  }
  
  protected abstract int getLayoutId();
  
  protected String getShowContent() {
    return !this.mConfig.cpCustomTip ? this.mConfig.tip : (TextUtils.isEmpty(this.mModel.content) ? this.mConfig.tip : this.mModel.content);
  }
  
  protected int getShowCount() {
    return ProcessSpData.getInt("MiniAppSpData", formatSpDataKey("showCount"), 0);
  }
  
  protected abstract int getShowGravity();
  
  protected long getShowInterval() {
    return isTip() ? this.mConfig.showIntervalTip : this.mConfig.showIntervalBar;
  }
  
  protected abstract int getShowX();
  
  protected abstract int getShowY();
  
  protected long getTotalShowDuration() {
    return isTip() ? 3000L : 10000L;
  }
  
  public boolean isShowing() {
    View view = this.mContentView;
    return (view != null && view.getParent() != null);
  }
  
  public abstract boolean isTip();
  
  protected void onShow() {
    saveShowCount(getShowCount() + 1);
    this.mLastShowTime = System.currentTimeMillis();
    saveLastShowTime(this.mLastShowTime);
    FavoriteEvent.onGuideShow(isTip(), getShowContent());
  }
  
  protected void saveLastShowTime(long paramLong) {
    ProcessSpData.saveLong("MiniAppSpData", formatSpDataKey("lastShowTime"), paramLong);
  }
  
  protected void saveShowCount(int paramInt) {
    ProcessSpData.saveInt("MiniAppSpData", formatSpDataKey("showCount"), paramInt);
  }
  
  public void show() {
    FrameLayout frameLayout = (FrameLayout)this.mCallback.getActivity().findViewById(2097545417);
    this.mContentView = LayoutInflater.from((Context)this.mCallback.getActivity()).inflate(getLayoutId(), (ViewGroup)frameLayout, false);
    ((TextView)this.mContentView.findViewById(2097545467)).setText(FavoriteUtils.endEllipsize(getShowContent(), 12));
    ((ImageView)this.mContentView.findViewById(2097545222)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            FavoriteGuideView.this.dismiss(true);
          }
        });
    this.mContentView.postDelayed(this.mDismissTask, getTotalShowDuration());
    showAtLocation(frameLayout, getShowGravity(), getShowX(), getShowY());
    onShow();
  }
  
  public void showAtLocation(FrameLayout paramFrameLayout, int paramInt1, int paramInt2, int paramInt3) {
    if (paramFrameLayout != null) {
      if (this.mContentView == null)
        return; 
      int i = paramInt2;
      if (paramInt2 < 0)
        i = 0; 
      paramInt2 = paramInt3;
      if (paramInt3 < 0)
        paramInt2 = 0; 
      paramFrameLayout.addView(this.mContentView);
      ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
      if (layoutParams instanceof FrameLayout.LayoutParams) {
        FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams)layoutParams;
        paramInt3 = paramInt1;
        if (paramInt1 == 0)
          paramInt3 = 51; 
        layoutParams1.gravity = paramInt3;
        if ((paramInt3 & 0x3) == 3)
          layoutParams1.leftMargin = i; 
        if ((paramInt3 & 0x5) == 5)
          layoutParams1.rightMargin = i; 
        if ((paramInt3 & 0x30) == 48)
          layoutParams1.topMargin = paramInt2; 
        if ((paramInt3 & 0x50) == 80)
          layoutParams1.bottomMargin = paramInt2; 
      } 
      this.mContentView.setLayoutParams(layoutParams);
    } 
  }
  
  public static interface Callback {
    Activity getActivity();
    
    boolean isGame();
    
    void onClickAddButton();
    
    void onDismiss();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\favorite\FavoriteGuideView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */