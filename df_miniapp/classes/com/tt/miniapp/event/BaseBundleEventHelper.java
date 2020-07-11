package com.tt.miniapp.event;

import android.util.Log;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import org.json.JSONObject;

public class BaseBundleEventHelper {
  public static BaseBundleEvent createEvent(String paramString1, String paramString2, String paramString3) {
    // Byte code:
    //   0: ldc com/tt/miniapp/event/BaseBundleEventHelper
    //   2: monitorenter
    //   3: new com/tt/miniapp/event/BaseBundleEventHelper$BaseBundleEvent
    //   6: dup
    //   7: aconst_null
    //   8: invokespecial <init> : (Lcom/tt/miniapp/event/BaseBundleEventHelper$1;)V
    //   11: astore_3
    //   12: aload_3
    //   13: invokestatic getProcessIdentify : ()Ljava/lang/String;
    //   16: invokevirtual setProcess : (Ljava/lang/String;)V
    //   19: aload_3
    //   20: aload_0
    //   21: invokevirtual setLaunchFromMethod : (Ljava/lang/String;)V
    //   24: aload_3
    //   25: aload_1
    //   26: invokevirtual setTrigger : (Ljava/lang/String;)V
    //   29: aload_3
    //   30: aload_2
    //   31: invokevirtual setCurrentVerison : (Ljava/lang/String;)V
    //   34: ldc com/tt/miniapp/event/BaseBundleEventHelper
    //   36: monitorexit
    //   37: aload_3
    //   38: areturn
    //   39: astore_0
    //   40: ldc com/tt/miniapp/event/BaseBundleEventHelper
    //   42: monitorexit
    //   43: aload_0
    //   44: athrow
    // Exception table:
    //   from	to	target	type
    //   3	34	39	finally
  }
  
  public static void monitor(BaseBundleEvent paramBaseBundleEvent) {
    /* monitor enter TypeReferenceDotClassExpression{ObjectType{com/tt/miniapp/event/BaseBundleEventHelper}} */
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("tma_trigger", paramBaseBundleEvent.getTrigger());
      jSONObject.put("tma_process", paramBaseBundleEvent.getProcess());
      jSONObject.put("tma_updateVersion", paramBaseBundleEvent.getUpdateVersion());
      jSONObject.put("tma_currentVersion", paramBaseBundleEvent.getCurrentVerison());
      jSONObject.put("tma_log", paramBaseBundleEvent.getLogs());
      jSONObject.put("tma_launchFromMethod", paramBaseBundleEvent.getLaunchFromMethod());
      AppBrandLogger.d("BaseBundleEventHelper", new Object[] { jSONObject.toString() });
      AppBrandMonitor.statusRate("mp_jssdk_change", 0, jSONObject);
      /* monitor exit TypeReferenceDotClassExpression{ObjectType{com/tt/miniapp/event/BaseBundleEventHelper}} */
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("BaseBundleEventHelper", new Object[] { exception });
      /* monitor exit TypeReferenceDotClassExpression{ObjectType{com/tt/miniapp/event/BaseBundleEventHelper}} */
      return;
    } finally {}
    /* monitor exit TypeReferenceDotClassExpression{ObjectType{com/tt/miniapp/event/BaseBundleEventHelper}} */
    throw paramBaseBundleEvent;
  }
  
  public static class BaseBundleEvent {
    private String currentVersion;
    
    private String launchFromMethod;
    
    private StringBuffer logs = new StringBuffer();
    
    private String process;
    
    private String trigger;
    
    private String updateVersion;
    
    private BaseBundleEvent() {}
    
    public void appendLog(String param1String) {
      StringBuffer stringBuffer2 = getLogs();
      StringBuffer stringBuffer1 = stringBuffer2;
      if (stringBuffer2 == null) {
        stringBuffer1 = new StringBuffer();
        setLogs(stringBuffer1);
      } 
      stringBuffer1.append(" | trace:");
      stringBuffer1.append(param1String);
    }
    
    public void appendLog(String param1String, Throwable param1Throwable) {
      StringBuffer stringBuffer2 = getLogs();
      StringBuffer stringBuffer1 = stringBuffer2;
      if (stringBuffer2 == null) {
        stringBuffer1 = new StringBuffer();
        setLogs(stringBuffer1);
      } 
      stringBuffer1.append(" | trace: ");
      stringBuffer1.append(param1String);
      stringBuffer1.append("exception:");
      stringBuffer1.append(Log.getStackTraceString(param1Throwable));
    }
    
    public String getCurrentVerison() {
      return this.currentVersion;
    }
    
    public String getLaunchFromMethod() {
      return this.launchFromMethod;
    }
    
    public StringBuffer getLogs() {
      return this.logs;
    }
    
    public String getProcess() {
      return this.process;
    }
    
    public String getTrigger() {
      return this.trigger;
    }
    
    public String getUpdateVersion() {
      return this.updateVersion;
    }
    
    public void setCurrentVerison(String param1String) {
      this.currentVersion = param1String;
    }
    
    public void setLaunchFromMethod(String param1String) {
      this.launchFromMethod = param1String;
    }
    
    public void setLogs(StringBuffer param1StringBuffer) {
      this.logs = param1StringBuffer;
    }
    
    public void setProcess(String param1String) {
      this.process = param1String;
    }
    
    public void setTrigger(String param1String) {
      this.trigger = param1String;
    }
    
    public void setUpdateVersion(String param1String) {
      this.updateVersion = param1String;
    }
  }
  
  public static interface Result {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\BaseBundleEventHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */