package com.tt.miniapp.msg.download;

import org.json.JSONObject;

public interface DxppCallback {
  void onFail(Exception paramException);
  
  void onFail(String paramString);
  
  void onSuccess(JSONObject paramJSONObject);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\download\DxppCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */