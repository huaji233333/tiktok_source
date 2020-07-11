package com.tt.miniapp.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.f.a;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public final class NetUtil {
  private static Handler sHandler = new Handler(Looper.getMainLooper());
  
  static boolean sIsNetAvailable;
  
  private static long sLastChangeTime;
  
  private static boolean sLastState = false;
  
  private static String sLastType;
  
  static NetChangeRunnable sNetChangeRunnable;
  
  static String sNetWorkType;
  
  static boolean sNetworkReceiverRegister = false;
  
  private static Runnable sRunnable;
  
  static {
    sIsNetAvailable = false;
    sNetWorkType = "";
    sNetChangeRunnable = new NetChangeRunnable();
  }
  
  public static List<String> getCanJumpOtherMiniappList() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    return (appInfoEntity != null && appInfoEntity.domainMap != null) ? (List<String>)appInfoEntity.domainMap.get("appids") : null;
  }
  
  public static String getConnectionType(Context paramContext) {
    NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo((ConnectivityManager)paramContext.getSystemService("connectivity"));
    if (networkInfo != null && networkInfo.isAvailable()) {
      int i = networkInfo.getType();
      if (i != 0) {
        if (i == 1)
          return "WIFI"; 
      } else {
        return "MOBILE";
      } 
    } 
    return "";
  }
  
  public static String getNetGeneration(Context paramContext) {
    switch (((TelephonyManager)paramContext.getSystemService("phone")).getNetworkType()) {
      default:
        return "unknown";
      case 13:
        return "4g";
      case 3:
      case 5:
      case 6:
      case 8:
      case 9:
      case 10:
      case 12:
      case 14:
      case 15:
        return "3g";
      case 1:
      case 2:
      case 4:
      case 7:
      case 11:
        break;
    } 
    return "2g";
  }
  
  public static final String getNetType(Context paramContext) {
    String str1;
    String str3 = null;
    Context context = null;
    String str2 = str3;
    try {
      NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo((ConnectivityManager)paramContext.getSystemService("connectivity"));
      paramContext = context;
      if (networkInfo != null) {
        paramContext = context;
        str2 = str3;
        if (networkInfo.isAvailable()) {
          str2 = str3;
          String str = networkInfo.getTypeName();
          str2 = str3;
          if (!"MOBILE".equalsIgnoreCase(str)) {
            str1 = str;
          } else {
            str2 = str3;
            str3 = networkInfo.getExtraInfo();
            str1 = str3;
            if (str3 == null) {
              str2 = str3;
              StringBuilder stringBuilder = new StringBuilder();
              str2 = str3;
              stringBuilder.append(str);
              str2 = str3;
              stringBuilder.append("#[]");
              str2 = str3;
              String str4 = stringBuilder.toString();
            } 
          } 
        } 
      } 
    } catch (Exception exception) {
      str1 = str2;
      AppBrandLogger.stacktrace(6, "tma_NetUtils", exception.getStackTrace());
    } 
    return (str1 == null) ? "" : str1;
  }
  
  public static int getNetWorkType(Context paramContext) {
    NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo((ConnectivityManager)paramContext.getSystemService("connectivity"));
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (networkInfo != null) {
      bool1 = bool2;
      if (networkInfo.isConnected()) {
        String str = networkInfo.getTypeName();
        if (str.equalsIgnoreCase("WIFI"))
          return 4; 
        bool1 = bool2;
        if (str.equalsIgnoreCase("MOBILE")) {
          if (TextUtils.isEmpty(Proxy.getDefaultHost()))
            return isFastMobileNetwork(paramContext) ? 3 : 2; 
          bool1 = true;
        } 
      } 
    } 
    return bool1;
  }
  
  public static String getNewNetType(Context paramContext) {
    try {
      NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo((ConnectivityManager)paramContext.getSystemService("connectivity"));
      if (networkInfo != null && networkInfo.isAvailable()) {
        int i = networkInfo.getType();
        if (i != 0) {
          if (i != 1)
            return "unknown"; 
        } else {
          return getNetGeneration(paramContext);
        } 
      } else {
        return "none";
      } 
    } catch (Exception exception) {
      return "unknown";
    } 
    return "wifi";
  }
  
  public static boolean isConnect(Context paramContext) {
    try {
      ConnectivityManager connectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (connectivityManager != null) {
        NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo(connectivityManager);
        if (networkInfo != null && networkInfo.isConnected()) {
          NetworkInfo.State state1 = networkInfo.getState();
          NetworkInfo.State state2 = NetworkInfo.State.CONNECTED;
          if (state1 == state2)
            return true; 
        } 
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_NetUtils", exception.getStackTrace());
    } 
    return false;
  }
  
  private static boolean isFastMobileNetwork(Context paramContext) {
    switch (((TelephonyManager)paramContext.getSystemService("phone")).getNetworkType()) {
      default:
        return false;
      case 12:
      case 13:
      case 14:
      case 15:
        return true;
      case 11:
        return false;
      case 8:
      case 9:
      case 10:
        return true;
      case 7:
        return false;
      case 5:
      case 6:
        return true;
      case 4:
        return false;
      case 3:
        return true;
      case 0:
      case 1:
      case 2:
        break;
    } 
    return false;
  }
  
  public static final boolean isNetTypeCT(Context paramContext) {
    String str = getNetType(paramContext);
    boolean bool = TextUtils.isEmpty(str);
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (!bool) {
      bool1 = bool2;
      if ("CT".equalsIgnoreCase(str.substring(0, 2)))
        bool1 = true; 
    } 
    return bool1;
  }
  
  public static final boolean isNetTypeWifiOr3G(Context paramContext) {
    String str = getNetType(paramContext);
    return !TextUtils.isEmpty(str) ? ("WIFI".equalsIgnoreCase(str) ? true : isFastMobileNetwork(paramContext)) : false;
  }
  
  public static boolean isNetworkAvailable(Context paramContext) {
    if (paramContext == null)
      return true; 
    NetworkInfo[] arrayOfNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getAllNetworkInfo();
    if (arrayOfNetworkInfo != null) {
      int j = arrayOfNetworkInfo.length;
      for (int i = 0; i < j; i++) {
        NetworkInfo networkInfo = arrayOfNetworkInfo[i];
        if (networkInfo.isAvailable() && networkInfo.getState() == NetworkInfo.State.CONNECTED)
          return true; 
      } 
    } 
    return false;
  }
  
  public static boolean isSafeDomain(String paramString1, String paramString2) {
    AppBrandLogger.d("tma_NetUtils", new Object[] { "isSafeDomain =  url ", paramString2 });
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity != null && (!appInfoEntity.isLocalTest() || appInfoEntity.isAudit())) {
      boolean bool;
      HostDependManager hostDependManager = HostDependManager.getInst();
      if (!appInfoEntity.isLocalTest() || appInfoEntity.isAudit()) {
        bool = true;
      } else {
        bool = false;
      } 
      if (hostDependManager.isCheckSafeDomain(bool, paramString1, paramString2)) {
        JSONObject jSONObject2;
        List list;
        if (TextUtils.isEmpty(paramString1))
          return true; 
        if (TextUtils.isEmpty(paramString2)) {
          jSONObject2 = new JSONObject();
          try {
            jSONObject2.put("errMsg", "no url");
            jSONObject2.put("url", paramString2);
          } catch (JSONException null) {
            AppBrandLogger.stacktrace(6, "tma_NetUtils", jSONException.getStackTrace());
          } 
          AppBrandLogger.d("tma_NetUtils", new Object[] { "no url " });
          AppBrandMonitor.statusRate("mp_start_error", 1011, jSONObject2);
          return false;
        } 
        a a = appInfoEntity.domainMap;
        if (a == null || a.isEmpty()) {
          jSONObject2 = new JSONObject();
          try {
            jSONObject2.put("errMsg", "mDomainMap null");
            jSONObject2.put("url", jSONException);
          } catch (JSONException null) {
            AppBrandLogger.stacktrace(6, "tma_NetUtils", jSONException.getStackTrace());
          } 
          AppBrandLogger.d("tma_NetUtils", new Object[] { "mDomainMap null " });
          AppBrandMonitor.statusRate("mp_start_error", 1011, jSONObject2);
          return false;
        } 
        Uri uri = Uri.parse((String)jSONException);
        String str2 = uri.getHost();
        AppBrandLogger.d("tma_NetUtils", new Object[] { "host = ", str2, " url ", jSONException });
        if (TextUtils.equals((CharSequence)jSONObject2, "request") || TextUtils.equals((CharSequence)jSONObject2, "upload") || TextUtils.equals((CharSequence)jSONObject2, "download")) {
          if (!TextUtils.equals("https", uri.getScheme())) {
            AppBrandLogger.d("tma_NetUtils", new Object[] { "getScheme = ", uri.getScheme(), " url ", jSONException });
            jSONObject2 = new JSONObject();
            try {
              jSONObject2.put("errMsg", "not https");
              jSONObject2.put("url", jSONException);
            } catch (JSONException jSONException) {
              AppBrandLogger.stacktrace(6, "tma_NetUtils", jSONException.getStackTrace());
            } 
            AppBrandMonitor.statusRate("mp_start_error", 1011, jSONObject2);
            return false;
          } 
        } else if (TextUtils.equals((CharSequence)jSONObject2, "socket")) {
          if (!TextUtils.equals("wss", uri.getScheme())) {
            AppBrandLogger.d("tma_NetUtils", new Object[] { "getScheme = ", uri.getScheme(), " url ", jSONException });
            jSONObject2 = new JSONObject();
            try {
              jSONObject2.put("errMsg", "not wss");
              jSONObject2.put("url", jSONException);
            } catch (JSONException jSONException1) {
              AppBrandLogger.stacktrace(6, "tma_NetUtils", jSONException1.getStackTrace());
            } 
            AppBrandMonitor.statusRate("mp_start_error", 1011, jSONObject2);
            return false;
          } 
        } else if (TextUtils.equals((CharSequence)jSONObject2, "appids")) {
          if (a.containsKey(jSONObject2)) {
            list = (List)a.get(jSONObject2);
            str1 = uri.getQueryParameter("app_id");
            if (list != null && list.contains(str1))
              return true; 
          } 
          return false;
        } 
        if (a.containsKey(list)) {
          List list1 = (List)a.get(list);
          if (list1 != null)
            if (TextUtils.equals((CharSequence)list, "webview")) {
              for (String str1 : list1) {
                if (str2 != null && str2.endsWith(str1))
                  return true; 
              } 
            } else {
              return str1.contains(str2);
            }  
          return false;
        } 
        JSONObject jSONObject1 = new JSONObject();
        try {
          jSONObject1.put("errMsg", "not exits");
          jSONObject1.put("url", str1);
        } catch (JSONException jSONException1) {
          AppBrandLogger.stacktrace(6, "tma_NetUtils", jSONException1.getStackTrace());
        } 
        AppBrandLogger.d("tma_NetUtils", new Object[] { "not exits " });
        AppBrandMonitor.statusRate("mp_start_error", 1011, jSONObject1);
        return false;
      } 
    } 
    AppBrandLogger.d("tma_NetUtils", new Object[] { "isSafeDomain not check" });
    return true;
  }
  
  public static boolean isUsingWap(Context paramContext) {
    if (isNetworkAvailable(paramContext)) {
      NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo((ConnectivityManager)paramContext.getSystemService("connectivity"));
      if (networkInfo != null) {
        String str = networkInfo.getExtraInfo();
        if (!TextUtils.isEmpty(str) && (str.toLowerCase().equalsIgnoreCase("uniwap") || str.toLowerCase().equalsIgnoreCase("3gwap")))
          return true; 
      } 
    } 
    return false;
  }
  
  public static boolean isWifi(Context paramContext) {
    NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo((ConnectivityManager)paramContext.getSystemService("connectivity"));
    return (networkInfo != null && networkInfo.getType() == 1);
  }
  
  public static boolean isWifiOr4G(Context paramContext) {
    NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo((ConnectivityManager)paramContext.getSystemService("connectivity"));
    if (networkInfo != null && networkInfo.getType() == 1)
      return true; 
    TelephonyManager telephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    return (telephonyManager != null && telephonyManager.getNetworkType() == 13);
  }
  
  public static String map2params(Map<String, String> paramMap) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<String, String> entry : paramMap.entrySet()) {
      if (entry.getValue() != null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append((String)entry.getKey());
        stringBuilder1.append("=");
        stringBuilder1.append((String)entry.getValue());
        stringBuilder1.append("&");
        stringBuilder.append(stringBuilder1.toString());
      } 
    } 
    return stringBuilder.toString();
  }
  
  static void onNetworkChanged() {
    boolean bool = isNetworkAvailable((Context)AppbrandContext.getInst().getApplicationContext());
    final String networkType = getNewNetType((Context)AppbrandContext.getInst().getApplicationContext());
    long l = System.currentTimeMillis();
    if (bool == sLastState && TextUtils.equals(sLastType, str) && l - sLastChangeTime <= 1000L) {
      sLastChangeTime = l;
      return;
    } 
    if (sLastType != null) {
      sLastState = bool;
      sLastType = str;
      sLastChangeTime = l;
      if (TextUtils.equals(str, "none")) {
        if (sHandler != null) {
          sRunnable = new Runnable() {
              public final void run() {
                NetUtil.sendState(networkType);
              }
            };
          sHandler.postDelayed(sRunnable, 1000L);
          return;
        } 
        sendState(str);
        return;
      } 
      sHandler.removeCallbacks(sRunnable);
      sendState(str);
      return;
    } 
    sLastState = bool;
    sLastType = str;
  }
  
  public static Map<String, String> params2Map(String paramString) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    Matcher matcher = Pattern.compile("([^\\?&]+)=([^\\?&]*)").matcher(paramString);
    while (matcher.find())
      hashMap.put(matcher.group(1), matcher.group(2)); 
    return (Map)hashMap;
  }
  
  public static byte[] readImage(String paramString) throws Exception {
    HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL(paramString)).openConnection();
    httpURLConnection.setRequestMethod("GET");
    httpURLConnection.setConnectTimeout(5000);
    return readStream(httpURLConnection.getInputStream());
  }
  
  public static byte[] readStream(InputStream paramInputStream) throws Exception {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (i != -1) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      byteArrayOutputStream.close();
      paramInputStream.close();
      return byteArrayOutputStream.toByteArray();
    } 
  }
  
  public static void registerListener() {
    if (!sNetworkReceiverRegister) {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      AppbrandContext.getInst().getApplicationContext().registerReceiver(new NetworkBroadcastReceiver(), intentFilter);
      sNetworkReceiverRegister = true;
    } 
  }
  
  static void sendState(String paramString) {
    boolean bool = isNetworkAvailable((Context)AppbrandContext.getInst().getApplicationContext());
    String str = getNewNetType((Context)AppbrandContext.getInst().getApplicationContext());
    if (sIsNetAvailable == bool && TextUtils.equals(sNetWorkType, str))
      return; 
    sIsNetAvailable = bool;
    sNetWorkType = str;
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("isConnected", bool);
      jSONObject.put("networkType", paramString);
      AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onNetworkStatusChange", jSONObject.toString());
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_NetUtils", exception.getStackTrace());
      return;
    } 
  }
  
  public static final int showNetType(Context paramContext) {
    String str = getNetType(paramContext);
    if (!TextUtils.isEmpty(str)) {
      if ("WIFI".equalsIgnoreCase(str))
        return 1; 
      if (str.toLowerCase().indexOf("3g") != -1 || "CT".equalsIgnoreCase(str.substring(0, 2)))
        return 2; 
    } 
    return 0;
  }
  
  static class NetChangeRunnable implements Runnable {
    public void run() {
      NetUtil.onNetworkChanged();
    }
  }
  
  static class NetworkBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      AppbrandContext.mainHandler.removeCallbacks(NetUtil.sNetChangeRunnable);
      AppbrandContext.mainHandler.postDelayed(NetUtil.sNetChangeRunnable, 500L);
    }
  }
  
  class NetUtil {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\NetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */