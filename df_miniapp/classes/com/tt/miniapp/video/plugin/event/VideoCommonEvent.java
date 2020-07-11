package com.tt.miniapp.video.plugin.event;

import android.os.Bundle;
import com.tt.miniapp.video.plugin.base.IVideoPluginEvent;

public class VideoCommonEvent implements IVideoPluginEvent {
  private Bundle mArgs;
  
  private int mType;
  
  public VideoCommonEvent(int paramInt) {
    this.mType = paramInt;
  }
  
  public VideoCommonEvent(int paramInt, Bundle paramBundle) {
    this.mType = paramInt;
    this.mArgs = paramBundle;
  }
  
  public Bundle getArgs() {
    return this.mArgs;
  }
  
  public int getType() {
    return this.mType;
  }
  
  public void setArgs(Bundle paramBundle) {
    this.mArgs = paramBundle;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\event\VideoCommonEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */