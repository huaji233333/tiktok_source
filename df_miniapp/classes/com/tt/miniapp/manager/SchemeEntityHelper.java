package com.tt.miniapp.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.q.i;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class SchemeEntityHelper {
  private static ConcurrentHashMap<String, MicroSchemaEntity> sCache = new ConcurrentHashMap<String, MicroSchemaEntity>();
  
  public static MicroSchemaEntity parseFromScheme(String paramString) {
    if (paramString != null) {
      if (sCache.containsKey(paramString))
        return sCache.get(paramString); 
      MicroSchemaEntity microSchemaEntity = MicroSchemaEntity.parseFromSchema(paramString);
      if (microSchemaEntity != null)
        sCache.put(paramString, microSchemaEntity); 
      return microSchemaEntity;
    } 
    return null;
  }
  
  public static void remoteValidate(final Activity miniAppActivity, int paramInt, final String scheme) {
    if (miniAppActivity != null && SettingsDAO.getInt((Context)miniAppActivity, 1, new Enum[] { (Enum)Settings.TT_TMA_SWITCH, (Enum)Settings.TmaSwitch.DISABLE_SCHEMA_REMOTE_VALIDATION_FOR_TEST_CHANNEL }) != 1)
      Observable.create(new Function<JSONObject>() {
            public final JSONObject fun() {
              i i = new i(AppbrandConstant.OpenApi.getInst().getSchemaV2ValidationUrl(), "POST");
              try {
                i.a("aid", Long.valueOf(Long.parseLong(AppbrandContext.getInst().getInitParams().getAppId())));
                i.a("schema", scheme);
                return new JSONObject(HostDependManager.getInst().doPostBody(i).a());
              } catch (Exception exception) {
                AppBrandLogger.e("schemaRemoteValidation", new Object[] { exception });
                return null;
              } 
            }
          }).schudleOn(Schedulers.longIO()).observeOn(Schedulers.ui()).subscribe((Subscriber)new Subscriber.NoConcernSubscriber<JSONObject>() {
            public final void onSuccess(JSONObject param1JSONObject) {
              if (param1JSONObject != null)
                try {
                  if (param1JSONObject.getInt("err_no") == 0) {
                    int i = param1JSONObject.getInt("result");
                    if (i == 2) {
                      (new AlertDialog.Builder((Context)miniAppActivity)).setMessage(param1JSONObject.getString("toast")).create().show();
                      return;
                    } 
                    if (i == 3) {
                      (new AlertDialog.Builder((Context)miniAppActivity)).setMessage(param1JSONObject.getString("toast")).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            public void onDismiss(DialogInterface param2DialogInterface) {
                              ToolUtils.onActivityExit(miniAppActivity, 12);
                            }
                          }).create().show();
                      return;
                    } 
                  } 
                } catch (JSONException jSONException) {
                  AppBrandLogger.e("schemaRemoteValidation", new Object[] { jSONException });
                }  
            }
          }); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\SchemeEntityHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */