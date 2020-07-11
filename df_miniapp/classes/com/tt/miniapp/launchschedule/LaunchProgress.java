package com.tt.miniapp.launchschedule;

import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;

class LaunchProgress implements Handler.Callback {
  private int mCurrentProgress;
  
  private volatile Handler mHandler;
  
  private ILaunchProgressListener mListener;
  
  private long mOpenSchemaCpuTime = -1L;
  
  private StatusRecord[] mStatusRecords;
  
  private volatile boolean mStopped;
  
  private int mTargetProgress;
  
  LaunchProgress() {
    ILaunchStatus.Status[] arrayOfStatus = ILaunchStatus.statusArray();
    this.mStatusRecords = new StatusRecord[arrayOfStatus.length];
    for (int i = 0; i < arrayOfStatus.length; i++)
      this.mStatusRecords[i] = new StatusRecord(arrayOfStatus[i]); 
    (this.mStatusRecords[ILaunchStatus.STATUS_INIT]).arrived = true;
  }
  
  private void tick() {
    if (!this.mStopped) {
      if (this.mHandler == null)
        return; 
      if (this.mHandler != null)
        this.mHandler.obtainMessage(1001).sendToTarget(); 
    } 
  }
  
  public ArrayMap<String, Long> getDurationForOpen() {
    ArrayMap<String, Long> arrayMap = new ArrayMap();
    if (this.mOpenSchemaCpuTime > 0L) {
      int i = 1;
      while (true) {
        StatusRecord[] arrayOfStatusRecord = this.mStatusRecords;
        if (i < arrayOfStatusRecord.length) {
          StatusRecord statusRecord = arrayOfStatusRecord[i];
          long l2 = -1L;
          long l1 = l2;
          if (statusRecord.arrived) {
            l1 = l2;
            if (statusRecord.mCpuTimeStamp > 0L)
              l1 = statusRecord.mCpuTimeStamp - this.mOpenSchemaCpuTime; 
          } 
          arrayMap.put(statusRecord.mName, Long.valueOf(l1));
          i++;
          continue;
        } 
        break;
      } 
    } 
    return arrayMap;
  }
  
  int getProgress() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mTargetProgress : I
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public boolean handleMessage(Message paramMessage) {
    if (paramMessage.what == 1001) {
      this.mHandler.removeMessages(1001);
      int i = this.mCurrentProgress;
      int j = this.mTargetProgress;
      if (i < j) {
        this.mCurrentProgress = Math.min(i + (j - i) / 5 + 2, j);
        ILaunchProgressListener iLaunchProgressListener = this.mListener;
        if (!this.mStopped && iLaunchProgressListener != null)
          iLaunchProgressListener.onProgressChanged(this.mCurrentProgress); 
        this.mHandler.sendEmptyMessageDelayed(1001, 40L);
      } 
      return true;
    } 
    return false;
  }
  
  public void setOpenSchemaTime(long paramLong) {
    this.mOpenSchemaCpuTime = paramLong;
  }
  
  void start(ILaunchProgressListener paramILaunchProgressListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mStopped : Z
    //   6: ifne -> 56
    //   9: aload_0
    //   10: getfield mHandler : Landroid/os/Handler;
    //   13: ifnull -> 19
    //   16: goto -> 56
    //   19: aload_0
    //   20: aload_1
    //   21: putfield mListener : Lcom/tt/miniapp/launchschedule/ILaunchProgressListener;
    //   24: aload_0
    //   25: new android/os/Handler
    //   28: dup
    //   29: invokestatic getMainLooper : ()Landroid/os/Looper;
    //   32: aload_0
    //   33: invokespecial <init> : (Landroid/os/Looper;Landroid/os/Handler$Callback;)V
    //   36: putfield mHandler : Landroid/os/Handler;
    //   39: aload_0
    //   40: getfield mHandler : Landroid/os/Handler;
    //   43: sipush #1001
    //   46: ldc2_w 40
    //   49: invokevirtual sendEmptyMessageDelayed : (IJ)Z
    //   52: pop
    //   53: aload_0
    //   54: monitorexit
    //   55: return
    //   56: aload_0
    //   57: monitorexit
    //   58: return
    //   59: astore_1
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_1
    //   63: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	59	finally
    //   19	53	59	finally
  }
  
  void stop() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_1
    //   4: putfield mStopped : Z
    //   7: aload_0
    //   8: getfield mHandler : Landroid/os/Handler;
    //   11: ifnull -> 27
    //   14: aload_0
    //   15: getfield mHandler : Landroid/os/Handler;
    //   18: aconst_null
    //   19: invokevirtual removeCallbacksAndMessages : (Ljava/lang/Object;)V
    //   22: aload_0
    //   23: aconst_null
    //   24: putfield mListener : Lcom/tt/miniapp/launchschedule/ILaunchProgressListener;
    //   27: aload_0
    //   28: monitorexit
    //   29: return
    //   30: astore_1
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_1
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   2	27	30	finally
  }
  
  void updateStatus(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mStatusRecords : [Lcom/tt/miniapp/launchschedule/LaunchProgress$StatusRecord;
    //   6: iload_1
    //   7: aaload
    //   8: getfield mCpuTimeStamp : J
    //   11: lconst_0
    //   12: lcmp
    //   13: ifge -> 28
    //   16: aload_0
    //   17: getfield mStatusRecords : [Lcom/tt/miniapp/launchschedule/LaunchProgress$StatusRecord;
    //   20: iload_1
    //   21: aaload
    //   22: invokestatic elapsedRealtime : ()J
    //   25: putfield mCpuTimeStamp : J
    //   28: aload_0
    //   29: getfield mStatusRecords : [Lcom/tt/miniapp/launchschedule/LaunchProgress$StatusRecord;
    //   32: iload_1
    //   33: aaload
    //   34: getfield arrived : Z
    //   37: ifne -> 72
    //   40: aload_0
    //   41: getfield mStatusRecords : [Lcom/tt/miniapp/launchschedule/LaunchProgress$StatusRecord;
    //   44: iload_1
    //   45: aaload
    //   46: iconst_1
    //   47: putfield arrived : Z
    //   50: aload_0
    //   51: aload_0
    //   52: getfield mTargetProgress : I
    //   55: aload_0
    //   56: getfield mStatusRecords : [Lcom/tt/miniapp/launchschedule/LaunchProgress$StatusRecord;
    //   59: iload_1
    //   60: aaload
    //   61: getfield mScore : I
    //   64: iadd
    //   65: putfield mTargetProgress : I
    //   68: aload_0
    //   69: invokespecial tick : ()V
    //   72: ldc 'LaunchProgress'
    //   74: iconst_4
    //   75: anewarray java/lang/Object
    //   78: dup
    //   79: iconst_0
    //   80: ldc 'updateStatus'
    //   82: aastore
    //   83: dup
    //   84: iconst_1
    //   85: aload_0
    //   86: getfield mStatusRecords : [Lcom/tt/miniapp/launchschedule/LaunchProgress$StatusRecord;
    //   89: iload_1
    //   90: aaload
    //   91: getfield mName : Ljava/lang/String;
    //   94: aastore
    //   95: dup
    //   96: iconst_2
    //   97: ldc ' progress'
    //   99: aastore
    //   100: dup
    //   101: iconst_3
    //   102: aload_0
    //   103: getfield mTargetProgress : I
    //   106: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   109: aastore
    //   110: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   113: aload_0
    //   114: monitorexit
    //   115: return
    //   116: astore_2
    //   117: aload_0
    //   118: monitorexit
    //   119: aload_2
    //   120: athrow
    // Exception table:
    //   from	to	target	type
    //   2	28	116	finally
    //   28	72	116	finally
    //   72	113	116	finally
  }
  
  void updateStatus(int paramInt, long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iload_1
    //   4: invokevirtual updateStatus : (I)V
    //   7: aload_0
    //   8: getfield mStatusRecords : [Lcom/tt/miniapp/launchschedule/LaunchProgress$StatusRecord;
    //   11: iload_1
    //   12: aaload
    //   13: getfield mCpuTimeStamp : J
    //   16: lconst_0
    //   17: lcmp
    //   18: ifle -> 41
    //   21: aload_0
    //   22: getfield mStatusRecords : [Lcom/tt/miniapp/launchschedule/LaunchProgress$StatusRecord;
    //   25: iload_1
    //   26: aaload
    //   27: astore #4
    //   29: aload #4
    //   31: aload #4
    //   33: getfield mCpuTimeStamp : J
    //   36: lload_2
    //   37: ladd
    //   38: putfield mCpuTimeStamp : J
    //   41: aload_0
    //   42: monitorexit
    //   43: return
    //   44: astore #4
    //   46: aload_0
    //   47: monitorexit
    //   48: aload #4
    //   50: athrow
    // Exception table:
    //   from	to	target	type
    //   2	41	44	finally
  }
  
  class StatusRecord extends ILaunchStatus.Status {
    public boolean arrived;
    
    public long mCpuTimeStamp = -1L;
    
    StatusRecord(ILaunchStatus.Status param1Status) {
      super(param1Status.mIdx, param1Status.mScore, param1Status.mName);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchschedule\LaunchProgress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */