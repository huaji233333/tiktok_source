package com.tt.miniapp.monitor;

import android.content.Context;
import android.widget.TextView;
import com.tt.miniapphost.AppBrandLogger;

public class DebugMonitorTextView extends TextView {
  private int mDebugCPU;
  
  private int mDebugFPS;
  
  private long mDebugMem;
  
  public DebugMonitorTextView(Context paramContext) {
    super(paramContext);
  }
  
  public void update() {
    StringBuffer stringBuffer = new StringBuffer();
    if (MonitorInfoPackTask.getLastCpuRate() > 0)
      this.mDebugCPU = MonitorInfoPackTask.getLastCpuRate(); 
    if (MonitorInfoPackTask.getLastFps() > 0)
      this.mDebugFPS = MonitorInfoPackTask.getLastFps(); 
    if (MonitorInfoPackTask.getLastMemoryPss() > 0)
      this.mDebugMem = MonitorInfoPackTask.getLastMemoryPss(); 
    stringBuffer.append("FPS:");
    stringBuffer.append(this.mDebugFPS);
    stringBuffer.append(" Mem:");
    stringBuffer.append(this.mDebugMem);
    if (this.mDebugCPU > 0) {
      stringBuffer.append(" CPU:");
      stringBuffer.append(this.mDebugCPU);
      stringBuffer.append("%");
    } 
    AppBrandLogger.d("performance ", new Object[] { stringBuffer.toString() });
    setText(stringBuffer);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\DebugMonitorTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */