package com.tt.miniapp.monitor;

import android.webkit.ValueCallback;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.view.webcore.NestWebView;
import com.tt.miniapphost.AppBrandLogger;

public class WebviewStuckMonitor implements Runnable {
  private String mPageUrl = "";
  
  public long mPingCnt;
  
  public volatile long mResponseCnt;
  
  private boolean mStopped = true;
  
  private NestWebView mWebView;
  
  public WebviewStuckMonitor(NestWebView paramNestWebView) {
    this.mWebView = paramNestWebView;
  }
  
  private void checkAlive() {
    if (this.mWebView != null && !this.mStopped) {
      this.mPingCnt = this.mResponseCnt;
      NestWebView nestWebView = this.mWebView;
      ValueCallback<String> valueCallback = new ValueCallback<String>() {
          public void onReceiveValue(String param1String) {
            if (param1String.equals("true")) {
              WebviewStuckMonitor webviewStuckMonitor = WebviewStuckMonitor.this;
              webviewStuckMonitor.mResponseCnt = webviewStuckMonitor.mPingCnt + 1L;
            } 
          }
        };
      StringBuilder stringBuilder = new StringBuilder("WebviewStuckMonitor");
      stringBuilder.append(this.mPingCnt);
      nestWebView.evaluateJavascript("true;", valueCallback, stringBuilder.toString());
      ThreadUtil.runOnUIThread(this, 4000L);
    } 
  }
  
  public void run() {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void setPageUrl(String paramString) {
    this.mPageUrl = paramString;
  }
  
  public void start() {
    if (this.mStopped) {
      this.mStopped = false;
      checkAlive();
    } 
  }
  
  public void stop() {
    StringBuilder stringBuilder = new StringBuilder("stop:");
    stringBuilder.append(this.mWebView.hashCode());
    AppBrandLogger.d("WebviewStuckMonitor", new Object[] { stringBuilder.toString() });
    this.mStopped = true;
    ThreadUtil.cancelUIRunnable(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\WebviewStuckMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */