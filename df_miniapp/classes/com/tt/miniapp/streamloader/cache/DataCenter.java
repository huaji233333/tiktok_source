package com.tt.miniapp.streamloader.cache;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import com.tt.miniapp.thread.HandlerThreadUtil;
import com.tt.miniapp.ttapkgdecoder.TTAPkgFile;
import com.tt.miniapp.ttapkgdecoder.TTAPkgInfo;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class DataCenter {
  private volatile boolean isRelease;
  
  public ContentCache mContentCache;
  
  private Handler mH;
  
  private HandlerThread mHt;
  
  private File mReadOnlyFile;
  
  private StreamCache mStreamCache = new StreamCache();
  
  private final StringCache mStringCache;
  
  private TTAPkgInfo mTTAPkgInfo;
  
  public DataCenter(File paramFile, int paramInt) {
    if (paramFile != null && paramFile.exists()) {
      this.mReadOnlyFile = paramFile;
      this.mContentCache = new ContentCache(this.mReadOnlyFile, paramInt);
    } else {
      this.mContentCache = new ContentCache(this.mReadOnlyFile, 2147483647);
    } 
    this.mStringCache = new StringCache();
    this.mHt = HandlerThreadUtil.getNewHandlerThread("DataCenter");
    this.mH = new Handler(this.mHt.getLooper());
  }
  
  public byte[] getOrWait(TTAPkgFile paramTTAPkgFile) {
    String str1;
    String str2 = paramTTAPkgFile.getFileName();
    if (this.mTTAPkgInfo != null && !TextUtils.isEmpty(str2))
      return this.mContentCache.getOrWait(paramTTAPkgFile); 
    if (this.mTTAPkgInfo == null) {
      str1 = "ApkgInfo is null";
    } else {
      StringBuilder stringBuilder = new StringBuilder("fileName: ");
      stringBuilder.append(str2);
      str1 = stringBuilder.toString();
    } 
    AppBrandMonitor.reportError("getOrWait_null", str1, Log.getStackTraceString(new Throwable()));
    return null;
  }
  
  public InputStream getStream(TTAPkgFile paramTTAPkgFile) {
    byte[] arrayOfByte;
    File file = this.mReadOnlyFile;
    String str = "file null";
    if (file != null) {
      byte[] arrayOfByte1 = getOrWait(paramTTAPkgFile);
      arrayOfByte = arrayOfByte1;
      if (arrayOfByte1 == null) {
        if (paramTTAPkgFile != null)
          str = paramTTAPkgFile.getFileName(); 
        AppBrandLogger.e("tma_DataCenter", new Object[] { "ttapkgFile content null", str });
        arrayOfByte = new byte[0];
      } 
      return new ByteArrayInputStream(arrayOfByte);
    } 
    InputStream inputStream = this.mStreamCache.takeStream(paramTTAPkgFile);
    if (inputStream == null) {
      byte[] arrayOfByte1 = getOrWait(paramTTAPkgFile);
      arrayOfByte = arrayOfByte1;
      if (arrayOfByte1 == null) {
        if (paramTTAPkgFile != null)
          str = paramTTAPkgFile.getFileName(); 
        AppBrandLogger.e("tma_DataCenter", new Object[] { "ttapkgFile content null2", str });
        arrayOfByte = new byte[0];
      } 
      return new ByteArrayInputStream(arrayOfByte);
    } 
    return (InputStream)arrayOfByte;
  }
  
  public String getStringCache(String paramString, byte[] paramArrayOfbyte) {
    return this.mStringCache.convertString(paramString, paramArrayOfbyte);
  }
  
  public void onDecodePart(TTAPkgFile paramTTAPkgFile, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.mStreamCache.pushToStream(paramTTAPkgFile, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public void onDecodeStart(TTAPkgFile paramTTAPkgFile, byte[] paramArrayOfbyte) {
    this.mStreamCache.prepareStream(paramTTAPkgFile, paramArrayOfbyte);
  }
  
  public void onFileAvailable(final TTAPkgFile ttaPkgFile, final byte[] data) {
    if (this.isRelease)
      return; 
    this.mStreamCache.closeStream(ttaPkgFile);
    this.mH.post(new Runnable() {
          public void run() {
            TTAPkgFile tTAPkgFile = ttaPkgFile;
            if (tTAPkgFile != null) {
              AppBrandLogger.i("tma_DataCenter", new Object[] { "onFileAvailable", tTAPkgFile.getFileName() });
              String str = ttaPkgFile.getFileName();
              if (!TextUtils.isEmpty(str))
                DataCenter.this.mContentCache.putAndNotify(str, data); 
            } 
          }
        });
  }
  
  public void onHeaderAvailable(TTAPkgInfo paramTTAPkgInfo) {
    if (this.isRelease)
      return; 
    this.mTTAPkgInfo = paramTTAPkgInfo;
  }
  
  public void release() {
    this.isRelease = true;
    this.mContentCache.release();
    this.mStringCache.release();
    this.mStreamCache.release();
    HandlerThread handlerThread = this.mHt;
    if (handlerThread != null && handlerThread.isAlive())
      this.mHt.quitSafely(); 
    AppBrandLogger.i("tma_DataCenter", new Object[] { "DataCenter is released" });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\cache\DataCenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */