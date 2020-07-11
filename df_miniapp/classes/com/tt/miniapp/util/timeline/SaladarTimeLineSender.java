package com.tt.miniapp.util.timeline;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import d.f.b.l;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class SaladarTimeLineSender extends AbsTimeLineSender {
  private boolean isEnableTrace = true;
  
  public SaladarTimeLineSender(Looper paramLooper) {
    super(paramLooper);
  }
  
  private final JSONObject packData(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("points", paramString);
      jSONObject.put("unique_id", getMUniqueId());
      jSONObject.put("index", getMGroupIndex().getAndIncrement());
      jSONObject.put("cpu_time", SystemClock.elapsedRealtime());
      return jSONObject;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable(getClass().getName(), "", (Throwable)jSONException);
      return jSONObject;
    } 
  }
  
  public final boolean isEnableTrace() {
    return this.isEnableTrace;
  }
  
  public final boolean isReadySend() {
    return true;
  }
  
  public final boolean isThreshold() {
    return (getMStashPointList().size() >= 50);
  }
  
  public final void onAppInfoInited(AppInfoEntity paramAppInfoEntity) {
    l.b(paramAppInfoEntity, "appInfo");
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    Context context = (Context)appbrandContext.getApplicationContext();
    Enum enum_ = (Enum)Settings.TT_TIMELINE_SWITCH;
    boolean bool2 = false;
    boolean bool3 = SettingsDAO.getBoolean(context, false, new Enum[] { enum_, (Enum)Settings.TimeLineSwitch.SWITCH });
    boolean bool4 = TextUtils.isEmpty(paramAppInfoEntity.timelineServerUrl);
    boolean bool1 = bool2;
    if (!bool3) {
      bool1 = bool2;
      if ((bool4 ^ true) == 0)
        bool1 = true; 
    } 
    this.isEnableTrace = bool1;
    if (!this.isEnableTrace)
      release(); 
  }
  
  public final void realSendData(String paramString) {
    l.b(paramString, "content");
    AppBrandMonitor.statusRate("mp_load_timeline", 0, packData(paramString));
  }
  
  public final void realSendData(JSONArray paramJSONArray) {
    l.b(paramJSONArray, "ja");
    String str = paramJSONArray.toString();
    l.a(str, "ja.toString()");
    realSendData(str);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\timeline\SaladarTimeLineSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */