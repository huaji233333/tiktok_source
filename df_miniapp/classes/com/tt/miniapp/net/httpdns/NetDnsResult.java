package com.tt.miniapp.net.httpdns;

import android.text.TextUtils;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetDnsResult {
  public List<InetAddress> addrList = new ArrayList<InetAddress>();
  
  public String cip;
  
  private long fetchTime;
  
  public String host;
  
  public List<String> ips = new ArrayList<String>();
  
  private long ttl;
  
  public static NetDnsResult parseDnsResult(String paramString) {
    boolean bool = TextUtils.isEmpty(paramString);
    String str = null;
    if (bool)
      return null; 
    try {
      NetDnsResult netDnsResult;
      JSONObject jSONObject = new JSONObject(paramString);
      String str1 = jSONObject.optString("host");
      paramString = str;
      if (!TextUtils.isEmpty(str1)) {
        netDnsResult = new NetDnsResult();
        try {
          netDnsResult.host = str1;
          netDnsResult.ttl = jSONObject.optLong("ttl");
          netDnsResult.fetchTime = System.currentTimeMillis() / 1000L;
          JSONArray jSONArray = jSONObject.optJSONArray("ips");
          if (jSONArray != null && jSONArray.length() > 0)
            for (int i = 0;; i++) {
              if (i < jSONArray.length()) {
                str1 = jSONArray.optString(i);
                if (!TextUtils.isEmpty(str1)) {
                  netDnsResult.ips.add(str1);
                  InetAddress inetAddress = NetAddressUtil.parseAddress(str1);
                  if (inetAddress != null)
                    netDnsResult.addrList.add(inetAddress); 
                } 
                continue;
              } 
              netDnsResult.cip = jSONObject.optString("cip");
            }  
          netDnsResult.cip = jSONObject.optString("cip");
        } catch (JSONException jSONException) {}
      } 
      return netDnsResult;
    } catch (JSONException jSONException) {
      return null;
    } 
  }
  
  public static MultiNetDnsResult parseDnsResultList(String paramString) {
    boolean bool = TextUtils.isEmpty(paramString);
    String str = null;
    if (bool)
      return null; 
    try {
      MultiNetDnsResult multiNetDnsResult;
      JSONObject jSONObject = new JSONObject(paramString);
      JSONArray jSONArray = jSONObject.optJSONArray("dns");
      paramString = str;
      if (jSONArray != null) {
        paramString = str;
        if (jSONArray.length() > 0) {
          multiNetDnsResult = new MultiNetDnsResult();
          for (int i = 0;; i++) {
            try {
              if (i < jSONArray.length()) {
                NetDnsResult netDnsResult = parseDnsResult(jSONArray.optJSONObject(i).toString());
                if (netDnsResult != null)
                  multiNetDnsResult.dnsResultList.add(netDnsResult); 
              } else {
                multiNetDnsResult.cip = jSONObject.optString("cip");
                return multiNetDnsResult;
              } 
            } catch (JSONException jSONException) {
              return multiNetDnsResult;
            } 
          } 
        } 
      } 
      return multiNetDnsResult;
    } catch (JSONException jSONException) {
      return null;
    } 
  }
  
  public static class MultiNetDnsResult {
    public String cip;
    
    public List<NetDnsResult> dnsResultList = new ArrayList<NetDnsResult>();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\httpdns\NetDnsResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */