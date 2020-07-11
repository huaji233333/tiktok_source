package com.tt.miniapp.msg.video;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.storage.videoSaveToPhotosAlbum.StorageCompat;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.util.IOUtils;
import com.tt.option.e.e;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.json.JSONObject;

public class ApiSaveVideoToPhotosAlbumCtrl extends b {
  private static final HashSet<String> VIDEO_TYPE_SET;
  
  static {
    HashSet<String> hashSet = new HashSet();
    VIDEO_TYPE_SET = hashSet;
    hashSet.add(".mp4");
    VIDEO_TYPE_SET.add(".3gp");
    VIDEO_TYPE_SET.add(".mpeg");
    VIDEO_TYPE_SET.add(".avi");
    VIDEO_TYPE_SET.add(".mov");
    VIDEO_TYPE_SET.add(".wmv");
    VIDEO_TYPE_SET.add(".vob");
    VIDEO_TYPE_SET.add(".m4v");
    VIDEO_TYPE_SET.add(".webm");
    VIDEO_TYPE_SET.add(".rmvb");
    VIDEO_TYPE_SET.add(".mkv");
    VIDEO_TYPE_SET.add(".f4v");
    VIDEO_TYPE_SET.add(".flv");
  }
  
  public ApiSaveVideoToPhotosAlbumCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private boolean isVideo(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    int i = paramString.lastIndexOf(".");
    if (i < 0)
      return false; 
    paramString = paramString.substring(i).toLowerCase();
    return VIDEO_TYPE_SET.contains(paramString);
  }
  
  private void saveVideoToPhotosAlbum(final Activity activity) {
    final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(17);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.ALBUM);
    BrandPermissionUtils.requestPermissions(activity, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestPermission)
              PermissionHelper.reportAuthFailResult("photo", "mp_reject"); 
            ApiSaveVideoToPhotosAlbumCtrl.this.callbackFail("auth deny");
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.WRITE_EXTERNAL_STORAGE");
            hashSet.add("android.permission.READ_EXTERNAL_STORAGE");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthFailResult("photo", "system_reject"); 
                    ApiSaveVideoToPhotosAlbumCtrl.this.callbackFail("system auth deny");
                  }
                  
                  public void onGranted() {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthSuccessResult("photo"); 
                    ApiSaveVideoToPhotosAlbumCtrl.this.saveVideo();
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
    saveVideoToPhotosAlbum((Activity)miniappHostBase);
  }
  
  public String getActionName() {
    return "saveVideoToPhotosAlbum";
  }
  
  public void saveVideo() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("filePath");
      if (TextUtils.isEmpty(str)) {
        callbackFail("filePath不存在");
        return;
      } 
      File file1 = new File(FileManager.inst().getRealFilePath(str));
      if (!file1.exists()) {
        callbackFail("filePath不存在");
        return;
      } 
      if (!FileManager.inst().canRead(file1)) {
        callbackFail(a.a(new String[] { "read", str }));
        return;
      } 
      if (!isVideo(str)) {
        callbackFail("格式不正确");
        return;
      } 
      Application application = AppbrandContext.getInst().getApplicationContext();
      File file2 = new File(StorageCompat.getSystemCameraDir((Context)application), file1.getName());
      IOUtils.copyFile(file1, file2, false);
      application.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiSaveVideoToPhotosAlbumCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\video\ApiSaveVideoToPhotosAlbumCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */