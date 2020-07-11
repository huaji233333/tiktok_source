package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.errorcode.ErrorCodeUtil;
import com.tt.miniapp.errorcode.Flow;
import com.tt.miniapp.errorcode.NetErrorUtil;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.TimeMeter;
import d.a.m;
import d.f.b.g;
import d.f.b.l;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseMetaRequester {
  public static final Companion Companion = new Companion(null);
  
  private final Context mContext;
  
  private final RequestType mRequestType;
  
  public BaseMetaRequester(Context paramContext, RequestType paramRequestType) {
    this.mContext = paramContext;
    this.mRequestType = paramRequestType;
  }
  
  public final boolean adaptResult(AppInfoRequestResult paramAppInfoRequestResult, RequestResultInfo paramRequestResultInfo) {
    paramRequestResultInfo.fromProcess = paramAppInfoRequestResult.fromProcess;
    paramRequestResultInfo.encryIV = paramAppInfoRequestResult.encryIV;
    paramRequestResultInfo.encryKey = paramAppInfoRequestResult.encryKey;
    ArrayList<AppInfoRequestResult.RequestMetaRecord> arrayList = paramAppInfoRequestResult.requestRecordList;
    l.a(arrayList, "result.requestRecordList");
    AppInfoRequestResult.RequestMetaRecord requestMetaRecord = (AppInfoRequestResult.RequestMetaRecord)m.g(arrayList);
    String str = requestMetaRecord.url;
    paramRequestResultInfo.url = str;
    if (!TextUtils.isEmpty(requestMetaRecord.data)) {
      if (AppInfoHelper.parseAppInfo(requestMetaRecord.data, paramAppInfoRequestResult.encryKey, paramAppInfoRequestResult.encryIV, str, this.mRequestType, paramRequestResultInfo) && paramRequestResultInfo.appInfo != null) {
        paramRequestResultInfo.appInfo.getFromType = 0;
        return true;
      } 
    } else if (TextUtils.isEmpty(requestMetaRecord.throwable) && TextUtils.isEmpty(requestMetaRecord.message) && NetErrorUtil.isSuccessful(requestMetaRecord.code)) {
      paramRequestResultInfo.errorCode = ErrorCode.META.NULL.getCode();
    } else {
      int i = requestMetaRecord.code;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(requestMetaRecord.message);
      stringBuilder.append(requestMetaRecord.throwable);
      String str1 = NetErrorUtil.getHttpCode(i, stringBuilder.toString());
      if (!TextUtils.equals(str1, ErrorCode.NETWORK.SUCCESS.getCodeStr())) {
        paramRequestResultInfo.errorCode = ErrorCodeUtil.getNetCode(Flow.Meta, str1);
      } else {
        paramRequestResultInfo.errorCode = ErrorCode.META.NULL.getCode();
      } 
      if (!TextUtils.isEmpty(requestMetaRecord.message)) {
        str1 = paramRequestResultInfo.errorMsg;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str1);
        stringBuilder1.append(requestMetaRecord.message);
        stringBuilder1.append("\n");
        paramRequestResultInfo.errorMsg = stringBuilder1.toString();
      } 
      if (!TextUtils.isEmpty(requestMetaRecord.throwable)) {
        str1 = paramRequestResultInfo.errorMsg;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str1);
        stringBuilder1.append(requestMetaRecord.throwable);
        stringBuilder1.append("\n");
        paramRequestResultInfo.errorMsg = stringBuilder1.toString();
      } 
    } 
    if (TextUtils.isEmpty(paramRequestResultInfo.errorMsg)) {
      StringBuilder stringBuilder = new StringBuilder();
      if (TextUtils.isEmpty(requestMetaRecord.data)) {
        stringBuilder.append("request result is empty");
      } else {
        stringBuilder.append("parse app info fail");
      } 
      stringBuilder.append(" & requestType = ");
      stringBuilder.append(this.mRequestType);
      stringBuilder.append(" & url = ");
      stringBuilder.append(str);
      stringBuilder.append(" & value = ");
      stringBuilder.append(requestMetaRecord.data);
      stringBuilder.append(" & message = ");
      stringBuilder.append(requestMetaRecord.message);
      stringBuilder.append(" & code = ");
      stringBuilder.append(requestMetaRecord.code);
      stringBuilder.append(" & isNetAvailable = ");
      stringBuilder.append(NetUtil.isNetworkAvailable(this.mContext));
      stringBuilder.append(" & networkType = ");
      stringBuilder.append(NetUtil.getNewNetType(this.mContext));
      stringBuilder.append(" & errStack = ");
      stringBuilder.append(requestMetaRecord.throwable);
      paramRequestResultInfo.errorMsg = stringBuilder.toString();
    } 
    return false;
  }
  
  protected final Context getMContext() {
    return this.mContext;
  }
  
  protected final RequestType getMRequestType() {
    return this.mRequestType;
  }
  
  protected abstract boolean onFetchLocalMetaSync(Context paramContext, AppInfoEntity paramAppInfoEntity, RequestResultInfo paramRequestResultInfo);
  
  protected void onRequestSuccess(RequestResultInfo paramRequestResultInfo, AppInfoRequestListener paramAppInfoRequestListener) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'requestResultInfo'
    //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 'listener'
    //   9: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_1
    //   13: getfield appInfo : Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   16: astore #6
    //   18: ldc 'BaseMetaRequester'
    //   20: iconst_3
    //   21: anewarray java/lang/Object
    //   24: dup
    //   25: iconst_0
    //   26: aload_0
    //   27: getfield mRequestType : Lcom/tt/miniapp/launchcache/RequestType;
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: ldc 'appmeta.domains '
    //   35: aastore
    //   36: dup
    //   37: iconst_2
    //   38: aload #6
    //   40: getfield domains : Ljava/lang/String;
    //   43: aastore
    //   44: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   47: aload #6
    //   49: getfield state : I
    //   52: iconst_2
    //   53: if_icmpne -> 73
    //   56: aload #6
    //   58: ldc 'appInfo'
    //   60: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   63: aload_2
    //   64: aload #6
    //   66: iconst_1
    //   67: invokeinterface onAppInfoInvalid : (Lcom/tt/miniapphost/entity/AppInfoEntity;I)V
    //   72: return
    //   73: aload #6
    //   75: getfield versionState : I
    //   78: iconst_1
    //   79: if_icmpne -> 99
    //   82: aload #6
    //   84: ldc 'appInfo'
    //   86: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   89: aload_2
    //   90: aload #6
    //   92: iconst_3
    //   93: invokeinterface onAppInfoInvalid : (Lcom/tt/miniapphost/entity/AppInfoEntity;I)V
    //   98: return
    //   99: aload #6
    //   101: getfield versionState : I
    //   104: iconst_2
    //   105: if_icmpne -> 125
    //   108: aload #6
    //   110: ldc 'appInfo'
    //   112: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   115: aload_2
    //   116: aload #6
    //   118: iconst_4
    //   119: invokeinterface onAppInfoInvalid : (Lcom/tt/miniapphost/entity/AppInfoEntity;I)V
    //   124: return
    //   125: aload #6
    //   127: getfield versionState : I
    //   130: iconst_4
    //   131: if_icmpne -> 151
    //   134: aload #6
    //   136: ldc 'appInfo'
    //   138: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   141: aload_2
    //   142: aload #6
    //   144: iconst_5
    //   145: invokeinterface onAppInfoInvalid : (Lcom/tt/miniapphost/entity/AppInfoEntity;I)V
    //   150: return
    //   151: aload #6
    //   153: getfield minJssdk : Ljava/lang/String;
    //   156: checkcast java/lang/CharSequence
    //   159: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   162: ifne -> 404
    //   165: aload #6
    //   167: getfield minJssdk : Ljava/lang/String;
    //   170: astore_1
    //   171: aload_1
    //   172: ldc_w 'appInfo.minJssdk'
    //   175: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   178: aload_1
    //   179: checkcast java/lang/CharSequence
    //   182: astore_1
    //   183: new d/m/l
    //   186: dup
    //   187: ldc_w '\.'
    //   190: invokespecial <init> : (Ljava/lang/String;)V
    //   193: aload_1
    //   194: iconst_0
    //   195: invokevirtual split : (Ljava/lang/CharSequence;I)Ljava/util/List;
    //   198: astore_1
    //   199: aload_1
    //   200: invokeinterface isEmpty : ()Z
    //   205: ifne -> 284
    //   208: aload_1
    //   209: aload_1
    //   210: invokeinterface size : ()I
    //   215: invokeinterface listIterator : (I)Ljava/util/ListIterator;
    //   220: astore #7
    //   222: aload #7
    //   224: invokeinterface hasPrevious : ()Z
    //   229: ifeq -> 284
    //   232: aload #7
    //   234: invokeinterface previous : ()Ljava/lang/Object;
    //   239: checkcast java/lang/String
    //   242: checkcast java/lang/CharSequence
    //   245: invokeinterface length : ()I
    //   250: ifne -> 258
    //   253: iconst_1
    //   254: istore_3
    //   255: goto -> 260
    //   258: iconst_0
    //   259: istore_3
    //   260: iload_3
    //   261: ifne -> 222
    //   264: aload_1
    //   265: checkcast java/lang/Iterable
    //   268: aload #7
    //   270: invokeinterface nextIndex : ()I
    //   275: iconst_1
    //   276: iadd
    //   277: invokestatic b : (Ljava/lang/Iterable;I)Ljava/util/List;
    //   280: astore_1
    //   281: goto -> 288
    //   284: invokestatic a : ()Ljava/util/List;
    //   287: astore_1
    //   288: aload_1
    //   289: checkcast java/util/Collection
    //   292: iconst_0
    //   293: anewarray java/lang/String
    //   296: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   301: astore_1
    //   302: aload_1
    //   303: ifnull -> 393
    //   306: aload_1
    //   307: checkcast [Ljava/lang/String;
    //   310: astore_1
    //   311: aload_1
    //   312: arraylength
    //   313: iconst_3
    //   314: if_icmpne -> 404
    //   317: aload_1
    //   318: invokestatic getIntFromStr : ([Ljava/lang/String;)I
    //   321: i2l
    //   322: lstore #4
    //   324: ldc 'BaseMetaRequester'
    //   326: iconst_3
    //   327: anewarray java/lang/Object
    //   330: dup
    //   331: iconst_0
    //   332: aload_0
    //   333: getfield mRequestType : Lcom/tt/miniapp/launchcache/RequestType;
    //   336: aastore
    //   337: dup
    //   338: iconst_1
    //   339: ldc_w 'jssdk version = '
    //   342: aastore
    //   343: dup
    //   344: iconst_2
    //   345: lload #4
    //   347: invokestatic valueOf : (J)Ljava/lang/Long;
    //   350: aastore
    //   351: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   354: invokestatic getInst : ()Lcom/tt/miniapp/manager/basebundle/BaseBundleManager;
    //   357: aload_0
    //   358: getfield mContext : Landroid/content/Context;
    //   361: invokevirtual getSdkCurrentVersionStr : (Landroid/content/Context;)Ljava/lang/String;
    //   364: invokestatic convertFourToThreeVersion : (Ljava/lang/String;)Ljava/lang/String;
    //   367: invokestatic convertVersionStrToCode : (Ljava/lang/String;)J
    //   370: lload #4
    //   372: lcmp
    //   373: ifge -> 404
    //   376: aload #6
    //   378: ldc 'appInfo'
    //   380: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   383: aload_2
    //   384: aload #6
    //   386: iconst_2
    //   387: invokeinterface onAppInfoInvalid : (Lcom/tt/miniapphost/entity/AppInfoEntity;I)V
    //   392: return
    //   393: new d/u
    //   396: dup
    //   397: ldc_w 'null cannot be cast to non-null type kotlin.Array<T>'
    //   400: invokespecial <init> : (Ljava/lang/String;)V
    //   403: athrow
    //   404: invokestatic getInstance : ()Lcom/tt/miniapp/settings/data/SettingsManager;
    //   407: aload #6
    //   409: getfield needUpdateSettings : I
    //   412: invokevirtual updateNeedUpdateSettings : (I)V
    //   415: aload #6
    //   417: ldc 'appInfo'
    //   419: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   422: aload_2
    //   423: aload #6
    //   425: invokeinterface requestAppInfoSuccess : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   430: return
  }
  
  protected abstract AppInfoRequestResult onRequestSync(AppInfoEntity paramAppInfoEntity);
  
  protected abstract void onSaveMetaData(RequestResultInfo paramRequestResultInfo);
  
  public final void request(AppInfoEntity paramAppInfoEntity, Scheduler paramScheduler, AppInfoRequestListener paramAppInfoRequestListener) {
    l.b(paramScheduler, "scheduler");
    l.b(paramAppInfoRequestListener, "listener");
    ThreadUtil.runOnWorkThread(new BaseMetaRequester$request$1(paramAppInfoEntity, paramAppInfoRequestListener, TimeMeter.newAndStart()), paramScheduler);
  }
  
  public final void requestAppInfoOnError(Throwable paramThrowable, RequestResultInfo paramRequestResultInfo, AppInfoEntity paramAppInfoEntity, TimeMeter paramTimeMeter, AppInfoRequestListener paramAppInfoRequestListener) {
    AppBrandLogger.e("BaseMetaRequester", new Object[] { this.mRequestType, "error msg ", paramThrowable });
    String str = Log.getStackTraceString(paramThrowable);
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errMsg", str);
      jSONObject.put("isNetworkAvailable", NetUtil.isNetworkAvailable(this.mContext));
    } catch (JSONException jSONException) {
      AppBrandLogger.e("BaseMetaRequester", new Object[] { this.mRequestType, jSONException });
    } 
    InnerEventHelper.mpMetaRequestResult(paramAppInfoEntity, this.mRequestType.toString(), AppInfoHelper.getUrlHost(paramRequestResultInfo.url), "fail", paramAppInfoEntity.launchFrom, TimeMeter.stop(paramTimeMeter), paramRequestResultInfo.fromProcess, str);
    AppBrandMonitor.statusRate(AppInfoHelper.getErrorMpServiceName(this.mRequestType), 1014, jSONObject);
    paramAppInfoRequestListener.requestAppInfoFail(paramRequestResultInfo.errorCode, str);
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class BaseMetaRequester$request$1 implements Action {
    BaseMetaRequester$request$1(AppInfoEntity param1AppInfoEntity, AppInfoRequestListener param1AppInfoRequestListener, TimeMeter param1TimeMeter) {}
    
    public final void act() {
      AppInfoEntity appInfoEntity = this.$appInfo;
      if (appInfoEntity == null) {
        this.$listener.requestAppInfoFail(ErrorCode.META.NULL.getCode(), "oldAppInfo is null");
        return;
      } 
      if (TextUtils.isEmpty(appInfoEntity.versionType))
        this.$appInfo.versionType = "current"; 
      RequestResultInfo requestResultInfo = new RequestResultInfo();
      try {
        if (!BaseMetaRequester.this.onFetchLocalMetaSync(BaseMetaRequester.this.getMContext(), this.$appInfo, requestResultInfo)) {
          InnerEventHelper.mpMetaRequestStart(this.$appInfo, BaseMetaRequester.this.getMRequestType().toString(), this.$appInfo.launchFrom);
          AppInfoRequestResult appInfoRequestResult = BaseMetaRequester.this.onRequestSync(this.$appInfo);
          if (appInfoRequestResult == null) {
            this.$listener.requestAppInfoFail(ErrorCode.META.NULL.getCode(), "requestSync return null");
            return;
          } 
          if (BaseMetaRequester.this.adaptResult(appInfoRequestResult, requestResultInfo)) {
            requestResultInfo.appInfo.versionType = this.$appInfo.versionType;
            requestResultInfo.appInfo.bdpLog = this.$appInfo.bdpLog;
            requestResultInfo.appInfo.location = this.$appInfo.location;
            requestResultInfo.appInfo.bizLocation = this.$appInfo.bizLocation;
            BaseMetaRequester.this.onSaveMetaData(requestResultInfo);
            InnerEventHelper.mpMetaRequestResult(this.$appInfo, BaseMetaRequester.this.getMRequestType().toString(), AppInfoHelper.getUrlHost(requestResultInfo.url), "success", this.$appInfo.launchFrom, TimeMeter.stop(this.$beginRequestMetaTime), requestResultInfo.fromProcess, "");
          } 
        } 
        if (TextUtils.isEmpty(requestResultInfo.errorCode) && requestResultInfo.appInfo != null) {
          BaseMetaRequester.this.onRequestSuccess(requestResultInfo, this.$listener);
          return;
        } 
        InnerEventHelper.mpMetaRequestResult(this.$appInfo, BaseMetaRequester.this.getMRequestType().toString(), AppInfoHelper.getUrlHost(requestResultInfo.url), "fail", this.$appInfo.launchFrom, TimeMeter.stop(this.$beginRequestMetaTime), requestResultInfo.fromProcess, requestResultInfo.errorMsg);
        this.$listener.requestAppInfoFail(requestResultInfo.errorCode, requestResultInfo.errorMsg);
        return;
      } catch (Exception exception) {
        BaseMetaRequester baseMetaRequester = BaseMetaRequester.this;
        Throwable throwable = exception;
        AppInfoEntity appInfoEntity1 = this.$appInfo;
        TimeMeter timeMeter = this.$beginRequestMetaTime;
        l.a(timeMeter, "beginRequestMetaTime");
        baseMetaRequester.requestAppInfoOnError(throwable, requestResultInfo, appInfoEntity1, timeMeter, this.$listener);
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\BaseMetaRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */