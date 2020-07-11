package com.tt.miniapp.badcase;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.service.suffixmeta.SuffixMetaEntity;
import com.tt.miniapp.service.suffixmeta.SuffixMetaServiceInterface;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import org.json.JSONArray;
import org.json.JSONException;

public class BlockPageManager extends AppbrandServiceManager.ServiceBase {
  public BlockPageManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private boolean disablePageBlock() {
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    Application application = AppbrandContext.getInst().getApplicationContext();
    return !appInfoEntity.isLocalTest() ? ((SettingsDAO.getInt((Context)application, 0, new Enum[] { (Enum)Settings.TT_TMA_SWITCH, (Enum)Settings.TmaSwitch.PAGE_BLOCK }) != 1)) : true;
  }
  
  public void handleColdLaunch() {
    if (disablePageBlock())
      return; 
    final SuffixMetaServiceInterface service = (SuffixMetaServiceInterface)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(SuffixMetaServiceInterface.class);
    suffixMetaServiceInterface.subscribe(new SuffixMetaServiceInterface.SuffixMetaListener() {
          public void onError(String param1String) {
            StringBuilder stringBuilder = new StringBuilder("get suffix meta error:");
            stringBuilder.append(param1String);
            AppBrandLogger.e("BlockPageManager", new Object[] { stringBuilder.toString() });
            service.unsubscribe(this);
            BlockPageMonitor.requestFail(param1String);
          }
          
          public void onSuccess(SuffixMetaEntity param1SuffixMetaEntity) {
            if (param1SuffixMetaEntity == null)
              return; 
            if (!param1SuffixMetaEntity.diskCache)
              service.unsubscribe(this); 
            try {
              JSONArray jSONArray = new JSONArray(param1SuffixMetaEntity.shieldPage);
              BlockPageManager.this.pushData(jSONArray);
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.e("BlockPageManager", new Object[] { jSONException });
              return;
            } 
          }
        });
  }
  
  public void handleErrorPage() {
    AppBrandLogger.i("BlockPageManager", new Object[] { "handle error page" });
    ((SuffixMetaServiceInterface)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(SuffixMetaServiceInterface.class)).removeLocalCache(SuffixMetaEntity.PROPERTY.shieldPage, true);
    if (AppbrandContext.getInst().getCurrentActivity() == null)
      return; 
    if (((PageRouter)this.mApp.getService(PageRouter.class)).getViewWindowRoot().getViewWindowCount() > 1)
      BlockPageMonitor.showErrorNotFirstPage("show error not first page"); 
  }
  
  public void handleHotLaunch() {
    if (disablePageBlock())
      return; 
    ((SuffixMetaServiceInterface)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(SuffixMetaServiceInterface.class)).getRemoteImmediately(new SuffixMetaServiceInterface.SuffixMetaListener() {
          public void onError(String param1String) {
            StringBuilder stringBuilder = new StringBuilder("get suffix meta error:");
            stringBuilder.append(param1String);
            AppBrandLogger.e("BlockPageManager", new Object[] { stringBuilder.toString() });
            BlockPageMonitor.requestFail(param1String);
          }
          
          public void onSuccess(SuffixMetaEntity param1SuffixMetaEntity) {
            try {
              JSONArray jSONArray = new JSONArray(param1SuffixMetaEntity.shieldPage);
              BlockPageManager.this.pushData(jSONArray);
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.e("BlockPageManager", new Object[] { jSONException });
              return;
            } 
          }
        });
  }
  
  public void pushData(JSONArray paramJSONArray) {
    if (paramJSONArray != null) {
      if (paramJSONArray.length() == 0)
        return; 
      try {
        return;
      } finally {
        paramJSONArray = null;
        AppBrandLogger.e("BlockPageManager", new Object[] { "push data error", paramJSONArray });
        StringBuilder stringBuilder = new StringBuilder("push data error:");
        stringBuilder.append(Log.getStackTraceString((Throwable)paramJSONArray));
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\badcase\BlockPageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */