package com.tt.miniapp.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.storage.async.Action;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.activity.MoveHostFrontActivity;
import com.tt.miniapp.activity.OpenSchemaMiddleActivity;
import com.tt.miniapp.manager.HostActivityManager;
import com.tt.miniapp.manager.SnapshotManager;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.placeholder.MiniappTabActivity1;
import java.util.Iterator;
import java.util.List;

public class ActivityUtil {
  private static String TAG_MOVE_ACTIVITY_TO_FRONT_SILENT = "MoveActivityToFrontSilent";
  
  public static void changeToSilentHideActivityAnimation(Activity paramActivity) {
    if (Build.VERSION.SDK_INT >= 23) {
      paramActivity.overridePendingTransition(2131034239, 2131034239);
      return;
    } 
    paramActivity.overridePendingTransition(2131034238, 2131034238);
  }
  
  private static boolean checkActivityAtStackTopOver23(Context paramContext, Class paramClass) {
    if (paramContext != null) {
      if (paramClass == null)
        return false; 
      List<ActivityManager.AppTask> list = getAppTasks(paramContext);
      if (list != null && list.size() > 0)
        try {
          Iterator<ActivityManager.AppTask> iterator = list.iterator();
          while (iterator.hasNext()) {
            ComponentName componentName = (((ActivityManager.AppTask)iterator.next()).getTaskInfo()).topActivity;
            if (componentName != null)
              return TextUtils.equals(componentName.getClassName(), paramClass.getName()); 
          } 
        } catch (Exception exception) {
          AppBrandLogger.e("ActivityUtil", new Object[] { "tryJumpMiniApp checkTopActivity", exception });
        }  
      return true;
    } 
    return false;
  }
  
  private static List<ActivityManager.AppTask> getAppTasks(Context paramContext) {
    try {
      ActivityManager activityManager = (ActivityManager)paramContext.getSystemService("activity");
      if (activityManager != null)
        return activityManager.getAppTasks(); 
    } catch (Exception exception) {
      AppBrandLogger.e("ActivityUtil", new Object[] { exception });
    } 
    return null;
  }
  
  private static String getBaseIntentComponentName(ActivityManager.AppTask paramAppTask) {
    if (paramAppTask == null)
      return null; 
    ComponentName componentName = (paramAppTask.getTaskInfo()).baseIntent.getComponent();
    return (componentName != null) ? componentName.getClassName() : null;
  }
  
  private static String getMiniAppActivityPackageName() {
    String str = MiniappTabActivity1.class.getName();
    return str.substring(0, str.lastIndexOf("."));
  }
  
  public static ActivityManager.AppTask getMiniAppActivityTask(Context paramContext, Class paramClass) {
    if (paramContext != null) {
      if (paramClass == null)
        return null; 
      AppBrandLogger.d("ActivityUtil", new Object[] { "getMiniAppActivityTask" });
      List<ActivityManager.AppTask> list = getAppTasks(paramContext);
      if (list != null && list.size() > 0) {
        String str = paramClass.getName();
        try {
          for (ActivityManager.AppTask appTask : list) {
            boolean bool = TextUtils.equals(getBaseIntentComponentName(appTask), str);
            if (bool)
              return appTask; 
          } 
        } catch (Exception exception) {
          AppBrandLogger.e("ActivityUtil", new Object[] { "moveMiniAppActivityToFront checkMatchTask", exception });
        } 
      } 
    } 
    return null;
  }
  
  public static boolean isActivityAtHostStackTop(Activity paramActivity) {
    return (paramActivity == HostActivityManager.getHostTopActivity()) ? true : isActivityAtStackTop(paramActivity);
  }
  
  public static boolean isActivityAtStackTop(Activity paramActivity) {
    return (Build.VERSION.SDK_INT >= 23) ? checkActivityAtStackTopOver23((Context)paramActivity, paramActivity.getClass()) : true;
  }
  
  public static boolean isFullScreen(Activity paramActivity) {
    return (((paramActivity.getWindow().getAttributes()).flags & 0x400) == 1024);
  }
  
  public static boolean isMiniAppStackAtTop(Context paramContext) {
    List<ActivityManager.AppTask> list = getAppTasks(paramContext);
    if (list != null && list.size() > 0)
      try {
        String str = getBaseIntentComponentName(list.get(0));
      } finally {
        list = null;
      }  
    return false;
  }
  
  public static boolean isMoveActivityToFrontSilentIntent(Intent paramIntent) {
    return (paramIntent == null) ? false : paramIntent.getBooleanExtra(TAG_MOVE_ACTIVITY_TO_FRONT_SILENT, false);
  }
  
  public static boolean isTaskSingleActivity(Activity paramActivity) {
    List<ActivityManager.AppTask> list = getAppTasks((Context)paramActivity);
    if (list != null && list.size() > 0)
      try {
        Iterator<ActivityManager.AppTask> iterator = list.iterator();
        while (iterator.hasNext()) {
          ActivityManager.RecentTaskInfo recentTaskInfo = ((ActivityManager.AppTask)iterator.next()).getTaskInfo();
          if (recentTaskInfo.id == paramActivity.getTaskId()) {
            if (Build.VERSION.SDK_INT >= 23) {
              if (recentTaskInfo.numActivities == 1)
                return true; 
            } else {
              ComponentName componentName = recentTaskInfo.baseIntent.getComponent();
              if (componentName != null)
                return TextUtils.equals(componentName.getClassName(), paramActivity.getClass().getName()); 
              continue;
            } 
            return false;
          } 
        } 
      } catch (Exception exception) {
        AppBrandLogger.e("ActivityUtil", new Object[] { "tryJumpMiniApp checkTopActivity", exception });
      }  
    return false;
  }
  
  private static void launchMiniAppActivity(Context paramContext, Class paramClass) {
    AppBrandLogger.d("ActivityUtil", new Object[] { "launchMiniAppActivity miniAppActivityClass:", paramClass });
    Intent intent = new Intent(paramContext, paramClass);
    if (!(paramContext instanceof Activity))
      intent.addFlags(268435456); 
    intent.putExtra(TAG_MOVE_ACTIVITY_TO_FRONT_SILENT, true);
    paramContext.startActivity(intent);
  }
  
  private static void launchMoveHostFrontActivity(Activity paramActivity, boolean paramBoolean) {
    AppBrandLogger.d("ActivityUtil", new Object[] { "launchMoveHostFrontActivity" });
    Intent intent = new Intent((Context)paramActivity, MoveHostFrontActivity.class);
    intent.addFlags(268435456);
    intent.addFlags(1073741824);
    if (paramBoolean)
      intent.putExtra(TAG_MOVE_ACTIVITY_TO_FRONT_SILENT, true); 
    paramActivity.startActivity(intent);
  }
  
  public static void moveHostActivityTaskToFront(Activity paramActivity, boolean paramBoolean) {
    AppBrandLogger.d("ActivityUtil", new Object[] { "moveHostActivityTaskToFront activity:", paramActivity });
    if (paramActivity == null) {
      AppBrandLogger.e("ActivityUtil", new Object[] { "moveHostActivityTaskToFront activity == null" });
      return;
    } 
    List<ActivityManager.AppTask> list = getAppTasks((Context)paramActivity);
    ActivityManager.AppTask appTask2 = null;
    ActivityManager.AppTask appTask1 = appTask2;
    if (list != null) {
      appTask1 = appTask2;
      if (list.size() > 0)
        try {
          String str1 = getMiniAppActivityPackageName();
          String str2 = paramActivity.getPackageName();
          Iterator<ActivityManager.AppTask> iterator = list.iterator();
          while (true) {
            appTask1 = appTask2;
            if (iterator.hasNext()) {
              appTask1 = iterator.next();
              String str = getBaseIntentComponentName(appTask1);
              if (str != null && str.contains(str2)) {
                boolean bool = str.contains(str1);
                if (!bool)
                  break; 
              } 
              continue;
            } 
            break;
          } 
        } catch (Exception exception) {
          AppBrandLogger.e("ActivityUtil", new Object[] { "moveHostActivityTaskToFront checkMatchTask", exception });
          appTask1 = appTask2;
        }  
    } 
    if (!moveTargetTaskToFront((Context)paramActivity, appTask1))
      launchMoveHostFrontActivity(paramActivity, paramBoolean); 
    if (paramBoolean)
      changeToSilentHideActivityAnimation(paramActivity); 
  }
  
  public static boolean moveMiniAppActivityToFront(Activity paramActivity, String paramString) {
    AppBrandLogger.d("ActivityUtil", new Object[] { "moveMiniAppActivityToFront activity:", paramActivity });
    if (paramActivity == null) {
      AppBrandLogger.e("ActivityUtil", new Object[] { "moveMiniAppActivityToFront activity == null" });
      return false;
    } 
    AppProcessManager.ProcessInfo processInfo = AppProcessManager.getProcessInfoByAppId(paramString);
    if (processInfo == null || processInfo.isLaunchActivityInHostStack()) {
      AppBrandLogger.i("ActivityUtil", new Object[] { "processInfo == null || processInfo.isLaunchActivityInHostStack()" });
      return false;
    } 
    Class clazz = processInfo.getLaunchActivityClass();
    if (clazz == null) {
      AppBrandLogger.e("ActivityUtil", new Object[] { "launchActivityClass == null" });
      return false;
    } 
    if (!moveTargetTaskToFront((Context)paramActivity, getMiniAppActivityTask((Context)paramActivity, clazz)))
      launchMiniAppActivity((Context)paramActivity, clazz); 
    changeToSilentHideActivityAnimation(paramActivity);
    return true;
  }
  
  private static boolean moveTargetTaskToFront(Context paramContext, ActivityManager.AppTask paramAppTask) {
    if (paramAppTask == null)
      return false; 
    ActivityManager activityManager = (ActivityManager)paramContext.getSystemService("activity");
    if (activityManager != null) {
      try {
        ActivityManager.RecentTaskInfo recentTaskInfo = paramAppTask.getTaskInfo();
        activityManager.moveTaskToFront(recentTaskInfo.id, 2);
      } finally {
        boolean bool1;
        activityManager = null;
      } 
      AppBrandLogger.e("ActivityUtil", new Object[] { "activityManager.moveTaskToFront", activityManager });
    } 
    boolean bool = false;
    if (!bool) {
      paramAppTask.moveToFront();
      AppBrandLogger.i("ActivityUtil", new Object[] { "matchAppTask.moveToFront targetAppTask:", paramAppTask });
    } 
    return true;
  }
  
  public static void previousGetSnapshot(final MiniappHostBase activity) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            SnapshotManager.getSnapshot((Activity)activity);
          }
        },  ThreadPools.defaults());
  }
  
  public static void setFullScreen(Activity paramActivity) {
    if (paramActivity == null)
      return; 
    if (!isFullScreen(paramActivity))
      paramActivity.getWindow().getDecorView().setSystemUiVisibility(2822); 
  }
  
  public static void startOpenSchemaActivity(MiniappHostBase paramMiniappHostBase, String paramString1, String paramString2, String paramString3) {
    previousGetSnapshot(paramMiniappHostBase);
    AppbrandApplicationImpl.getInst().setOpenedSchema(true);
    String str2 = SettingsDAO.getString((Context)paramMiniappHostBase, "", new Enum[] { (Enum)Settings.TT_TMA_SWITCH, (Enum)Settings.TmaSwitch.LAUNCH_FLAG });
    String str1 = paramString2;
    if (TextUtils.isEmpty(paramString2))
      str1 = str2; 
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    Intent intent = new Intent((Context)paramMiniappHostBase, OpenSchemaMiddleActivity.class);
    intent.putExtra("schema", paramString1);
    intent.putExtra("class_name", paramMiniappHostBase.getClass().getName());
    intent.putExtra("from_app_id", appInfoEntity.appId);
    intent.putExtra("is_from_app_in_host_stack", paramMiniappHostBase.isInHostStack());
    intent.putExtra("is_game", appInfoEntity.isGame());
    intent.putExtra("launch_flag", str1);
    intent.putExtra("args", paramString3);
    if (!"currentTask".equalsIgnoreCase(str1))
      intent.addFlags(268435456); 
    paramMiniappHostBase.startActivity(intent);
    changeToSilentHideActivityAnimation((Activity)paramMiniappHostBase);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\ActivityUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */