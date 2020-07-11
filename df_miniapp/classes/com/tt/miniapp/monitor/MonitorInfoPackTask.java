package com.tt.miniapp.monitor;

import android.os.Debug;
import android.support.v4.f.k;
import android.text.TextUtils;
import android.util.SparseArray;
import com.tt.miniapp.entity.PerformanceEntity;
import com.tt.miniapp.event.Event;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.DebugUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

public class MonitorInfoPackTask extends BaseMonitorTask {
  private static AtomicInteger mCountTime;
  
  private static AtomicBoolean mIsFirstPack;
  
  private static final LinkedList<k<Boolean, Integer>> sCpuTopRates;
  
  private static final LinkedList<k<Boolean, Integer>> sCpuUseRates = new LinkedList<k<Boolean, Integer>>();
  
  private static final LinkedList<k<Boolean, Integer>> sFpsList;
  
  private static final SparseArray<List<Integer>> sGameDataMap;
  
  private static IFeedback sIFeedback;
  
  private static int sLastFirstCpuUseRates;
  
  private static int sLastSecondaryCpuUseRates;
  
  private static final LinkedList<k<Boolean, Debug.MemoryInfo>> sMemoryInfoList;
  
  static {
    sCpuTopRates = new LinkedList<k<Boolean, Integer>>();
    sFpsList = new LinkedList<k<Boolean, Integer>>();
    sMemoryInfoList = new LinkedList<k<Boolean, Debug.MemoryInfo>>();
    sGameDataMap = new SparseArray();
    sLastSecondaryCpuUseRates = -1;
    mIsFirstPack = new AtomicBoolean(true);
    mCountTime = new AtomicInteger(1);
    sLastFirstCpuUseRates = -1;
  }
  
  public MonitorInfoPackTask() {
    super(10000L);
  }
  
  public static void addCpuRate(boolean paramBoolean, int paramInt) {
    synchronized (sCpuUseRates) {
      sCpuUseRates.offer(new k(Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt)));
      if (sCpuUseRates.size() > 30)
        sCpuUseRates.pollFirst(); 
      return;
    } 
  }
  
  public static void addDrawCall(int paramInt) {
    synchronized (sGameDataMap) {
      List<Integer> list2 = (List)sGameDataMap.get(1);
      List<Integer> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        sGameDataMap.put(1, list1);
      } 
      list1.add(Integer.valueOf(paramInt));
      return;
    } 
  }
  
  public static void addFps(boolean paramBoolean, int paramInt) {
    synchronized (sFpsList) {
      sFpsList.offer(new k(Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt)));
      if (sFpsList.size() > 30)
        sFpsList.pollFirst(); 
      return;
    } 
  }
  
  public static void addGameFps(int paramInt) {
    synchronized (sGameDataMap) {
      List<Integer> list2 = (List)sGameDataMap.get(0);
      List<Integer> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        sGameDataMap.put(0, list1);
      } 
      list1.add(Integer.valueOf(paramInt));
      return;
    } 
  }
  
  public static void addMemoryInfo(boolean paramBoolean, Debug.MemoryInfo paramMemoryInfo) {
    synchronized (sMemoryInfoList) {
      sMemoryInfoList.offer(new k(Boolean.valueOf(paramBoolean), paramMemoryInfo));
      if (sMemoryInfoList.size() > 30)
        sMemoryInfoList.pollFirst(); 
      return;
    } 
  }
  
  public static void addSecondaryCpuRate(int paramInt) {
    // Byte code:
    //   0: ldc com/tt/miniapp/monitor/MonitorInfoPackTask
    //   2: monitorenter
    //   3: iload_0
    //   4: putstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sLastSecondaryCpuUseRates : I
    //   7: ldc com/tt/miniapp/monitor/MonitorInfoPackTask
    //   9: monitorexit
    //   10: return
    //   11: astore_1
    //   12: ldc com/tt/miniapp/monitor/MonitorInfoPackTask
    //   14: monitorexit
    //   15: aload_1
    //   16: athrow
    // Exception table:
    //   from	to	target	type
    //   3	7	11	finally
  }
  
  public static void addSecondaryTopRate(boolean paramBoolean, int paramInt) {
    synchronized (sCpuTopRates) {
      sCpuTopRates.offer(new k(Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt)));
      if (sCpuTopRates.size() > 30)
        sCpuTopRates.pollFirst(); 
      return;
    } 
  }
  
  public static void addTri(int paramInt) {
    synchronized (sGameDataMap) {
      List<Integer> list2 = (List)sGameDataMap.get(3);
      List<Integer> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        sGameDataMap.put(3, list1);
      } 
      list1.add(Integer.valueOf(paramInt));
      return;
    } 
  }
  
  public static void addVert(int paramInt) {
    synchronized (sGameDataMap) {
      List<Integer> list2 = (List)sGameDataMap.get(2);
      List<Integer> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        sGameDataMap.put(2, list1);
      } 
      list1.add(Integer.valueOf(paramInt));
      return;
    } 
  }
  
  private PerformanceEntity calPerformance(boolean paramBoolean) {
    // Byte code:
    //   0: iload_1
    //   1: istore #15
    //   3: invokestatic getInst : ()Lcom/tt/miniapphost/IAppbrandApplication;
    //   6: invokeinterface getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   11: astore #45
    //   13: new com/tt/miniapp/entity/PerformanceEntity
    //   16: dup
    //   17: invokespecial <init> : ()V
    //   20: astore #44
    //   22: aload #44
    //   24: iload #15
    //   26: putfield isBackground : Z
    //   29: iconst_0
    //   30: istore #11
    //   32: aload #45
    //   34: ifnull -> 89
    //   37: aload #44
    //   39: aload #45
    //   41: getfield appId : Ljava/lang/String;
    //   44: putfield appID : Ljava/lang/String;
    //   47: aload #45
    //   49: getfield type : I
    //   52: iconst_2
    //   53: if_icmpne -> 62
    //   56: iconst_1
    //   57: istore #14
    //   59: goto -> 65
    //   62: iconst_0
    //   63: istore #14
    //   65: aload #44
    //   67: iload #14
    //   69: putfield isGame : Z
    //   72: aload #44
    //   74: invokestatic getInst : ()Lcom/tt/miniapp/manager/basebundle/BaseBundleManager;
    //   77: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   80: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   83: invokevirtual getSdkCurrentVersionStr : (Landroid/content/Context;)Ljava/lang/String;
    //   86: putfield libVersion : Ljava/lang/String;
    //   89: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sCpuUseRates : Ljava/util/LinkedList;
    //   92: astore #45
    //   94: aload #45
    //   96: monitorenter
    //   97: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sCpuUseRates : Ljava/util/LinkedList;
    //   100: invokevirtual size : ()I
    //   103: istore #9
    //   105: iload #9
    //   107: ifle -> 252
    //   110: iconst_0
    //   111: istore #4
    //   113: iconst_0
    //   114: istore #5
    //   116: fconst_0
    //   117: fstore_2
    //   118: iconst_0
    //   119: istore #6
    //   121: iload #4
    //   123: iload #9
    //   125: if_icmpge -> 219
    //   128: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sCpuUseRates : Ljava/util/LinkedList;
    //   131: iload #4
    //   133: invokevirtual get : (I)Ljava/lang/Object;
    //   136: checkcast android/support/v4/f/k
    //   139: astore #46
    //   141: iload #5
    //   143: istore #7
    //   145: fload_2
    //   146: fstore_3
    //   147: iload #6
    //   149: istore #8
    //   151: aload #46
    //   153: ifnull -> 1162
    //   156: iload #5
    //   158: istore #7
    //   160: fload_2
    //   161: fstore_3
    //   162: iload #6
    //   164: istore #8
    //   166: aload #46
    //   168: getfield a : Ljava/lang/Object;
    //   171: checkcast java/lang/Boolean
    //   174: invokevirtual booleanValue : ()Z
    //   177: iload #15
    //   179: if_icmpne -> 1162
    //   182: aload #46
    //   184: getfield b : Ljava/lang/Object;
    //   187: checkcast java/lang/Integer
    //   190: invokevirtual intValue : ()I
    //   193: istore #7
    //   195: iload #6
    //   197: iload #7
    //   199: invokestatic max : (II)I
    //   202: istore #8
    //   204: fload_2
    //   205: iload #7
    //   207: i2f
    //   208: fadd
    //   209: fstore_3
    //   210: iload #5
    //   212: iconst_1
    //   213: iadd
    //   214: istore #7
    //   216: goto -> 1162
    //   219: iload #5
    //   221: ifle -> 252
    //   224: aload #44
    //   226: fload_2
    //   227: iload #5
    //   229: i2f
    //   230: fdiv
    //   231: invokestatic round : (F)I
    //   234: putfield aveCpuRate : I
    //   237: aload #44
    //   239: iload #5
    //   241: putfield cpuMonitorCount : I
    //   244: aload #44
    //   246: getfield aveCpuRate : I
    //   249: putstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sLastFirstCpuUseRates : I
    //   252: aload #45
    //   254: monitorexit
    //   255: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sCpuTopRates : Ljava/util/LinkedList;
    //   258: astore #45
    //   260: aload #45
    //   262: monitorenter
    //   263: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sCpuTopRates : Ljava/util/LinkedList;
    //   266: invokevirtual size : ()I
    //   269: istore #12
    //   271: iload #12
    //   273: ifle -> 456
    //   276: iconst_0
    //   277: istore #7
    //   279: iconst_0
    //   280: istore #8
    //   282: fconst_0
    //   283: fstore_2
    //   284: iconst_0
    //   285: istore #4
    //   287: iconst_0
    //   288: istore #6
    //   290: iload #7
    //   292: iload #12
    //   294: if_icmpge -> 417
    //   297: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sCpuTopRates : Ljava/util/LinkedList;
    //   300: iload #7
    //   302: invokevirtual get : (I)Ljava/lang/Object;
    //   305: checkcast android/support/v4/f/k
    //   308: astore #46
    //   310: iload #8
    //   312: istore #9
    //   314: fload_2
    //   315: fstore_3
    //   316: iload #4
    //   318: istore #5
    //   320: iload #6
    //   322: istore #10
    //   324: aload #46
    //   326: ifnull -> 1181
    //   329: iload #8
    //   331: istore #9
    //   333: fload_2
    //   334: fstore_3
    //   335: iload #4
    //   337: istore #5
    //   339: iload #6
    //   341: istore #10
    //   343: aload #46
    //   345: getfield a : Ljava/lang/Object;
    //   348: checkcast java/lang/Boolean
    //   351: invokevirtual booleanValue : ()Z
    //   354: iload #15
    //   356: if_icmpne -> 1181
    //   359: aload #46
    //   361: getfield b : Ljava/lang/Object;
    //   364: checkcast java/lang/Integer
    //   367: invokevirtual intValue : ()I
    //   370: istore #9
    //   372: iload #4
    //   374: istore #5
    //   376: iload #9
    //   378: aload_0
    //   379: aload #44
    //   381: invokespecial getThresholdCPU : (Lcom/tt/miniapp/entity/PerformanceEntity;)I
    //   384: if_icmple -> 393
    //   387: iload #4
    //   389: iconst_1
    //   390: iadd
    //   391: istore #5
    //   393: iload #6
    //   395: iload #9
    //   397: invokestatic max : (II)I
    //   400: istore #10
    //   402: fload_2
    //   403: iload #9
    //   405: i2f
    //   406: fadd
    //   407: fstore_3
    //   408: iload #8
    //   410: iconst_1
    //   411: iadd
    //   412: istore #9
    //   414: goto -> 1181
    //   417: iload #8
    //   419: ifle -> 456
    //   422: aload #44
    //   424: fload_2
    //   425: iload #8
    //   427: i2f
    //   428: fdiv
    //   429: invokestatic round : (F)I
    //   432: putfield avgCpuFront : I
    //   435: aload #44
    //   437: iload #8
    //   439: putfield cpuSecondaryCount : I
    //   442: aload #44
    //   444: iload #4
    //   446: bipush #100
    //   448: imul
    //   449: iload #8
    //   451: idiv
    //   452: i2f
    //   453: putfield cpuThresholdTimes : F
    //   456: aload #45
    //   458: monitorexit
    //   459: aload #44
    //   461: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sLastSecondaryCpuUseRates : I
    //   464: putfield secondaryCpuRate : I
    //   467: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sFpsList : Ljava/util/LinkedList;
    //   470: astore #45
    //   472: aload #45
    //   474: monitorenter
    //   475: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sFpsList : Ljava/util/LinkedList;
    //   478: invokevirtual size : ()I
    //   481: istore #12
    //   483: iload #12
    //   485: ifle -> 696
    //   488: iconst_0
    //   489: istore #7
    //   491: iconst_0
    //   492: istore #9
    //   494: fconst_0
    //   495: fstore_3
    //   496: ldc 2147483647
    //   498: istore #10
    //   500: iconst_0
    //   501: istore #4
    //   503: iload #7
    //   505: iload #12
    //   507: if_icmpge -> 650
    //   510: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sFpsList : Ljava/util/LinkedList;
    //   513: iload #7
    //   515: invokevirtual get : (I)Ljava/lang/Object;
    //   518: checkcast android/support/v4/f/k
    //   521: astore #46
    //   523: iload #9
    //   525: istore #5
    //   527: fload_3
    //   528: fstore_2
    //   529: iload #10
    //   531: istore #6
    //   533: iload #4
    //   535: istore #8
    //   537: aload #46
    //   539: ifnull -> 1204
    //   542: iload #9
    //   544: istore #5
    //   546: fload_3
    //   547: fstore_2
    //   548: iload #10
    //   550: istore #6
    //   552: iload #4
    //   554: istore #8
    //   556: aload #46
    //   558: getfield a : Ljava/lang/Object;
    //   561: checkcast java/lang/Boolean
    //   564: invokevirtual booleanValue : ()Z
    //   567: iload #15
    //   569: if_icmpne -> 1204
    //   572: aload #46
    //   574: getfield b : Ljava/lang/Object;
    //   577: checkcast java/lang/Integer
    //   580: invokevirtual intValue : ()I
    //   583: istore #13
    //   585: iload #10
    //   587: iload #13
    //   589: invokestatic min : (II)I
    //   592: istore #10
    //   594: fload_3
    //   595: iload #13
    //   597: i2f
    //   598: fadd
    //   599: fstore_3
    //   600: iload #9
    //   602: iconst_1
    //   603: iadd
    //   604: istore #9
    //   606: iload #9
    //   608: istore #5
    //   610: fload_3
    //   611: fstore_2
    //   612: iload #10
    //   614: istore #6
    //   616: iload #4
    //   618: istore #8
    //   620: iload #13
    //   622: aload_0
    //   623: aload #44
    //   625: invokespecial getThresholdFps : (Lcom/tt/miniapp/entity/PerformanceEntity;)I
    //   628: if_icmpge -> 1204
    //   631: iload #4
    //   633: iconst_1
    //   634: iadd
    //   635: istore #8
    //   637: iload #9
    //   639: istore #5
    //   641: fload_3
    //   642: fstore_2
    //   643: iload #10
    //   645: istore #6
    //   647: goto -> 1204
    //   650: iload #9
    //   652: ifle -> 696
    //   655: aload #44
    //   657: fload_3
    //   658: iload #9
    //   660: i2f
    //   661: fdiv
    //   662: invokestatic round : (F)I
    //   665: putfield aveFps : I
    //   668: aload #44
    //   670: iload #10
    //   672: putfield minFps : I
    //   675: aload #44
    //   677: iload #9
    //   679: putfield fpsMonitorCount : I
    //   682: aload #44
    //   684: iload #4
    //   686: bipush #100
    //   688: imul
    //   689: iload #9
    //   691: idiv
    //   692: i2f
    //   693: putfield fpsThreshold : F
    //   696: aload #45
    //   698: monitorexit
    //   699: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sMemoryInfoList : Ljava/util/LinkedList;
    //   702: astore #45
    //   704: aload #45
    //   706: monitorenter
    //   707: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sMemoryInfoList : Ljava/util/LinkedList;
    //   710: invokevirtual size : ()I
    //   713: istore #4
    //   715: iload #4
    //   717: ifle -> 1000
    //   720: lconst_0
    //   721: lstore #36
    //   723: iconst_0
    //   724: istore #6
    //   726: lconst_0
    //   727: lstore #26
    //   729: lconst_0
    //   730: lstore #32
    //   732: lconst_0
    //   733: lstore #22
    //   735: lconst_0
    //   736: lstore #18
    //   738: lconst_0
    //   739: lstore #20
    //   741: lconst_0
    //   742: lstore #16
    //   744: iload #11
    //   746: istore #5
    //   748: iload #5
    //   750: iload #4
    //   752: if_icmpge -> 922
    //   755: getstatic com/tt/miniapp/monitor/MonitorInfoPackTask.sMemoryInfoList : Ljava/util/LinkedList;
    //   758: iload #5
    //   760: invokevirtual get : (I)Ljava/lang/Object;
    //   763: checkcast android/support/v4/f/k
    //   766: astore #46
    //   768: aload #46
    //   770: ifnull -> 1227
    //   773: lload #36
    //   775: lstore #24
    //   777: iload #6
    //   779: istore #7
    //   781: lload #26
    //   783: lstore #28
    //   785: lload #32
    //   787: lstore #30
    //   789: lload #22
    //   791: lstore #34
    //   793: lload #18
    //   795: lstore #38
    //   797: lload #20
    //   799: lstore #40
    //   801: lload #16
    //   803: lstore #42
    //   805: aload #46
    //   807: getfield a : Ljava/lang/Object;
    //   810: checkcast java/lang/Boolean
    //   813: invokevirtual booleanValue : ()Z
    //   816: iload_1
    //   817: if_icmpne -> 1259
    //   820: aload #46
    //   822: getfield b : Ljava/lang/Object;
    //   825: checkcast android/os/Debug$MemoryInfo
    //   828: astore #46
    //   830: lload #32
    //   832: aload #46
    //   834: getfield dalvikPss : I
    //   837: i2l
    //   838: invokestatic max : (JJ)J
    //   841: lstore #30
    //   843: lload #36
    //   845: aload #46
    //   847: getfield nativePss : I
    //   850: i2l
    //   851: invokestatic max : (JJ)J
    //   854: lstore #24
    //   856: lload #22
    //   858: aload #46
    //   860: invokevirtual getTotalPss : ()I
    //   863: i2l
    //   864: invokestatic max : (JJ)J
    //   867: lstore #34
    //   869: lload #26
    //   871: aload #46
    //   873: getfield dalvikPss : I
    //   876: i2l
    //   877: ladd
    //   878: lstore #28
    //   880: lload #20
    //   882: aload #46
    //   884: getfield nativePss : I
    //   887: i2l
    //   888: ladd
    //   889: lstore #40
    //   891: lload #18
    //   893: aload #46
    //   895: invokevirtual getTotalPss : ()I
    //   898: i2l
    //   899: ladd
    //   900: lstore #38
    //   902: lload #16
    //   904: aload #46
    //   906: getfield otherPss : I
    //   909: i2l
    //   910: ladd
    //   911: lstore #42
    //   913: iload #6
    //   915: iconst_1
    //   916: iadd
    //   917: istore #7
    //   919: goto -> 1259
    //   922: iload #6
    //   924: ifle -> 1000
    //   927: iload #6
    //   929: i2l
    //   930: lstore #24
    //   932: aload #44
    //   934: lload #26
    //   936: lload #24
    //   938: ldiv
    //   939: putfield aveDalvikPss : J
    //   942: aload #44
    //   944: lload #32
    //   946: putfield maxDalvikPss : J
    //   949: aload #44
    //   951: lload #20
    //   953: lload #24
    //   955: ldiv
    //   956: putfield aveNativePss : J
    //   959: aload #44
    //   961: lload #36
    //   963: putfield maxNativePss : J
    //   966: aload #44
    //   968: lload #18
    //   970: lload #24
    //   972: ldiv
    //   973: putfield aveTotalPss : J
    //   976: aload #44
    //   978: lload #22
    //   980: putfield maxTotalPss : J
    //   983: aload #44
    //   985: lload #16
    //   987: lload #24
    //   989: ldiv
    //   990: putfield avgOtherPss : J
    //   993: aload #44
    //   995: iload #6
    //   997: putfield memoryMonitorCount : I
    //   1000: aload #45
    //   1002: monitorexit
    //   1003: aload_0
    //   1004: aload #44
    //   1006: invokespecial setGameFps : (Lcom/tt/miniapp/entity/PerformanceEntity;)V
    //   1009: aload_0
    //   1010: aload #44
    //   1012: invokespecial setGameDrawCall : (Lcom/tt/miniapp/entity/PerformanceEntity;)V
    //   1015: aload_0
    //   1016: aload #44
    //   1018: invokespecial setGameVert : (Lcom/tt/miniapp/entity/PerformanceEntity;)V
    //   1021: aload_0
    //   1022: aload #44
    //   1024: invokespecial setGameTri : (Lcom/tt/miniapp/entity/PerformanceEntity;)V
    //   1027: invokestatic getMaxMemory : ()J
    //   1030: lconst_0
    //   1031: lcmp
    //   1032: ifle -> 1108
    //   1035: aload #44
    //   1037: invokestatic getMaxMemory : ()J
    //   1040: putfield maxMemory : J
    //   1043: aload #44
    //   1045: getfield maxMemory : J
    //   1048: lconst_0
    //   1049: lcmp
    //   1050: ifle -> 1108
    //   1053: aload #44
    //   1055: getfield memoryMonitorCount : I
    //   1058: ifle -> 1108
    //   1061: aload #44
    //   1063: getfield maxMemory : J
    //   1066: l2f
    //   1067: fstore_2
    //   1068: aload #44
    //   1070: aload #44
    //   1072: getfield maxTotalPss : J
    //   1075: l2f
    //   1076: fload_2
    //   1077: fdiv
    //   1078: ldc_w 100.0
    //   1081: fmul
    //   1082: invokestatic round : (F)I
    //   1085: putfield maxMemoryRate : I
    //   1088: aload #44
    //   1090: aload #44
    //   1092: getfield aveTotalPss : J
    //   1095: l2f
    //   1096: fload_2
    //   1097: fdiv
    //   1098: ldc_w 100.0
    //   1101: fmul
    //   1102: invokestatic round : (F)I
    //   1105: putfield aveMemoryRate : I
    //   1108: aload #44
    //   1110: invokestatic optAvgRouterTime : ()J
    //   1113: putfield avgRouterTime : J
    //   1116: aload #44
    //   1118: areturn
    //   1119: astore #44
    //   1121: goto -> 1126
    //   1124: astore #44
    //   1126: aload #45
    //   1128: monitorexit
    //   1129: aload #44
    //   1131: athrow
    //   1132: astore #44
    //   1134: aload #45
    //   1136: monitorexit
    //   1137: aload #44
    //   1139: athrow
    //   1140: astore #44
    //   1142: aload #45
    //   1144: monitorexit
    //   1145: aload #44
    //   1147: athrow
    //   1148: astore #44
    //   1150: aload #45
    //   1152: monitorexit
    //   1153: goto -> 1159
    //   1156: aload #44
    //   1158: athrow
    //   1159: goto -> 1156
    //   1162: iload #4
    //   1164: iconst_1
    //   1165: iadd
    //   1166: istore #4
    //   1168: iload #7
    //   1170: istore #5
    //   1172: fload_3
    //   1173: fstore_2
    //   1174: iload #8
    //   1176: istore #6
    //   1178: goto -> 121
    //   1181: iload #7
    //   1183: iconst_1
    //   1184: iadd
    //   1185: istore #7
    //   1187: iload #9
    //   1189: istore #8
    //   1191: fload_3
    //   1192: fstore_2
    //   1193: iload #5
    //   1195: istore #4
    //   1197: iload #10
    //   1199: istore #6
    //   1201: goto -> 290
    //   1204: iload #7
    //   1206: iconst_1
    //   1207: iadd
    //   1208: istore #7
    //   1210: iload #5
    //   1212: istore #9
    //   1214: fload_2
    //   1215: fstore_3
    //   1216: iload #6
    //   1218: istore #10
    //   1220: iload #8
    //   1222: istore #4
    //   1224: goto -> 503
    //   1227: lload #16
    //   1229: lstore #42
    //   1231: lload #20
    //   1233: lstore #40
    //   1235: lload #18
    //   1237: lstore #38
    //   1239: lload #22
    //   1241: lstore #34
    //   1243: lload #32
    //   1245: lstore #30
    //   1247: lload #26
    //   1249: lstore #28
    //   1251: iload #6
    //   1253: istore #7
    //   1255: lload #36
    //   1257: lstore #24
    //   1259: iload #5
    //   1261: iconst_1
    //   1262: iadd
    //   1263: istore #5
    //   1265: lload #24
    //   1267: lstore #36
    //   1269: iload #7
    //   1271: istore #6
    //   1273: lload #28
    //   1275: lstore #26
    //   1277: lload #30
    //   1279: lstore #32
    //   1281: lload #34
    //   1283: lstore #22
    //   1285: lload #38
    //   1287: lstore #18
    //   1289: lload #40
    //   1291: lstore #20
    //   1293: lload #42
    //   1295: lstore #16
    //   1297: goto -> 748
    // Exception table:
    //   from	to	target	type
    //   97	105	1148	finally
    //   128	141	1148	finally
    //   166	204	1148	finally
    //   224	252	1148	finally
    //   252	255	1148	finally
    //   263	271	1140	finally
    //   297	310	1140	finally
    //   343	372	1140	finally
    //   376	387	1140	finally
    //   393	402	1140	finally
    //   422	456	1140	finally
    //   456	459	1140	finally
    //   475	483	1132	finally
    //   510	523	1132	finally
    //   556	594	1132	finally
    //   620	631	1132	finally
    //   655	696	1132	finally
    //   696	699	1132	finally
    //   707	715	1124	finally
    //   755	768	1119	finally
    //   805	913	1119	finally
    //   932	1000	1119	finally
    //   1000	1003	1119	finally
    //   1126	1129	1124	finally
    //   1134	1137	1132	finally
    //   1142	1145	1140	finally
    //   1150	1153	1148	finally
  }
  
  private int calculateAverage(List<Integer> paramList) {
    if (paramList != null) {
      if (paramList.size() <= 0)
        return 0; 
      int j = 0;
      int k = 0;
      int i;
      for (i = 0; j < paramList.size(); i = m) {
        Integer integer = paramList.get(j);
        int n = k;
        int m = i;
        if (integer != null) {
          n = k;
          m = i;
          if (integer.intValue() > 0) {
            n = k + 1;
            m = i + integer.intValue();
          } 
        } 
        j++;
        k = n;
      } 
      if (k > 0)
        return Math.round((i / k)); 
    } 
    return 0;
  }
  
  private String feedbackPerformance() {
    return getPerformanceJSONObject(calPerformance(false)).toString();
  }
  
  public static int getCpuRate() {
    int j = sLastFirstCpuUseRates;
    int i = j;
    if (j <= 0)
      i = sLastSecondaryCpuUseRates; 
    return i;
  }
  
  public static int getLastCpuRate() {
    return (sCpuTopRates.size() > 0) ? ((Integer)((k)sCpuTopRates.getLast()).b).intValue() : 0;
  }
  
  public static int getLastFps() {
    return (sFpsList.size() > 0) ? ((Integer)((k)sFpsList.getLast()).b).intValue() : 0;
  }
  
  public static int getLastMemoryPss() {
    return (sMemoryInfoList.size() > 0) ? ((Debug.MemoryInfo)((k)sMemoryInfoList.getLast()).b).getTotalPss() : 0;
  }
  
  private JSONObject getPerformanceJSONObject(PerformanceEntity paramPerformanceEntity) {
    JSONObject jSONObject = new JSONObject();
    try {
      String str;
      boolean bool1 = TextUtils.isEmpty(paramPerformanceEntity.appID);
      boolean bool = false;
      if (bool1) {
        AppBrandLogger.e("tma_MonitorInfoPackTask", new Object[] { "TextUtils.isEmpty(entity.appID)" });
        return jSONObject;
      } 
      jSONObject.put("mp_id", paramPerformanceEntity.appID);
      if (paramPerformanceEntity.isGame) {
        str = "micro_game";
      } else {
        str = "micro_app";
      } 
      jSONObject.put("_param_for_special", str);
      jSONObject.put("lib_version", paramPerformanceEntity.libVersion);
      if (paramPerformanceEntity.avgRouterTime > 0L)
        jSONObject.put("avg_router_time", paramPerformanceEntity.avgRouterTime); 
      if (paramPerformanceEntity.cpuMonitorCount > 0)
        jSONObject.put("avg_cpu_ratio", paramPerformanceEntity.aveCpuRate); 
      if (paramPerformanceEntity.secondaryCpuRate >= 0) {
        jSONObject.put("secondary_cpu_rate", paramPerformanceEntity.secondaryCpuRate);
        jSONObject.put("avg_cpu_front", paramPerformanceEntity.avgCpuFront);
        jSONObject.put("cpu_threshold_times", paramPerformanceEntity.cpuThresholdTimes);
      } 
      if (paramPerformanceEntity.fpsMonitorCount > 0) {
        jSONObject.put("min_fps", paramPerformanceEntity.minFps);
        jSONObject.put("avg_fps", paramPerformanceEntity.aveFps);
        jSONObject.put("fps_threshold", paramPerformanceEntity.fpsThreshold);
      } 
      if (paramPerformanceEntity.memoryMonitorCount > 0) {
        jSONObject.put("max_memory_allocation", paramPerformanceEntity.maxMemory);
        jSONObject.put("avg_memory_ratio", paramPerformanceEntity.aveMemoryRate);
        if (paramPerformanceEntity.isBackground)
          bool = true; 
        jSONObject.put("is_background", bool);
        jSONObject.put("max_dalvik_pss", paramPerformanceEntity.maxDalvikPss);
        jSONObject.put("avg_dalvik_pss", paramPerformanceEntity.aveDalvikPss);
        jSONObject.put("max_native_pss", paramPerformanceEntity.maxNativePss);
        jSONObject.put("avg_native_pss", paramPerformanceEntity.aveNativePss);
        jSONObject.put("max_total_pss", paramPerformanceEntity.maxTotalPss);
        jSONObject.put("avg_total_pss", paramPerformanceEntity.aveTotalPss);
        jSONObject.put("avg_other_pss", paramPerformanceEntity.avgOtherPss);
      } 
      if (!mIsFirstPack.get()) {
        if (paramPerformanceEntity.aveGameFps > 0)
          jSONObject.put("avg_game_fps", paramPerformanceEntity.aveGameFps); 
        if (paramPerformanceEntity.aveDrawCall > 0)
          jSONObject.put("avg_draw_call", paramPerformanceEntity.aveDrawCall); 
        if (paramPerformanceEntity.aveTri > 0)
          jSONObject.put("avg_tri", paramPerformanceEntity.aveTri); 
        if (paramPerformanceEntity.aveVert > 0)
          jSONObject.put("avg_vert", paramPerformanceEntity.aveVert); 
      } 
      return jSONObject;
    } catch (Exception exception) {
      return jSONObject;
    } 
  }
  
  private int getThresholdCPU(PerformanceEntity paramPerformanceEntity) {
    return paramPerformanceEntity.isGame ? 80 : 55;
  }
  
  private int getThresholdFps(PerformanceEntity paramPerformanceEntity) {
    return paramPerformanceEntity.isGame ? 24 : 54;
  }
  
  private boolean isIntervalTimeOut() {
    long l;
    if (mIsFirstPack.get() && mCountTime.incrementAndGet() < 3L)
      return false; 
    if (DebugUtil.debug()) {
      l = 6L;
    } else {
      l = 12L;
    } 
    if (!mIsFirstPack.get() && mCountTime.incrementAndGet() < l)
      return false; 
    mCountTime.set(1);
    mIsFirstPack.set(false);
    return true;
  }
  
  private void mpPerformance(PerformanceEntity paramPerformanceEntity) {
    JSONObject jSONObject = getPerformanceJSONObject(paramPerformanceEntity);
    AppBrandLogger.d("tma_MonitorInfoPackTask", new Object[] { jSONObject.toString() });
    Event.builder("mp_performance_report").addKVJsonObject(jSONObject).flush();
    StringBuilder stringBuilder = new StringBuilder("avgCpuFront: ");
    stringBuilder.append(jSONObject.optInt("avg_cpu_front", -1));
    AppBrandLogger.d("tma_MonitorInfoPackTask", new Object[] { stringBuilder.toString() });
  }
  
  public static void registerFeedback(IFeedback paramIFeedback) {
    sIFeedback = paramIFeedback;
  }
  
  private void resetList() {
    synchronized (sCpuUseRates) {
      sCpuUseRates.clear();
      synchronized (sFpsList) {
        sFpsList.clear();
        synchronized (sMemoryInfoList) {
          sMemoryInfoList.clear();
          synchronized (sGameDataMap) {
            sGameDataMap.clear();
            return;
          } 
        } 
      } 
    } 
  }
  
  private void setGameDrawCall(PerformanceEntity paramPerformanceEntity) {
    synchronized (sGameDataMap) {
      List<Integer> list = (List)sGameDataMap.get(1);
      if (list != null && list.size() > 0)
        paramPerformanceEntity.aveDrawCall = calculateAverage(list); 
      return;
    } 
  }
  
  private void setGameFps(PerformanceEntity paramPerformanceEntity) {
    synchronized (sGameDataMap) {
      List<Integer> list = (List)sGameDataMap.get(0);
      if (list != null && list.size() > 0)
        paramPerformanceEntity.aveGameFps = calculateAverage(list); 
      return;
    } 
  }
  
  private void setGameTri(PerformanceEntity paramPerformanceEntity) {
    synchronized (sGameDataMap) {
      List<Integer> list = (List)sGameDataMap.get(3);
      if (list != null && list.size() > 0)
        paramPerformanceEntity.aveTri = calculateAverage(list); 
      return;
    } 
  }
  
  private void setGameVert(PerformanceEntity paramPerformanceEntity) {
    synchronized (sGameDataMap) {
      List<Integer> list = (List)sGameDataMap.get(2);
      if (list != null && list.size() > 0)
        paramPerformanceEntity.aveVert = calculateAverage(list); 
      return;
    } 
  }
  
  public static void unregisterFeedback() {
    sIFeedback = null;
  }
  
  public void execute() {
    super.execute();
    IFeedback iFeedback = sIFeedback;
    if (iFeedback != null)
      iFeedback.onPerformanceReport(feedbackPerformance()); 
  }
  
  protected void executeActual() {
    if (!isReportPerformance) {
      AppBrandLogger.e("tma_MonitorInfoPackTask", new Object[] { "isReportPerformance not support" });
      return;
    } 
    if (!isIntervalTimeOut())
      return; 
    PerformanceEntity performanceEntity1 = calPerformance(false);
    PerformanceEntity performanceEntity2 = calPerformance(true);
    resetList();
    if (performanceEntity1.cpuMonitorCount > 0 || performanceEntity1.fpsMonitorCount > 0 || performanceEntity1.memoryMonitorCount > 0)
      mpPerformance(performanceEntity1); 
    if (performanceEntity2.cpuMonitorCount > 0 || performanceEntity2.fpsMonitorCount > 0 || performanceEntity2.memoryMonitorCount > 0)
      mpPerformance(performanceEntity2); 
  }
  
  public static interface IFeedback {
    void onPerformanceReport(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\MonitorInfoPackTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */