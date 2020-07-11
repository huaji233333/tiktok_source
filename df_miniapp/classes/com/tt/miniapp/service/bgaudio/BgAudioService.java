package com.tt.miniapp.service.bgaudio;

import com.tt.miniapp.audio.background.BgAudioManagerServiceNative;
import com.tt.miniapp.audio.background.BgAudioPlayStateListener;

public class BgAudioService implements BgAudioServiceInterface {
  public void openCurrentBgPlayMiniApp(String paramString1, String paramString2, String paramString3) {
    BgAudioManagerServiceNative.getInst().openCurrentBgPlayMiniApp(paramString1, paramString2, paramString3);
  }
  
  public boolean playNext() {
    return BgAudioManagerServiceNative.getInst().playNext();
  }
  
  public boolean playPrevious() {
    return BgAudioManagerServiceNative.getInst().playPrevious();
  }
  
  public void registerPlayStateListener(BgAudioPlayStateListener paramBgAudioPlayStateListener) {
    BgAudioManagerServiceNative.getInst().registerHostPlayStateListener(paramBgAudioPlayStateListener);
  }
  
  public void setKeepAlive(boolean paramBoolean) {
    BgAudioManagerServiceNative.getInst().setKeepAlive(paramBoolean);
  }
  
  public boolean stop() {
    return BgAudioManagerServiceNative.getInst().stopCurrentBg();
  }
  
  public boolean switchPlayState() {
    return BgAudioManagerServiceNative.getInst().switchCurrentBgPlayState();
  }
  
  public void unregisterPlayStateListener(BgAudioPlayStateListener paramBgAudioPlayStateListener) {
    BgAudioManagerServiceNative.getInst().unregisterHostPlayStateListener(paramBgAudioPlayStateListener);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\bgaudio\BgAudioService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */