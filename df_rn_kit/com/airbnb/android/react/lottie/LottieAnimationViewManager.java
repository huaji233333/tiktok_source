package com.airbnb.android.react.lottie;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.u;
import android.view.View;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.Map;
import org.json.JSONObject;

class LottieAnimationViewManager extends SimpleViewManager<LottieAnimationView> {
  private static final String TAG = LottieAnimationViewManager.class.getSimpleName();
  
  public LottieAnimationView createViewInstance(ThemedReactContext paramThemedReactContext) {
    LottieAnimationView lottieAnimationView = new LottieAnimationView((Context)paramThemedReactContext);
    lottieAnimationView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    return lottieAnimationView;
  }
  
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("play", Integer.valueOf(1), "reset", Integer.valueOf(2));
  }
  
  public Map<String, Object> getExportedViewConstants() {
    return MapBuilder.builder().put("VERSION", Integer.valueOf(1)).build();
  }
  
  public String getName() {
    return "LottieAnimationView";
  }
  
  public void receiveCommand(LottieAnimationView paramLottieAnimationView, int paramInt, ReadableArray paramReadableArray) {
    if (paramInt != 1) {
      if (paramInt != 2)
        return; 
      (new Handler(Looper.getMainLooper())).post(new Runnable(this, paramLottieAnimationView) {
            public final void run() {
              if (u.B((View)this.a)) {
                this.a.f();
                this.a.setProgress(0.0F);
              } 
            }
          });
      return;
    } 
    (new Handler(Looper.getMainLooper())).post(new Runnable(this, paramReadableArray, paramLottieAnimationView) {
          public final void run() {
            int i = this.a.getInt(0);
            int j = this.a.getInt(1);
            if (i != -1 && j != -1)
              this.b.a(this.a.getInt(0), this.a.getInt(1)); 
            if (u.B((View)this.b)) {
              this.b.setProgress(0.0F);
              this.b.b();
            } 
          }
        });
  }
  
  @ReactProp(name = "enableMergePathsAndroidForKitKatAndAbove")
  public void setEnableMergePaths(LottieAnimationView paramLottieAnimationView, boolean paramBoolean) {
    paramLottieAnimationView.a(paramBoolean);
  }
  
  @ReactProp(name = "hardwareAccelerationAndroid")
  public void setHardwareAcceleration(LottieAnimationView paramLottieAnimationView, boolean paramBoolean) {
    paramLottieAnimationView.b(paramBoolean);
  }
  
  @ReactProp(name = "imageAssetsFolder")
  public void setImageAssetsFolder(LottieAnimationView paramLottieAnimationView, String paramString) {
    paramLottieAnimationView.setImageAssetsFolder(paramString);
  }
  
  @ReactProp(name = "loop")
  public void setLoop(LottieAnimationView paramLottieAnimationView, boolean paramBoolean) {
    paramLottieAnimationView.c(paramBoolean);
  }
  
  @ReactProp(name = "progress")
  public void setProgress(LottieAnimationView paramLottieAnimationView, float paramFloat) {
    paramLottieAnimationView.setProgress(paramFloat);
  }
  
  @ReactProp(name = "resizeMode")
  public void setResizeMode(LottieAnimationView paramLottieAnimationView, String paramString) {
    if ("cover".equals(paramString)) {
      paramLottieAnimationView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      return;
    } 
    if ("contain".equals(paramString)) {
      paramLottieAnimationView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      return;
    } 
    if ("center".equals(paramString))
      paramLottieAnimationView.setScaleType(ImageView.ScaleType.CENTER); 
  }
  
  @ReactProp(name = "sourceJson")
  public void setSourceJson(LottieAnimationView paramLottieAnimationView, String paramString) {
    try {
      paramLottieAnimationView.setAnimation(new JSONObject(paramString));
      return;
    } catch (Exception exception) {
      return;
    } 
  }
  
  @ReactProp(name = "sourceName")
  public void setSourceName(LottieAnimationView paramLottieAnimationView, String paramString) {
    paramLottieAnimationView.setAnimation(paramString);
  }
  
  @ReactProp(name = "speed")
  public void setSpeed(LottieAnimationView paramLottieAnimationView, double paramDouble) {
    paramLottieAnimationView.setSpeed((float)paramDouble);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\airbnb\android\react\lottie\LottieAnimationViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */