package com.tt.miniapp.audio;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.SparseArray;
import com.ss.ttvideoengine.DataSource;
import com.ss.ttvideoengine.SeekCompletionListener;
import com.ss.ttvideoengine.TTVideoEngine;
import com.ss.ttvideoengine.VideoEngineListener;
import com.ss.ttvideoengine.log.VideoEventListener;
import com.ss.ttvideoengine.log.VideoEventManager;
import com.ss.ttvideoengine.utils.Error;
import com.ss.ttvideoengine.utils.TTVideoEngineLog;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.video.player.VideoEventListenerImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.DebugUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TTVideoAudio extends AudioManager {
  public static SparseArray<TTMedia> mSparseArray = new SparseArray();
  
  public boolean isTelPhoneRequestPlay = false;
  
  private AudioManager mAudioManager;
  
  private List<Integer> mStashPlayingAudioList = new ArrayList<Integer>();
  
  public int telPhoneRequestPlayAudioId = -1;
  
  public TTVideoAudio() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application != null)
      this.mAudioManager = (AudioManager)application.getSystemService("audio"); 
  }
  
  public TTVideoAudio(boolean paramBoolean, AudioManager.BgSendMsgStateListener paramBgSendMsgStateListener) {
    this();
    isBgAudio = paramBoolean;
    bgSendMsgStateListener = paramBgSendMsgStateListener;
  }
  
  private static String buildFailReason(String paramString, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(", audioId == ");
    stringBuilder.append(paramInt);
    return stringBuilder.toString();
  }
  
  private static TTVideoEngine buildTTVideoEngine(AudioStateModule paramAudioStateModule, TTMedia paramTTMedia) {
    String str;
    int i = paramAudioStateModule.audioId;
    TTVideoEngine tTVideoEngine = new TTVideoEngine((Context)AppbrandContext.getInst().getApplicationContext(), 0);
    tTVideoEngine.setListener(new MiniAppAudioListener(i, paramTTMedia));
    tTVideoEngine.setIntOption(110, 1);
    VideoEventManager.instance.setListener((VideoEventListener)VideoEventListenerImpl.getInstance());
    tTVideoEngine.setTag("miniapp");
    if (TextUtils.isEmpty(paramTTMedia.miniAppId)) {
      str = getCurrentMiniAppId();
    } else {
      str = paramTTMedia.miniAppId;
    } 
    StringBuilder stringBuilder = new StringBuilder("miniapp_appid:");
    stringBuilder.append(str);
    tTVideoEngine.setSubTag(stringBuilder.toString());
    tTVideoEngine.setIntOption(415, 1);
    return tTVideoEngine;
  }
  
  private static String getCurrentMiniAppId() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    return (appInfoEntity != null) ? appInfoEntity.appId : "";
  }
  
  private boolean requestFocusAndPlay(TTMedia paramTTMedia) {
    int i = this.mAudioManager.requestAudioFocus(paramTTMedia.mAudioFocusChangeListener, 3, 2);
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle())
      HostDependManager.getInst().muteLiveWindowView((Activity)AppbrandContext.getInst().getCurrentActivity(), AppbrandApplicationImpl.getInst().getSchema()); 
    if ((i == 2 || i == 1) && paramTTMedia.videoEngine != null) {
      paramTTMedia.isSeekEnd = false;
      paramTTMedia.videoEngine.play();
      paramTTMedia.isPauseToSeek = false;
      paramTTMedia.isPlayToSeek = false;
      if (paramTTMedia.state != 0 && paramTTMedia.state != 6) {
        sendMsgState(paramTTMedia.audioId, "play");
        paramTTMedia.state = 2;
      } 
      this.isTelPhoneRequestPlay = false;
      return true;
    } 
    return false;
  }
  
  private static TTVideoEngine setTTVideoEnginePath(String paramString, TTVideoEngine paramTTVideoEngine) {
    if (paramString.startsWith("file") || paramString.startsWith("/") || paramString.startsWith(".") || paramString.startsWith("～")) {
      paramTTVideoEngine.setLocalURL(paramString);
      return paramTTVideoEngine;
    } 
    paramTTVideoEngine.setDirectURL(paramString);
    return paramTTVideoEngine;
  }
  
  private static void setVolume(TTMedia paramTTMedia) {
    if (paramTTMedia != null) {
      if (paramTTMedia.videoEngine == null)
        return; 
      TTVideoEngine tTVideoEngine = paramTTMedia.videoEngine;
      AudioManager audioManager = (AudioManager)AppbrandContext.getInst().getApplicationContext().getSystemService("audio");
      if (audioManager != null && !paramTTMedia.isBgAudio) {
        AppBrandLogger.d("tma_TTVideoAudio", new Object[] { "getMode ", Integer.valueOf(audioManager.getMode()) });
        if (audioManager.getMode() != 0)
          audioManager.setMode(0); 
        float f2 = paramTTMedia.volume;
        float f1 = 1.0F;
        if (f2 <= 1.0F)
          if (paramTTMedia.volume < 0.0F) {
            f1 = 0.0F;
          } else {
            f1 = paramTTMedia.volume;
          }  
        if (paramTTMedia.obeyMuteSwitch) {
          if (audioManager.getRingerMode() != 2) {
            tTVideoEngine.setVolume(0.0F, 0.0F);
            return;
          } 
          tTVideoEngine.setVolume(f1, f1);
          return;
        } 
        tTVideoEngine.setVolume(f1, f1);
      } 
    } 
  }
  
  public boolean containAudioId(int paramInt) {
    return ((TTMedia)mSparseArray.get(paramInt) != null);
  }
  
  public int createAudio(AudioStateModule paramAudioStateModule, String paramString, ApiErrorInfoEntity paramApiErrorInfoEntity) {
    TTVideoEngine.setHTTPDNSFirst(false);
    final TTMedia media = new TTMedia();
    tTMedia.state = 0;
    int i = paramAudioStateModule.audioId;
    tTMedia.audioId = i;
    tTMedia.miniAppId = paramString;
    tTMedia.videoEngine = buildTTVideoEngine(paramAudioStateModule, tTMedia);
    if (DebugUtil.debug())
      TTVideoEngineLog.turnOn(1, 1); 
    TTVideoEngine tTVideoEngine = tTMedia.videoEngine;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(i);
    tTVideoEngine.setVideoID(stringBuilder.toString());
    tTMedia.videoEngine.setDataSource(new DataSource() {
          public String apiForFetcher(Map<String, String> param1Map, int param1Int) {
            return null;
          }
        });
    tTMedia.mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int param1Int) {
          if (param1Int != -3 && param1Int != -2 && param1Int != -1) {
            if (param1Int != 1)
              return; 
            if (TTVideoAudio.this.isTelPhoneRequestPlay) {
              TTVideoAudio tTVideoAudio = TTVideoAudio.this;
              tTVideoAudio.play(tTVideoAudio.telPhoneRequestPlayAudioId, (AudioManager.TaskListener)null);
              TTVideoAudio.this.isTelPhoneRequestPlay = false;
            } 
            return;
          } 
          if (media.videoEngine != null)
            TTVideoAudio.this.pause(media.audioId, (AudioManager.TaskListener)null); 
          AudioManager.isAudioFocusChangePause = true;
        }
      };
    mSparseArray.put(i, tTMedia);
    return i;
  }
  
  public AudioManager.AudioState getAudioState(int paramInt, ApiErrorInfoEntity paramApiErrorInfoEntity) {
    ApiErrorInfoEntity apiErrorInfoEntity = paramApiErrorInfoEntity;
    if (paramApiErrorInfoEntity == null)
      apiErrorInfoEntity = new ApiErrorInfoEntity(); 
    TTMedia tTMedia = (TTMedia)mSparseArray.get(paramInt);
    if (tTMedia == null) {
      apiErrorInfoEntity.append(buildFailReason("audio init fail", paramInt));
      return null;
    } 
    TTVideoEngine tTVideoEngine = tTMedia.videoEngine;
    if (tTVideoEngine == null) {
      apiErrorInfoEntity.append(buildFailReason("audio create fail", paramInt));
      return null;
    } 
    AudioManager.AudioState audioState = new AudioManager.AudioState();
    audioState.src = tTMedia.src;
    try {
      if (tTMedia.isPreparing) {
        audioState.duration = 0L;
      } else {
        audioState.duration = tTVideoEngine.getDuration();
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("tma_TTVideoAudio", new Object[] { "mediaPlayer is not Playing", exception });
      audioState.duration = 0L;
    } 
    AppBrandLogger.d("tma_TTVideoAudio", new Object[] { "audioState.src ", audioState.src, " ", Long.valueOf(audioState.duration) });
    try {
      if (tTMedia.isPreparing) {
        audioState.currentTime = 0L;
      } else if (tTMedia.isSeekEnd) {
        audioState.currentTime = tTVideoEngine.getDuration();
      } else {
        audioState.currentTime = tTVideoEngine.getCurrentPlaybackTime();
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("tma_TTVideoAudio", new Object[] { "mediaPlayer is not Playing", exception });
      audioState.currentTime = 0L;
    } 
    try {
      if (tTMedia.state == 8) {
        if (tTMedia.isPlayToSeek) {
          audioState.paused = false;
        } else {
          audioState.paused = true;
        } 
      } else {
        boolean bool;
        if (tTMedia.state != 2) {
          bool = true;
        } else {
          bool = false;
        } 
        audioState.paused = bool;
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("tma_TTVideoAudio", new Object[] { "mediaPlayer is not Playing", exception });
      audioState.paused = true;
    } 
    audioState.buffered = tTMedia.buffer;
    audioState.obeyMuteSwitch = tTMedia.obeyMuteSwitch;
    audioState.autoplay = tTMedia.autoPlay;
    audioState.loop = tTMedia.loop;
    audioState.volume = tTMedia.volume;
    return audioState;
  }
  
  public boolean isMediaValid(int paramInt) {
    TTMedia tTMedia = (TTMedia)mSparseArray.get(paramInt);
    return (tTMedia != null && tTMedia.videoEngine != null);
  }
  
  public boolean isPlaying(int paramInt) {
    TTMedia tTMedia = (TTMedia)mSparseArray.get(paramInt);
    return (tTMedia == null) ? false : ((tTMedia.state == 2));
  }
  
  public void onEnterBackground() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc_w 'tma_TTVideoAudio'
    //   5: iconst_1
    //   6: anewarray java/lang/Object
    //   9: dup
    //   10: iconst_0
    //   11: ldc_w 'onEnterBackground'
    //   14: aastore
    //   15: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   18: iconst_1
    //   19: putstatic com/tt/miniapp/audio/TTVideoAudio.isAppInBackground : Z
    //   22: getstatic com/tt/miniapp/audio/TTVideoAudio.mSparseArray : Landroid/util/SparseArray;
    //   25: invokevirtual size : ()I
    //   28: istore_2
    //   29: aload_0
    //   30: getfield mStashPlayingAudioList : Ljava/util/List;
    //   33: invokeinterface clear : ()V
    //   38: iconst_0
    //   39: istore_1
    //   40: iload_1
    //   41: iload_2
    //   42: if_icmpge -> 154
    //   45: getstatic com/tt/miniapp/audio/TTVideoAudio.mSparseArray : Landroid/util/SparseArray;
    //   48: iload_1
    //   49: invokevirtual keyAt : (I)I
    //   52: istore_3
    //   53: aload_0
    //   54: iload_3
    //   55: aconst_null
    //   56: invokevirtual getAudioState : (ILcom/tt/miniapphost/entity/ApiErrorInfoEntity;)Lcom/tt/miniapp/audio/AudioManager$AudioState;
    //   59: astore #4
    //   61: getstatic com/tt/miniapp/audio/TTVideoAudio.mSparseArray : Landroid/util/SparseArray;
    //   64: iload_3
    //   65: invokevirtual get : (I)Ljava/lang/Object;
    //   68: checkcast com/tt/miniapp/audio/TTVideoAudio$TTMedia
    //   71: astore #5
    //   73: new java/lang/StringBuilder
    //   76: dup
    //   77: ldc_w 'onEnterBackground '
    //   80: invokespecial <init> : (Ljava/lang/String;)V
    //   83: astore #6
    //   85: aload #6
    //   87: aload #5
    //   89: getfield state : I
    //   92: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: ldc_w 'tma_TTVideoAudio'
    //   99: iconst_1
    //   100: anewarray java/lang/Object
    //   103: dup
    //   104: iconst_0
    //   105: aload #6
    //   107: invokevirtual toString : ()Ljava/lang/String;
    //   110: aastore
    //   111: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   114: aload #4
    //   116: ifnull -> 147
    //   119: aload #4
    //   121: getfield paused : Z
    //   124: ifne -> 147
    //   127: aload_0
    //   128: getfield mStashPlayingAudioList : Ljava/util/List;
    //   131: iload_3
    //   132: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   135: invokeinterface add : (Ljava/lang/Object;)Z
    //   140: pop
    //   141: aload_0
    //   142: iload_3
    //   143: aconst_null
    //   144: invokevirtual pause : (ILcom/tt/miniapp/audio/AudioManager$TaskListener;)V
    //   147: iload_1
    //   148: iconst_1
    //   149: iadd
    //   150: istore_1
    //   151: goto -> 40
    //   154: aload_0
    //   155: monitorexit
    //   156: return
    //   157: astore #4
    //   159: aload_0
    //   160: monitorexit
    //   161: goto -> 167
    //   164: aload #4
    //   166: athrow
    //   167: goto -> 164
    // Exception table:
    //   from	to	target	type
    //   2	38	157	finally
    //   45	114	157	finally
    //   119	147	157	finally
  }
  
  public void onEnterForeground() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc_w 'tma_TTVideoAudio'
    //   5: iconst_1
    //   6: anewarray java/lang/Object
    //   9: dup
    //   10: iconst_0
    //   11: ldc_w 'onEnterForeground'
    //   14: aastore
    //   15: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   18: iconst_0
    //   19: putstatic com/tt/miniapp/audio/TTVideoAudio.isAppInBackground : Z
    //   22: aload_0
    //   23: getfield mStashPlayingAudioList : Ljava/util/List;
    //   26: invokeinterface iterator : ()Ljava/util/Iterator;
    //   31: astore_1
    //   32: aload_1
    //   33: invokeinterface hasNext : ()Z
    //   38: ifeq -> 61
    //   41: aload_0
    //   42: aload_1
    //   43: invokeinterface next : ()Ljava/lang/Object;
    //   48: checkcast java/lang/Integer
    //   51: invokevirtual intValue : ()I
    //   54: aconst_null
    //   55: invokevirtual play : (ILcom/tt/miniapp/audio/AudioManager$TaskListener;)V
    //   58: goto -> 32
    //   61: aload_0
    //   62: getfield mStashPlayingAudioList : Ljava/util/List;
    //   65: invokeinterface clear : ()V
    //   70: aload_0
    //   71: monitorexit
    //   72: return
    //   73: astore_1
    //   74: aload_0
    //   75: monitorexit
    //   76: goto -> 81
    //   79: aload_1
    //   80: athrow
    //   81: goto -> 79
    // Exception table:
    //   from	to	target	type
    //   2	32	73	finally
    //   32	58	73	finally
    //   61	70	73	finally
  }
  
  public void pause(int paramInt, AudioManager.TaskListener paramTaskListener) {
    TTMedia tTMedia = (TTMedia)mSparseArray.get(paramInt);
    if (tTMedia == null) {
      if (paramTaskListener != null)
        paramTaskListener.onFail(buildFailReason("audio init fail", paramInt), null); 
      return;
    } 
    if (tTMedia.videoEngine == null) {
      if (paramTaskListener != null)
        paramTaskListener.onFail(buildFailReason("audio create fail", paramInt), null); 
      return;
    } 
    tTMedia.isSeekEnd = false;
    tTMedia.isPlayToSeek = false;
    if (tTMedia.state == 7) {
      if (paramTaskListener != null)
        paramTaskListener.onFail(buildFailReason("audio state fail", paramInt), null); 
      return;
    } 
    if (tTMedia.state == 2)
      try {
        sendMsgState(paramInt, "pause");
        tTMedia.videoEngine.pause();
        tTMedia.state = 4;
      } catch (Exception exception) {
        AppBrandLogger.e("tma_TTVideoAudio", new Object[] { "pause", exception });
        if (paramTaskListener != null)
          paramTaskListener.onFail("audio pause fail", exception); 
        return;
      }  
    if (paramTaskListener != null)
      paramTaskListener.onSuccess(); 
  }
  
  public void play(int paramInt, AudioManager.TaskListener paramTaskListener) {
    TTMedia tTMedia = (TTMedia)mSparseArray.get(paramInt);
    if (tTMedia == null) {
      if (paramTaskListener != null)
        paramTaskListener.onFail(buildFailReason("audio init fail", paramInt), null); 
      return;
    } 
    if (tTMedia.videoEngine == null) {
      if (paramTaskListener != null)
        paramTaskListener.onFail(buildFailReason("audio create fail", paramInt), null); 
      return;
    } 
    if (isAppInBackground) {
      if (paramTaskListener != null)
        paramTaskListener.onFail(buildFailReason("app in background", paramInt), null); 
      return;
    } 
    if (requestFocusAndPlay(tTMedia)) {
      if (tTMedia.state == 0 || tTMedia.state == 6)
        tTMedia.startByUser = true; 
      if (paramTaskListener != null) {
        paramTaskListener.onSuccess();
        return;
      } 
    } else if (paramTaskListener != null) {
      paramTaskListener.onFail(buildFailReason("request focus and play", paramInt), null);
    } 
  }
  
  public void releaseAllPlayers() {
    for (int i = 0; i < mSparseArray.size(); i++) {
      TTMedia tTMedia = (TTMedia)mSparseArray.valueAt(i);
      if (tTMedia != null && tTMedia.videoEngine != null) {
        tTMedia.videoEngine.release();
        tTMedia.videoEngine = null;
        sendMsgState(mSparseArray.keyAt(i), "ended");
      } 
    } 
    mSparseArray.clear();
    this.playingAudioId.clear();
  }
  
  public boolean releaseAudio(int paramInt, ApiErrorInfoEntity paramApiErrorInfoEntity) {
    return releaseAudio(paramInt, paramApiErrorInfoEntity, false);
  }
  
  public boolean releaseAudio(int paramInt, ApiErrorInfoEntity paramApiErrorInfoEntity, boolean paramBoolean) {
    ApiErrorInfoEntity apiErrorInfoEntity = paramApiErrorInfoEntity;
    if (paramApiErrorInfoEntity == null)
      apiErrorInfoEntity = new ApiErrorInfoEntity(); 
    AppBrandLogger.d("tma_TTVideoAudio", new Object[] { "releaseAudio ", Integer.valueOf(paramInt) });
    TTMedia tTMedia = (TTMedia)mSparseArray.get(paramInt);
    if (tTMedia == null) {
      apiErrorInfoEntity.append(buildFailReason("audio init fail", paramInt));
      return false;
    } 
    TTVideoEngine tTVideoEngine = tTMedia.videoEngine;
    if (tTVideoEngine == null) {
      apiErrorInfoEntity.append(buildFailReason("audio create fail", paramInt));
      return false;
    } 
    if (!paramBoolean)
      sendMsgState(paramInt, "ended"); 
    try {
      tTVideoEngine.release();
      this.mAudioManager.abandonAudioFocus(tTMedia.mAudioFocusChangeListener);
      mSparseArray.delete(paramInt);
      this.playingAudioId.remove(paramInt);
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_TTVideoAudio", new Object[] { "releaseAudio", exception });
      StringBuilder stringBuilder = new StringBuilder("release fail, audioId == ");
      stringBuilder.append(paramInt);
      apiErrorInfoEntity.append(stringBuilder.toString());
      apiErrorInfoEntity.setThrowable(exception);
      return false;
    } 
  }
  
  public void releaseBgAudio(int paramInt) {
    for (int i = 0; i < mSparseArray.size(); i++) {
      TTMedia tTMedia = (TTMedia)mSparseArray.valueAt(i);
      if (tTMedia != null && tTMedia.audioId == paramInt && tTMedia.videoEngine != null) {
        if (tTMedia.state != 0)
          tTMedia.videoEngine.stop(); 
        releaseAudio(tTMedia.audioId, (ApiErrorInfoEntity)null);
      } 
    } 
  }
  
  public void seek(final int audioId, final int currentTime, final AudioManager.TaskListener taskListener) {
    final TTMedia media = (TTMedia)mSparseArray.get(audioId);
    if (tTMedia == null) {
      if (taskListener != null)
        taskListener.onFail(buildFailReason("audio init fail", audioId), null); 
      return;
    } 
    if (tTMedia.videoEngine == null) {
      if (taskListener != null)
        taskListener.onFail(buildFailReason("audio create fail", audioId), null); 
      return;
    } 
    tTMedia.isSeekEnd = false;
    if (tTMedia.state == 2) {
      tTMedia.isPlayToSeek = true;
    } else {
      tTMedia.isPlayToSeek = false;
    } 
    if ((!tTMedia.isPauseToSeek && tTMedia.state == 4) || (tTMedia.isPauseToSeek && tTMedia.state == 9) || (tTMedia.isPauseToSeek && tTMedia.state == 5)) {
      tTMedia.isPauseToSeek = true;
    } else {
      tTMedia.isPauseToSeek = false;
    } 
    if (currentTime > tTMedia.videoEngine.getDuration() - 1000)
      tTMedia.isSeekEnd = true; 
    sendMsgState(audioId, "seeking");
    tTMedia.videoEngine.seekTo(currentTime, new SeekCompletionListener() {
          public void onCompletion(boolean param1Boolean) {
            if (param1Boolean) {
              AudioManager.sendMsgState(audioId, "seeked");
              AudioManager.TaskListener taskListener = taskListener;
              if (taskListener != null)
                taskListener.onSuccess(); 
            } else {
              media.videoEngine.setStartTime(currentTime);
              if (media.isPlayToSeek) {
                media.videoEngine.play();
              } else {
                media.videoEngine.pause();
              } 
              AudioManager.sendMsgState(audioId, "seeked");
              AudioManager.TaskListener taskListener = taskListener;
              if (taskListener != null)
                taskListener.onSuccess(); 
            } 
            if (media.isPlayToSeek) {
              media.state = 2;
              return;
            } 
            media.state = 9;
          }
        });
  }
  
  public void setAudioState(AudioStateModule paramAudioStateModule, AudioManager.TaskListener paramTaskListener) {
    // Byte code:
    //   0: aload_1
    //   1: getfield audioId : I
    //   4: istore #5
    //   6: getstatic com/tt/miniapp/audio/TTVideoAudio.mSparseArray : Landroid/util/SparseArray;
    //   9: iload #5
    //   11: invokevirtual get : (I)Ljava/lang/Object;
    //   14: checkcast com/tt/miniapp/audio/TTVideoAudio$TTMedia
    //   17: astore #7
    //   19: aload #7
    //   21: astore #6
    //   23: aload #7
    //   25: ifnonnull -> 58
    //   28: aload_0
    //   29: aload_1
    //   30: aload_1
    //   31: getfield miniAppId : Ljava/lang/String;
    //   34: new com/tt/miniapphost/entity/ApiErrorInfoEntity
    //   37: dup
    //   38: invokespecial <init> : ()V
    //   41: invokevirtual createAudio : (Lcom/tt/miniapp/audio/AudioStateModule;Ljava/lang/String;Lcom/tt/miniapphost/entity/ApiErrorInfoEntity;)I
    //   44: pop
    //   45: getstatic com/tt/miniapp/audio/TTVideoAudio.mSparseArray : Landroid/util/SparseArray;
    //   48: iload #5
    //   50: invokevirtual get : (I)Ljava/lang/Object;
    //   53: checkcast com/tt/miniapp/audio/TTVideoAudio$TTMedia
    //   56: astore #6
    //   58: aload #6
    //   60: ifnonnull -> 99
    //   63: ldc_w 'tma_TTVideoAudio'
    //   66: iconst_1
    //   67: anewarray java/lang/Object
    //   70: dup
    //   71: iconst_0
    //   72: ldc_w 'setAudioState media == null'
    //   75: aastore
    //   76: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   79: aload_2
    //   80: ifnull -> 98
    //   83: aload_2
    //   84: ldc_w 'audio init fail'
    //   87: iload #5
    //   89: invokestatic buildFailReason : (Ljava/lang/String;I)Ljava/lang/String;
    //   92: aconst_null
    //   93: invokeinterface onFail : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   98: return
    //   99: aload #6
    //   101: getfield videoEngine : Lcom/ss/ttvideoengine/TTVideoEngine;
    //   104: astore #8
    //   106: aload #8
    //   108: ifnonnull -> 147
    //   111: ldc_w 'tma_TTVideoAudio'
    //   114: iconst_1
    //   115: anewarray java/lang/Object
    //   118: dup
    //   119: iconst_0
    //   120: ldc_w 'setAudioState mediaPlayer == null'
    //   123: aastore
    //   124: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   127: aload_2
    //   128: ifnull -> 146
    //   131: aload_2
    //   132: ldc_w 'audio create fail'
    //   135: iload #5
    //   137: invokestatic buildFailReason : (Ljava/lang/String;I)Ljava/lang/String;
    //   140: aconst_null
    //   141: invokeinterface onFail : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   146: return
    //   147: aload_1
    //   148: getfield src : Ljava/lang/String;
    //   151: astore #7
    //   153: aload #7
    //   155: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   158: ifeq -> 197
    //   161: ldc_w 'tma_TTVideoAudio'
    //   164: iconst_1
    //   165: anewarray java/lang/Object
    //   168: dup
    //   169: iconst_0
    //   170: ldc_w 'setAudioState src is empty'
    //   173: aastore
    //   174: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   177: aload_2
    //   178: ifnull -> 196
    //   181: aload_2
    //   182: ldc_w 'audio set fail, src is empty'
    //   185: iload #5
    //   187: invokestatic buildFailReason : (Ljava/lang/String;I)Ljava/lang/String;
    //   190: aconst_null
    //   191: invokeinterface onFail : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   196: return
    //   197: aload #7
    //   199: ldc_w 'http'
    //   202: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   205: ifeq -> 320
    //   208: invokestatic isMiniappProcess : ()Z
    //   211: ifeq -> 320
    //   214: ldc_w 'request'
    //   217: aload #7
    //   219: invokestatic isSafeDomain : (Ljava/lang/String;Ljava/lang/String;)Z
    //   222: ifne -> 320
    //   225: ldc_w 'tma_TTVideoAudio'
    //   228: iconst_1
    //   229: anewarray java/lang/Object
    //   232: dup
    //   233: iconst_0
    //   234: ldc_w 'setAudioState src is not valid domain'
    //   237: aastore
    //   238: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   241: new java/util/HashMap
    //   244: dup
    //   245: invokespecial <init> : ()V
    //   248: astore_1
    //   249: aload_1
    //   250: ldc_w 'errCode'
    //   253: iconst_0
    //   254: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   257: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   260: pop
    //   261: aload_1
    //   262: ldc_w 'errMsg'
    //   265: ldc_w 'not in valid domains'
    //   268: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   271: pop
    //   272: iload #5
    //   274: ldc_w 'error'
    //   277: aload_1
    //   278: invokestatic sendMsgState : (ILjava/lang/String;Ljava/util/Map;)V
    //   281: aload_2
    //   282: ifnull -> 319
    //   285: new java/lang/StringBuilder
    //   288: dup
    //   289: ldc_w 'audio set fail, src is not valid domain, src == '
    //   292: invokespecial <init> : (Ljava/lang/String;)V
    //   295: astore_1
    //   296: aload_1
    //   297: aload #7
    //   299: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   302: pop
    //   303: aload_2
    //   304: aload_1
    //   305: invokevirtual toString : ()Ljava/lang/String;
    //   308: iload #5
    //   310: invokestatic buildFailReason : (Ljava/lang/String;I)Ljava/lang/String;
    //   313: aconst_null
    //   314: invokeinterface onFail : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   319: return
    //   320: aload #6
    //   322: getfield src : Ljava/lang/String;
    //   325: aload #7
    //   327: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   330: ifeq -> 357
    //   333: ldc_w 'tma_TTVideoAudio'
    //   336: iconst_2
    //   337: anewarray java/lang/Object
    //   340: dup
    //   341: iconst_0
    //   342: ldc_w 'setAudioState TextUtils.equals(media.src, playUrl) '
    //   345: aastore
    //   346: dup
    //   347: iconst_1
    //   348: aload #7
    //   350: aastore
    //   351: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   354: goto -> 449
    //   357: ldc_w 'tma_TTVideoAudio'
    //   360: iconst_2
    //   361: anewarray java/lang/Object
    //   364: dup
    //   365: iconst_0
    //   366: ldc_w 'setAudioState src is '
    //   369: aastore
    //   370: dup
    //   371: iconst_1
    //   372: aload #7
    //   374: aastore
    //   375: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   378: aload_1
    //   379: getfield encryptToken : Ljava/lang/String;
    //   382: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   385: ifne -> 397
    //   388: aload #8
    //   390: aload_1
    //   391: getfield encryptToken : Ljava/lang/String;
    //   394: invokevirtual setDecryptionKey : (Ljava/lang/String;)V
    //   397: aload #7
    //   399: aload #8
    //   401: invokestatic setTTVideoEnginePath : (Ljava/lang/String;Lcom/ss/ttvideoengine/TTVideoEngine;)Lcom/ss/ttvideoengine/TTVideoEngine;
    //   404: pop
    //   405: aload #6
    //   407: iconst_1
    //   408: putfield isPreparing : Z
    //   411: aload #6
    //   413: bipush #6
    //   415: putfield state : I
    //   418: aload_1
    //   419: getfield autoplay : Z
    //   422: ifeq -> 449
    //   425: aload_0
    //   426: aload #6
    //   428: invokespecial requestFocusAndPlay : (Lcom/tt/miniapp/audio/TTVideoAudio$TTMedia;)Z
    //   431: ifne -> 449
    //   434: aload_2
    //   435: ifnull -> 448
    //   438: aload_2
    //   439: ldc_w 'audio set fail, auto play fail'
    //   442: aconst_null
    //   443: invokeinterface onFail : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   448: return
    //   449: aload #6
    //   451: aload #7
    //   453: putfield src : Ljava/lang/String;
    //   456: aload #6
    //   458: aload_1
    //   459: getfield autoplay : Z
    //   462: putfield autoPlay : Z
    //   465: aload #6
    //   467: aload_1
    //   468: getfield loop : Z
    //   471: putfield loop : Z
    //   474: aload #6
    //   476: aload_1
    //   477: getfield obeyMuteSwitch : Z
    //   480: putfield obeyMuteSwitch : Z
    //   483: aload_1
    //   484: getfield volume : F
    //   487: fstore #4
    //   489: fconst_0
    //   490: fstore_3
    //   491: fload #4
    //   493: fconst_1
    //   494: fcmpl
    //   495: ifle -> 503
    //   498: fconst_1
    //   499: fstore_3
    //   500: goto -> 520
    //   503: aload_1
    //   504: getfield volume : F
    //   507: fconst_0
    //   508: fcmpg
    //   509: ifge -> 515
    //   512: goto -> 520
    //   515: aload_1
    //   516: getfield volume : F
    //   519: fstore_3
    //   520: aload #6
    //   522: fload_3
    //   523: putfield volume : F
    //   526: aload #6
    //   528: aload_1
    //   529: getfield isBgAudio : Z
    //   532: putfield isBgAudio : Z
    //   535: aload #6
    //   537: invokestatic setVolume : (Lcom/tt/miniapp/audio/TTVideoAudio$TTMedia;)V
    //   540: aload_2
    //   541: ifnull -> 550
    //   544: aload_2
    //   545: invokeinterface onSuccess : ()V
    //   550: return
    //   551: astore_1
    //   552: aload_2
    //   553: ifnull -> 588
    //   556: new java/lang/StringBuilder
    //   559: dup
    //   560: ldc_w 'audio set fail, src is error, src == '
    //   563: invokespecial <init> : (Ljava/lang/String;)V
    //   566: astore #6
    //   568: aload #6
    //   570: aload #7
    //   572: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   575: pop
    //   576: aload_2
    //   577: aload #6
    //   579: invokevirtual toString : ()Ljava/lang/String;
    //   582: aload_1
    //   583: invokeinterface onFail : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   588: return
    // Exception table:
    //   from	to	target	type
    //   378	397	551	java/lang/Exception
    //   397	405	551	java/lang/Exception
  }
  
  public void stop(int paramInt, AudioManager.TaskListener paramTaskListener) {
    stop(paramInt, paramTaskListener, false);
  }
  
  public void stop(int paramInt, AudioManager.TaskListener paramTaskListener, boolean paramBoolean) {
    TTMedia tTMedia = (TTMedia)mSparseArray.get(paramInt);
    if (tTMedia == null || tTMedia.videoEngine == null) {
      if (paramTaskListener != null)
        paramTaskListener.onSuccess(); 
      return;
    } 
    tTMedia.isSeekEnd = false;
    tTMedia.isPauseToSeek = false;
    tTMedia.isPlayToSeek = false;
    tTMedia.videoEngine.stop();
    tTMedia.state = 5;
    if (!paramBoolean)
      sendMsgState(paramInt, "stop"); 
    if (paramTaskListener != null)
      paramTaskListener.onSuccess(); 
  }
  
  static class MiniAppAudioListener implements VideoEngineListener {
    private int mAudioId;
    
    private TTVideoAudio.TTMedia mMedia;
    
    public MiniAppAudioListener(int param1Int, TTVideoAudio.TTMedia param1TTMedia) {
      this.mAudioId = param1Int;
      this.mMedia = param1TTMedia;
    }
    
    public void onBufferingUpdate(TTVideoEngine param1TTVideoEngine, int param1Int) {
      this.mMedia.buffer = param1Int;
    }
    
    public void onCompletion(TTVideoEngine param1TTVideoEngine) {
      TTVideoAudio.TTMedia tTMedia = this.mMedia;
      if (tTMedia == null)
        return; 
      if (tTMedia.loop && !this.mMedia.isPauseToSeek) {
        tTMedia = this.mMedia;
        tTMedia.isSeekEnd = false;
        tTMedia.videoEngine.play();
        AudioManager.sendMsgState(this.mMedia.audioId, "play");
        return;
      } 
      if (this.mMedia.isSeekEnd) {
        AudioManager.sendMsgState(this.mAudioId, "pause");
        return;
      } 
      tTMedia = this.mMedia;
      tTMedia.isPauseToSeek = false;
      tTMedia.state = 3;
      AudioManager.sendMsgState(this.mAudioId, "ended");
    }
    
    public void onError(Error param1Error) {
      int i = param1Error.code;
      String str = param1Error.description;
      TTVideoAudio.TTMedia tTMedia = this.mMedia;
      if (tTMedia != null)
        tTMedia.state = 7; 
      AppBrandLogger.e("tma_TTVideoAudio", new Object[] { "audioId ", Integer.valueOf(this.mAudioId), " onError what ", Integer.valueOf(i), " extra ", str });
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("errCode", Integer.valueOf(i));
      hashMap.put("errMsg", str);
      AudioManager.sendMsgState(this.mAudioId, "error", (Map)hashMap);
    }
    
    public void onLoadStateChanged(TTVideoEngine param1TTVideoEngine, int param1Int) {}
    
    public void onPlaybackStateChanged(TTVideoEngine param1TTVideoEngine, int param1Int) {
      if (param1Int != 0) {
        if (param1Int != 1) {
          if (param1Int != 2) {
            if (param1Int != 3)
              return; 
            this.mMedia.state = 7;
            return;
          } 
          this.mMedia.state = 4;
          return;
        } 
        this.mMedia.state = 2;
        return;
      } 
      this.mMedia.state = 5;
    }
    
    public void onPrepare(TTVideoEngine param1TTVideoEngine) {
      TTVideoAudio.TTMedia tTMedia = this.mMedia;
      tTMedia.isPreparing = true;
      tTMedia.state = 6;
    }
    
    public void onPrepared(TTVideoEngine param1TTVideoEngine) {
      AppBrandLogger.d("tma_TTVideoAudio", new Object[] { "onPrepared audioId ", Integer.valueOf(this.mAudioId) });
      TTVideoAudio.TTMedia tTMedia = this.mMedia;
      if (tTMedia == null)
        return; 
      if (tTMedia.src != null && !this.mMedia.src.startsWith("http"))
        this.mMedia.buffer = 100; 
      tTMedia = this.mMedia;
      tTMedia.isPreparing = false;
      if (tTMedia.videoEngine != null) {
        this.mMedia.state = 1;
        AudioManager.sendMsgState(this.mAudioId, "canplay");
      } 
      if (AudioManager.isAppInBackground)
        return; 
      if (this.mMedia.autoPlay || this.mMedia.startByUser) {
        tTMedia = this.mMedia;
        tTMedia.startByUser = false;
        tTMedia.state = 2;
        AudioManager.sendMsgState(tTMedia.audioId, "play");
      } 
    }
    
    public void onRenderStart(TTVideoEngine param1TTVideoEngine) {}
    
    public void onStreamChanged(TTVideoEngine param1TTVideoEngine, int param1Int) {}
    
    public void onVideoSizeChanged(TTVideoEngine param1TTVideoEngine, int param1Int1, int param1Int2) {}
    
    public void onVideoStatusException(int param1Int) {}
  }
  
  public static class TTMedia extends AudioManager.BaseMedia {
    public boolean isPauseToSeek;
    
    public boolean isSeekEnd;
    
    public String miniAppId;
    
    public TTVideoEngine videoEngine;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\TTVideoAudio.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */