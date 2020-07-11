package com.tt.miniapp.video.plugin.base;

import android.os.Bundle;

public interface IVideoPluginEvent {
  Bundle getArgs();
  
  int getType();
  
  void setArgs(Bundle paramBundle);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\base\IVideoPluginEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */