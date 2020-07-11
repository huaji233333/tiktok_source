package com.tt.miniapphost.hostmethod;

import android.app.Activity;
import android.content.Intent;
import org.json.JSONObject;

public interface IHostMethod {
  void call(Activity paramActivity, JSONObject paramJSONObject, HostMethodManager.ResponseCallBack paramResponseCallBack) throws Exception;
  
  String callSync(Activity paramActivity, JSONObject paramJSONObject) throws Exception;
  
  boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent);
  
  boolean shouldHandleActivityResult(JSONObject paramJSONObject);
  
  public static class Stub implements IHostMethod {
    public void call(Activity param1Activity, JSONObject param1JSONObject, HostMethodManager.ResponseCallBack param1ResponseCallBack) throws Exception {}
    
    public String callSync(Activity param1Activity, JSONObject param1JSONObject) throws Exception {
      return null;
    }
    
    public boolean onActivityResult(int param1Int1, int param1Int2, Intent param1Intent) {
      return false;
    }
    
    public boolean shouldHandleActivityResult(JSONObject param1JSONObject) {
      return false;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\hostmethod\IHostMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */