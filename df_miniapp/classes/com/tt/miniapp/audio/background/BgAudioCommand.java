package com.tt.miniapp.audio.background;

import android.text.TextUtils;

public enum BgAudioCommand {
  GET_AUDIO_STATE,
  NEED_KEEP_ALIVE,
  OBTAIN_MANAGER("obtainManager"),
  PAUSE("obtainManager"),
  PLAY("play"),
  SEEK("play"),
  SET_AUDIO_MODEL("play"),
  STOP("play");
  
  private String mCommand;
  
  static {
    PAUSE = new BgAudioCommand("PAUSE", 2, "pause");
    STOP = new BgAudioCommand("STOP", 3, "stop");
    SEEK = new BgAudioCommand("SEEK", 4, "seek");
    SET_AUDIO_MODEL = new BgAudioCommand("SET_AUDIO_MODEL", 5, "setAudioModel");
    GET_AUDIO_STATE = new BgAudioCommand("GET_AUDIO_STATE", 6, "getAudioState");
    NEED_KEEP_ALIVE = new BgAudioCommand("NEED_KEEP_ALIVE", 7, "needKeepAlive");
    $VALUES = new BgAudioCommand[] { OBTAIN_MANAGER, PLAY, PAUSE, STOP, SEEK, SET_AUDIO_MODEL, GET_AUDIO_STATE, NEED_KEEP_ALIVE };
  }
  
  BgAudioCommand(String paramString1) {
    this.mCommand = paramString1;
  }
  
  public static BgAudioCommand fromString(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    for (BgAudioCommand bgAudioCommand : values()) {
      if (bgAudioCommand.mCommand.equalsIgnoreCase(paramString))
        return bgAudioCommand; 
    } 
    return null;
  }
  
  public final String getCommand() {
    return this.mCommand;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\background\BgAudioCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */