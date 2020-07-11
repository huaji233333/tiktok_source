package com.tt.miniapp.map.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.tt.miniapp.map.model.ThirdMapModel;
import com.tt.miniapphost.AppBrandLogger;
import java.util.ArrayList;
import java.util.List;

public class MapUtils {
  public static boolean checkApkExist(Context paramContext, String paramString) {
    try {
      PackageInfo packageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 0);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      AppBrandLogger.stacktrace(6, "MapUtils", nameNotFoundException.getStackTrace());
      nameNotFoundException = null;
    } 
    return (nameNotFoundException != null);
  }
  
  public static List<String> getLocalInstallMap(Context paramContext) {
    ArrayList<String> arrayList = new ArrayList(4);
    if (checkApkExist(paramContext, "com.baidu.BaiduMap"))
      arrayList.add(paramContext.getResources().getString(2097741943)); 
    if (checkApkExist(paramContext, "com.autonavi.minimap"))
      arrayList.add(paramContext.getResources().getString(2097741945)); 
    if (checkApkExist(paramContext, "com.tencent.map"))
      arrayList.add(paramContext.getResources().getString(2097741948)); 
    return arrayList;
  }
  
  public static String getVersionName(Context paramContext) {
    try {
      return (paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0)).versionName;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "MapUtils", exception.getStackTrace());
      return "";
    } 
  }
  
  public static void jumpToBaiduMap(Context paramContext, ThirdMapModel paramThirdMapModel1, ThirdMapModel paramThirdMapModel2) {
    if (paramThirdMapModel1 != null) {
      if (paramThirdMapModel2 == null)
        return; 
      StringBuilder stringBuilder = new StringBuilder("baidumap://map/direction?origin_region=");
      stringBuilder.append(paramThirdMapModel1.getName());
      stringBuilder.append("&origin=name:");
      stringBuilder.append(paramThirdMapModel1.getName());
      stringBuilder.append("|latlng:");
      stringBuilder.append((paramThirdMapModel1.getPoint()).a);
      stringBuilder.append(",");
      stringBuilder.append((paramThirdMapModel1.getPoint()).b);
      stringBuilder.append("&destination_region=");
      stringBuilder.append(paramThirdMapModel2.getName());
      stringBuilder.append("&destination=name:");
      stringBuilder.append(paramThirdMapModel2.getName());
      stringBuilder.append("|latlng:");
      stringBuilder.append((paramThirdMapModel2.getPoint()).a);
      stringBuilder.append(",");
      stringBuilder.append((paramThirdMapModel2.getPoint()).b);
      stringBuilder.append("&mode=walking");
      paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
    } 
  }
  
  public static void jumpToGaodeMap(Context paramContext, ThirdMapModel paramThirdMapModel1, ThirdMapModel paramThirdMapModel2) {
    if (paramThirdMapModel1 != null) {
      if (paramThirdMapModel2 == null)
        return; 
      StringBuilder stringBuilder = new StringBuilder("amapuri://route/plan/?sourceApplication=");
      stringBuilder.append(getVersionName(paramContext));
      stringBuilder.append("&sname=");
      stringBuilder.append(paramThirdMapModel1.getName());
      stringBuilder.append("&slat=");
      stringBuilder.append((paramThirdMapModel1.getPoint()).a);
      stringBuilder.append("&slon=");
      stringBuilder.append((paramThirdMapModel1.getPoint()).b);
      stringBuilder.append("&dname=");
      stringBuilder.append(paramThirdMapModel2.getName());
      stringBuilder.append("&dlat=");
      stringBuilder.append((paramThirdMapModel2.getPoint()).a);
      stringBuilder.append("&dlon=");
      stringBuilder.append((paramThirdMapModel2.getPoint()).b);
      stringBuilder.append("&dev=0&t=2");
      paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
    } 
  }
  
  public static void jumpToTencentMap(Context paramContext, ThirdMapModel paramThirdMapModel1, ThirdMapModel paramThirdMapModel2) {
    if (paramThirdMapModel1 != null) {
      if (paramThirdMapModel2 == null)
        return; 
      StringBuilder stringBuilder = new StringBuilder("qqmap://map/routeplan?referer=");
      stringBuilder.append(getVersionName(paramContext));
      stringBuilder.append("&type=walk&from=");
      stringBuilder.append(paramThirdMapModel1.getName());
      stringBuilder.append("&fromcoord=");
      stringBuilder.append((paramThirdMapModel1.getPoint()).a);
      stringBuilder.append(",");
      stringBuilder.append((paramThirdMapModel1.getPoint()).b);
      stringBuilder.append("&to=");
      stringBuilder.append(paramThirdMapModel2.getName());
      stringBuilder.append("&tocoord=");
      stringBuilder.append((paramThirdMapModel2.getPoint()).a);
      stringBuilder.append(",");
      stringBuilder.append((paramThirdMapModel2.getPoint()).b);
      stringBuilder.append("&policy=0");
      paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ma\\utils\MapUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */