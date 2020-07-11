package com.tt.option.y;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import com.tt.miniapp.impl.HostOptionUiDependImpl;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.entity.AnchorConfig;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.permission.IPermissionsResultAction;
import com.tt.option.a;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class a extends a<b> implements b {
  public void dismissLiveWindowView(Activity paramActivity, String paramString, boolean paramBoolean) {
    if (inject())
      ((b)this.defaultOptionDepend).dismissLiveWindowView(paramActivity, paramString, paramBoolean); 
  }
  
  public AnchorConfig getAnchorConfig(String paramString) {
    if (inject())
      ((b)this.defaultOptionDepend).getAnchorConfig(paramString); 
    return null;
  }
  
  public Dialog getLoadingDialog(Activity paramActivity, String paramString) {
    return inject() ? ((b)this.defaultOptionDepend).getLoadingDialog(paramActivity, paramString) : null;
  }
  
  public void hideToast() {
    if (inject())
      ((b)this.defaultOptionDepend).hideToast(); 
  }
  
  public void initFeignHostConfig(Context paramContext) {
    if (inject())
      ((b)this.defaultOptionDepend).initFeignHostConfig(paramContext); 
  }
  
  public void initNativeUIParams() {
    if (inject())
      ((b)this.defaultOptionDepend).initNativeUIParams(); 
  }
  
  public void muteLiveWindowView(Activity paramActivity, String paramString) {
    if (inject())
      ((b)this.defaultOptionDepend).muteLiveWindowView(paramActivity, paramString); 
  }
  
  public void showActionSheet(Context paramContext, String paramString, String[] paramArrayOfString, NativeModule.NativeModuleCallback<Integer> paramNativeModuleCallback) {
    if (inject())
      ((b)this.defaultOptionDepend).showActionSheet(paramContext, paramString, paramArrayOfString, paramNativeModuleCallback); 
  }
  
  public void showDatePickerView(Activity paramActivity, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, b.a<String> parama) {
    if (inject()) {
      ((b)this.defaultOptionDepend).showDatePickerView(paramActivity, paramString1, paramString2, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, parama);
      return;
    } 
  }
  
  public void showLiveWindowView(Activity paramActivity, String paramString) {
    if (inject())
      ((b)this.defaultOptionDepend).showLiveWindowView(paramActivity, paramString); 
  }
  
  public void showModal(Activity paramActivity, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5, String paramString6, String paramString7, NativeModule.NativeModuleCallback<Integer> paramNativeModuleCallback) {
    if (inject()) {
      ((b)this.defaultOptionDepend).showModal(paramActivity, paramString1, paramString2, paramString3, paramBoolean, paramString4, paramString5, paramString6, paramString7, paramNativeModuleCallback);
      return;
    } 
  }
  
  public void showMultiPickerView(Activity paramActivity, String paramString, List<List<String>> paramList, int[] paramArrayOfint, b.b paramb) {
    if (inject())
      ((b)this.defaultOptionDepend).showMultiPickerView(paramActivity, paramString, paramList, paramArrayOfint, paramb); 
  }
  
  public Dialog showPermissionDialog(Activity paramActivity, int paramInt, String paramString, IPermissionsResultAction paramIPermissionsResultAction) {
    return inject() ? ((b)this.defaultOptionDepend).showPermissionDialog(paramActivity, paramInt, paramString, paramIPermissionsResultAction) : null;
  }
  
  public Dialog showPermissionsDialog(Activity paramActivity, Set<Integer> paramSet, LinkedHashMap<Integer, String> paramLinkedHashMap, IPermissionsRequestCallback paramIPermissionsRequestCallback, HashMap<String, String> paramHashMap) {
    return inject() ? ((b)this.defaultOptionDepend).showPermissionsDialog(paramActivity, paramSet, paramLinkedHashMap, paramIPermissionsRequestCallback, paramHashMap) : null;
  }
  
  public void showPickerView(Activity paramActivity, String paramString, int paramInt, List<String> paramList, b.c<String> paramc) {
    if (inject())
      ((b)this.defaultOptionDepend).showPickerView(paramActivity, paramString, paramInt, paramList, paramc); 
  }
  
  public void showRegionPickerView(Activity paramActivity, String paramString, String[] paramArrayOfString, b.e parame) {
    if (inject())
      ((b)this.defaultOptionDepend).showRegionPickerView(paramActivity, paramString, paramArrayOfString, parame); 
  }
  
  public void showTimePickerView(Activity paramActivity, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, b.f<String> paramf) {
    if (inject()) {
      ((b)this.defaultOptionDepend).showTimePickerView(paramActivity, paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramf);
      return;
    } 
  }
  
  public void showToast(Context paramContext, String paramString1, String paramString2, long paramLong, String paramString3) {
    if (inject())
      ((b)this.defaultOptionDepend).showToast(paramContext, paramString1, paramString2, paramLong, paramString3); 
  }
  
  public void showUnSupportView(Activity paramActivity, String paramString, b.g paramg) {
    if (inject())
      ((b)this.defaultOptionDepend).showUnSupportView(paramActivity, paramString, paramg); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\y\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */