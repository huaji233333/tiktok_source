package com.tt.miniapp.jsbridge;

import android.content.ContextWrapper;
import com.he.SettingsProvider;
import com.he.jsbinding.JsContext;
import com.he.jsbinding.JsEngine;
import com.he.jsbinding.JsObject;
import com.he.jsbinding.JsScopedContext;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.JsRuntime;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.debug.SwitchManager;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.launchschedule.LaunchScheduler;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.DebugUtil;
import java.util.Locale;

public class JsTMARuntime extends JsRuntime {
  private ContextWrapper context;
  
  public JsTMARuntime(ContextWrapper paramContextWrapper, PreloadedJsContext paramPreloadedJsContext) {
    super(paramPreloadedJsContext);
    this.context = paramContextWrapper;
    start();
  }
  
  public void cleanup() {
    this.mLoader.cleanup();
  }
  
  public String getJsCoreFileName() {
    return "tma-core.js";
  }
  
  public void loadMainJs(final JsRuntime.MainJsLoadCallback mainJsLoadCallback) {
    TimeLogger.getInstance().logTimeDuration(new String[] { "JsTMARuntime_beforeLoadMainJs" });
    executeInJsThread(new JsContext.ScopeCallback() {
          public void run(JsScopedContext param1JsScopedContext) {
            boolean bool;
            String str;
            TimeLogger.getInstance().logTimeDuration(new String[] { "JsTMARuntime_runInJsThread2LoadMainJs" });
            JsObject jsObject = param1JsScopedContext.createObject();
            jsObject.set("debug", ((SwitchManager)AppbrandApplicationImpl.getInst().getService(SwitchManager.class)).isVConsoleSwitchOn());
            jsObject.set("platform", "android");
            if (JsEngine.type == JsEngine.Type.V8) {
              bool = true;
            } else {
              bool = false;
            } 
            jsObject.set("isArrayBufferSupport", bool);
            if (((MpTimeLineReporter)AppbrandApplicationImpl.getInst().getService(MpTimeLineReporter.class)).isJsEnableTrace()) {
              str = "1";
            } else {
              str = "0";
            } 
            jsObject.set("trace", str);
            Locale locale = LocaleManager.getInst().getCurrentLocale();
            if (locale != null)
              jsObject.set("lang", locale.getLanguage()); 
            param1JsScopedContext.global().set("nativeTMAConfig", jsObject);
            try {
              param1JsScopedContext.global().getObject("TMAConfig").callMethod("ready", 0);
            } catch (Exception exception) {
              DebugUtil.outputError("tma_JsTMARuntime", new Object[] { "get TMAConfig JsObject fail", exception });
              JsTMARuntime.this.showLoadError(ErrorCode.JSCORE.TMA_CONFIG_EXECUTE_ERROR.getCode());
            } 
            try {
              if (mainJsLoadCallback != null)
                mainJsLoadCallback.beforeEval(); 
              ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEvent("startAppService");
              param1JsScopedContext.eval("loadScript('app-service.js')", null);
              param1JsScopedContext.pop();
              ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEvent("stopAppService");
              if (mainJsLoadCallback != null)
                mainJsLoadCallback.afterEval(); 
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("tma_JsTMARuntime", new Object[] { exception });
              JsRuntime.MainJsLoadCallback mainJsLoadCallback = mainJsLoadCallback;
              if (mainJsLoadCallback != null)
                mainJsLoadCallback.evalException(exception); 
              JsTMARuntime.this.showLoadError(ErrorCode.JSCORE.MAIN_JS_NOT_FOUND.getCode());
              return;
            } 
          }
        });
  }
  
  public void run(JsScopedContext paramJsScopedContext) {
    this.mLoader.setup(this.context, (SettingsProvider)this);
    this.context = null;
    setupLoader();
    DebugManager.getInst().openDebugIfNeed(this.mJsThread.getJsContext());
    JsObject jsObject = paramJsScopedContext.createObject();
    jsObject.set("platform", "android");
    paramJsScopedContext.global().set("TMAConfig", jsObject);
    if (loadJsSdk(paramJsScopedContext))
      ((LaunchScheduler)AppbrandApplicationImpl.getInst().getService(LaunchScheduler.class)).onJsCoreLoaded(1); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\jsbridge\JsTMARuntime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */