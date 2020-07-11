package com.tt.miniapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import com.tt.miniapphost.util.UIUtils;

public class VideoUtils {
  private static int sdkVersion = Build.VERSION.SDK_INT;
  
  public static int getScreenPortraitHeight(Context paramContext) {
    if (paramContext == null)
      return 0; 
    Configuration configuration = paramContext.getResources().getConfiguration();
    return (configuration != null && configuration.orientation == 2) ? UIUtils.getScreenWidth(paramContext) : UIUtils.getScreenHeight(paramContext);
  }
  
  public static int getScreenPortraitWidth(Context paramContext) {
    if (paramContext == null)
      return 0; 
    Configuration configuration = paramContext.getResources().getConfiguration();
    return (configuration != null && configuration.orientation == 2) ? UIUtils.getScreenHeight(paramContext) : UIUtils.getScreenWidth(paramContext);
  }
  
  public static Activity getViewAttachedActivity(View paramView) {
    if (paramView == null)
      return null; 
    Activity activity2 = safeCastActivity(paramView.getContext());
    if (activity2 != null)
      return activity2; 
    View view1 = paramView.getRootView();
    if (view1 == null)
      return null; 
    View view2 = view1.findViewById(16908290);
    Activity activity1 = activity2;
    if (view2 != null)
      activity1 = safeCastActivity(view2.getContext()); 
    return (activity1 != null) ? activity1 : safeCastActivity(view1.getContext());
  }
  
  public static String milliSecondsToTimer(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    long l1 = (paramInt / 60000);
    long l2 = (paramInt % 3600000 % 60000 / 1000);
    if (l1 >= 10L) {
      stringBuilder.append(l1);
    } else if (l1 > 0L) {
      stringBuilder.append(0);
      stringBuilder.append(l1);
    } else {
      stringBuilder.append(0);
      stringBuilder.append(0);
    } 
    stringBuilder.append(":");
    if (l2 >= 10L) {
      stringBuilder.append(l2);
    } else if (l2 > 0L) {
      stringBuilder.append(0);
      stringBuilder.append(l2);
    } else {
      stringBuilder.append(0);
      stringBuilder.append(0);
    } 
    return stringBuilder.toString();
  }
  
  public static String percentToTime(int paramInt1, int paramInt2) {
    return milliSecondsToTimer(paramInt1 * paramInt2 / 100);
  }
  
  public static Activity safeCastActivity(Context paramContext) {
    while (true) {
      if (paramContext == null)
        return null; 
      if (Activity.class.isInstance(paramContext))
        return (Activity)paramContext; 
      if (ContextWrapper.class.isInstance(paramContext)) {
        paramContext = ((ContextWrapper)paramContext).getBaseContext();
        continue;
      } 
      return null;
    } 
  }
  
  public static void showOrHideNaviBar(View paramView, boolean paramBoolean) {
    if (paramView == null)
      return; 
    if (paramBoolean) {
      paramView.setSystemUiVisibility(0);
      return;
    } 
    int i = sdkVersion;
    if (i >= 19) {
      paramView.setSystemUiVisibility(3846);
      return;
    } 
    if (i >= 16) {
      paramView.setSystemUiVisibility(5);
      return;
    } 
    paramView.setSystemUiVisibility(1);
  }
  
  public static int timeToPercent(int paramInt1, int paramInt2) {
    return (paramInt2 > 0) ? (int)(paramInt1 * 100.0F / paramInt2 + 0.5F) : 0;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\VideoUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */