package com.tt.miniapp.suffixmeta;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.bytedance.sandboxapp.b.a;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.service.suffixmeta.SuffixMetaEntity;
import com.tt.miniapp.service.suffixmeta.SuffixMetaParam;
import com.tt.miniapp.service.suffixmeta.SuffixMetaServiceInterface;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.option.q.d;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONObject;

public class SuffixMetaService implements SuffixMetaServiceInterface {
  private MiniAppContext mContextWrapper;
  
  private volatile Status mCurrentStatus = new Status();
  
  private final Object mLock = new Object();
  
  public volatile CopyOnWriteArrayList<SuffixMetaServiceInterface.SuffixMetaListener> mObservers = new CopyOnWriteArrayList<SuffixMetaServiceInterface.SuffixMetaListener>();
  
  private Object queryMap;
  
  public SuffixMetaService(MiniAppContext paramMiniAppContext) {
    this.mContextWrapper = paramMiniAppContext;
  }
  
  private String appendURL(String paramString1, String paramString2, String paramString3) {
    StringBuilder stringBuilder = new StringBuilder(AppbrandConstant.OpenApi.getInst().getSUFFIX_META());
    Iterator<Map.Entry> iterator = getQueryMap(paramString1, paramString2, paramString3).entrySet().iterator();
    boolean bool = true;
    while (iterator.hasNext()) {
      Map.Entry entry = iterator.next();
      if (bool) {
        stringBuilder.append("?");
        stringBuilder.append((String)entry.getKey());
        stringBuilder.append("=");
        stringBuilder.append((String)entry.getValue());
        bool = false;
        continue;
      } 
      stringBuilder.append("&");
      stringBuilder.append((String)entry.getKey());
      stringBuilder.append("=");
      stringBuilder.append((String)entry.getValue());
    } 
    return stringBuilder.toString();
  }
  
  private SuffixMetaParam getParamFromContext() {
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    return (initParamsEntity == null) ? null : ((this.mContextWrapper.getAppInfo() == null) ? null : new SuffixMetaParam((this.mContextWrapper.getAppInfo()).appId, initParamsEntity.getAppId(), initParamsEntity.getDeviceId()));
  }
  
  private Map<String, String> getQueryMap(String paramString1, String paramString2, String paramString3) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("aid", paramString2);
    hashMap.put("appid", paramString1);
    if (!TextUtils.isEmpty(paramString3))
      hashMap.put("device_id", paramString3); 
    hashMap.put("version", (this.mContextWrapper.getAppInfo()).versionType);
    hashMap.put("sdk_version", "1");
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null) {
      hashMap.put("version_code", initParamsEntity.getVersionCode());
      hashMap.put("bdp_version_code", initParamsEntity.getVersionCode());
      hashMap.put("bdp_device_id", d.a());
      hashMap.put("channel", initParamsEntity.getChannel());
      hashMap.put("device_platform", initParamsEntity.getDevicePlatform());
      hashMap.put("bdp_device_platform", initParamsEntity.getDevicePlatform());
      hashMap.put("os_version", initParamsEntity.getOsVersion());
      hashMap.put("tma_jssdk_version", BaseBundleManager.getInst().getSdkCurrentVersionStr(this.mContextWrapper.getApplicationContext()));
    } 
    return (Map)hashMap;
  }
  
  private void iteratorInNewThread(final SuffixMetaEntity entity, final String errorMsg) {
    Observable.create(new Action() {
          public void act() {
            if (entity != null) {
              Iterator<SuffixMetaServiceInterface.SuffixMetaListener> iterator1 = SuffixMetaService.this.mObservers.iterator();
              while (iterator1.hasNext())
                ((SuffixMetaServiceInterface.SuffixMetaListener)iterator1.next()).onSuccess(entity); 
              return;
            } 
            Iterator<SuffixMetaServiceInterface.SuffixMetaListener> iterator = SuffixMetaService.this.mObservers.iterator();
            while (iterator.hasNext())
              ((SuffixMetaServiceInterface.SuffixMetaListener)iterator.next()).onError(errorMsg); 
          }
        }).schudleOn(Schedulers.shortIO()).subscribeSimple();
  }
  
  public void callbackError(String paramString) {
    iteratorInNewThread(null, paramString);
  }
  
  public void callbackSuccess(SuffixMetaEntity paramSuffixMetaEntity) {
    iteratorInNewThread(paramSuffixMetaEntity, "");
  }
  
  public SuffixMetaResponse fetchLatest(SuffixMetaParam paramSuffixMetaParam) {
    try {
      StringBuilder stringBuilder;
      String str = NetManager.getInst().request(appendURL(paramSuffixMetaParam.getAppId(), paramSuffixMetaParam.getHostAppId(), paramSuffixMetaParam.getDeviceId())).a();
      if (TextUtils.isEmpty(str)) {
        AppBrandLogger.e("SuffixMetaService", new Object[] { "request suffixMeta data is empty" });
        return new SuffixMetaResponse("", null, "request suffixMeta data is empty");
      } 
      JSONObject jSONObject2 = new JSONObject(str);
      int i = jSONObject2.optInt("error");
      if (i != 0) {
        AppBrandLogger.e("SuffixMetaService", new Object[] { "server errorCode", Integer.valueOf(i) });
        stringBuilder = new StringBuilder("server errorCode");
        stringBuilder.append(i);
        return new SuffixMetaResponse("", null, stringBuilder.toString());
      } 
      JSONObject jSONObject1 = stringBuilder.optJSONObject("data");
      if (jSONObject1 == null) {
        AppBrandLogger.i("SuffixMetaService", new Object[] { "server data is null" });
        return new SuffixMetaResponse("", null, "server data is null");
      } 
      SuffixMetaEntity suffixMetaEntity = SuffixMetaParser.parse(jSONObject1);
      if (suffixMetaEntity == null)
        return new SuffixMetaResponse("", null, "parse SuffixMetaEntity error"); 
      suffixMetaEntity.diskCache = false;
      return new SuffixMetaResponse(jSONObject1.toString(), suffixMetaEntity);
    } catch (Exception exception) {
      AppBrandLogger.e("SuffixMetaService", new Object[] { exception });
      return new SuffixMetaResponse("", null, Log.getStackTraceString(exception));
    } 
  }
  
  public SuffixMetaResponse fetchLocalCache(SuffixMetaParam paramSuffixMetaParam) {
    String str = SuffixMetaStorage.getLocalCache((Context)AppbrandContext.getInst().getApplicationContext(), paramSuffixMetaParam.getAppId());
    if (TextUtils.isEmpty(str))
      return null; 
    SuffixMetaEntity suffixMetaEntity = SuffixMetaParser.parse(str);
    if (suffixMetaEntity == null)
      return null; 
    suffixMetaEntity.diskCache = true;
    return new SuffixMetaResponse("", suffixMetaEntity);
  }
  
  public SuffixMetaEntity get() {
    return get(false);
  }
  
  public SuffixMetaEntity get(boolean paramBoolean) {
    SuffixMetaEntity suffixMetaEntity2 = getOrNull(paramBoolean);
    SuffixMetaEntity suffixMetaEntity1 = suffixMetaEntity2;
    if (suffixMetaEntity2 == null)
      suffixMetaEntity1 = new SuffixMetaEntity(); 
    return suffixMetaEntity1;
  }
  
  public MiniAppContext getContext() {
    return this.mContextWrapper;
  }
  
  public SuffixMetaEntity getOrNull(boolean paramBoolean) {
    SuffixMetaEntity suffixMetaEntity2 = (getStatus()).data;
    SuffixMetaEntity suffixMetaEntity1 = suffixMetaEntity2;
    if (suffixMetaEntity2 == null) {
      suffixMetaEntity1 = suffixMetaEntity2;
      if (paramBoolean) {
        SuffixMetaParam suffixMetaParam = getParamFromContext();
        suffixMetaEntity1 = suffixMetaEntity2;
        if (suffixMetaParam != null) {
          SuffixMetaResponse suffixMetaResponse = fetchLocalCache(suffixMetaParam);
          suffixMetaEntity1 = suffixMetaEntity2;
          if (suffixMetaResponse != null)
            suffixMetaEntity1 = suffixMetaResponse.suffixMetaEntity; 
        } 
      } 
    } 
    return suffixMetaEntity1;
  }
  
  public void getRemoteImmediately(final SuffixMetaServiceInterface.SuffixMetaListener callback) {
    final String param;
    if (callback == null)
      return; 
    SuffixMetaParam suffixMetaParam = getParamFromContext();
    if (suffixMetaParam == null) {
      AppBrandLogger.e("SuffixMetaService", new Object[] { "request suffixMeta params error param is null" });
      callback.onError("request suffixMeta params error param is null");
      return;
    } 
    if (TextUtils.isEmpty(suffixMetaParam.getAppId()) || TextUtils.isEmpty(suffixMetaParam.getHostAppId())) {
      StringBuilder stringBuilder = new StringBuilder("request suffixMeta params error,appId:");
      stringBuilder.append(suffixMetaParam.getAppId());
      stringBuilder.append("hostAppId:");
      stringBuilder.append(suffixMetaParam.getHostAppId());
      str = stringBuilder.toString();
      AppBrandLogger.e("SuffixMetaService", new Object[] { str });
      callback.onError(str);
      return;
    } 
    Observable.create(new Action() {
          public void act() {
            try {
              SuffixMetaResponse suffixMetaResponse = SuffixMetaService.this.fetchLatest(param);
              if (suffixMetaResponse.suffixMetaEntity != null) {
                SuffixMetaService.this.setStatus(suffixMetaResponse.suffixMetaEntity, 1, "");
                callback.onSuccess(suffixMetaResponse.suffixMetaEntity);
                SuffixMetaStorage.saveSuffixMeta((Context)AppbrandContext.getInst().getApplicationContext(), param.getAppId(), suffixMetaResponse.originData);
                return;
              } 
              callback.onError(suffixMetaResponse.errorMsg);
              SuffixMetaMonitor.requestSuffixMetaFail(suffixMetaResponse.errorMsg);
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("SuffixMetaService", new Object[] { exception });
              String str = Log.getStackTraceString(exception);
              callback.onError(str);
              SuffixMetaMonitor.requestSuffixMetaFail(str);
              return;
            } 
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public Status getStatus() {
    synchronized (this.mLock) {
      return this.mCurrentStatus;
    } 
  }
  
  public void onDestroy() {
    if (this.mObservers != null && !this.mObservers.isEmpty())
      this.mObservers.clear(); 
  }
  
  public void removeLocalCache(final SuffixMetaEntity.PROPERTY propertyName, boolean paramBoolean) {
    final Context context = this.mContextWrapper.getApplicationContext();
    final AppInfoEntity appInfo = AppbrandApplicationImpl.getInst().getAppInfo();
    if (paramBoolean) {
      SuffixMetaStorage.removeProperty(context, appInfoEntity.appId, propertyName.getName());
      return;
    } 
    Observable.create(new Action() {
          public void act() {
            SuffixMetaStorage.removeProperty(context, appInfo.appId, propertyName.name());
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public void requestSuffixMeta() {
    final SuffixMetaParam param = getParamFromContext();
    if (suffixMetaParam == null) {
      AppBrandLogger.e("SuffixMetaService", new Object[] { "request suffixMeta params error param is null" });
      return;
    } 
    if (TextUtils.isEmpty(suffixMetaParam.getAppId()) || TextUtils.isEmpty(suffixMetaParam.getHostAppId())) {
      StringBuilder stringBuilder = new StringBuilder("request suffixMeta params error,appId:");
      stringBuilder.append(suffixMetaParam.getAppId());
      stringBuilder.append("hostAppId:");
      stringBuilder.append(suffixMetaParam.getHostAppId());
      AppBrandLogger.e("SuffixMetaService", new Object[] { stringBuilder.toString() });
      return;
    } 
    Observable.create(new Action() {
          public void act() {
            SuffixMetaResponse suffixMetaResponse = SuffixMetaService.this.fetchLocalCache(param);
            if (suffixMetaResponse != null) {
              SuffixMetaService.this.setStatus(suffixMetaResponse.suffixMetaEntity, 1, "");
              SuffixMetaService suffixMetaService = SuffixMetaService.this;
              suffixMetaService.callbackSuccess((suffixMetaService.getStatus()).data);
            } 
            suffixMetaResponse = SuffixMetaService.this.fetchLatest(param);
            if (suffixMetaResponse.suffixMetaEntity != null) {
              SuffixMetaService.this.setStatus(suffixMetaResponse.suffixMetaEntity, 1, "");
              SuffixMetaService suffixMetaService = SuffixMetaService.this;
              suffixMetaService.callbackSuccess((suffixMetaService.getStatus()).data);
              SuffixMetaStorage.saveSuffixMeta((Context)AppbrandContext.getInst().getApplicationContext(), param.getAppId(), suffixMetaResponse.originData);
            } else {
              if ((SuffixMetaService.this.getStatus()).status != 1) {
                SuffixMetaService.this.setStatus(null, 2, suffixMetaResponse.errorMsg);
                SuffixMetaService suffixMetaService = SuffixMetaService.this;
                suffixMetaService.callbackError((suffixMetaService.getStatus()).errorMsg);
              } 
              SuffixMetaMonitor.requestSuffixMetaFail(suffixMetaResponse.errorMsg);
            } 
            SuffixMetaStorage.removeOldVersionFile((Context)AppbrandContext.getInst().getApplicationContext(), param.getAppId());
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public void setStatus(SuffixMetaEntity paramSuffixMetaEntity, int paramInt, String paramString) {
    synchronized (this.mLock) {
      this.mCurrentStatus.data = paramSuffixMetaEntity;
      this.mCurrentStatus.status = paramInt;
      this.mCurrentStatus.errorMsg = paramString;
      return;
    } 
  }
  
  public void subscribe(SuffixMetaServiceInterface.SuffixMetaListener paramSuffixMetaListener) {
    synchronized (this.mLock) {
      Status status = getStatus();
      if (status.status == 2)
        paramSuffixMetaListener.onError(status.errorMsg); 
      if (status.data != null)
        paramSuffixMetaListener.onSuccess(status.data); 
      this.mObservers.add(paramSuffixMetaListener);
      return;
    } 
  }
  
  public void unsubscribe(SuffixMetaServiceInterface.SuffixMetaListener paramSuffixMetaListener) {
    this.mObservers.remove(paramSuffixMetaListener);
  }
  
  static class Status {
    SuffixMetaEntity data;
    
    String errorMsg;
    
    int status;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\suffixmeta\SuffixMetaService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */