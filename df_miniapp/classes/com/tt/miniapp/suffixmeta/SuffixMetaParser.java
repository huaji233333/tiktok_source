package com.tt.miniapp.suffixmeta;

import android.text.TextUtils;
import com.tt.miniapp.service.suffixmeta.SuffixMetaEntity;
import com.tt.miniapphost.AppBrandLogger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SuffixMetaParser {
  private static List<String> jsonArray2StringList(JSONArray paramJSONArray) {
    ArrayList<String> arrayList = new ArrayList();
    if (paramJSONArray == null)
      return arrayList; 
    try {
      int j = paramJSONArray.length();
      for (int i = 0; i < j; i++) {
        String str = paramJSONArray.optString(i);
        if (!TextUtils.isEmpty(str))
          arrayList.add(str); 
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("SuffixMetaParser", new Object[] { exception });
    } 
    return arrayList;
  }
  
  public static SuffixMetaEntity parse(String paramString) {
    try {
      return parse(new JSONObject(paramString));
    } catch (JSONException jSONException) {
      AppBrandLogger.e("SuffixMetaParser", new Object[] { jSONException });
      return null;
    } 
  }
  
  public static SuffixMetaEntity parse(JSONObject paramJSONObject) {
    String str = paramJSONObject.optString("shield_page");
    List<String> list = jsonArray2StringList(paramJSONObject.optJSONArray("shareChannelBlackList"));
    SuffixMetaEntity suffixMetaEntity = new SuffixMetaEntity();
    suffixMetaEntity.shieldPage = str;
    suffixMetaEntity.shareChannelBlackList = list;
    suffixMetaEntity.mNativeOrH5 = paramJSONObject.optInt("nativeOrH5", -2147483648);
    suffixMetaEntity.isNew = paramJSONObject.optInt("is_new", 0);
    suffixMetaEntity.mLivePlayNativeOrH5 = paramJSONObject.optInt("liveNativeOrH5", -2147483648);
    suffixMetaEntity.awemeUserId = paramJSONObject.optString("aweme_user_id");
    suffixMetaEntity.awemeSecUserId = paramJSONObject.optString("aweme_sec_user_id");
    return suffixMetaEntity;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\suffixmeta\SuffixMetaParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */