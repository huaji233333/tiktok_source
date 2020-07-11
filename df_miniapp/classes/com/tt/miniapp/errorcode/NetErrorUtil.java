package com.tt.miniapp.errorcode;

import android.content.Context;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppbrandContext;

public class NetErrorUtil {
  private static String[] mConnectErrorDic;
  
  private static String[] mDNSErrorDic;
  
  private static String[] mNetworkChangedDic;
  
  private static String[] mNetworkDisableDic = new String[] { "network not available", "network_not_available" };
  
  static {
    mNetworkChangedDic = new String[] { "network_changed", "network changed" };
    mDNSErrorDic = new String[] { "unknownhost", "no address associated with hostname", "unknownhostexception", "err_name_not_resolved" };
    mConnectErrorDic = new String[] { "err_ttnet_app_timed_out", "err_address_unreachable", "err_connection_aborted", "err_connection_refused", "err_network_changed" };
  }
  
  private static boolean contains(String[] paramArrayOfString, String paramString) {
    int j = paramArrayOfString.length;
    for (int i = 0; i < j; i++) {
      String str = paramArrayOfString[i];
      if (paramString.toLowerCase().contains(str))
        return true; 
    } 
    return false;
  }
  
  public static String getHttpCode(int paramInt, String paramString) {
    if (isSuccessful(paramInt))
      return ErrorCode.NETWORK.SUCCESS.getCodeStr(); 
    if (isNetworkDisable(paramString))
      return ErrorCode.NETWORK.NETWORK_NOT_AVAILABLE.getCodeStr(); 
    if (isNetworkChanged(paramString))
      return ErrorCode.NETWORK.NETWORK_CHANGED_ERROR.getCodeStr(); 
    if (isDNSError(paramString))
      return ErrorCode.NETWORK.NETWORK_DNS_ERROR.getCodeStr(); 
    if (isConnectError(paramString))
      return ErrorCode.NETWORK.NETWORK_CONNECT_ERROR.getCodeStr(); 
    StringBuilder stringBuilder = new StringBuilder(ErrorCode.NETWORK.NETWORK_UNKNOWN_ERROR.getCodeStr());
    stringBuilder.append("(");
    stringBuilder.append(paramInt);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  private static boolean isConnectError(String paramString) {
    return contains(mConnectErrorDic, paramString);
  }
  
  private static boolean isDNSError(String paramString) {
    return contains(mDNSErrorDic, paramString);
  }
  
  private static boolean isNetworkChanged(String paramString) {
    return contains(mNetworkChangedDic, paramString);
  }
  
  private static boolean isNetworkDisable(String paramString) {
    return (!NetUtil.isNetworkAvailable((Context)AppbrandContext.getInst().getApplicationContext()) || contains(mNetworkDisableDic, paramString));
  }
  
  public static boolean isSuccessful(int paramInt) {
    return (paramInt >= 200 && paramInt < 300);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\errorcode\NetErrorUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */