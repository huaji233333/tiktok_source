package com.he;

import com.he.loader.Log;

public final class Monitor {
  public static Impl impl = new Impl() {
      public final void onAsyncCompile(int param1Int1, int param1Int2, int param1Int3) {
        StringBuilder stringBuilder = new StringBuilder("onAsyncCompile:");
        stringBuilder.append(param1Int1);
        stringBuilder.append(", duration:");
        stringBuilder.append(param1Int2);
        stringBuilder.append(" cache_size:");
        stringBuilder.append(param1Int3);
        Log.d("helium-monitor", new Object[] { stringBuilder.toString() });
      }
      
      public final void onAurumInitFail(int param1Int1, int param1Int2) {
        StringBuilder stringBuilder = new StringBuilder("onAurumInitFail:");
        stringBuilder.append(param1Int1);
        stringBuilder.append(", result:");
        stringBuilder.append(param1Int2);
        Log.e("helium-monitor", new Object[] { stringBuilder.toString() });
      }
      
      public final void onCameraOpenFail(int param1Int1, int param1Int2, Throwable param1Throwable) {
        StringBuilder stringBuilder = new StringBuilder("onCameraOpenFail:");
        stringBuilder.append(param1Int1);
        stringBuilder.append(", retries:");
        stringBuilder.append(param1Int2);
        Log.eWithThrowable("helium-monitor", stringBuilder.toString(), param1Throwable);
      }
      
      public final void onHeliumAddViewFail() {
        Log.e("helium-monitor", new Object[] { "onHeliumAddViewFail" });
      }
      
      public final void onHeliumSetupFail() {
        Log.e("helium-monitor", new Object[] { "onHeliumSetupFail" });
      }
      
      public final void onLoadEffectFail(Throwable param1Throwable) {
        Log.eWithThrowable("helium-monitor", "onLoadEffectFail", param1Throwable);
      }
      
      public final void onRTCLogReport(String param1String1, String param1String2) {
        StringBuilder stringBuilder = new StringBuilder("onRTCLogReport");
        stringBuilder.append(param1String1);
        stringBuilder.append(param1String2);
        Log.d("helium-monitor", new Object[] { stringBuilder.toString() });
      }
      
      public final void onSmashModelDownloadFail(String param1String, int param1Int, long param1Long, Throwable param1Throwable) {
        StringBuilder stringBuilder = new StringBuilder("onSmashModelDownload:");
        stringBuilder.append(param1String);
        stringBuilder.append(", status:");
        stringBuilder.append(param1Int);
        stringBuilder.append(", duration:");
        stringBuilder.append(param1Long);
        Log.eWithThrowable("helium-monitor", stringBuilder.toString(), param1Throwable);
      }
      
      public final void onSmashModelDownloadSuccess(String param1String, long param1Long) {
        StringBuilder stringBuilder = new StringBuilder("onSmashModelDownload:");
        stringBuilder.append(param1String);
        stringBuilder.append(", duration:");
        stringBuilder.append(param1Long);
        Log.d("helium-monitor", new Object[] { stringBuilder.toString() });
      }
      
      public final void onSmashModelMapFail(String param1String, Throwable param1Throwable) {
        StringBuilder stringBuilder = new StringBuilder("onSmashModelMap:");
        stringBuilder.append(param1String);
        Log.eWithThrowable("helium-monitor", stringBuilder.toString(), param1Throwable);
      }
    };
  
  public static interface Impl {
    void onAsyncCompile(int param1Int1, int param1Int2, int param1Int3);
    
    void onAurumInitFail(int param1Int1, int param1Int2);
    
    void onCameraOpenFail(int param1Int1, int param1Int2, Throwable param1Throwable);
    
    void onHeliumAddViewFail();
    
    void onHeliumSetupFail();
    
    void onLoadEffectFail(Throwable param1Throwable);
    
    void onRTCLogReport(String param1String1, String param1String2);
    
    void onSmashModelDownloadFail(String param1String, int param1Int, long param1Long, Throwable param1Throwable);
    
    void onSmashModelDownloadSuccess(String param1String, long param1Long);
    
    void onSmashModelMapFail(String param1String, Throwable param1Throwable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\Monitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */