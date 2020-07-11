package com.tt.miniapp.process;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.audio.background.BgAudioManagerClient;
import com.tt.miniapp.entity.AppJumpListManager;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.process.bridge.InnerMiniAppProcessBridge;
import com.tt.miniapp.thread.HandlerThreadUtil;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.AppbrandSupport;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.dynamic.IProcessManager;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.placeholder.MiniappService0;
import com.tt.miniapphost.placeholder.MiniappService1;
import com.tt.miniapphost.placeholder.MiniappService2;
import com.tt.miniapphost.placeholder.MiniappService200;
import com.tt.miniapphost.placeholder.MiniappService3;
import com.tt.miniapphost.placeholder.MiniappService4;
import com.tt.miniapphost.placeholder.MiniappTabActivity0;
import com.tt.miniapphost.placeholder.MiniappTabActivity1;
import com.tt.miniapphost.placeholder.MiniappTabActivity2;
import com.tt.miniapphost.placeholder.MiniappTabActivity3;
import com.tt.miniapphost.placeholder.MiniappTabActivity4;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleActivity0;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleActivity1;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleActivity2;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleActivity3;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleActivity4;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleHostStackActivity0;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleHostStackActivity1;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleHostStackActivity2;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleHostStackActivity3;
import com.tt.miniapphost.placeholder.MiniappTabFloatStyleHostStackActivity4;
import com.tt.miniapphost.placeholder.MiniappTabHostStackActivity0;
import com.tt.miniapphost.placeholder.MiniappTabHostStackActivity1;
import com.tt.miniapphost.placeholder.MiniappTabHostStackActivity2;
import com.tt.miniapphost.placeholder.MiniappTabHostStackActivity3;
import com.tt.miniapphost.placeholder.MiniappTabHostStackActivity4;
import com.tt.miniapphost.util.ProcessUtil;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONObject;

public class AppProcessManager implements IProcessManager {
  private static String killingAppId;
  
  public static MiniProcessMonitor.ProcessLifeListener mInnerProcessLifeListener;
  
  private static volatile Handler mProcessHandler;
  
  public static String playingBgAudioProcessName;
  
  public static final Object sKillProcessLock;
  
  public static List<MiniProcessMonitor.ProcessLifeListener> sProcessLifeListenerList = new CopyOnWriteArrayList<MiniProcessMonitor.ProcessLifeListener>();
  
  private static ProcessInfo[] sProcessList;
  
  private static ProcessInfo sUcProcess;
  
  private boolean mPreloadedEmptyProcess;
  
  static {
    sKillProcessLock = new Object();
    mInnerProcessLifeListener = new MiniProcessMonitor.ProcessLifeListener() {
        public final void onAlive(AppProcessManager.ProcessInfo param1ProcessInfo) {
          // Byte code:
          //   0: ldc 'AppProcessManager'
          //   2: iconst_2
          //   3: anewarray java/lang/Object
          //   6: dup
          //   7: iconst_0
          //   8: ldc '小程序进程启动：'
          //   10: aastore
          //   11: dup
          //   12: iconst_1
          //   13: aload_1
          //   14: getfield mProcessName : Ljava/lang/String;
          //   17: aastore
          //   18: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
          //   21: ldc com/tt/miniapp/process/MiniProcessMonitor
          //   23: monitorenter
          //   24: getstatic com/tt/miniapp/process/AppProcessManager.sProcessLifeListenerList : Ljava/util/List;
          //   27: invokeinterface iterator : ()Ljava/util/Iterator;
          //   32: astore_2
          //   33: aload_2
          //   34: invokeinterface hasNext : ()Z
          //   39: ifeq -> 66
          //   42: aload_2
          //   43: invokeinterface next : ()Ljava/lang/Object;
          //   48: checkcast com/tt/miniapp/process/MiniProcessMonitor$ProcessLifeListener
          //   51: astore_3
          //   52: aload_3
          //   53: ifnull -> 33
          //   56: aload_3
          //   57: aload_1
          //   58: invokeinterface onAlive : (Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;)V
          //   63: goto -> 33
          //   66: ldc com/tt/miniapp/process/MiniProcessMonitor
          //   68: monitorexit
          //   69: return
          //   70: astore_1
          //   71: ldc com/tt/miniapp/process/MiniProcessMonitor
          //   73: monitorexit
          //   74: aload_1
          //   75: athrow
          //   76: astore_1
          //   77: new java/lang/StringBuilder
          //   80: dup
          //   81: invokespecial <init> : ()V
          //   84: astore_2
          //   85: aload_2
          //   86: aload_1
          //   87: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
          //   90: pop
          //   91: ldc 'AppProcessManager'
          //   93: iconst_1
          //   94: anewarray java/lang/Object
          //   97: dup
          //   98: iconst_0
          //   99: aload_2
          //   100: invokevirtual toString : ()Ljava/lang/String;
          //   103: aastore
          //   104: invokestatic outputError : (Ljava/lang/String;[Ljava/lang/Object;)V
          //   107: return
          // Exception table:
          //   from	to	target	type
          //   0	24	76	java/lang/Exception
          //   24	33	70	finally
          //   33	52	70	finally
          //   56	63	70	finally
          //   66	69	70	finally
          //   71	74	70	finally
          //   74	76	76	java/lang/Exception
        }
        
        public final void onDied(AppProcessManager.ProcessInfo param1ProcessInfo) {
          // Byte code:
          //   0: ldc 'AppProcessManager'
          //   2: iconst_2
          //   3: anewarray java/lang/Object
          //   6: dup
          //   7: iconst_0
          //   8: ldc '小程序进程死亡：'
          //   10: aastore
          //   11: dup
          //   12: iconst_1
          //   13: aload_1
          //   14: getfield mProcessName : Ljava/lang/String;
          //   17: aastore
          //   18: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
          //   21: aload_1
          //   22: getfield mAppId : Ljava/lang/String;
          //   25: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
          //   28: ifeq -> 45
          //   31: aload_1
          //   32: ldc 'preload'
          //   34: sipush #9200
          //   37: ldc 'monitor'
          //   39: invokestatic reportProcessStatus : (Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;Ljava/lang/String;ILjava/lang/String;)V
          //   42: invokestatic delayCheckIfPreProcessAutoStart : ()V
          //   45: ldc com/tt/miniapp/process/MiniProcessMonitor
          //   47: monitorenter
          //   48: getstatic com/tt/miniapp/process/AppProcessManager.sProcessLifeListenerList : Ljava/util/List;
          //   51: invokeinterface iterator : ()Ljava/util/Iterator;
          //   56: astore_2
          //   57: aload_2
          //   58: invokeinterface hasNext : ()Z
          //   63: ifeq -> 90
          //   66: aload_2
          //   67: invokeinterface next : ()Ljava/lang/Object;
          //   72: checkcast com/tt/miniapp/process/MiniProcessMonitor$ProcessLifeListener
          //   75: astore_3
          //   76: aload_3
          //   77: ifnull -> 57
          //   80: aload_3
          //   81: aload_1
          //   82: invokeinterface onDied : (Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;)V
          //   87: goto -> 57
          //   90: ldc com/tt/miniapp/process/MiniProcessMonitor
          //   92: monitorexit
          //   93: aload_1
          //   94: invokestatic notifyUpdateAppJumpList : (Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;)V
          //   97: ldc 'AppProcessManager'
          //   99: iconst_2
          //   100: anewarray java/lang/Object
          //   103: dup
          //   104: iconst_0
          //   105: ldc '小程序进程死亡，重置小程序进程信息 processInfo：'
          //   107: aastore
          //   108: dup
          //   109: iconst_1
          //   110: aload_1
          //   111: aastore
          //   112: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
          //   115: aload_1
          //   116: invokevirtual reset : ()V
          //   119: return
          //   120: astore_1
          //   121: ldc com/tt/miniapp/process/MiniProcessMonitor
          //   123: monitorexit
          //   124: aload_1
          //   125: athrow
          //   126: astore_1
          //   127: new java/lang/StringBuilder
          //   130: dup
          //   131: invokespecial <init> : ()V
          //   134: astore_2
          //   135: aload_2
          //   136: aload_1
          //   137: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
          //   140: pop
          //   141: ldc 'AppProcessManager'
          //   143: iconst_1
          //   144: anewarray java/lang/Object
          //   147: dup
          //   148: iconst_0
          //   149: aload_2
          //   150: invokevirtual toString : ()Ljava/lang/String;
          //   153: aastore
          //   154: invokestatic outputError : (Ljava/lang/String;[Ljava/lang/Object;)V
          //   157: return
          // Exception table:
          //   from	to	target	type
          //   0	45	126	java/lang/Exception
          //   45	48	126	java/lang/Exception
          //   48	57	120	finally
          //   57	76	120	finally
          //   80	87	120	finally
          //   90	93	120	finally
          //   93	119	126	java/lang/Exception
          //   121	124	120	finally
          //   124	126	126	java/lang/Exception
        }
      };
    sProcessList = new ProcessInfo[] { new ProcessInfo(0, "miniapp0", ":miniapp0", MiniappTabActivity0.class, MiniappTabFloatStyleActivity0.class, MiniappTabHostStackActivity0.class, MiniappTabFloatStyleHostStackActivity0.class, MiniappService0.class), new ProcessInfo(1, "miniapp1", ":miniapp1", MiniappTabActivity1.class, MiniappTabFloatStyleActivity1.class, MiniappTabHostStackActivity1.class, MiniappTabFloatStyleHostStackActivity1.class, MiniappService1.class), new ProcessInfo(2, "miniapp2", ":miniapp2", MiniappTabActivity2.class, MiniappTabFloatStyleActivity2.class, MiniappTabHostStackActivity2.class, MiniappTabFloatStyleHostStackActivity2.class, MiniappService2.class), new ProcessInfo(3, "miniapp3", ":miniapp3", MiniappTabActivity3.class, MiniappTabFloatStyleActivity3.class, MiniappTabHostStackActivity3.class, MiniappTabFloatStyleHostStackActivity3.class, MiniappService3.class), new ProcessInfo(4, "miniapp4", ":miniapp4", MiniappTabActivity4.class, MiniappTabFloatStyleActivity4.class, MiniappTabHostStackActivity4.class, MiniappTabFloatStyleHostStackActivity4.class, MiniappService4.class) };
    sUcProcess = new ProcessInfo(200, "miniapp200", ":miniapp200", null, null, null, null, MiniappService200.class);
  }
  
  public static void delayCheckIfPreProcessAutoStart() {
    getProcessHandler().removeMessages(2);
    getProcessHandler().sendEmptyMessageDelayed(2, 21000L);
  }
  
  public static void finishServiceSticky(Context paramContext, Class paramClass) {
    if (paramClass == null) {
      AppBrandLogger.d("AppProcessManager", new Object[] { "finishServiceSticky serviceClass == null" });
      return;
    } 
    try {
      Intent intent = new Intent(paramContext, paramClass);
      intent.putExtra("command", "finishSticky");
      _lancet.com_ss_android_ugc_aweme_push_downgrade_StartServiceLancet_startService(paramContext, intent);
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "AppProcessManager", exception.getStackTrace());
      return;
    } 
  }
  
  private static ProcessInfo getAvailableProcessInfo(Context paramContext) {
    List<ActivityManager.RunningAppProcessInfo> list = getRunningAppProcessInfoList(paramContext);
    for (ProcessInfo processInfo : sProcessList) {
      if (!processInfo.isUsing(list))
        return processInfo; 
    } 
    return null;
  }
  
  public static Class getCurrentMiniAppProcessServiceClass() {
    return getMiniAppProcessServiceClass(ProcessUtil.getProcessIdentify());
  }
  
  public static ProcessInfo getLatestUsingHostStackProcessInfo() {
    ProcessInfo[] arrayOfProcessInfo = sProcessList;
    ProcessInfo processInfo = null;
    if (arrayOfProcessInfo == null)
      return null; 
    int j = arrayOfProcessInfo.length;
    int i = 0;
    for (long l = 0L; i < j; l = l1) {
      ProcessInfo processInfo2 = arrayOfProcessInfo[i];
      ProcessInfo processInfo1 = processInfo;
      long l1 = l;
      if (processInfo2.isLaunchActivityInHostStack()) {
        processInfo1 = processInfo;
        l1 = l;
        if (processInfo2.mUseTime > l) {
          processInfo1 = processInfo;
          l1 = l;
          if (processInfo2.isUsing()) {
            l1 = processInfo2.mUseTime;
            processInfo1 = processInfo2;
          } 
        } 
      } 
      i++;
      processInfo = processInfo1;
    } 
    return processInfo;
  }
  
  public static LaunchInfo getLaunchClass(Context paramContext, AppInfoEntity paramAppInfoEntity, LaunchConfig paramLaunchConfig) {
    // Byte code:
    //   0: ldc com/tt/miniapp/process/AppProcessManager
    //   2: monitorenter
    //   3: aload_1
    //   4: getfield appId : Ljava/lang/String;
    //   7: astore #6
    //   9: aload_1
    //   10: getfield versionType : Ljava/lang/String;
    //   13: astore #7
    //   15: ldc 'AppProcessManager'
    //   17: iconst_4
    //   18: anewarray java/lang/Object
    //   21: dup
    //   22: iconst_0
    //   23: ldc 'getLaunchClass appInfo:'
    //   25: aastore
    //   26: dup
    //   27: iconst_1
    //   28: aload_1
    //   29: aastore
    //   30: dup
    //   31: iconst_2
    //   32: ldc 'isInHostStack:'
    //   34: aastore
    //   35: dup
    //   36: iconst_3
    //   37: aload_2
    //   38: getfield mIsInHostStack : Z
    //   41: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   44: aastore
    //   45: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   48: aload #6
    //   50: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   53: istore #5
    //   55: iload #5
    //   57: ifeq -> 65
    //   60: ldc com/tt/miniapp/process/AppProcessManager
    //   62: monitorexit
    //   63: aconst_null
    //   64: areturn
    //   65: aload_0
    //   66: invokestatic getRunningAppProcessInfoList : (Landroid/content/Context;)Ljava/util/List;
    //   69: astore #8
    //   71: getstatic com/tt/miniapp/process/AppProcessManager.sProcessList : [Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   74: astore #9
    //   76: aload #9
    //   78: arraylength
    //   79: istore #4
    //   81: iconst_0
    //   82: istore_3
    //   83: iload_3
    //   84: iload #4
    //   86: if_icmpge -> 349
    //   89: aload #9
    //   91: iload_3
    //   92: aaload
    //   93: astore #10
    //   95: ldc 'AppProcessManager'
    //   97: bipush #6
    //   99: anewarray java/lang/Object
    //   102: dup
    //   103: iconst_0
    //   104: ldc_w 'getLaunchClass checkExist appId:'
    //   107: aastore
    //   108: dup
    //   109: iconst_1
    //   110: aload #6
    //   112: aastore
    //   113: dup
    //   114: iconst_2
    //   115: ldc_w 'processInfo.mAppId:'
    //   118: aastore
    //   119: dup
    //   120: iconst_3
    //   121: aload #10
    //   123: getfield mAppId : Ljava/lang/String;
    //   126: aastore
    //   127: dup
    //   128: iconst_4
    //   129: ldc_w 'processInfo.mVersionType:'
    //   132: aastore
    //   133: dup
    //   134: iconst_5
    //   135: aload #10
    //   137: getfield mVersionType : Ljava/lang/String;
    //   140: aastore
    //   141: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   144: aload #6
    //   146: aload #10
    //   148: getfield mAppId : Ljava/lang/String;
    //   151: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   154: ifeq -> 550
    //   157: aload #10
    //   159: aload #8
    //   161: invokevirtual isUsing : (Ljava/util/List;)Z
    //   164: ifne -> 190
    //   167: ldc 'AppProcessManager'
    //   169: iconst_2
    //   170: anewarray java/lang/Object
    //   173: dup
    //   174: iconst_0
    //   175: ldc_w 'isAppProcessAvailable !isUsing. processInfo: '
    //   178: aastore
    //   179: dup
    //   180: iconst_1
    //   181: aload #10
    //   183: aastore
    //   184: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   187: goto -> 550
    //   190: ldc 'AppProcessManager'
    //   192: iconst_2
    //   193: anewarray java/lang/Object
    //   196: dup
    //   197: iconst_0
    //   198: ldc_w 'getLaunchClass launch app exists. processInfo: '
    //   201: aastore
    //   202: dup
    //   203: iconst_1
    //   204: aload #10
    //   206: aastore
    //   207: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   210: aload_2
    //   211: getfield mForceColdBoot : Z
    //   214: istore #5
    //   216: aload_1
    //   217: aload #10
    //   219: getfield mVersionType : Ljava/lang/String;
    //   222: invokevirtual shouldHotLaunch : (Ljava/lang/String;)Z
    //   225: ifne -> 246
    //   228: ldc 'AppProcessManager'
    //   230: iconst_1
    //   231: anewarray java/lang/Object
    //   234: dup
    //   235: iconst_0
    //   236: ldc_w 'getLaunchClass forceKillProcess'
    //   239: aastore
    //   240: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   243: iconst_1
    //   244: istore #5
    //   246: iload #5
    //   248: ifne -> 289
    //   251: ldc 'AppProcessManager'
    //   253: iconst_2
    //   254: anewarray java/lang/Object
    //   257: dup
    //   258: iconst_0
    //   259: ldc_w 'getLaunchClass process is available. processInfo: '
    //   262: aastore
    //   263: dup
    //   264: iconst_1
    //   265: aload #10
    //   267: aastore
    //   268: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   271: new com/tt/miniapp/process/AppProcessManager$LaunchInfo
    //   274: dup
    //   275: aload #10
    //   277: aload_2
    //   278: iconst_0
    //   279: aconst_null
    //   280: invokespecial <init> : (Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;Lcom/tt/miniapp/process/AppProcessManager$LaunchConfig;ZLcom/tt/miniapp/process/AppProcessManager$1;)V
    //   283: astore_0
    //   284: ldc com/tt/miniapp/process/AppProcessManager
    //   286: monitorexit
    //   287: aload_0
    //   288: areturn
    //   289: ldc 'AppProcessManager'
    //   291: iconst_2
    //   292: anewarray java/lang/Object
    //   295: dup
    //   296: iconst_0
    //   297: ldc_w 'getLaunchClass checkExistedApp kill process. processInfo: '
    //   300: aastore
    //   301: dup
    //   302: iconst_1
    //   303: aload #10
    //   305: aastore
    //   306: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   309: aload #10
    //   311: invokevirtual isLaunchActivityInHostStack : ()Z
    //   314: ifeq -> 325
    //   317: aload #6
    //   319: aconst_null
    //   320: iconst_0
    //   321: invokestatic backToApp : (Ljava/lang/String;Ljava/lang/String;Z)Z
    //   324: pop
    //   325: invokestatic isUIThread : ()Z
    //   328: ifeq -> 340
    //   331: aload_0
    //   332: aload #10
    //   334: invokestatic killProcessOnUIThread : (Landroid/content/Context;Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;)V
    //   337: goto -> 349
    //   340: aload_0
    //   341: aload #10
    //   343: invokestatic killProcess : (Landroid/content/Context;Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;)V
    //   346: goto -> 349
    //   349: aload_2
    //   350: getfield mUsePreloadProcess : Z
    //   353: ifeq -> 408
    //   356: aload_0
    //   357: invokestatic getPreloadProcessInfo : (Landroid/content/Context;)Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   360: astore_1
    //   361: aload_1
    //   362: ifnull -> 408
    //   365: ldc 'AppProcessManager'
    //   367: iconst_2
    //   368: anewarray java/lang/Object
    //   371: dup
    //   372: iconst_0
    //   373: ldc_w 'getLaunchClass use preloadProcessInfo. processInfo: '
    //   376: aastore
    //   377: dup
    //   378: iconst_1
    //   379: aload_1
    //   380: aastore
    //   381: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   384: aload_1
    //   385: aload #6
    //   387: aload #7
    //   389: invokevirtual reuseInfo : (Ljava/lang/String;Ljava/lang/String;)V
    //   392: new com/tt/miniapp/process/AppProcessManager$LaunchInfo
    //   395: dup
    //   396: aload_1
    //   397: aload_2
    //   398: aconst_null
    //   399: invokespecial <init> : (Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;Lcom/tt/miniapp/process/AppProcessManager$LaunchConfig;Lcom/tt/miniapp/process/AppProcessManager$1;)V
    //   402: astore_0
    //   403: ldc com/tt/miniapp/process/AppProcessManager
    //   405: monitorexit
    //   406: aload_0
    //   407: areturn
    //   408: aload_0
    //   409: invokestatic getAvailableProcessInfo : (Landroid/content/Context;)Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   412: astore_1
    //   413: aload_1
    //   414: ifnull -> 460
    //   417: ldc 'AppProcessManager'
    //   419: iconst_2
    //   420: anewarray java/lang/Object
    //   423: dup
    //   424: iconst_0
    //   425: ldc_w 'getLaunchClass use availableProcessInfo: '
    //   428: aastore
    //   429: dup
    //   430: iconst_1
    //   431: aload_1
    //   432: aastore
    //   433: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   436: aload_1
    //   437: aload #6
    //   439: aload #7
    //   441: invokevirtual reuseInfo : (Ljava/lang/String;Ljava/lang/String;)V
    //   444: new com/tt/miniapp/process/AppProcessManager$LaunchInfo
    //   447: dup
    //   448: aload_1
    //   449: aload_2
    //   450: aconst_null
    //   451: invokespecial <init> : (Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;Lcom/tt/miniapp/process/AppProcessManager$LaunchConfig;Lcom/tt/miniapp/process/AppProcessManager$1;)V
    //   454: astore_0
    //   455: ldc com/tt/miniapp/process/AppProcessManager
    //   457: monitorexit
    //   458: aload_0
    //   459: areturn
    //   460: aload_0
    //   461: invokestatic killEarliestProcessAndReturnInfo : (Landroid/content/Context;)Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   464: astore_0
    //   465: aload_0
    //   466: ifnull -> 512
    //   469: aload_0
    //   470: aload #6
    //   472: aload #7
    //   474: invokevirtual reuseInfo : (Ljava/lang/String;Ljava/lang/String;)V
    //   477: ldc 'AppProcessManager'
    //   479: iconst_2
    //   480: anewarray java/lang/Object
    //   483: dup
    //   484: iconst_0
    //   485: ldc_w 'getLaunchClass kill process. processInfo: '
    //   488: aastore
    //   489: dup
    //   490: iconst_1
    //   491: aload_0
    //   492: aastore
    //   493: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   496: new com/tt/miniapp/process/AppProcessManager$LaunchInfo
    //   499: dup
    //   500: aload_0
    //   501: aload_2
    //   502: aconst_null
    //   503: invokespecial <init> : (Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;Lcom/tt/miniapp/process/AppProcessManager$LaunchConfig;Lcom/tt/miniapp/process/AppProcessManager$1;)V
    //   506: astore_0
    //   507: ldc com/tt/miniapp/process/AppProcessManager
    //   509: monitorexit
    //   510: aload_0
    //   511: areturn
    //   512: ldc 'AppProcessManager'
    //   514: iconst_2
    //   515: anewarray java/lang/Object
    //   518: dup
    //   519: iconst_0
    //   520: ldc_w 'getLaunchClass fail ProcessList: '
    //   523: aastore
    //   524: dup
    //   525: iconst_1
    //   526: getstatic com/tt/miniapp/process/AppProcessManager.sProcessList : [Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   529: aastore
    //   530: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   533: ldc com/tt/miniapp/process/AppProcessManager
    //   535: monitorexit
    //   536: aconst_null
    //   537: areturn
    //   538: astore_0
    //   539: ldc com/tt/miniapp/process/AppProcessManager
    //   541: monitorexit
    //   542: goto -> 547
    //   545: aload_0
    //   546: athrow
    //   547: goto -> 545
    //   550: iload_3
    //   551: iconst_1
    //   552: iadd
    //   553: istore_3
    //   554: goto -> 83
    // Exception table:
    //   from	to	target	type
    //   3	55	538	finally
    //   65	81	538	finally
    //   95	187	538	finally
    //   190	216	538	finally
    //   216	243	538	finally
    //   251	284	538	finally
    //   289	325	538	finally
    //   325	337	538	finally
    //   340	346	538	finally
    //   349	361	538	finally
    //   365	403	538	finally
    //   408	413	538	finally
    //   417	455	538	finally
    //   460	465	538	finally
    //   469	507	538	finally
    //   512	533	538	finally
  }
  
  private static Class getMiniAppProcessServiceClass(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    for (ProcessInfo processInfo : sProcessList) {
      if (TextUtils.equals(processInfo.mProcessIdentity, paramString))
        return processInfo.mPreloadServiceClass; 
    } 
    return "miniapp200".equals(paramString) ? MiniappService200.class : null;
  }
  
  public static ProcessInfo getPreloadProcessInfo() {
    for (ProcessInfo processInfo : sProcessList) {
      if (TextUtils.isEmpty(processInfo.mAppId))
        return processInfo; 
    } 
    return null;
  }
  
  public static ProcessInfo getPreloadProcessInfo(Context paramContext) {
    List<ActivityManager.RunningAppProcessInfo> list = getRunningAppProcessInfoList(paramContext);
    for (ProcessInfo processInfo : sProcessList) {
      AppBrandLogger.d("AppProcessManager", new Object[] { "getPreloadProcessInfo processInfo: ", processInfo });
      if (processInfo.isPreload(list)) {
        AppBrandLogger.d("AppProcessManager", new Object[] { "getPreloadProcessInfo preload process exists. processInfo:", processInfo });
        return processInfo;
      } 
    } 
    return null;
  }
  
  private ProcessInfo getPreparePreloadProcess(Context paramContext) {
    AppBrandLogger.d("AppProcessManager", new Object[] { "getPreparePreloadProcessIndex" });
    ProcessInfo processInfo2 = getAvailableProcessInfo(paramContext);
    if (processInfo2 != null) {
      AppBrandLogger.d("AppProcessManager", new Object[] { "getPreparePreloadProcessIndex availableProcessInfo: ", processInfo2 });
      return processInfo2;
    } 
    ProcessInfo processInfo1 = killEarliestProcessAndReturnInfo(paramContext);
    if (processInfo1 != null) {
      AppBrandLogger.d("AppProcessManager", new Object[] { "getPreparePreloadProcessIndex killedProcessInfo: ", processInfo1 });
      return processInfo1;
    } 
    return null;
  }
  
  public static Handler getProcessHandler() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/process/AppProcessManager.mProcessHandler : Landroid/os/Handler;
    //   3: ifnonnull -> 30
    //   6: ldc com/tt/miniapp/process/AppProcessManager
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/process/AppProcessManager.mProcessHandler : Landroid/os/Handler;
    //   12: ifnonnull -> 18
    //   15: invokestatic initProcessHandler : ()V
    //   18: ldc com/tt/miniapp/process/AppProcessManager
    //   20: monitorexit
    //   21: goto -> 30
    //   24: astore_0
    //   25: ldc com/tt/miniapp/process/AppProcessManager
    //   27: monitorexit
    //   28: aload_0
    //   29: athrow
    //   30: getstatic com/tt/miniapp/process/AppProcessManager.mProcessHandler : Landroid/os/Handler;
    //   33: areturn
    // Exception table:
    //   from	to	target	type
    //   9	18	24	finally
    //   18	21	24	finally
    //   25	28	24	finally
  }
  
  public static ProcessInfo getProcessInfoByAppId(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    for (ProcessInfo processInfo : sProcessList) {
      if (TextUtils.equals(processInfo.mAppId, paramString))
        return processInfo; 
    } 
    return null;
  }
  
  public static ProcessInfo getProcessInfoByProcessIdentity(String paramString) {
    for (ProcessInfo processInfo : sProcessList) {
      if (TextUtils.equals(processInfo.mProcessIdentity, paramString))
        return processInfo; 
    } 
    return "miniapp200".equals(paramString) ? sUcProcess : null;
  }
  
  public static ProcessInfo getProcessInfoByProcessName(String paramString) {
    for (ProcessInfo processInfo : sProcessList) {
      if (TextUtils.equals(processInfo.mProcessName, paramString))
        return processInfo; 
    } 
    return TextUtils.equals(sUcProcess.mProcessName, paramString) ? sUcProcess : null;
  }
  
  private static ActivityManager.RunningAppProcessInfo getRunningAppProcessInfoByProcessName(List<ActivityManager.RunningAppProcessInfo> paramList, String paramString) {
    if (paramList != null)
      for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : paramList) {
        if (TextUtils.equals(runningAppProcessInfo.processName, paramString))
          return runningAppProcessInfo; 
      }  
    return null;
  }
  
  public static List<ActivityManager.RunningAppProcessInfo> getRunningAppProcessInfoList(Context paramContext) {
    if (paramContext == null)
      return null; 
    ActivityManager activityManager = (ActivityManager)paramContext.getApplicationContext().getSystemService("activity");
    return (activityManager != null) ? activityManager.getRunningAppProcesses() : null;
  }
  
  private static void initProcessHandler() {
    mProcessHandler = new Handler(HandlerThreadUtil.getBackgroundHandlerThread().getLooper()) {
        public final void handleMessage(Message param1Message) {
          super.handleMessage(param1Message);
          if (param1Message.what == 1) {
            if (BgAudioManagerClient.getInst().needKeepAlive()) {
              AppBrandLogger.i("AppProcessManager", new Object[] { "小程序进入后台超过限定时间，但被保活，不会被杀死" });
              return;
            } 
            AppBrandLogger.i("AppProcessManager", new Object[] { "小程序进入后台超过限定时间，等待被杀死" });
            ToolUtils.onActivityExit((Activity)AppbrandContext.getInst().getCurrentActivity(), 12);
            return;
          } 
          if (param1Message.what == 2) {
            if (AppProcessManager.getPreloadProcessInfo((Context)AppbrandContext.getInst().getApplicationContext()) == null)
              AppProcessManager.reportProcessStatus(null, "preload", 9200, "recheck"); 
            return;
          } 
          if (param1Message.what == 3) {
            removeMessages(3);
            try {
              return;
            } finally {
              param1Message = null;
            } 
          } 
        }
      };
  }
  
  public static void initProcessList(Context paramContext) {
    ProcessInfo[] arrayOfProcessInfo = sProcessList;
    int j = arrayOfProcessInfo.length;
    for (int i = 0; i < j; i++)
      arrayOfProcessInfo[i].init(paramContext); 
    sUcProcess.init(paramContext);
  }
  
  public static boolean isAppProcessExist(Context paramContext, String paramString) {
    StringBuilder stringBuilder = new StringBuilder("isAppProcessExist: ");
    stringBuilder.append(paramString);
    AppBrandLogger.d("AppProcessManager", new Object[] { stringBuilder.toString() });
    ProcessInfo processInfo = getProcessInfoByAppId(paramString);
    return (processInfo == null) ? false : isProcessExist(paramContext, processInfo.mProcessName);
  }
  
  public static boolean isHostProcessExist(Context paramContext) {
    return isProcessExist(paramContext, paramContext.getPackageName());
  }
  
  public static boolean isInHostStack(Class paramClass) {
    if (paramClass == null)
      return false; 
    ProcessInfo[] arrayOfProcessInfo = sProcessList;
    int j = arrayOfProcessInfo.length;
    int i = 0;
    while (i < j) {
      ProcessInfo processInfo = arrayOfProcessInfo[i];
      if (paramClass != processInfo.mNormalActivityClass) {
        if (paramClass == processInfo.mFloatStyleActivityClass)
          return false; 
        if (paramClass == processInfo.mInHostStackActivityClass || paramClass == processInfo.mFloatStyleHostStackActivityClass)
          return true; 
        i++;
      } 
    } 
    return false;
  }
  
  public static boolean isMiniAppProcessExist(Context paramContext) {
    for (ProcessInfo processInfo : sProcessList) {
      if (processInfo != null && isProcessExist(paramContext, processInfo.mProcessName))
        return true; 
    } 
    return false;
  }
  
  public static boolean isProcessExist(Context paramContext, String paramString) {
    return isProcessExist(getRunningAppProcessInfoList(paramContext), paramString);
  }
  
  public static boolean isProcessExist(List<ActivityManager.RunningAppProcessInfo> paramList, String paramString) {
    return (getRunningAppProcessInfoByProcessName(paramList, paramString) != null);
  }
  
  private static ProcessInfo killEarliestProcessAndReturnInfo(Context paramContext) {
    ProcessInfo[] arrayOfProcessInfo = sProcessList;
    int j = arrayOfProcessInfo.length;
    ProcessInfo processInfo1 = null;
    int i = 0;
    while (i < j) {
      ProcessInfo processInfo5 = arrayOfProcessInfo[i];
      ProcessInfo processInfo4 = processInfo1;
      if (!AppJumpListManager.isInAppJumpList(processInfo5.mAppId)) {
        processInfo4 = processInfo1;
        if (!TextUtils.equals(processInfo5.mProcessName, playingBgAudioProcessName)) {
          ProcessInfo processInfo = processInfo1;
          if (processInfo1 == null)
            processInfo = processInfo5; 
          processInfo4 = processInfo;
          if (processInfo5.mUseTime < processInfo.mUseTime)
            processInfo4 = processInfo5; 
        } 
      } 
      i++;
      processInfo1 = processInfo4;
    } 
    ProcessInfo processInfo2 = processInfo1;
    if (processInfo1 == null) {
      ProcessInfo[] arrayOfProcessInfo1 = sProcessList;
      j = arrayOfProcessInfo1.length;
      i = 0;
      while (true) {
        processInfo2 = processInfo1;
        if (i < j) {
          processInfo2 = arrayOfProcessInfo1[i];
          if (TextUtils.equals(AppJumpListManager.getNeedKillAppId(), processInfo2.mAppId))
            processInfo1 = processInfo2; 
          i++;
          continue;
        } 
        break;
      } 
    } 
    ProcessInfo processInfo3 = processInfo2;
    if (processInfo2 == null) {
      ProcessInfo[] arrayOfProcessInfo1 = sProcessList;
      processInfo1 = arrayOfProcessInfo1[0];
      j = arrayOfProcessInfo1.length;
      i = 0;
      while (true) {
        processInfo3 = processInfo1;
        if (i < j) {
          processInfo3 = arrayOfProcessInfo1[i];
          processInfo2 = processInfo1;
          if (processInfo3.mUseTime < processInfo1.mUseTime)
            processInfo2 = processInfo3; 
          i++;
          processInfo1 = processInfo2;
          continue;
        } 
        break;
      } 
    } 
    AppBrandLogger.d("AppProcessManager", new Object[] { "kill earliestProcess. killProcessInfo: ", processInfo3 });
    if (ThreadUtil.isUIThread()) {
      killProcessOnUIThread(paramContext, processInfo3);
      return processInfo3;
    } 
    killProcess(paramContext, processInfo3);
    return processInfo3;
  }
  
  public static void killProcess(Context paramContext, ProcessInfo paramProcessInfo) {
    AppBrandLogger.d("AppProcessManager", new Object[] { "killProcess processInfo:", paramProcessInfo });
    if (TextUtils.equals(killingAppId, paramProcessInfo.mAppId)) {
      AppBrandLogger.d("AppProcessManager", new Object[] { "killProcess TextUtils.equals(killingAppId, processInfo.mAppId)" });
      return;
    } 
    killingAppId = paramProcessInfo.mAppId;
    String str = paramProcessInfo.mProcessName;
    ActivityManager.RunningAppProcessInfo runningAppProcessInfo = getRunningAppProcessInfoByProcessName(getRunningAppProcessInfoList(paramContext), str);
    if (runningAppProcessInfo != null) {
      int i = 0;
      while (true) {
        if (i <= 2) {
          if (killProcessAndRemoveActivityTask(runningAppProcessInfo.pid, paramContext, paramProcessInfo)) {
            i = 1;
            break;
          } 
          AppBrandLogger.d("AppProcessManager", new Object[] { "killProcessAndRemoveActivityTask retryNumber:", Integer.valueOf(i) });
          i++;
          continue;
        } 
        i = 0;
        break;
      } 
      if (i == 0) {
        AppBrandLogger.e("AppProcessManager", new Object[] { "killProcessAndRemoveActivityTaskWithRetry fail" });
        AppBrandMonitor.reportError("mp_special_error", "killProcessAndRemoveActivityTaskWithRetry fail", null);
      } 
    } 
    killingAppId = null;
    notifyUpdateAppJumpList(paramProcessInfo);
    paramProcessInfo.reset();
  }
  
  public static void killProcess(String paramString) {
    int i = 0;
    AppBrandLogger.d("AppProcessManager", new Object[] { "killProcess appId: ", paramString });
    Application application = AppbrandContext.getInst().getApplicationContext();
    ProcessInfo[] arrayOfProcessInfo = sProcessList;
    int j = arrayOfProcessInfo.length;
    while (i < j) {
      ProcessInfo processInfo = arrayOfProcessInfo[i];
      if (TextUtils.equals(processInfo.mAppId, paramString)) {
        killProcess((Context)application, processInfo);
        return;
      } 
      i++;
    } 
  }
  
  private static boolean killProcessAndRemoveActivityTask(int paramInt, Context paramContext, ProcessInfo paramProcessInfo) {
    // Byte code:
    //   0: aload_2
    //   1: getfield mProcessName : Ljava/lang/String;
    //   4: astore #10
    //   6: aload_1
    //   7: aload_2
    //   8: getfield mLaunchActivityClass : Ljava/lang/Class;
    //   11: invokestatic getMiniAppActivityTask : (Landroid/content/Context;Ljava/lang/Class;)Landroid/app/ActivityManager$AppTask;
    //   14: astore #11
    //   16: aload #11
    //   18: ifnull -> 29
    //   21: aload #11
    //   23: invokevirtual finishAndRemoveTask : ()V
    //   26: goto -> 72
    //   29: ldc 'AppProcessManager'
    //   31: iconst_1
    //   32: anewarray java/lang/Object
    //   35: dup
    //   36: iconst_0
    //   37: ldc_w 'do not need to finishAndRemoveTask'
    //   40: aastore
    //   41: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   44: iconst_1
    //   45: istore #4
    //   47: goto -> 75
    //   50: astore #11
    //   52: ldc 'AppProcessManager'
    //   54: iconst_2
    //   55: anewarray java/lang/Object
    //   58: dup
    //   59: iconst_0
    //   60: ldc_w 'finishAndRemoveTask'
    //   63: aastore
    //   64: dup
    //   65: iconst_1
    //   66: aload #11
    //   68: aastore
    //   69: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   72: iconst_0
    //   73: istore #4
    //   75: new java/lang/StringBuilder
    //   78: dup
    //   79: ldc_w 'Killing Process: '
    //   82: invokespecial <init> : (Ljava/lang/String;)V
    //   85: astore #11
    //   87: aload #11
    //   89: aload_1
    //   90: invokestatic getCurProcessName : (Landroid/content/Context;)Ljava/lang/String;
    //   93: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: aload #11
    //   99: ldc_w '\\n'
    //   102: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: pop
    //   106: aload #11
    //   108: new java/lang/Throwable
    //   111: dup
    //   112: invokespecial <init> : ()V
    //   115: invokestatic getStackTraceString : (Ljava/lang/Throwable;)Ljava/lang/String;
    //   118: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload #11
    //   124: invokevirtual toString : ()Ljava/lang/String;
    //   127: invokestatic mpTechnologyMsg : (Ljava/lang/String;)V
    //   130: iload_0
    //   131: invokestatic killProcess : (I)V
    //   134: iconst_0
    //   135: istore_0
    //   136: iconst_0
    //   137: istore #8
    //   139: iload #4
    //   141: istore #5
    //   143: iload #8
    //   145: istore #6
    //   147: iload #5
    //   149: istore #9
    //   151: iload_0
    //   152: istore_3
    //   153: iload_0
    //   154: bipush #30
    //   156: if_icmpge -> 345
    //   159: iload #8
    //   161: istore #4
    //   163: iload #5
    //   165: istore #7
    //   167: ldc2_w 50
    //   170: invokestatic sleep : (J)V
    //   173: iload_0
    //   174: iconst_1
    //   175: iadd
    //   176: istore_3
    //   177: iload #8
    //   179: istore #6
    //   181: iload #8
    //   183: ifne -> 256
    //   186: iload #8
    //   188: istore #4
    //   190: iload #5
    //   192: istore #7
    //   194: aload_1
    //   195: aload #10
    //   197: invokestatic isProcessExist : (Landroid/content/Context;Ljava/lang/String;)Z
    //   200: iconst_1
    //   201: ixor
    //   202: istore #8
    //   204: iload #8
    //   206: istore #6
    //   208: iload #8
    //   210: ifeq -> 256
    //   213: iload #8
    //   215: istore #4
    //   217: iload #5
    //   219: istore #7
    //   221: ldc 'AppProcessManager'
    //   223: iconst_3
    //   224: anewarray java/lang/Object
    //   227: dup
    //   228: iconst_0
    //   229: ldc_w 'killProcess wait'
    //   232: aastore
    //   233: dup
    //   234: iconst_1
    //   235: iload_3
    //   236: bipush #50
    //   238: imul
    //   239: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   242: aastore
    //   243: dup
    //   244: iconst_2
    //   245: ldc_w 'ms'
    //   248: aastore
    //   249: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   252: iload #8
    //   254: istore #6
    //   256: iload #5
    //   258: istore #9
    //   260: iload #5
    //   262: ifne -> 539
    //   265: iload #6
    //   267: istore #4
    //   269: iload #5
    //   271: istore #7
    //   273: aload_1
    //   274: aload_2
    //   275: getfield mLaunchActivityClass : Ljava/lang/Class;
    //   278: invokestatic getMiniAppActivityTask : (Landroid/content/Context;Ljava/lang/Class;)Landroid/app/ActivityManager$AppTask;
    //   281: ifnonnull -> 533
    //   284: iconst_1
    //   285: istore #5
    //   287: goto -> 290
    //   290: iload #5
    //   292: istore #9
    //   294: iload #5
    //   296: ifeq -> 539
    //   299: iload #6
    //   301: istore #4
    //   303: iload #5
    //   305: istore #7
    //   307: ldc 'AppProcessManager'
    //   309: iconst_3
    //   310: anewarray java/lang/Object
    //   313: dup
    //   314: iconst_0
    //   315: ldc_w 'removedActivityTask wait'
    //   318: aastore
    //   319: dup
    //   320: iconst_1
    //   321: iload_3
    //   322: bipush #50
    //   324: imul
    //   325: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   328: aastore
    //   329: dup
    //   330: iconst_2
    //   331: ldc_w 'ms'
    //   334: aastore
    //   335: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   338: iload #5
    //   340: istore #9
    //   342: goto -> 539
    //   345: iload #6
    //   347: istore #4
    //   349: iload #9
    //   351: istore #7
    //   353: ldc 'AppProcessManager'
    //   355: iconst_3
    //   356: anewarray java/lang/Object
    //   359: dup
    //   360: iconst_0
    //   361: ldc_w 'killProcess and removedActivityTask wait'
    //   364: aastore
    //   365: dup
    //   366: iconst_1
    //   367: iload_3
    //   368: bipush #50
    //   370: imul
    //   371: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   374: aastore
    //   375: dup
    //   376: iconst_2
    //   377: ldc_w 'ms'
    //   380: aastore
    //   381: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   384: iload #6
    //   386: istore #4
    //   388: iload #9
    //   390: istore #7
    //   392: goto -> 426
    //   395: astore_1
    //   396: iconst_0
    //   397: istore #5
    //   399: iload #4
    //   401: istore #7
    //   403: iload #5
    //   405: istore #4
    //   407: ldc 'AppProcessManager'
    //   409: iconst_2
    //   410: anewarray java/lang/Object
    //   413: dup
    //   414: iconst_0
    //   415: ldc_w 'killProcess'
    //   418: aastore
    //   419: dup
    //   420: iconst_1
    //   421: aload_1
    //   422: aastore
    //   423: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   426: iload #4
    //   428: ifeq -> 460
    //   431: iload #7
    //   433: ifne -> 439
    //   436: goto -> 460
    //   439: ldc 'AppProcessManager'
    //   441: iconst_2
    //   442: anewarray java/lang/Object
    //   445: dup
    //   446: iconst_0
    //   447: ldc_w 'killProcessAndRemoveTask finish processInfo:'
    //   450: aastore
    //   451: dup
    //   452: iconst_1
    //   453: aload_2
    //   454: aastore
    //   455: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   458: iconst_1
    //   459: ireturn
    //   460: new java/lang/StringBuilder
    //   463: dup
    //   464: ldc_w 'killedProcess: '
    //   467: invokespecial <init> : (Ljava/lang/String;)V
    //   470: astore_1
    //   471: aload_1
    //   472: iload #4
    //   474: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   477: pop
    //   478: aload_1
    //   479: ldc_w ' removedActivityTask: '
    //   482: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   485: pop
    //   486: aload_1
    //   487: iload #7
    //   489: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   492: pop
    //   493: aload_1
    //   494: invokevirtual toString : ()Ljava/lang/String;
    //   497: astore_1
    //   498: ldc_w 'mp_special_error'
    //   501: ldc_w 'killProcessAndRemoveActivityTask fail'
    //   504: aload_1
    //   505: invokestatic reportError : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   508: ldc 'AppProcessManager'
    //   510: iconst_3
    //   511: anewarray java/lang/Object
    //   514: dup
    //   515: iconst_0
    //   516: ldc_w 'killProcessAndRemoveActivityTask fail processInfo:'
    //   519: aastore
    //   520: dup
    //   521: iconst_1
    //   522: aload_2
    //   523: aastore
    //   524: dup
    //   525: iconst_2
    //   526: aload_1
    //   527: aastore
    //   528: invokestatic w : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   531: iconst_0
    //   532: ireturn
    //   533: iconst_0
    //   534: istore #5
    //   536: goto -> 290
    //   539: iload #6
    //   541: istore #8
    //   543: iload #9
    //   545: istore #5
    //   547: iload_3
    //   548: istore_0
    //   549: iload #9
    //   551: ifeq -> 143
    //   554: iload #6
    //   556: istore #8
    //   558: iload #9
    //   560: istore #5
    //   562: iload_3
    //   563: istore_0
    //   564: iload #6
    //   566: ifeq -> 143
    //   569: goto -> 345
    //   572: astore_1
    //   573: goto -> 407
    // Exception table:
    //   from	to	target	type
    //   6	16	50	java/lang/Exception
    //   21	26	50	java/lang/Exception
    //   29	44	50	java/lang/Exception
    //   75	134	395	java/lang/Exception
    //   167	173	572	java/lang/Exception
    //   194	204	572	java/lang/Exception
    //   221	252	572	java/lang/Exception
    //   273	284	572	java/lang/Exception
    //   307	338	572	java/lang/Exception
    //   353	384	572	java/lang/Exception
  }
  
  private static void killProcessOnUIThread(final Context context, final ProcessInfo processInfo) {
    AppBrandLogger.i("AppProcessManager", new Object[] { "killProcessOnUIThread processInfo:", processInfo });
    long l = System.currentTimeMillis();
    synchronized (sKillProcessLock) {
      ThreadUtil.runOnWorkThread(new Action() {
            public final void act() {
              AppProcessManager.killProcess(context, processInfo);
              synchronized (AppProcessManager.sKillProcessLock) {
                AppProcessManager.sKillProcessLock.notifyAll();
                return;
              } 
            }
          }(Scheduler)LaunchThreadPool.getInst(), false);
      try {
        sKillProcessLock.wait();
      } catch (InterruptedException interruptedException) {
        AppBrandLogger.e("AppProcessManager", new Object[] { "killProcessOnUIThread", interruptedException });
      } 
      AppBrandLogger.i("AppProcessManager", new Object[] { "killProcessOnUIThread duration:", Long.valueOf(System.currentTimeMillis() - l) });
      return;
    } 
  }
  
  public static void notifyUpdateAppJumpList(ProcessInfo paramProcessInfo) {
    if (!TextUtils.isEmpty(paramProcessInfo.mAppId)) {
      AppJumpListManager.removeApp(paramProcessInfo.mAppId);
      if (paramProcessInfo.isLaunchActivityInHostStack())
        InnerMiniAppProcessBridge.notifyAllMiniAppUpdateSnapshotEvent(); 
    } 
  }
  
  public static void registerHostProcessLifeListener(ServiceBindManager.HostProcessLifeListener paramHostProcessLifeListener) {
    ServiceBindManager.registerHostProcessLifeListener(paramHostProcessLifeListener);
  }
  
  public static void registerProcessLifeListener(MiniProcessMonitor.ProcessLifeListener paramProcessLifeListener) {
    // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: ldc_w 'registerProcessLifeListener: '
    //   7: invokespecial <init> : (Ljava/lang/String;)V
    //   10: astore_1
    //   11: aload_1
    //   12: aload_0
    //   13: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: ldc 'AppProcessManager'
    //   19: iconst_1
    //   20: anewarray java/lang/Object
    //   23: dup
    //   24: iconst_0
    //   25: aload_1
    //   26: invokevirtual toString : ()Ljava/lang/String;
    //   29: aastore
    //   30: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   33: aload_0
    //   34: ifnonnull -> 38
    //   37: return
    //   38: ldc_w com/tt/miniapp/process/MiniProcessMonitor
    //   41: monitorenter
    //   42: getstatic com/tt/miniapp/process/AppProcessManager.sProcessLifeListenerList : Ljava/util/List;
    //   45: aload_0
    //   46: invokeinterface add : (Ljava/lang/Object;)Z
    //   51: pop
    //   52: ldc_w com/tt/miniapp/process/MiniProcessMonitor
    //   55: monitorexit
    //   56: return
    //   57: astore_0
    //   58: ldc_w com/tt/miniapp/process/MiniProcessMonitor
    //   61: monitorexit
    //   62: aload_0
    //   63: athrow
    // Exception table:
    //   from	to	target	type
    //   42	56	57	finally
    //   58	62	57	finally
  }
  
  public static void reportProcessStatus(ProcessInfo paramProcessInfo, String paramString1, int paramInt, String paramString2) {
    JSONObject jSONObject2 = new JSONObject();
    String str = "";
    long l = 0L;
    if (paramProcessInfo != null)
      try {
        str = paramProcessInfo.mProcessName;
        l = System.currentTimeMillis() - paramProcessInfo.mStartTime;
        jSONObject2.put("process_type", paramString1);
        jSONObject2.put("name", str);
        jSONObject2.put("report_form", paramString2);
        JSONObject jSONObject = new JSONObject();
        return;
      } finally {
        paramProcessInfo = null;
        AppBrandLogger.e("AppProcessManager", new Object[] { paramProcessInfo });
      }  
    jSONObject2.put("process_type", paramString1);
    jSONObject2.put("name", str);
    jSONObject2.put("report_form", paramString2);
    JSONObject jSONObject1 = new JSONObject();
    jSONObject1.put("duration", l);
    AppBrandMonitor.statusAndDuration("mp_process_status", paramInt, jSONObject1, jSONObject2);
  }
  
  public static void startMiniProcessMonitor(Context paramContext, ProcessInfo paramProcessInfo) {
    startMiniProcessMonitor(paramContext, paramProcessInfo, true);
  }
  
  public static void startMiniProcessMonitor(Context paramContext, ProcessInfo paramProcessInfo, boolean paramBoolean) {
    if (paramProcessInfo == null)
      return; 
    StringBuilder stringBuilder = new StringBuilder("49411_startMiniProcessMonitor: ");
    stringBuilder.append(paramProcessInfo.mProcessIdentity);
    stringBuilder.append(", autoCreate=");
    stringBuilder.append(paramBoolean);
    AppBrandLogger.d("AppProcessManager", new Object[] { stringBuilder.toString() });
    if (!paramBoolean && !isProcessExist(paramContext, paramProcessInfo.mProcessName)) {
      StringBuilder stringBuilder1 = new StringBuilder("49411_startMiniProcessMonitor: MiniProcess not exist && !autoCreate ");
      stringBuilder1.append(paramProcessInfo.mProcessName);
      AppBrandLogger.e("AppProcessManager", new Object[] { stringBuilder1.toString() });
      return;
    } 
    paramProcessInfo.mMiniProcessMonitor.startMonitorMiniAppProcess();
  }
  
  public static void unregisterHostProcessLifeListener(ServiceBindManager.HostProcessLifeListener paramHostProcessLifeListener) {
    ServiceBindManager.unregisterHostProcessLifeListener(paramHostProcessLifeListener);
  }
  
  public static void unregisterProcessLifeListener(MiniProcessMonitor.ProcessLifeListener paramProcessLifeListener) {
    // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: ldc_w 'unregisterProcessLifeListener: '
    //   7: invokespecial <init> : (Ljava/lang/String;)V
    //   10: astore_1
    //   11: aload_1
    //   12: aload_0
    //   13: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: ldc 'AppProcessManager'
    //   19: iconst_1
    //   20: anewarray java/lang/Object
    //   23: dup
    //   24: iconst_0
    //   25: aload_1
    //   26: invokevirtual toString : ()Ljava/lang/String;
    //   29: aastore
    //   30: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   33: aload_0
    //   34: ifnonnull -> 38
    //   37: return
    //   38: ldc_w com/tt/miniapp/process/MiniProcessMonitor
    //   41: monitorenter
    //   42: getstatic com/tt/miniapp/process/AppProcessManager.sProcessLifeListenerList : Ljava/util/List;
    //   45: aload_0
    //   46: invokeinterface remove : (Ljava/lang/Object;)Z
    //   51: pop
    //   52: ldc_w com/tt/miniapp/process/MiniProcessMonitor
    //   55: monitorexit
    //   56: return
    //   57: astore_0
    //   58: ldc_w com/tt/miniapp/process/MiniProcessMonitor
    //   61: monitorexit
    //   62: aload_0
    //   63: athrow
    // Exception table:
    //   from	to	target	type
    //   42	56	57	finally
    //   58	62	57	finally
  }
  
  public static void updateAppProcessInfo(String paramString1, String paramString2, String paramString3) {
    // Byte code:
    //   0: ldc com/tt/miniapp/process/AppProcessManager
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/process/AppProcessManager.sProcessList : [Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   6: astore #5
    //   8: aload #5
    //   10: arraylength
    //   11: istore #4
    //   13: iconst_0
    //   14: istore_3
    //   15: iload_3
    //   16: iload #4
    //   18: if_icmpge -> 95
    //   21: aload #5
    //   23: iload_3
    //   24: aaload
    //   25: astore #6
    //   27: aload #6
    //   29: getfield mProcessName : Ljava/lang/String;
    //   32: aload_0
    //   33: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   36: ifeq -> 88
    //   39: aload_1
    //   40: aload #6
    //   42: getfield mAppId : Ljava/lang/String;
    //   45: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   48: ifne -> 81
    //   51: ldc 'AppProcessManager'
    //   53: iconst_4
    //   54: anewarray java/lang/Object
    //   57: dup
    //   58: iconst_0
    //   59: ldc_w 'runningAppId not equals processInfo mAppId. processInfo:'
    //   62: aastore
    //   63: dup
    //   64: iconst_1
    //   65: aload #6
    //   67: aastore
    //   68: dup
    //   69: iconst_2
    //   70: ldc_w ' runningAppId: '
    //   73: aastore
    //   74: dup
    //   75: iconst_3
    //   76: aload_1
    //   77: aastore
    //   78: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   81: aload #6
    //   83: aload_1
    //   84: aload_2
    //   85: invokevirtual processRunning : (Ljava/lang/String;Ljava/lang/String;)V
    //   88: iload_3
    //   89: iconst_1
    //   90: iadd
    //   91: istore_3
    //   92: goto -> 15
    //   95: ldc com/tt/miniapp/process/AppProcessManager
    //   97: monitorexit
    //   98: return
    //   99: astore_0
    //   100: ldc com/tt/miniapp/process/AppProcessManager
    //   102: monitorexit
    //   103: goto -> 108
    //   106: aload_0
    //   107: athrow
    //   108: goto -> 106
    // Exception table:
    //   from	to	target	type
    //   3	13	99	finally
    //   27	81	99	finally
    //   81	88	99	finally
  }
  
  public void killAllProcess() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    ProcessInfo[] arrayOfProcessInfo = sProcessList;
    int j = arrayOfProcessInfo.length;
    for (int i = 0; i < j; i++)
      killProcess((Context)application, arrayOfProcessInfo[i]); 
  }
  
  public void preloadEmptyProcess(boolean paramBoolean) {
    preloadEmptyProcessDelay(paramBoolean, 0);
  }
  
  public void preloadEmptyProcessDelay(boolean paramBoolean, int paramInt) {
    Message message = getProcessHandler().obtainMessage(3);
    getProcessHandler().removeMessages(3);
    getProcessHandler().sendMessageDelayed(message, paramInt);
  }
  
  public void preloadEmptyProcessInternal() {
    AppBrandLogger.d("AppProcessManager", new Object[] { "preloadEmptyProcess" });
    final Application context = AppbrandContext.getInst().getApplicationContext();
    BaseBundleManager.getInst().preload((Context)application);
    if (!AppbrandSupport.inst().isSDKSupport()) {
      AppBrandLogger.e("AppProcessManager", new Object[] { "SDK UnSupport stopPreloadEmptyProcess" });
      return;
    } 
    if (application == null) {
      AppBrandLogger.d("AppProcessManager", new Object[] { "preloadEmptyProcess context is null" });
      return;
    } 
    if (getPreloadProcessInfo((Context)application) != null) {
      AppBrandLogger.d("AppProcessManager", new Object[] { "preloadEmptyProcess has preload Process" });
      return;
    } 
    final ProcessInfo preparePreloadProcessInfo = getPreparePreloadProcess((Context)application);
    if (processInfo == null) {
      AppBrandLogger.e("AppProcessManager", new Object[] { "preloadEmptyProcess invalid Process" });
      return;
    } 
    AppBrandLogger.d("AppProcessManager", new Object[] { "preloadEmptyProcess dest process not exists, preload it. preparePreloadProcessInfo: ", processInfo });
    Class clazz = processInfo.mPreloadServiceClass;
    if (clazz == null) {
      AppBrandLogger.e("AppProcessManager", new Object[] { "preloadEmptyProcess getServiceClassByProcessIndex fail. preparePreloadProcessInfo: ", processInfo });
      return;
    } 
    try {
      processInfo.reset();
      HostDependManager.getInst().startMiniAppService((Context)application, new Intent((Context)application, clazz));
      if (!this.mPreloadedEmptyProcess)
        this.mPreloadedEmptyProcess = true; 
      ThreadUtil.runOnWorkThread(new Action() {
            public void act() {
              AppProcessManager.startMiniProcessMonitor(context, preparePreloadProcessInfo);
            }
          }(Scheduler)LaunchThreadPool.getInst());
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(5, "AppProcessManager", exception.getStackTrace());
      return;
    } 
  }
  
  public static class LaunchConfig {
    public final boolean mFloatStyle;
    
    public final boolean mForceColdBoot;
    
    public final boolean mIsInHostStack;
    
    public final boolean mUsePreloadProcess;
    
    public LaunchConfig(boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3, boolean param1Boolean4) {
      this.mIsInHostStack = param1Boolean1;
      this.mFloatStyle = param1Boolean2;
      this.mUsePreloadProcess = param1Boolean3;
      this.mForceColdBoot = param1Boolean4;
    }
  }
  
  public static class LaunchInfo {
    private final boolean mIsNeedClearTask;
    
    private final Class mLaunchActivityClass;
    
    private final Class mLaunchServiceClass;
    
    private final AppProcessManager.ProcessInfo mProcessInfo;
    
    private LaunchInfo(AppProcessManager.ProcessInfo param1ProcessInfo, AppProcessManager.LaunchConfig param1LaunchConfig) {
      this(param1ProcessInfo, param1LaunchConfig, true);
    }
    
    private LaunchInfo(AppProcessManager.ProcessInfo param1ProcessInfo, AppProcessManager.LaunchConfig param1LaunchConfig, boolean param1Boolean) {
      this.mLaunchActivityClass = param1ProcessInfo.generateLaunchActivityClass(param1LaunchConfig.mIsInHostStack, param1LaunchConfig.mFloatStyle);
      this.mProcessInfo = param1ProcessInfo;
      this.mLaunchServiceClass = param1ProcessInfo.mPreloadServiceClass;
      this.mIsNeedClearTask = param1Boolean;
      param1ProcessInfo.prepareStart();
    }
    
    public Class getLaunchActivityClass() {
      return this.mLaunchActivityClass;
    }
    
    public Class getLaunchServiceClass() {
      return this.mLaunchServiceClass;
    }
    
    public AppProcessManager.ProcessInfo getProcessInfo() {
      return this.mProcessInfo;
    }
    
    public boolean isFloatStyle() {
      return this.mProcessInfo.isLaunchActivityFloatStyle();
    }
    
    public boolean isInHostStack() {
      return this.mProcessInfo.isLaunchActivityInHostStack();
    }
    
    public boolean isNeedClearTask() {
      return this.mIsNeedClearTask;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{mIsNeedClearTask: ");
      stringBuilder.append(this.mIsNeedClearTask);
      stringBuilder.append(",mProcessInfo: ");
      stringBuilder.append(this.mProcessInfo);
      stringBuilder.append(",mLaunchServiceClass: ");
      stringBuilder.append(this.mLaunchServiceClass);
      stringBuilder.append(",mLaunchActivityClass: ");
      stringBuilder.append(this.mLaunchActivityClass);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
  
  public static class ProcessInfo {
    public String mAppId = "";
    
    public final Class mFloatStyleActivityClass;
    
    public final Class mFloatStyleHostStackActivityClass;
    
    public final Class mInHostStackActivityClass;
    
    private boolean mIsStarting;
    
    public Class mLaunchActivityClass;
    
    public MiniProcessMonitor mMiniProcessMonitor;
    
    public final Class mNormalActivityClass;
    
    public final Class mPreloadServiceClass;
    
    public final String mProcessIdentity;
    
    private final int mProcessIndex;
    
    public String mProcessName;
    
    public final String mProcessNameSuffix;
    
    public long mStartTime;
    
    public long mUseTime;
    
    public String mVersionType;
    
    private ProcessInfo(int param1Int, String param1String1, String param1String2, Class param1Class1, Class param1Class2, Class param1Class3, Class param1Class4, Class param1Class5) {
      this.mProcessIndex = param1Int;
      this.mProcessIdentity = param1String1;
      this.mProcessNameSuffix = param1String2;
      this.mNormalActivityClass = param1Class1;
      this.mFloatStyleActivityClass = param1Class2;
      this.mInHostStackActivityClass = param1Class3;
      this.mFloatStyleHostStackActivityClass = param1Class4;
      this.mPreloadServiceClass = param1Class5;
      this.mMiniProcessMonitor = new MiniProcessMonitor(this, AppProcessManager.mInnerProcessLifeListener);
    }
    
    public Class generateLaunchActivityClass(boolean param1Boolean1, boolean param1Boolean2) {
      if (this.mLaunchActivityClass == null)
        if (param1Boolean1) {
          if (param1Boolean2) {
            this.mLaunchActivityClass = this.mFloatStyleHostStackActivityClass;
          } else {
            this.mLaunchActivityClass = this.mInHostStackActivityClass;
          } 
        } else if (param1Boolean2) {
          this.mLaunchActivityClass = this.mFloatStyleActivityClass;
        } else {
          this.mLaunchActivityClass = this.mNormalActivityClass;
        }  
      return this.mLaunchActivityClass;
    }
    
    public Class getLaunchActivityClass() {
      return this.mLaunchActivityClass;
    }
    
    public void init(Context param1Context) {
      String str = param1Context.getPackageName();
      HostDependManager hostDependManager = HostDependManager.getInst();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(this.mProcessNameSuffix);
      this.mProcessName = hostDependManager.replaceProcessName(stringBuilder.toString());
    }
    
    public boolean isLaunchActivityFloatStyle() {
      Class clazz = this.mLaunchActivityClass;
      return (clazz == this.mFloatStyleActivityClass || clazz == this.mFloatStyleHostStackActivityClass);
    }
    
    public boolean isLaunchActivityInHostStack() {
      Class clazz = this.mLaunchActivityClass;
      return (clazz == this.mInHostStackActivityClass || clazz == this.mFloatStyleHostStackActivityClass);
    }
    
    public boolean isPreload(List<ActivityManager.RunningAppProcessInfo> param1List) {
      return (TextUtils.isEmpty(this.mAppId) && AppProcessManager.isProcessExist(param1List, this.mProcessName));
    }
    
    public boolean isUsing() {
      return isUsing(AppProcessManager.getRunningAppProcessInfoList((Context)AppbrandContext.getInst().getApplicationContext()));
    }
    
    public boolean isUsing(List<ActivityManager.RunningAppProcessInfo> param1List) {
      return (this.mIsStarting || AppProcessManager.isProcessExist(param1List, this.mProcessName));
    }
    
    public void prepareStart() {
      this.mIsStarting = true;
      this.mUseTime = System.currentTimeMillis();
    }
    
    public void processRunning(String param1String1, String param1String2) {
      this.mAppId = param1String1;
      this.mVersionType = param1String2;
      this.mIsStarting = false;
      this.mUseTime = System.currentTimeMillis();
    }
    
    public void reset() {
      this.mUseTime = 0L;
      this.mStartTime = System.currentTimeMillis();
      this.mAppId = "";
      this.mVersionType = null;
      this.mIsStarting = false;
      this.mLaunchActivityClass = null;
    }
    
    public void reuseInfo(String param1String1, String param1String2) {
      reset();
      this.mAppId = param1String1;
      this.mVersionType = param1String2;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{mProcessIndex: ");
      stringBuilder.append(this.mProcessIndex);
      stringBuilder.append(", mProcessName: ");
      stringBuilder.append(this.mProcessName);
      stringBuilder.append(", mAppId: ");
      stringBuilder.append(this.mAppId);
      stringBuilder.append(", mUseTime: ");
      stringBuilder.append(this.mUseTime);
      stringBuilder.append(", isLaunchActivityInHostStack: ");
      stringBuilder.append(isLaunchActivityInHostStack());
      stringBuilder.append(", isLaunchActivityFloatStyle: ");
      stringBuilder.append(isLaunchActivityFloatStyle());
      stringBuilder.append(", mLaunchActivityClass: ");
      stringBuilder.append(this.mLaunchActivityClass);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
  
  class AppProcessManager {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\AppProcessManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */