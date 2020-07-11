package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import d.f.b.g;
import d.f.b.l;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONObject;

public abstract class BaseBatchMetaRequester {
  public static final Companion Companion = new Companion(null);
  
  private final Context mContext;
  
  private final RequestType mRequestType;
  
  public BaseBatchMetaRequester(Context paramContext, RequestType paramRequestType) {
    this.mContext = paramContext;
    this.mRequestType = paramRequestType;
  }
  
  public final List<RequestResultInfo> adaptResult(BatchMetaRequestResult paramBatchMetaRequestResult) {
    ArrayList<RequestResultInfo> arrayList = new ArrayList();
    for (String str : paramBatchMetaRequestResult.originDataList) {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("data", new JSONObject(str));
      jSONObject.put("error", 0);
      RequestResultInfo requestResultInfo = new RequestResultInfo();
      if (AppInfoHelper.parseAppInfo(jSONObject.toString(), paramBatchMetaRequestResult.encryKey, paramBatchMetaRequestResult.encryIV, paramBatchMetaRequestResult.url, this.mRequestType, requestResultInfo) && requestResultInfo.appInfo != null)
        requestResultInfo.appInfo.getFromType = 0; 
      arrayList.add(requestResultInfo);
    } 
    return arrayList;
  }
  
  protected final Context getMContext() {
    return this.mContext;
  }
  
  protected final RequestType getMRequestType() {
    return this.mRequestType;
  }
  
  protected BatchMetaRequestResult onRequestSync(Collection<String> paramCollection) {
    l.b(paramCollection, "appIdList");
    AppBrandLogger.i("BaseBatchMetaRequester", new Object[] { this.mRequestType, "onRequestSync" });
    BatchMetaRequestResult batchMetaRequestResult = AppInfoHelper.requestBatchMeta(this.mContext, paramCollection, this.mRequestType);
    l.a(batchMetaRequestResult, "AppInfoHelper.requestBat… appIdList, mRequestType)");
    return batchMetaRequestResult;
  }
  
  public abstract void onSaveMetaList(List<? extends RequestResultInfo> paramList);
  
  public final void request(Collection<String> paramCollection, Scheduler paramScheduler, AppInfoBatchRequestListener paramAppInfoBatchRequestListener) {
    l.b(paramScheduler, "scheduler");
    l.b(paramAppInfoBatchRequestListener, "listener");
    AppBrandLogger.i("BaseBatchMetaRequester", new Object[] { this.mRequestType, "request" });
    ThreadUtil.runOnWorkThread(new BaseBatchMetaRequester$request$1(paramCollection, paramAppInfoBatchRequestListener), paramScheduler);
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class BaseBatchMetaRequester$request$1 implements Action {
    BaseBatchMetaRequester$request$1(Collection param1Collection, AppInfoBatchRequestListener param1AppInfoBatchRequestListener) {}
    
    public final void act() {
      Collection collection = this.$appIdList;
      if (collection == null || collection.isEmpty()) {
        AppInfoBatchRequestListener appInfoBatchRequestListener = this.$listener;
        StringBuilder stringBuilder = new StringBuilder("appIdList is null or empty: ");
        stringBuilder.append(this.$appIdList);
        appInfoBatchRequestListener.requestBatchAppInfoFail(stringBuilder.toString());
        return;
      } 
      try {
        String str;
        BatchMetaRequestResult batchMetaRequestResult = BaseBatchMetaRequester.this.onRequestSync(this.$appIdList);
        if (batchMetaRequestResult.originDataList == null || batchMetaRequestResult.originDataList.isEmpty()) {
          if (!TextUtils.isEmpty(batchMetaRequestResult.errorMsg)) {
            AppInfoBatchRequestListener appInfoBatchRequestListener1 = this.$listener;
            str = batchMetaRequestResult.errorMsg;
            l.a(str, "batchResult.errorMsg");
            appInfoBatchRequestListener1.requestBatchAppInfoFail(str);
            return;
          } 
          AppInfoBatchRequestListener appInfoBatchRequestListener = this.$listener;
          StringBuilder stringBuilder = new StringBuilder("requestSync return null or empty: ");
          stringBuilder.append(((BatchMetaRequestResult)str).originDataList);
          appInfoBatchRequestListener.requestBatchAppInfoFail(stringBuilder.toString());
          return;
        } 
        List<RequestResultInfo> list = BaseBatchMetaRequester.this.adaptResult((BatchMetaRequestResult)str);
        BaseBatchMetaRequester.this.onSaveMetaList(list);
        if ((list.isEmpty() ^ true) != 0) {
          this.$listener.requestBatchAppInfoSuccess(list);
          return;
        } 
        this.$listener.requestBatchAppInfoFail("adaptResult return empty.");
        return;
      } catch (Exception exception) {
        AppInfoBatchRequestListener appInfoBatchRequestListener = this.$listener;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(exception.getMessage());
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(exception));
        appInfoBatchRequestListener.requestBatchAppInfoFail(stringBuilder.toString());
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\BaseBatchMetaRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */