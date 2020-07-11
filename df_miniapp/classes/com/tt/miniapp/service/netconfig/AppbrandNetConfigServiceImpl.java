package com.tt.miniapp.service.netconfig;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class AppbrandNetConfigServiceImpl implements AppbrandNetConfigService {
  private ConcurrentHashMap<String, String> cachedTTRequestTypeMap = new ConcurrentHashMap<String, String>();
  
  public String getTTRequestType(Context paramContext, String paramString) {
    if (paramString != null) {
      String str = this.cachedTTRequestTypeMap.get(paramString);
      if (str != null)
        return str; 
    } 
    Context context = paramContext.getApplicationContext();
    String str2 = SettingsDAO.getString(context, "default", new Enum[] { (Enum)Settings.BDP_TTREQUEST_CONFIG, (Enum)Settings.BdpTtRequestConfig.REQUEST_TYPE });
    String str1 = str2;
    if (paramString != null) {
      JSONObject jSONObject = SettingsDAO.getJSONObject(context, new Enum[] { (Enum)Settings.BDP_TTREQUEST_CONFIG, (Enum)Settings.BdpTtRequestConfig.MP_IDS });
      str1 = str2;
      if (jSONObject != null) {
        String str = jSONObject.optString(paramString);
        str1 = str2;
        if (!TextUtils.isEmpty(str))
          str1 = str; 
      } 
      this.cachedTTRequestTypeMap.put(paramString, str1);
    } 
    return str1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\netconfig\AppbrandNetConfigServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */