package com.tt.miniapp.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import java.util.LinkedList;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class TimeLogger extends AppbrandServiceManager.ServiceBase implements Handler.Callback {
  private volatile Handler mH;
  
  private volatile HandlerThread mHt;
  
  private int mIndex;
  
  private long mIntervalTime = this.mStartTime;
  
  private String mLastErrorLog;
  
  private int mLogCount;
  
  private LinkedList<LogRecord> mPendingLogs;
  
  private final long mStartTime = System.currentTimeMillis();
  
  private boolean mStarted;
  
  private boolean mStopFlush;
  
  private TimeLogger(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private void addPendingLog(LogRecord paramLogRecord) {
    if (this.mPendingLogs == null)
      this.mPendingLogs = new LinkedList<LogRecord>(); 
    this.mPendingLogs.addLast(paramLogRecord);
  }
  
  private void flushPendingLogs() {
    LinkedList<LogRecord> linkedList = this.mPendingLogs;
    if (linkedList != null && !linkedList.isEmpty())
      mpLog(formatLogs(this.mPendingLogs)); 
  }
  
  private String formatLogs(LinkedList<LogRecord> paramLinkedList) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private long getDurationTime() {
    return System.currentTimeMillis() - this.mStartTime;
  }
  
  public static TimeLogger getInstance() {
    return (TimeLogger)AppbrandApplicationImpl.getInst().getService(TimeLogger.class);
  }
  
  private long getInterval() {
    long l1 = System.currentTimeMillis();
    long l2 = this.mIntervalTime;
    this.mIntervalTime = l1;
    return l1 - l2;
  }
  
  private boolean isTimerAlive() {
    return (this.mHt != null && this.mHt.isAlive() && this.mH != null);
  }
  
  private void mpLog(String paramString) {
    byte b;
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("logContent", paramString);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("TimeLogger", new Object[] { "", jSONException });
    } 
    if (AppbrandContext.getInst().isGame()) {
      b = 20;
    } else {
      b = 10;
    } 
    int i = b;
    if (!TextUtils.isEmpty(this.mLastErrorLog))
      i = b + 1; 
    AppBrandMonitor.statusRate("mp_launch_loggers", i, jSONObject);
  }
  
  private void postLog(boolean paramBoolean, String[] paramArrayOfString) {
    LogRecord logRecord = new LogRecord(paramBoolean, System.currentTimeMillis(), getInterval(), getDurationTime(), paramArrayOfString);
    this.mH.obtainMessage(10, logRecord).sendToTarget();
  }
  
  private void printLog(LogRecord paramLogRecord) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String str1 : paramLogRecord.msgs) {
      stringBuilder.append(" ");
      stringBuilder.append(str1);
    } 
    String str = a.a(Locale.getDefault(), "%s [% 4d],[% 6d] %s", new Object[] { AppbrandContext.getInst().getUniqueId(), Long.valueOf(paramLogRecord.interval), Long.valueOf(paramLogRecord.duration), stringBuilder.toString() });
    if (paramLogRecord.isErrorLog) {
      AppBrandLogger.e("TimeLogger", new Object[] { str });
      return;
    } 
    AppBrandLogger.i("TimeLogger", new Object[] { str });
  }
  
  private void start() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mHt : Landroid/os/HandlerThread;
    //   4: ifnonnull -> 59
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield mHt : Landroid/os/HandlerThread;
    //   13: ifnonnull -> 49
    //   16: aload_0
    //   17: ldc 'TimeLogger'
    //   19: invokestatic getNewHandlerThread : (Ljava/lang/String;)Landroid/os/HandlerThread;
    //   22: putfield mHt : Landroid/os/HandlerThread;
    //   25: aload_0
    //   26: new android/os/Handler
    //   29: dup
    //   30: aload_0
    //   31: getfield mHt : Landroid/os/HandlerThread;
    //   34: invokevirtual getLooper : ()Landroid/os/Looper;
    //   37: aload_0
    //   38: invokespecial <init> : (Landroid/os/Looper;Landroid/os/Handler$Callback;)V
    //   41: putfield mH : Landroid/os/Handler;
    //   44: aload_0
    //   45: iconst_1
    //   46: putfield mStarted : Z
    //   49: aload_0
    //   50: monitorexit
    //   51: goto -> 59
    //   54: astore_1
    //   55: aload_0
    //   56: monitorexit
    //   57: aload_1
    //   58: athrow
    //   59: invokestatic debug : ()Z
    //   62: ifeq -> 79
    //   65: ldc 'TimeLogger'
    //   67: iconst_1
    //   68: anewarray java/lang/Object
    //   71: dup
    //   72: iconst_0
    //   73: ldc 'start tiktok'
    //   75: aastore
    //   76: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   79: return
    // Exception table:
    //   from	to	target	type
    //   9	49	54	finally
    //   49	51	54	finally
    //   55	57	54	finally
  }
  
  public String getLastErrorLog() {
    return this.mLastErrorLog;
  }
  
  public long getStartTimeStamp() {
    return this.mStartTime;
  }
  
  public boolean handleMessage(Message paramMessage) {
    LogRecord logRecord;
    if (paramMessage.what == 10) {
      logRecord = (LogRecord)paramMessage.obj;
      printLog(logRecord);
      int i = this.mLogCount;
      if (i <= 200 && !this.mStopFlush) {
        this.mLogCount = i + 1;
        addPendingLog(logRecord);
        if (logRecord.isErrorLog)
          flushPendingLogs(); 
      } 
      if (logRecord.isErrorLog)
        this.mLastErrorLog = logRecord.getMsgForShort(); 
      return true;
    } 
    if (((Message)logRecord).what == 11) {
      AppBrandLogger.d("TimeLogger", new Object[] { "schedule flush" });
      if (!this.mStopFlush) {
        logRecord = new LogRecord(false, System.currentTimeMillis(), getInterval(), getDurationTime(), new String[] { "TimeLogger_OvertimeFlush" });
        printLog(logRecord);
        addPendingLog(logRecord);
        flushPendingLogs();
      } 
      return true;
    } 
    if (((Message)logRecord).what == 12) {
      if (!TextUtils.isEmpty(this.mLastErrorLog)) {
        flushPendingLogs();
      } else {
        this.mPendingLogs.clear();
      } 
      this.mH.removeMessages(11);
      this.mStopFlush = true;
      return true;
    } 
    return false;
  }
  
  public void logError(String... paramVarArgs) {
    if (!this.mStarted)
      start(); 
    if (isTimerAlive())
      postLog(true, paramVarArgs); 
  }
  
  public void logTimeDuration(String... paramVarArgs) {
    if (!this.mStarted)
      start(); 
    if (isTimerAlive())
      postLog(false, paramVarArgs); 
  }
  
  public void scheduleFlush() {
    if (!this.mStarted)
      start(); 
    if (isTimerAlive()) {
      if (AppbrandContext.getInst().isGame()) {
        this.mH.sendEmptyMessageDelayed(11, 8000L);
        this.mH.sendEmptyMessageDelayed(11, 15000L);
        return;
      } 
      this.mH.sendEmptyMessageDelayed(11, 5000L);
      this.mH.sendEmptyMessageDelayed(11, 10000L);
    } 
  }
  
  public void stopScheduleFlush() {
    AppBrandLogger.d("TimeLogger", new Object[] { "call stop schedule flush" });
    if (isTimerAlive())
      this.mH.sendEmptyMessage(12); 
  }
  
  class LogRecord {
    long duration;
    
    long interval;
    
    boolean isErrorLog;
    
    long logTime;
    
    String[] msgs;
    
    LogRecord(boolean param1Boolean, long param1Long1, long param1Long2, long param1Long3, String[] param1ArrayOfString) {
      this.isErrorLog = param1Boolean;
      this.logTime = param1Long1;
      this.interval = param1Long2;
      this.duration = param1Long3;
      this.msgs = param1ArrayOfString;
    }
    
    public String getMsgForShort() {
      String[] arrayOfString = this.msgs;
      return (arrayOfString != null && arrayOfString.length > 0) ? arrayOfString[0] : "";
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\TimeLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */