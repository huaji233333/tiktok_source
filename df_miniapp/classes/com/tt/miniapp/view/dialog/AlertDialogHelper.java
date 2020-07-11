package com.tt.miniapp.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.util.UIUtils;

public class AlertDialogHelper {
  public static void adjustDialogViewSize(final Context context, final View dialogView, boolean paramBoolean) {
    int[] arrayOfInt = getAdjustWidthAndHeight(context, paramBoolean);
    int i = arrayOfInt[0];
    final int dialogMaxHeight = arrayOfInt[1];
    (dialogView.getLayoutParams()).width = (int)UIUtils.dip2Px(context, i);
    dialogView.post(new Runnable() {
          public final void run() {
            boolean bool;
            if (UIUtils.px2dip(context, dialogView.getMeasuredHeight()) > dialogMaxHeight) {
              bool = true;
            } else {
              bool = false;
            } 
            if (bool) {
              (dialogView.getLayoutParams()).height = (int)UIUtils.dip2Px(context, dialogMaxHeight);
              if (DevicesUtil.isHuawei() || (DevicesUtil.getBrand().contentEquals("vivo") && Build.MODEL.contentEquals("vivo X21A")))
                ((FrameLayout.LayoutParams)dialogView.getLayoutParams()).topMargin = (DevicesUtil.getScreenHight(context) - dialogMaxHeight) / 2; 
            } 
          }
        });
  }
  
  public static void focusable(Window paramWindow) {
    if (paramWindow == null)
      return; 
    paramWindow.getDecorView().setSystemUiVisibility(5894);
    paramWindow.clearFlags(8);
  }
  
  public static int[] getAdjustWidthAndHeight(Context paramContext, boolean paramBoolean) {
    float f1;
    int i;
    int j;
    float f2 = UIUtils.px2dip(paramContext, DevicesUtil.getScreenWidth(paramContext)) / paramContext.getResources().getInteger(2097610754);
    if ((paramContext.getResources().getConfiguration()).orientation == 2) {
      j = 1;
    } else {
      j = 0;
    } 
    if (j) {
      i = paramContext.getResources().getInteger(2097610753);
    } else {
      i = (int)(paramContext.getResources().getInteger(2097610753) * f2);
    } 
    if (paramBoolean) {
      f1 = 1.0F;
    } else {
      f1 = 0.7F;
    } 
    if (j) {
      j = UIUtils.px2dip(paramContext, DevicesUtil.getScreenHight(paramContext) * f1);
    } else {
      j = (int)(paramContext.getResources().getInteger(2097610752) * f2);
    } 
    return new int[] { i, j };
  }
  
  public static void showActionSheet(Context paramContext, String[] paramArrayOfString, final ActionSheetClickListener clickListener) {
    if (!(paramContext instanceof Activity))
      return; 
    AlertDialog.Builder builder = new AlertDialog.Builder(paramContext);
    builder.setItems((CharSequence[])paramArrayOfString, new DialogInterface.OnClickListener() {
          public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
            param1DialogInterface.dismiss();
            AlertDialogHelper.ActionSheetClickListener actionSheetClickListener = clickListener;
            if (actionSheetClickListener != null)
              actionSheetClickListener.onActionSheetClick(param1Int); 
          }
        });
    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
          public final void onCancel(DialogInterface param1DialogInterface) {
            AlertDialogHelper.ActionSheetClickListener actionSheetClickListener = clickListener;
            if (actionSheetClickListener != null)
              actionSheetClickListener.onCancel(); 
          }
        });
    AlertDialog alertDialog = builder.create();
    alertDialog.setCanceledOnTouchOutside(true);
    if (!((Activity)paramContext).isFinishing())
      alertDialog.show(); 
  }
  
  public static void unfocused(Window paramWindow) {
    if (paramWindow == null)
      return; 
    paramWindow.setFlags(8, 8);
  }
  
  public static interface ActionSheetClickListener {
    void onActionSheetClick(int param1Int);
    
    void onCancel();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\dialog\AlertDialogHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */