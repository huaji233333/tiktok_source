package com.tt.miniapp.streamloader.cache;

import android.os.SystemClock;
import com.tt.miniapp.ttapkgdecoder.TTAPkgFile;
import com.tt.miniapphost.util.IOUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ConcurrentHashMap;

public class ContentCache {
  private volatile boolean isRelease;
  
  private ConcurrentHashMap<String, ByteObject> mCache;
  
  private final File mReadOnlyFile;
  
  public ContentCache(File paramFile, int paramInt) {
    this.mReadOnlyFile = paramFile;
    this.mCache = new ConcurrentHashMap<String, ByteObject>();
  }
  
  private byte[] getOrWait0(TTAPkgFile paramTTAPkgFile, GetOrWaitMpObject paramGetOrWaitMpObject) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getFileName : ()Ljava/lang/String;
    //   4: astore #5
    //   6: new java/lang/StringBuilder
    //   9: dup
    //   10: ldc 'getOrWait_Request: '
    //   12: invokespecial <init> : (Ljava/lang/String;)V
    //   15: astore_3
    //   16: aload_3
    //   17: aload #5
    //   19: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   22: pop
    //   23: ldc 'ContentCache'
    //   25: iconst_1
    //   26: anewarray java/lang/Object
    //   29: dup
    //   30: iconst_0
    //   31: aload_3
    //   32: invokevirtual toString : ()Ljava/lang/String;
    //   35: aastore
    //   36: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   39: aload_0
    //   40: getfield mCache : Ljava/util/concurrent/ConcurrentHashMap;
    //   43: aload #5
    //   45: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   48: checkcast com/tt/miniapp/streamloader/cache/ContentCache$ByteObject
    //   51: astore #4
    //   53: aload #4
    //   55: astore_3
    //   56: aload #4
    //   58: ifnonnull -> 93
    //   61: new com/tt/miniapp/streamloader/cache/ContentCache$ByteObject
    //   64: dup
    //   65: aload_0
    //   66: invokespecial <init> : (Lcom/tt/miniapp/streamloader/cache/ContentCache;)V
    //   69: astore_3
    //   70: aload_0
    //   71: getfield mCache : Ljava/util/concurrent/ConcurrentHashMap;
    //   74: aload #5
    //   76: aload_3
    //   77: invokevirtual putIfAbsent : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   80: checkcast com/tt/miniapp/streamloader/cache/ContentCache$ByteObject
    //   83: astore #4
    //   85: aload #4
    //   87: ifnull -> 93
    //   90: aload #4
    //   92: astore_3
    //   93: aload_3
    //   94: getfield bytes : [B
    //   97: ifnull -> 144
    //   100: new java/lang/StringBuilder
    //   103: dup
    //   104: ldc 'getOrWait_Got1: '
    //   106: invokespecial <init> : (Ljava/lang/String;)V
    //   109: astore_1
    //   110: aload_1
    //   111: aload #5
    //   113: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: pop
    //   117: ldc 'ContentCache'
    //   119: iconst_1
    //   120: anewarray java/lang/Object
    //   123: dup
    //   124: iconst_0
    //   125: aload_1
    //   126: invokevirtual toString : ()Ljava/lang/String;
    //   129: aastore
    //   130: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   133: aload_2
    //   134: ldc 'memory'
    //   136: putfield loadFrom : Ljava/lang/String;
    //   139: aload_3
    //   140: getfield bytes : [B
    //   143: areturn
    //   144: aload_3
    //   145: monitorenter
    //   146: aload_3
    //   147: getfield bytes : [B
    //   150: ifnull -> 201
    //   153: new java/lang/StringBuilder
    //   156: dup
    //   157: ldc 'getOrWait_Got2: '
    //   159: invokespecial <init> : (Ljava/lang/String;)V
    //   162: astore_1
    //   163: aload_1
    //   164: aload #5
    //   166: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: pop
    //   170: ldc 'ContentCache'
    //   172: iconst_1
    //   173: anewarray java/lang/Object
    //   176: dup
    //   177: iconst_0
    //   178: aload_1
    //   179: invokevirtual toString : ()Ljava/lang/String;
    //   182: aastore
    //   183: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   186: aload_2
    //   187: ldc 'memory_locked'
    //   189: putfield loadFrom : Ljava/lang/String;
    //   192: aload_3
    //   193: getfield bytes : [B
    //   196: astore_1
    //   197: aload_3
    //   198: monitorexit
    //   199: aload_1
    //   200: areturn
    //   201: aload_0
    //   202: getfield mReadOnlyFile : Ljava/io/File;
    //   205: ifnull -> 265
    //   208: aload_3
    //   209: aload_0
    //   210: aload_1
    //   211: invokespecial readFromDisk : (Lcom/tt/miniapp/ttapkgdecoder/TTAPkgFile;)[B
    //   214: putfield bytes : [B
    //   217: new java/lang/StringBuilder
    //   220: dup
    //   221: ldc 'getOrWait_Got3: '
    //   223: invokespecial <init> : (Ljava/lang/String;)V
    //   226: astore_1
    //   227: aload_1
    //   228: aload #5
    //   230: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: pop
    //   234: ldc 'ContentCache'
    //   236: iconst_1
    //   237: anewarray java/lang/Object
    //   240: dup
    //   241: iconst_0
    //   242: aload_1
    //   243: invokevirtual toString : ()Ljava/lang/String;
    //   246: aastore
    //   247: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   250: aload_2
    //   251: ldc 'disk_locked'
    //   253: putfield loadFrom : Ljava/lang/String;
    //   256: aload_3
    //   257: getfield bytes : [B
    //   260: astore_1
    //   261: aload_3
    //   262: monitorexit
    //   263: aload_1
    //   264: areturn
    //   265: aload_2
    //   266: ldc 'wait'
    //   268: putfield loadFrom : Ljava/lang/String;
    //   271: aload_3
    //   272: getfield bytes : [B
    //   275: astore_1
    //   276: aload_1
    //   277: ifnonnull -> 338
    //   280: aload_3
    //   281: ldc2_w 3000
    //   284: invokevirtual wait : (J)V
    //   287: aload_3
    //   288: getfield bytes : [B
    //   291: ifnonnull -> 271
    //   294: aload_2
    //   295: ldc 'timeout'
    //   297: putfield status : Ljava/lang/String;
    //   300: aload_2
    //   301: aload_0
    //   302: getfield isRelease : Z
    //   305: putfield isReleased : Z
    //   308: invokestatic getInstance : ()Lcom/tt/miniapp/util/TimeLogger;
    //   311: iconst_2
    //   312: anewarray java/lang/String
    //   315: dup
    //   316: iconst_0
    //   317: ldc 'ContentCache_getOrWait_timeout'
    //   319: aastore
    //   320: dup
    //   321: iconst_1
    //   322: aload #5
    //   324: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   327: aastore
    //   328: invokevirtual logError : ([Ljava/lang/String;)V
    //   331: aload_2
    //   332: invokevirtual report : ()V
    //   335: goto -> 271
    //   338: new java/lang/StringBuilder
    //   341: dup
    //   342: ldc 'getOrWait_Got4: '
    //   344: invokespecial <init> : (Ljava/lang/String;)V
    //   347: astore_1
    //   348: aload_1
    //   349: aload #5
    //   351: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   354: pop
    //   355: ldc 'ContentCache'
    //   357: iconst_1
    //   358: anewarray java/lang/Object
    //   361: dup
    //   362: iconst_0
    //   363: aload_1
    //   364: invokevirtual toString : ()Ljava/lang/String;
    //   367: aastore
    //   368: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   371: aload_3
    //   372: getfield bytes : [B
    //   375: astore_1
    //   376: aload_3
    //   377: monitorexit
    //   378: aload_1
    //   379: areturn
    //   380: astore_1
    //   381: aload_3
    //   382: monitorexit
    //   383: goto -> 388
    //   386: aload_1
    //   387: athrow
    //   388: goto -> 386
    //   391: astore_1
    //   392: goto -> 287
    // Exception table:
    //   from	to	target	type
    //   146	199	380	finally
    //   201	263	380	finally
    //   265	271	380	finally
    //   271	276	380	finally
    //   280	287	391	java/lang/InterruptedException
    //   280	287	380	finally
    //   287	335	380	finally
    //   338	378	380	finally
    //   381	383	380	finally
  }
  
  private byte[] readFromDisk(TTAPkgFile paramTTAPkgFile) {
    byte[] arrayOfByte = new byte[paramTTAPkgFile.getSize()];
    Object object = null;
    Exception exception = null;
    try {
      RandomAccessFile randomAccessFile1;
      Exception exception1;
      RandomAccessFile randomAccessFile2 = new RandomAccessFile(this.mReadOnlyFile, "r");
      try {
        return arrayOfByte;
      } catch (IOException iOException) {
      
      } finally {
        exception = null;
        randomAccessFile1 = randomAccessFile2;
      } 
      IOUtils.close(randomAccessFile1);
      throw exception1;
    } catch (IOException iOException) {
    
    } finally {
      Exception exception1 = exception;
      IOUtils.close((Closeable)exception1);
    } 
    IOUtils.close((Closeable)SYNTHETIC_LOCAL_VARIABLE_2);
    return arrayOfByte;
  }
  
  public byte[] getOrWait(TTAPkgFile paramTTAPkgFile) {
    long l = SystemClock.elapsedRealtime();
    GetOrWaitMpObject getOrWaitMpObject = new GetOrWaitMpObject();
    getOrWaitMpObject.fileName = paramTTAPkgFile.getFileName();
    try {
      return getOrWait0(paramTTAPkgFile, getOrWaitMpObject);
    } finally {
      getOrWaitMpObject.useTime = String.valueOf(SystemClock.elapsedRealtime() - l);
      getOrWaitMpObject.status = "success";
      getOrWaitMpObject.report();
    } 
  }
  
  public void putAndNotify(String paramString, byte[] paramArrayOfbyte) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mCache : Ljava/util/concurrent/ConcurrentHashMap;
    //   4: aload_1
    //   5: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   8: checkcast com/tt/miniapp/streamloader/cache/ContentCache$ByteObject
    //   11: astore #4
    //   13: aload #4
    //   15: astore_3
    //   16: aload #4
    //   18: ifnonnull -> 49
    //   21: new com/tt/miniapp/streamloader/cache/ContentCache$ByteObject
    //   24: dup
    //   25: aload_0
    //   26: invokespecial <init> : (Lcom/tt/miniapp/streamloader/cache/ContentCache;)V
    //   29: astore_3
    //   30: aload_0
    //   31: getfield mCache : Ljava/util/concurrent/ConcurrentHashMap;
    //   34: aload_1
    //   35: aload_3
    //   36: invokevirtual putIfAbsent : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   39: checkcast com/tt/miniapp/streamloader/cache/ContentCache$ByteObject
    //   42: astore_1
    //   43: aload_1
    //   44: ifnull -> 49
    //   47: aload_1
    //   48: astore_3
    //   49: aload_3
    //   50: aload_2
    //   51: putfield bytes : [B
    //   54: aload_3
    //   55: monitorenter
    //   56: aload_3
    //   57: invokevirtual notifyAll : ()V
    //   60: aload_3
    //   61: monitorexit
    //   62: return
    //   63: astore_1
    //   64: aload_3
    //   65: monitorexit
    //   66: aload_1
    //   67: athrow
    // Exception table:
    //   from	to	target	type
    //   56	62	63	finally
    //   64	66	63	finally
  }
  
  public void release() {
    this.isRelease = true;
  }
  
  class ByteObject {
    volatile byte[] bytes;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\cache\ContentCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */