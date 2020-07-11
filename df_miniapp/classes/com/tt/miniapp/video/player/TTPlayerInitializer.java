package com.tt.miniapp.video.player;

import android.content.Context;
import com.ss.ttvideoengine.TTVideoEngine;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TTPlayerInitializer {
  private final String TAG = "tma_TTPlayerInitializer";
  
  private int mPlayerType;
  
  public TTVideoEngine createVideoEngine() {
    return createVideoEngine(getPlayerType());
  }
  
  public TTVideoEngine createVideoEngine(int paramInt) {
    this.mPlayerType = paramInt;
    TTVideoEngine tTVideoEngine = new TTVideoEngine((Context)AppbrandContext.getInst().getApplicationContext(), paramInt);
    tTVideoEngine.setIntOption(110, 1);
    tTVideoEngine.setTag("miniapp");
    StringBuilder stringBuilder = new StringBuilder("miniapp_appid:");
    stringBuilder.append((AppbrandApplication.getInst().getAppInfo()).appId);
    tTVideoEngine.setSubTag(stringBuilder.toString());
    return tTVideoEngine;
  }
  
  public int getCurPlayerType() {
    return this.mPlayerType;
  }
  
  public int getPlayerType() {
    return 0;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TTPlayerType {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\player\TTPlayerInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */