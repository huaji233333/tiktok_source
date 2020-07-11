package com.tt.miniapp.streamloader;

import android.os.Handler;
import android.os.Message;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.event.Event;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileAccessLogger extends AppbrandServiceManager.ServiceBase implements Handler.Callback {
  private Set<String> mAccessedFiles;
  
  private Handler mHandler;
  
  private List<FileAccessRecord> mRecentAccessedFile;
  
  private long mStartTime = System.currentTimeMillis();
  
  private String mUniqueId;
  
  private FileAccessLogger(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(UUID.randomUUID().toString().substring(0, 6));
    stringBuilder.append(System.currentTimeMillis());
    this.mUniqueId = stringBuilder.toString();
    this.mAccessedFiles = new HashSet<String>();
    this.mRecentAccessedFile = new ArrayList<FileAccessRecord>();
  }
  
  private void collectAndReport() {
    this.mHandler.removeMessages(5000);
    try {
      if (!this.mRecentAccessedFile.isEmpty()) {
        JSONArray jSONArray = new JSONArray();
        Iterator<FileAccessRecord> iterator = this.mRecentAccessedFile.iterator();
        while (iterator.hasNext())
          jSONArray.put(((FileAccessRecord)iterator.next()).toJSONObject()); 
        this.mRecentAccessedFile.clear();
        AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
        if (appInfoEntity == null) {
          AppBrandLogger.e("tma_FileAccessLogger", new Object[] { "AppInfo is null!", new Throwable() });
          return;
        } 
        Event.builder("mp_stream_load_files_index").kv("app_id", appInfoEntity.appId).kv("version", appInfoEntity.version).kv("version_type", appInfoEntity.versionType).kv("version_code", Long.valueOf(appInfoEntity.versionCode)).kv("unique_id", this.mUniqueId).kv("files", jSONArray.toString()).flush();
        return;
      } 
      AppBrandLogger.w("tma_FileAccessLogger", new Object[] { "RecentAccessedFile is empty!" });
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_FileAccessLogger", "collectAndReport", exception);
      return;
    } 
  }
  
  public boolean handleMessage(Message paramMessage) {
    if (paramMessage.what == 5000) {
      collectAndReport();
      return true;
    } 
    if (paramMessage.what == 5001) {
      String str = (String)paramMessage.obj;
      int i = paramMessage.arg1;
      if (this.mAccessedFiles.contains(str))
        return true; 
      FileAccessRecord fileAccessRecord = new FileAccessRecord();
      fileAccessRecord.name = str;
      fileAccessRecord.index = i;
      this.mAccessedFiles.add(fileAccessRecord.name);
      this.mRecentAccessedFile.add(fileAccessRecord);
      if (this.mRecentAccessedFile.size() >= 30) {
        this.mHandler.sendEmptyMessage(5000);
        return true;
      } 
      this.mHandler.sendEmptyMessageDelayed(5000, 3000L);
      return true;
    } 
    return false;
  }
  
  public void logFileAccess(String paramString) {
    logFileAccess(paramString, System.currentTimeMillis());
  }
  
  public void logFileAccess(String paramString, long paramLong) {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   4: ifeq -> 33
    //   7: ldc 'tma_FileAccessLogger'
    //   9: iconst_2
    //   10: anewarray java/lang/Object
    //   13: dup
    //   14: iconst_0
    //   15: ldc_w 'FileName is null!'
    //   18: aastore
    //   19: dup
    //   20: iconst_1
    //   21: new java/lang/Throwable
    //   24: dup
    //   25: invokespecial <init> : ()V
    //   28: aastore
    //   29: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   32: return
    //   33: aload_0
    //   34: getfield mAccessedFiles : Ljava/util/Set;
    //   37: aload_1
    //   38: invokeinterface contains : (Ljava/lang/Object;)Z
    //   43: ifeq -> 47
    //   46: return
    //   47: aload_0
    //   48: getfield mHandler : Landroid/os/Handler;
    //   51: ifnonnull -> 91
    //   54: aload_0
    //   55: monitorenter
    //   56: aload_0
    //   57: getfield mHandler : Landroid/os/Handler;
    //   60: ifnonnull -> 81
    //   63: aload_0
    //   64: new android/os/Handler
    //   67: dup
    //   68: invokestatic getBackgroundHandlerThread : ()Landroid/os/HandlerThread;
    //   71: invokevirtual getLooper : ()Landroid/os/Looper;
    //   74: aload_0
    //   75: invokespecial <init> : (Landroid/os/Looper;Landroid/os/Handler$Callback;)V
    //   78: putfield mHandler : Landroid/os/Handler;
    //   81: aload_0
    //   82: monitorexit
    //   83: goto -> 91
    //   86: astore_1
    //   87: aload_0
    //   88: monitorexit
    //   89: aload_1
    //   90: athrow
    //   91: aload_0
    //   92: getfield mHandler : Landroid/os/Handler;
    //   95: sipush #5001
    //   98: lload_2
    //   99: aload_0
    //   100: getfield mStartTime : J
    //   103: lsub
    //   104: l2i
    //   105: iconst_0
    //   106: aload_1
    //   107: invokevirtual obtainMessage : (IIILjava/lang/Object;)Landroid/os/Message;
    //   110: invokevirtual sendToTarget : ()V
    //   113: return
    // Exception table:
    //   from	to	target	type
    //   56	81	86	finally
    //   81	83	86	finally
    //   87	89	86	finally
  }
  
  class FileAccessRecord {
    public int index;
    
    public String name;
    
    private FileAccessRecord() {}
    
    public JSONObject toJSONObject() throws JSONException {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("index", this.index);
      jSONObject.put("name", this.name);
      return jSONObject;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\FileAccessLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */