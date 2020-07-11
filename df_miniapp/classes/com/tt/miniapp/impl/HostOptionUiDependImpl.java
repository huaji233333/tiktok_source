package com.tt.miniapp.impl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.a;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.address.AddressInfo;
import com.tt.miniapp.component.nativeview.game.RoundedImageView;
import com.tt.miniapp.component.nativeview.picker.framework.popup.ConfirmPopup;
import com.tt.miniapp.component.nativeview.picker.wheel.DatePicker;
import com.tt.miniapp.component.nativeview.picker.wheel.MultiPicker;
import com.tt.miniapp.component.nativeview.picker.wheel.RegionPicker;
import com.tt.miniapp.component.nativeview.picker.wheel.SinglePicker;
import com.tt.miniapp.component.nativeview.picker.wheel.TimePicker;
import com.tt.miniapp.facialverify.FacialVerifyProtocolActivity;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.toast.ToastManager;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.view.dialog.AlertDialogHelper;
import com.tt.miniapp.view.dialog.LoadingDialog;
import com.tt.miniapp.view.dialog.ModalDialog;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.entity.AnchorConfig;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageUtils;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.permission.IPermissionsResultAction;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.y.b;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HostOptionUiDependImpl implements b {
  private void initMultiplePermissionsUI(final Activity activity, FrameLayout paramFrameLayout, final LinkedHashMap<Integer, Boolean> permissionTypes, final TextView tvAllow) {
    final int checkBoxBorderColor = Color.parseColor("#E8E8E8");
    ListView listView = new ListView((Context)activity);
    listView.setHorizontalScrollBarEnabled(false);
    listView.setVerticalScrollBarEnabled(false);
    listView.setDividerHeight(0);
    View view = new View((Context)activity);
    view.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, (int)UIUtils.dip2Px((Context)activity, 0.5F)));
    view.setBackgroundColor(activity.getResources().getColor(2097348618));
    listView.addFooterView(view);
    paramFrameLayout.addView((View)listView);
    listView.setAdapter((ListAdapter)new BaseAdapter() {
          public int getCount() {
            return permissionTypesKeySet.length;
          }
          
          public Object getItem(int param1Int) {
            return permissionTypesKeySet[param1Int];
          }
          
          public long getItemId(int param1Int) {
            return param1Int;
          }
          
          public View getView(final int position, View param1View, ViewGroup param1ViewGroup) {
            View view = param1View;
            if (param1View == null) {
              view = LayoutInflater.from((Context)activity).inflate(2097676318, null);
              ((TextView)view.findViewById(2097545486)).setText(HostDependManager.getInst().permissionTypeToPermission(permissionTypesKeySet[position].intValue()).getMsg());
              final CheckBox cbPermission = (CheckBox)view.findViewById(2097545220);
              GradientDrawable gradientDrawable1 = new GradientDrawable();
              gradientDrawable1.setShape(1);
              gradientDrawable1.setColor(Color.parseColor(NativeUIParamsEntity.getInst().getPositiveColor()));
              GradientDrawable gradientDrawable2 = new GradientDrawable();
              gradientDrawable2.setShape(1);
              gradientDrawable2.setColor(Color.parseColor(NativeUIParamsEntity.getInst().getNegativeColor()));
              gradientDrawable2.setStroke((int)UIUtils.dip2Px((Context)activity, 1.0F), checkBoxBorderColor);
              StateListDrawable stateListDrawable = new StateListDrawable();
              stateListDrawable.addState(new int[] { 16842912 }, (Drawable)gradientDrawable1);
              stateListDrawable.addState(new int[] { -16842912 }, (Drawable)gradientDrawable2);
              checkBox.setBackground((Drawable)stateListDrawable);
              checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton param2CompoundButton, boolean param2Boolean) {
                      // Byte code:
                      //   0: aload_0
                      //   1: getfield this$1 : Lcom/tt/miniapp/impl/HostOptionUiDependImpl$28;
                      //   4: getfield val$permissionTypes : Ljava/util/LinkedHashMap;
                      //   7: aload_0
                      //   8: getfield this$1 : Lcom/tt/miniapp/impl/HostOptionUiDependImpl$28;
                      //   11: getfield val$permissionTypesKeySet : [Ljava/lang/Integer;
                      //   14: aload_0
                      //   15: getfield val$position : I
                      //   18: aaload
                      //   19: iload_2
                      //   20: invokestatic valueOf : (Z)Ljava/lang/Boolean;
                      //   23: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
                      //   26: pop
                      //   27: iconst_0
                      //   28: istore_3
                      //   29: iload_2
                      //   30: ifeq -> 44
                      //   33: aload_0
                      //   34: getfield val$ivPermissionCheckboxFG : Landroid/widget/ImageView;
                      //   37: iconst_0
                      //   38: invokevirtual setVisibility : (I)V
                      //   41: goto -> 53
                      //   44: aload_0
                      //   45: getfield val$ivPermissionCheckboxFG : Landroid/widget/ImageView;
                      //   48: bipush #8
                      //   50: invokevirtual setVisibility : (I)V
                      //   53: aload_0
                      //   54: getfield this$1 : Lcom/tt/miniapp/impl/HostOptionUiDependImpl$28;
                      //   57: getfield val$permissionTypes : Ljava/util/LinkedHashMap;
                      //   60: invokevirtual values : ()Ljava/util/Collection;
                      //   63: invokeinterface iterator : ()Ljava/util/Iterator;
                      //   68: astore_1
                      //   69: aload_1
                      //   70: invokeinterface hasNext : ()Z
                      //   75: ifeq -> 96
                      //   78: aload_1
                      //   79: invokeinterface next : ()Ljava/lang/Object;
                      //   84: checkcast java/lang/Boolean
                      //   87: invokevirtual booleanValue : ()Z
                      //   90: ifeq -> 69
                      //   93: goto -> 98
                      //   96: iconst_1
                      //   97: istore_3
                      //   98: aload_0
                      //   99: getfield this$1 : Lcom/tt/miniapp/impl/HostOptionUiDependImpl$28;
                      //   102: getfield val$tvAllow : Landroid/widget/TextView;
                      //   105: iload_3
                      //   106: iconst_1
                      //   107: ixor
                      //   108: invokevirtual setEnabled : (Z)V
                      //   111: return
                    }
                  });
              view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View param2View) {
                      CheckBox checkBox = cbPermission;
                      checkBox.setChecked(checkBox.isChecked() ^ true);
                    }
                  });
              checkBox.setChecked(((Boolean)permissionTypes.get(permissionTypesKeySet[position])).booleanValue());
            } 
            return view;
          }
        });
  }
  
  private void initPermissionFacialVerifyDialog(final Activity activity, FrameLayout paramFrameLayout, HashMap<String, String> paramHashMap, final TextView tvAllow) {
    View view2 = LayoutInflater.from((Context)activity).inflate(2097676357, (ViewGroup)paramFrameLayout);
    String str1 = CharacterUtils.empty();
    if (paramHashMap != null)
      str1 = paramHashMap.get("name"); 
    ((TextView)view2.findViewById(2097545488)).setText(a.a(UIUtils.getString(2097741889), new Object[] { str1 }));
    TextView textView = (TextView)view2.findViewById(2097545478);
    String str2 = UIUtils.getString(2097741890);
    SpannableString spannableString = SpannableString.valueOf(str2);
    spannableString.setSpan(new ClickableSpan() {
          public void onClick(View param1View) {
            FacialVerifyProtocolActivity.startFacialVerifyProtocol((Context)activity);
          }
          
          public void updateDrawState(TextPaint param1TextPaint) {
            super.updateDrawState(param1TextPaint);
            param1TextPaint.setColor(Color.parseColor("#66000000"));
            param1TextPaint.setUnderlineText(true);
          }
        },  str2.length() - 4, str2.length(), 17);
    textView.setMovementMethod(LinkMovementMethod.getInstance());
    textView.setHighlightColor(activity.getResources().getColor(2097348673));
    textView.setText((CharSequence)spannableString);
    View view1 = view2.findViewById(2097545225);
    final CheckBox cbPermission = (CheckBox)view2.findViewById(2097545219);
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setShape(1);
    gradientDrawable.setColor(Color.parseColor(NativeUIParamsEntity.getInst().getPositiveColor()));
    Drawable drawable = activity.getResources().getDrawable(2097479699);
    StateListDrawable stateListDrawable = new StateListDrawable();
    stateListDrawable.addState(new int[] { 16842912 }, (Drawable)gradientDrawable);
    stateListDrawable.addState(new int[] { -16842912 }, drawable);
    checkBox.setBackground((Drawable)stateListDrawable);
    checkBox.setChecked(false);
    tvAllow.setEnabled(false);
    final ImageView checkView = (ImageView)view2.findViewById(2097545232);
    view1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            cbPermission.performClick();
          }
        });
    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
            if (param1Boolean) {
              checkView.setVisibility(0);
              tvAllow.setEnabled(true);
              return;
            } 
            checkView.setVisibility(4);
            tvAllow.setEnabled(false);
          }
        });
  }
  
  private void initPermissionsCommonUI(Activity paramActivity, FrameLayout paramFrameLayout, int paramInt) {
    View view = LayoutInflater.from((Context)paramActivity).inflate(2097676356, (ViewGroup)paramFrameLayout);
    BrandPermissionUtils.BrandPermission brandPermission = HostDependManager.getInst().permissionTypeToPermission(paramInt);
    ((TextView)view.findViewById(2097545487)).setText(brandPermission.getMsg());
    String str = CharacterUtils.trimString(brandPermission.getSubtitle(), 46, false, "...");
    ((TextView)view.findViewById(2097545492)).setText(str);
  }
  
  private void initPermissionsDialog(final Activity activity, final Dialog dialog, View paramView1, TextView paramTextView1, TextView paramTextView2, View paramView2, final LinkedHashMap<Integer, Boolean> permissionTypes, final LinkedHashMap<Integer, String> grantedResult, final IPermissionsRequestCallback callback) {
    dialog.setCanceledOnTouchOutside(false);
    dialog.setContentView(paramView1);
    dialog.setCancelable(false);
    paramTextView1.setTextColor(Color.parseColor(NativeUIParamsEntity.getInst().getPositiveItemNegativeTextColor()));
    GradientDrawable gradientDrawable = (GradientDrawable)paramTextView1.getBackground();
    gradientDrawable.setCornerRadius(UIUtils.dip2Px((Context)activity, NativeUIParamsEntity.getInst().getBtnCornerRadius()));
    gradientDrawable.setColor(Color.parseColor(NativeUIParamsEntity.getInst().getPositiveColor()));
    paramTextView2.setTextColor(Color.parseColor(NativeUIParamsEntity.getInst().getNegativeTextColor()));
    gradientDrawable = (GradientDrawable)paramTextView2.getBackground();
    gradientDrawable.setCornerRadius(UIUtils.dip2Px((Context)activity, NativeUIParamsEntity.getInst().getBtnCornerRadius()));
    gradientDrawable.setColor(Color.parseColor(NativeUIParamsEntity.getInst().getNegativeColor()));
    paramTextView1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            for (Map.Entry entry : permissionTypes.entrySet()) {
              boolean bool;
              if (entry.getValue() != null && ((Boolean)entry.getValue()).booleanValue()) {
                bool = true;
              } else {
                bool = false;
              } 
              if (bool) {
                grantedResult.put(entry.getKey(), "ok");
                continue;
              } 
              grantedResult.put(entry.getKey(), "auth deny");
            } 
            callback.onGranted(grantedResult);
            _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(dialog);
          }
          
          class null {}
        });
    paramTextView2.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Iterator<Integer> iterator = permissionTypes.keySet().iterator();
            while (iterator.hasNext()) {
              int i = ((Integer)iterator.next()).intValue();
              grantedResult.put(Integer.valueOf(i), "auth deny");
            } 
            callback.onDenied(grantedResult);
            _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(dialog);
          }
          
          class null {}
        });
    paramView2.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            String str = (AppbrandApplication.getInst().getAppInfo()).privacyPolicyUrl;
            if (ApiPermissionManager.isAdSiteMiniApp() && permissionTypes.keySet().contains(Integer.valueOf(16))) {
              str = Uri.encode("https://sf3-ttcdn-tos.pstatp.com/obj/developer/misc/ad_site_privacy.html");
            } else {
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append(Uri.encode(str));
              stringBuilder1.append("&title=");
              stringBuilder1.append(Uri.encode(activity.getString(2097741983)));
              stringBuilder1.append("&hide_bar=0");
              str = stringBuilder1.toString();
            } 
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(AppbrandContext.getInst().getInitParams().getHostStr(1008, "sslocal"));
            stringBuilder.append("://webview?url=");
            stringBuilder.append(str);
            str = stringBuilder.toString();
            Activity activity = activity;
            if (activity instanceof MiniappHostBase)
              ActivityUtil.startOpenSchemaActivity((MiniappHostBase)activity, str, null, null); 
          }
        });
    Window window = dialog.getWindow();
    if (window != null) {
      if (TextUtils.equals(DevicesUtil.getBrand().toLowerCase(), "huawei") && AppbrandContext.getInst().isGame()) {
        window.setFlags(1024, 1024);
        window.getDecorView().setSystemUiVisibility(2822);
      } 
      window.setLayout(-1, -2);
      window.setGravity(80);
      window.setWindowAnimations(2132607943);
      window.getDecorView().setSystemUiVisibility(2304);
    } 
  }
  
  private void initPermissionsDialogUI(final View dialogView, final RoundedImageView ivMiniappIcon, final Activity activity, TextView paramTextView, View paramView2, LinkedHashMap<Integer, Boolean> paramLinkedHashMap) {
    boolean bool1;
    dialogView.post(new Runnable() {
          public void run() {
            if ((activity.getResources().getConfiguration()).orientation == 2) {
              i = 1;
            } else {
              i = 0;
            } 
            if (i) {
              Activity activity = activity;
              i = (int)UIUtils.dip2Px((Context)activity, activity.getResources().getInteger(2097610756));
              (dialogView.getLayoutParams()).width = i;
              i = (DevicesUtil.getScreenWidth((Context)activity) - i) / 2;
              dialogView.setX(i);
            } 
            int i = (int)(dialogView.getMeasuredWidth() * dialogHeightScale);
            if (dialogView.getMeasuredHeight() > i)
              (dialogView.getLayoutParams()).height = i; 
          }
        });
    ivMiniappIcon.setBorderColor(-1);
    ivMiniappIcon.setBorderWidth(UIUtils.dip2Px((Context)activity, 2.0F));
    int i = (int)((ivMiniappIcon.getLayoutParams()).height * NativeUIParamsEntity.getInst().getMicroAppLogoCornerRadiusRatio());
    double d = NativeUIParamsEntity.getInst().getMicroAppLogoCornerRadiusRatio();
    boolean bool2 = true;
    if (d == 0.5D) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (bool1) {
      ivMiniappIcon.setOval(true);
    } else {
      ivMiniappIcon.setCornerRadius(i);
    } 
    activity.runOnUiThread(new Runnable() {
          public void run() {
            if ((AppbrandApplication.getInst().getAppInfo()).icon != null)
              HostDependManager.getInst().loadImage((Context)activity, (ImageView)ivMiniappIcon, Uri.parse((AppbrandApplication.getInst().getAppInfo()).icon)); 
          }
        });
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append((AppbrandApplication.getInst().getAppInfo()).appName);
    stringBuilder1.append("  ");
    String str = stringBuilder1.toString();
    if (ApiPermissionManager.isAdSiteMiniApp() && paramLinkedHashMap.keySet().contains(Integer.valueOf(16))) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    if (bool1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(activity.getResources().getString(2097741951));
      str = stringBuilder.toString();
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(activity.getResources().getString(2097741952));
      str = stringBuilder.toString();
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str);
    stringBuilder2.append(":");
    paramTextView.setText(stringBuilder2.toString());
    if (!TextUtils.isEmpty((AppbrandApplication.getInst().getAppInfo()).privacyPolicyUrl) || bool1) {
      UIUtils.setViewVisibility(paramView2, 0);
      if (bool1) {
        ((TextView)paramView2.findViewById(2097545491)).setText(activity.getResources().getString(2097741849));
        return;
      } 
      ((TextView)paramView2.findViewById(2097545490)).setText((AppbrandApplication.getInst().getAppInfo()).appName);
    } 
  }
  
  private void initPermissionsPhoneNumberUI(Activity paramActivity, FrameLayout paramFrameLayout, HashMap<String, String> paramHashMap) {
    String str;
    View view = LayoutInflater.from((Context)paramActivity).inflate(2097676358, (ViewGroup)paramFrameLayout);
    TextView textView2 = (TextView)view.findViewById(2097545489);
    TextView textView1 = (TextView)view.findViewById(2097545487);
    BrandPermissionUtils.BrandPermission brandPermission = HostDependManager.getInst().permissionTypeToPermission(16);
    if (brandPermission != null)
      textView1.setText(brandPermission.getMsg()); 
    if (paramHashMap != null) {
      str = paramHashMap.get("phoneNumber");
    } else {
      str = "";
    } 
    textView2.setText(str);
  }
  
  private void initPermissionsUserInfoUI(Activity paramActivity, FrameLayout paramFrameLayout, LinkedHashMap<Integer, String> paramLinkedHashMap, HashMap<String, String> paramHashMap) {
    boolean bool;
    View view = LayoutInflater.from((Context)paramActivity).inflate(2097676359, (ViewGroup)paramFrameLayout);
    TextView textView1 = (TextView)view.findViewById(2097545487);
    RoundedImageView roundedImageView = (RoundedImageView)view.findViewById(2097545234);
    TextView textView3 = (TextView)view.findViewById(2097545495);
    TextView textView2 = (TextView)view.findViewById(2097545473);
    BrandPermissionUtils.BrandPermission brandPermission = HostDependManager.getInst().permissionTypeToPermission(11);
    if (brandPermission != null)
      textView1.setText(brandPermission.getMsg()); 
    roundedImageView.setBorderColor(-1);
    roundedImageView.setBorderWidth(UIUtils.dip2Px((Context)paramActivity, 1.0F));
    int i = (int)((roundedImageView.getLayoutParams()).height * NativeUIParamsEntity.getInst().getAvatorAppLogoCornerRadiusRatio());
    if (NativeUIParamsEntity.getInst().getAvatorAppLogoCornerRadiusRatio() == 0.5D) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      roundedImageView.setOval(true);
    } else {
      roundedImageView.setCornerRadius(i);
    } 
    String str2 = "";
    if (paramHashMap != null) {
      str1 = paramHashMap.get("nickName");
      str2 = paramHashMap.get("avatarUrl");
    } else {
      str1 = "";
    } 
    HostDependManager.getInst().loadImage((Context)paramActivity, (ImageView)roundedImageView, Uri.parse(str2));
    textView3.setText(str1);
    String str1 = paramActivity.getResources().getString(2097742060);
    str2 = paramActivity.getResources().getString(2097742061);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str1);
    stringBuilder.append(AppbrandUtil.getApplicationName((Context)paramActivity));
    stringBuilder.append(str2);
    textView2.setText(stringBuilder.toString());
  }
  
  public void dismissLiveWindowView(Activity paramActivity, String paramString, boolean paramBoolean) {}
  
  public AnchorConfig getAnchorConfig(String paramString) {
    return null;
  }
  
  public Dialog getLoadingDialog(Activity paramActivity, String paramString) {
    return (Dialog)new LoadingDialog((Context)paramActivity, paramString);
  }
  
  public void hideToast() {
    ToastManager.hideToast();
  }
  
  public void initFeignHostConfig(Context paramContext) {}
  
  public void initNativeUIParams() {}
  
  public void muteLiveWindowView(Activity paramActivity, String paramString) {}
  
  public void showActionSheet(Context paramContext, String paramString, String[] paramArrayOfString, final NativeModule.NativeModuleCallback<Integer> nativeModuleCallback) {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null) {
      if (paramArrayOfString == null)
        return; 
      AlertDialogHelper.showActionSheet((Context)miniappHostBase, paramArrayOfString, new AlertDialogHelper.ActionSheetClickListener() {
            public void onActionSheetClick(int param1Int) {
              nativeModuleCallback.onNativeModuleCall(Integer.valueOf(param1Int));
            }
            
            public void onCancel() {
              nativeModuleCallback.onNativeModuleCall(Integer.valueOf(-1));
            }
          });
    } 
  }
  
  public void showDatePickerView(Activity paramActivity, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, final b.a<String> extendDatePickerListener) {
    DatePicker datePicker;
    if (TextUtils.equals(paramString2, "year")) {
      datePicker = new DatePicker(paramActivity, 5);
      datePicker.setRange(paramInt1, paramInt4);
      datePicker.setSelectedItem(paramInt7, 0, 0);
    } else if (TextUtils.equals(paramString2, "month")) {
      datePicker = new DatePicker((Activity)datePicker, 1);
      datePicker.setRangeStart(paramInt1, paramInt2);
      datePicker.setRangeEnd(paramInt4, paramInt5);
      datePicker.setSelectedItem(paramInt7, paramInt8, 0);
    } else if (TextUtils.equals(paramString2, "day")) {
      datePicker = new DatePicker((Activity)datePicker, 0);
      datePicker.setRangeStart(paramInt1, paramInt2, paramInt3);
      datePicker.setRangeEnd(paramInt4, paramInt5, paramInt6);
      datePicker.setSelectedItem(paramInt7, paramInt8, paramInt9);
    } else {
      datePicker = null;
    } 
    if (datePicker == null)
      return; 
    datePicker.setLabel(null, null, null, null, null);
    datePicker.setOnCancelListener(new ConfirmPopup.OnCancelListener() {
          public void onCancel() {
            extendDatePickerListener.onCancel();
          }
        });
    datePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
          public void onDismiss(DialogInterface param1DialogInterface) {
            extendDatePickerListener.onCancel();
          }
        });
    if (TextUtils.equals(paramString2, "year")) {
      datePicker.setOnDatePickListener((DatePicker.OnDatePickListener)new DatePicker.OnYearPickListener() {
            public void onDateTimePicked(String param1String) {
              extendDatePickerListener.onDatePicked(param1String, null, null);
            }
          });
    } else if (TextUtils.equals(paramString2, "month")) {
      datePicker.setOnDatePickListener((DatePicker.OnDatePickListener)new DatePicker.OnYearMonthPickListener() {
            public void onDatePicked(String param1String1, String param1String2) {
              extendDatePickerListener.onDatePicked(param1String1, param1String2, null);
            }
          });
    } else if (TextUtils.equals(paramString2, "day")) {
      datePicker.setOnDatePickListener((DatePicker.OnDatePickListener)new DatePicker.OnYearMonthDayPickListener() {
            public void onDatePicked(String param1String1, String param1String2, String param1String3) {
              extendDatePickerListener.onDatePicked(param1String1, param1String2, param1String3);
            }
          });
    } 
    datePicker.show();
  }
  
  public void showLiveWindowView(Activity paramActivity, String paramString) {}
  
  public void showModal(Activity paramActivity, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5, String paramString6, String paramString7, final NativeModule.NativeModuleCallback<Integer> nativeModuleCallback) {
    ModalDialog.Builder.builder(paramActivity).title(paramString2).content(paramString3).negativeBtnText(paramString4).positiveBtnText(paramString6).onNegativeBtnClickListener(new ModalDialog.OnNegativeBtnClickListener() {
          public void onClick() {
            nativeModuleCallback.onNativeModuleCall(Integer.valueOf(0));
          }
        }).onPositiveBtnClickListener(new ModalDialog.OnPositiveBtnClickListener() {
          public void onClick() {
            nativeModuleCallback.onNativeModuleCall(Integer.valueOf(1));
          }
        }).build().show();
  }
  
  public void showMultiPickerView(Activity paramActivity, String paramString, List<List<String>> paramList, int[] paramArrayOfint, final b.b extendMultiPickerListener) {
    MultiPicker multiPicker = new MultiPicker(paramActivity, paramList);
    multiPicker.setSelectedItem(paramArrayOfint);
    multiPicker.show();
    multiPicker.setOnCancelListener(new ConfirmPopup.OnCancelListener() {
          public void onCancel() {
            extendMultiPickerListener.onCancel();
          }
        });
    multiPicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
          public void onDismiss(DialogInterface param1DialogInterface) {
            extendMultiPickerListener.onCancel();
          }
        });
    multiPicker.setOnComfimListener(new MultiPicker.onConfirmListener() {
          public void onConfirm(int[] param1ArrayOfint) {
            extendMultiPickerListener.onConfirm(param1ArrayOfint);
          }
        });
    multiPicker.setOnWheelListener(new MultiPicker.OnWheelListener() {
          public void onWheeled(int param1Int1, int param1Int2, Object param1Object) {
            extendMultiPickerListener.onWheeled(param1Int1, param1Int2, param1Object);
          }
        });
  }
  
  public Dialog showPermissionDialog(Activity paramActivity, int paramInt, String paramString, IPermissionsResultAction paramIPermissionsResultAction) {
    ModalDialog modalDialog = ModalDialog.Builder.builder(paramActivity).title(paramActivity.getString(2097742010, new Object[] { (AppbrandApplication.getInst().getAppInfo()).appName })).content(paramString).positiveBtnText(paramActivity.getString(2097741866)).negativeBtnText(paramActivity.getString(2097741868)).build();
    modalDialog.show();
    return (Dialog)modalDialog;
  }
  
  public Dialog showPermissionsDialog(Activity paramActivity, Set<Integer> paramSet, LinkedHashMap<Integer, String> paramLinkedHashMap, IPermissionsRequestCallback paramIPermissionsRequestCallback, HashMap<String, String> paramHashMap) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
    Iterator<Integer> iterator = paramSet.iterator();
    while (iterator.hasNext())
      linkedHashMap.put(Integer.valueOf(((Integer)iterator.next()).intValue()), Boolean.valueOf(true)); 
    View view1 = LayoutInflater.from((Context)paramActivity).inflate(2097676298, null);
    RoundedImageView roundedImageView = (RoundedImageView)view1.findViewById(2097545231);
    TextView textView3 = (TextView)view1.findViewById(2097545485);
    FrameLayout frameLayout = (FrameLayout)view1.findViewById(2097545227);
    TextView textView2 = (TextView)view1.findViewById(2097545475);
    TextView textView1 = (TextView)view1.findViewById(2097545474);
    View view2 = view1.findViewById(2097545363);
    initPermissionsDialogUI(view1, roundedImageView, paramActivity, textView3, view2, (LinkedHashMap)linkedHashMap);
    Dialog dialog = new Dialog((Context)paramActivity, 2097807367);
    initPermissionsDialog(paramActivity, dialog, view1, textView1, textView2, view2, (LinkedHashMap)linkedHashMap, paramLinkedHashMap, paramIPermissionsRequestCallback);
    int i = (int)UIUtils.dip2Px((Context)paramActivity, 32.0F);
    int j = (int)UIUtils.dip2Px((Context)paramActivity, 32.0F);
    if (linkedHashMap.size() == 1) {
      if (linkedHashMap.keySet().contains(Integer.valueOf(11))) {
        linkedHashMap.put(Integer.valueOf(11), Boolean.valueOf(true));
        initPermissionsUserInfoUI(paramActivity, frameLayout, paramLinkedHashMap, paramHashMap);
      } else if (linkedHashMap.keySet().contains(Integer.valueOf(16))) {
        linkedHashMap.put(Integer.valueOf(16), Boolean.valueOf(true));
        initPermissionsPhoneNumberUI(paramActivity, frameLayout, paramHashMap);
      } else if (linkedHashMap.keySet().contains(Integer.valueOf(19))) {
        linkedHashMap.put(Integer.valueOf(19), Boolean.valueOf(false));
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)frameLayout.getLayoutParams();
        marginLayoutParams.setMarginStart(0);
        marginLayoutParams.setMarginEnd(0);
        initPermissionFacialVerifyDialog(paramActivity, frameLayout, paramHashMap, textView1);
      } else {
        j = ((Integer)linkedHashMap.keySet().iterator().next()).intValue();
        linkedHashMap.put(Integer.valueOf(j), Boolean.valueOf(true));
        initPermissionsCommonUI(paramActivity, frameLayout, j);
        float f = UIUtils.dip2Px((Context)paramActivity, 67.0F);
        j = (int)f;
      } 
    } else {
      initMultiplePermissionsUI(paramActivity, frameLayout, (LinkedHashMap)linkedHashMap, textView1);
      i = (int)UIUtils.dip2Px((Context)paramActivity, 28.0F);
      float f = UIUtils.dip2Px((Context)paramActivity, 29.0F);
      j = (int)f;
    } 
    frameLayout.setPadding(0, i, 0, j + frameLayout.getPaddingBottom());
    return dialog;
  }
  
  public void showPickerView(Activity paramActivity, String paramString, int paramInt, List<String> paramList, final b.c<String> extendPickerListener) {
    SinglePicker singlePicker = new SinglePicker(paramActivity, paramList);
    singlePicker.setOnCancelListener(new ConfirmPopup.OnCancelListener() {
          public void onCancel() {
            extendPickerListener.onCancel();
          }
        });
    singlePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
          public void onDismiss(DialogInterface param1DialogInterface) {
            extendPickerListener.onDismiss();
          }
        });
    singlePicker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
          public void onItemPicked(int param1Int, String param1String) {
            extendPickerListener.onItemPicked(param1Int, param1String);
          }
        });
    singlePicker.setSelectedIndex(paramInt);
    singlePicker.show();
  }
  
  public void showRegionPickerView(Activity paramActivity, String paramString, String[] paramArrayOfString, final b.e extendMultiPickerListener) {
    final RegionPicker multiPicker = new RegionPicker(paramActivity);
    regionPicker.setSelectedRegionItem(paramArrayOfString);
    regionPicker.setWheelHead(paramString);
    regionPicker.loadItems();
    regionPicker.show();
    regionPicker.setOnCancelListener(new ConfirmPopup.OnCancelListener() {
          public void onCancel() {
            extendMultiPickerListener.onCancel();
          }
        });
    regionPicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
          public void onDismiss(DialogInterface param1DialogInterface) {
            extendMultiPickerListener.onCancel();
          }
        });
    regionPicker.setOnComfimListener(new MultiPicker.onConfirmListener() {
          public void onConfirm(int[] param1ArrayOfint) {
            AddressInfo[] arrayOfAddressInfo = multiPicker.getSelectedRegionArray();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(arrayOfAddressInfo[0]);
            stringBuilder.append(",");
            stringBuilder.append(arrayOfAddressInfo[1]);
            stringBuilder.append(",");
            stringBuilder.append(arrayOfAddressInfo[1]);
            AppBrandLogger.d("showRegionPickerView", new Object[] { stringBuilder.toString() });
            b.e e1 = extendMultiPickerListener;
            String str2 = (arrayOfAddressInfo[0]).name;
            String str3 = (arrayOfAddressInfo[1]).name;
            String str4 = (arrayOfAddressInfo[2]).name;
            String str5 = (arrayOfAddressInfo[0]).code;
            String str6 = (arrayOfAddressInfo[1]).code;
            String str1 = (arrayOfAddressInfo[2]).code;
            e1.onConfirm(new String[] { str2, str3, str4 }, new String[] { str5, str6, str1 });
          }
        });
    regionPicker.setOnWheelListener(new MultiPicker.OnWheelListener() {
          public void onWheeled(int param1Int1, int param1Int2, Object param1Object) {
            extendMultiPickerListener.onWheeled(param1Int1, param1Int2, param1Object);
          }
        });
  }
  
  public void showTimePickerView(Activity paramActivity, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, final b.f<String> extendTimePickerListener) {
    TimePicker timePicker = new TimePicker(paramActivity);
    timePicker.setRangeStart(paramInt1, paramInt2);
    timePicker.setRangeEnd(paramInt3, paramInt4);
    timePicker.setSelectedItem(paramInt5, paramInt6);
    timePicker.setOnCancelListener(new ConfirmPopup.OnCancelListener() {
          public void onCancel() {
            extendTimePickerListener.onCancel();
          }
        });
    timePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
          public void onDismiss(DialogInterface param1DialogInterface) {
            extendTimePickerListener.onDismiss();
          }
        });
    timePicker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
          public void onTimePicked(String param1String1, String param1String2) {
            extendTimePickerListener.onTimePicked(param1String1, param1String2);
          }
        });
    timePicker.show();
  }
  
  public void showToast(Context paramContext, String paramString1, String paramString2, long paramLong, String paramString3) {
    ToastManager.showToast(paramContext, paramString2, paramLong, paramString3);
  }
  
  public void showUnSupportView(Activity paramActivity, String paramString, b.g paramg) {
    if (paramActivity == null || TextUtils.isEmpty(paramString) || !HostDependManager.getInst().handleAppbrandDisablePage((Context)paramActivity, paramString)) {
      HostDependManager hostDependManager = HostDependManager.getInst();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(AppbrandConstant.OpenApi.getInst().getNOT_SUPPORT_URL());
      stringBuilder.append("?");
      stringBuilder.append(LanguageUtils.appendLanguageQueryParam());
      hostDependManager.jumpToWebView((Context)paramActivity, stringBuilder.toString(), "", true);
    } 
    paramg.proceed();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostOptionUiDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */