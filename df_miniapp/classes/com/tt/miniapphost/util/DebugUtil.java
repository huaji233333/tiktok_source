package com.tt.miniapphost.util;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import com.bytedance.sandboxapp.b.a.a.b;
import com.tt.miniapp.util.ChannelUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IAppbrandInitializer;
import com.tt.miniapphost.host.HostDependManager;
import java.io.File;

public class DebugUtil {
  private static boolean sDebug;
  
  public static boolean debug() {
    return sDebug;
  }
  
  public static void logOrThrow(String paramString, Object... paramVarArgs) {
    String str;
    if (debug() && ChannelUtil.isLocalTest()) {
      StringBuilder stringBuilder = new StringBuilder();
      int j = paramVarArgs.length;
      boolean bool = false;
      int i;
      for (i = 0; i < j; i++) {
        Object object = paramVarArgs[i];
        stringBuilder.append(" ");
        stringBuilder.append(object);
      } 
      str = stringBuilder.toString();
      i = bool;
      if (paramVarArgs.length > 0) {
        i = bool;
        if (paramVarArgs[paramVarArgs.length - 1] instanceof Throwable)
          i = 1; 
      } 
      if (i != 0)
        throw new RuntimeException(str, (Throwable)paramVarArgs[paramVarArgs.length - 1]); 
      throw new RuntimeException(str);
    } 
    AppBrandLogger.e(str, paramVarArgs);
  }
  
  public static void outputError(String paramString, Object... paramVarArgs) {
    if (debug() && ChannelUtil.isLocalTest()) {
      StringBuilder stringBuilder = new StringBuilder();
      int j = paramVarArgs.length;
      for (int i = 0; i < j; i++) {
        Object object = paramVarArgs[i];
        stringBuilder.append(" ");
        stringBuilder.append(object);
      } 
      Application application = AppbrandContext.getInst().getApplicationContext();
      if (application != null) {
        HostDependManager hostDependManager = HostDependManager.getInst();
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(UIUtils.getString(2097741935));
        stringBuilder1.append(stringBuilder.toString());
        hostDependManager.showToast((Context)application, null, stringBuilder1.toString(), 1L, null);
      } 
    } 
    AppBrandLogger.e(paramString, paramVarArgs);
  }
  
  private static boolean shouldDebug(Context paramContext, IAppbrandInitializer paramIAppbrandInitializer) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
      stringBuilder.append("/Android/data/");
      stringBuilder.append(paramContext.getPackageName());
      stringBuilder.append("/cache/");
      String str = stringBuilder.toString();
      stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append("debug.flag");
      boolean bool = (new File(stringBuilder.toString())).exists();
      if (bool)
        return true; 
    } catch (Exception exception) {
      AppBrandLogger.e("DebugUtil", new Object[] { "shouldDebug", exception.getStackTrace() });
    } 
    return (paramIAppbrandInitializer != null) ? paramIAppbrandInitializer.isDebug() : debug();
  }
  
  public static void updateDebugState(Context paramContext, IAppbrandInitializer paramIAppbrandInitializer) {
    boolean bool = shouldDebug(paramContext, paramIAppbrandInitializer);
    sDebug = bool;
    AppBrandLogger.setShowMoreLogInfo(bool);
    b.a(sDebug);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\DebugUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */