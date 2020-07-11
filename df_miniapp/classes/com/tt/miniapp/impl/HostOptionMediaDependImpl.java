package com.tt.miniapp.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.base.activity.ActivityServiceInterface;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.chooser.PickerActivity;
import com.tt.miniapp.chooser.PickerConfig;
import com.tt.miniapp.chooser.adapter.MediaGridAdapter;
import com.tt.miniapp.component.nativeview.web.FileChooseHandler;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.entity.ScanResultEntity;
import com.tt.option.n.b;
import com.tt.option.n.c;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class HostOptionMediaDependImpl implements b {
  public void chooseImage(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, final b.b callback, b.a parama) {
    if (paramBoolean2 && !paramBoolean1) {
      MediaGridAdapter.openCamera(paramActivity, 10);
    } else {
      Intent intent = new Intent((Context)paramActivity, PickerActivity.class);
      intent.putExtra("select_mode", 100);
      intent.putExtra("max_select_size", 188743680L);
      intent.putExtra("max_select_count", paramInt);
      if (paramBoolean2 || !paramBoolean1)
        intent.putExtra("camerType", 1); 
      paramActivity.startActivityForResult(intent, 7);
    } 
    parama.setActivityResultHandler(new IActivityResultHandler() {
          public boolean autoClearAfterActivityResult() {
            return true;
          }
          
          public boolean handleActivityResult(int param1Int1, int param1Int2, Intent param1Intent) {
            ArrayList<MediaEntity> arrayList = new ArrayList();
            if (param1Int1 == 7) {
              if (param1Int2 == 19901026 && param1Intent != null) {
                ArrayList arrayList1 = param1Intent.getParcelableArrayListExtra("select_result");
                callback.onSuccess(arrayList1);
                return true;
              } 
              callback.onCancel();
              return true;
            } 
            if (param1Int1 == 10) {
              if (param1Int2 == -1) {
                String str = PickerConfig.currentCameraPhotoPath;
                if (!TextUtils.isEmpty(str)) {
                  File file = new File(str);
                  if (file.exists()) {
                    arrayList.add(new MediaEntity(str, file.getName(), 0L, 0, file.length(), 0, ""));
                    callback.onSuccess(arrayList);
                    return true;
                  } 
                  callback.onFail(a.e(str));
                  return true;
                } 
                callback.onFail("file path is empty");
                return true;
              } 
              callback.onCancel();
              return true;
            } 
            return false;
          }
        });
  }
  
  public void chooseVideo(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, final b.c callback) {
    if (paramBoolean2 && !paramBoolean1) {
      MediaGridAdapter.openVideoCap(paramActivity, 9);
    } else {
      Intent intent = new Intent((Context)paramActivity, PickerActivity.class);
      intent.putExtra("select_mode", 102);
      intent.putExtra("max_select_size", 188743680L);
      intent.putExtra("max_select_count", 1);
      if (paramBoolean2 || !paramBoolean1)
        intent.putExtra("camerType", 2); 
      paramActivity.startActivityForResult(intent, 4);
    } 
    ((ActivityServiceInterface)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(ActivityServiceInterface.class)).registerActivityResultHandler(new IActivityResultHandler() {
          public boolean autoClearAfterActivityResult() {
            return true;
          }
          
          public boolean handleActivityResult(int param1Int1, int param1Int2, Intent param1Intent) {
            if (param1Int1 == 4) {
              if (param1Int2 == 19901026 && param1Intent != null) {
                ArrayList arrayList1 = param1Intent.getParcelableArrayListExtra("select_result");
                ArrayList<String> arrayList = new ArrayList();
                if (arrayList1 != null && !arrayList1.isEmpty()) {
                  Iterator iterator = arrayList1.iterator();
                  while (iterator.hasNext())
                    arrayList.add(((MediaEntity)iterator.next()).path); 
                } 
                callback.onSuccess(arrayList);
                return true;
              } 
              callback.onCancel();
              return true;
            } 
            if (param1Int1 == 9) {
              if (param1Int2 == -1) {
                String str = PickerConfig.currentCameraVideoPath;
                if (!TextUtils.isEmpty(str)) {
                  if ((new File(str)).exists()) {
                    ArrayList<String> arrayList = new ArrayList();
                    arrayList.add(str);
                    callback.onSuccess(arrayList);
                    return true;
                  } 
                  callback.onFail(a.e(str));
                  return true;
                } 
                callback.onFail("file path is empty");
                return true;
              } 
              callback.onCancel();
              return true;
            } 
            return false;
          }
        });
  }
  
  public c createChooseFileHandler(Activity paramActivity) {
    return (c)new FileChooseHandler(paramActivity);
  }
  
  public ScanResultEntity handleActivityScanResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return new ScanResultEntity();
  }
  
  public boolean scanCode(Activity paramActivity, b.d paramd) {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostOptionMediaDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */