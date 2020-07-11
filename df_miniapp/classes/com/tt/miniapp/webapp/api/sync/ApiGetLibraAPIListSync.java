package com.tt.miniapp.webapp.api.sync;

import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.reflect.Field;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiGetLibraAPIListSync extends SyncMsgCtrl {
  public ApiGetLibraAPIListSync(String paramString) {
    super(paramString);
  }
  
  public String act() {
    JSONObject jSONObject = new JSONObject();
    JSONArray jSONArray = new JSONArray();
    Field[] arrayOfField = AppbrandConstant.AppApi.LIBRA_API.class.getDeclaredFields();
    try {
      int j = arrayOfField.length;
      for (int i = 0; i < j; i++)
        jSONArray.put(arrayOfField[i].get(null)); 
    } catch (IllegalAccessException illegalAccessException) {
      AppBrandLogger.e("tma_getLibraAPIList", new Object[] { illegalAccessException });
    } 
    try {
      jSONObject.put("list", jSONArray);
      return makeOkMsg(jSONObject);
    } catch (Exception exception) {
      AppBrandLogger.e("tma_getLibraAPIList", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "getLibraAPIList";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webapp\api\sync\ApiGetLibraAPIListSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */