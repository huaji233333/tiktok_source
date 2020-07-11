package com.tt.miniapp.settings;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.manager.basebundle.BaseBundleDAO;
import com.tt.miniapp.util.ChannelUtil;
import com.tt.miniapp.view.AppbrandSwitch;
import com.tt.miniapp.view.swipeback.SwipeBackActivity;
import com.tt.miniapphost.util.UIUtils;

public class ProjectSettingsActivity extends SwipeBackActivity {
  private LinearLayout mLayout;
  
  private View mScrollView;
  
  public static Intent genIntent(Context paramContext) {
    return new Intent(paramContext, ProjectSettingsActivity.class);
  }
  
  private void initJsSdkSwitch(LayoutInflater paramLayoutInflater) {
    View view = paramLayoutInflater.inflate(2097676325, (ViewGroup)this.mLayout, false);
    ((TextView)view.findViewById(2097545351)).setText(getString(2097742041));
    AppbrandSwitch appbrandSwitch = (AppbrandSwitch)view.findViewById(2097545365);
    appbrandSwitch.setTag(Integer.valueOf(1));
    appbrandSwitch.setChecked(BaseBundleDAO.isLocalTestBundleSwitch((Context)this));
    appbrandSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
            BaseBundleDAO.setLocalTestBundleSwitch((Context)ProjectSettingsActivity.this, param1Boolean);
          }
        });
    this.mLayout.addView(view);
  }
  
  private void initTitle() {
    ((ImageView)findViewById(2097545355)).setImageResource(2097479751);
    UIUtils.configTitleBarWithHeight((Context)this, findViewById(2097545400));
    findViewById(2097545404).setVisibility(8);
    findViewById(2097545400).setBackgroundColor(-1);
    findViewById(2097545355).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ProjectSettingsActivity.this.finish();
          }
        });
    UIUtils.setViewVisibility(findViewById(2097545413), 8);
    ((TextView)findViewById(2097545358)).setText(getString(2097741883));
  }
  
  private void initVdomVersionCodeSwitch(LayoutInflater paramLayoutInflater) {
    View view = paramLayoutInflater.inflate(2097676325, (ViewGroup)this.mLayout, false);
    ((TextView)view.findViewById(2097545351)).setText(getString(2097742050));
    AppbrandSwitch appbrandSwitch = (AppbrandSwitch)view.findViewById(2097545365);
    appbrandSwitch.setTag(Integer.valueOf(2));
    appbrandSwitch.setChecked(BaseBundleDAO.isVdomNotCompareVersionCode((Context)this));
    appbrandSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
            BaseBundleDAO.setVdomNotCompareVersionSwitch((Context)ProjectSettingsActivity.this, param1Boolean);
          }
        });
    this.mLayout.addView(view);
  }
  
  private void initView() {
    Window window = getWindow();
    window.clearFlags(67108864);
    window.addFlags(-2147483648);
    if (Build.VERSION.SDK_INT >= 21)
      window.setStatusBarColor(getResources().getColor(2097348663)); 
    LayoutInflater layoutInflater = LayoutInflater.from((Context)this);
    this.mScrollView = findViewById(2097545386);
    this.mLayout = (LinearLayout)findViewById(2097545321);
    if (ChannelUtil.isLocalTest())
      initJsSdkSwitch(layoutInflater); 
    initVdomVersionCodeSwitch(layoutInflater);
    ImmersedStatusBarHelper immersedStatusBarHelper = new ImmersedStatusBarHelper((Activity)this, (new ImmersedStatusBarHelper.ImmersedStatusBarConfig()).setFitsSystemWindows(true));
    immersedStatusBarHelper.setup(true);
    immersedStatusBarHelper.setUseLightStatusBarInternal(true);
  }
  
  public void finish() {
    super.finish();
    overridePendingTransition(2131034235, UIUtils.getSlideOutAnimation());
  }
  
  public void onBackPressed() {
    finish();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2097676295);
    initTitle();
    initView();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\ProjectSettingsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */