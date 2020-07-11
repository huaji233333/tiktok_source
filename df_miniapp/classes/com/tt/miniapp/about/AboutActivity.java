package com.tt.miniapp.about;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.util.ChannelUtil;
import com.tt.miniapp.util.RSAUtil;
import com.tt.miniapp.util.SaftyUtil;
import com.tt.miniapp.view.RoundedImageView;
import com.tt.miniapp.view.ViewUtils;
import com.tt.miniapp.view.swipeback.SwipeBackActivity;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.UIUtils;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AboutActivity extends SwipeBackActivity implements LanguageChangeListener {
  public Button btnBackMiniapp;
  
  private ImageView ivHeadlinePlatformGoto;
  
  public RoundedImageView ivHeadlinePlatformIcon0;
  
  public RoundedImageView ivMiniappIcon;
  
  private ImageView ivMiniappInfoGoto;
  
  public LinearLayout lyHeadlinePlatform;
  
  public LinearLayout lyHeadlinePlatformIcon;
  
  public LinearLayout lyServiceCategory;
  
  private LinearLayout lySubjectInformation;
  
  public AboutInfo mAboutInfo;
  
  public TextView mDebugInfoView;
  
  private TextView tvAboutDeveloper;
  
  private TextView tvAboutHeadlinePlatform;
  
  private TextView tvAboutServiceCatagory;
  
  private TextView tvAboutSubjectInfo;
  
  public TextView tvHeadlinePlatformName;
  
  public TextView tvMiniappCategory;
  
  public TextView tvMiniappName;
  
  public TextView tvMiniappSummary;
  
  public TextView tvSubjectInfor;
  
  private String CreateTTCode() {
    String str1 = SaftyUtil.genRandomString();
    String str2 = SaftyUtil.genRandomString();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str1);
    stringBuilder.append("#");
    stringBuilder.append(str2);
    byte[] arrayOfByte = RSAUtil.encryptContent((Context)this, stringBuilder.toString());
    if (arrayOfByte != null) {
      AboutInfo aboutInfo = this.mAboutInfo;
      aboutInfo.encryKey = str1;
      aboutInfo.encryIV = str2;
      return Base64.encodeToString(arrayOfByte, 10);
    } 
    return "";
  }
  
  private void bindListener() {
    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View param1View) {
          Intent intent = new Intent((Context)AboutActivity.this, MicroAppSubjectInfoActivity.class);
          intent.putExtra("icon", AboutActivity.this.mAboutInfo.icon);
          intent.putExtra("name", AboutActivity.this.mAboutInfo.name);
          if (AboutActivity.this.mAboutInfo.is_corp) {
            intent.putExtra("corp_name", AboutActivity.this.mAboutInfo.corp_name);
          } else {
            intent.putExtra("corp_name", AboutActivity.this.getString(2097741986));
          } 
          intent.putExtra("service_category", AboutActivity.this.mAboutInfo.service_category);
          intent.putExtra("version", AboutActivity.this.mAboutInfo.version);
          intent.putExtra("update_time", AboutActivity.this.mAboutInfo.update_time);
          intent.putStringArrayListExtra("domains", AboutActivity.this.mAboutInfo.domains);
          AboutActivity.this.startActivity(intent);
          AboutActivity.this.overridePendingTransition(UIUtils.getSlideInAnimation(), 2131034242);
        }
      };
    this.ivMiniappInfoGoto.setOnClickListener(onClickListener);
    this.lySubjectInformation.setOnClickListener(onClickListener);
    this.ivHeadlinePlatformGoto.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {}
        });
    this.btnBackMiniapp.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            AboutActivity.this.onBackPressed();
          }
        });
  }
  
  private void initTitle() {
    ((ImageView)findViewById(2097545355)).setImageResource(2097479751);
    UIUtils.configTitleBarWithHeight((Context)this, findViewById(2097545400));
    findViewById(2097545404).setVisibility(8);
    findViewById(2097545400).setBackgroundColor(-1);
    findViewById(2097545355).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            AboutActivity.this.finish();
          }
        });
    UIUtils.setViewVisibility(findViewById(2097545413), 8);
    ((TextView)findViewById(2097545358)).setText(getString(2097741832));
  }
  
  private void initView() {
    this.ivMiniappIcon = (RoundedImageView)findViewById(2097545314);
    this.tvMiniappName = (TextView)findViewById(2097545422);
    this.tvAboutServiceCatagory = (TextView)findViewById(2097545242);
    this.tvAboutSubjectInfo = (TextView)findViewById(2097545243);
    this.tvAboutHeadlinePlatform = (TextView)findViewById(2097545241);
    this.tvMiniappSummary = (TextView)findViewById(2097545427);
    this.lyServiceCategory = (LinearLayout)findViewById(2097545385);
    this.tvMiniappCategory = (TextView)findViewById(2097545484);
    this.lySubjectInformation = (LinearLayout)findViewById(2097545240);
    this.tvSubjectInfor = (TextView)findViewById(2097545426);
    this.ivMiniappInfoGoto = (ImageView)findViewById(2097545315);
    this.lyHeadlinePlatform = (LinearLayout)findViewById(2097545304);
    this.lyHeadlinePlatformIcon = (LinearLayout)findViewById(2097545306);
    this.ivHeadlinePlatformIcon0 = (RoundedImageView)findViewById(2097545307);
    this.tvHeadlinePlatformName = (TextView)findViewById(2097545308);
    this.ivHeadlinePlatformGoto = (ImageView)findViewById(2097545305);
    this.btnBackMiniapp = (Button)findViewById(2097545250);
    ViewUtils.setButtonStyle(this.btnBackMiniapp, NativeUIParamsEntity.getInst().getPositiveColor(), NativeUIParamsEntity.getInst().getPositiveItemNegativeTextColor(), NativeUIParamsEntity.getInst().getBtnCornerRadius());
    this.lyServiceCategory.setVisibility(8);
    this.lyHeadlinePlatform.setVisibility(8);
    this.ivMiniappIcon.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          public void onGlobalLayout() {
            AboutActivity.this.ivMiniappIcon.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            ViewUtils.setImageViewStyle((ImageView)AboutActivity.this.ivMiniappIcon, (int)(NativeUIParamsEntity.getInst().getMicroAppLogoCornerRadiusRatio() * AboutActivity.this.ivMiniappIcon.getMeasuredHeight()));
            ViewUtils.setButtonStyle(AboutActivity.this.btnBackMiniapp, NativeUIParamsEntity.getInst().getPositiveColor(), NativeUIParamsEntity.getInst().getPositiveItemNegativeTextColor(), NativeUIParamsEntity.getInst().getBtnCornerRadius());
          }
        });
    this.mDebugInfoView = (TextView)findViewById(2097545266);
    if (DebugUtil.debug() && ChannelUtil.isLocalTest()) {
      this.mDebugInfoView.setVisibility(0);
      return;
    } 
    this.mDebugInfoView.setVisibility(8);
    this.tvMiniappName.setOnClickListener(new MultipleTimesClickListener(10, 300L) {
          void onMultipleClick(View param1View) {
            if (AboutActivity.this.mDebugInfoView.getVisibility() == 0)
              return; 
            AboutActivity.this.mDebugInfoView.setVisibility(0);
            AboutActivity.this.tvMiniappName.setClickable(false);
            AboutActivity.this.tvMiniappName.setOnClickListener(null);
          }
        });
  }
  
  private void initdata() {
    String str1;
    this.mAboutInfo = new AboutInfo();
    String str2 = CreateTTCode();
    AppBrandLogger.d("AboutActivity", new Object[] { "ttCode ", str2 });
    if (AppbrandContext.getInst().getInitParams() != null) {
      str1 = AppbrandContext.getInst().getInitParams().getAppId();
    } else {
      str1 = "";
    } 
    String str3 = getIntent().getStringExtra("appid");
    String str4 = getIntent().getStringExtra("bdp_debug_info");
    if (!TextUtils.isEmpty(str4)) {
      TextView textView = this.mDebugInfoView;
      if (textView != null)
        textView.setText(str4); 
    } 
    AppBrandLogger.d("AboutActivity", new Object[] { "appid ", str3 });
    AppBrandLogger.d("AboutActivity", new Object[] { "aid ", str1 });
    requestAboutInfo(str3, str2, str1);
  }
  
  private void requestAboutInfo(final String url, String paramString2, String paramString3) {
    if (TextUtils.isEmpty(url) || TextUtils.isEmpty(paramString2) || TextUtils.isEmpty(paramString3)) {
      AppBrandLogger.e("AboutActivity", new Object[] { "请求参数出错，不能为空" });
      return;
    } 
    this.mAboutInfo.aid = paramString3;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(AppbrandConstant.OpenApi.getInst().getABOUT_URL());
    stringBuilder.append("appid=");
    stringBuilder.append(url);
    stringBuilder.append("&ttcode=");
    stringBuilder.append(paramString2);
    stringBuilder.append("&aid=");
    stringBuilder.append(paramString3);
    url = stringBuilder.toString();
    AppBrandLogger.d("AboutActivity", new Object[] { "url= ", url });
    Observable.create(new Function<Object>() {
          public AboutInfo fun() {
            String str = NetManager.getInst().request(url).a();
            AppBrandLogger.d("AboutActivity", new Object[] { "requestAboutInfo :  url is  ", this.val$url, " & result = ", str });
            AboutActivity aboutActivity = AboutActivity.this;
            aboutActivity.parseAboutInfo(aboutActivity.mAboutInfo, str);
            return AboutActivity.this.mAboutInfo;
          }
        }).schudleOn(Schedulers.longIO()).observeOn(Schedulers.ui()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<AboutInfo>() {
          public void onError(Throwable param1Throwable) {
            AppBrandLogger.e("AboutActivity", new Object[] { "error msg ", param1Throwable });
            HostDependManager hostDependManager = HostDependManager.getInst();
            AboutActivity aboutActivity = AboutActivity.this;
            hostDependManager.showToast((Context)aboutActivity, null, aboutActivity.getResources().getString(2097741959), 0L, null);
          }
          
          public void onSuccess(AboutInfo param1AboutInfo) {
            if (param1AboutInfo == null)
              return; 
            if (!TextUtils.isEmpty(param1AboutInfo.icon)) {
              HostDependManager hostDependManager = HostDependManager.getInst();
              AboutActivity aboutActivity = AboutActivity.this;
              hostDependManager.loadImage((Context)aboutActivity, (ImageView)aboutActivity.ivMiniappIcon, Uri.parse(param1AboutInfo.icon));
            } else {
              AboutActivity.this.ivMiniappIcon.setImageDrawable(AboutActivity.this.getResources().getDrawable(2097479724));
            } 
            if (!TextUtils.isEmpty(param1AboutInfo.name))
              AboutActivity.this.tvMiniappName.setText(param1AboutInfo.name); 
            if (!TextUtils.isEmpty(param1AboutInfo.summary))
              AboutActivity.this.tvMiniappSummary.setText(param1AboutInfo.summary); 
            if (!TextUtils.isEmpty(param1AboutInfo.service_category)) {
              if (!AboutActivity.this.lyServiceCategory.isShown())
                AboutActivity.this.lyServiceCategory.setVisibility(0); 
              AboutActivity.this.tvMiniappCategory.setText(param1AboutInfo.service_category);
            } else if (AboutActivity.this.lyServiceCategory.isShown()) {
              AboutActivity.this.lyServiceCategory.setVisibility(8);
            } 
            if (param1AboutInfo.is_corp && !TextUtils.isEmpty(param1AboutInfo.corp_name)) {
              AboutActivity.this.tvSubjectInfor.setText(param1AboutInfo.corp_name);
            } else if (!param1AboutInfo.is_corp && !TextUtils.isEmpty(param1AboutInfo.id_name)) {
              AboutActivity.this.tvSubjectInfor.setText(AboutActivity.this.getString(2097741986));
            } 
            if (AboutActivity.this.mAboutInfo.mp_list == null) {
              if (AboutActivity.this.lyHeadlinePlatform.isShown())
                AboutActivity.this.lyHeadlinePlatform.setVisibility(8); 
              return;
            } 
            if (!AboutActivity.this.lyHeadlinePlatform.isShown())
              AboutActivity.this.lyHeadlinePlatform.setVisibility(0); 
            if (AboutActivity.this.mAboutInfo.mp_list.size() == 0) {
              if (AboutActivity.this.lyHeadlinePlatform.isShown()) {
                AboutActivity.this.lyHeadlinePlatform.setVisibility(8);
                return;
              } 
            } else {
              int j = AboutActivity.this.mAboutInfo.mp_list.size();
              int i = 1;
              if (j == 1) {
                HostDependManager hostDependManager1 = HostDependManager.getInst();
                AboutActivity aboutActivity1 = AboutActivity.this;
                hostDependManager1.loadImage((Context)aboutActivity1, (ImageView)aboutActivity1.ivHeadlinePlatformIcon0, Uri.parse(((AboutInfo.HeadlinePlatform)param1AboutInfo.mp_list.get(0)).avatar_url));
                if (!AboutActivity.this.tvHeadlinePlatformName.isShown())
                  AboutActivity.this.tvHeadlinePlatformName.setVisibility(0); 
                AboutActivity.this.tvHeadlinePlatformName.setText(param1AboutInfo.name);
                return;
              } 
              AboutActivity.this.tvHeadlinePlatformName.setVisibility(8);
              HostDependManager hostDependManager = HostDependManager.getInst();
              AboutActivity aboutActivity = AboutActivity.this;
              hostDependManager.loadImage((Context)aboutActivity, (ImageView)aboutActivity.ivHeadlinePlatformIcon0, Uri.parse(((AboutInfo.HeadlinePlatform)param1AboutInfo.mp_list.get(0)).avatar_url));
              j = AboutActivity.this.mAboutInfo.mp_list.size();
              while (i < j && i <= 5) {
                RoundedImageView roundedImageView = new RoundedImageView((Context)AboutActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)UIUtils.dip2Px((Context)AboutActivity.this, 24.0F), (int)UIUtils.dip2Px((Context)AboutActivity.this, 24.0F));
                layoutParams.setMargins((int)UIUtils.dip2Px((Context)AboutActivity.this, 12.0F), 0, 0, 0);
                roundedImageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                AboutActivity.this.lyHeadlinePlatformIcon.addView((View)roundedImageView);
                HostDependManager.getInst().loadImage((Context)AboutActivity.this, (ImageView)roundedImageView, Uri.parse(((AboutInfo.HeadlinePlatform)param1AboutInfo.mp_list.get(i)).avatar_url));
                i++;
              } 
            } 
          }
        });
  }
  
  public void finish() {
    super.finish();
    overridePendingTransition(0, UIUtils.getSlideOutAnimation());
  }
  
  public AboutInfo getAboutInfo() {
    return this.mAboutInfo;
  }
  
  protected ImmersedStatusBarHelper.ImmersedStatusBarConfig getImmersedStatusBarConfig() {
    return (new ImmersedStatusBarHelper.ImmersedStatusBarConfig()).setFitsSystemWindows(true).setStatusBarColor(getResources().getColor(2097348664));
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2097676288);
    ImmersedStatusBarHelper immersedStatusBarHelper = new ImmersedStatusBarHelper((Activity)this, getImmersedStatusBarConfig());
    immersedStatusBarHelper.setup(true);
    immersedStatusBarHelper.setUseLightStatusBarInternal(true);
    initView();
    initTitle();
    initdata();
    bindListener();
    LocaleManager.getInst().registerLangChangeListener(this);
  }
  
  public void onLanguageChange() {
    ((TextView)findViewById(2097545358)).setText(getString(2097741832));
    this.tvMiniappName.setText(getString(2097741949));
    this.tvAboutServiceCatagory.setText(getString(2097741836));
    this.tvAboutSubjectInfo.setText(getString(2097741837));
    this.tvAboutHeadlinePlatform.setText(getString(2097741835));
    this.btnBackMiniapp.setText(getString(2097741833));
  }
  
  public void onPause() {
    super.onPause();
  }
  
  public void onResume() {
    super.onResume();
  }
  
  public void parseAboutInfo(AboutInfo paramAboutInfo, String paramString) {
    boolean bool = TextUtils.isEmpty(paramString);
    int i = 0;
    if (bool) {
      AppBrandLogger.e("AboutActivity", new Object[] { "parseAboutInfo json is null" });
      HostDependManager.getInst().showToast((Context)this, null, getResources().getString(2097741959), 0L, null);
      return;
    } 
    try {
      JSONObject jSONObject2 = new JSONObject(paramString);
      if (jSONObject2.optInt("error") != 0)
        return; 
      JSONObject jSONObject1 = new JSONObject(RSAUtil.AESDecrypt(paramAboutInfo.encryKey, paramAboutInfo.encryIV, jSONObject2.getString("data")));
      AppBrandLogger.d("AboutActivity", new Object[] { "data = ", jSONObject1.toString() });
      this.mAboutInfo.icon = jSONObject1.optString("icon");
      this.mAboutInfo.name = jSONObject1.optString("name");
      this.mAboutInfo.summary = jSONObject1.optString("summary");
      this.mAboutInfo.service_category = jSONObject1.optString("service_category");
      this.mAboutInfo.corp_name = jSONObject1.optString("corp_name");
      this.mAboutInfo.id_name = jSONObject1.optString("id_name");
      this.mAboutInfo.version = jSONObject1.optString("version");
      this.mAboutInfo.update_time = jSONObject1.optLong("update_time");
      this.mAboutInfo.is_corp = jSONObject1.optBoolean("is_corp");
      JSONArray jSONArray2 = jSONObject1.getJSONArray("domains");
      if (jSONArray2 != null && jSONArray2.length() != 0) {
        int j = jSONArray2.length();
        this.mAboutInfo.domains = new ArrayList<String>();
        while (i < j) {
          this.mAboutInfo.domains.add(jSONArray2.getString(i));
          i++;
        } 
      } 
      JSONArray jSONArray1 = jSONObject1.getJSONArray("mp_list");
      if (jSONArray1 != null && jSONArray1.length() != 0) {
        jSONArray1.length();
        this.mAboutInfo.mp_list = new ArrayList<AboutInfo.HeadlinePlatform>();
      } 
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
  
  abstract class MultipleTimesClickListener implements View.OnClickListener {
    private long INTERVAL_TIME;
    
    private int TOTAL_TIMES;
    
    private int mClickTimes;
    
    private long mLastClickTime;
    
    private MultipleTimesClickListener(int param1Int, long param1Long) {
      this.TOTAL_TIMES = param1Int;
      this.INTERVAL_TIME = param1Long;
    }
    
    public void onClick(View param1View) {
      long l = SystemClock.elapsedRealtime();
      if (l - this.mLastClickTime <= this.INTERVAL_TIME || this.mClickTimes == 0) {
        this.mLastClickTime = l;
        this.mClickTimes++;
      } else {
        this.mClickTimes = 1;
        this.mLastClickTime = l;
      } 
      if (this.mClickTimes >= this.TOTAL_TIMES) {
        onMultipleClick(param1View);
        this.mClickTimes = 0;
      } 
    }
    
    abstract void onMultipleClick(View param1View);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\about\AboutActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */