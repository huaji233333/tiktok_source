package com.tt.miniapp.locate;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.maplocate.TMALocation;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.util.CoordinateTransformUtil;
import com.tt.option.q.i;
import org.json.JSONException;
import org.json.JSONObject;

public class LocateReporter {
  private static final String BASE_URL = AppbrandConstant.OpenApi.getInst().getUSER_LOCATION_URL();
  
  private LocateCrossProcessRequester requester;
  
  public static void reportLocationAsyncWhenAppinfoReady() {
    AppBrandLogger.d("LocateReporter", new Object[] { "reportLocationAsyncWhenAppinfoReady" });
    Observable.create(new Function<Void>() {
          public final Void fun() {
            LocateReporter.reportLocationWithSession();
            return null;
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public static void reportLocationWithSession() {
    AppBrandLogger.d("LocateReporter", new Object[] { "requireAndReportLocation" });
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      AppBrandLogger.d("LocateReporter", new Object[] { "activity null" });
      return;
    } 
    if (!BrandPermissionUtils.isGranted(12)) {
      AppBrandLogger.d("LocateReporter", new Object[] { "no appbrand permission" });
      return;
    } 
    if (!PermissionsManager.getInstance().hasPermission((Context)miniappHostBase, "android.permission.ACCESS_COARSE_LOCATION") && !PermissionsManager.getInstance().hasPermission((Context)miniappHostBase, "android.permission.ACCESS_FINE_LOCATION")) {
      AppBrandLogger.d("LocateReporter", new Object[] { "no app permission" });
      return;
    } 
    (new LocateReporter()).requireAndReportLocation();
  }
  
  public void requireAndReportLocation() {
    boolean bool;
    if ((AppbrandApplicationImpl.getInst().getAppInfo()).isOpenLocation == 1) {
      bool = true;
    } else {
      bool = false;
    } 
    if (!bool) {
      AppBrandLogger.d("LocateReporter", new Object[] { "isOpenLocation false.return" });
      return;
    } 
    this.requester = new LocateCrossProcessRequester("LocateReporter");
    TMALocation tMALocation = this.requester.getCachedLocation();
    if (tMALocation != null && System.currentTimeMillis() - tMALocation.getTime() < 86400000L) {
      startNetworkRequest(tMALocation);
      return;
    } 
    this.requester.startCrossProcessLocate(5000L, new LocateCrossProcessRequester.LocateResultCallbck() {
          public void onFailed(String param1String) {
            StringBuilder stringBuilder = new StringBuilder("location report failed:");
            stringBuilder.append(param1String);
            AppBrandLogger.d("LocateReporter", new Object[] { stringBuilder.toString() });
          }
          
          public void onSuccess(TMALocation param1TMALocation) {
            LocateReporter.this.startNetworkRequest(param1TMALocation);
          }
        });
  }
  
  public void startNetworkRequest(TMALocation paramTMALocation) {
    if (paramTMALocation == null)
      return; 
    double[] arrayOfDouble = CoordinateTransformUtil.gcj02towgs84(paramTMALocation.getLongitude(), paramTMALocation.getLatitude());
    double d1 = arrayOfDouble[0];
    double d2 = arrayOfDouble[1];
    StringBuilder stringBuilder = new StringBuilder("startNetworkRequest:");
    stringBuilder.append(paramTMALocation.getLatitude());
    stringBuilder.append(" ");
    stringBuilder.append(paramTMALocation.getLongitude());
    AppBrandLogger.d("LocateReporter", new Object[] { stringBuilder.toString() });
    try {
      String str = InnerHostProcessBridge.getPlatformSession((AppbrandApplication.getInst().getAppInfo()).appId);
      if (TextUtils.isEmpty(str))
        return; 
      Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
      builder.appendQueryParameter("session", str);
      builder.appendQueryParameter("appid", (AppbrandApplicationImpl.getInst().getAppInfo()).appId);
      builder.appendQueryParameter("aid", AppbrandContext.getInst().getInitParams().getAppId());
      builder.appendQueryParameter("longitude", String.valueOf(d1));
      builder.appendQueryParameter("latitude", String.valueOf(d2));
      str = builder.build().toString();
      i i = new i(str, "POST");
      i.j = 6000L;
      i.l = 6000L;
      i.k = 6000L;
      AppBrandLogger.d("LocateReporter", new Object[] { "post str is", i.d() });
      return;
    } finally {
      paramTMALocation = null;
      AppBrandLogger.eWithThrowable("LocateReporter", "jsonerror", (Throwable)paramTMALocation);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\locate\LocateReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */