package com.bytedance.sandboxapp.protocol.service.b;

import com.bytedance.sandboxapp.b.b;
import org.json.JSONArray;
import org.json.JSONObject;

public interface a extends b {
  JSONArray getApiBlackList();
  
  JSONArray getApiWhiteList();
  
  String getAppId();
  
  JSONObject getExtConfigInfoJson();
  
  int getPkgType();
  
  String getPlatformSession();
  
  String getSchema();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */