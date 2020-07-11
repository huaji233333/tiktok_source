package com.tt.miniapp.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.process.bridge.InnerMiniAppProcessBridge;
import com.tt.miniapp.view.AppbrandSwitch;
import com.tt.miniapp.view.ViewUtils;
import com.tt.miniapp.view.swipeback.SwipeBackActivity;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.miniapphost.util.UIUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionSettingActivity extends SwipeBackActivity implements CompoundButton.OnCheckedChangeListener, LanguageChangeListener {
  private Map<Integer, Boolean> changeMap = new HashMap<Integer, Boolean>();
  
  private FrameLayout mAppbrandDotContent;
  
  private FrameLayout mAppbrandMore;
  
  private TextView mNoPermissionText;
  
  private TextView mPermissionText;
  
  private View mScrollView;
  
  private View mTitleBarMenuLayout;
  
  private View createDividerView() {
    LinearLayout linearLayout = new LinearLayout((Context)this);
    linearLayout.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, 1));
    linearLayout.setBackgroundResource(2097348680);
    View view = new View((Context)this);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
    layoutParams.leftMargin = (int)UIUtils.dip2Px((Context)this, 15.0F);
    view.setBackgroundResource(2097348653);
    view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    linearLayout.addView(view);
    return (View)linearLayout;
  }
  
  private View createPermissionView(LayoutInflater paramLayoutInflater, LinearLayout paramLinearLayout, PermissionData paramPermissionData) {
    View view = paramLayoutInflater.inflate(2097676325, (ViewGroup)paramLinearLayout, false);
    ((TextView)view.findViewById(2097545351)).setText(paramPermissionData.name);
    AppbrandSwitch appbrandSwitch = (AppbrandSwitch)view.findViewById(2097545365);
    appbrandSwitch.setTag(Integer.valueOf(paramPermissionData.permission));
    appbrandSwitch.setChecked(paramPermissionData.isGranted);
    appbrandSwitch.setOnCheckedChangeListener(this);
    ViewUtils.setSwitchBgColor((Context)this, appbrandSwitch, NativeUIParamsEntity.getInst().getPositiveColor());
    return view;
  }
  
  public static Intent genIntent(Context paramContext) {
    Intent intent = new Intent(paramContext, PermissionSettingActivity.class);
    ArrayList<PermissionData> arrayList = new ArrayList();
    for (BrandPermissionUtils.BrandPermission brandPermission : HostDependManager.getInst().getUserDefinableHostPermissionList()) {
      if (BrandPermissionUtils.hasRequestPermission(brandPermission.getPermissionType()))
        arrayList.add(new PermissionData(brandPermission.getPermissionType(), brandPermission.getName(), BrandPermissionUtils.isGranted(brandPermission.getPermissionType()))); 
    } 
    if (AppbrandApplication.getInst().getAppInfo().isGame()) {
      BrandPermissionUtils.BrandPermission brandPermission = BrandPermissionUtils.BrandPermission.SCREEN_RECORD;
      intent.putExtra("extra_screen_record", new PermissionData(brandPermission.getPermissionType(), brandPermission.getName(), BrandPermissionUtils.isGranted(brandPermission.getPermissionType(), true)));
    } 
    intent.putExtra("extra_brand_name", (AppbrandApplication.getInst().getAppInfo()).appName);
    intent.putExtra("extra_permission_list", arrayList);
    intent.putExtra("ma_callerProcessIdentify", ProcessUtil.getProcessIdentify());
    return intent;
  }
  
  private void initTitle() {
    ((ImageView)findViewById(2097545355)).setImageResource(2097479751);
    UIUtils.configTitleBarWithHeight((Context)this, findViewById(2097545400));
    findViewById(2097545404).setVisibility(8);
    findViewById(2097545400).setBackgroundColor(-1);
    findViewById(2097545355).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PermissionSettingActivity.this.finish();
          }
        });
    this.mTitleBarMenuLayout = findViewById(2097545413);
    UIUtils.setViewVisibility(this.mTitleBarMenuLayout, 8);
    ((TextView)findViewById(2097545358)).setText(getString(2097742025));
  }
  
  public void finish() {
    if (!this.changeMap.isEmpty()) {
      Intent intent = new Intent();
      intent.putExtra("extra_change_permission_map", (Serializable)this.changeMap);
      setResult(51, intent);
    } 
    super.finish();
    overridePendingTransition(2131034235, UIUtils.getSlideOutAnimation());
  }
  
  public void onBackPressed() {
    finish();
  }
  
  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
    int i = ((Integer)paramCompoundButton.getTag()).intValue();
    this.changeMap.put(Integer.valueOf(i), Boolean.valueOf(paramBoolean));
    if (i == 11)
      InnerMiniAppProcessBridge.savePermissionGrant(getIntent().getStringExtra("ma_callerProcessIdentify"), i, paramBoolean); 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2097676294);
    Window window = getWindow();
    window.clearFlags(67108864);
    window.addFlags(-2147483648);
    if (Build.VERSION.SDK_INT >= 21)
      window.setStatusBarColor(getResources().getColor(2097348663)); 
    this.mScrollView = findViewById(2097545364);
    this.mNoPermissionText = (TextView)findViewById(2097545352);
    this.mPermissionText = (TextView)findViewById(2097545366);
    initTitle();
    List<PermissionData> list = (List)getIntent().getSerializableExtra("extra_permission_list");
    PermissionData permissionData = (PermissionData)getIntent().getSerializableExtra("extra_screen_record");
    if ((list != null && !list.isEmpty()) || permissionData != null) {
      UIUtils.setViewVisibility((View)this.mNoPermissionText, 8);
      UIUtils.setViewVisibility(this.mScrollView, 0);
      this.mPermissionText.setText(getString(2097741867, new Object[] { getIntent().getStringExtra("extra_brand_name") }));
      LinearLayout linearLayout = (LinearLayout)findViewById(2097545320);
      LayoutInflater layoutInflater = LayoutInflater.from((Context)this);
      int i;
      for (i = 0; list != null && i < list.size(); i++) {
        if (i != 0)
          linearLayout.addView(createDividerView()); 
        linearLayout.addView(createPermissionView(layoutInflater, linearLayout, list.get(i)));
      } 
      if (permissionData != null) {
        View view = createPermissionView(layoutInflater, linearLayout, permissionData);
        if (list != null && !list.isEmpty())
          UIUtils.updateLayoutMargin(view, 0, (int)UIUtils.dip2Px((Context)this, 24.0F), 0, 0); 
        linearLayout.addView(view);
        linearLayout.addView(createDividerView());
        TextView textView = new TextView((Context)this);
        textView.setText(2097742015);
        textView.setTextColor(getResources().getColor(2097348668));
        textView.setTextSize(14.0F);
        textView.setBackgroundResource(2097348680);
        textView.setLineSpacing((int)UIUtils.dip2Px((Context)this, 8.0F), 1.0F);
        i = (int)UIUtils.dip2Px((Context)this, 14.0F);
        int j = (int)UIUtils.dip2Px((Context)this, 15.0F);
        textView.setPadding(j, i, j, i);
        linearLayout.addView((View)textView);
      } 
    } else {
      UIUtils.setViewVisibility((View)this.mNoPermissionText, 0);
      UIUtils.setViewVisibility(this.mScrollView, 8);
      this.mNoPermissionText.setText(getString(2097741865, new Object[] { getIntent().getStringExtra("extra_brand_name") }));
    } 
    ImmersedStatusBarHelper immersedStatusBarHelper = new ImmersedStatusBarHelper((Activity)this, (new ImmersedStatusBarHelper.ImmersedStatusBarConfig()).setFitsSystemWindows(true));
    immersedStatusBarHelper.setup(true);
    immersedStatusBarHelper.setUseLightStatusBarInternal(true);
  }
  
  public void onLanguageChange() {
    initTitle();
  }
  
  public void onPause() {
    super.onPause();
  }
  
  public void onResume() {
    super.onResume();
  }
  
  public boolean swipeBackPriority() {
    return super.swipeBackPriority();
  }
  
  static final class PermissionData implements Serializable {
    public boolean isGranted;
    
    public String name;
    
    public int permission;
    
    private PermissionData(int param1Int, String param1String, boolean param1Boolean) {
      this.permission = param1Int;
      this.name = param1String;
      this.isGranted = param1Boolean;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\PermissionSettingActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */