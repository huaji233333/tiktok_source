package com.tt.miniapp.share;

import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.q.d;
import com.tt.option.q.i;
import com.tt.option.q.j;
import com.tt.option.w.h;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareRequestHelper {
  public static void cleanUselessShare(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    final i tmaRequest = new i(AppbrandConstant.OpenApi.getInst().getCLEAN_SHARE_MESSAGE(), "POST");
    initCommonParams(i);
    i.a("share_token", paramString);
    Observable.create(new Action() {
          public final void act() {
            j j = HostDependManager.getInst().doPostBody(tmaRequest);
            if (TextUtils.isEmpty(j.a())) {
              StringBuilder stringBuilder = new StringBuilder("cleanUselessShare fail,response data ");
              stringBuilder.append(j.a());
              AppBrandLogger.d("ShareRequestHelper", new Object[] { stringBuilder.toString() });
              return;
            } 
            try {
              String str = (new JSONObject(j.a())).optJSONObject("data").optString("status");
              if (TextUtils.equals("success", str)) {
                AppBrandLogger.d("ShareRequestHelper", new Object[] { "cleanUselessShare success" });
                return;
              } 
              StringBuilder stringBuilder = new StringBuilder("cleanUselessShare fail, ");
              stringBuilder.append(str);
              AppBrandLogger.d("ShareRequestHelper", new Object[] { stringBuilder.toString() });
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.d("ShareRequestHelper", new Object[] { "", jSONException });
              return;
            } 
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public static void getDefaultShareInfo(final h shareInfoModel, final OnShareRequestListener listener) {
    if (shareInfoModel == null) {
      if (listener != null)
        listener.onFail("initial shareInfoModel is null"); 
      return;
    } 
    Observable.create(new Action() {
          public final void act() {
            try {
              j j = HostDependManager.getInst().doPostBody(tmaRequest);
              if (TextUtils.isEmpty(j.a())) {
                if (listener != null) {
                  listener.onFail("response is empty");
                  return;
                } 
              } else {
                ShareResp shareResp = ShareResp.newDefaultShareResp(j.a());
                if (shareResp.errNo != 0) {
                  if (listener != null) {
                    listener.onFail("errNo != 0");
                    return;
                  } 
                } else {
                  boolean bool;
                  ShareRequestHelper.updateShareInfoModel(shareInfoModel, shareResp);
                  shareInfoModel.channel = "token";
                  if (shareInfoModel.appInfo == null)
                    shareInfoModel.appInfo = AppbrandApplication.getInst().getAppInfo(); 
                  String str = "portrait";
                  if (AppbrandApplicationImpl.getInst().getAppConfig() != null)
                    str = (AppbrandApplicationImpl.getInst().getAppConfig()).screenOrientation; 
                  h h1 = shareInfoModel;
                  if (TextUtils.equals(str, "landscape")) {
                    bool = true;
                  } else {
                    bool = false;
                  } 
                  h1.orientation = bool;
                  if (listener != null)
                    listener.onSuccess(shareInfoModel); 
                  return;
                } 
                return;
              } 
            } catch (Exception exception) {
              ShareRequestHelper.OnShareRequestListener onShareRequestListener = listener;
              if (onShareRequestListener != null)
                onShareRequestListener.onException(exception); 
              AppBrandLogger.eWithThrowable("ShareRequestHelper", "", exception);
              return;
            } 
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  private static i getDefaultShareInfoRequest(h paramh) {
    i i = new i(AppbrandConstant.OpenApi.getInst().getSHARE_MESSAGE_DEFAULT(), "POST");
    initCommonParams(i);
    i.a("title", paramh.title);
    i.a("description", paramh.desc);
    if (!TextUtils.isEmpty(paramh.imageUrl))
      i.a("uri", paramh.imageUrl); 
    i.a("query", paramh.queryString);
    i.a("channel", paramh.channel);
    i.a("template_id", paramh.templateId);
    i.j = 6000L;
    i.l = 6000L;
    i.k = 6000L;
    return i;
  }
  
  private static String getHostId() {
    String str;
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null) {
      str = initParamsEntity.getAppId();
    } else {
      str = "";
    } 
    if (TextUtils.isEmpty(str)) {
      AppBrandLogger.e("ShareRequestHelper", new Object[] { "host id is empty" });
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("errMsg", "host id is empty");
      } catch (JSONException jSONException) {
        AppBrandLogger.stacktrace(6, "ShareRequestHelper", jSONException.getStackTrace());
      } 
      AppBrandMonitor.statusRate("mp_start_error", 2003, jSONObject);
    } 
    return str;
  }
  
  public static void getNormalShareInfoAsync(final h shareInfoModel, long paramLong, final OnShareRequestListener listener) {
    if (shareInfoModel == null) {
      if (listener != null)
        listener.onFail("initial shareInfoModel is null"); 
      return;
    } 
    Observable.create(new Action() {
          public final void act() {
            try {
              j j = HostDependManager.getInst().doPostBody(tmaRequest);
              if (TextUtils.isEmpty(j.a())) {
                if (listener != null) {
                  listener.onFail("response is empty");
                  return;
                } 
              } else {
                ShareResp shareResp = ShareResp.newShareResp(j.a());
                if (shareResp.errNo != 0) {
                  if (listener != null) {
                    listener.onFail("errNo != 0");
                    return;
                  } 
                } else {
                  ShareRequestHelper.updateShareInfoModel(shareInfoModel, shareResp);
                  if (listener != null)
                    listener.onSuccess(shareInfoModel); 
                  return;
                } 
                return;
              } 
            } catch (Exception exception) {
              ShareRequestHelper.OnShareRequestListener onShareRequestListener = listener;
              if (onShareRequestListener != null)
                onShareRequestListener.onException(exception); 
              AppBrandLogger.eWithThrowable("ShareRequestHelper", "", exception);
              return;
            } 
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public static h getNormalShareInfoSync(h paramh, long paramLong) {
    if (paramh == null)
      return null; 
    i i = getShareInfoTmaRequest(paramh, paramLong);
    j j = HostDependManager.getInst().doPostBody(i);
    if (TextUtils.isEmpty(j.a()))
      return null; 
    ShareResp shareResp = ShareResp.newShareResp(j.a());
    if (shareResp.errNo != 0)
      return null; 
    updateShareInfoModel(paramh, shareResp);
    return paramh;
  }
  
  private static i getShareInfoTmaRequest(h paramh, long paramLong) {
    i i = new i(AppbrandConstant.OpenApi.getInst().getSHARE_MESSAGE(), "POST");
    initCommonParams(i);
    i.a("title", paramh.title);
    i.a("description", paramh.desc);
    if (!TextUtils.isEmpty(paramh.imageUrl))
      i.a("uri", paramh.imageUrl); 
    i.a("query", paramh.queryString);
    i.a("share_channel", paramh.shareType);
    i.a("channel", paramh.channel);
    i.a("template_id", paramh.templateId);
    if (!TextUtils.isEmpty(paramh.linkTitle)) {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("link_title", paramh.linkTitle);
        i.a("share_extra", jSONObject);
      } catch (JSONException jSONException) {
        AppBrandLogger.e("ShareRequestHelper", new Object[] { "getShareInfoTmaRequest", jSONException });
      } 
    } 
    i.j = paramLong;
    i.l = paramLong;
    i.k = paramLong;
    return i;
  }
  
  private static i getUploadTmaRequest(String paramString) {
    // Byte code:
    //   0: new com/tt/option/q/i
    //   3: dup
    //   4: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandConstant$OpenApi;
    //   7: invokevirtual getSHARE_UPLOAD_IMG : ()Ljava/lang/String;
    //   10: ldc 'POST'
    //   12: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   15: astore_2
    //   16: aload_2
    //   17: invokestatic initCommonParams : (Lcom/tt/option/q/i;)V
    //   20: aload_0
    //   21: ldc 'http'
    //   23: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   26: ifeq -> 40
    //   29: aload_2
    //   30: ldc_w 'image_url'
    //   33: aload_0
    //   34: invokevirtual a : (Ljava/lang/String;Ljava/lang/Object;)V
    //   37: goto -> 125
    //   40: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   43: aload_0
    //   44: invokevirtual getRealFilePath : (Ljava/lang/String;)Ljava/lang/String;
    //   47: astore_3
    //   48: new java/io/File
    //   51: dup
    //   52: aload_3
    //   53: invokespecial <init> : (Ljava/lang/String;)V
    //   56: astore_0
    //   57: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   60: aload_0
    //   61: invokevirtual canRead : (Ljava/io/File;)Z
    //   64: ifeq -> 148
    //   67: aload_0
    //   68: invokevirtual exists : ()Z
    //   71: ifeq -> 148
    //   74: new java/io/File
    //   77: dup
    //   78: aload_3
    //   79: invokestatic getCompressImg : (Ljava/lang/String;)Ljava/lang/String;
    //   82: invokespecial <init> : (Ljava/lang/String;)V
    //   85: astore_1
    //   86: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   89: aload_1
    //   90: invokevirtual canRead : (Ljava/io/File;)Z
    //   93: ifeq -> 105
    //   96: aload_1
    //   97: astore_0
    //   98: aload_1
    //   99: invokevirtual exists : ()Z
    //   102: ifne -> 114
    //   105: new java/io/File
    //   108: dup
    //   109: aload_3
    //   110: invokespecial <init> : (Ljava/lang/String;)V
    //   113: astore_0
    //   114: aload_2
    //   115: ldc_w 'image_file'
    //   118: aload_0
    //   119: ldc_w 'image/*'
    //   122: invokevirtual a : (Ljava/lang/String;Ljava/io/File;Ljava/lang/String;)V
    //   125: aload_2
    //   126: ldc2_w 6000
    //   129: putfield j : J
    //   132: aload_2
    //   133: ldc2_w 6000
    //   136: putfield l : J
    //   139: aload_2
    //   140: ldc2_w 6000
    //   143: putfield k : J
    //   146: aload_2
    //   147: areturn
    //   148: aconst_null
    //   149: areturn
  }
  
  private static void initCommonParams(i parami) {
    parami.a("host_id", Integer.valueOf(Integer.parseInt(getHostId())));
    parami.a("app_id", (AppbrandApplication.getInst().getAppInfo()).appId);
    parami.a("os", "android");
    parami.a("did", d.a());
    String str = InnerHostProcessBridge.getPlatformSession((AppbrandApplication.getInst().getAppInfo()).appId);
    if (!TextUtils.isEmpty(str))
      parami.a("session", str); 
  }
  
  public static void updateShareInfoModel(h paramh, ShareResp paramShareResp) {
    if (paramh != null && paramShareResp != null) {
      if (paramShareResp.data == null)
        return; 
      ShareResp.Data data = paramShareResp.data;
      if (!TextUtils.isEmpty(data.title))
        paramh.title = data.title; 
      if (!TextUtils.isEmpty(data.desc))
        paramh.desc = data.desc; 
      if (!TextUtils.isEmpty(data.token))
        paramh.token = data.token; 
      if (!TextUtils.isEmpty(data.imageUrl))
        paramh.imageUrl = data.imageUrl; 
      if (!TextUtils.isEmpty(data.miniImageUrl))
        paramh.miniImageUrl = data.miniImageUrl; 
      if (!TextUtils.isEmpty(data.ugUrl))
        paramh.ugUrl = data.ugUrl; 
      if (!TextUtils.isEmpty(data.shareExtra)) {
        if (TextUtils.isEmpty(paramh.extra)) {
          paramh.extra = data.shareExtra;
          return;
        } 
        try {
          JSONObject jSONObject1 = new JSONObject(data.shareExtra);
          JSONObject jSONObject2 = new JSONObject(paramh.extra);
          int i = jSONObject1.optInt("platform_share_type");
          String str = jSONObject1.optString("link_title");
          long l = jSONObject1.optLong("share_id");
          jSONObject2.put("platform_share_type", i);
          jSONObject2.put("link_title", str);
          jSONObject2.put("share_id", l);
          paramh.extra = jSONObject2.toString();
          return;
        } catch (JSONException jSONException) {
          AppBrandLogger.eWithThrowable("ShareRequestHelper", "", (Throwable)jSONException);
        } 
      } 
    } 
  }
  
  public static void uploadShareImgAsync(final h shareInfoModel, final int retryCount, final OnShareRequestListener listener) {
    if (shareInfoModel == null) {
      if (listener != null)
        listener.onFail("initial shareInfoModel is null"); 
      return;
    } 
    String str = shareInfoModel.imageUrl;
    if (TextUtils.isEmpty(str)) {
      if (listener != null)
        listener.onFail("origin image url is empty"); 
      return;
    } 
    final i tmaRequest = getUploadTmaRequest(str);
    if (i == null) {
      if (listener != null)
        listener.onFail("create request return null"); 
      return;
    } 
    Observable.create(new Action() {
          public final void act() {
            try {
              j j = HostDependManager.getInst().postMultiPart(tmaRequest);
              if (TextUtils.isEmpty(j.a())) {
                if (retryCount > 0) {
                  int k = retryCount;
                  ShareRequestHelper.uploadShareImgAsync(shareInfoModel, k - 1, listener);
                  return;
                } 
                if (listener != null) {
                  listener.onFail("response is empty");
                  return;
                } 
              } else {
                JSONObject jSONObject = new JSONObject(j.a());
                int k = jSONObject.optInt("err_no", -1);
                jSONObject = jSONObject.optJSONObject("data");
                if (k == 0 && jSONObject != null) {
                  shareInfoModel.imageUrl = jSONObject.optString("uri");
                  if (listener != null) {
                    listener.onSuccess(shareInfoModel);
                    return;
                  } 
                } else if (listener != null) {
                  listener.onFail("response status not valid");
                } 
                return;
              } 
            } catch (Exception exception) {
              int j = retryCount;
              if (j > 0) {
                ShareRequestHelper.uploadShareImgAsync(shareInfoModel, j - 1, listener);
                return;
              } 
              ShareRequestHelper.OnShareRequestListener onShareRequestListener = listener;
              if (onShareRequestListener != null)
                onShareRequestListener.onException(exception); 
              return;
            } 
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public static String uploadShareImgSync(String paramString) {
    i i = getUploadTmaRequest(paramString);
    if (i == null)
      return CharacterUtils.empty(); 
    try {
      j j = HostDependManager.getInst().postMultiPart(i);
      if (!TextUtils.isEmpty(j.a())) {
        JSONObject jSONObject = new JSONObject(j.a());
        int k = jSONObject.optInt("err_no", -1);
        jSONObject = jSONObject.optJSONObject("data");
        if (k == 0 && jSONObject != null)
          return jSONObject.optString("uri"); 
      } 
    } catch (Exception exception) {}
    return CharacterUtils.empty();
  }
  
  public static interface OnShareRequestListener {
    void onException(Exception param1Exception);
    
    void onFail(String param1String);
    
    void onSuccess(h param1h);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\share\ShareRequestHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */