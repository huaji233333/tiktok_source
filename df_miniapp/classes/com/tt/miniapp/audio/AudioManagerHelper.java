package com.tt.miniapp.audio;

import android.content.Context;
import android.media.AudioManager;

public class AudioManagerHelper {
  public AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;
  
  private AudioManager mAudioManager;
  
  public AudioManagerHelper(Context paramContext, final AudioFocusChangeListener listener) {
    if (paramContext == null)
      return; 
    this.mAudioManager = (AudioManager)paramContext.getApplicationContext().getSystemService("audio");
    this.mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int param1Int) {
          if (param1Int != -3 && param1Int != -2 && param1Int != -1) {
            if (param1Int != 1 && param1Int != 2 && param1Int != 3)
              return; 
            AudioFocusChangeListener audioFocusChangeListener = listener;
            if (audioFocusChangeListener != null) {
              audioFocusChangeListener.gainAudioFocus();
              return;
            } 
          } else {
            AudioFocusChangeListener audioFocusChangeListener = listener;
            if (audioFocusChangeListener != null)
              audioFocusChangeListener.lossAudioFocus(); 
          } 
        }
      };
  }
  
  public void abandonAudioFocus(Context paramContext) {
    if (this.mAudioManager == null && paramContext != null)
      this.mAudioManager = (AudioManager)paramContext.getApplicationContext().getSystemService("audio"); 
    AudioManager audioManager = this.mAudioManager;
    if (audioManager != null) {
      AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = this.mAudioFocusChangeListener;
      if (onAudioFocusChangeListener != null)
        audioManager.abandonAudioFocus(onAudioFocusChangeListener); 
    } 
    this.mAudioManager = null;
  }
  
  public void requestAudioFocus(Context paramContext) {
    if (this.mAudioManager == null && paramContext != null)
      this.mAudioManager = (AudioManager)paramContext.getApplicationContext().getSystemService("audio"); 
    AudioManager audioManager = this.mAudioManager;
    if (audioManager != null) {
      AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = this.mAudioFocusChangeListener;
      if (onAudioFocusChangeListener != null)
        audioManager.requestAudioFocus(onAudioFocusChangeListener, 3, 2); 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\AudioManagerHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */