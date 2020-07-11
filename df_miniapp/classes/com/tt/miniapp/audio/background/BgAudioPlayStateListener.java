package com.tt.miniapp.audio.background;

public interface BgAudioPlayStateListener {
  void onBgPlayProcessDied(BgAudioModel paramBgAudioModel, boolean paramBoolean);
  
  void onPlayStateChange(String paramString, BgAudioModel paramBgAudioModel, boolean paramBoolean);
  
  void onProgressChange(int paramInt, BgAudioModel paramBgAudioModel);
  
  void onTriggerPlay(BgAudioModel paramBgAudioModel);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\background\BgAudioPlayStateListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */