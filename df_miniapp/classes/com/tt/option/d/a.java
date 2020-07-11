package com.tt.option.d;

import android.content.Context;
import com.tt.miniapp.msg.download.DxppCallback;
import org.json.JSONArray;
import org.json.JSONObject;

public interface a {
  void createDxppTask(Context paramContext, String paramString1, boolean paramBoolean, String paramString2, String paramString3, String paramString4, JSONArray paramJSONArray, String paramString5, JSONObject paramJSONObject, long paramLong, DxppCallback paramDxppCallback);
  
  JSONObject getDxppTaskStatus(String paramString1, String paramString2, String paramString3, boolean paramBoolean);
  
  void operateDxppTask(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, String paramString5, JSONArray paramJSONArray, String paramString6, JSONObject paramJSONObject, boolean paramBoolean, DxppCallback paramDxppCallback);
  
  boolean supportDxpp();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */