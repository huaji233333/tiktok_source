package com.tt.miniapp.permission;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Process;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.reflect.InvocationTargetException;

public final class MIUIAppOpsHelper {
  private static int checkOps(int paramInt, Context paramContext) {
    try {
      Class<?> clazz = Class.forName("android.app.AppOpsManager");
      AppOpsManager appOpsManager = (AppOpsManager)paramContext.getSystemService("appops");
      return ((Integer)clazz.getMethod("checkOp", new Class[] { int.class, int.class, String.class }).invoke(appOpsManager, new Object[] { Integer.valueOf(paramInt), Integer.valueOf(Process.myUid()), paramContext.getPackageName() })).intValue();
    } catch (InvocationTargetException invocationTargetException) {
      return 0;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "MIUIAppOpsHelper", exception.getStackTrace());
      return -1;
    } 
  }
  
  public static boolean isRejected(int paramInt, Context paramContext) {
    return (checkOps(paramInt, paramContext) == 1);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\MIUIAppOpsHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */