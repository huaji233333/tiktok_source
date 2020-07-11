package com.tt.miniapp.business.frontendapihandle.entity;

import com.tt.frontendapiinterface.g;
import java.util.Iterator;
import org.json.JSONObject;

public class CommonApiOutputParam implements g {
  private final JSONObject mJsonObject;
  
  public CommonApiOutputParam(JSONObject paramJSONObject) {
    this.mJsonObject = paramJSONObject;
  }
  
  public <T> T getData(String paramString) {
    Object object2 = this.mJsonObject.opt(paramString);
    Object object1 = object2;
    if (object2 == JSONObject.NULL)
      object1 = null; 
    return (T)object1;
  }
  
  public Iterator<String> keys() {
    return this.mJsonObject.keys();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\entity\CommonApiOutputParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */