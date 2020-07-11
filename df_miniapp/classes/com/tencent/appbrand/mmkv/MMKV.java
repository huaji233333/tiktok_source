package com.tencent.appbrand.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MMKV implements SharedPreferences, SharedPreferences.Editor {
  private static MMKVHandler gCallbackHandler;
  
  private static b gContentChangeNotify;
  
  private static boolean gWantLogReDirecting;
  
  private static c[] index2LogLevel;
  
  private static boolean isInit;
  
  private static EnumMap<c, Integer> logLevel2Index;
  
  private static final HashMap<String, Parcelable.Creator<?>> mCreators;
  
  private static EnumMap<d, Integer> recoverIndex;
  
  private static String rootDir;
  
  private boolean isClose;
  
  private long nativeHandle;
  
  static {
    EnumMap<d, Object> enumMap = new EnumMap<d, Object>(d.class);
    recoverIndex = (EnumMap)enumMap;
    d d1 = d.OnErrorDiscard;
    Integer integer1 = Integer.valueOf(0);
    enumMap.put(d1, integer1);
    EnumMap<d, Integer> enumMap1 = recoverIndex;
    d d2 = d.OnErrorRecover;
    Integer integer2 = Integer.valueOf(1);
    enumMap1.put(d2, integer2);
    enumMap1 = (EnumMap)new EnumMap<Object, Integer>(c.class);
    logLevel2Index = (EnumMap)enumMap1;
    enumMap1.put(c.LevelDebug, integer1);
    logLevel2Index.put(c.LevelInfo, integer2);
    logLevel2Index.put(c.LevelWarning, Integer.valueOf(2));
    logLevel2Index.put(c.LevelError, Integer.valueOf(3));
    logLevel2Index.put(c.LevelNone, Integer.valueOf(4));
    index2LogLevel = new c[] { c.LevelDebug, c.LevelInfo, c.LevelWarning, c.LevelError, c.LevelNone };
    rootDir = null;
    mCreators = new HashMap<String, Parcelable.Creator<?>>();
    gWantLogReDirecting = false;
  }
  
  private MMKV(long paramLong) {
    this.nativeHandle = paramLong;
  }
  
  private native String[] allKeys();
  
  public static String byteToString(byte[] paramArrayOfbyte, String paramString) {
    return (paramArrayOfbyte == null || paramArrayOfbyte.length <= 0) ? "" : byteToStringNative(paramArrayOfbyte, paramString);
  }
  
  private static native String byteToStringNative(byte[] paramArrayOfbyte, String paramString);
  
  private native void clearAll();
  
  private native void clearMemoryCache();
  
  private native void close();
  
  private native boolean containsKey(long paramLong, String paramString);
  
  private native long count(long paramLong);
  
  private static native long createNB(int paramInt);
  
  public static e createNativeBuffer(int paramInt) {
    long l = createNB(paramInt);
    return (l <= 0L) ? null : new e(l, paramInt);
  }
  
  private native boolean decodeBool(long paramLong, String paramString, boolean paramBoolean);
  
  private native byte[] decodeBytes(long paramLong, String paramString);
  
  private native double decodeDouble(long paramLong, String paramString, double paramDouble);
  
  private native float decodeFloat(long paramLong, String paramString, float paramFloat);
  
  private native int decodeInt(long paramLong, String paramString, int paramInt);
  
  private native long decodeLong(long paramLong1, String paramString, long paramLong2);
  
  private native String decodeString(long paramLong, String paramString1, String paramString2);
  
  private native String[] decodeStringSet(long paramLong, String paramString);
  
  public static MMKV defaultMMKV() {
    if (rootDir != null)
      return new MMKV(getDefaultMMKV(1, null)); 
    throw new IllegalStateException("You should Call MMKV.initialize() first.");
  }
  
  public static MMKV defaultMMKV(int paramInt, String paramString) {
    if (rootDir != null)
      return new MMKV(getDefaultMMKV(paramInt, paramString)); 
    throw new IllegalStateException("You should Call MMKV.initialize() first.");
  }
  
  private static native void destroyNB(long paramLong, int paramInt);
  
  public static void destroyNativeBuffer(e parame) {
    destroyNB(parame.a, parame.b);
  }
  
  private native boolean encodeBool(long paramLong, String paramString, boolean paramBoolean);
  
  private native boolean encodeBytes(long paramLong, String paramString, byte[] paramArrayOfbyte);
  
  private native boolean encodeDouble(long paramLong, String paramString, double paramDouble);
  
  private native boolean encodeFloat(long paramLong, String paramString, float paramFloat);
  
  private native boolean encodeInt(long paramLong, String paramString, int paramInt);
  
  private native boolean encodeLong(long paramLong1, String paramString, long paramLong2);
  
  private native boolean encodeSet(long paramLong, String paramString, String[] paramArrayOfString);
  
  private native boolean encodeString(long paramLong, String paramString1, String paramString2);
  
  private static native long getDefaultMMKV(int paramInt, String paramString);
  
  private static native long getMMKVWithAshmemFD(String paramString1, int paramInt1, int paramInt2, String paramString2);
  
  private static native long getMMKVWithID(String paramString1, int paramInt, String paramString2, String paramString3);
  
  private static native long getMMKVWithIDAndSize(String paramString1, int paramInt1, int paramInt2, String paramString2);
  
  public static String getRootDir() {
    return rootDir;
  }
  
  public static String initialize(Context paramContext) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramContext.getFilesDir().getAbsolutePath());
    stringBuilder.append("/mmkv");
    return initialize(stringBuilder.toString(), null, c.LevelInfo);
  }
  
  public static String initialize(Context paramContext, c paramc) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramContext.getFilesDir().getAbsolutePath());
    stringBuilder.append("/mmkv");
    return initialize(stringBuilder.toString(), null, paramc);
  }
  
  public static String initialize(String paramString) {
    return initialize(paramString, null, c.LevelInfo);
  }
  
  public static String initialize(String paramString, a parama) {
    return initialize(paramString, parama, c.LevelInfo);
  }
  
  public static String initialize(String paramString, a parama, c paramc) {
    if (parama != null) {
      parama.loadLibrary("appbrand-mmkv");
    } else {
      a.a("appbrand-mmkv");
    } 
    rootDir = paramString;
    jniInitialize(paramString, logLevel2Int(paramc));
    isInit = true;
    return paramString;
  }
  
  public static String initialize(String paramString, c paramc) {
    return initialize(paramString, null, paramc);
  }
  
  private boolean isClose() {
    return this.isClose;
  }
  
  public static native boolean isFileValid(String paramString);
  
  public static boolean isInit() {
    return isInit;
  }
  
  private static native void jniInitialize(String paramString, int paramInt);
  
  private static int logLevel2Int(c paramc) {
    int i = null.a[paramc.ordinal()];
    byte b1 = 4;
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i != 5)
              return 1; 
          } else {
            return 3;
          } 
        } else {
          return 2;
        } 
      } else {
        return 1;
      } 
    } else {
      b1 = 0;
    } 
    return b1;
  }
  
  private static void mmkvLogImp(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3) {
    MMKVHandler mMKVHandler = gCallbackHandler;
    if (mMKVHandler != null && gWantLogReDirecting) {
      mMKVHandler.mmkvLog(index2LogLevel[paramInt1], paramString1, paramInt2, paramString2, paramString3);
      return;
    } 
    index2LogLevel[paramInt1].ordinal();
  }
  
  public static MMKV mmkvWithAshmemFD(String paramString1, int paramInt1, int paramInt2, String paramString2) {
    return new MMKV(getMMKVWithAshmemFD(paramString1, paramInt1, paramInt2, paramString2));
  }
  
  public static MMKV mmkvWithAshmemID(Context paramContext, String paramString1, int paramInt1, int paramInt2, String paramString2) {
    // Byte code:
    //   0: getstatic com/tencent/appbrand/mmkv/MMKV.rootDir : Ljava/lang/String;
    //   3: ifnull -> 432
    //   6: aload_0
    //   7: invokestatic myPid : ()I
    //   10: invokestatic a : (Landroid/content/Context;I)Ljava/lang/String;
    //   13: astore #5
    //   15: aconst_null
    //   16: astore #7
    //   18: aconst_null
    //   19: astore #6
    //   21: aload #5
    //   23: ifnull -> 421
    //   26: aload #5
    //   28: invokevirtual length : ()I
    //   31: ifne -> 37
    //   34: goto -> 421
    //   37: aload #5
    //   39: ldc_w ':'
    //   42: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   45: ifeq -> 393
    //   48: getstatic com/tencent/appbrand/mmkv/MMKVContentProvider.a : Landroid/net/Uri;
    //   51: ifnull -> 62
    //   54: getstatic com/tencent/appbrand/mmkv/MMKVContentProvider.a : Landroid/net/Uri;
    //   57: astore #5
    //   59: goto -> 121
    //   62: aload_0
    //   63: ifnonnull -> 72
    //   66: aconst_null
    //   67: astore #5
    //   69: goto -> 121
    //   72: aload_0
    //   73: invokestatic a : (Landroid/content/Context;)Ljava/lang/String;
    //   76: astore #5
    //   78: aload #5
    //   80: ifnonnull -> 86
    //   83: goto -> 66
    //   86: new java/lang/StringBuilder
    //   89: dup
    //   90: ldc_w 'content://'
    //   93: invokespecial <init> : (Ljava/lang/String;)V
    //   96: astore #8
    //   98: aload #8
    //   100: aload #5
    //   102: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: pop
    //   106: aload #8
    //   108: invokevirtual toString : ()Ljava/lang/String;
    //   111: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   114: astore #5
    //   116: aload #5
    //   118: putstatic com/tencent/appbrand/mmkv/MMKVContentProvider.a : Landroid/net/Uri;
    //   121: aload #5
    //   123: ifnonnull -> 137
    //   126: getstatic com/tencent/appbrand/mmkv/c.LevelError : Lcom/tencent/appbrand/mmkv/c;
    //   129: ldc_w 'MMKVContentProvider has invalid authority'
    //   132: invokestatic simpleLog : (Lcom/tencent/appbrand/mmkv/c;Ljava/lang/String;)V
    //   135: aconst_null
    //   136: areturn
    //   137: getstatic com/tencent/appbrand/mmkv/c.LevelInfo : Lcom/tencent/appbrand/mmkv/c;
    //   140: astore #8
    //   142: new java/lang/StringBuilder
    //   145: dup
    //   146: ldc_w 'getting parcelable mmkv in process, Uri = '
    //   149: invokespecial <init> : (Ljava/lang/String;)V
    //   152: astore #9
    //   154: aload #9
    //   156: aload #5
    //   158: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   161: pop
    //   162: aload #8
    //   164: aload #9
    //   166: invokevirtual toString : ()Ljava/lang/String;
    //   169: invokestatic simpleLog : (Lcom/tencent/appbrand/mmkv/c;Ljava/lang/String;)V
    //   172: new android/os/Bundle
    //   175: dup
    //   176: invokespecial <init> : ()V
    //   179: astore #8
    //   181: aload #8
    //   183: ldc_w 'KEY_SIZE'
    //   186: iload_2
    //   187: invokevirtual putInt : (Ljava/lang/String;I)V
    //   190: aload #8
    //   192: ldc_w 'KEY_MODE'
    //   195: iload_3
    //   196: invokevirtual putInt : (Ljava/lang/String;I)V
    //   199: aload #4
    //   201: ifnull -> 214
    //   204: aload #8
    //   206: ldc_w 'KEY_CRYPT'
    //   209: aload #4
    //   211: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   214: aload_0
    //   215: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   218: aload #5
    //   220: ldc_w 'mmkvFromAshmemID'
    //   223: aload_1
    //   224: aload #8
    //   226: invokevirtual call : (Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;Landroid/os/Bundle;)Landroid/os/Bundle;
    //   229: astore_0
    //   230: aload #7
    //   232: astore_1
    //   233: aload_0
    //   234: ifnull -> 391
    //   237: aload_0
    //   238: ldc_w com/tencent/appbrand/mmkv/ParcelableMMKV
    //   241: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   244: invokevirtual setClassLoader : (Ljava/lang/ClassLoader;)V
    //   247: aload_0
    //   248: ldc_w 'KEY'
    //   251: invokevirtual getParcelable : (Ljava/lang/String;)Landroid/os/Parcelable;
    //   254: checkcast com/tencent/appbrand/mmkv/ParcelableMMKV
    //   257: astore #4
    //   259: aload #7
    //   261: astore_1
    //   262: aload #4
    //   264: ifnull -> 391
    //   267: aload #6
    //   269: astore_0
    //   270: aload #4
    //   272: getfield b : I
    //   275: iflt -> 313
    //   278: aload #6
    //   280: astore_0
    //   281: aload #4
    //   283: getfield c : I
    //   286: iflt -> 313
    //   289: aload #4
    //   291: getfield a : Ljava/lang/String;
    //   294: aload #4
    //   296: getfield b : I
    //   299: aload #4
    //   301: getfield c : I
    //   304: aload #4
    //   306: getfield d : Ljava/lang/String;
    //   309: invokestatic mmkvWithAshmemFD : (Ljava/lang/String;IILjava/lang/String;)Lcom/tencent/appbrand/mmkv/MMKV;
    //   312: astore_0
    //   313: aload_0
    //   314: astore_1
    //   315: aload_0
    //   316: ifnull -> 391
    //   319: getstatic com/tencent/appbrand/mmkv/c.LevelInfo : Lcom/tencent/appbrand/mmkv/c;
    //   322: astore_1
    //   323: new java/lang/StringBuilder
    //   326: dup
    //   327: invokespecial <init> : ()V
    //   330: astore #4
    //   332: aload #4
    //   334: aload_0
    //   335: invokevirtual mmapID : ()Ljava/lang/String;
    //   338: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   341: pop
    //   342: aload #4
    //   344: ldc_w ' fd = '
    //   347: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   350: pop
    //   351: aload #4
    //   353: aload_0
    //   354: invokevirtual ashmemFD : ()I
    //   357: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   360: pop
    //   361: aload #4
    //   363: ldc_w ', meta fd = '
    //   366: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   369: pop
    //   370: aload #4
    //   372: aload_0
    //   373: invokevirtual ashmemMetaFD : ()I
    //   376: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   379: pop
    //   380: aload_1
    //   381: aload #4
    //   383: invokevirtual toString : ()Ljava/lang/String;
    //   386: invokestatic simpleLog : (Lcom/tencent/appbrand/mmkv/c;Ljava/lang/String;)V
    //   389: aload_0
    //   390: astore_1
    //   391: aload_1
    //   392: areturn
    //   393: getstatic com/tencent/appbrand/mmkv/c.LevelInfo : Lcom/tencent/appbrand/mmkv/c;
    //   396: ldc_w 'getting mmkv in main process'
    //   399: invokestatic simpleLog : (Lcom/tencent/appbrand/mmkv/c;Ljava/lang/String;)V
    //   402: new com/tencent/appbrand/mmkv/MMKV
    //   405: dup
    //   406: aload_1
    //   407: iload_2
    //   408: iload_3
    //   409: bipush #8
    //   411: ior
    //   412: aload #4
    //   414: invokestatic getMMKVWithIDAndSize : (Ljava/lang/String;IILjava/lang/String;)J
    //   417: invokespecial <init> : (J)V
    //   420: areturn
    //   421: getstatic com/tencent/appbrand/mmkv/c.LevelError : Lcom/tencent/appbrand/mmkv/c;
    //   424: ldc_w 'process name detect fail, try again later'
    //   427: invokestatic simpleLog : (Lcom/tencent/appbrand/mmkv/c;Ljava/lang/String;)V
    //   430: aconst_null
    //   431: areturn
    //   432: new java/lang/IllegalStateException
    //   435: dup
    //   436: ldc 'You should Call MMKV.initialize() first.'
    //   438: invokespecial <init> : (Ljava/lang/String;)V
    //   441: astore_0
    //   442: goto -> 447
    //   445: aload_0
    //   446: athrow
    //   447: goto -> 445
  }
  
  public static MMKV mmkvWithID(String paramString) {
    if (rootDir != null)
      return new MMKV(getMMKVWithID(paramString, 1, null, null)); 
    throw new IllegalStateException("You should Call MMKV.initialize() first.");
  }
  
  public static MMKV mmkvWithID(String paramString, int paramInt) {
    if (rootDir != null)
      return new MMKV(getMMKVWithID(paramString, paramInt, null, null)); 
    throw new IllegalStateException("You should Call MMKV.initialize() first.");
  }
  
  public static MMKV mmkvWithID(String paramString1, int paramInt, String paramString2) {
    if (rootDir != null)
      return new MMKV(getMMKVWithID(paramString1, paramInt, paramString2, null)); 
    throw new IllegalStateException("You should Call MMKV.initialize() first.");
  }
  
  public static MMKV mmkvWithID(String paramString1, int paramInt, String paramString2, String paramString3) {
    if (rootDir != null) {
      long l = getMMKVWithID(paramString1, paramInt, paramString2, paramString3);
      return (l == 0L) ? null : new MMKV(l);
    } 
    throw new IllegalStateException("You should Call MMKV.initialize() first.");
  }
  
  public static MMKV mmkvWithID(String paramString1, String paramString2) {
    if (rootDir != null) {
      long l = getMMKVWithID(paramString1, 1, null, paramString2);
      return (l == 0L) ? null : new MMKV(l);
    } 
    throw new IllegalStateException("You should Call MMKV.initialize() first.");
  }
  
  private static void onContentChangedByOuterProcess(String paramString) {}
  
  public static native void onExit();
  
  private static int onMMKVCRCCheckFail(String paramString) {
    d d = d.OnErrorDiscard;
    MMKVHandler mMKVHandler = gCallbackHandler;
    if (mMKVHandler != null)
      d = mMKVHandler.onMMKVCRCCheckFail(paramString); 
    c c1 = c.LevelInfo;
    StringBuilder stringBuilder = new StringBuilder("Recover strategic for ");
    stringBuilder.append(paramString);
    stringBuilder.append(" is ");
    stringBuilder.append(d);
    simpleLog(c1, stringBuilder.toString());
    Integer integer = recoverIndex.get(d);
    return (integer == null) ? 0 : integer.intValue();
  }
  
  private static int onMMKVFileLengthError(String paramString) {
    d d = d.OnErrorDiscard;
    MMKVHandler mMKVHandler = gCallbackHandler;
    if (mMKVHandler != null)
      d = mMKVHandler.onMMKVFileLengthError(paramString); 
    c c1 = c.LevelInfo;
    StringBuilder stringBuilder = new StringBuilder("Recover strategic for ");
    stringBuilder.append(paramString);
    stringBuilder.append(" is ");
    stringBuilder.append(d);
    simpleLog(c1, stringBuilder.toString());
    Integer integer = recoverIndex.get(d);
    return (integer == null) ? 0 : integer.intValue();
  }
  
  public static native int pageSize();
  
  public static void registerContentChangeNotify(b paramb) {
    boolean bool;
    gContentChangeNotify = paramb;
    if (paramb != null) {
      bool = true;
    } else {
      bool = false;
    } 
    setWantsContentChangeNotify(bool);
  }
  
  public static void registerHandler(MMKVHandler paramMMKVHandler) {
    gCallbackHandler = paramMMKVHandler;
    if (paramMMKVHandler.wantLogRedirecting()) {
      setLogReDirecting(true);
      gWantLogReDirecting = true;
      return;
    } 
    setLogReDirecting(false);
    gWantLogReDirecting = false;
  }
  
  private native void removeValueForKey(long paramLong, String paramString);
  
  private native void removeValuesForKeys(String[] paramArrayOfString);
  
  private static native void setLogLevel(int paramInt);
  
  public static void setLogLevel(c paramc) {
    setLogLevel(logLevel2Int(paramc));
  }
  
  private static native void setLogReDirecting(boolean paramBoolean);
  
  private static native void setWantsContentChangeNotify(boolean paramBoolean);
  
  private static void simpleLog(c paramc, String paramString) {
    int i;
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    StackTraceElement stackTraceElement = arrayOfStackTraceElement[arrayOfStackTraceElement.length - 1];
    Integer integer = logLevel2Index.get(paramc);
    if (integer == null) {
      i = 0;
    } else {
      i = integer.intValue();
    } 
    mmkvLogImp(i, stackTraceElement.getFileName(), stackTraceElement.getLineNumber(), stackTraceElement.getMethodName(), paramString);
  }
  
  private native void sync(boolean paramBoolean);
  
  private native long totalSize(long paramLong);
  
  private native void trim();
  
  public static void unregisterContentChangeNotify() {
    gContentChangeNotify = null;
    setWantsContentChangeNotify(false);
  }
  
  public static void unregisterHandler() {
    gCallbackHandler = null;
    setLogReDirecting(false);
    gWantLogReDirecting = false;
  }
  
  private native int valueSize(long paramLong, String paramString, boolean paramBoolean);
  
  private native int writeValueToNB(long paramLong1, String paramString, long paramLong2, int paramInt);
  
  public String[] allKeysMMKV() {
    return isClose() ? new String[0] : allKeys();
  }
  
  public void apply() {
    if (!isClose()) {
      sync(false);
      return;
    } 
    simpleLog(c.LevelError, "mmkv is close, data is not get/set in expectations");
  }
  
  public native int ashmemFD();
  
  public native int ashmemMetaFD();
  
  public void async() {
    if (!isClose())
      sync(false); 
  }
  
  public native void checkContentChangedByOuterProcess();
  
  public native void checkReSetCryptKey(String paramString);
  
  public SharedPreferences.Editor clear() {
    if (!isClose())
      clearAll(); 
    return this;
  }
  
  public void clearAllMMKV() {
    if (isClose())
      return; 
    clearAll();
  }
  
  public void clearMemoryCacheMMKV() {
    if (isClose())
      return; 
    clearMemoryCache();
  }
  
  public void closeMMKV() {
    this.isClose = true;
    close();
  }
  
  public boolean commit() {
    if (!isClose()) {
      sync(true);
      return true;
    } 
    simpleLog(c.LevelError, "mmkv is close, data is not get/set in expectations");
    return true;
  }
  
  public boolean contains(String paramString) {
    return isClose() ? false : containsKey(paramString);
  }
  
  public boolean containsKey(String paramString) {
    return isClose() ? false : containsKey(this.nativeHandle, paramString);
  }
  
  public long count() {
    return isClose() ? 0L : count(this.nativeHandle);
  }
  
  public native String cryptKey();
  
  public boolean decodeBool(String paramString) {
    return isClose() ? false : decodeBool(this.nativeHandle, paramString, false);
  }
  
  public boolean decodeBool(String paramString, boolean paramBoolean) {
    return isClose() ? paramBoolean : decodeBool(this.nativeHandle, paramString, paramBoolean);
  }
  
  public byte[] decodeBytes(String paramString) {
    return isClose() ? null : decodeBytes(paramString, (byte[])null);
  }
  
  public byte[] decodeBytes(String paramString, byte[] paramArrayOfbyte) {
    if (isClose())
      return paramArrayOfbyte; 
    byte[] arrayOfByte = decodeBytes(this.nativeHandle, paramString);
    return (arrayOfByte != null) ? arrayOfByte : paramArrayOfbyte;
  }
  
  public double decodeDouble(String paramString) {
    return isClose() ? 0.0D : decodeDouble(this.nativeHandle, paramString, 0.0D);
  }
  
  public double decodeDouble(String paramString, double paramDouble) {
    return isClose() ? paramDouble : decodeDouble(this.nativeHandle, paramString, paramDouble);
  }
  
  public float decodeFloat(String paramString) {
    return isClose() ? 0.0F : decodeFloat(this.nativeHandle, paramString, 0.0F);
  }
  
  public float decodeFloat(String paramString, float paramFloat) {
    return isClose() ? paramFloat : decodeFloat(this.nativeHandle, paramString, paramFloat);
  }
  
  public int decodeInt(String paramString) {
    return isClose() ? 0 : decodeInt(this.nativeHandle, paramString, 0);
  }
  
  public int decodeInt(String paramString, int paramInt) {
    return isClose() ? paramInt : decodeInt(this.nativeHandle, paramString, paramInt);
  }
  
  public long decodeLong(String paramString) {
    return isClose() ? 0L : decodeLong(this.nativeHandle, paramString, 0L);
  }
  
  public long decodeLong(String paramString, long paramLong) {
    return isClose() ? paramLong : decodeLong(this.nativeHandle, paramString, paramLong);
  }
  
  public <T extends Parcelable> T decodeParcelable(String paramString, Class<T> paramClass) {
    return isClose() ? null : decodeParcelable(paramString, paramClass, null);
  }
  
  public <T extends Parcelable> T decodeParcelable(String paramString, Class<T> paramClass, T paramT) {
    if (isClose())
      return paramT; 
    if (paramClass == null)
      return paramT; 
    byte[] arrayOfByte = decodeBytes(this.nativeHandle, paramString);
    if (arrayOfByte == null)
      return paramT; 
    Parcel parcel = Parcel.obtain();
    parcel.unmarshall(arrayOfByte, 0, arrayOfByte.length);
    parcel.setDataPosition(0);
    try {
      String str = paramClass.toString();
      synchronized (mCreators) {
        Parcelable.Creator<?> creator2 = mCreators.get(str);
        Parcelable.Creator<?> creator1 = creator2;
        if (creator2 == null) {
          Parcelable.Creator<?> creator = (Parcelable.Creator)paramClass.getField("CREATOR").get(null);
          creator1 = creator;
          if (creator != null) {
            mCreators.put(str, creator);
            creator1 = creator;
          } 
        } 
        if (creator1 != null) {
          Parcelable parcelable = (Parcelable)creator1.createFromParcel(parcel);
          parcel.recycle();
          return (T)parcelable;
        } 
        StringBuilder stringBuilder = new StringBuilder("Parcelable protocol requires a non-null static Parcelable.Creator object called CREATOR on class ");
        stringBuilder.append(str);
        throw new Exception(stringBuilder.toString());
      } 
    } catch (Exception exception) {
      simpleLog(c.LevelError, exception.toString());
      parcel.recycle();
      return paramT;
    } finally {}
    parcel.recycle();
    throw arrayOfByte;
  }
  
  public String decodeString(String paramString) {
    return isClose() ? null : decodeString(this.nativeHandle, paramString, null);
  }
  
  public String decodeString(String paramString1, String paramString2) {
    return isClose() ? paramString2 : decodeString(this.nativeHandle, paramString1, paramString2);
  }
  
  public Set<String> decodeStringSet(String paramString) {
    return isClose() ? null : decodeStringSet(paramString, (Set<String>)null);
  }
  
  public Set<String> decodeStringSet(String paramString, Set<String> paramSet) {
    return isClose() ? paramSet : decodeStringSet(paramString, paramSet, (Class)HashSet.class);
  }
  
  public Set<String> decodeStringSet(String paramString, Set<String> paramSet, Class<? extends Set> paramClass) {
    if (isClose())
      return paramSet; 
    String[] arrayOfString = decodeStringSet(this.nativeHandle, paramString);
    if (arrayOfString == null)
      return paramSet; 
    try {
      Set<String> set = paramClass.newInstance();
      set.addAll(Arrays.asList(arrayOfString));
      return set;
    } catch (IllegalAccessException|InstantiationException illegalAccessException) {
      return paramSet;
    } 
  }
  
  public SharedPreferences.Editor edit() {
    return this;
  }
  
  public boolean encode(String paramString, double paramDouble) {
    return isClose() ? false : encodeDouble(this.nativeHandle, paramString, paramDouble);
  }
  
  public boolean encode(String paramString, float paramFloat) {
    return isClose() ? false : encodeFloat(this.nativeHandle, paramString, paramFloat);
  }
  
  public boolean encode(String paramString, int paramInt) {
    return isClose() ? false : encodeInt(this.nativeHandle, paramString, paramInt);
  }
  
  public boolean encode(String paramString, long paramLong) {
    return isClose() ? false : encodeLong(this.nativeHandle, paramString, paramLong);
  }
  
  public boolean encode(String paramString, Parcelable paramParcelable) {
    if (isClose())
      return false; 
    Parcel parcel = Parcel.obtain();
    paramParcelable.writeToParcel(parcel, paramParcelable.describeContents());
    byte[] arrayOfByte = parcel.marshall();
    parcel.recycle();
    return encodeBytes(this.nativeHandle, paramString, arrayOfByte);
  }
  
  public boolean encode(String paramString1, String paramString2) {
    return isClose() ? false : encodeString(this.nativeHandle, paramString1, paramString2);
  }
  
  public boolean encode(String paramString, Set<String> paramSet) {
    return isClose() ? false : encodeSet(this.nativeHandle, paramString, paramSet.<String>toArray(new String[0]));
  }
  
  public boolean encode(String paramString, boolean paramBoolean) {
    return isClose() ? false : encodeBool(this.nativeHandle, paramString, paramBoolean);
  }
  
  public boolean encode(String paramString, byte[] paramArrayOfbyte) {
    return isClose() ? false : encodeBytes(this.nativeHandle, paramString, paramArrayOfbyte);
  }
  
  public Map<String, ?> getAll() {
    throw new UnsupportedOperationException("use allKeys() instead, getAll() not implement because type-erasure inside mmkv");
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean) {
    return isClose() ? paramBoolean : decodeBool(this.nativeHandle, paramString, paramBoolean);
  }
  
  public byte[] getBytes(String paramString, byte[] paramArrayOfbyte) {
    return isClose() ? paramArrayOfbyte : decodeBytes(paramString, paramArrayOfbyte);
  }
  
  public float getFloat(String paramString, float paramFloat) {
    return isClose() ? paramFloat : decodeFloat(this.nativeHandle, paramString, paramFloat);
  }
  
  public int getInt(String paramString, int paramInt) {
    return isClose() ? paramInt : decodeInt(this.nativeHandle, paramString, paramInt);
  }
  
  public long getLong(String paramString, long paramLong) {
    return isClose() ? paramLong : decodeLong(this.nativeHandle, paramString, paramLong);
  }
  
  public String getString(String paramString1, String paramString2) {
    return isClose() ? paramString2 : decodeString(this.nativeHandle, paramString1, paramString2);
  }
  
  public Set<String> getStringSet(String paramString, Set<String> paramSet) {
    return isClose() ? paramSet : decodeStringSet(paramString, paramSet);
  }
  
  public int getValueActualSize(String paramString) {
    return isClose() ? 0 : valueSize(this.nativeHandle, paramString, true);
  }
  
  public int getValueSize(String paramString) {
    return isClose() ? 0 : valueSize(this.nativeHandle, paramString, false);
  }
  
  public int importFromSharedPreferences(SharedPreferences paramSharedPreferences) {
    Map map = paramSharedPreferences.getAll();
    if (map == null || map.size() <= 0)
      return 0; 
    for (Map.Entry entry : map.entrySet()) {
      String str = (String)entry.getKey();
      entry = (Map.Entry)entry.getValue();
      if (str != null && entry != null) {
        if (entry instanceof Boolean) {
          encodeBool(this.nativeHandle, str, ((Boolean)entry).booleanValue());
          continue;
        } 
        if (entry instanceof Integer) {
          encodeInt(this.nativeHandle, str, ((Integer)entry).intValue());
          continue;
        } 
        if (entry instanceof Long) {
          encodeLong(this.nativeHandle, str, ((Long)entry).longValue());
          continue;
        } 
        if (entry instanceof Float) {
          encodeFloat(this.nativeHandle, str, ((Float)entry).floatValue());
          continue;
        } 
        if (entry instanceof Double) {
          encodeDouble(this.nativeHandle, str, ((Double)entry).doubleValue());
          continue;
        } 
        if (entry instanceof String) {
          encodeString(this.nativeHandle, str, (String)entry);
          continue;
        } 
        if (entry instanceof Set) {
          encode(str, (Set<String>)entry);
          continue;
        } 
        c c1 = c.LevelError;
        StringBuilder stringBuilder = new StringBuilder("unknown type: ");
        stringBuilder.append(entry.getClass());
        simpleLog(c1, stringBuilder.toString());
      } 
    } 
    return map.size();
  }
  
  public native void lock();
  
  public native String mmapID();
  
  public SharedPreferences.Editor putBoolean(String paramString, boolean paramBoolean) {
    if (!isClose())
      encodeBool(this.nativeHandle, paramString, paramBoolean); 
    return this;
  }
  
  public SharedPreferences.Editor putBytes(String paramString, byte[] paramArrayOfbyte) {
    if (!isClose())
      encode(paramString, paramArrayOfbyte); 
    return this;
  }
  
  public SharedPreferences.Editor putFloat(String paramString, float paramFloat) {
    if (!isClose())
      encodeFloat(this.nativeHandle, paramString, paramFloat); 
    return this;
  }
  
  public SharedPreferences.Editor putInt(String paramString, int paramInt) {
    if (!isClose())
      encodeInt(this.nativeHandle, paramString, paramInt); 
    return this;
  }
  
  public SharedPreferences.Editor putLong(String paramString, long paramLong) {
    if (!isClose())
      encodeLong(this.nativeHandle, paramString, paramLong); 
    return this;
  }
  
  public SharedPreferences.Editor putString(String paramString1, String paramString2) {
    if (!isClose())
      encodeString(this.nativeHandle, paramString1, paramString2); 
    return this;
  }
  
  public SharedPreferences.Editor putStringSet(String paramString, Set<String> paramSet) {
    if (!isClose())
      encode(paramString, paramSet); 
    return this;
  }
  
  public native boolean reKey(String paramString);
  
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {
    throw new UnsupportedOperationException("Not implement in MMKV");
  }
  
  public SharedPreferences.Editor remove(String paramString) {
    if (!isClose())
      removeValueForKey(paramString); 
    return this;
  }
  
  public void removeValueForKey(String paramString) {
    if (isClose())
      return; 
    removeValueForKey(this.nativeHandle, paramString);
  }
  
  public void removeValuesForKeysMMKV(String[] paramArrayOfString) {
    if (isClose())
      return; 
    removeValuesForKeys(paramArrayOfString);
  }
  
  public void sync() {
    if (!isClose())
      sync(true); 
  }
  
  public long totalSize() {
    return isClose() ? 0L : totalSize(this.nativeHandle);
  }
  
  public void trimMMKV() {
    if (isClose())
      return; 
    trim();
  }
  
  public native boolean tryLock();
  
  public native void unlock();
  
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {
    throw new UnsupportedOperationException("Not implement in MMKV");
  }
  
  public int writeValueToNativeBuffer(String paramString, e parame) {
    return writeValueToNB(this.nativeHandle, paramString, parame.a, parame.b);
  }
  
  public static interface a {
    void loadLibrary(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tencent\appbrand\mmkv\MMKV.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */