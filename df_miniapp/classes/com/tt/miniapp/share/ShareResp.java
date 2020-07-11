package com.tt.miniapp.share;

import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareResp {
  public Data data;
  
  public int errNo;
  
  public String message;
  
  public static ShareResp newDefaultShareResp(String paramString) {
    ShareResp shareResp = new ShareResp();
    if (paramString != null)
      try {
        JSONObject jSONObject = new JSONObject(paramString);
        shareResp.errNo = jSONObject.optInt("err_no");
        shareResp.message = jSONObject.optString("message");
        Data data = new Data();
        jSONObject = jSONObject.optJSONObject("data");
        if (jSONObject != null) {
          data.title = jSONObject.optString("title");
          data.desc = jSONObject.optString("description");
          data.imageUrl = jSONObject.optString("image_url");
          shareResp.data = data;
          return shareResp;
        } 
      } catch (JSONException jSONException) {
        AppBrandLogger.stacktrace(6, "ShareResp", jSONException.getStackTrace());
      }  
    return shareResp;
  }
  
  public static ShareResp newShareResp(String paramString) {
    ShareResp shareResp = new ShareResp();
    if (paramString != null)
      try {
        JSONObject jSONObject = new JSONObject(paramString);
        shareResp.errNo = jSONObject.optInt("err_no");
        shareResp.message = jSONObject.optString("message");
        Data data = new Data();
        jSONObject = jSONObject.optJSONObject("data");
        if (jSONObject != null) {
          data.token = jSONObject.optString("token");
          data.ugUrl = jSONObject.optString("ug_url");
          data.title = jSONObject.optString("title");
          data.desc = jSONObject.optString("description");
          data.imageUrl = jSONObject.optString("image_url");
          data.miniImageUrl = jSONObject.optString("mini_image_url");
          data.shareExtra = jSONObject.optString("share_extra");
        } 
        shareResp.data = data;
        return shareResp;
      } catch (JSONException jSONException) {
        AppBrandLogger.stacktrace(6, "ShareResp", jSONException.getStackTrace());
      }  
    return shareResp;
  }
  
  public static class Data {
    public String desc;
    
    public String imageUrl;
    
    public String miniImageUrl;
    
    public String shareExtra;
    
    public String title;
    
    public String token;
    
    public String ugUrl;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\share\ShareResp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */