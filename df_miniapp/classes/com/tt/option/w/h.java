package com.tt.option.w;

import android.net.Uri;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import com.tt.miniapphost.util.CharacterUtils;
import java.io.Serializable;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class h implements Serializable {
  public String anchorExtra;
  
  public AppInfoEntity appInfo;
  
  public String channel;
  
  public String desc;
  
  public String entryPath;
  
  public String extra;
  
  public String imageUrl;
  
  public boolean isExtraContainVideoPath;
  
  public String linkTitle;
  
  public String miniImageUrl;
  
  public int orientation;
  
  public String queryString;
  
  public String schema;
  
  public String shareType;
  
  public String snapshotUrl;
  
  public String templateId;
  
  public String title;
  
  public String token;
  
  public String ugUrl;
  
  public boolean withShareTicket;
  
  public static h parse(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    try {
      JSONObject jSONObject2 = new JSONObject(paramString);
      h h1 = new h();
      h1.channel = jSONObject2.optString("channel");
      h1.title = jSONObject2.optString("title");
      h1.desc = jSONObject2.optString("desc");
      h1.linkTitle = jSONObject2.optString("linkTitle");
      h1.imageUrl = jSONObject2.optString("imageUrl");
      h1.appInfo = AppbrandApplication.getInst().getAppInfo();
      h1.templateId = jSONObject2.optString("templateId");
      h1.withShareTicket = jSONObject2.optBoolean("withShareTicket", false);
      paramString = jSONObject2.optString("query");
      String str = jSONObject2.optString("path");
      if (h1.appInfo.type != 2)
        paramString = str; 
      h1.queryString = paramString;
      JSONObject jSONObject1 = jSONObject2.optJSONObject("extra");
      if (jSONObject1 != null) {
        str = jSONObject1.optString("videoPath");
        if (!TextUtils.isEmpty(str)) {
          h1.isExtraContainVideoPath = true;
          str = FileManager.inst().getRealFilePath(str);
          if (!TextUtils.isEmpty(str)) {
            jSONObject1.put("videoPath", str);
            boolean bool = TextUtils.equals(str, AppbrandConstant.getVideoFilePath());
            if (bool || TextUtils.equals(str, AppbrandConstant.getMergeVideoFilePath()) || TextUtils.equals(str, AppbrandConstant.getPreEditVideoPath())) {
              jSONObject1.put("videoType", 1);
            } else {
              jSONObject1.put("videoType", 0);
            } 
          } 
        } 
        h1.extra = jSONObject1.toString();
      } else {
        h1.extra = "";
      } 
      h1.entryPath = jSONObject2.optString("entryPath");
      h1.schema = toSchema(h1, true);
      return h1;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ShareInfoModel", exception.getStackTrace());
      return null;
    } 
  }
  
  public static String toSchema(h paramh, boolean paramBoolean) {
    MicroSchemaEntity.Host host;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("name", paramh.appInfo.appName);
    hashMap.put("icon", paramh.appInfo.icon);
    MicroSchemaEntity.Builder builder2 = (new MicroSchemaEntity.Builder()).appId(paramh.appInfo.appId).versionType(MicroSchemaEntity.VersionType.fromString(paramh.appInfo.versionType)).meta(hashMap).protocol(AppbrandContext.getInst().getInitParams().getHostStr(1008, "sslocal"));
    if (paramh.appInfo.isGame()) {
      host = MicroSchemaEntity.Host.MICROGAME;
    } else {
      host = MicroSchemaEntity.Host.MICROAPP;
    } 
    MicroSchemaEntity.Builder builder1 = builder2.host(host);
    if (paramBoolean) {
      HashMap<Object, Object> hashMap1 = new HashMap<Object, Object>();
      hashMap1.put("launch_from", "publish_weitoutiao");
      builder1.bdpLog(hashMap1);
    } 
    if (paramh.appInfo.type == 2 && !TextUtils.isEmpty(paramh.queryString)) {
      try {
        builder1.query(CharacterUtils.getMapFromJson(Uri.decode(paramh.queryString)));
      } catch (Exception exception) {}
    } else {
      String str1 = paramh.queryString;
      if (!TextUtils.isEmpty(str1))
        builder1.path(Uri.decode(str1)); 
    } 
    String str = paramh.appInfo.token;
    if (!TextUtils.isEmpty(str))
      builder1.token(str); 
    return builder1.build().toSchema();
  }
  
  public final JSONObject toJson() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("appId", this.appInfo.appId);
      jSONObject.put("appName", this.appInfo.appName);
      jSONObject.put("appIcon", this.appInfo.icon);
      jSONObject.put("appType", this.appInfo.type);
      jSONObject.put("desc", this.desc);
      jSONObject.put("extra", this.extra);
      jSONObject.put("anchorExtra", this.anchorExtra);
      jSONObject.put("snapshotUrl", this.snapshotUrl);
      jSONObject.put("imageUrl", this.imageUrl);
      jSONObject.put("miniImageUrl", this.miniImageUrl);
      jSONObject.put("query", this.queryString);
      jSONObject.put("schema", toSchema(this, false));
      jSONObject.put("title", this.title);
      jSONObject.put("token", this.token);
      jSONObject.put("ttid", this.appInfo.ttId);
      jSONObject.put("withShareTicket", this.withShareTicket);
      jSONObject.put("ugUrl", this.ugUrl);
      jSONObject.put("channel", this.channel);
      jSONObject.put("linkTitle", this.linkTitle);
      jSONObject.put("templateId", this.templateId);
      return jSONObject;
    } catch (JSONException jSONException) {
      return jSONObject;
    } 
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder("ShareInfoModel{\n\nchannel='");
    stringBuilder.append(this.channel);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n title='");
    stringBuilder.append(this.title);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n linkTitle='");
    stringBuilder.append(this.linkTitle);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n imageUrl='");
    stringBuilder.append(this.imageUrl);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n queryString='");
    stringBuilder.append(this.queryString);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n extra='");
    stringBuilder.append(this.extra);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n isExtraContainVideoPath=");
    stringBuilder.append(this.isExtraContainVideoPath);
    stringBuilder.append(",\n\n appInfo=");
    stringBuilder.append(this.appInfo);
    stringBuilder.append(",\n\n entryPath='");
    stringBuilder.append(this.entryPath);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n token='");
    stringBuilder.append(this.token);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n miniImageUrl='");
    stringBuilder.append(this.miniImageUrl);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n ugUrl='");
    stringBuilder.append(this.ugUrl);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n schema='");
    stringBuilder.append(this.schema);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n withShareTicket=");
    stringBuilder.append(this.withShareTicket);
    stringBuilder.append(",\n\n shareType='");
    stringBuilder.append(this.shareType);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n desc='");
    stringBuilder.append(this.desc);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n orientation=");
    stringBuilder.append(this.orientation);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\w\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */