package com.tt.miniapp.audio;

import android.content.Context;
import android.util.SparseArray;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import java.util.Map;
import org.json.JSONObject;

public abstract class AudioManager {
  public static BgSendMsgStateListener bgSendMsgStateListener;
  
  protected static boolean isAppInBackground;
  
  public static boolean isAudioFocusChangePause;
  
  public static boolean isBgAudio;
  
  protected SparseArray<AudioStateModule> playingAudioId = new SparseArray();
  
  public static AudioManager getInst() {
    // Byte code:
    //   0: ldc com/tt/miniapp/audio/AudioManager
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/audio/AudioManager$Holder.INSTANCE : Lcom/tt/miniapp/audio/AudioManager;
    //   6: astore_0
    //   7: ldc com/tt/miniapp/audio/AudioManager
    //   9: monitorexit
    //   10: aload_0
    //   11: areturn
    //   12: astore_0
    //   13: ldc com/tt/miniapp/audio/AudioManager
    //   15: monitorexit
    //   16: aload_0
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   3	7	12	finally
  }
  
  public static void preload(Context paramContext) {
    getInst();
  }
  
  protected static void sendMsgState(int paramInt, String paramString) {
    sendMsgState(paramInt, paramString, null);
  }
  
  protected static void sendMsgState(int paramInt, String paramString, Map<String, Object> paramMap) {
    isAudioFocusChangePause = false;
    if (isBgAudio) {
      BgSendMsgStateListener bgSendMsgStateListener = bgSendMsgStateListener;
      if (bgSendMsgStateListener != null) {
        bgSendMsgStateListener.onSendMsgState(paramInt, paramString);
        return;
      } 
    } 
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("audioId", paramInt);
      jSONObject.put("state", paramString);
      if (paramMap != null)
        for (String str : paramMap.keySet())
          jSONObject.put(str, paramMap.get(str));  
      AppBrandLogger.d("tma_AudioManager", new Object[] { "sendMsgState ", paramString });
      AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onAudioStateChange", jSONObject.toString());
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_AudioManager", new Object[] { "", exception });
      return;
    } 
  }
  
  public abstract AudioState getAudioState(int paramInt, ApiErrorInfoEntity paramApiErrorInfoEntity);
  
  public abstract void onEnterBackground();
  
  public abstract void onEnterForeground();
  
  public abstract void pause(int paramInt, TaskListener paramTaskListener);
  
  public abstract void play(int paramInt, TaskListener paramTaskListener);
  
  public abstract void releaseAllPlayers();
  
  public abstract boolean releaseAudio(int paramInt, ApiErrorInfoEntity paramApiErrorInfoEntity);
  
  public abstract void seek(int paramInt1, int paramInt2, TaskListener paramTaskListener);
  
  public abstract void setAudioState(AudioStateModule paramAudioStateModule, TaskListener paramTaskListener);
  
  public abstract void stop(int paramInt, TaskListener paramTaskListener);
  
  public static class AudioState {
    public boolean autoplay;
    
    public int buffered;
    
    public long currentTime;
    
    public long duration;
    
    public boolean loop;
    
    public boolean obeyMuteSwitch;
    
    public boolean paused;
    
    public String src;
    
    public long startTime;
    
    public float volume;
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("AudioState{src='");
      stringBuilder.append(this.src);
      stringBuilder.append('\'');
      stringBuilder.append(", startTime=");
      stringBuilder.append(this.startTime);
      stringBuilder.append(", paused=");
      stringBuilder.append(this.paused);
      stringBuilder.append(", currentTime=");
      stringBuilder.append(this.currentTime);
      stringBuilder.append(", duration=");
      stringBuilder.append(this.duration);
      stringBuilder.append(", obeyMuteSwitch=");
      stringBuilder.append(this.obeyMuteSwitch);
      stringBuilder.append(", buffered=");
      stringBuilder.append(this.buffered);
      stringBuilder.append(", autoplay=");
      stringBuilder.append(this.autoplay);
      stringBuilder.append(", loop=");
      stringBuilder.append(this.loop);
      stringBuilder.append(", volume=");
      stringBuilder.append(this.volume);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
  
  public static class BaseMedia {
    public int audioId;
    
    public boolean autoPlay;
    
    public int buffer;
    
    public boolean isBgAudio;
    
    public boolean isPlayToSeek;
    
    public boolean isPreparing;
    
    public boolean loop;
    
    public android.media.AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;
    
    public boolean obeyMuteSwitch;
    
    public String src;
    
    public boolean startByUser;
    
    public volatile int state;
    
    public float volume;
  }
  
  public static interface BgSendMsgStateListener {
    void onSendMsgState(int param1Int, String param1String);
  }
  
  static interface Holder {
    public static final AudioManager INSTANCE = new TTVideoAudio();
  }
  
  public static interface TaskListener {
    void onFail(String param1String, Throwable param1Throwable);
    
    void onSuccess();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\AudioManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */