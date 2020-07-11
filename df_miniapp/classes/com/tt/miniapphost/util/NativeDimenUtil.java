package com.tt.miniapphost.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppbrandContext;

public class NativeDimenUtil {
  private static float sDensity = -1.0F;
  
  private static int sDeviceHeight = -1;
  
  private static int sDeviceWidth = -1;
  
  private static float sHeightFocus;
  
  public static int convertPxToRx(int paramInt) {
    init();
    return floatToInt(paramInt / sDensity);
  }
  
  public static int convertRxToPx(double paramDouble) {
    init();
    return floatToInt((float)paramDouble * sDensity);
  }
  
  public static int convertRxToPxVertical(int paramInt) {
    init();
    return (int)(convertRxToPx(paramInt) * sHeightFocus);
  }
  
  private static int floatToInt(float paramFloat) {
    return Math.round(paramFloat);
  }
  
  private static void init() {
    if (sDeviceWidth < 0) {
      Application application = AppbrandContext.getInst().getApplicationContext();
      WindowManager windowManager = (WindowManager)application.getSystemService("window");
      Point point = new Point();
      windowManager.getDefaultDisplay().getSize(point);
      sDeviceWidth = point.x;
      int i = point.y;
      sDeviceHeight = i;
      sHeightFocus = i * 1.0F / sDeviceWidth;
      sDensity = DevicesUtil.getPixelRadio((Context)application);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\NativeDimenUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */