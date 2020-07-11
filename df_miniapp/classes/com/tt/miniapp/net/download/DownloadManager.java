package com.tt.miniapp.net.download;

import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.RequestInceptUtil;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.net.AppbrandCallback;
import com.tt.miniapp.net.NetBus;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.option.q.c;
import com.tt.option.q.f;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.e;
import okhttp3.f;
import okhttp3.y;

public class DownloadManager {
  private static DownloadManager downloadUtil;
  
  private final y okHttpClient = NetBus.okHttpClient;
  
  private void asyncDownload(String paramString1, Map<String, String> paramMap, String paramString2, String paramString3, OnDownloadListener paramOnDownloadListener, TaskInfo paramTaskInfo, boolean paramBoolean) {
    if (HostDependManager.getInst().isEnableDownlaodFileApiRewrite()) {
      doFileDownlaodByHostImpl(paramString1, paramMap, paramString2, paramString3, paramOnDownloadListener, paramTaskInfo, paramBoolean);
      return;
    } 
    doFileDownloadByInnerOkhttpWay(paramString1, paramMap, paramString2, paramString3, paramOnDownloadListener, paramTaskInfo, paramBoolean);
  }
  
  private void doFileDownlaodByHostImpl(String paramString1, Map<String, String> paramMap, String paramString2, String paramString3, final OnDownloadListener listener, TaskInfo paramTaskInfo, boolean paramBoolean) {
    AppBrandLogger.d("tma_DownloadManager", new Object[] { "download file rewrite enable" });
    File file = new File(paramString2);
    if (!file.exists())
      file.mkdirs(); 
    AppBrandLogger.d("tma_DownloadManager", new Object[] { "asyncDownload " });
    f f = new f(paramString1);
    if (paramMap != null)
      for (Map.Entry<String, String> entry : paramMap.entrySet())
        f.a((String)entry.getKey(), (String)entry.getValue());  
    f.a = file.getAbsolutePath();
    f.b = paramString3;
    NetManager.getInst().downloadFile(f, new c.a() {
          public void downloadFailed(String param1String, Throwable param1Throwable) {
            AppBrandLogger.e("tma_DownloadManager", new Object[] { "onFailure", param1Throwable });
            DownloadManager.OnDownloadListener onDownloadListener = listener;
            if (onDownloadListener != null)
              onDownloadListener.onDownloadFailed("download fail", param1Throwable); 
          }
          
          public void downloadSuccess(ae param1ae) {
            AppBrandLogger.d("tma_DownloadManager", new Object[] { "downloadSuccess" });
            DownloadManager.OnDownloadListener onDownloadListener = listener;
            if (onDownloadListener != null)
              onDownloadListener.onDownloadSuccess(param1ae); 
          }
          
          public void updateProgress(int param1Int, long param1Long1, long param1Long2) {
            AppBrandLogger.e("tma_DownloadManager", new Object[] { "updateProgress" });
            DownloadManager.OnDownloadListener onDownloadListener = listener;
            if (onDownloadListener != null)
              onDownloadListener.onDownloading(param1Int, param1Long1, param1Long2); 
          }
        });
  }
  
  private void doFileDownloadByInnerOkhttpWay(String paramString1, Map<String, String> paramMap, final String saveDir, final String filename, final OnDownloadListener listener, final TaskInfo taskInfo, boolean paramBoolean) {
    try {
      AppConfig appConfig;
      long l;
      ac.a a = (new ac.a()).a(paramString1);
      RequestInceptUtil.inceptRequest(a);
      if (paramMap != null && paramMap.size() > 0)
        initRequestHeaders(a, paramMap); 
      ac ac = a.c();
      a = null;
      if (paramBoolean)
        appConfig = AppbrandApplicationImpl.getInst().getAppConfig(); 
      if (appConfig != null) {
        l = (appConfig.getNetworkTimeout()).downloadFile;
      } else {
        l = 60000L;
      } 
      this.okHttpClient.c().a(l, TimeUnit.MILLISECONDS).b(l, TimeUnit.MILLISECONDS).c(l, TimeUnit.MILLISECONDS).a().a(ac).a((f)new AppbrandCallback() {
            public String callbackFrom() {
              return "4003";
            }
            
            public void onFailure(e param1e, IOException param1IOException) {
              AppBrandLogger.e("tma_DownloadManager", new Object[] { "onFailure", param1IOException });
              DownloadManager.OnDownloadListener onDownloadListener = listener;
              if (onDownloadListener != null)
                onDownloadListener.onDownloadFailed("download fail", param1IOException); 
            }
            
            public void onSuccess(e param1e, ae param1ae) {
              File file = new File(saveDir, filename);
              DownloadManager.this.responseToFile(param1ae, file, listener, taskInfo);
            }
          });
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_DownloadManager", exception.getStackTrace());
      if (listener != null)
        listener.onDownloadFailed("download fail", exception); 
      if (!DebugUtil.debug())
        return; 
      StringBuilder stringBuilder = new StringBuilder("DownloadManager download exception ");
      stringBuilder.append(exception);
      throw new RuntimeException(stringBuilder.toString());
    } 
  }
  
  public static DownloadManager get() {
    if (downloadUtil == null)
      downloadUtil = new DownloadManager(); 
    return downloadUtil;
  }
  
  private void initRequestHeaders(ac.a parama, Map<String, String> paramMap) {
    if (paramMap != null && !paramMap.isEmpty())
      for (Map.Entry<String, String> entry : paramMap.entrySet()) {
        parama.b((String)entry.getKey());
        parama.b((String)entry.getKey(), (String)entry.getValue());
      }  
  }
  
  public void asyncDownload(String paramString1, Map<String, String> paramMap, String paramString2, String paramString3, OnDownloadListener paramOnDownloadListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: aload_2
    //   5: aload_3
    //   6: aload #4
    //   8: aload #5
    //   10: aconst_null
    //   11: iconst_0
    //   12: invokespecial asyncDownload : (Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;Lcom/tt/miniapp/net/download/DownloadManager$OnDownloadListener;Lcom/tt/miniapp/net/download/DownloadManager$TaskInfo;Z)V
    //   15: aload_0
    //   16: monitorexit
    //   17: return
    //   18: astore_1
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_1
    //   22: athrow
    // Exception table:
    //   from	to	target	type
    //   2	15	18	finally
  }
  
  public void asyncDownload(String paramString1, Map<String, String> paramMap, String paramString2, String paramString3, OnDownloadListener paramOnDownloadListener, TaskInfo paramTaskInfo) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: astore #7
    //   5: invokestatic getInst : ()Lcom/tt/miniapp/debug/DebugManager;
    //   8: getfield mRemoteDebugEnable : Z
    //   11: ifeq -> 44
    //   14: aload_2
    //   15: astore #7
    //   17: aload_2
    //   18: ifnonnull -> 30
    //   21: new java/util/HashMap
    //   24: dup
    //   25: invokespecial <init> : ()V
    //   28: astore #7
    //   30: aload #7
    //   32: ldc_w 'remoteDebug'
    //   35: ldc_w 'download'
    //   38: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   43: pop
    //   44: aload_0
    //   45: aload_1
    //   46: aload #7
    //   48: aload_3
    //   49: aload #4
    //   51: aload #5
    //   53: aload #6
    //   55: iconst_1
    //   56: invokespecial asyncDownload : (Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;Lcom/tt/miniapp/net/download/DownloadManager$OnDownloadListener;Lcom/tt/miniapp/net/download/DownloadManager$TaskInfo;Z)V
    //   59: aload_0
    //   60: monitorexit
    //   61: return
    //   62: astore_1
    //   63: aload_0
    //   64: monitorexit
    //   65: aload_1
    //   66: athrow
    // Exception table:
    //   from	to	target	type
    //   5	14	62	finally
    //   21	30	62	finally
    //   30	44	62	finally
    //   44	59	62	finally
  }
  
  public File responseToFile(ae paramae, File paramFile, OnDownloadListener paramOnDownloadListener, TaskInfo paramTaskInfo) {
    // Byte code:
    //   0: aload_1
    //   1: getfield g : Lokhttp3/af;
    //   4: astore #19
    //   6: iconst_0
    //   7: istore #8
    //   9: iconst_0
    //   10: istore #9
    //   12: iconst_0
    //   13: istore #10
    //   15: iconst_0
    //   16: istore #11
    //   18: iconst_0
    //   19: istore #12
    //   21: iconst_0
    //   22: istore #5
    //   24: aload #19
    //   26: ifnonnull -> 57
    //   29: ldc 'tma_DownloadManager'
    //   31: iconst_1
    //   32: anewarray java/lang/Object
    //   35: dup
    //   36: iconst_0
    //   37: ldc_w 'onFailure response.body() == null'
    //   40: aastore
    //   41: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   44: aload_3
    //   45: ifnull -> 55
    //   48: aload_3
    //   49: aload_1
    //   50: invokeinterface onDownloadSuccess : (Lokhttp3/ae;)V
    //   55: aload_2
    //   56: areturn
    //   57: sipush #2048
    //   60: newarray byte
    //   62: astore #21
    //   64: aload_1
    //   65: getfield g : Lokhttp3/af;
    //   68: invokevirtual byteStream : ()Ljava/io/InputStream;
    //   71: astore #19
    //   73: aload_1
    //   74: getfield g : Lokhttp3/af;
    //   77: invokevirtual contentLength : ()J
    //   80: lstore #14
    //   82: aload_2
    //   83: invokevirtual exists : ()Z
    //   86: istore #18
    //   88: iload #18
    //   90: ifeq -> 188
    //   93: ldc 'tma_DownloadManager'
    //   95: iconst_1
    //   96: anewarray java/lang/Object
    //   99: dup
    //   100: iconst_0
    //   101: ldc_w 'file.exists()'
    //   104: aastore
    //   105: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   108: new org/json/JSONObject
    //   111: dup
    //   112: invokespecial <init> : ()V
    //   115: astore #20
    //   117: aload #20
    //   119: ldc_w 'errMsg'
    //   122: ldc_w 'file.exists()'
    //   125: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   128: pop
    //   129: ldc_w 'mp_start_error'
    //   132: sipush #1022
    //   135: aload #20
    //   137: invokestatic statusRate : (Ljava/lang/String;ILorg/json/JSONObject;)V
    //   140: goto -> 157
    //   143: astore #20
    //   145: bipush #6
    //   147: ldc 'tma_DownloadManager'
    //   149: aload #20
    //   151: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   154: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   157: aload_2
    //   158: invokevirtual delete : ()Z
    //   161: pop
    //   162: goto -> 188
    //   165: astore_2
    //   166: aconst_null
    //   167: astore #20
    //   169: iload #12
    //   171: istore #5
    //   173: goto -> 606
    //   176: astore #4
    //   178: aconst_null
    //   179: astore #20
    //   181: iload #10
    //   183: istore #5
    //   185: goto -> 521
    //   188: new java/io/FileOutputStream
    //   191: dup
    //   192: aload_2
    //   193: invokespecial <init> : (Ljava/io/File;)V
    //   196: astore #20
    //   198: lconst_0
    //   199: lstore #16
    //   201: aload #19
    //   203: aload #21
    //   205: invokevirtual read : ([B)I
    //   208: istore #6
    //   210: iload #6
    //   212: iconst_m1
    //   213: if_icmpeq -> 684
    //   216: aload #4
    //   218: ifnull -> 314
    //   221: aload #4
    //   223: invokeinterface isCancel : ()Z
    //   228: istore #18
    //   230: iload #18
    //   232: ifeq -> 314
    //   235: aload #19
    //   237: invokevirtual close : ()V
    //   240: goto -> 272
    //   243: astore_2
    //   244: iconst_1
    //   245: istore #5
    //   247: goto -> 606
    //   250: astore #4
    //   252: iconst_1
    //   253: istore #5
    //   255: goto -> 521
    //   258: astore #4
    //   260: bipush #6
    //   262: ldc 'tma_DownloadManager'
    //   264: aload #4
    //   266: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   269: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   272: aload #20
    //   274: invokevirtual flush : ()V
    //   277: aload #20
    //   279: invokevirtual close : ()V
    //   282: goto -> 299
    //   285: astore #4
    //   287: bipush #6
    //   289: ldc 'tma_DownloadManager'
    //   291: aload #4
    //   293: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   296: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   299: iconst_1
    //   300: istore #5
    //   302: goto -> 375
    //   305: astore_2
    //   306: goto -> 169
    //   309: astore #4
    //   311: goto -> 181
    //   314: aload #20
    //   316: aload #21
    //   318: iconst_0
    //   319: iload #6
    //   321: invokevirtual write : ([BII)V
    //   324: lload #16
    //   326: iload #6
    //   328: i2l
    //   329: ladd
    //   330: lstore #16
    //   332: lload #16
    //   334: l2f
    //   335: fconst_1
    //   336: fmul
    //   337: lload #14
    //   339: l2f
    //   340: fdiv
    //   341: ldc_w 100.0
    //   344: fmul
    //   345: f2i
    //   346: istore #13
    //   348: aload_3
    //   349: ifnull -> 681
    //   352: iload #8
    //   354: istore #6
    //   356: iload #9
    //   358: istore #7
    //   360: aload_3
    //   361: iload #13
    //   363: lload #16
    //   365: lload #14
    //   367: invokeinterface onDownloading : (IJJ)V
    //   372: goto -> 681
    //   375: aload #19
    //   377: astore #21
    //   379: aload #20
    //   381: astore #4
    //   383: iload #5
    //   385: istore #6
    //   387: iload #5
    //   389: istore #7
    //   391: aload #4
    //   393: invokevirtual flush : ()V
    //   396: aload #21
    //   398: ifnull -> 406
    //   401: aload #21
    //   403: invokevirtual close : ()V
    //   406: aload #4
    //   408: invokevirtual close : ()V
    //   411: goto -> 428
    //   414: astore #4
    //   416: bipush #6
    //   418: ldc 'tma_DownloadManager'
    //   420: aload #4
    //   422: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   425: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   428: aload_3
    //   429: ifnull -> 603
    //   432: iload #5
    //   434: ifeq -> 596
    //   437: goto -> 584
    //   440: astore_2
    //   441: iload #6
    //   443: istore #5
    //   445: goto -> 606
    //   448: astore #4
    //   450: iload #7
    //   452: istore #5
    //   454: goto -> 521
    //   457: astore_2
    //   458: iload #11
    //   460: istore #5
    //   462: goto -> 606
    //   465: astore #4
    //   467: goto -> 181
    //   470: astore_2
    //   471: aconst_null
    //   472: astore #20
    //   474: iload #12
    //   476: istore #5
    //   478: goto -> 606
    //   481: astore #4
    //   483: aconst_null
    //   484: astore #20
    //   486: iload #10
    //   488: istore #5
    //   490: goto -> 521
    //   493: astore_2
    //   494: aconst_null
    //   495: astore #20
    //   497: aload #20
    //   499: astore #19
    //   501: iload #12
    //   503: istore #5
    //   505: goto -> 606
    //   508: astore #4
    //   510: aconst_null
    //   511: astore #20
    //   513: aload #20
    //   515: astore #19
    //   517: iload #10
    //   519: istore #5
    //   521: iconst_5
    //   522: ldc 'tma_DownloadManager'
    //   524: aload #4
    //   526: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   529: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   532: aload_2
    //   533: invokevirtual delete : ()Z
    //   536: pop
    //   537: aload #19
    //   539: ifnull -> 550
    //   542: aload #19
    //   544: invokevirtual close : ()V
    //   547: goto -> 550
    //   550: aload #20
    //   552: ifnull -> 575
    //   555: aload #20
    //   557: invokevirtual close : ()V
    //   560: goto -> 575
    //   563: bipush #6
    //   565: ldc 'tma_DownloadManager'
    //   567: aload #4
    //   569: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   572: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   575: aload_3
    //   576: ifnull -> 603
    //   579: iload #5
    //   581: ifeq -> 596
    //   584: aload_3
    //   585: ldc_w 'abort'
    //   588: aconst_null
    //   589: invokeinterface onDownloadFailed : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   594: aload_2
    //   595: areturn
    //   596: aload_3
    //   597: aload_1
    //   598: invokeinterface onDownloadSuccess : (Lokhttp3/ae;)V
    //   603: aload_2
    //   604: areturn
    //   605: astore_2
    //   606: aload #19
    //   608: ifnull -> 619
    //   611: aload #19
    //   613: invokevirtual close : ()V
    //   616: goto -> 619
    //   619: aload #20
    //   621: ifnull -> 644
    //   624: aload #20
    //   626: invokevirtual close : ()V
    //   629: goto -> 644
    //   632: bipush #6
    //   634: ldc 'tma_DownloadManager'
    //   636: aload #4
    //   638: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   641: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   644: aload_3
    //   645: ifnull -> 673
    //   648: iload #5
    //   650: ifeq -> 666
    //   653: aload_3
    //   654: ldc_w 'abort'
    //   657: aconst_null
    //   658: invokeinterface onDownloadFailed : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   663: goto -> 673
    //   666: aload_3
    //   667: aload_1
    //   668: invokeinterface onDownloadSuccess : (Lokhttp3/ae;)V
    //   673: goto -> 678
    //   676: aload_2
    //   677: athrow
    //   678: goto -> 676
    //   681: goto -> 201
    //   684: goto -> 375
    //   687: astore #4
    //   689: goto -> 563
    //   692: astore #4
    //   694: goto -> 632
    // Exception table:
    //   from	to	target	type
    //   64	73	508	java/lang/Exception
    //   64	73	493	finally
    //   73	88	481	java/lang/Exception
    //   73	88	470	finally
    //   93	117	176	java/lang/Exception
    //   93	117	165	finally
    //   117	140	143	org/json/JSONException
    //   117	140	176	java/lang/Exception
    //   117	140	165	finally
    //   145	157	176	java/lang/Exception
    //   145	157	165	finally
    //   157	162	176	java/lang/Exception
    //   157	162	165	finally
    //   188	198	481	java/lang/Exception
    //   188	198	470	finally
    //   201	210	465	java/lang/Exception
    //   201	210	457	finally
    //   221	230	309	java/lang/Exception
    //   221	230	305	finally
    //   235	240	258	java/io/IOException
    //   235	240	250	java/lang/Exception
    //   235	240	243	finally
    //   260	272	250	java/lang/Exception
    //   260	272	243	finally
    //   272	282	285	java/io/IOException
    //   272	282	250	java/lang/Exception
    //   272	282	243	finally
    //   287	299	250	java/lang/Exception
    //   287	299	243	finally
    //   314	324	465	java/lang/Exception
    //   314	324	457	finally
    //   360	372	448	java/lang/Exception
    //   360	372	440	finally
    //   391	396	448	java/lang/Exception
    //   391	396	440	finally
    //   401	406	414	java/io/IOException
    //   406	411	414	java/io/IOException
    //   521	537	605	finally
    //   542	547	687	java/io/IOException
    //   555	560	687	java/io/IOException
    //   611	616	692	java/io/IOException
    //   624	629	692	java/io/IOException
  }
  
  public File syncDownload(String paramString1, Map<String, String> paramMap, String paramString2, String paramString3, OnDownloadListener paramOnDownloadListener, TaskInfo paramTaskInfo) {
    /* monitor enter ThisExpression{ObjectType{com/tt/miniapp/net/download/DownloadManager}} */
    try {
      File file1;
      ac.a a = (new ac.a()).a(paramString1);
      RequestInceptUtil.inceptRequest(a);
      initRequestHeaders(a, paramMap);
      ac ac = a.c();
      ae ae = this.okHttpClient.a(ac).b();
      File file2 = new File(paramString2, paramString3);
      if (ae != null) {
        file1 = responseToFile(ae, file2, paramOnDownloadListener, paramTaskInfo);
      } else {
        file1 = file2;
        if (file2.exists()) {
          file2.delete();
          file1 = file2;
        } 
      } 
      /* monitor exit ThisExpression{ObjectType{com/tt/miniapp/net/download/DownloadManager}} */
      return file1;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_DownloadManager", exception.getStackTrace());
      if (paramOnDownloadListener != null) {
        StringBuilder stringBuilder = new StringBuilder("sync download fail ");
        stringBuilder.append(exception.getMessage());
        paramOnDownloadListener.onDownloadFailed(stringBuilder.toString(), exception);
      } 
      /* monitor exit ThisExpression{ObjectType{com/tt/miniapp/net/download/DownloadManager}} */
      return null;
    } finally {}
    /* monitor exit ThisExpression{ObjectType{com/tt/miniapp/net/download/DownloadManager}} */
    throw paramString1;
  }
  
  public static interface OnDownloadListener {
    void onDownloadFailed(String param1String, Throwable param1Throwable);
    
    void onDownloadSuccess(ae param1ae);
    
    void onDownloading(int param1Int, long param1Long1, long param1Long2);
  }
  
  public static interface TaskInfo {
    void cancel();
    
    boolean isCancel();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\download\DownloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */