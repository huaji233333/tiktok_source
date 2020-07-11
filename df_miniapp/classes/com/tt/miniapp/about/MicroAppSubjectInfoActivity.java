package com.tt.miniapp.about;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.util.DateUtils;
import com.tt.miniapp.view.RoundedImageView;
import com.tt.miniapp.view.ViewUtils;
import com.tt.miniapp.view.swipeback.SwipeBackActivity;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.util.UIUtils;
import java.util.List;

public class MicroAppSubjectInfoActivity extends SwipeBackActivity implements LanguageChangeListener {
  public String corp_name;
  
  private List<String> domains;
  
  public String icon;
  
  public RoundedImageView ivMicroappIcon;
  
  private LinearLayout lyMicroappDomains;
  
  private LinearLayout lyMicroappServiceCategory;
  
  private ImmersedStatusBarHelper mImmersedStatusBarHelper;
  
  public String name;
  
  public String service_category;
  
  private TextView tvMicroappCorpName;
  
  private TextView tvMicroappDomains;
  
  private TextView tvMicroappName;
  
  private TextView tvMicroappServiceCategory;
  
  private TextView tvMicroappUpdateTime;
  
  private TextView tvMicroappVersion;
  
  public long update_time;
  
  public String version;
  
  private void initIntent() {
    Intent intent = getIntent();
    this.icon = intent.getStringExtra("icon");
    this.name = intent.getStringExtra("name");
    this.corp_name = intent.getStringExtra("corp_name");
    this.service_category = intent.getStringExtra("service_category");
    this.version = intent.getStringExtra("version");
    this.update_time = intent.getLongExtra("update_time", 0L);
    this.domains = intent.getStringArrayListExtra("domains");
  }
  
  private void initTitle() {
    ((ImageView)findViewById(2097545355)).setImageResource(2097479751);
    UIUtils.configTitleBarWithHeight((Context)this, findViewById(2097545400));
    findViewById(2097545404).setVisibility(8);
    findViewById(2097545400).setBackgroundColor(-1);
    findViewById(2097545355).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MicroAppSubjectInfoActivity.this.finish();
          }
        });
    UIUtils.setViewVisibility(findViewById(2097545413), 8);
    ((TextView)findViewById(2097545358)).setText(getString(2097741837));
  }
  
  private void initView() {
    this.ivMicroappIcon = (RoundedImageView)findViewById(2097545230);
    ViewUtils.setImageViewStyle((ImageView)this.ivMicroappIcon, (int)(NativeUIParamsEntity.getInst().getMicroAppLogoCornerRadiusRatio() * this.ivMicroappIcon.getMeasuredHeight()));
    this.tvMicroappName = (TextView)findViewById(2097545482);
    this.tvMicroappCorpName = (TextView)findViewById(2097545480);
    this.tvMicroappServiceCategory = (TextView)findViewById(2097545483);
    this.tvMicroappVersion = (TextView)findViewById(2097545496);
    this.tvMicroappUpdateTime = (TextView)findViewById(2097545494);
    this.tvMicroappDomains = (TextView)findViewById(2097545481);
    this.lyMicroappServiceCategory = (LinearLayout)findViewById(2097545239);
    this.lyMicroappDomains = (LinearLayout)findViewById(2097545238);
    if (TextUtils.isEmpty(this.service_category))
      this.lyMicroappServiceCategory.setVisibility(8); 
    if (!TextUtils.isEmpty(this.icon)) {
      HostDependManager.getInst().loadImage((Context)this, (ImageView)this.ivMicroappIcon, Uri.parse(this.icon));
    } else {
      this.ivMicroappIcon.setImageDrawable(getResources().getDrawable(2097479724));
    } 
    if (!TextUtils.isEmpty(this.name))
      this.tvMicroappName.setText(this.name); 
    if (!TextUtils.isEmpty(this.corp_name))
      this.tvMicroappCorpName.setText(this.corp_name); 
    if (!TextUtils.isEmpty(this.service_category) && this.lyMicroappServiceCategory.isShown())
      this.tvMicroappServiceCategory.setText(this.service_category); 
    if (!TextUtils.isEmpty(this.version))
      if (!TextUtils.equals("null", this.version)) {
        this.tvMicroappVersion.setText(this.version);
      } else {
        this.tvMicroappVersion.setText(getString(2097742044));
      }  
    long l = this.update_time;
    if (l != 0L) {
      this.tvMicroappUpdateTime.setText(DateUtils.parseDate(l * 1000L));
    } else {
      this.tvMicroappUpdateTime.setText(getString(2097742044));
    } 
    List<String> list = this.domains;
    if (list != null && list.size() != 0) {
      StringBuffer stringBuffer = new StringBuffer();
      int j = this.domains.size();
      for (int i = 0; i < j; i++) {
        if (i > 0)
          stringBuffer.append("\n"); 
        if (!this.lyMicroappDomains.isShown())
          this.lyMicroappDomains.setVisibility(0); 
        stringBuffer.append(this.domains.get(i));
      } 
      this.tvMicroappDomains.setText(stringBuffer);
    } else {
      this.lyMicroappDomains.setVisibility(8);
    } 
    this.ivMicroappIcon.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          public void onGlobalLayout() {
            MicroAppSubjectInfoActivity.this.ivMicroappIcon.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            ViewUtils.setImageViewStyle((ImageView)MicroAppSubjectInfoActivity.this.ivMicroappIcon, (int)(NativeUIParamsEntity.getInst().getMicroAppLogoCornerRadiusRatio() * MicroAppSubjectInfoActivity.this.ivMicroappIcon.getMeasuredHeight()));
          }
        });
  }
  
  public void finish() {
    super.finish();
    overridePendingTransition(0, UIUtils.getSlideOutAnimation());
  }
  
  protected ImmersedStatusBarHelper.ImmersedStatusBarConfig getImmersedStatusBarConfig() {
    return (new ImmersedStatusBarHelper.ImmersedStatusBarConfig()).setFitsSystemWindows(true).setStatusBarColor(getResources().getColor(2097348664));
  }
  
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(0, UIUtils.getSlideOutAnimation());
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2097676289);
    this.mImmersedStatusBarHelper = new ImmersedStatusBarHelper((Activity)this, getImmersedStatusBarConfig());
    this.mImmersedStatusBarHelper.setup(true);
    this.mImmersedStatusBarHelper.setUseLightStatusBarInternal(true);
    initIntent();
    initView();
    initTitle();
  }
  
  public void onLanguageChange() {}
  
  public boolean swipeBackPriority() {
    return super.swipeBackPriority();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\about\MicroAppSubjectInfoActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */