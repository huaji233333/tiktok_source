package com.tt.miniapp.jsbridge;

import android.content.Context;
import android.content.ContextWrapper;
import com.he.JsRunLoop;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapphost.AppBrandLogger;

public class PreloadedJsContext implements Runnable {
  private final String bundle_dir;
  
  private final JsRunLoop loop;
  
  private int tma_script = -1;
  
  private int tmg_script = -1;
  
  PreloadedJsContext(ContextWrapper paramContextWrapper) {
    this.bundle_dir = AppbrandConstant.getJsBundleDir((Context)paramContextWrapper).getAbsolutePath();
    this.loop = new JsRunLoop();
    this.loop.post(this);
    this.loop.start();
  }
  
  private int compile(String paramString) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.bundle_dir);
      stringBuilder.append("/");
      return (arrayOfByte == null) ? -1 : this.loop.getJsContext().compile(arrayOfByte, paramString, true);
    } finally {
      paramString = null;
      AppBrandLogger.eWithThrowable("PreloadedJsContext", "compile failed", (Throwable)paramString);
    } 
  }
  
  public int get(String paramString) {
    int i;
    if (paramString.equals("tma-core.js")) {
      int j = this.tma_script;
      i = j;
      if (this.tmg_script != -1) {
        this.loop.getJsContext().releaseCompiledScript(this.tmg_script);
        i = j;
      } 
    } else {
      int j = this.tmg_script;
      i = j;
      if (this.tma_script != -1) {
        this.loop.getJsContext().releaseCompiledScript(this.tma_script);
        i = j;
      } 
    } 
    this.tmg_script = -1;
    this.tma_script = -1;
    return i;
  }
  
  public JsRunLoop getLoop() {
    return this.loop;
  }
  
  public void run() {
    this.tma_script = compile("tma-core.js");
    this.tmg_script = compile("tmg-core.js");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\jsbridge\PreloadedJsContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */