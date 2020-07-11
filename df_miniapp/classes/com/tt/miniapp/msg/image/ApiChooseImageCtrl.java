package com.tt.miniapp.msg.image;

import android.app.Activity;
import android.graphics.Bitmap;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.util.ImageUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.util.FileUtil;
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

public class ApiChooseImageCtrl extends b {
  public b.b callback = new b.b() {
      public void onCancel() {
        ApiChooseImageCtrl.this.callbackCancel();
      }
      
      public void onFail(String param1String) {
        ApiChooseImageCtrl.this.callbackFail(param1String);
      }
      
      public void onSuccess(List<MediaEntity> param1List) {
        if (param1List != null && !param1List.isEmpty()) {
          ApiChooseImageCtrl.this.handleImageFiles(param1List);
          return;
        } 
        ApiChooseImageCtrl.this.callbackCancel();
      }
    };
  
  public boolean containsAlbum;
  
  public boolean containsCamera;
  
  public int selectMaxCount = 9;
  
  public ApiChooseImageCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void initArgs() throws JSONException {
    JSONObject jSONObject = new JSONObject(this.mArgs);
    this.selectMaxCount = jSONObject.optInt("count", 9);
    if (this.selectMaxCount <= 0)
      this.selectMaxCount = 9; 
    if (this.selectMaxCount > 20)
      this.selectMaxCount = 20; 
    ArrayList<String> arrayList = new ArrayList();
    JSONArray jSONArray = jSONObject.optJSONArray("sourceType");
    if (jSONArray != null) {
      int j = jSONArray.length();
      for (int i = 0; i < j; i++)
        arrayList.add(jSONArray.getString(i)); 
    } 
    this.containsCamera = arrayList.contains("camera");
    this.containsAlbum = arrayList.contains("album");
  }
  
  private void requestCamera(final Activity activity) {
    final boolean hasRequestCameraPermission = BrandPermissionUtils.hasRequestPermission(14);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.CAMERA);
    BrandPermissionUtils.requestPermissions(activity, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestCameraPermission)
              PermissionHelper.reportAuthFailResult("camera", "mp_reject"); 
            ApiChooseImageCtrl.this.unRegesterResultHandler();
            ApiChooseImageCtrl.this.callbackFail("auth deny");
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.CAMERA");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestCameraPermission)
                      PermissionHelper.reportAuthFailResult("camera", "system_reject"); 
                    ApiChooseImageCtrl.this.unRegesterResultHandler();
                    ApiChooseImageCtrl.this.callbackFail("system auth deny");
                  }
                  
                  public void onGranted() {
                    if (!hasRequestCameraPermission)
                      PermissionHelper.reportAuthSuccessResult("camera"); 
                    if (ApiChooseImageCtrl.this.containsAlbum) {
                      ApiChooseImageCtrl.this.requestAlbum(activity);
                      return;
                    } 
                    HostDependManager.getInst().chooseImage(activity, ApiChooseImageCtrl.this.selectMaxCount, ApiChooseImageCtrl.this.containsAlbum, ApiChooseImageCtrl.this.containsCamera, ApiChooseImageCtrl.this.callback);
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
        requestCamera((Activity)miniappHostBase);
        return;
      } 
      if (this.containsAlbum) {
        requestAlbum((Activity)miniappHostBase);
        return;
      } 
      this.containsAlbum = true;
      this.containsCamera = true;
      requestCamera((Activity)miniappHostBase);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_ApiChooseImageCtrl", new Object[] { "initArgs", jSONException });
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "chooseImage";
  }
  
  public void handleImageFiles(final List<MediaEntity> mediaList) {
    if (mediaList == null) {
      callbackFail("cancel");
      return;
    } 
    Observable.create(new Action() {
          public void act() {
            try {
              ArrayList<String> arrayList = new ArrayList();
              JSONArray jSONArray1 = new JSONArray();
              for (MediaEntity mediaEntity : mediaList) {
                File file = new File(mediaEntity.path);
                if (file.exists()) {
                  File file1;
                  AppBrandLogger.d("tma_ApiChooseImageCtrl", new Object[] { "media.path ", mediaEntity.path });
                  if (mediaEntity.path.endsWith(".png")) {
                    file1 = FileManager.inst().getTempDir();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(System.currentTimeMillis());
                    stringBuilder.append(".png");
                    file1 = new File(file1, stringBuilder.toString());
                    ImageUtil.compressImage(file, 640, 480, Bitmap.CompressFormat.PNG, 75, file1.toString());
                    arrayList.add(file1.toString());
                  } else if (((MediaEntity)file1).path.endsWith(".jpg")) {
                    file1 = FileManager.inst().getTempDir();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(System.currentTimeMillis());
                    stringBuilder.append(".jpg");
                    file1 = new File(file1, stringBuilder.toString());
                    ImageUtil.compressImage(file, 640, 480, Bitmap.CompressFormat.JPEG, 75, file1.toString());
                    arrayList.add(file1.toString());
                  } else if (((MediaEntity)file1).path.endsWith(".jpeg")) {
                    file1 = FileManager.inst().getTempDir();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(System.currentTimeMillis());
                    stringBuilder.append(".jpeg");
                    file1 = new File(file1, stringBuilder.toString());
                    ImageUtil.compressImage(file, 640, 480, Bitmap.CompressFormat.JPEG, 75, file1.toString());
                    arrayList.add(file1.toString());
                  } else {
                    int i = file.getName().lastIndexOf(".");
                    String str = "";
                    if (i > 0)
                      str = file.getName().substring(i); 
                    File file2 = FileManager.inst().getTempDir();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(System.currentTimeMillis());
                    stringBuilder.append(str);
                    file1 = new File(file2, stringBuilder.toString());
                    IOUtils.copyFile(file, file1, false);
                    arrayList.add(file1.toString());
                  } 
                  JSONObject jSONObject1 = new JSONObject();
                  jSONObject1.put("path", FileManager.inst().getSchemaFilePath(file1.toString()));
                  jSONObject1.put("size", FileUtil.getFileSize(file1));
                  jSONArray1.put(jSONObject1);
                } 
              } 
              JSONObject jSONObject = new JSONObject();
              JSONArray jSONArray2 = new JSONArray();
              for (String str : arrayList)
                jSONArray2.put(FileManager.inst().getSchemaFilePath(str)); 
              AppBrandLogger.d("tma_ApiChooseImageCtrl", new Object[] { "tempFilePaths ", jSONArray2.toString() });
              jSONObject.put("tempFiles", jSONArray1);
              jSONObject.put("tempFilePaths", jSONArray2);
              ApiChooseImageCtrl.this.callbackOk(jSONObject);
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("tma_ApiChooseImageCtrl", new Object[] { "handleImageFiles", exception });
              ApiChooseImageCtrl.this.callbackFail(exception);
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
            ApiChooseImageCtrl.this.callbackFail("auth deny");
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.WRITE_EXTERNAL_STORAGE");
            hashSet.add("android.permission.READ_EXTERNAL_STORAGE");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestAlbumPermission)
                      PermissionHelper.reportAuthFailResult("photo", "system_reject"); 
                    ApiChooseImageCtrl.this.unRegesterResultHandler();
                    ApiChooseImageCtrl.this.callbackFail("system auth deny");
                  }
                  
                  public void onGranted() {
                    if (!hasRequestAlbumPermission)
                      PermissionHelper.reportAuthSuccessResult("photo"); 
                    HostDependManager.getInst().chooseImage(activity, ApiChooseImageCtrl.this.selectMaxCount, ApiChooseImageCtrl.this.containsAlbum, ApiChooseImageCtrl.this.containsCamera, ApiChooseImageCtrl.this.callback);
                  }
                });
          }
        }null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\image\ApiChooseImageCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */