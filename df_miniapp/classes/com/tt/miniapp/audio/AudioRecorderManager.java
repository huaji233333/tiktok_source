package com.tt.miniapp.audio;

import android.media.AudioRecord;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.bytedance.v.a.a.a.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.audio.encoder.PcmToWav;
import com.tt.miniapp.manager.ForeBackgroundManager;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.util.CountDownHelper;
import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioRecorderManager {
  private final AudioRecorderConfig DEFAULT_CONFIG = new AudioRecorderConfig(60L, 8000, 48000, (short)2, "aac", 0);
  
  public int mAllStep;
  
  public AudioRecord mAudioRecord;
  
  public int mBufferSize;
  
  private CountDownHelper mCountDownHelper = new CountDownHelper();
  
  public AudioRecorderConfig mCurrentConfig;
  
  public byte[] mFrameBuffer;
  
  public boolean mIsRecord;
  
  public boolean mPauseRecordWhenBackground;
  
  public String mPcmTmpFile;
  
  public IRecorderCallback mRecorderCallback;
  
  public int mStepCount;
  
  private AudioRecorderManager() {
    ForeBackgroundManager foreBackgroundManager = AppbrandApplicationImpl.getInst().getForeBackgroundManager();
    this.mPauseRecordWhenBackground = foreBackgroundManager.isBackground();
    foreBackgroundManager.registerForeBackgroundListener((ForeBackgroundManager.ForeBackgroundListener)new ForeBackgroundManager.DefaultForeBackgroundListener() {
          public void onBackground() {
            synchronized (AudioRecorderManager.this) {
              AudioRecorderManager.this.mPauseRecordWhenBackground = true;
              if (AudioRecorderManager.this.mIsRecord)
                AudioRecorderManager.this.pause(); 
              return;
            } 
          }
          
          public void onForeground() {
            synchronized (AudioRecorderManager.this) {
              AudioRecorderManager.this.mPauseRecordWhenBackground = false;
              return;
            } 
          }
        });
    this.mCountDownHelper.setListener(new CountDownHelper.ICountDownListener() {
          public void onFinish() {
            AudioRecorderManager.this.stop();
          }
        });
  }
  
  private static void com_tt_miniapp_audio_AudioRecorderManager_android_media_AudioRecord_stop(AudioRecord paramAudioRecord) {
    paramAudioRecord.stop();
    b.a(null, paramAudioRecord, new Object[0], false, 100401, "android.media.AudioRecord.stop()");
  }
  
  public static String createTmpFile(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append((new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date()));
    stringBuilder.append(".");
    stringBuilder.append(paramString);
    paramString = stringBuilder.toString();
    File file = new File(FileManager.inst().getTempDir(), paramString);
    AppBrandLogger.d("tma_AudioRecorderManager", new Object[] { "destFile.getAbsolutePath() ", file.getAbsolutePath() });
    return file.getAbsolutePath();
  }
  
  private AudioRecorderConfig getAllConfig(AudioRecorderConfig paramAudioRecorderConfig) {
    short s;
    int i;
    long l;
    String str;
    if (paramAudioRecorderConfig == null)
      return this.DEFAULT_CONFIG; 
    if (paramAudioRecorderConfig.duration > 0L) {
      l = paramAudioRecorderConfig.duration;
    } else {
      l = this.DEFAULT_CONFIG.duration;
    } 
    paramAudioRecorderConfig.duration = l;
    if (paramAudioRecorderConfig.sampleRate > 0) {
      i = paramAudioRecorderConfig.sampleRate;
    } else {
      i = this.DEFAULT_CONFIG.sampleRate;
    } 
    paramAudioRecorderConfig.sampleRate = i;
    if (paramAudioRecorderConfig.encodeBitRate > 0) {
      i = paramAudioRecorderConfig.encodeBitRate;
    } else {
      i = this.DEFAULT_CONFIG.encodeBitRate;
    } 
    paramAudioRecorderConfig.encodeBitRate = i;
    if (paramAudioRecorderConfig.numberOfChannels > 0) {
      s = paramAudioRecorderConfig.numberOfChannels;
    } else {
      s = this.DEFAULT_CONFIG.numberOfChannels;
    } 
    paramAudioRecorderConfig.numberOfChannels = s;
    if (!TextUtils.isEmpty(paramAudioRecorderConfig.format)) {
      str = paramAudioRecorderConfig.format;
    } else {
      str = this.DEFAULT_CONFIG.format;
    } 
    paramAudioRecorderConfig.format = str;
    if (paramAudioRecorderConfig.frameSize > 0) {
      i = paramAudioRecorderConfig.frameSize;
    } else {
      i = this.DEFAULT_CONFIG.frameSize;
    } 
    paramAudioRecorderConfig.frameSize = i;
    return paramAudioRecorderConfig;
  }
  
  private int getBufferSize(int paramInt1, int paramInt2) {
    int i = 1;
    while (i) {
      i = paramInt1 % paramInt2;
      if (i == 0)
        return paramInt2; 
      paramInt2++;
    } 
    return paramInt2;
  }
  
  private int getChannelType(int paramInt) {
    return (paramInt == 1) ? 16 : 12;
  }
  
  public static AudioRecorderManager getInst() {
    return Holder.instance;
  }
  
  private void initAudioRecord(AudioRecorderConfig paramAudioRecorderConfig) {
    int i = AudioRecord.getMinBufferSize(paramAudioRecorderConfig.sampleRate, getChannelType(paramAudioRecorderConfig.numberOfChannels), 2);
    if (paramAudioRecorderConfig.frameSize != 0) {
      int j = paramAudioRecorderConfig.frameSize * 1024;
      this.mBufferSize = getBufferSize(j, i);
      this.mAllStep = j / this.mBufferSize;
      this.mFrameBuffer = new byte[j];
      AppBrandLogger.d("tma_AudioRecorderManager", new Object[] { "frameSize_byte = ", Integer.valueOf(j) });
      AppBrandLogger.d("tma_AudioRecorderManager", new Object[] { "mBufferSize = ", Integer.valueOf(this.mBufferSize) });
    } else {
      this.mBufferSize = i;
    } 
    this.mAudioRecord = new AudioRecord(1, paramAudioRecorderConfig.sampleRate, getChannelType(paramAudioRecorderConfig.numberOfChannels), 2, this.mBufferSize);
    this.mPcmTmpFile = createTmpFile("pcm");
  }
  
  public void onError(String paramString) {
    AppBrandLogger.e("tma_AudioRecorderManager", new Object[] { "onError:", paramString });
    IRecorderCallback iRecorderCallback = this.mRecorderCallback;
    if (iRecorderCallback != null)
      iRecorderCallback.onRecorderStateChange("error", paramString); 
  }
  
  public void pause() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mIsRecord : Z
    //   6: ifne -> 44
    //   9: aload_0
    //   10: getfield mPauseRecordWhenBackground : Z
    //   13: ifeq -> 34
    //   16: ldc 'tma_AudioRecorderManager'
    //   18: iconst_1
    //   19: anewarray java/lang/Object
    //   22: dup
    //   23: iconst_0
    //   24: ldc_w 'PauseRecordWhenBackground'
    //   27: aastore
    //   28: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: aload_0
    //   35: ldc_w 'not recording'
    //   38: invokevirtual onError : (Ljava/lang/String;)V
    //   41: aload_0
    //   42: monitorexit
    //   43: return
    //   44: aload_0
    //   45: iconst_0
    //   46: putfield mIsRecord : Z
    //   49: aload_0
    //   50: getfield mRecorderCallback : Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;
    //   53: ifnull -> 69
    //   56: aload_0
    //   57: getfield mRecorderCallback : Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;
    //   60: ldc_w 'pause'
    //   63: aconst_null
    //   64: invokeinterface onRecorderStateChange : (Ljava/lang/String;Ljava/lang/String;)V
    //   69: aload_0
    //   70: getfield mCountDownHelper : Lcom/tt/miniapp/util/CountDownHelper;
    //   73: invokevirtual pause : ()V
    //   76: invokestatic inst : ()Lcom/tt/miniapp/secrecy/SecrecyManager;
    //   79: bipush #13
    //   81: invokevirtual notifyStateStop : (I)Z
    //   84: pop
    //   85: aload_0
    //   86: monitorexit
    //   87: return
    //   88: astore_1
    //   89: aload_0
    //   90: monitorexit
    //   91: aload_1
    //   92: athrow
    // Exception table:
    //   from	to	target	type
    //   2	31	88	finally
    //   34	41	88	finally
    //   44	69	88	finally
    //   69	85	88	finally
  }
  
  public void registerRecorderCallback(IRecorderCallback paramIRecorderCallback) {
    if (paramIRecorderCallback != null)
      this.mRecorderCallback = paramIRecorderCallback; 
  }
  
  public void resume() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPauseRecordWhenBackground : Z
    //   6: ifeq -> 19
    //   9: aload_0
    //   10: ldc_w 'app in background'
    //   13: invokevirtual onError : (Ljava/lang/String;)V
    //   16: aload_0
    //   17: monitorexit
    //   18: return
    //   19: aload_0
    //   20: iconst_1
    //   21: putfield mIsRecord : Z
    //   24: new com/tt/miniapp/audio/AudioRecorderManager$AudioRecordTask
    //   27: dup
    //   28: aload_0
    //   29: invokespecial <init> : (Lcom/tt/miniapp/audio/AudioRecorderManager;)V
    //   32: iconst_0
    //   33: anewarray java/lang/Void
    //   36: invokevirtual execute : ([Ljava/lang/Object;)Landroid/os/AsyncTask;
    //   39: pop
    //   40: aload_0
    //   41: getfield mRecorderCallback : Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;
    //   44: ifnull -> 60
    //   47: aload_0
    //   48: getfield mRecorderCallback : Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;
    //   51: ldc_w 'resume'
    //   54: aconst_null
    //   55: invokeinterface onRecorderStateChange : (Ljava/lang/String;Ljava/lang/String;)V
    //   60: aload_0
    //   61: getfield mCountDownHelper : Lcom/tt/miniapp/util/CountDownHelper;
    //   64: invokevirtual resume : ()V
    //   67: invokestatic inst : ()Lcom/tt/miniapp/secrecy/SecrecyManager;
    //   70: bipush #13
    //   72: invokevirtual notifyStateStart : (I)Z
    //   75: pop
    //   76: aload_0
    //   77: monitorexit
    //   78: return
    //   79: astore_1
    //   80: aload_0
    //   81: monitorexit
    //   82: aload_1
    //   83: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	79	finally
    //   19	60	79	finally
    //   60	76	79	finally
  }
  
  public void start(AudioRecorderConfig paramAudioRecorderConfig) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPauseRecordWhenBackground : Z
    //   6: ifeq -> 19
    //   9: aload_0
    //   10: ldc_w 'app in background'
    //   13: invokevirtual onError : (Ljava/lang/String;)V
    //   16: aload_0
    //   17: monitorexit
    //   18: return
    //   19: aload_0
    //   20: aload_1
    //   21: invokespecial getAllConfig : (Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;)Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;
    //   24: astore_2
    //   25: aload_2
    //   26: ldc_w 'wav'
    //   29: putfield format : Ljava/lang/String;
    //   32: aload_2
    //   33: getfield format : Ljava/lang/String;
    //   36: ldc_w 'wav'
    //   39: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   42: ifne -> 55
    //   45: aload_0
    //   46: ldc_w 'format is error'
    //   49: invokevirtual onError : (Ljava/lang/String;)V
    //   52: aload_0
    //   53: monitorexit
    //   54: return
    //   55: invokestatic getInst : ()Lcom/tt/miniapp/audio/background/BgAudioManagerClient;
    //   58: aconst_null
    //   59: invokevirtual pause : (Lcom/tt/miniapp/audio/background/BgAudioManagerClient$TaskListener;)V
    //   62: aload_0
    //   63: aload_2
    //   64: putfield mCurrentConfig : Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;
    //   67: aload_0
    //   68: getfield mAudioRecord : Landroid/media/AudioRecord;
    //   71: ifnonnull -> 82
    //   74: aload_0
    //   75: aload_0
    //   76: getfield mCurrentConfig : Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;
    //   79: invokespecial initAudioRecord : (Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;)V
    //   82: aload_0
    //   83: iconst_1
    //   84: putfield mIsRecord : Z
    //   87: new com/tt/miniapp/audio/AudioRecorderManager$AudioRecordTask
    //   90: dup
    //   91: aload_0
    //   92: invokespecial <init> : (Lcom/tt/miniapp/audio/AudioRecorderManager;)V
    //   95: iconst_0
    //   96: anewarray java/lang/Void
    //   99: invokevirtual execute : ([Ljava/lang/Object;)Landroid/os/AsyncTask;
    //   102: pop
    //   103: aload_0
    //   104: getfield mRecorderCallback : Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;
    //   107: ifnull -> 123
    //   110: aload_0
    //   111: getfield mRecorderCallback : Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;
    //   114: ldc_w 'start'
    //   117: aconst_null
    //   118: invokeinterface onRecorderStateChange : (Ljava/lang/String;Ljava/lang/String;)V
    //   123: aload_0
    //   124: getfield mCountDownHelper : Lcom/tt/miniapp/util/CountDownHelper;
    //   127: aload_1
    //   128: getfield duration : J
    //   131: invokevirtual start : (J)V
    //   134: invokestatic inst : ()Lcom/tt/miniapp/secrecy/SecrecyManager;
    //   137: bipush #13
    //   139: invokevirtual notifyStateStart : (I)Z
    //   142: pop
    //   143: aload_0
    //   144: monitorexit
    //   145: return
    //   146: astore_1
    //   147: aload_0
    //   148: monitorexit
    //   149: aload_1
    //   150: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	146	finally
    //   19	52	146	finally
    //   55	82	146	finally
    //   82	123	146	finally
    //   123	143	146	finally
  }
  
  public void stop() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_0
    //   4: putfield mIsRecord : Z
    //   7: aload_0
    //   8: getfield mAudioRecord : Landroid/media/AudioRecord;
    //   11: astore_1
    //   12: aload_1
    //   13: ifnull -> 58
    //   16: aload_0
    //   17: getfield mAudioRecord : Landroid/media/AudioRecord;
    //   20: invokestatic com_tt_miniapp_audio_AudioRecorderManager_android_media_AudioRecord_stop : (Landroid/media/AudioRecord;)V
    //   23: aload_0
    //   24: getfield mAudioRecord : Landroid/media/AudioRecord;
    //   27: invokevirtual release : ()V
    //   30: goto -> 53
    //   33: astore_1
    //   34: ldc 'tma_AudioRecorderManager'
    //   36: iconst_2
    //   37: anewarray java/lang/Object
    //   40: dup
    //   41: iconst_0
    //   42: ldc_w 'stop audio record'
    //   45: aastore
    //   46: dup
    //   47: iconst_1
    //   48: aload_1
    //   49: aastore
    //   50: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   53: aload_0
    //   54: aconst_null
    //   55: putfield mAudioRecord : Landroid/media/AudioRecord;
    //   58: aload_0
    //   59: getfield mCountDownHelper : Lcom/tt/miniapp/util/CountDownHelper;
    //   62: invokevirtual stop : ()V
    //   65: aload_0
    //   66: getfield mCurrentConfig : Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;
    //   69: ifnull -> 120
    //   72: invokestatic inst : ()Lcom/tt/miniapp/secrecy/SecrecyManager;
    //   75: bipush #13
    //   77: invokevirtual isSecrecyDenied : (I)Z
    //   80: ifeq -> 93
    //   83: aload_0
    //   84: ldc_w 'auth deny'
    //   87: invokevirtual onError : (Ljava/lang/String;)V
    //   90: goto -> 120
    //   93: new com/tt/miniapp/audio/AudioRecorderManager$AudioEncoderTask
    //   96: dup
    //   97: aload_0
    //   98: getfield mCurrentConfig : Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;
    //   101: aload_0
    //   102: getfield mRecorderCallback : Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;
    //   105: aload_0
    //   106: getfield mPcmTmpFile : Ljava/lang/String;
    //   109: invokespecial <init> : (Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;Ljava/lang/String;)V
    //   112: iconst_0
    //   113: anewarray java/lang/Void
    //   116: invokevirtual execute : ([Ljava/lang/Object;)Landroid/os/AsyncTask;
    //   119: pop
    //   120: aload_0
    //   121: aconst_null
    //   122: putfield mCurrentConfig : Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;
    //   125: aload_0
    //   126: aconst_null
    //   127: putfield mRecorderCallback : Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;
    //   130: invokestatic inst : ()Lcom/tt/miniapp/secrecy/SecrecyManager;
    //   133: bipush #13
    //   135: invokevirtual notifyStateStop : (I)Z
    //   138: pop
    //   139: aload_0
    //   140: monitorexit
    //   141: return
    //   142: astore_1
    //   143: aload_0
    //   144: monitorexit
    //   145: aload_1
    //   146: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	142	finally
    //   16	30	33	java/lang/Exception
    //   16	30	142	finally
    //   34	53	142	finally
    //   53	58	142	finally
    //   58	90	142	finally
    //   93	120	142	finally
    //   120	139	142	finally
  }
  
  static class AudioEncoderTask extends AsyncTask<Void, Void, Void> {
    private AudioRecorderManager.IRecorderCallback mCallback;
    
    private AudioRecorderManager.AudioRecorderConfig mConfig;
    
    private String mPcmFile;
    
    public AudioEncoderTask(AudioRecorderManager.AudioRecorderConfig param1AudioRecorderConfig, AudioRecorderManager.IRecorderCallback param1IRecorderCallback, String param1String) {
      this.mPcmFile = param1String;
      this.mConfig = param1AudioRecorderConfig;
      this.mCallback = param1IRecorderCallback;
    }
    
    protected Void doInBackground(Void... param1VarArgs) {
      String str = AudioRecorderManager.createTmpFile("wav");
      PcmToWav.makePCMFileToWAVFile(this.mPcmFile, str, true, this.mConfig);
      AppBrandLogger.d("tma_AudioRecorderManager", new Object[] { "doInBackground ", str, " ", this.mCallback });
      AudioRecorderManager.IRecorderCallback iRecorderCallback = this.mCallback;
      if (iRecorderCallback != null)
        iRecorderCallback.onRecorderStateChange("stop", str); 
      return null;
    }
  }
  
  class AudioRecordTask extends AsyncTask<Void, Void, Void> {
    private static void com_tt_miniapp_audio_AudioRecorderManager$AudioRecordTask_android_media_AudioRecord_startRecording(AudioRecord param1AudioRecord) {
      param1AudioRecord.startRecording();
      b.a(null, param1AudioRecord, new Object[0], false, 100400, "android.media.AudioRecord.startRecording()");
    }
    
    private static void com_tt_miniapp_audio_AudioRecorderManager$AudioRecordTask_android_media_AudioRecord_stop(AudioRecord param1AudioRecord) {
      param1AudioRecord.stop();
      b.a(null, param1AudioRecord, new Object[0], false, 100401, "android.media.AudioRecord.stop()");
    }
    
    protected Void doInBackground(Void... param1VarArgs) {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   4: getfield mAudioRecord : Landroid/media/AudioRecord;
      //   7: ifnonnull -> 12
      //   10: aconst_null
      //   11: areturn
      //   12: new java/io/RandomAccessFile
      //   15: dup
      //   16: new java/io/File
      //   19: dup
      //   20: aload_0
      //   21: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   24: getfield mPcmTmpFile : Ljava/lang/String;
      //   27: invokespecial <init> : (Ljava/lang/String;)V
      //   30: ldc 'rw'
      //   32: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
      //   35: astore #4
      //   37: aload #4
      //   39: astore_1
      //   40: aload_0
      //   41: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   44: getfield mBufferSize : I
      //   47: newarray byte
      //   49: astore #5
      //   51: aload #4
      //   53: astore_1
      //   54: aload_0
      //   55: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   58: getfield mAudioRecord : Landroid/media/AudioRecord;
      //   61: invokestatic com_tt_miniapp_audio_AudioRecorderManager$AudioRecordTask_android_media_AudioRecord_startRecording : (Landroid/media/AudioRecord;)V
      //   64: aload #4
      //   66: astore_1
      //   67: aload_0
      //   68: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   71: getfield mIsRecord : Z
      //   74: ifeq -> 346
      //   77: aload #4
      //   79: astore_1
      //   80: aload_0
      //   81: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   84: getfield mAudioRecord : Landroid/media/AudioRecord;
      //   87: aload #5
      //   89: iconst_0
      //   90: aload #5
      //   92: arraylength
      //   93: invokevirtual read : ([BII)I
      //   96: istore_2
      //   97: iload_2
      //   98: iflt -> 346
      //   101: aload #4
      //   103: astore_1
      //   104: ldc 'tma_AudioRecorderManager'
      //   106: iconst_2
      //   107: anewarray java/lang/Object
      //   110: dup
      //   111: iconst_0
      //   112: ldc 'readSize = '
      //   114: aastore
      //   115: dup
      //   116: iconst_1
      //   117: iload_2
      //   118: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   121: aastore
      //   122: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   125: aload #4
      //   127: astore_1
      //   128: aload #4
      //   130: aload #4
      //   132: invokevirtual length : ()J
      //   135: invokevirtual seek : (J)V
      //   138: aload #4
      //   140: astore_1
      //   141: aload #4
      //   143: aload #5
      //   145: iconst_0
      //   146: aload #5
      //   148: arraylength
      //   149: invokevirtual write : ([BII)V
      //   152: aload #4
      //   154: astore_1
      //   155: aload_0
      //   156: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   159: getfield mCurrentConfig : Lcom/tt/miniapp/audio/AudioRecorderManager$AudioRecorderConfig;
      //   162: astore #6
      //   164: aload #6
      //   166: ifnull -> 64
      //   169: aload #4
      //   171: astore_1
      //   172: aload #6
      //   174: getfield frameSize : I
      //   177: ifle -> 64
      //   180: aload #4
      //   182: astore_1
      //   183: aload #5
      //   185: iconst_0
      //   186: aload_0
      //   187: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   190: getfield mFrameBuffer : [B
      //   193: aload_0
      //   194: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   197: getfield mStepCount : I
      //   200: aload_0
      //   201: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   204: getfield mBufferSize : I
      //   207: imul
      //   208: aload #5
      //   210: arraylength
      //   211: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
      //   214: aload #4
      //   216: astore_1
      //   217: aload_0
      //   218: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   221: astore #6
      //   223: aload #4
      //   225: astore_1
      //   226: aload #6
      //   228: aload #6
      //   230: getfield mStepCount : I
      //   233: iconst_1
      //   234: iadd
      //   235: putfield mStepCount : I
      //   238: aload #4
      //   240: astore_1
      //   241: aload_0
      //   242: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   245: getfield mStepCount : I
      //   248: aload_0
      //   249: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   252: getfield mAllStep : I
      //   255: if_icmpne -> 64
      //   258: aload #4
      //   260: astore_1
      //   261: aload_0
      //   262: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   265: iconst_0
      //   266: putfield mStepCount : I
      //   269: aload #4
      //   271: astore_1
      //   272: aload_0
      //   273: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   276: getfield mRecorderCallback : Lcom/tt/miniapp/audio/AudioRecorderManager$IRecorderCallback;
      //   279: astore #6
      //   281: aload #6
      //   283: ifnull -> 329
      //   286: aload #4
      //   288: astore_1
      //   289: aload_0
      //   290: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   293: getfield mFrameBuffer : [B
      //   296: astore #7
      //   298: aload #4
      //   300: astore_1
      //   301: aload_0
      //   302: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   305: getfield mIsRecord : Z
      //   308: ifne -> 652
      //   311: iconst_1
      //   312: istore_3
      //   313: goto -> 316
      //   316: aload #4
      //   318: astore_1
      //   319: aload #6
      //   321: aload #7
      //   323: iload_3
      //   324: invokeinterface onFrameRecorded : ([BZ)V
      //   329: aload #4
      //   331: astore_1
      //   332: aload_0
      //   333: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   336: getfield mFrameBuffer : [B
      //   339: iconst_0
      //   340: invokestatic fill : ([BB)V
      //   343: goto -> 64
      //   346: aload_0
      //   347: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   350: getfield mAudioRecord : Landroid/media/AudioRecord;
      //   353: ifnull -> 388
      //   356: aload_0
      //   357: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   360: getfield mAudioRecord : Landroid/media/AudioRecord;
      //   363: invokestatic com_tt_miniapp_audio_AudioRecorderManager$AudioRecordTask_android_media_AudioRecord_stop : (Landroid/media/AudioRecord;)V
      //   366: goto -> 388
      //   369: astore_1
      //   370: ldc 'tma_AudioRecorderManager'
      //   372: iconst_2
      //   373: anewarray java/lang/Object
      //   376: dup
      //   377: iconst_0
      //   378: ldc 'stop audio record'
      //   380: aastore
      //   381: dup
      //   382: iconst_1
      //   383: aload_1
      //   384: aastore
      //   385: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   388: aload #4
      //   390: invokevirtual close : ()V
      //   393: aconst_null
      //   394: areturn
      //   395: astore_1
      //   396: ldc 'tma_AudioRecorderManager'
      //   398: iconst_2
      //   399: anewarray java/lang/Object
      //   402: dup
      //   403: iconst_0
      //   404: ldc 'close random access file'
      //   406: aastore
      //   407: dup
      //   408: iconst_1
      //   409: aload_1
      //   410: aastore
      //   411: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   414: aconst_null
      //   415: areturn
      //   416: astore #5
      //   418: goto -> 433
      //   421: astore_1
      //   422: aconst_null
      //   423: astore #4
      //   425: goto -> 566
      //   428: astore #5
      //   430: aconst_null
      //   431: astore #4
      //   433: aload #4
      //   435: astore_1
      //   436: ldc 'tma_AudioRecorderManager'
      //   438: iconst_2
      //   439: anewarray java/lang/Object
      //   442: dup
      //   443: iconst_0
      //   444: ldc 'audio recording error'
      //   446: aastore
      //   447: dup
      //   448: iconst_1
      //   449: aload #5
      //   451: aastore
      //   452: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   455: aload #4
      //   457: astore_1
      //   458: aload_0
      //   459: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   462: getfield mIsRecord : Z
      //   465: ifeq -> 483
      //   468: aload #4
      //   470: astore_1
      //   471: aload_0
      //   472: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   475: aload #5
      //   477: invokevirtual getMessage : ()Ljava/lang/String;
      //   480: invokevirtual onError : (Ljava/lang/String;)V
      //   483: aload_0
      //   484: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   487: getfield mAudioRecord : Landroid/media/AudioRecord;
      //   490: ifnull -> 525
      //   493: aload_0
      //   494: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   497: getfield mAudioRecord : Landroid/media/AudioRecord;
      //   500: invokestatic com_tt_miniapp_audio_AudioRecorderManager$AudioRecordTask_android_media_AudioRecord_stop : (Landroid/media/AudioRecord;)V
      //   503: goto -> 525
      //   506: astore_1
      //   507: ldc 'tma_AudioRecorderManager'
      //   509: iconst_2
      //   510: anewarray java/lang/Object
      //   513: dup
      //   514: iconst_0
      //   515: ldc 'stop audio record'
      //   517: aastore
      //   518: dup
      //   519: iconst_1
      //   520: aload_1
      //   521: aastore
      //   522: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   525: aload #4
      //   527: ifnull -> 556
      //   530: aload #4
      //   532: invokevirtual close : ()V
      //   535: aconst_null
      //   536: areturn
      //   537: astore_1
      //   538: ldc 'tma_AudioRecorderManager'
      //   540: iconst_2
      //   541: anewarray java/lang/Object
      //   544: dup
      //   545: iconst_0
      //   546: ldc 'close random access file'
      //   548: aastore
      //   549: dup
      //   550: iconst_1
      //   551: aload_1
      //   552: aastore
      //   553: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   556: aconst_null
      //   557: areturn
      //   558: astore #5
      //   560: aload_1
      //   561: astore #4
      //   563: aload #5
      //   565: astore_1
      //   566: aload_0
      //   567: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   570: getfield mAudioRecord : Landroid/media/AudioRecord;
      //   573: ifnull -> 610
      //   576: aload_0
      //   577: getfield this$0 : Lcom/tt/miniapp/audio/AudioRecorderManager;
      //   580: getfield mAudioRecord : Landroid/media/AudioRecord;
      //   583: invokestatic com_tt_miniapp_audio_AudioRecorderManager$AudioRecordTask_android_media_AudioRecord_stop : (Landroid/media/AudioRecord;)V
      //   586: goto -> 610
      //   589: astore #5
      //   591: ldc 'tma_AudioRecorderManager'
      //   593: iconst_2
      //   594: anewarray java/lang/Object
      //   597: dup
      //   598: iconst_0
      //   599: ldc 'stop audio record'
      //   601: aastore
      //   602: dup
      //   603: iconst_1
      //   604: aload #5
      //   606: aastore
      //   607: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   610: aload #4
      //   612: ifnull -> 644
      //   615: aload #4
      //   617: invokevirtual close : ()V
      //   620: goto -> 644
      //   623: astore #4
      //   625: ldc 'tma_AudioRecorderManager'
      //   627: iconst_2
      //   628: anewarray java/lang/Object
      //   631: dup
      //   632: iconst_0
      //   633: ldc 'close random access file'
      //   635: aastore
      //   636: dup
      //   637: iconst_1
      //   638: aload #4
      //   640: aastore
      //   641: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   644: goto -> 649
      //   647: aload_1
      //   648: athrow
      //   649: goto -> 647
      //   652: iconst_0
      //   653: istore_3
      //   654: goto -> 316
      // Exception table:
      //   from	to	target	type
      //   12	37	428	java/lang/Exception
      //   12	37	421	finally
      //   40	51	416	java/lang/Exception
      //   40	51	558	finally
      //   54	64	416	java/lang/Exception
      //   54	64	558	finally
      //   67	77	416	java/lang/Exception
      //   67	77	558	finally
      //   80	97	416	java/lang/Exception
      //   80	97	558	finally
      //   104	125	416	java/lang/Exception
      //   104	125	558	finally
      //   128	138	416	java/lang/Exception
      //   128	138	558	finally
      //   141	152	416	java/lang/Exception
      //   141	152	558	finally
      //   155	164	416	java/lang/Exception
      //   155	164	558	finally
      //   172	180	416	java/lang/Exception
      //   172	180	558	finally
      //   183	214	416	java/lang/Exception
      //   183	214	558	finally
      //   217	223	416	java/lang/Exception
      //   217	223	558	finally
      //   226	238	416	java/lang/Exception
      //   226	238	558	finally
      //   241	258	416	java/lang/Exception
      //   241	258	558	finally
      //   261	269	416	java/lang/Exception
      //   261	269	558	finally
      //   272	281	416	java/lang/Exception
      //   272	281	558	finally
      //   289	298	416	java/lang/Exception
      //   289	298	558	finally
      //   301	311	416	java/lang/Exception
      //   301	311	558	finally
      //   319	329	416	java/lang/Exception
      //   319	329	558	finally
      //   332	343	416	java/lang/Exception
      //   332	343	558	finally
      //   346	366	369	java/lang/IllegalStateException
      //   388	393	395	java/io/IOException
      //   436	455	558	finally
      //   458	468	558	finally
      //   471	483	558	finally
      //   483	503	506	java/lang/IllegalStateException
      //   530	535	537	java/io/IOException
      //   566	586	589	java/lang/IllegalStateException
      //   615	620	623	java/io/IOException
    }
  }
  
  public static class AudioRecorderConfig {
    public long duration;
    
    public int encodeBitRate;
    
    public String format;
    
    public int frameSize;
    
    public short numberOfChannels;
    
    public int sampleRate;
    
    public AudioRecorderConfig(long param1Long, int param1Int1, int param1Int2, short param1Short, String param1String, int param1Int3) {
      this.duration = param1Long;
      this.sampleRate = param1Int1;
      this.encodeBitRate = param1Int2;
      this.numberOfChannels = param1Short;
      this.format = param1String;
      this.frameSize = param1Int3;
    }
  }
  
  static class Holder {
    static AudioRecorderManager instance = new AudioRecorderManager();
  }
  
  public static interface IRecorderCallback {
    void onFrameRecorded(byte[] param1ArrayOfbyte, boolean param1Boolean);
    
    void onRecorderStateChange(String param1String1, String param1String2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\AudioRecorderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */