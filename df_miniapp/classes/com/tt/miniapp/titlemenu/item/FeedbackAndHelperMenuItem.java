package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.feedback.entrance.FAQActivity;
import com.tt.miniapp.feedback.entrance.vo.FeedbackParam;
import com.tt.miniapp.feedback.report.ReportHelper;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapp.util.PageUtil;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.option.h.b;
import com.tt.option.q.d;
import org.json.JSONObject;

public class FeedbackAndHelperMenuItem extends MenuItemAdapter {
  private MenuItemView mItemView;
  
  public FeedbackAndHelperMenuItem(final Activity activity) {
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(activity.getDrawable(2097479734));
    this.mItemView.setLabel(activity.getString(ReportHelper.getTitleResId()));
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MenuDialog.inst(activity).dismiss();
            Event.builder("mp_feedback_click").flush();
            InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
            AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
            b b = new b(appInfoEntity.appId, appInfoEntity.type, appInfoEntity.appName, appInfoEntity.version, appInfoEntity.versionType);
            if (HostDependManager.getInst().feedbackIntercept(activity, b))
              return; 
            Intent intent = FeedbackAndHelperMenuItem.buildFAQIntent((Context)activity, -1L, initParamsEntity, appInfoEntity);
            if (intent != null)
              activity.startActivity(intent); 
          }
        });
  }
  
  public static Intent buildFAQIntent(Context paramContext, long paramLong) {
    return buildFAQIntent(paramContext, paramLong, AppbrandContext.getInst().getInitParams(), AppbrandApplication.getInst().getAppInfo());
  }
  
  public static Intent buildFAQIntent(Context paramContext, long paramLong, InitParamsEntity paramInitParamsEntity, AppInfoEntity paramAppInfoEntity) {
    if (paramInitParamsEntity != null && paramAppInfoEntity != null) {
      FeedbackParam feedbackParam = new FeedbackParam();
      if (paramAppInfoEntity.isGame()) {
        feedbackParam.setType(2);
        feedbackParam.setFeedbackAid("1234567891");
        feedbackParam.setFeedbackAppkey("microgame-android");
        feedbackParam.setFeedbackAppName("microgame");
      } else {
        feedbackParam.setType(1);
        feedbackParam.setFeedbackAppName("microapp");
        feedbackParam.setFeedbackAid("1234567890");
        feedbackParam.setFeedbackAppkey("microapp-android");
      } 
      feedbackParam.setTtId(paramAppInfoEntity.ttId);
      feedbackParam.setLaunchFrom(paramAppInfoEntity.launchFrom);
      String[] arrayOfString = PageUtil.getPathAndQuery(AppbrandApplicationImpl.getInst().getCurrentPageUrl());
      feedbackParam.setPath(arrayOfString[0]);
      feedbackParam.setQuery(arrayOfString[1]);
      feedbackParam.setVersionType(paramAppInfoEntity.versionType);
      feedbackParam.setAid(paramAppInfoEntity.appId);
      feedbackParam.setAppName(paramAppInfoEntity.appName);
      if (!d.a(d.a)) {
        if (AppbrandContext.getInst().getInitParams() != null)
          d.a = AppbrandContext.getInst().getInitParams().getInstallId(); 
        if (!d.a(d.a)) {
          JSONObject jSONObject = HostProcessBridge.getNetCommonParams();
          if (jSONObject != null)
            d.a = jSONObject.optString("iid", "0"); 
        } 
      } 
      feedbackParam.setIid(d.a);
      feedbackParam.setChannel(paramInitParamsEntity.getChannel());
      feedbackParam.setDeviceId(d.a());
      feedbackParam.setHostAid(paramInitParamsEntity.getAppId());
      feedbackParam.setHostAppName(paramInitParamsEntity.getAppName());
      feedbackParam.setHostAppKey(paramInitParamsEntity.getFeedbackAppKey());
      feedbackParam.setHostAppVersion(paramInitParamsEntity.getVersionCode());
      feedbackParam.setHostAppUpdateVersion(paramInitParamsEntity.getUpdateVersionCode());
      return FAQActivity.genIntent(paramContext, feedbackParam, paramAppInfoEntity, paramLong);
    } 
    return null;
  }
  
  public final String getId() {
    return "feedback_and_helper";
  }
  
  public MenuItemView getView() {
    return this.mItemView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\FeedbackAndHelperMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */