package com.tt.miniapp.offlinezip;

import android.content.Context;
import android.net.Uri;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.data.SettingsManager;
import com.tt.miniapp.settings.data.SettingsUpdateListener;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.ProcessUtil;
import d.a.g;
import d.f.b.l;
import d.f.b.w;
import d.m.p;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public final class OfflineZipManager implements IOfflineZipService {
  public static final OfflineZipManager INSTANCE = new OfflineZipManager();
  
  public final void checkOfflineModuleNeedUpdate(Context paramContext, JSONObject paramJSONObject, ArrayList<OfflineZipEntity> paramArrayList) {
    try {
      String str2 = paramJSONObject.getString("path");
      String str3 = paramJSONObject.getString("url");
      String str1 = paramJSONObject.getString("md5");
      l.a(str2, "moduleName");
      l.a(str1, "md5");
      if (isOfflineModuleNeedUpdate(paramContext, str2, str1)) {
        l.a(str3, "url");
        paramArrayList.add(new OfflineZipEntity(str2, str3, str1));
        return;
      } 
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_OfflineZipManager", new Object[] { "checkOfflineModuleNeedUpdate", jSONException });
    } 
  }
  
  public final void checkUpdateOfflineZip(Context paramContext, OnOfflineZipCheckUpdateResultListener paramOnOfflineZipCheckUpdateResultListener, String... paramVarArgs) {
    l.b(paramContext, "context");
    l.b(paramVarArgs, "moduleNames");
    if (ProcessUtil.isMainProcess(paramContext)) {
      checkUpdateOfflineZipInMainProcess(paramContext, g.f((Object[])paramVarArgs), paramOnOfflineZipCheckUpdateResultListener);
      return;
    } 
    InnerHostProcessBridge.checkUpdateOfflineZip(g.f((Object[])paramVarArgs), paramOnOfflineZipCheckUpdateResultListener);
  }
  
  public final void checkUpdateOfflineZipInMainProcess(Context paramContext, List<String> paramList, OnOfflineZipCheckUpdateResultListener paramOnOfflineZipCheckUpdateResultListener) {
    l.b(paramContext, "context");
    l.b(paramList, "moduleNames");
    SettingsManager.getInstance().updateSettings(new OfflineZipManager$checkUpdateOfflineZipInMainProcess$1(paramContext, paramList, paramOnOfflineZipCheckUpdateResultListener));
  }
  
  public final String getDEBUG_FLAG() {
    return this.$$delegate_0.getDEBUG_FLAG();
  }
  
  public final String getEXTERNAL_OFFLINE_PATH() {
    return this.$$delegate_0.getEXTERNAL_OFFLINE_PATH();
  }
  
  public final int getInternalOfflineZipVersion(Context paramContext) {
    l.b(paramContext, "context");
    return this.$$delegate_0.getInternalOfflineZipVersion(paramContext);
  }
  
  public final String getMD5_FILE_SUFFIX() {
    return this.$$delegate_0.getMD5_FILE_SUFFIX();
  }
  
  public final JSONObject getOfflineZipSettings(Context paramContext) {
    JSONObject jSONObject = SettingsDAO.getJSONObject(paramContext, new Enum[] { (Enum)Settings.BDP_OFFLINE_ZIP });
    l.a(jSONObject, "SettingsDAO.getJSONObjec…Settings.BDP_OFFLINE_ZIP)");
    return jSONObject;
  }
  
  public final String getSpecifiedOfflineModuleVersion(Context paramContext, File paramFile, String paramString) {
    l.b(paramContext, "context");
    l.b(paramFile, "rootPath");
    l.b(paramString, "moduleName");
    return this.$$delegate_0.getSpecifiedOfflineModuleVersion(paramContext, paramFile, paramString);
  }
  
  public final String getSpecifiedOfflineModuleVersion(Context paramContext, String paramString) {
    l.b(paramContext, "context");
    l.b(paramString, "moduleName");
    return this.$$delegate_0.getSpecifiedOfflineModuleVersion(paramContext, paramString);
  }
  
  public final String getZIP_FILE_SUFFIX() {
    return this.$$delegate_0.getZIP_FILE_SUFFIX();
  }
  
  public final void init(Context paramContext) {
    l.b(paramContext, "context");
    OfflineZipUpdateManager.INSTANCE.init(paramContext);
  }
  
  public final boolean isOfflineModuleNeedUpdate(Context paramContext, String paramString1, String paramString2) {
    l.b(paramContext, "context");
    l.b(paramString1, "moduleName");
    l.b(paramString2, "md5");
    return this.$$delegate_0.isOfflineModuleNeedUpdate(paramContext, paramString1, paramString2);
  }
  
  public final String offlineUrlToFileUrl(Context paramContext, Uri paramUri) {
    l.b(paramContext, "context");
    l.b(paramUri, "uri");
    if (l.a("ttoffline", paramUri.getScheme())) {
      String str = paramUri.toString();
      l.a(str, "uri.toString()");
      str = p.a(str, "ttoffline:/", "", false, 4, null);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Uri.fromFile(AppbrandUtil.getOfflineZipDir(paramContext)).toString());
      stringBuilder.append(str);
      return stringBuilder.toString();
    } 
    return "";
  }
  
  public final String offlineUrlToFileUrl(Context paramContext, String paramString) {
    l.b(paramContext, "context");
    l.b(paramString, "url");
    Uri uri = Uri.parse(paramString);
    l.a(uri, "Uri.parse(url)");
    return offlineUrlToFileUrl(paramContext, uri);
  }
  
  public final void setInternalOfflineZipVersion(Context paramContext, int paramInt) {
    l.b(paramContext, "context");
    this.$$delegate_0.setInternalOfflineZipVersion(paramContext, paramInt);
  }
  
  public final void setSpecifiedOfflineModuleVersion(File paramFile, String paramString) {
    l.b(paramFile, "moduleDir");
    l.b(paramString, "md5");
    this.$$delegate_0.setSpecifiedOfflineModuleVersion(paramFile, paramString);
  }
  
  static final class OfflineZipManager$checkUpdateOfflineZipInMainProcess$1 implements SettingsUpdateListener {
    OfflineZipManager$checkUpdateOfflineZipInMainProcess$1(Context param1Context, List param1List, OnOfflineZipCheckUpdateResultListener param1OnOfflineZipCheckUpdateResultListener) {}
    
    public final void onUpdateComplete() {
      JSONObject jSONObject = OfflineZipManager.INSTANCE.getOfflineZipSettings(this.$context);
      if (this.$moduleNames.isEmpty()) {
        Iterator<String> iterator = jSONObject.keys();
        l.a(iterator, "offlineZipSettings.keys()");
        while (iterator.hasNext()) {
          String str = iterator.next();
          List<String> list = this.$moduleNames;
          l.a(str, "it");
          list.add(str);
        } 
      } 
      ArrayList<OfflineZipEntity> arrayList = new ArrayList();
      for (String str : this.$moduleNames) {
        JSONObject jSONObject1;
        if (jSONObject.has(str)) {
          jSONObject1 = jSONObject.optJSONObject(str);
          OfflineZipManager offlineZipManager = OfflineZipManager.INSTANCE;
          Context context = this.$context;
          l.a(jSONObject1, "specifiedModuleSettings");
          offlineZipManager.checkOfflineModuleNeedUpdate(context, jSONObject1, arrayList);
          continue;
        } 
        StringBuilder stringBuilder = new StringBuilder("checkUpdateOfflineZip, ");
        stringBuilder.append("offlineZip settings does not have module: ");
        stringBuilder.append((String)jSONObject1);
        AppBrandLogger.e("tma_OfflineZipManager", new Object[] { stringBuilder.toString() });
      } 
      if (arrayList.isEmpty()) {
        OnOfflineZipCheckUpdateResultListener onOfflineZipCheckUpdateResultListener = this.$listener;
        if (onOfflineZipCheckUpdateResultListener != null) {
          onOfflineZipCheckUpdateResultListener.onComplete(true);
          return;
        } 
        return;
      } 
      w.c c = new w.c();
      c.element = 0;
      w.a a = new w.a();
      a.element = true;
      OfflineZipUpdateManager.INSTANCE.updateModule(this.$context, arrayList, new OnOfflineZipUpdateResultListener(c, arrayList, a) {
            public final void onFailed(String param1String) {
              l.b(param1String, "moduleName");
              this.$result.element = false;
              w.c c1 = this.$callbackNum;
              c1.element++;
              if (c1.element == this.$modulesNeedUpdate.size()) {
                OnOfflineZipCheckUpdateResultListener onOfflineZipCheckUpdateResultListener = OfflineZipManager$checkUpdateOfflineZipInMainProcess$1.this.$listener;
                if (onOfflineZipCheckUpdateResultListener != null)
                  onOfflineZipCheckUpdateResultListener.onComplete(this.$result.element); 
              } 
            }
            
            public final void onSuccess(String param1String) {
              l.b(param1String, "moduleName");
              w.c c1 = this.$callbackNum;
              c1.element++;
              if (c1.element == this.$modulesNeedUpdate.size()) {
                OnOfflineZipCheckUpdateResultListener onOfflineZipCheckUpdateResultListener = OfflineZipManager$checkUpdateOfflineZipInMainProcess$1.this.$listener;
                if (onOfflineZipCheckUpdateResultListener != null)
                  onOfflineZipCheckUpdateResultListener.onComplete(this.$result.element); 
              } 
            }
          });
    }
  }
  
  public static final class null implements OnOfflineZipUpdateResultListener {
    null(w.c param1c, ArrayList param1ArrayList, w.a param1a) {}
    
    public final void onFailed(String param1String) {
      l.b(param1String, "moduleName");
      this.$result.element = false;
      w.c c1 = this.$callbackNum;
      c1.element++;
      if (c1.element == this.$modulesNeedUpdate.size()) {
        OnOfflineZipCheckUpdateResultListener onOfflineZipCheckUpdateResultListener = OfflineZipManager$checkUpdateOfflineZipInMainProcess$1.this.$listener;
        if (onOfflineZipCheckUpdateResultListener != null)
          onOfflineZipCheckUpdateResultListener.onComplete(this.$result.element); 
      } 
    }
    
    public final void onSuccess(String param1String) {
      l.b(param1String, "moduleName");
      w.c c1 = this.$callbackNum;
      c1.element++;
      if (c1.element == this.$modulesNeedUpdate.size()) {
        OnOfflineZipCheckUpdateResultListener onOfflineZipCheckUpdateResultListener = OfflineZipManager$checkUpdateOfflineZipInMainProcess$1.this.$listener;
        if (onOfflineZipCheckUpdateResultListener != null)
          onOfflineZipCheckUpdateResultListener.onComplete(this.$result.element); 
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\offlinezip\OfflineZipManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */