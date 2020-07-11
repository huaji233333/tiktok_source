package com.tt.miniapp.audio.background;

import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.SparseArray;
import com.tt.miniapp.audio.AudioManager;
import com.tt.miniapp.audio.AudioStateModule;
import com.tt.miniapp.audio.TTVideoAudio;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.MiniProcessMonitor;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BgAudioManagerServiceNative {
  private static BgAudioManagerServiceNative sInstance;
  
  public volatile BgAudioControlCell mCurrentBgAudioControlCell;
  
  public int mCurrentPlayAudioId = -1;
  
  private GetProgressTask mGetProgressTask = new GetProgressTask();
  
  public List<BgAudioPlayStateListener> mHostPlayStateListeners = new ArrayList<BgAudioPlayStateListener>();
  
  private AtomicInteger mId = new AtomicInteger(0);
  
  public boolean mIsRing;
  
  private boolean mKeepAlive = false;
  
  private AtomicBoolean mListeningPhoneState = new AtomicBoolean(false);
  
  private SparseArray<BgAudioControlCell> mMiniAppProcessBgAudios = new SparseArray();
  
  public final TTVideoAudio mTtVideoAudio = new TTVideoAudio(true, new AudioManager.BgSendMsgStateListener() {
        public void onSendMsgState(int param1Int, String param1String) {
          BgAudioManagerServiceNative.this.onEvent(param1Int, param1String);
        }
      });
  
  private BgAudioManagerServiceNative() {
    AppProcessManager.registerProcessLifeListener(new MiniProcessMonitor.ProcessLifeListener() {
          public void onAlive(AppProcessManager.ProcessInfo param1ProcessInfo) {}
          
          public void onDied(AppProcessManager.ProcessInfo param1ProcessInfo) {
            BgAudioManagerServiceNative.this.onProcessDied(param1ProcessInfo.mProcessName);
          }
        });
  }
  
  public static BgAudioManagerServiceNative getInst() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/audio/background/BgAudioManagerServiceNative.sInstance : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
    //   3: ifnonnull -> 31
    //   6: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   8: monitorenter
    //   9: new com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: putstatic com/tt/miniapp/audio/background/BgAudioManagerServiceNative.sInstance : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
    //   19: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   21: monitorexit
    //   22: goto -> 31
    //   25: astore_0
    //   26: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   28: monitorexit
    //   29: aload_0
    //   30: athrow
    //   31: getstatic com/tt/miniapp/audio/background/BgAudioManagerServiceNative.sInstance : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
    //   34: areturn
    // Exception table:
    //   from	to	target	type
    //   9	22	25	finally
    //   26	29	25	finally
  }
  
  private BgAudioControlCell getMiniAppProcessBgAudioModel(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mMiniAppProcessBgAudios : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast com/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell
    //   13: astore_2
    //   14: aload_2
    //   15: ifnull -> 22
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_2
    //   21: areturn
    //   22: new com/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell
    //   25: dup
    //   26: aload_0
    //   27: iload_1
    //   28: aconst_null
    //   29: invokespecial <init> : (Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;ILcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$1;)V
    //   32: astore_2
    //   33: aload_0
    //   34: getfield mMiniAppProcessBgAudios : Landroid/util/SparseArray;
    //   37: iload_1
    //   38: aload_2
    //   39: invokevirtual put : (ILjava/lang/Object;)V
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_2
    //   45: areturn
    //   46: astore_2
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_2
    //   50: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	46	finally
    //   22	42	46	finally
  }
  
  private void listenPhoneState() {
    if (!this.mListeningPhoneState.compareAndSet(false, true)) {
      AppBrandLogger.d("BgAudioManagerServiceNative", new Object[] { "isListeningPhoneState" });
      return;
    } 
    AppBrandLogger.d("BgAudioManagerServiceNative", new Object[] { "startListenPhoneState" });
    ThreadUtil.getThread(new Runnable() {
          public void run() {
            Looper.prepare();
            ((TelephonyManager)AppbrandContext.getInst().getApplicationContext().getSystemService("phone")).listen(new PhoneStateListener() {
                  public void onCallStateChanged(int param2Int, String param2String) {
                    if (param2Int != 0) {
                      if (param2Int == 1)
                        BgAudioManagerServiceNative.this.mIsRing = true; 
                    } else {
                      if (BgAudioManagerServiceNative.this.mCurrentPlayAudioId >= 0 && BgAudioManagerServiceNative.this.mIsRing) {
                        BgAudioManagerServiceNative.this.mTtVideoAudio.isTelPhoneRequestPlay = true;
                        BgAudioManagerServiceNative.this.mTtVideoAudio.telPhoneRequestPlayAudioId = BgAudioManagerServiceNative.this.mCurrentPlayAudioId;
                        BgAudioManagerServiceNative.this.mTtVideoAudio.play(BgAudioManagerServiceNative.this.mCurrentPlayAudioId, null);
                      } 
                      BgAudioManagerServiceNative.this.mIsRing = false;
                    } 
                    super.onCallStateChanged(param2Int, param2String);
                  }
                }32);
            Looper.loop();
          }
        }"BgListenerPhoneState").start();
  }
  
  public BgAudioState getAudioState(int paramInt) {
    AudioManager.AudioState audioState = this.mTtVideoAudio.getAudioState(paramInt, null);
    if (audioState == null)
      return null; 
    BgAudioState bgAudioState = new BgAudioState();
    bgAudioState.duration = (int)audioState.duration;
    bgAudioState.currentTime = (int)audioState.currentTime;
    bgAudioState.paused = audioState.paused;
    bgAudioState.bufferd = audioState.buffered;
    bgAudioState.volume = Math.round(audioState.volume);
    return bgAudioState;
  }
  
  public boolean needKeepAlive(int paramInt) {
    String str = getMiniAppProcessBgAudioModel(paramInt).getMiniAppProcessName();
    return (str == null) ? false : TextUtils.equals(AppProcessManager.playingBgAudioProcessName, str);
  }
  
  public int obtainManager(int paramInt, BgAudioCallExtra paramBgAudioCallExtra) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial listenPhoneState : ()V
    //   6: aload_0
    //   7: getfield mTtVideoAudio : Lcom/tt/miniapp/audio/TTVideoAudio;
    //   10: iload_1
    //   11: invokevirtual containAudioId : (I)Z
    //   14: istore_3
    //   15: iload_3
    //   16: ifeq -> 23
    //   19: aload_0
    //   20: monitorexit
    //   21: iload_1
    //   22: ireturn
    //   23: aload_0
    //   24: getfield mId : Ljava/util/concurrent/atomic/AtomicInteger;
    //   27: invokevirtual incrementAndGet : ()I
    //   30: istore_1
    //   31: aload_0
    //   32: iload_1
    //   33: invokespecial getMiniAppProcessBgAudioModel : (I)Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   36: aload_2
    //   37: putfield mBgAudioCallExtra : Lcom/tt/miniapp/audio/background/BgAudioCallExtra;
    //   40: aload_0
    //   41: monitorexit
    //   42: iload_1
    //   43: ireturn
    //   44: astore_2
    //   45: aload_0
    //   46: monitorexit
    //   47: aload_2
    //   48: athrow
    // Exception table:
    //   from	to	target	type
    //   2	15	44	finally
    //   23	40	44	finally
  }
  
  public void onEvent(int paramInt, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 'BgAudioManagerServiceNative'
    //   4: iconst_4
    //   5: anewarray java/lang/Object
    //   8: dup
    //   9: iconst_0
    //   10: ldc 'onEvent state'
    //   12: aastore
    //   13: dup
    //   14: iconst_1
    //   15: aload_2
    //   16: aastore
    //   17: dup
    //   18: iconst_2
    //   19: ldc 'audioId'
    //   21: aastore
    //   22: dup
    //   23: iconst_3
    //   24: iload_1
    //   25: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   28: aastore
    //   29: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   32: aload_0
    //   33: iload_1
    //   34: invokespecial getMiniAppProcessBgAudioModel : (I)Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   37: astore #4
    //   39: iconst_m1
    //   40: istore_3
    //   41: aload_2
    //   42: invokevirtual hashCode : ()I
    //   45: lookupswitch default -> 221, -906224361 -> 130, 3443508 -> 116, 550609668 -> 102, 1971820138 -> 88
    //   88: aload_2
    //   89: ldc 'seeking'
    //   91: invokevirtual equals : (Ljava/lang/Object;)Z
    //   94: ifeq -> 141
    //   97: iconst_2
    //   98: istore_3
    //   99: goto -> 141
    //   102: aload_2
    //   103: ldc 'canplay'
    //   105: invokevirtual equals : (Ljava/lang/Object;)Z
    //   108: ifeq -> 141
    //   111: iconst_1
    //   112: istore_3
    //   113: goto -> 141
    //   116: aload_2
    //   117: ldc 'play'
    //   119: invokevirtual equals : (Ljava/lang/Object;)Z
    //   122: ifeq -> 141
    //   125: iconst_0
    //   126: istore_3
    //   127: goto -> 141
    //   130: aload_2
    //   131: ldc 'seeked'
    //   133: invokevirtual equals : (Ljava/lang/Object;)Z
    //   136: ifeq -> 141
    //   139: iconst_3
    //   140: istore_3
    //   141: iload_3
    //   142: ifeq -> 181
    //   145: iload_3
    //   146: iconst_1
    //   147: if_icmpeq -> 207
    //   150: iload_3
    //   151: iconst_2
    //   152: if_icmpeq -> 207
    //   155: iload_3
    //   156: iconst_3
    //   157: if_icmpeq -> 207
    //   160: aload_0
    //   161: getfield mKeepAlive : Z
    //   164: ifne -> 171
    //   167: aconst_null
    //   168: putstatic com/tt/miniapp/process/AppProcessManager.playingBgAudioProcessName : Ljava/lang/String;
    //   171: aload_0
    //   172: getfield mGetProgressTask : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$GetProgressTask;
    //   175: invokevirtual stop : ()V
    //   178: goto -> 207
    //   181: aload_0
    //   182: iload_1
    //   183: putfield mCurrentPlayAudioId : I
    //   186: aload_0
    //   187: aload #4
    //   189: putfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   192: aload #4
    //   194: invokevirtual getMiniAppProcessName : ()Ljava/lang/String;
    //   197: putstatic com/tt/miniapp/process/AppProcessManager.playingBgAudioProcessName : Ljava/lang/String;
    //   200: aload_0
    //   201: getfield mGetProgressTask : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$GetProgressTask;
    //   204: invokevirtual start : ()V
    //   207: aload #4
    //   209: aload_2
    //   210: invokevirtual notifyStateChange : (Ljava/lang/String;)V
    //   213: aload_0
    //   214: monitorexit
    //   215: return
    //   216: astore_2
    //   217: aload_0
    //   218: monitorexit
    //   219: aload_2
    //   220: athrow
    //   221: goto -> 141
    // Exception table:
    //   from	to	target	type
    //   2	39	216	finally
    //   41	88	216	finally
    //   88	97	216	finally
    //   102	111	216	finally
    //   116	125	216	finally
    //   130	139	216	finally
    //   160	171	216	finally
    //   171	178	216	finally
    //   181	207	216	finally
    //   207	213	216	finally
  }
  
  public void onProcessDied(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 'BgAudioManagerServiceNative'
    //   4: iconst_2
    //   5: anewarray java/lang/Object
    //   8: dup
    //   9: iconst_0
    //   10: ldc_w 'onProcessDied processName:'
    //   13: aastore
    //   14: dup
    //   15: iconst_1
    //   16: aload_1
    //   17: aastore
    //   18: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   21: aload_0
    //   22: getfield mMiniAppProcessBgAudios : Landroid/util/SparseArray;
    //   25: invokevirtual size : ()I
    //   28: istore_3
    //   29: iconst_0
    //   30: istore_2
    //   31: iload_2
    //   32: iload_3
    //   33: if_icmpge -> 115
    //   36: aload_0
    //   37: getfield mMiniAppProcessBgAudios : Landroid/util/SparseArray;
    //   40: iload_2
    //   41: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   44: checkcast com/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell
    //   47: astore #4
    //   49: aload #4
    //   51: invokevirtual getMiniAppProcessName : ()Ljava/lang/String;
    //   54: aload_1
    //   55: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   58: ifeq -> 108
    //   61: ldc 'BgAudioManagerServiceNative'
    //   63: iconst_2
    //   64: anewarray java/lang/Object
    //   67: dup
    //   68: iconst_0
    //   69: ldc_w 'onBgPlayProcessDied processName:'
    //   72: aastore
    //   73: dup
    //   74: iconst_1
    //   75: aload_1
    //   76: aastore
    //   77: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   80: aload_0
    //   81: getfield mTtVideoAudio : Lcom/tt/miniapp/audio/TTVideoAudio;
    //   84: aload #4
    //   86: getfield mAudioId : I
    //   89: invokevirtual releaseBgAudio : (I)V
    //   92: aload #4
    //   94: invokevirtual notifyProgressDied : ()V
    //   97: aload_0
    //   98: getfield mMiniAppProcessBgAudios : Landroid/util/SparseArray;
    //   101: iload_2
    //   102: invokevirtual removeAt : (I)V
    //   105: aload_0
    //   106: monitorexit
    //   107: return
    //   108: iload_2
    //   109: iconst_1
    //   110: iadd
    //   111: istore_2
    //   112: goto -> 31
    //   115: aload_0
    //   116: monitorexit
    //   117: return
    //   118: astore_1
    //   119: aload_0
    //   120: monitorexit
    //   121: goto -> 126
    //   124: aload_1
    //   125: athrow
    //   126: goto -> 124
    // Exception table:
    //   from	to	target	type
    //   2	29	118	finally
    //   36	105	118	finally
  }
  
  public void openCurrentBgPlayMiniApp(String paramString1, String paramString2, String paramString3) {
    // Byte code:
    //   0: ldc 'BgAudioManagerServiceNative'
    //   2: iconst_1
    //   3: anewarray java/lang/Object
    //   6: dup
    //   7: iconst_0
    //   8: ldc_w 'openCurrentBgPlayMiniApp'
    //   11: aastore
    //   12: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   15: aload_0
    //   16: getfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   19: ifnonnull -> 38
    //   22: ldc 'BgAudioManagerServiceNative'
    //   24: iconst_1
    //   25: anewarray java/lang/Object
    //   28: dup
    //   29: iconst_0
    //   30: ldc_w 'mCurrentBgAudioControlCell == null'
    //   33: aastore
    //   34: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   37: return
    //   38: aload_0
    //   39: getfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   42: getfield mBgAudioCallExtra : Lcom/tt/miniapp/audio/background/BgAudioCallExtra;
    //   45: astore #5
    //   47: aconst_null
    //   48: astore #6
    //   50: aload #5
    //   52: ifnull -> 82
    //   55: aload_0
    //   56: getfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   59: getfield mBgAudioCallExtra : Lcom/tt/miniapp/audio/background/BgAudioCallExtra;
    //   62: getfield callAppId : Ljava/lang/String;
    //   65: astore #5
    //   67: aload_0
    //   68: getfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   71: getfield mBgAudioCallExtra : Lcom/tt/miniapp/audio/background/BgAudioCallExtra;
    //   74: getfield isGame : Z
    //   77: istore #4
    //   79: goto -> 88
    //   82: aconst_null
    //   83: astore #5
    //   85: iconst_0
    //   86: istore #4
    //   88: aload #5
    //   90: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   93: ifeq -> 119
    //   96: ldc 'BgAudioManagerServiceNative'
    //   98: iconst_2
    //   99: anewarray java/lang/Object
    //   102: dup
    //   103: iconst_0
    //   104: ldc_w 'TextUtils.isEmpty(appId) mCurrentBgAudioControlCell:'
    //   107: aastore
    //   108: dup
    //   109: iconst_1
    //   110: aload_0
    //   111: getfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   114: aastore
    //   115: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   118: return
    //   119: aload_0
    //   120: getfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   123: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
    //   126: ifnull -> 201
    //   129: aload_0
    //   130: getfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
    //   133: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
    //   136: getfield mAudioPage : Lorg/json/JSONObject;
    //   139: astore #8
    //   141: aload #8
    //   143: ifnull -> 201
    //   146: aload #8
    //   148: ldc_w 'path'
    //   151: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   154: astore #7
    //   156: aload #8
    //   158: ldc_w 'query'
    //   161: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   164: invokestatic getMapFromJson : (Ljava/lang/String;)Ljava/util/Map;
    //   167: astore #8
    //   169: aload #8
    //   171: astore #6
    //   173: goto -> 204
    //   176: astore #8
    //   178: ldc 'BgAudioManagerServiceNative'
    //   180: iconst_2
    //   181: anewarray java/lang/Object
    //   184: dup
    //   185: iconst_0
    //   186: ldc_w 'openCurrentBgPlayMiniApp'
    //   189: aastore
    //   190: dup
    //   191: iconst_1
    //   192: aload #8
    //   194: aastore
    //   195: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   198: goto -> 204
    //   201: aconst_null
    //   202: astore #7
    //   204: new com/tt/miniapphost/entity/MicroSchemaEntity$Builder
    //   207: dup
    //   208: invokespecial <init> : ()V
    //   211: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   214: invokevirtual getInitParams : ()Lcom/tt/miniapphost/entity/InitParamsEntity;
    //   217: sipush #1008
    //   220: ldc_w 'sslocal'
    //   223: invokevirtual getHostStr : (ILjava/lang/String;)Ljava/lang/String;
    //   226: invokevirtual protocol : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   229: astore #9
    //   231: iload #4
    //   233: ifeq -> 244
    //   236: getstatic com/tt/miniapphost/entity/MicroSchemaEntity$Host.MICROGAME : Lcom/tt/miniapphost/entity/MicroSchemaEntity$Host;
    //   239: astore #8
    //   241: goto -> 249
    //   244: getstatic com/tt/miniapphost/entity/MicroSchemaEntity$Host.MICROAPP : Lcom/tt/miniapphost/entity/MicroSchemaEntity$Host;
    //   247: astore #8
    //   249: aload #9
    //   251: aload #8
    //   253: invokevirtual host : (Lcom/tt/miniapphost/entity/MicroSchemaEntity$Host;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   256: aload #5
    //   258: invokevirtual appId : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   261: aload #7
    //   263: invokevirtual path : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   266: aload #6
    //   268: invokevirtual query : (Ljava/util/Map;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   271: aload_1
    //   272: invokevirtual scene : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   275: new com/tt/miniapp/audio/background/BgAudioManagerServiceNative$4
    //   278: dup
    //   279: aload_0
    //   280: aload_2
    //   281: aload_3
    //   282: invokespecial <init> : (Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;Ljava/lang/String;Ljava/lang/String;)V
    //   285: invokevirtual bdpLog : (Ljava/util/Map;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   288: invokevirtual build : ()Lcom/tt/miniapphost/entity/MicroSchemaEntity;
    //   291: invokevirtual toSchema : ()Ljava/lang/String;
    //   294: astore_1
    //   295: invokestatic inst : ()Lcom/tt/miniapphost/AppbrandSupport;
    //   298: aload_1
    //   299: invokevirtual openAppbrand : (Ljava/lang/String;)Z
    //   302: pop
    //   303: ldc 'BgAudioManagerServiceNative'
    //   305: iconst_2
    //   306: anewarray java/lang/Object
    //   309: dup
    //   310: iconst_0
    //   311: ldc_w 'openCurrentBgPlayMiniApp schema:'
    //   314: aastore
    //   315: dup
    //   316: iconst_1
    //   317: aload_1
    //   318: aastore
    //   319: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   322: return
    // Exception table:
    //   from	to	target	type
    //   156	169	176	java/lang/Exception
  }
  
  public void pause(int paramInt) {
    this.mCurrentPlayAudioId = -1;
    this.mTtVideoAudio.pause(paramInt, null);
  }
  
  public void play(int paramInt) {
    this.mTtVideoAudio.play(paramInt, null);
  }
  
  public boolean playNext() {
    if (this.mCurrentBgAudioControlCell == null) {
      AppBrandLogger.e("BgAudioManagerServiceNative", new Object[] { "playPrevious mCurrentBgAudioControlCell == null" });
      return false;
    } 
    BgAudioPlayStateListener bgAudioPlayStateListener = this.mCurrentBgAudioControlCell.mBgAudioPlayStateListener;
    if (bgAudioPlayStateListener != null) {
      bgAudioPlayStateListener.onPlayStateChange("next", null, true);
      return true;
    } 
    return false;
  }
  
  public boolean playPrevious() {
    if (this.mCurrentBgAudioControlCell == null) {
      AppBrandLogger.e("BgAudioManagerServiceNative", new Object[] { "playPrevious mCurrentBgAudioControlCell == null" });
      return false;
    } 
    BgAudioPlayStateListener bgAudioPlayStateListener = this.mCurrentBgAudioControlCell.mBgAudioPlayStateListener;
    if (bgAudioPlayStateListener != null) {
      bgAudioPlayStateListener.onPlayStateChange("prev", null, true);
      return true;
    } 
    return false;
  }
  
  public void register(int paramInt, BgAudioPlayStateListener paramBgAudioPlayStateListener) {
    AppBrandLogger.d("BgAudioManagerServiceNative", new Object[] { "register id:", Integer.valueOf(paramInt), "listener:", paramBgAudioPlayStateListener });
    (getMiniAppProcessBgAudioModel(paramInt)).mBgAudioPlayStateListener = paramBgAudioPlayStateListener;
  }
  
  public void registerHostPlayStateListener(BgAudioPlayStateListener paramBgAudioPlayStateListener) {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull -> 5
    //   4: return
    //   5: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   7: monitorenter
    //   8: aload_0
    //   9: getfield mHostPlayStateListeners : Ljava/util/List;
    //   12: aload_1
    //   13: invokeinterface add : (Ljava/lang/Object;)Z
    //   18: pop
    //   19: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   21: monitorexit
    //   22: return
    //   23: astore_1
    //   24: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   26: monitorexit
    //   27: aload_1
    //   28: athrow
    // Exception table:
    //   from	to	target	type
    //   8	22	23	finally
    //   24	27	23	finally
  }
  
  public void seek(int paramInt1, int paramInt2) {
    this.mTtVideoAudio.seek(paramInt1, paramInt2, null);
  }
  
  public void setAudioModel(int paramInt, BgAudioModel paramBgAudioModel) {
    AppBrandLogger.d("BgAudioManagerServiceNative", new Object[] { "setAudioModel model:", paramBgAudioModel });
    if (paramBgAudioModel == null)
      return; 
    getMiniAppProcessBgAudioModel(paramInt).updateBgAudioModel(paramBgAudioModel);
    AudioStateModule audioStateModule = new AudioStateModule();
    audioStateModule.audioId = paramInt;
    audioStateModule.src = paramBgAudioModel.src;
    this.mTtVideoAudio.stop(paramInt, null, true);
    this.mTtVideoAudio.releaseAudio(paramInt, null, true);
    audioStateModule.startTime = paramBgAudioModel.startTime;
    audioStateModule.autoplay = true;
    audioStateModule.loop = paramBgAudioModel.loop;
    audioStateModule.obeyMuteSwitch = paramBgAudioModel.obeyMuteSwitch;
    audioStateModule.volume = paramBgAudioModel.volume;
    audioStateModule.isBgAudio = true;
    audioStateModule.miniAppId = paramBgAudioModel.miniAppId;
    try {
      this.mTtVideoAudio.setAudioState(audioStateModule, null);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("BgAudioManagerServiceNative", new Object[] { "", exception });
      return;
    } 
  }
  
  public void setKeepAlive(boolean paramBoolean) {
    this.mKeepAlive = paramBoolean;
    if (!this.mKeepAlive && !this.mTtVideoAudio.isPlaying(this.mCurrentPlayAudioId))
      AppProcessManager.playingBgAudioProcessName = null; 
  }
  
  public void stop(int paramInt) {
    this.mCurrentPlayAudioId = -1;
    this.mTtVideoAudio.stop(paramInt, null);
  }
  
  public boolean stopCurrentBg() {
    if (this.mCurrentBgAudioControlCell == null)
      return false; 
    stop(this.mCurrentBgAudioControlCell.mAudioId);
    return true;
  }
  
  public boolean switchCurrentBgPlayState() {
    if (this.mCurrentBgAudioControlCell == null)
      return false; 
    int i = this.mCurrentBgAudioControlCell.mAudioId;
    if (this.mTtVideoAudio.isPlaying(i)) {
      pause(i);
    } else {
      play(i);
    } 
    return true;
  }
  
  public void unregisterHostPlayStateListener(BgAudioPlayStateListener paramBgAudioPlayStateListener) {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull -> 5
    //   4: return
    //   5: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   7: monitorenter
    //   8: aload_0
    //   9: getfield mHostPlayStateListeners : Ljava/util/List;
    //   12: aload_1
    //   13: invokeinterface remove : (Ljava/lang/Object;)Z
    //   18: pop
    //   19: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   21: monitorexit
    //   22: return
    //   23: astore_1
    //   24: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
    //   26: monitorexit
    //   27: aload_1
    //   28: athrow
    // Exception table:
    //   from	to	target	type
    //   8	22	23	finally
    //   24	27	23	finally
  }
  
  class BgAudioControlCell {
    public final int mAudioId;
    
    public BgAudioCallExtra mBgAudioCallExtra;
    
    public BgAudioModel mBgAudioModel;
    
    public BgAudioPlayStateListener mBgAudioPlayStateListener;
    
    private BgAudioControlCell(int param1Int) {
      this.mAudioId = param1Int;
    }
    
    public String getMiniAppProcessName() {
      BgAudioCallExtra bgAudioCallExtra = this.mBgAudioCallExtra;
      return (bgAudioCallExtra != null) ? bgAudioCallExtra.callProcessName : null;
    }
    
    public void notifyProgressChange(int param1Int) {
      // Byte code:
      //   0: aload_0
      //   1: getfield mBgAudioPlayStateListener : Lcom/tt/miniapp/audio/background/BgAudioPlayStateListener;
      //   4: astore #4
      //   6: aload #4
      //   8: ifnull -> 23
      //   11: aload #4
      //   13: iload_1
      //   14: aload_0
      //   15: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
      //   18: invokeinterface onProgressChange : (ILcom/tt/miniapp/audio/background/BgAudioModel;)V
      //   23: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   25: monitorenter
      //   26: aload_0
      //   27: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   30: getfield mHostPlayStateListeners : Ljava/util/List;
      //   33: invokeinterface size : ()I
      //   38: istore_3
      //   39: iconst_0
      //   40: istore_2
      //   41: iload_2
      //   42: iload_3
      //   43: if_icmpge -> 79
      //   46: aload_0
      //   47: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   50: getfield mHostPlayStateListeners : Ljava/util/List;
      //   53: iload_2
      //   54: invokeinterface get : (I)Ljava/lang/Object;
      //   59: checkcast com/tt/miniapp/audio/background/BgAudioPlayStateListener
      //   62: iload_1
      //   63: aload_0
      //   64: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
      //   67: invokeinterface onProgressChange : (ILcom/tt/miniapp/audio/background/BgAudioModel;)V
      //   72: iload_2
      //   73: iconst_1
      //   74: iadd
      //   75: istore_2
      //   76: goto -> 41
      //   79: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   81: monitorexit
      //   82: return
      //   83: astore #4
      //   85: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   87: monitorexit
      //   88: goto -> 94
      //   91: aload #4
      //   93: athrow
      //   94: goto -> 91
      // Exception table:
      //   from	to	target	type
      //   26	39	83	finally
      //   46	72	83	finally
      //   79	82	83	finally
      //   85	88	83	finally
    }
    
    public void notifyProgressDied() {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   4: getfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
      //   7: astore #4
      //   9: iconst_0
      //   10: istore_1
      //   11: aload #4
      //   13: aload_0
      //   14: if_acmpne -> 30
      //   17: aload_0
      //   18: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   21: aconst_null
      //   22: putfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
      //   25: iconst_1
      //   26: istore_3
      //   27: goto -> 32
      //   30: iconst_0
      //   31: istore_3
      //   32: aload_0
      //   33: getfield mBgAudioPlayStateListener : Lcom/tt/miniapp/audio/background/BgAudioPlayStateListener;
      //   36: astore #4
      //   38: aload #4
      //   40: ifnull -> 55
      //   43: aload #4
      //   45: aload_0
      //   46: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
      //   49: iload_3
      //   50: invokeinterface onBgPlayProcessDied : (Lcom/tt/miniapp/audio/background/BgAudioModel;Z)V
      //   55: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   57: monitorenter
      //   58: aload_0
      //   59: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   62: getfield mHostPlayStateListeners : Ljava/util/List;
      //   65: invokeinterface size : ()I
      //   70: istore_2
      //   71: iload_1
      //   72: iload_2
      //   73: if_icmpge -> 109
      //   76: aload_0
      //   77: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   80: getfield mHostPlayStateListeners : Ljava/util/List;
      //   83: iload_1
      //   84: invokeinterface get : (I)Ljava/lang/Object;
      //   89: checkcast com/tt/miniapp/audio/background/BgAudioPlayStateListener
      //   92: aload_0
      //   93: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
      //   96: iload_3
      //   97: invokeinterface onBgPlayProcessDied : (Lcom/tt/miniapp/audio/background/BgAudioModel;Z)V
      //   102: iload_1
      //   103: iconst_1
      //   104: iadd
      //   105: istore_1
      //   106: goto -> 71
      //   109: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   111: monitorexit
      //   112: return
      //   113: astore #4
      //   115: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   117: monitorexit
      //   118: goto -> 124
      //   121: aload #4
      //   123: athrow
      //   124: goto -> 121
      // Exception table:
      //   from	to	target	type
      //   58	71	113	finally
      //   76	102	113	finally
      //   109	112	113	finally
      //   115	118	113	finally
    }
    
    public void notifyStateChange(String param1String) {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   4: getfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
      //   7: astore #5
      //   9: iconst_0
      //   10: istore_2
      //   11: aload #5
      //   13: aload_0
      //   14: if_acmpne -> 23
      //   17: iconst_1
      //   18: istore #4
      //   20: goto -> 26
      //   23: iconst_0
      //   24: istore #4
      //   26: aload_0
      //   27: getfield mBgAudioPlayStateListener : Lcom/tt/miniapp/audio/background/BgAudioPlayStateListener;
      //   30: astore #5
      //   32: aload #5
      //   34: ifnull -> 51
      //   37: aload #5
      //   39: aload_1
      //   40: aload_0
      //   41: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
      //   44: iload #4
      //   46: invokeinterface onPlayStateChange : (Ljava/lang/String;Lcom/tt/miniapp/audio/background/BgAudioModel;Z)V
      //   51: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   53: monitorenter
      //   54: aload_0
      //   55: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   58: getfield mHostPlayStateListeners : Ljava/util/List;
      //   61: invokeinterface size : ()I
      //   66: istore_3
      //   67: iload_2
      //   68: iload_3
      //   69: if_icmpge -> 107
      //   72: aload_0
      //   73: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   76: getfield mHostPlayStateListeners : Ljava/util/List;
      //   79: iload_2
      //   80: invokeinterface get : (I)Ljava/lang/Object;
      //   85: checkcast com/tt/miniapp/audio/background/BgAudioPlayStateListener
      //   88: aload_1
      //   89: aload_0
      //   90: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
      //   93: iload #4
      //   95: invokeinterface onPlayStateChange : (Ljava/lang/String;Lcom/tt/miniapp/audio/background/BgAudioModel;Z)V
      //   100: iload_2
      //   101: iconst_1
      //   102: iadd
      //   103: istore_2
      //   104: goto -> 67
      //   107: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   109: monitorexit
      //   110: return
      //   111: astore_1
      //   112: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   114: monitorexit
      //   115: goto -> 120
      //   118: aload_1
      //   119: athrow
      //   120: goto -> 118
      // Exception table:
      //   from	to	target	type
      //   54	67	111	finally
      //   72	100	111	finally
      //   107	110	111	finally
      //   112	115	111	finally
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{mAudioId: ");
      stringBuilder.append(this.mAudioId);
      stringBuilder.append(", mBgAudioCallExtra: ");
      stringBuilder.append(this.mBgAudioCallExtra);
      stringBuilder.append(", mBgAudioModel: ");
      stringBuilder.append(this.mBgAudioModel);
      stringBuilder.append(", mBgAudioPlayStateListener:");
      stringBuilder.append(this.mBgAudioPlayStateListener);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
    
    public void updateBgAudioModel(BgAudioModel param1BgAudioModel) {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: putfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
      //   5: aload_0
      //   6: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   9: aload_0
      //   10: putfield mCurrentBgAudioControlCell : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative$BgAudioControlCell;
      //   13: aload_0
      //   14: getfield mBgAudioPlayStateListener : Lcom/tt/miniapp/audio/background/BgAudioPlayStateListener;
      //   17: astore_1
      //   18: aload_1
      //   19: ifnull -> 32
      //   22: aload_1
      //   23: aload_0
      //   24: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
      //   27: invokeinterface onTriggerPlay : (Lcom/tt/miniapp/audio/background/BgAudioModel;)V
      //   32: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   34: monitorenter
      //   35: aload_0
      //   36: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   39: getfield mHostPlayStateListeners : Ljava/util/List;
      //   42: invokeinterface size : ()I
      //   47: istore_3
      //   48: iconst_0
      //   49: istore_2
      //   50: iload_2
      //   51: iload_3
      //   52: if_icmpge -> 87
      //   55: aload_0
      //   56: getfield this$0 : Lcom/tt/miniapp/audio/background/BgAudioManagerServiceNative;
      //   59: getfield mHostPlayStateListeners : Ljava/util/List;
      //   62: iload_2
      //   63: invokeinterface get : (I)Ljava/lang/Object;
      //   68: checkcast com/tt/miniapp/audio/background/BgAudioPlayStateListener
      //   71: aload_0
      //   72: getfield mBgAudioModel : Lcom/tt/miniapp/audio/background/BgAudioModel;
      //   75: invokeinterface onTriggerPlay : (Lcom/tt/miniapp/audio/background/BgAudioModel;)V
      //   80: iload_2
      //   81: iconst_1
      //   82: iadd
      //   83: istore_2
      //   84: goto -> 50
      //   87: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   89: monitorexit
      //   90: return
      //   91: astore_1
      //   92: ldc com/tt/miniapp/audio/background/BgAudioManagerServiceNative
      //   94: monitorexit
      //   95: goto -> 100
      //   98: aload_1
      //   99: athrow
      //   100: goto -> 98
      // Exception table:
      //   from	to	target	type
      //   35	48	91	finally
      //   55	80	91	finally
      //   87	90	91	finally
      //   92	95	91	finally
    }
  }
  
  class GetProgressTask implements Runnable {
    private GetProgressTask() {}
    
    public void run() {
      if (BgAudioManagerServiceNative.this.mCurrentBgAudioControlCell == null) {
        ThreadUtil.runOnUIThread(this, 1000L);
        return;
      } 
      AudioManager.AudioState audioState = BgAudioManagerServiceNative.this.mTtVideoAudio.getAudioState(BgAudioManagerServiceNative.this.mCurrentBgAudioControlCell.mAudioId, null);
      if (audioState == null || audioState.duration == 0L) {
        ThreadUtil.runOnUIThread(this, 1000L);
        return;
      } 
      int j = (int)(audioState.currentTime * 100L / audioState.duration);
      int i = j;
      if (j > 100)
        i = 100; 
      BgAudioManagerServiceNative.this.mCurrentBgAudioControlCell.notifyProgressChange(i);
      ThreadUtil.runOnUIThread(this, 1000L);
    }
    
    public void start() {
      ThreadUtil.cancelUIRunnable(this);
      ThreadUtil.runOnUIThread(this);
    }
    
    public void stop() {
      ThreadUtil.cancelUIRunnable(this);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\background\BgAudioManagerServiceNative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */