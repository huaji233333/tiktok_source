package com.tt.miniapp.service.bgaudio;

import com.tt.miniapp.audio.background.BgAudioPlayStateListener;
import com.tt.miniapp.service.PureServiceInterface;

public interface BgAudioServiceInterface extends PureServiceInterface {
  void openCurrentBgPlayMiniApp(String paramString1, String paramString2, String paramString3);
  
  boolean playNext();
  
  boolean playPrevious();
  
  void registerPlayStateListener(BgAudioPlayStateListener paramBgAudioPlayStateListener);
  
  void setKeepAlive(boolean paramBoolean);
  
  boolean stop();
  
  boolean switchPlayState();
  
  void unregisterPlayStateListener(BgAudioPlayStateListener paramBgAudioPlayStateListener);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\bgaudio\BgAudioServiceInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */