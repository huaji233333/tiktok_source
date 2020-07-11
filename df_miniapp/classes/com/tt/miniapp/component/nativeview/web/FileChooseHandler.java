package com.tt.miniapp.component.nativeview.web;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.n.c;
import java.io.File;
import java.util.HashSet;

public class FileChooseHandler implements c {
  private Activity mActivity;
  
  private boolean mActivityNotFound;
  
  private String mCameraFilePath;
  
  private ValueCallback<Uri[]> mUploadCallback;
  
  public FileChooseHandler(Activity paramActivity) {
    this.mActivity = paramActivity;
  }
  
  private Intent createCamcorderIntent() {
    return new Intent("android.media.action.VIDEO_CAPTURE");
  }
  
  private Intent createCameraIntent() {
    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(file.getAbsolutePath());
    stringBuilder.append(File.separator);
    stringBuilder.append("browser-photos");
    file = new File(stringBuilder.toString());
    file.mkdirs();
    stringBuilder = new StringBuilder();
    stringBuilder.append(file.getAbsolutePath());
    stringBuilder.append(File.separator);
    stringBuilder.append(System.currentTimeMillis());
    stringBuilder.append(".jpg");
    this.mCameraFilePath = stringBuilder.toString();
    intent.putExtra("output", (Parcelable)Uri.fromFile(new File(this.mCameraFilePath)));
    return intent;
  }
  
  private Intent createChooserIntent(Intent... paramVarArgs) {
    Intent intent = new Intent("android.intent.action.CHOOSER");
    intent.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[])paramVarArgs);
    intent.putExtra("android.intent.extra.TITLE", UIUtils.getString(2097741870));
    return intent;
  }
  
  private Intent createDefaultOpenableIntent() {
    Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
    intent1.addCategory("android.intent.category.OPENABLE");
    intent1.setType("*/*");
    Intent intent2 = createChooserIntent(new Intent[] { createCameraIntent(), createCamcorderIntent(), createSoundRecorderIntent() });
    intent2.putExtra("android.intent.extra.INTENT", (Parcelable)intent1);
    return intent2;
  }
  
  private Intent createOpenableIntent(String paramString) {
    Intent intent = new Intent("android.intent.action.GET_CONTENT");
    intent.addCategory("android.intent.category.OPENABLE");
    intent.setType(paramString);
    return intent;
  }
  
  private Intent createSoundRecorderIntent() {
    return new Intent("android.provider.MediaStore.RECORD_SOUND");
  }
  
  private void onResult(int paramInt, Intent paramIntent) {
    Uri uri3 = null;
    if (paramInt == 0 && this.mActivityNotFound) {
      this.mActivityNotFound = false;
      setResult(null);
      return;
    } 
    Uri uri2 = uri3;
    if (paramIntent != null)
      if (paramInt != -1) {
        uri2 = uri3;
      } else {
        uri2 = paramIntent.getData();
      }  
    final Uri data = uri2;
    if (uri2 == null) {
      uri1 = uri2;
      if (paramInt == -1) {
        File file = new File(this.mCameraFilePath);
        uri1 = uri2;
        if (file.exists()) {
          uri1 = Uri.fromFile(file);
          this.mActivity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", uri1));
        } 
      } 
    } 
    HashSet<String> hashSet = new HashSet();
    hashSet.add("android.permission.READ_EXTERNAL_STORAGE");
    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this.mActivity, hashSet, new PermissionsResultAction() {
          public void onDenied(String param1String) {
            FileChooseHandler.this.setResult(null);
          }
          
          public void onGranted() {
            FileChooseHandler.this.setResult(data);
          }
        });
  }
  
  private void openFileChooser(ValueCallback<Uri[]> paramValueCallback, String[] paramArrayOfString) {
    Intent intent;
    if (this.mUploadCallback != null)
      return; 
    this.mUploadCallback = paramValueCallback;
    String str = paramArrayOfString[0];
    this.mCameraFilePath = null;
    if (str.equals("image/*")) {
      intent = createChooserIntent(new Intent[] { createCameraIntent() });
      intent.putExtra("android.intent.extra.INTENT", (Parcelable)createOpenableIntent("image/*"));
      startActivity(intent, 11);
      return;
    } 
    if (intent.equals("video/*")) {
      intent = createChooserIntent(new Intent[] { createCamcorderIntent() });
      intent.putExtra("android.intent.extra.INTENT", (Parcelable)createOpenableIntent("video/*"));
      startActivity(intent, 11);
      return;
    } 
    if (intent.equals("audio/*")) {
      intent = createChooserIntent(new Intent[] { createSoundRecorderIntent() });
      intent.putExtra("android.intent.extra.INTENT", (Parcelable)createOpenableIntent("audio/*"));
      startActivity(intent, 11);
      return;
    } 
    startActivity(createDefaultOpenableIntent(), 11);
  }
  
  private void startActivity(Intent paramIntent, int paramInt) {
    try {
      this.mActivity.startActivityForResult(paramIntent, paramInt);
      return;
    } catch (ActivityNotFoundException activityNotFoundException) {
      try {
        this.mActivityNotFound = true;
        this.mActivity.startActivityForResult(createDefaultOpenableIntent(), paramInt);
        return;
      } catch (ActivityNotFoundException activityNotFoundException1) {
        return;
      } 
    } 
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (paramInt1 != 11)
      return; 
    onResult(paramInt2, paramIntent);
  }
  
  public void openFileChooser(ValueCallback<Uri[]> paramValueCallback, WebChromeClient.FileChooserParams paramFileChooserParams) {
    openFileChooser(paramValueCallback, paramFileChooserParams.getAcceptTypes());
  }
  
  public void setResult(Uri paramUri) {
    ValueCallback<Uri[]> valueCallback = this.mUploadCallback;
    if (valueCallback != null)
      if (paramUri != null) {
        valueCallback.onReceiveValue(new Uri[] { paramUri });
      } else {
        valueCallback.onReceiveValue(new Uri[0]);
      }  
    this.mUploadCallback = null;
    this.mActivityNotFound = false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\web\FileChooseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */