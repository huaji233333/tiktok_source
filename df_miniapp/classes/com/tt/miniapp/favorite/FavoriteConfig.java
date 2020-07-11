package com.tt.miniapp.favorite;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppbrandContext;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FavoriteConfig {
  public boolean cpCustomTip;
  
  public final List<String> feedSceneList = new ArrayList<String>();
  
  public long showIntervalBar;
  
  public long showIntervalTip;
  
  public String tip;
  
  private FavoriteConfig() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    boolean bool = true;
    Settings settings = Settings.BDP_FAVORITES;
    int i = 0;
    JSONObject jSONObject = SettingsDAO.getJSONObject((Context)application, new Enum[] { (Enum)settings });
    if (jSONObject != null) {
      this.tip = jSONObject.optString("tip_str");
      if (jSONObject.optInt("enable_cp_tip_str", 0) == 0)
        bool = false; 
      this.cpCustomTip = bool;
      JSONArray jSONArray = jSONObject.optJSONArray("feed_scene_list");
      if (jSONArray != null) {
        int j = jSONArray.length();
        while (i < j) {
          this.feedSceneList.add(jSONArray.optString(i));
          i++;
        } 
      } 
      jSONObject = jSONObject.optJSONObject("interval");
      if (jSONObject != null) {
        this.showIntervalTip = (long)(jSONObject.optDouble("bubble") * 1000.0D);
        this.showIntervalBar = (long)(jSONObject.optDouble("floating") * 1000.0D);
      } 
    } 
    if (TextUtils.isEmpty(this.tip))
      this.tip = application.getResources().getString(2097741893); 
    if (this.showIntervalTip <= 0L)
      this.showIntervalTip = 259200000L; 
    if (this.showIntervalBar <= 0L)
      this.showIntervalBar = 604800000L; 
  }
  
  public static FavoriteConfig get() {
    return new FavoriteConfig();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\favorite\FavoriteConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */