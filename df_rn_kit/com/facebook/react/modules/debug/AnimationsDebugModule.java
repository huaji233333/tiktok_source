package com.facebook.react.modules.debug;

import android.content.Context;
import android.widget.Toast;
import com.a;
import com.facebook.common.e.a;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import java.util.Locale;

@ReactModule(name = "AnimationsDebugModule")
public class AnimationsDebugModule extends ReactContextBaseJavaModule {
  private final DeveloperSettings mCatalystSettings;
  
  private FpsDebugFrameCallback mFrameCallback;
  
  public AnimationsDebugModule(ReactApplicationContext paramReactApplicationContext, DeveloperSettings paramDeveloperSettings) {
    super(paramReactApplicationContext);
    this.mCatalystSettings = paramDeveloperSettings;
  }
  
  public String getName() {
    return "AnimationsDebugModule";
  }
  
  public void onCatalystInstanceDestroy() {
    FpsDebugFrameCallback fpsDebugFrameCallback = this.mFrameCallback;
    if (fpsDebugFrameCallback != null) {
      fpsDebugFrameCallback.stop();
      this.mFrameCallback = null;
    } 
  }
  
  @ReactMethod
  public void startRecordingFps() {
    DeveloperSettings developerSettings = this.mCatalystSettings;
    if (developerSettings != null) {
      if (!developerSettings.isAnimationFpsDebugEnabled())
        return; 
      if (this.mFrameCallback == null) {
        this.mFrameCallback = new FpsDebugFrameCallback(ChoreographerCompat.getInstance(), (ReactContext)getReactApplicationContext());
        this.mFrameCallback.startAndRecordFpsAtEachFrame();
        return;
      } 
      throw new JSApplicationCausedNativeException("Already recording FPS!");
    } 
  }
  
  @ReactMethod
  public void stopRecordingFps(double paramDouble) {
    FpsDebugFrameCallback fpsDebugFrameCallback = this.mFrameCallback;
    if (fpsDebugFrameCallback == null)
      return; 
    fpsDebugFrameCallback.stop();
    FpsDebugFrameCallback.FpsInfo fpsInfo = this.mFrameCallback.getFpsInfo((long)paramDouble);
    if (fpsInfo == null) {
      Toast.makeText((Context)getReactApplicationContext(), "Unable to get FPS info", 1);
    } else {
      String str2 = a.a(Locale.US, "FPS: %.2f, %d frames (%d expected)", new Object[] { Double.valueOf(fpsInfo.fps), Integer.valueOf(fpsInfo.totalFrames), Integer.valueOf(fpsInfo.totalExpectedFrames) });
      String str3 = a.a(Locale.US, "JS FPS: %.2f, %d frames (%d expected)", new Object[] { Double.valueOf(fpsInfo.jsFps), Integer.valueOf(fpsInfo.totalJsFrames), Integer.valueOf(fpsInfo.totalExpectedFrames) });
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str2);
      stringBuilder.append("\n");
      stringBuilder.append(str3);
      stringBuilder.append("\nTotal Time MS: ");
      stringBuilder.append(a.a(Locale.US, "%d", new Object[] { Integer.valueOf(fpsInfo.totalTimeMs) }));
      String str1 = stringBuilder.toString();
      a.a("ReactNative", str1);
      _lancet.com_ss_android_ugc_aweme_lancet_DesignBugFixLancet_show(Toast.makeText((Context)getReactApplicationContext(), str1, 1));
    } 
    this.mFrameCallback = null;
  }
  
  class AnimationsDebugModule {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\debug\AnimationsDebugModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */