package com.tt.miniapp.monitor;

import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.IOUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;

public class CpuMonitorTask extends BaseMonitorTask {
  private int mCpuIndex = -1;
  
  private int mPidIndex = -1;
  
  public CpuMonitorTask() {
    super(10000L);
  }
  
  public CpuMonitorTask(long paramLong) {
    super(paramLong);
  }
  
  private long getTotalCpuTime() {
    String str = readFirstLine("/proc/stat");
    if (!TextUtils.isEmpty(str))
      try {
        String[] arrayOfString = str.split(" ");
        int j = arrayOfString.length;
        long l = 0L;
        for (int i = 2; i < j; i++) {
          long l1 = Long.parseLong(arrayOfString[i]);
          l += l1;
        } 
        return l;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "CpuMonitorTask", exception.getStackTrace());
      }  
    return -1L;
  }
  
  private long getTotalProcessCpuTime() {
    int i = Process.myPid();
    StringBuilder stringBuilder = new StringBuilder("/proc/");
    stringBuilder.append(i);
    stringBuilder.append("/stat");
    String str = readFirstLine(stringBuilder.toString());
    if (!TextUtils.isEmpty(str))
      try {
        String[] arrayOfString = str.split(" ");
        long l = 0L;
        for (i = 13; i <= 16; i++) {
          long l1 = Long.parseLong(arrayOfString[i]);
          l += l1;
        } 
        return l;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "CpuMonitorTask", exception.getStackTrace());
      }  
    return -1L;
  }
  
  private String readFirstLine(String paramString) {
    Exception exception1;
    Exception exception2;
    BufferedReader bufferedReader1;
    String str1 = null;
    BufferedReader bufferedReader2 = null;
    try {
      BufferedReader bufferedReader;
    } catch (Exception null) {
    
    } finally {
      bufferedReader2 = null;
      exception1 = null;
      exception2 = exception1;
      paramString = str1;
    } 
    BufferedReader bufferedReader3 = bufferedReader1;
    String str2 = paramString;
    Exception exception3 = exception2;
    AppBrandLogger.stacktrace(6, "CpuMonitorTask", exception1.getStackTrace());
    IOUtils.close((Closeable)paramString);
    IOUtils.close((Closeable)exception2);
    IOUtils.close(bufferedReader1);
    return null;
  }
  
  protected void executeActual() {
    if (Build.VERSION.SDK_INT < 26) {
      long l1 = getTotalCpuTime();
      long l2 = getTotalProcessCpuTime();
      if (l1 > 0L && l2 > 0L)
        try {
          Thread.sleep(360L);
          long l3 = getTotalCpuTime();
          long l4 = getTotalProcessCpuTime();
          if (l3 > 0L && l4 > 0L) {
            int j = Math.round((float)(l4 - l2) * 1.0F / (float)(l3 - l1) * 100.0F);
            if (j > 0)
              MonitorInfoPackTask.addCpuRate(AppbrandApplicationImpl.getInst().getForeBackgroundManager().isBackground(), j); 
          } 
        } catch (InterruptedException interruptedException) {
          AppBrandLogger.stacktrace(6, "CpuMonitorTask", interruptedException.getStackTrace());
        } catch (Exception exception) {
          AppBrandLogger.stacktrace(6, "CpuMonitorTask", exception.getStackTrace());
        }  
    } 
    int i = getProcessCpuTime(Process.myPid());
    if (i >= 0) {
      MonitorInfoPackTask.addSecondaryTopRate(AppbrandApplicationImpl.getInst().getForeBackgroundManager().isBackground(), i);
      MonitorInfoPackTask.addSecondaryCpuRate(i);
    } 
  }
  
  public int getProcessCpuTime(int paramInt) {
    byte b1;
    StringBuilder stringBuilder2;
    byte b2 = -1;
    String str1 = null;
    String str2 = null;
    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[] { "sh", "-c", "top -n 1" }).getInputStream()));
      label61: while (true) {
        String str4;
        str1 = str2;
        String str3 = bufferedReader.readLine();
        b1 = b2;
        str2 = str3;
        if (str3 != null) {
          str1 = str3;
          b1 = b2;
          str2 = str3;
          if (!str3.equalsIgnoreCase("quit")) {
            str1 = str3;
            AppBrandLogger.d("CpuMonitorTask", new Object[] { str3 });
            str1 = str3;
            String[] arrayOfString = str3.replaceAll("\\[\\d[a-zA-Z]", "").trim().split(" +");
            str1 = str3;
            if (this.mCpuIndex == -1) {
              str1 = str3;
              if (this.mPidIndex == -1) {
                b1 = 0;
                while (true) {
                  str2 = str3;
                  str1 = str3;
                  if (b1 < arrayOfString.length) {
                    str2 = arrayOfString[b1];
                    str1 = str3;
                    if (str2.contains("PID")) {
                      str1 = str3;
                      this.mPidIndex = b1;
                    } 
                    str1 = str3;
                    if (str2.contains("CPU")) {
                      str1 = str3;
                      if (str2.substring(0, str2.indexOf("CPU")).contains("S")) {
                        str1 = str3;
                        this.mCpuIndex = b1 + 1;
                      } else {
                        str1 = str3;
                        this.mCpuIndex = b1;
                      } 
                    } 
                    b1++;
                    continue;
                  } 
                  continue label61;
                } 
                break;
              } 
            } 
            str2 = str3;
            str1 = str3;
            if (this.mPidIndex < arrayOfString.length) {
              str2 = str3;
              str1 = str3;
              if (this.mPidIndex >= 0) {
                str2 = str3;
                str1 = str3;
                if (arrayOfString[this.mPidIndex].equals(String.valueOf(paramInt))) {
                  str2 = str3;
                  str1 = str3;
                  if (this.mCpuIndex < arrayOfString.length) {
                    str2 = str3;
                    str1 = str3;
                    if (this.mCpuIndex >= 0) {
                      str1 = str3;
                      str2 = arrayOfString[this.mCpuIndex].replaceAll("%", "");
                      try {
                        float f = Float.valueOf(str2).floatValue();
                        b1 = (int)f;
                        str2 = str3;
                      } catch (Exception exception) {
                        str1 = str3;
                        StringBuilder stringBuilder = new StringBuilder("The cmd result is: ");
                        str1 = str3;
                        stringBuilder.append(str3);
                        str1 = str3;
                        AppBrandLogger.e("CpuMonitorTask", new Object[] { stringBuilder.toString() });
                        str1 = str3;
                        AppBrandLogger.eWithThrowable("CPU Motion", "Top shell response format exception:", exception);
                        str4 = str3;
                        continue;
                      } 
                    } else {
                      continue;
                    } 
                  } else {
                    continue;
                  } 
                } else {
                  continue;
                } 
              } else {
                continue;
              } 
            } else {
              continue;
            } 
          } 
        } 
        if (b1 > 2000) {
          StringBuilder stringBuilder = new StringBuilder("The cmd result is: ");
          stringBuilder.append(str4);
          AppBrandLogger.e("CpuMonitorTask", new Object[] { stringBuilder.toString() });
        } 
        stringBuilder1 = new StringBuilder("result: ");
        stringBuilder1.append(b1);
        AppBrandLogger.d("CpuMonitorTask", new Object[] { stringBuilder1.toString() });
        return b1;
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "CpuMonitorTask", exception.getStackTrace());
      stringBuilder2 = stringBuilder1;
      b1 = b2;
    } 
    if (b1 > 2000) {
      stringBuilder1 = new StringBuilder("The cmd result is: ");
      stringBuilder1.append((String)stringBuilder2);
      AppBrandLogger.e("CpuMonitorTask", new Object[] { stringBuilder1.toString() });
    } 
    StringBuilder stringBuilder1 = new StringBuilder("result: ");
    stringBuilder1.append(b1);
    AppBrandLogger.d("CpuMonitorTask", new Object[] { stringBuilder1.toString() });
    return b1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\CpuMonitorTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */