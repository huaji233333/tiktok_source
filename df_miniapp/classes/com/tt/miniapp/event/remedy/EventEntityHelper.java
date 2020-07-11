package com.tt.miniapp.event.remedy;

import android.text.TextUtils;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class EventEntityHelper {
  public static String getEventNameFromSaveKey(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return CharacterUtils.empty(); 
    String[] arrayOfString = paramString.split("@");
    return (arrayOfString.length != 2) ? CharacterUtils.empty() : arrayOfString[0];
  }
  
  public static String getSaveKey(String paramString, EventEntity paramEventEntity) {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(paramEventEntity.eventName);
    stringBuilder1.append("@");
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append(paramEventEntity.uniqueKey);
    stringBuilder1.append(CharacterUtils.md5Hex(stringBuilder2.toString()));
    return stringBuilder1.toString();
  }
  
  public static String getSaveVal(EventEntity paramEventEntity) {
    return paramEventEntity.eventData.toString();
  }
  
  public static String getUniqueKey(String paramString, JSONObject paramJSONObject) {
    return TextUtils.isEmpty(paramString) ? CharacterUtils.empty() : (("mp_page_load_start".equals(paramString) || "mp_page_load_result".equals(paramString)) ? paramJSONObject.optString("page_path", CharacterUtils.empty()) : CharacterUtils.empty());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\EventEntityHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */