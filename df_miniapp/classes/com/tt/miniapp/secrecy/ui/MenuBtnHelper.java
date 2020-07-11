package com.tt.miniapp.secrecy.ui;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.c;
import android.support.v4.view.b.f;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.secrecy.SecrecyEntity;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.FeignHostConfig;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MenuBtnHelper {
  private Animation.AnimationListener mCheckListener;
  
  private Animation mHideAnim;
  
  private Animation mShowAnim;
  
  private Animation mStressAnim;
  
  private Animation.AnimationListener mSwitchListener;
  
  public SecrecyUIHelper mUIHelper;
  
  private List<WeakReference<ImageView>> mViewRefs;
  
  MenuBtnHelper(SecrecyUIHelper paramSecrecyUIHelper) {
    this.mUIHelper = paramSecrecyUIHelper;
    this.mViewRefs = new LinkedList<WeakReference<ImageView>>();
    initAnimation();
  }
  
  private void clearAnimWithoutCallback(ImageView paramImageView) {
    this.mStressAnim.setAnimationListener(null);
    this.mHideAnim.setAnimationListener(null);
    this.mShowAnim.setAnimationListener(null);
    paramImageView.clearAnimation();
    this.mStressAnim.setAnimationListener(this.mCheckListener);
    this.mShowAnim.setAnimationListener(this.mCheckListener);
    this.mHideAnim.setAnimationListener(this.mSwitchListener);
  }
  
  private String getCurrentPageBarTextStyle() {
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager == null)
      return "white"; 
    WebViewManager.IRender iRender = webViewManager.getCurrentIRender();
    if (iRender instanceof AppbrandSinglePage) {
      String str = ((AppbrandSinglePage)iRender).getTitleBar().getBarTextStyle();
      if (str != null)
        return str; 
    } 
    return "white";
  }
  
  private Drawable getImageDrawable(int paramInt) {
    Drawable drawable;
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (12 == paramInt)
      return c.a((Context)application, 2097479780); 
    if (13 == paramInt)
      return c.a((Context)application, 2097479781); 
    FeignHostConfig feignHostConfig = FeignHostConfig.inst();
    boolean bool = AppbrandContext.getInst().isGame();
    if (TextUtils.equals(getCurrentPageBarTextStyle(), "black")) {
      drawable = feignHostConfig.getAppMoreMenuBlackDrawable();
      if (drawable != null)
        return drawable; 
    } else {
      if (bool) {
        drawable = drawable.getGameMoreMenuDrawable();
      } else {
        drawable = drawable.getAppMoreMenuDrawable();
      } 
      if (drawable != null)
        return drawable; 
    } 
    return c.a((Context)application, 2097479802);
  }
  
  private void initAnimation() {
    this.mCheckListener = new AnimEndListener() {
        public void onAnimationEnd(Animation param1Animation) {
          AppbrandContext.mainHandler.post(new Runnable() {
                public void run() {
                  MenuBtnHelper.this.updateState(3);
                }
              });
        }
      };
    this.mSwitchListener = new AnimEndListener() {
        public void onAnimationEnd(Animation param1Animation) {
          AppbrandContext.mainHandler.post(new Runnable() {
                public void run() {
                  MenuBtnHelper.this.mUIHelper.switch2NextSecrecy();
                }
              });
        }
      };
    this.mStressAnim = (Animation)new AlphaAnimation(1.0F, 0.4F);
    this.mStressAnim.setInterpolator(f.a(0.25F, 0.1F, 0.25F, 1.0F));
    this.mStressAnim.setDuration(800L);
    this.mStressAnim.setRepeatMode(2);
    this.mStressAnim.setRepeatCount(1);
    this.mStressAnim.setAnimationListener(this.mCheckListener);
    this.mHideAnim = (Animation)new AlphaAnimation(1.0F, 0.0F);
    this.mHideAnim.setInterpolator(f.a(0.8F, 0.4F, 1.0F, 0.6F));
    this.mHideAnim.setDuration(800L);
    this.mHideAnim.setFillAfter(true);
    this.mHideAnim.setAnimationListener(this.mSwitchListener);
    this.mShowAnim = (Animation)new AlphaAnimation(0.0F, 1.0F);
    this.mShowAnim.setInterpolator(f.a(0.2F, 0.6F, 0.0F, 0.4F));
    this.mShowAnim.setDuration(800L);
    this.mShowAnim.setFillAfter(true);
    this.mShowAnim.setAnimationListener(this.mCheckListener);
  }
  
  private void updateState(ImageView paramImageView, int paramInt) {
    SecrecyEntity secrecyEntity = this.mUIHelper.getShownEntity();
    clearAnimWithoutCallback(paramImageView);
    if (secrecyEntity == null) {
      paramImageView.setImageDrawable(getImageDrawable(-1));
      return;
    } 
    paramImageView.setImageDrawable(getImageDrawable(secrecyEntity.type));
    if (2 == paramInt) {
      paramImageView.startAnimation(this.mShowAnim);
      return;
    } 
    if (1 == paramInt) {
      paramImageView.startAnimation(this.mStressAnim);
      return;
    } 
    if (this.mUIHelper.needAnim2Hide()) {
      paramImageView.startAnimation(this.mHideAnim);
      return;
    } 
    paramImageView.startAnimation(this.mStressAnim);
  }
  
  void registerView(ImageView paramImageView) {
    int i = 0;
    boolean bool = false;
    while (i < this.mViewRefs.size()) {
      ImageView imageView = ((WeakReference<ImageView>)this.mViewRefs.get(i)).get();
      if (imageView == null) {
        this.mViewRefs.remove(i);
        continue;
      } 
      if (Objects.equals(paramImageView, imageView))
        bool = true; 
      i++;
    } 
    if (bool)
      return; 
    this.mViewRefs.add(new WeakReference<ImageView>(paramImageView));
    updateState(paramImageView, 0);
  }
  
  void updateState(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder("updateState: ");
    stringBuilder.append(paramInt);
    String str = stringBuilder.toString();
    int i = 0;
    AppBrandLogger.d("MenuBtnHelper", new Object[] { str });
    while (i < this.mViewRefs.size()) {
      ImageView imageView = ((WeakReference<ImageView>)this.mViewRefs.get(i)).get();
      if (imageView == null) {
        this.mViewRefs.remove(i);
        continue;
      } 
      updateState(imageView, paramInt);
      i++;
    } 
  }
  
  static abstract class AnimEndListener implements Animation.AnimationListener {
    private AnimEndListener() {}
    
    public void onAnimationRepeat(Animation param1Animation) {}
    
    public void onAnimationStart(Animation param1Animation) {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\secrec\\ui\MenuBtnHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */