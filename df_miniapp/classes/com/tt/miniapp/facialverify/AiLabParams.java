package com.tt.miniapp.facialverify;

import org.json.JSONObject;

public class AiLabParams {
  public String busi_type;
  
  public String merchant_app_id;
  
  public String merchant_id;
  
  public String source;
  
  public String uid;
  
  public AiLabParams(JSONObject paramJSONObject) {
    this.merchant_id = paramJSONObject.optString("merchant_id");
    this.merchant_app_id = paramJSONObject.optString("merchant_app_id");
    this.busi_type = paramJSONObject.optString("busi_type");
    this.source = paramJSONObject.optString("source");
    this.uid = paramJSONObject.optString("uid");
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("AiLabParams{merchant_id='");
    stringBuilder.append(this.merchant_id);
    stringBuilder.append('\'');
    stringBuilder.append(", merchant_app_id='");
    stringBuilder.append(this.merchant_app_id);
    stringBuilder.append('\'');
    stringBuilder.append(", source='");
    stringBuilder.append(this.source);
    stringBuilder.append('\'');
    stringBuilder.append(", busi_type='");
    stringBuilder.append(this.busi_type);
    stringBuilder.append('\'');
    stringBuilder.append(", uid='");
    stringBuilder.append(this.uid);
    stringBuilder.append('\'');
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\facialverify\AiLabParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */