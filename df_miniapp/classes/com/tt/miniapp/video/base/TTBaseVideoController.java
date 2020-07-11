package com.tt.miniapp.video.base;

import android.app.Application;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Surface;
import com.ss.ttvideoengine.DataSource;
import com.ss.ttvideoengine.Resolution;
import com.ss.ttvideoengine.SeekCompletionListener;
import com.ss.ttvideoengine.TTVideoEngine;
import com.ss.ttvideoengine.VideoEngineListener;
import com.ss.ttvideoengine.log.VideoEventListener;
import com.ss.ttvideoengine.log.VideoEventManager;
import com.ss.ttvideoengine.model.VideoModel;
import com.ss.ttvideoengine.model.VideoRef;
import com.ss.ttvideoengine.utils.Error;
import com.ss.ttvideoengine.utils.TTVideoEngineLog;
import com.tt.miniapp.util.WeakHandler;
import com.tt.miniapp.video.adapter.BDataSource;
import com.tt.miniapp.video.adapter.BVideoEngineListener;
import com.tt.miniapp.video.common.VideoDataContext;
import com.tt.miniapp.video.common.VideoLog;
import com.tt.miniapp.video.common.VideoUrlDepend;
import com.tt.miniapp.video.player.TTPlayerInitializer;
import com.tt.miniapp.video.player.VideoEventListenerImpl;
import com.tt.miniapp.video.plugin.base.BaseVideoPluginHost;
import com.tt.miniapp.video.plugin.base.IVideoPluginEvent;
import com.tt.miniapp.video.plugin.event.VideoCommonEvent;
import com.tt.miniapp.video.view.CoreVideoView;
import com.tt.miniapp.video.view.IVideoViewCallback;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.DebugUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.json.JSONObject;

public class TTBaseVideoController extends BaseVideoPluginHost implements WeakHandler.IHandler, BVideoEngineListener, ITTVideoController, IVideoViewCallback {
  private boolean hasRenderStarted = false;
  
  protected int mActualViewHeight;
  
  protected int mActualViewWidth;
  
  private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
      public void onAudioFocusChange(int param1Int) {
        if ((param1Int == -2 || param1Int == -1) && TTBaseVideoController.this.isVideoPlaying()) {
          VideoLog.getInstance().writeVideoLog("onAudioFocusChange", true);
          TTBaseVideoController tTBaseVideoController = TTBaseVideoController.this;
          tTBaseVideoController.mPausedByAudioFocusLoss = true;
          if (tTBaseVideoController.mVideoEngine != null)
            TTBaseVideoController.this.pauseVideo(); 
        } 
      }
    };
  
  private AudioManager mAudioManager;
  
  private int mBlockedCount = 0;
  
  private boolean mCanCountBlocked = true;
  
  protected int mCurrent;
  
  protected int mDuration;
  
  private boolean mExecutingActions;
  
  protected WeakHandler mHandler = new WeakHandler(this);
  
  private boolean mHasSetDisplay;
  
  private boolean mIsComplete;
  
  private boolean mIsLooping;
  
  private boolean mIsMute;
  
  private boolean mIsSurfaceValid;
  
  private int mLoadState = 0;
  
  private boolean mNeedAutoPause;
  
  public boolean mPausedByAudioFocusLoss;
  
  private ArrayList<Runnable> mPendingActions;
  
  protected int mPendingSeekToPositionBeforeRender = -1;
  
  protected int mPendingSeekToPositionForRetry = -1;
  
  protected ITTVideoController.PlayerEntity mPlayerEntity;
  
  private TTPlayerInitializer mPlayerInitializer;
  
  private String mPlayingVideoUrl;
  
  private SeekCompletionListener mSeekCompletionListener = new SeekCompletionListener() {
      public void onCompletion(boolean param1Boolean) {
        TTBaseVideoController.this.onSeekComplete(param1Boolean);
      }
    };
  
  private boolean mSeekToCompleteWhenPause;
  
  private Surface mSurface;
  
  protected TTVideoEngine mVideoEngine;
  
  protected CoreVideoView mVideoView;
  
  private void clearPendingActions() {
    ArrayList<Runnable> arrayList = this.mPendingActions;
    if (arrayList != null) {
      if (arrayList.isEmpty())
        return; 
      this.mPendingActions.clear();
    } 
  }
  
  private void enqueueAction(Runnable paramRunnable) {
    if (this.mPendingActions == null)
      this.mPendingActions = new ArrayList<Runnable>(); 
    this.mPendingActions.add(paramRunnable);
  }
  
  private void execPendingActions() {
    if (this.mExecutingActions)
      return; 
    ArrayList<Runnable> arrayList = this.mPendingActions;
    if (arrayList != null) {
      if (arrayList.isEmpty())
        return; 
      this.mExecutingActions = true;
      Iterator<?> iterator = (new ArrayList(this.mPendingActions)).iterator();
      while (iterator.hasNext())
        ((Runnable)iterator.next()).run(); 
      this.mPendingActions.clear();
      this.mExecutingActions = false;
    } 
  }
  
  private boolean needRecreateVideoEngine(ITTVideoController.PlayerEntity paramPlayerEntity1, ITTVideoController.PlayerEntity paramPlayerEntity2, boolean paramBoolean) {
    return (paramBoolean || !Objects.equals(paramPlayerEntity1, paramPlayerEntity2));
  }
  
  private int patchTime(int paramInt) {
    boolean bool;
    int i = this.mVideoEngine.getDuration();
    int j = this.mVideoEngine.getCurrentPlaybackTime();
    if (i < 2200)
      return paramInt; 
    int k = i - 2000;
    if (!isVideoPlaying() && !isVideoPlaybackCompleted()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (paramInt >= i) {
      this.mSeekToCompleteWhenPause = isVideoPlaying() ^ true;
      return paramInt;
    } 
    i = paramInt;
    if (paramInt > k)
      i = k - 200; 
    if (j > k || !isVideoStarted()) {
      if (isVideoStarted())
        this.mVideoEngine.stop(); 
      startVideo();
      if (bool)
        pauseVideo(); 
    } 
    return i;
  }
  
  private void play(final ITTVideoController.PlayerEntity entity, boolean paramBoolean) {
    ITTVideoController.PlayerEntity playerEntity = this.mPlayerEntity;
    this.mPlayerEntity = entity;
    if (!TextUtils.equals(this.mPlayingVideoUrl, entity.getVideoUrl()))
      onVideoUrlChanged(this.mPlayingVideoUrl, entity.getVideoUrl()); 
    boolean bool = needRecreateVideoEngine(playerEntity, entity, paramBoolean);
    int i = 0;
    if (bool) {
      createVideoEngine();
      String str = entity.getVideoUrl();
      TTVideoEngine tTVideoEngine = this.mVideoEngine;
      bool = true;
      if (tTVideoEngine != null) {
        VideoRef videoRef;
        tTVideoEngine.setEncodedKey(entity.getEncodedKey());
        this.mVideoEngine.setDecryptionKey(entity.getDecryptToken());
        if (!TextUtils.isEmpty(entity.getVideoModelJsonStr())) {
          VideoModel videoModel;
          VideoLog.getInstance().writeVideoLog("try play with video model json", true);
          videoRef = new VideoRef();
          try {
            videoRef.extractFields(new JSONObject(entity.getVideoModelJsonStr()));
          } finally {
            tTVideoEngine = null;
          } 
          videoModel.setVideoRef(videoRef);
          this.mVideoEngine.setVideoModel(videoModel);
        } else if (!TextUtils.isEmpty(entity.getVideoId())) {
          VideoLog.getInstance().writeVideoLog("try play with vid", true);
          this.mVideoEngine.setPlayAPIVersion(entity.getPlayApiVersion(), entity.getAuthToken());
          this.mVideoEngine.setDataSource((DataSource)new BDataSource() {
                public String apiForFetcher(Map param1Map) {
                  return VideoUrlDepend.urlWithVideoId(entity.getVidDataSourceUrl(), param1Map);
                }
                
                public String apiForFetcher(Map<String, String> param1Map, int param1Int) {
                  return VideoUrlDepend.urlWithVideoId(entity.getVidDataSourceUrl(), param1Map);
                }
              });
          this.mVideoEngine.setVideoID(entity.getVideoId());
          this.mVideoEngine.setIntOption(33, 1);
        } else if (!TextUtils.isEmpty((CharSequence)videoRef)) {
          this.mPlayingVideoUrl = (String)videoRef;
          VideoLog.getInstance().writeVideoLog("try play with url", true);
          this.mVideoEngine.setDirectURL((String)videoRef);
        } 
      } 
      if (!this.mPlayerEntity.getAutoPlay() && !paramBoolean) {
        paramBoolean = bool;
      } else {
        paramBoolean = false;
      } 
      this.mNeedAutoPause = paramBoolean;
      startVideo(false);
    } 
    if (!TextUtils.isEmpty(entity.getVideoId())) {
      Resolution resolution2 = Resolution.Auto;
      Resolution resolution1 = resolution2;
      if (!TextUtils.isEmpty(entity.getResolution())) {
        String str = entity.getResolution().toUpperCase();
        Resolution[] arrayOfResolution = Resolution.getAllResolutions();
        int j = arrayOfResolution.length;
        while (true) {
          resolution1 = resolution2;
          if (i < j) {
            resolution1 = arrayOfResolution[i];
            if (str.equals(resolution1.toString(VideoRef.TYPE_VIDEO).toUpperCase()))
              break; 
            i++;
            continue;
          } 
          break;
        } 
      } 
      this.mVideoEngine.configResolution(resolution1);
    } 
    setLooping(entity.isLoop());
  }
  
  private void replayVideo() {
    this.mCurrent = 0;
    retry();
  }
  
  private void setKeepScreenOn(boolean paramBoolean) {
    VideoLog videoLog = VideoLog.getInstance();
    StringBuilder stringBuilder = new StringBuilder("setKeepScreenOn");
    stringBuilder.append(paramBoolean);
    videoLog.writeVideoLog(stringBuilder.toString(), true);
    if (getVideoView() != null)
      getVideoView().keepScreenOn(paramBoolean); 
  }
  
  private void startVideo(boolean paramBoolean) {
    if (paramBoolean)
      this.mNeedAutoPause = false; 
    VideoLog.getInstance().writeVideoLog("startVideo", true);
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    if (tTVideoEngine != null) {
      tTVideoEngine.setIsMute(this.mIsMute);
      int i = this.mPendingSeekToPositionForRetry;
      if (i > 0)
        this.mVideoEngine.setStartTime(i); 
    } 
    this.mIsComplete = false;
    if (!this.mIsSurfaceValid) {
      reattachSurface();
      execAction(new Runnable() {
            public void run() {
              TTBaseVideoController.this.doPlay();
            }
          });
    } else {
      tTVideoEngine = this.mVideoEngine;
      if (tTVideoEngine != null) {
        Surface surface = this.mSurface;
        if (surface != null)
          tTVideoEngine.setSurface(surface); 
        doPlay();
      } 
    } 
    this.mPendingSeekToPositionForRetry = -1;
    this.mSeekToCompleteWhenPause = false;
  }
  
  protected void createVideoEngine() {
    VideoLog.getInstance().writeVideoLog("createVideoEngine", true);
    if (this.mVideoEngine != null) {
      clearPendingActions();
      this.mVideoEngine.release();
    } 
    if (this.mPlayerInitializer == null)
      this.mPlayerInitializer = new TTPlayerInitializer(); 
    if (isLocalVideoOK()) {
      this.mVideoEngine = this.mPlayerInitializer.createVideoEngine(2);
    } else {
      this.mVideoEngine = this.mPlayerInitializer.createVideoEngine();
    } 
    this.mVideoEngine.setIntOption(110, 1);
    this.mVideoEngine.setIntOption(4, 1);
    Surface surface = this.mSurface;
    if (surface != null)
      this.mVideoEngine.setSurface(surface); 
    VideoEventManager.instance.setListener((VideoEventListener)VideoEventListenerImpl.getInstance());
    this.mVideoEngine.setListener((VideoEngineListener)this);
    if (DebugUtil.debug())
      TTVideoEngineLog.turnOn(1, 1); 
  }
  
  protected void doPlay() {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    if (tTVideoEngine != null)
      try {
        tTVideoEngine.play();
        return;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "TTBaseVideoController", exception.getStackTrace());
      }  
  }
  
  public void enterFullScreen() {
    if (getVideoView() != null)
      getVideoView().enterFullScreen(); 
  }
  
  public void execAction(Runnable paramRunnable) {
    if (paramRunnable == null)
      return; 
    if (this.mIsSurfaceValid) {
      paramRunnable.run();
      return;
    } 
    enqueueAction(paramRunnable);
  }
  
  public void exitFullScreen() {
    if (getVideoView() != null)
      getVideoView().exitFullScreen(); 
  }
  
  public int getCurrentPosition() {
    return this.mCurrent;
  }
  
  public int getDuration() {
    return this.mDuration;
  }
  
  public int getLoadState() {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    return (tTVideoEngine == null) ? this.mLoadState : tTVideoEngine.getLoadState();
  }
  
  public int getPlayDuration() {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    return (tTVideoEngine != null) ? tTVideoEngine.getWatchedDuration() : 0;
  }
  
  public int getPlaybackState() {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    return (tTVideoEngine == null) ? -1 : tTVideoEngine.getPlaybackState();
  }
  
  public CoreVideoView getVideoView() {
    return this.mVideoView;
  }
  
  public void handleMsg(Message paramMessage) {
    if (paramMessage.what != 101)
      return; 
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    if (tTVideoEngine != null) {
      int i = tTVideoEngine.getCurrentPlaybackTime();
      int j = this.mVideoEngine.getDuration();
      this.mCurrent = i;
      this.mDuration = j;
      VideoDataContext.getInstance().setDuration(this.mDuration);
      VideoLog videoLog = VideoLog.getInstance();
      StringBuilder stringBuilder = new StringBuilder("Duration: ");
      stringBuilder.append(j);
      stringBuilder.append(" current: ");
      stringBuilder.append(i);
      videoLog.writeVideoLog(stringBuilder.toString(), false);
      if (j > 0)
        onProgressAndTimeUpdate(i, j); 
    } 
    if (isVideoPlaying()) {
      Message message = this.mHandler.obtainMessage(101);
      this.mHandler.sendMessageDelayed(message, 250L);
    } 
  }
  
  public boolean isAutoPlay() {
    ITTVideoController.PlayerEntity playerEntity = this.mPlayerEntity;
    return (playerEntity != null && playerEntity.getAutoPlay());
  }
  
  public boolean isComplete() {
    return this.mIsComplete;
  }
  
  public boolean isFullScreen() {
    return getVideoView().isFullScreen();
  }
  
  protected boolean isLocalVideoOK() {
    boolean bool;
    String str;
    ITTVideoController.PlayerEntity playerEntity = this.mPlayerEntity;
    if (playerEntity != null && !TextUtils.isEmpty(playerEntity.getLocalUrl())) {
      bool = true;
    } else {
      bool = false;
    } 
    playerEntity = null;
    if (bool)
      str = this.mPlayerEntity.getLocalUrl(); 
    if (!TextUtils.isEmpty(str)) {
      File file = new File(str);
      if (file.exists() && file.canRead())
        return true; 
    } 
    return false;
  }
  
  public boolean isNeedAutoPause() {
    return this.mNeedAutoPause;
  }
  
  public boolean isShouldPlay() {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    return (tTVideoEngine != null && tTVideoEngine.isShouldPlay());
  }
  
  public boolean isVideoLooping() {
    return this.mIsLooping;
  }
  
  public boolean isVideoPaused() {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    return (tTVideoEngine != null && tTVideoEngine.isStarted() && this.mVideoEngine.getPlaybackState() == 2);
  }
  
  public boolean isVideoPausedByAudioFocus() {
    return this.mPausedByAudioFocusLoss;
  }
  
  public boolean isVideoPlaybackCompleted() {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    return (tTVideoEngine != null && tTVideoEngine.getPlaybackState() == 0 && this.mIsComplete);
  }
  
  public boolean isVideoPlaying() {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    return (tTVideoEngine != null && tTVideoEngine.isStarted() && this.mVideoEngine.getPlaybackState() == 1);
  }
  
  public boolean isVideoReleased() {
    return (this.mVideoEngine == null);
  }
  
  public boolean isVideoStarted() {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    return (tTVideoEngine != null && tTVideoEngine.isStarted() && this.mVideoEngine.getPlaybackState() != 0 && this.mVideoEngine.getPlaybackState() != 3);
  }
  
  protected boolean needTrackAudioFocus() {
    return true;
  }
  
  public void onBufferEnd() {
    VideoLog.getInstance().writeVideoLog("onBufferEnd", true);
  }
  
  public void onBufferStart() {
    VideoLog.getInstance().writeVideoLog("onBufferStart", true);
  }
  
  public void onBufferingUpdate(TTVideoEngine paramTTVideoEngine, int paramInt) {}
  
  public void onCompletion(TTVideoEngine paramTTVideoEngine) {
    VideoLog.getInstance().writeVideoLog("onCompletion", true);
    if (this.mIsLooping) {
      if (this.mSeekToCompleteWhenPause) {
        this.mSeekToCompleteWhenPause = false;
        return;
      } 
      replayVideo();
      return;
    } 
    this.mIsComplete = true;
  }
  
  public void onError(Error paramError) {
    boolean bool;
    VideoLog videoLog = VideoLog.getInstance();
    StringBuilder stringBuilder = new StringBuilder("onError");
    if (paramError != null) {
      bool = paramError.code;
    } else {
      bool = false;
    } 
    stringBuilder.append(bool);
    videoLog.writeVideoLog(stringBuilder.toString(), true);
    setKeepScreenOn(false);
  }
  
  public void onLoadStateChanged(TTVideoEngine paramTTVideoEngine, int paramInt) {
    this.mLoadState = paramTTVideoEngine.getLoadState();
    if (paramInt != 1) {
      if (paramInt != 2)
        return; 
      onBufferStart();
      return;
    } 
    onBufferEnd();
  }
  
  public void onPlaybackStateChanged(TTVideoEngine paramTTVideoEngine, int paramInt) {
    VideoLog videoLog = VideoLog.getInstance();
    StringBuilder stringBuilder = new StringBuilder("onPlaybackStateChanged playbackState:");
    stringBuilder.append(paramInt);
    videoLog.writeVideoLog(stringBuilder.toString(), true);
    if (paramInt != 0)
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt != 3)
            return; 
        } else {
          this.mIsComplete = false;
        } 
      } else {
        pauseOtherMusicPlayer();
        resumeProgressUpdate();
        setKeepScreenOn(true);
        this.mIsComplete = false;
        return;
      }  
    resumeOtherMusicPlayer();
    pauseProgressUpdate();
    clearPendingActions();
    setKeepScreenOn(false);
  }
  
  public void onPrepare(TTVideoEngine paramTTVideoEngine) {
    VideoLog.getInstance().writeVideoLog("onPrepare", true);
    this.hasRenderStarted = false;
    this.mIsComplete = false;
    if (this.mNeedAutoPause)
      pauseVideo(); 
  }
  
  public void onPrepared(TTVideoEngine paramTTVideoEngine) {
    VideoLog.getInstance().writeVideoLog("onPrepared", true);
    this.mIsComplete = false;
  }
  
  public void onProgressAndTimeUpdate(int paramInt1, int paramInt2) {}
  
  public void onRenderStart(TTVideoEngine paramTTVideoEngine) {
    VideoLog.getInstance().writeVideoLog("onRenderStart", true);
    this.hasRenderStarted = true;
    int i = this.mPendingSeekToPositionBeforeRender;
    if (i != -1) {
      seekTo(i);
      this.mPendingSeekToPositionBeforeRender = -1;
    } 
    this.mIsComplete = false;
  }
  
  protected void onSeekComplete(boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder("onSeekCompletion");
    stringBuilder.append(paramBoolean);
    String str = stringBuilder.toString();
    VideoLog.getInstance().writeVideoLog(str, true);
    if (isVideoStarted()) {
      Message message = this.mHandler.obtainMessage(101);
      this.mHandler.sendMessageDelayed(message, 250L);
    } 
  }
  
  public void onStreamChanged(TTVideoEngine paramTTVideoEngine, int paramInt) {}
  
  public void onVideoSizeChanged(TTVideoEngine paramTTVideoEngine, int paramInt1, int paramInt2) {
    VideoLog videoLog = VideoLog.getInstance();
    StringBuilder stringBuilder = new StringBuilder("onVideoSizeChanged called :");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" ");
    stringBuilder.append(paramInt2);
    videoLog.writeVideoLog(stringBuilder.toString(), true);
    if (paramInt1 > 0 && paramInt2 > 0) {
      this.mActualViewWidth = paramInt1;
      this.mActualViewHeight = paramInt2;
      this.mVideoView.setVideoSize(this.mActualViewWidth, this.mActualViewHeight);
    } 
  }
  
  public void onVideoStatusException(int paramInt) {
    // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: ldc_w 'onVideoStatusException'
    //   7: invokespecial <init> : (Ljava/lang/String;)V
    //   10: astore_2
    //   11: aload_2
    //   12: iload_1
    //   13: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload_2
    //   18: invokevirtual toString : ()Ljava/lang/String;
    //   21: astore_2
    //   22: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   25: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   28: astore_3
    //   29: invokestatic getInstance : ()Lcom/tt/miniapp/video/common/VideoLog;
    //   32: aload_2
    //   33: iconst_1
    //   34: invokevirtual writeVideoLog : (Ljava/lang/String;Z)V
    //   37: iload_1
    //   38: iconst_3
    //   39: if_icmpeq -> 105
    //   42: iload_1
    //   43: iconst_4
    //   44: if_icmpeq -> 105
    //   47: iload_1
    //   48: bipush #20
    //   50: if_icmpeq -> 105
    //   53: iload_1
    //   54: bipush #30
    //   56: if_icmpeq -> 105
    //   59: iload_1
    //   60: bipush #40
    //   62: if_icmpeq -> 80
    //   65: iload_1
    //   66: sipush #1000
    //   69: if_icmpeq -> 105
    //   72: iload_1
    //   73: sipush #1002
    //   76: if_icmpeq -> 80
    //   79: return
    //   80: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   83: aload_3
    //   84: aconst_null
    //   85: aload_3
    //   86: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   89: ldc_w 2097742053
    //   92: invokevirtual getString : (I)Ljava/lang/String;
    //   95: lconst_0
    //   96: aconst_null
    //   97: invokevirtual showToast : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)V
    //   100: aload_0
    //   101: invokevirtual releaseMedia : ()V
    //   104: return
    //   105: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   108: aload_3
    //   109: aconst_null
    //   110: aload_3
    //   111: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   114: ldc_w 2097742054
    //   117: invokevirtual getString : (I)Ljava/lang/String;
    //   120: lconst_0
    //   121: aconst_null
    //   122: invokevirtual showToast : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)V
    //   125: aload_0
    //   126: invokevirtual releaseMedia : ()V
    //   129: return
  }
  
  public void onVideoUrlChanged(String paramString1, String paramString2) {}
  
  protected void pauseOtherMusicPlayer() {
    this.mPausedByAudioFocusLoss = false;
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null)
      return; 
    if (!needTrackAudioFocus())
      return; 
    if (this.mAudioManager == null)
      this.mAudioManager = (AudioManager)application.getSystemService("audio"); 
    try {
      return;
    } finally {
      application = null;
    } 
  }
  
  protected void pauseProgressUpdate() {
    WeakHandler weakHandler = this.mHandler;
    if (weakHandler != null)
      weakHandler.removeMessages(101); 
  }
  
  public void pauseVideo() {
    VideoLog.getInstance().writeVideoLog("pauseVideo", true);
    clearPendingActions();
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    if (tTVideoEngine != null && tTVideoEngine.isStarted())
      this.mVideoEngine.pause(); 
  }
  
  public void play(ITTVideoController.PlayerEntity paramPlayerEntity) {
    play(paramPlayerEntity, false);
  }
  
  public void reattachSurface() {
    if (getVideoView() != null) {
      getVideoView().setSurfaceViewVisible(8);
      getVideoView().setSurfaceViewVisible(0);
    } 
  }
  
  public void releaseMedia() {
    VideoLog.getInstance().writeVideoLog("releaseMedia", true);
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    if (tTVideoEngine != null) {
      tTVideoEngine.release();
      this.mVideoEngine = null;
    } 
    resumeOtherMusicPlayer();
    clearPendingActions();
    pauseProgressUpdate();
    this.mIsComplete = false;
    this.mPausedByAudioFocusLoss = false;
    this.mPendingSeekToPositionForRetry = -1;
    this.mPendingSeekToPositionBeforeRender = -1;
    this.mPlayerEntity = null;
  }
  
  void resumeOtherMusicPlayer() {
    if (!needTrackAudioFocus())
      return; 
    AudioManager audioManager = this.mAudioManager;
    if (audioManager != null) {
      AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = this.mAudioFocusListener;
      if (onAudioFocusChangeListener != null) {
        audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        this.mAudioManager = null;
      } 
    } 
  }
  
  protected void resumeProgressUpdate() {
    WeakHandler weakHandler = this.mHandler;
    if (weakHandler != null && !weakHandler.hasMessages(101))
      this.mHandler.sendEmptyMessage(101); 
  }
  
  public void retry() {
    VideoLog.getInstance().writeVideoLog("retry", true);
    int i = this.mCurrent;
    if (i > 0)
      this.mPendingSeekToPositionForRetry = i; 
    ITTVideoController.PlayerEntity playerEntity = this.mPlayerEntity;
    if (playerEntity != null)
      play(playerEntity, true); 
  }
  
  public void seekTo(int paramInt) {
    VideoLog videoLog = VideoLog.getInstance();
    StringBuilder stringBuilder = new StringBuilder("seekTo ");
    stringBuilder.append(paramInt);
    videoLog.writeVideoLog(stringBuilder.toString(), true);
    if (this.mVideoEngine == null)
      return; 
    paramInt = patchTime(paramInt);
    pauseProgressUpdate();
    if (this.hasRenderStarted) {
      this.mVideoEngine.seekTo(paramInt, this.mSeekCompletionListener);
      return;
    } 
    this.mPendingSeekToPositionBeforeRender = paramInt;
  }
  
  public void setIsMute(boolean paramBoolean) {
    VideoLog videoLog = VideoLog.getInstance();
    StringBuilder stringBuilder = new StringBuilder("setIsMute ");
    stringBuilder.append(paramBoolean);
    videoLog.writeVideoLog(stringBuilder.toString(), true);
    this.mIsMute = paramBoolean;
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    if (tTVideoEngine != null)
      tTVideoEngine.setIsMute(this.mIsMute); 
  }
  
  public void setLooping(boolean paramBoolean) {
    this.mIsLooping = paramBoolean;
  }
  
  public void setVideoView(CoreVideoView paramCoreVideoView) {
    this.mVideoView = paramCoreVideoView;
    paramCoreVideoView = this.mVideoView;
    if (paramCoreVideoView != null)
      paramCoreVideoView.setVideoViewCallback(this); 
  }
  
  public void startVideo() {
    startVideo(true);
  }
  
  public void stopVideo() {
    VideoLog.getInstance().writeVideoLog("stopVideo", true);
    clearPendingActions();
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    if (tTVideoEngine != null)
      tTVideoEngine.stop(); 
    seekTo(0);
  }
  
  public void textureViewCreated(Surface paramSurface) {
    VideoLog.getInstance().writeVideoLog("textureViewCreated", true);
    this.mIsSurfaceValid = true;
    this.mSurface = paramSurface;
    if (this.mHasSetDisplay)
      return; 
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    if (tTVideoEngine == null)
      return; 
    tTVideoEngine.setSurface(paramSurface);
    this.mHasSetDisplay = true;
    execPendingActions();
  }
  
  public void textureViewDestroyed(Surface paramSurface) {
    VideoLog.getInstance().writeVideoLog("textureViewDestroyed", true);
    this.mIsSurfaceValid = false;
    this.mSurface = null;
    setKeepScreenOn(false);
    this.mHasSetDisplay = false;
  }
  
  public void textureViewSizeChanged(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2) {
    TTVideoEngine tTVideoEngine = this.mVideoEngine;
    if (tTVideoEngine != null)
      tTVideoEngine.setSurface(new Surface(paramSurfaceTexture)); 
  }
  
  public void updateShowState(ITTVideoController.ShowStateEntity paramShowStateEntity) {
    CoreVideoView coreVideoView = getVideoView();
    if (coreVideoView != null) {
      coreVideoView.setObjectFit(paramShowStateEntity.getObjectFit());
    } else {
      AppBrandLogger.e("TTBaseVideoController", new Object[] { "updateShowStateError coreVideoView == null" });
    } 
    Bundle bundle = new Bundle();
    bundle.putParcelable("showState", paramShowStateEntity);
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(203, bundle));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\base\TTBaseVideoController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */