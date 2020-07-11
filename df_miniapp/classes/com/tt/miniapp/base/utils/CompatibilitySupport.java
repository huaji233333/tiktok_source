package com.tt.miniapp.base.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.webkit.WebView;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CompatibilitySupport {
  private static int DisableOverScrollIntValue;
  
  private static Method DisableOverScrollMethod;
  
  public static boolean ZTE_U5_SCROLL_FINISHED;
  
  public static final boolean afterKITKAT() {
    return (Build.VERSION.SDK_INT >= 19);
  }
  
  public static final boolean beforeGINGERBREAD() {
    return (Build.VERSION.SDK_INT < 9);
  }
  
  public static final boolean beforeHONEYCOMB() {
    return (Build.VERSION.SDK_INT < 11);
  }
  
  public static final boolean beforeIceCreamSandwich() {
    return (Build.VERSION.SDK_INT < 14);
  }
  
  public static boolean beforeJelleyBean() {
    return (Build.VERSION.SDK_INT < 16);
  }
  
  public static final boolean beforeKIKAT() {
    return (Build.VERSION.SDK_INT < 19);
  }
  
  public static final boolean beforeSDK42() {
    return (Build.VERSION.SDK_INT <= 17);
  }
  
  public static final boolean canHideNotificationBar() {
    return (Build.VERSION.SDK_INT <= 8) ? false : (!isGTI9100() ? (!isSCHi929()) : false);
  }
  
  public static final boolean canSupportSelect() {
    return (Build.VERSION.SDK_INT < 14);
  }
  
  public static final void copy(Context paramContext, String paramString) {
    ClipboardManager clipboardManager1;
    if (Build.VERSION.SDK_INT < 11) {
      clipboardManager1 = (ClipboardManager)paramContext.getSystemService("clipboard");
      if (clipboardManager1 != null)
        clipboardManager1.setText(paramString); 
      return;
    } 
    ClipboardManager clipboardManager = (ClipboardManager)clipboardManager1.getSystemService("clipboard");
    if (clipboardManager != null) {
      ClipData clipData = ClipData.newPlainText(null, paramString);
      try {
        clipboardManager.setPrimaryClip(clipData);
        return;
      } catch (NullPointerException nullPointerException) {
        return;
      } 
    } 
  }
  
  public static String getSystemProperty(String paramString) {
    try {
      Class<?> clazz = Class.forName("android.os.SystemProperties");
      return (String)clazz.getMethod("get", new Class[] { String.class }).invoke(clazz, new Object[] { paramString });
    } catch (Exception exception) {
      return "";
    } 
  }
  
  public static int getWebViewTouchMode(Object paramObject) {
    if (paramObject instanceof WebView)
      try {
        Object object;
        null = Build.VERSION.SDK_INT;
        if (null >= 16) {
          Field field = WebView.class.getDeclaredField("mProvider");
          field.setAccessible(true);
          object = field.get(paramObject);
          paramObject = object.getClass().getDeclaredField("mTouchMode");
        } else {
          Field field = WebView.class.getDeclaredField("mTouchMode");
          object = paramObject;
          paramObject = field;
        } 
        paramObject.setAccessible(true);
        return ((Integer)paramObject.get(object)).intValue();
      } catch (Exception exception) {} 
    return -1;
  }
  
  public static boolean is1501_M02() {
    return ("360".equals(Build.BRAND) && "1501-M02".equals(Build.MODEL));
  }
  
  public static final boolean isCoolpad9900() {
    return "9900".equals(Build.MODEL);
  }
  
  public static final boolean isCoolpad_9190L() {
    return "Coolpad 9190L".equals(Build.MODEL);
  }
  
  public static final boolean isEclair() {
    return (Build.VERSION.SDK_INT == 7);
  }
  
  public static final boolean isGTI9000() {
    return Build.MODEL.equals("GT-I9000");
  }
  
  public static final boolean isGTI9100() {
    return Build.MODEL.equals("GT-I9100");
  }
  
  public static final boolean isGTN7000() {
    return Build.MODEL.equals("GT-N7000");
  }
  
  public static final boolean isGTN7105() {
    return Build.MODEL.equals("GT-N7105");
  }
  
  public static final boolean isGT_S7562() {
    return Build.MODEL.equals("GT-S7562");
  }
  
  public static final boolean isGtI9200(Context paramContext) {
    int i = (paramContext.getResources().getDisplayMetrics()).widthPixels;
    int j = (paramContext.getResources().getDisplayMetrics()).heightPixels;
    return (i == 720 && j == 1280 && (paramContext.getResources().getDisplayMetrics()).density == 1.5D);
  }
  
  public static final boolean isHTC7088(Context paramContext, float paramFloat) {
    int i = (paramContext.getResources().getDisplayMetrics()).widthPixels;
    int j = (paramContext.getResources().getDisplayMetrics()).heightPixels;
    return (i == 540 && j == 960 && paramFloat == 1.5D);
  }
  
  public static final boolean isHTC_T328d() {
    return "HTC T328d".equals(Build.MODEL);
  }
  
  public static final boolean isHongMI() {
    return Build.MODEL.equals("2013023");
  }
  
  public static boolean isHuaweiOrHonor() {
    return (Build.BRAND.equalsIgnoreCase("Huawei") || Build.BRAND.equalsIgnoreCase("Honor"));
  }
  
  public static boolean isJelleyBean() {
    return (Build.VERSION.SDK_INT >= 16);
  }
  
  public static boolean isKingsun() {
    return Build.BRAND.equalsIgnoreCase("KINGSUN");
  }
  
  public static final boolean isLenovoA560() {
    return "Lenovo A560".equals(Build.MODEL);
  }
  
  public static final boolean isLenovoA850() {
    return "Lenovo A850+".equals(Build.MODEL);
  }
  
  public static final boolean isLenovoS880() {
    return "Lenovo S880".equals(Build.MODEL);
  }
  
  public static final boolean isM030() {
    return Build.MODEL.equals("M030");
  }
  
  public static final boolean isM032() {
    return Build.MODEL.equals("M032");
  }
  
  public static final boolean isM9() {
    return Build.MODEL.equals("M9");
  }
  
  public static final boolean isMI2() {
    return Build.MODEL.equals("MI 2");
  }
  
  public static boolean isMIUI() {
    return Build.HOST.contains("miui");
  }
  
  public static boolean isMIUIV6() {
    return "v6".equalsIgnoreCase(getSystemProperty("ro.miui.ui.version.name"));
  }
  
  public static final boolean isMZ() {
    String str = Build.MODEL.toLowerCase();
    return (str.equals("m9") || str.equals("m032") || str.equals("meizu mx") || str.equals("mx2") || str.equals("mx"));
  }
  
  public static final boolean isMeizu() {
    return Build.BRAND.equalsIgnoreCase("MEIZU");
  }
  
  public static final boolean isMeizu3() {
    return (isMeizu() && Build.DEVICE.equalsIgnoreCase("mx3"));
  }
  
  public static final boolean isMeizuM9(Context paramContext) {
    int i = (paramContext.getResources().getDisplayMetrics()).widthPixels;
    int j = (paramContext.getResources().getDisplayMetrics()).heightPixels;
    return (i == 640 && j == 960 && (paramContext.getResources().getDisplayMetrics()).density == 2.0D);
  }
  
  public static final boolean isMeizuMX() {
    return Build.MODEL.equals("MEIZU MX");
  }
  
  public static boolean isMeizuPro6() {
    return (isMeizu() && Build.MODEL.equalsIgnoreCase("PRO 6"));
  }
  
  public static final boolean isMi4() {
    return Build.MODEL.equals("MI 4LTE");
  }
  
  public static boolean isMiUi() {
    return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
  }
  
  public static boolean isNX511J() {
    return "NX511J".equals(Build.MODEL);
  }
  
  public static final boolean isNexus5() {
    return "Nexus 5".equals(Build.MODEL);
  }
  
  public static boolean isNubia() {
    return Build.BRAND.equalsIgnoreCase("nubia");
  }
  
  public static boolean isONEPLUS3() {
    return "ONEPLUS A3000".equals(Build.MODEL);
  }
  
  public static final boolean isOPPOX9077() {
    return Build.MODEL.equals("X9077");
  }
  
  public static final boolean isOrAfterAndroidM() {
    return (Build.VERSION.SDK_INT >= 23);
  }
  
  public static final boolean isOrAfterGINGERBREAD() {
    return (Build.VERSION.SDK_INT >= 9);
  }
  
  public static final boolean isOrAfterKKKAT() {
    return (Build.VERSION.SDK_INT >= 19);
  }
  
  public static final boolean isOrAfterLollipop() {
    return (Build.VERSION.SDK_INT >= 21);
  }
  
  public static boolean isQiKU() {
    return (Build.BRAND.equalsIgnoreCase("QiKU") || Build.BRAND.equalsIgnoreCase("360"));
  }
  
  public static final boolean isSCHi929() {
    return "SCH-i929".equals(Build.MODEL);
  }
  
  public static final boolean isSMN9008V() {
    return "SM-N9008V".equals(Build.MODEL);
  }
  
  public static final boolean isSM_G9006V() {
    return "SM-G9006V".equals(Build.MODEL);
  }
  
  public static boolean isSamsung() {
    return Build.BRAND.equalsIgnoreCase("samsung");
  }
  
  public static final boolean isSonyL39t() {
    return "L39t".equals(Build.MODEL);
  }
  
  public static final boolean isWebViewSoftLayer() {
    return Build.MODEL.equals("8692-M02");
  }
  
  public static final boolean isXiaoMiOne() {
    return Build.MODEL.toUpperCase().equals("MI-ONE PLUS");
  }
  
  public static boolean isXiaomi() {
    return Build.BRAND.equalsIgnoreCase("Xiaomi");
  }
  
  public static final boolean isZTE_U5() {
    return Build.MODEL.equals("ZTE U5");
  }
  
  public static final boolean isZTE_U970() {
    return Build.MODEL.equals("ZTE U970");
  }
  
  public static final boolean isZTE_U985() {
    return "ZTE U985".equals(Build.MODEL);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\bas\\utils\CompatibilitySupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */