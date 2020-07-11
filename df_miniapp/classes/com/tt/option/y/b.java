package com.tt.option.y;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.entity.AnchorConfig;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.permission.IPermissionsResultAction;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public interface b {
  void dismissLiveWindowView(Activity paramActivity, String paramString, boolean paramBoolean);
  
  AnchorConfig getAnchorConfig(String paramString);
  
  Dialog getLoadingDialog(Activity paramActivity, String paramString);
  
  void hideToast();
  
  void initFeignHostConfig(Context paramContext);
  
  void initNativeUIParams();
  
  void muteLiveWindowView(Activity paramActivity, String paramString);
  
  void showActionSheet(Context paramContext, String paramString, String[] paramArrayOfString, NativeModule.NativeModuleCallback<Integer> paramNativeModuleCallback);
  
  void showDatePickerView(Activity paramActivity, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, a<String> parama);
  
  void showLiveWindowView(Activity paramActivity, String paramString);
  
  void showModal(Activity paramActivity, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5, String paramString6, String paramString7, NativeModule.NativeModuleCallback<Integer> paramNativeModuleCallback);
  
  void showMultiPickerView(Activity paramActivity, String paramString, List<List<String>> paramList, int[] paramArrayOfint, b paramb);
  
  Dialog showPermissionDialog(Activity paramActivity, int paramInt, String paramString, IPermissionsResultAction paramIPermissionsResultAction);
  
  Dialog showPermissionsDialog(Activity paramActivity, Set<Integer> paramSet, LinkedHashMap<Integer, String> paramLinkedHashMap, IPermissionsRequestCallback paramIPermissionsRequestCallback, HashMap<String, String> paramHashMap);
  
  void showPickerView(Activity paramActivity, String paramString, int paramInt, List<String> paramList, c<String> paramc);
  
  void showRegionPickerView(Activity paramActivity, String paramString, String[] paramArrayOfString, e parame);
  
  void showTimePickerView(Activity paramActivity, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, f<String> paramf);
  
  void showToast(Context paramContext, String paramString1, String paramString2, long paramLong, String paramString3);
  
  void showUnSupportView(Activity paramActivity, String paramString, g paramg);
  
  public static interface a<T> extends d {
    void onDatePicked(T param1T1, T param1T2, T param1T3);
  }
  
  public static interface b extends d {
    void onConfirm(int[] param1ArrayOfint);
    
    void onWheeled(int param1Int1, int param1Int2, Object param1Object);
  }
  
  public static interface c<T> extends d {
    void onItemPicked(int param1Int, T param1T);
  }
  
  public static interface d {
    void onCancel();
    
    void onDismiss();
  }
  
  public static interface e extends d {
    void onConfirm(String[] param1ArrayOfString1, String[] param1ArrayOfString2);
    
    void onWheeled(int param1Int1, int param1Int2, Object param1Object);
  }
  
  public static interface f<T> extends d {
    void onTimePicked(T param1T1, T param1T2);
  }
  
  public static interface g {
    void proceed();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\y\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */