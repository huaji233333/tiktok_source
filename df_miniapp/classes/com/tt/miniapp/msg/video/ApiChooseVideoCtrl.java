package com.tt.miniapp.msg.video;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.util.IOUtils;
import com.tt.option.e.e;
import com.tt.option.n.b;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiChooseVideoCtrl extends b {
  public b.c callback = new b.c() {
      public void onCancel() {
        ApiChooseVideoCtrl.this.callbackCancel();
      }
      
      public void onFail(String param1String) {
        ApiChooseVideoCtrl.this.callbackFail(param1String);
      }
      
      public void onSuccess(List<String> param1List) {
        if (param1List != null && !param1List.isEmpty()) {
          ApiChooseVideoCtrl.this.invokeCallBack(param1List.get(0));
          return;
        } 
        ApiChooseVideoCtrl.this.callbackCancel();
      }
    };
  
  public boolean containsAlbum;
  
  public boolean containsCamera;
  
  public String mVideoPath;
  
  public int maxDuration = 60;
  
  public ApiChooseVideoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void initArgs() throws JSONException {
    JSONObject jSONObject = new JSONObject(this.mArgs);
    ArrayList<String> arrayList = new ArrayList();
    JSONArray jSONArray = jSONObject.optJSONArray("sourceType");
    if (jSONArray != null) {
      int j = jSONArray.length();
      for (int i = 0; i < j; i++)
        arrayList.add(jSONArray.getString(i)); 
    } 
    this.containsCamera = arrayList.contains("camera");
    this.containsAlbum = arrayList.contains("album");
    this.maxDuration = jSONObject.optInt("maxDuration", 60);
    if (this.maxDuration > 180)
      this.maxDuration = 180; 
    if (this.maxDuration <= 0)
      this.maxDuration = 60; 
  }
  
  private void requestVideoCap(final Activity activity) {
    final boolean hasRequestCameraPermission = BrandPermissionUtils.hasRequestPermission(14);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.CAMERA);
    BrandPermissionUtils.requestPermissions(activity, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestCameraPermission)
              PermissionHelper.reportAuthFailResult("camera", "mp_reject"); 
            ApiChooseVideoCtrl.this.unRegesterResultHandler();
            ApiChooseVideoCtrl.this.callbackFail("auth deny");
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.CAMERA");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestCameraPermission)
                      PermissionHelper.reportAuthFailResult("camera", "system_reject"); 
                    ApiChooseVideoCtrl.this.unRegesterResultHandler();
                    ApiChooseVideoCtrl.this.callbackFail("system auth deny");
                  }
                  
                  public void onGranted() {
                    if (!hasRequestCameraPermission)
                      PermissionHelper.reportAuthSuccessResult("camera"); 
                    if (ApiChooseVideoCtrl.this.containsAlbum) {
                      ApiChooseVideoCtrl.this.requestAlbum(activity);
                      return;
                    } 
                    HostDependManager.getInst().chooseVideo(activity, ApiChooseVideoCtrl.this.maxDuration, ApiChooseVideoCtrl.this.containsAlbum, ApiChooseVideoCtrl.this.containsCamera, ApiChooseVideoCtrl.this.callback);
                  }
                });
          }
        }null);
  }
  
  public void act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail("activity is null");
      return;
    } 
    try {
      initArgs();
      if (this.containsCamera) {
        requestVideoCap((Activity)miniappHostBase);
        return;
      } 
      if (this.containsAlbum) {
        requestAlbum((Activity)miniappHostBase);
        return;
      } 
      this.containsAlbum = true;
      this.containsCamera = true;
      requestVideoCap((Activity)miniappHostBase);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiChooseVideoCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "chooseVideo";
  }
  
  public String getFileType(String paramString) {
    if (!TextUtils.isEmpty(paramString)) {
      int i = paramString.lastIndexOf(".");
      if (i > 0 && i < paramString.length())
        return paramString.substring(i); 
    } 
    return "";
  }
  
  public void invokeCallBack(String paramString) {
    boolean bool;
    if (!TextUtils.isEmpty(paramString) && (new File(paramString)).exists()) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mVideoPath = paramString;
    if (!bool)
      try {
        callbackFail("cancel");
        return;
      } catch (Exception exception) {
        AppBrandLogger.e("tma_ApiChooseVideoCtrl", new Object[] { exception });
        callbackFail(exception);
        return;
      }  
    Observable.create(new Action() {
          public void act() {
            try {
              boolean bool1;
              boolean bool2;
              JSONObject jSONObject = new JSONObject();
              File file = FileManager.inst().getTempDir();
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(System.currentTimeMillis());
              stringBuilder.append(ApiChooseVideoCtrl.this.getFileType(ApiChooseVideoCtrl.this.mVideoPath));
              file = new File(file, stringBuilder.toString());
              IOUtils.copyFile(new File(ApiChooseVideoCtrl.this.mVideoPath), file, false);
              MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
              mediaMetadataRetriever.setDataSource(ApiChooseVideoCtrl.this.mVideoPath);
              String str = mediaMetadataRetriever.extractMetadata(9);
              Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
              if (bitmap != null) {
                bool2 = bitmap.getWidth();
                bool1 = bitmap.getHeight();
              } else {
                bool1 = false;
                bool2 = false;
              } 
              try {
                long l = Long.parseLong(str) / 1000L;
                if (l > ApiChooseVideoCtrl.this.maxDuration) {
                  ApiChooseVideoCtrl.this.callbackFail("over the maxDuration");
                  return;
                } 
                jSONObject.put("duration", l);
              } catch (NumberFormatException numberFormatException) {}
              jSONObject.put("tempFilePath", FileManager.inst().getSchemaFilePath(file.getCanonicalPath()));
              jSONObject.put("size", file.length());
              jSONObject.put("width", bool2);
              jSONObject.put("height", bool1);
              ApiChooseVideoCtrl.this.callbackOk(jSONObject);
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("tma_ApiChooseVideoCtrl", new Object[] { exception });
              ApiChooseVideoCtrl.this.callbackFail(exception);
              return;
            } 
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public void requestAlbum(final Activity activity) {
    final boolean hasRequestAlbumPermission = BrandPermissionUtils.hasRequestPermission(17);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.ALBUM);
    BrandPermissionUtils.requestPermissions(activity, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestAlbumPermission)
              PermissionHelper.reportAuthFailResult("photo", "mp_reject"); 
            ApiChooseVideoCtrl.this.unRegesterResultHandler();
            ApiChooseVideoCtrl.this.callbackFail("auth deny");
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.WRITE_EXTERNAL_STORAGE");
            hashSet.add("android.permission.READ_EXTERNAL_STORAGE");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestAlbumPermission)
                      PermissionHelper.reportAuthFailResult("photo", "system_reject"); 
                    ApiChooseVideoCtrl.this.unRegesterResultHandler();
                    ApiChooseVideoCtrl.this.callbackFail("system auth deny");
                  }
                  
                  public void onGranted() {
                    if (!hasRequestAlbumPermission)
                      PermissionHelper.reportAuthSuccessResult("photo"); 
                    HostDependManager.getInst().chooseVideo(activity, ApiChooseVideoCtrl.this.maxDuration, ApiChooseVideoCtrl.this.containsAlbum, ApiChooseVideoCtrl.this.containsCamera, ApiChooseVideoCtrl.this.callback);
                  }
                });
          }
        }null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\video\ApiChooseVideoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */