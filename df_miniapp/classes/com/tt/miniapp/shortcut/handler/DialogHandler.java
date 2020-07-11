package com.tt.miniapp.shortcut.handler;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.support.v4.content.c;
import com.a;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.net.NetBus;
import com.tt.miniapp.shortcut.ShortcutEventReporter;
import com.tt.miniapp.shortcut.ShortcutPermissionUtil;
import com.tt.miniapp.shortcut.ShortcutResult;
import com.tt.miniapp.shortcut.ShortcutService;
import com.tt.miniapp.shortcut.dialog.DialogConfig;
import com.tt.miniapp.shortcut.dialog.MultiSpanView;
import com.tt.miniapp.shortcut.dialog.ShortcutDialogHandler;
import com.tt.miniapp.shortcut.dialog.SingleSpanView;
import com.tt.miniapp.shortcut.dialog.TextSpan;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.AppbrandUtil;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.ac;
import okhttp3.ae;

public class DialogHandler extends AbsHandler {
  public static ShortcutUrlManager sShortCutGuideManager;
  
  DialogHandler(ShortcutRequest paramShortcutRequest) {
    super(paramShortcutRequest);
    if (sShortCutGuideManager == null)
      sShortCutGuideManager = new ShortcutUrlManager(); 
  }
  
  public void goSetting(Context paramContext) {
    ShortcutPermissionUtil.goSettingPage(paramContext);
  }
  
  protected ShortcutResult handleRequest() {
    AppbrandContext.mainHandler.postDelayed(new Runnable() {
          public void run() {
            if (((ShortcutService)AppbrandApplicationImpl.getInst().getService(ShortcutService.class)).isShowDialog()) {
              DialogHandler dialogHandler = DialogHandler.this;
              dialogHandler.showShortcutDialog(dialogHandler.mAct);
            } 
          }
        },  150L);
    return null;
  }
  
  public void showShortcutDialog(final Activity activity) {
    SingleSpanView singleSpanView = new SingleSpanView(DialogConfig.createDefaultTitle((Context)activity, activity.getString(2097742042)));
    List<TextSpan> list = DialogConfig.createDefaultContent((Context)activity, new String[] { activity.getString(2097742029, new Object[] { AppbrandContext.getInst().getInitParams().getHostStr(101, AppbrandUtil.getApplicationName((Context)AppbrandContext.getInst().getApplicationContext())) }), activity.getString(2097742030) });
    list.add((new TextSpan.Builder()).setText(activity.getString(2097742028)).setTextColor(c.c((Context)activity, 2097348656)).setTextSize(activity.getResources().getDimension(2097414179)).setClickCallback(new DialogConfig.TextClickListener() {
            public void onTextClick(String param1String) {
              ShortcutEventReporter.reportDialogOption("learn_more");
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(DialogHandler.sShortCutGuideManager.getCurrentValidUrl());
              stringBuilder.append("?phoneBrand=");
              stringBuilder.append(ShortcutPermissionUtil.getPhoneBrand());
              stringBuilder.append("&aid=");
              stringBuilder.append(AppbrandContext.getInst().getInitParams().getAppId());
              String str = stringBuilder.toString();
              HostDependManager hostDependManager = HostDependManager.getInst();
              Activity activity = activity;
              hostDependManager.jumpToWebView((Context)activity, str, activity.getString(2097742031), false);
              ShortcutEventReporter.reportLearnMore();
            }
          }).build());
    ShortcutDialogHandler.showDialogByConfig(new DialogConfig(singleSpanView, new MultiSpanView(list, true, 8388611), (Context)activity, new SingleSpanView((new TextSpan.Builder()).setText(activity.getString(2097742024)).setTextColor(c.c((Context)activity, 2097348612)).setTextSize(activity.getResources().getDimension(2097414179)).build()), new SingleSpanView((new TextSpan.Builder()).setText(activity.getString(2097741933)).setTextSize(activity.getResources().getDimension(2097414179)).setTextColor(c.c((Context)activity, 2097348612)).build())), new ShortcutDialogHandler.CallBackListener() {
          public void cancel() {
            ShortcutEventReporter.reportDialogOption("back");
          }
          
          public void confirm() {
            ShortcutEventReporter.reportDialogOption("go_configuration");
            DialogHandler.this.goSetting((Context)activity);
            ((ShortcutService)AppbrandApplicationImpl.getInst().getService(ShortcutService.class)).setOpenSettingPage(true);
          }
          
          public void mobEvent() {
            ShortcutEventReporter.reportDialogShow();
          }
        });
  }
  
  static class ShortcutUrlManager implements LanguageChangeListener {
    public long latestReqeustTime;
    
    public volatile Locale mCurrrentValidLocale = Locale.ENGLISH;
    
    public ShortcutUrlManager() {
      if (HostDependManager.getInst().isEnableI18nNetRequest()) {
        LocaleManager.getInst().registerLangChangeListener(this);
        getLatestI18NLocaleAndVerify();
      } 
    }
    
    private void getLatestI18NLocaleAndVerify() {
      Locale locale = LocaleManager.getInst().getCurrentHostSetLocale();
      if (locale == null)
        return; 
      AppbrandConstant.OpenApi.getInst();
      testUrl(a.a("https://s.ipstatp.com/fe_toutiao/helo_instruction_page/%1$s/", new Object[] { locale.getLanguage() }), locale);
    }
    
    private void testUrl(final String url, final Locale locale) {
      this.latestReqeustTime = SystemClock.elapsedRealtime();
      ThreadUtil.runOnWorkThread(new Action() {
            public void act() {
              try {
                ac ac = (new ac.a()).b().a(url).c();
                ae ae = NetBus.okHttpClient.c().a(5000L, TimeUnit.MILLISECONDS).c(5000L, TimeUnit.MILLISECONDS).b(5000L, TimeUnit.MILLISECONDS).a().a(ac).b();
                if (ae == null)
                  return; 
                if (ae.c != 200)
                  return; 
                if (requestTimestamp < DialogHandler.ShortcutUrlManager.this.latestReqeustTime)
                  return; 
                DialogHandler.ShortcutUrlManager.this.mCurrrentValidLocale = locale;
                return;
              } catch (IOException iOException) {
                AppBrandLogger.e("ShortcutUrlManager", new Object[] { iOException });
                return;
              } 
            }
          }Schedulers.shortIO());
    }
    
    public String getCurrentValidUrl() {
      if (!HostDependManager.getInst().isEnableI18nNetRequest()) {
        AppbrandConstant.OpenApi.getInst();
        return "https://tmaportal.snssdk.com/jssdk/h5/add_to_desktop_learn_more/";
      } 
      AppbrandConstant.OpenApi.getInst();
      return a.a("https://s.ipstatp.com/fe_toutiao/helo_instruction_page/%1$s/", new Object[] { this.mCurrrentValidLocale.getLanguage() });
    }
    
    public void onLanguageChange() {
      getLatestI18NLocaleAndVerify();
    }
  }
  
  class null implements Action {
    public void act() {
      try {
        ac ac = (new ac.a()).b().a(url).c();
        ae ae = NetBus.okHttpClient.c().a(5000L, TimeUnit.MILLISECONDS).c(5000L, TimeUnit.MILLISECONDS).b(5000L, TimeUnit.MILLISECONDS).a().a(ac).b();
        if (ae == null)
          return; 
        if (ae.c != 200)
          return; 
        if (requestTimestamp < this.this$0.latestReqeustTime)
          return; 
        this.this$0.mCurrrentValidLocale = locale;
        return;
      } catch (IOException iOException) {
        AppBrandLogger.e("ShortcutUrlManager", new Object[] { iOException });
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\handler\DialogHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */