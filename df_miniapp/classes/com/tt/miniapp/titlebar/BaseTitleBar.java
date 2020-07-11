package com.tt.miniapp.titlebar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.c;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.frontendapiinterface.e;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.exit.AppBrandExitManager;
import com.tt.miniapp.guide.ReenterGuideHelper;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.secrecy.ui.SecrecyUIHelper;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.view.TitleBarProgressView;
import com.tt.miniapp.view.swipeback.EventParamsValue;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.FeignHostConfig;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.UIUtils;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public abstract class BaseTitleBar implements ITitleBar, LanguageChangeListener {
  public static ITitleBar EMPTY = new ITitleBar() {
      public final View getTitleView() {
        return null;
      }
      
      public final void performMoreButtonClick() {
        throw new IllegalStateException("disable to do this action when Fragment not render");
      }
      
      public final void setTitleBarCapsuleVisible(boolean param1Boolean) {
        TitleBarControl.getInst().updateCapsuleButtonState(param1Boolean);
      }
    };
  
  protected int mAlpha;
  
  protected ImageView mBackHomeButton;
  
  protected LinearLayout mBackHomeButtonContainer;
  
  protected String mBackgroundColor;
  
  protected String mBarTextStyle;
  
  protected Context mContext;
  
  protected boolean mInited;
  
  protected boolean mIsCustomNavigation;
  
  protected ImageView mPageCloseButton;
  
  protected String mPageText;
  
  protected TextView mPageTitle;
  
  protected boolean mReparentTitleBarForCustom;
  
  protected String mStyle;
  
  protected int mTitleBackgroundColor;
  
  protected TitleBarProgressView mTitleBarProgressBar;
  
  protected ImageView mTitleMenuMore;
  
  protected View mTitleView;
  
  protected LinearLayout mTitlebarCapsule;
  
  protected ImageView mTitlebarDot;
  
  protected View mTitlebarMiddleLine;
  
  protected String mTransparentTitle;
  
  protected final ViewGroup mView;
  
  protected boolean mWebViewShowBack;
  
  protected String settingPageTitle;
  
  public BaseTitleBar(Context paramContext, ViewGroup paramViewGroup) {
    this.mView = paramViewGroup;
    this.mContext = paramContext;
    findView();
    LocaleManager.getInst().registerLangChangeListener(this);
    AppBrandLogger.d("tma_BaseTitleBar", new Object[] { Boolean.valueOf(UIUtils.isRTL()) });
    UIUtils.setProperLayoutDirection((View)this.mView);
    TitleBarControl.getInst().addBaseTitleBar(this);
  }
  
  private void bindView(String paramString) {
    int i;
    String str1 = getPageTitleText(paramString);
    String str2 = getGlobalTitleText();
    if (str1 != null) {
      this.settingPageTitle = str1;
      this.mPageTitle.setText(str1);
    } else if (str2 != null) {
      this.mPageTitle.setText(str2);
    } else {
      this.mPageTitle.setText("");
    } 
    this.mBarTextStyle = getNavigationBarTextStyle(paramString);
    AppBrandLogger.d("tma_BaseTitleBar", new Object[] { "getNavigationBarTitleText ", this.mPageTitle.getText() });
    try {
      this.mTitleBackgroundColor = Color.parseColor(UIUtils.rgbaToFullARGBStr(getNavigationBarBackgroundColor(paramString), "#000000"));
    } catch (Exception exception) {
      AppBrandLogger.e("tma_BaseTitleBar", new Object[] { "解析颜色字符串失败", exception });
      this.mTitleBackgroundColor = c.c(this.mContext, 2097348611);
    } 
    this.mTitleView.setBackgroundColor(this.mTitleBackgroundColor);
    if (this.mIsCustomNavigation) {
      this.mPageCloseButton.setVisibility(8);
      this.mPageTitle.setVisibility(8);
      this.mTitleView.setBackgroundColor(Color.parseColor("#00000000"));
    } else if (!TextUtils.equals(this.mTransparentTitle, "none")) {
      this.mTitleView.getBackground().setAlpha(this.mAlpha);
    } 
    boolean bool = isBlackStyle();
    TextView textView = this.mPageTitle;
    if (bool) {
      i = Color.parseColor("#000000");
    } else {
      i = Color.parseColor("#ffffff");
    } 
    textView.setTextColor(i);
    ImageView imageView = this.mPageCloseButton;
    if (bool) {
      i = -14540254;
    } else {
      i = -1;
    } 
    imageView.setColorFilter(i);
    feignHostTitleBar();
  }
  
  private void feignHostTitleBar() {
    int i;
    int j;
    boolean bool;
    int k;
    Drawable drawable2;
    Drawable drawable3;
    Drawable drawable4;
    Integer integer1;
    Integer integer2;
    boolean bool1 = isBlackStyle();
    FeignHostConfig feignHostConfig = FeignHostConfig.inst();
    if (bool1) {
      drawable1 = feignHostConfig.getAppCapsuleBlackDrawable();
    } else {
      drawable1 = feignHostConfig.getAppCapsuleDrawable();
    } 
    if (bool1) {
      drawable2 = feignHostConfig.getAppMoreMenuBlackDrawable();
    } else {
      drawable2 = feignHostConfig.getAppMoreMenuDrawable();
    } 
    if (bool1) {
      integer1 = feignHostConfig.getAppBlackIconColor();
    } else {
      integer1 = feignHostConfig.getAppIconColor();
    } 
    if (bool1) {
      drawable3 = feignHostConfig.getAppCloseIconBlackDrawable();
    } else {
      drawable3 = feignHostConfig.getAppCloseIconDrawable();
    } 
    if (bool1) {
      integer2 = feignHostConfig.getAppBlackIconColor();
    } else {
      integer2 = feignHostConfig.getAppIconColor();
    } 
    if (drawable1 == null) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool1) {
      k = this.mContext.getResources().getColor(2097348675);
    } else {
      k = this.mContext.getResources().getColor(2097348687);
    } 
    if (bool1) {
      drawable4 = c.a(this.mContext, 2097479794);
    } else {
      drawable4 = c.a(this.mContext, 2097479793);
    } 
    Drawable drawable7 = c.a(this.mContext, 2097479802);
    if (bool1) {
      i = -14540254;
    } else {
      i = -1;
    } 
    Drawable drawable6 = c.a(this.mContext, 2097479796);
    if (bool1) {
      j = -14540254;
    } else {
      j = -1;
    } 
    Drawable drawable5 = drawable1;
    if (drawable1 == null)
      drawable5 = drawable4; 
    this.mTitlebarCapsule.setBackground(drawable5);
    if (!bool) {
      this.mTitlebarMiddleLine.setVisibility(8);
    } else {
      this.mTitlebarMiddleLine.setVisibility(0);
      this.mTitlebarMiddleLine.setBackgroundColor(k);
    } 
    Drawable drawable1 = drawable2;
    if (drawable2 == null)
      drawable1 = drawable7; 
    if (integer1 != null)
      i = integer1.intValue(); 
    this.mTitleMenuMore.setImageDrawable(drawable1);
    this.mTitleMenuMore.setColorFilter(i);
    drawable1 = drawable3;
    if (drawable3 == null)
      drawable1 = drawable6; 
    if (integer2 != null)
      j = integer2.intValue(); 
    this.mTitlebarDot.setImageDrawable(drawable1);
    this.mTitlebarDot.setColorFilter(j);
  }
  
  private void findView() {
    this.mTitleView = this.mView.findViewById(2097545400);
    this.mPageCloseButton = (ImageView)this.mTitleView.findViewById(2097545355);
    this.mPageTitle = (TextView)this.mTitleView.findViewById(2097545358);
    this.mTitlebarCapsule = (LinearLayout)this.mTitleView.findViewById(2097545404);
    this.mTitlebarDot = (ImageView)this.mTitleView.findViewById(2097545405);
    this.mTitlebarMiddleLine = this.mTitleView.findViewById(2097545407);
    this.mBackHomeButton = (ImageView)this.mTitleView.findViewById(2097545411);
    this.mBackHomeButtonContainer = (LinearLayout)this.mTitleView.findViewById(2097545412);
    this.mTitleBarProgressBar = (TitleBarProgressView)this.mTitleView.findViewById(2097545414);
    this.mTitleMenuMore = (ImageView)this.mTitleView.findViewById(2097545408);
  }
  
  private boolean isBlackStyle() {
    return TextUtils.equals(this.mBarTextStyle, "black");
  }
  
  private void makeStatusBar(String paramString) {
    if (this.mInited) {
      if (getActivity() == null)
        return; 
      boolean bool = TextUtils.equals(paramString, "black");
      if (isOverM())
        ImmersedStatusBarHelper.setUseLightStatusBar(getActivity().getWindow(), bool); 
      updateLeftBackHomeView();
      if (this.mTitleBarProgressBar.isLoading() && (!this.mTitleBarProgressBar.isDarkMode() || !bool))
        this.mTitleBarProgressBar.updateLoading(bool); 
    } 
  }
  
  private void reparentTitleBarForCustom() {
    this.mReparentTitleBarForCustom = true;
    UIUtils.removeParentView(this.mTitleView);
    ((RelativeLayout)this.mView.findViewById(2097545265)).addView(this.mTitleView);
  }
  
  private void showLeftHomeView() {
    this.mBackHomeButtonContainer.setVisibility(0);
    updateLeftBackHomeView();
  }
  
  private void updateLeftBackHomeView() {
    if (!this.mIsCustomNavigation && this.mBackHomeButtonContainer.getVisibility() == 0) {
      if (getActivity() == null)
        return; 
      this.mBackHomeButton.setImageDrawable(getActivity().getResources().getDrawable(2097479798));
      if (isBlackStyle()) {
        this.mBackHomeButton.setBackground(getActivity().getResources().getDrawable(2097479794));
        this.mBackHomeButton.setColorFilter(-15066598);
        return;
      } 
      this.mBackHomeButton.setBackground(getActivity().getResources().getDrawable(2097479793));
      this.mBackHomeButton.setColorFilter(-1);
    } 
  }
  
  public void autoTransparentTitleBar(int paramInt) {
    if (!this.mIsCustomNavigation && TextUtils.equals(this.mTransparentTitle, "auto")) {
      if (getTitleBarHeight() <= 0)
        return; 
      AppBrandLogger.d("tma_BaseTitleBar", new Object[] { Integer.valueOf(paramInt) });
      int i = getTitleBarHeight() * 2;
      if (paramInt < i) {
        this.mAlpha = paramInt * 255 / i;
      } else {
        this.mAlpha = 255;
      } 
      if (paramInt < i / 2) {
        makeStatusBar(this.mBarTextStyle);
      } else if (isBlackStyle()) {
        makeStatusBar("white");
      } else {
        makeStatusBar("black");
      } 
      this.mTitleView.getBackground().setAlpha(this.mAlpha);
    } 
  }
  
  public void destroy() {
    TitleBarControl.getInst().removeBaseTitleBar(this);
  }
  
  protected void exitInternal() {
    Runnable runnable = new Runnable() {
        public void run() {
          ToolUtils.onActivityExit(BaseTitleBar.this.getActivity(), 2);
        }
      };
    ReenterGuideHelper.checkReenterGuideTip(getActivity(), runnable);
  }
  
  public abstract Activity getActivity();
  
  public String getBarTextStyle() {
    return this.mBarTextStyle;
  }
  
  protected abstract String getGlobalTitleText();
  
  protected String getLegalTransParentTitle(String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual hashCode : ()I
    //   4: istore_2
    //   5: iload_2
    //   6: ldc_w -1414557169
    //   9: if_icmpeq -> 37
    //   12: iload_2
    //   13: ldc_w 3005871
    //   16: if_icmpeq -> 22
    //   19: goto -> 52
    //   22: aload_1
    //   23: ldc_w 'auto'
    //   26: invokevirtual equals : (Ljava/lang/Object;)Z
    //   29: ifeq -> 52
    //   32: iconst_1
    //   33: istore_2
    //   34: goto -> 54
    //   37: aload_1
    //   38: ldc_w 'always'
    //   41: invokevirtual equals : (Ljava/lang/Object;)Z
    //   44: ifeq -> 52
    //   47: iconst_0
    //   48: istore_2
    //   49: goto -> 54
    //   52: iconst_m1
    //   53: istore_2
    //   54: iload_2
    //   55: ifeq -> 70
    //   58: iload_2
    //   59: iconst_1
    //   60: if_icmpeq -> 66
    //   63: ldc 'none'
    //   65: areturn
    //   66: ldc_w 'auto'
    //   69: areturn
    //   70: ldc_w 'always'
    //   73: areturn
  }
  
  protected abstract String getNavigationBarBackgroundColor(String paramString);
  
  protected abstract String getNavigationBarTextStyle(String paramString);
  
  protected abstract String getNavigationStyle(String paramString);
  
  protected abstract String getNavigationTransparentTitle(String paramString);
  
  protected abstract String getPageTitleText(String paramString);
  
  public int getTitleBarHeight() {
    return this.mIsCustomNavigation ? 0 : this.mTitleView.getMeasuredHeight();
  }
  
  public View getTitleView() {
    return this.mTitleView;
  }
  
  public void hideBackHomeButton() {
    if (this.mIsCustomNavigation)
      return; 
    this.mBackHomeButtonContainer.setVisibility(8);
  }
  
  public void initView(String paramString) {
    if (TextUtils.equals(getNavigationStyle(paramString), "custom"))
      this.mIsCustomNavigation = true; 
    this.mTransparentTitle = getNavigationTransparentTitle(paramString);
    if (this.mIsCustomNavigation) {
      reparentTitleBarForCustom();
    } else if (!TextUtils.equals(this.mTransparentTitle, "none")) {
      reparentTitleBarForCustom();
      this.mAlpha = 0;
    } else {
      this.mAlpha = 255;
    } 
    updateStatusBarVisible(TitleBarControl.getInst().isShowStatusBar());
    updateCapsuleButtonVisible(TitleBarControl.getInst().isShowCapsuleButton());
    this.mPageCloseButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            e e = AppbrandApplication.getInst().getActivityLife();
            if (e != null)
              e.goback(); 
          }
        });
    this.mTitleBarProgressBar.bindTitle(this.mPageTitle);
    this.mTitlebarDot.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            AppbrandApplicationImpl.getInst().setStopReason("click_close_btn");
            EventParamsValue.PARAMS_EXIT_TYPE = "btn";
            EventParamsValue.IS_OTHER_FLAG = false;
            if (ApiPermissionManager.shouldCallbackBeforeClose()) {
              AppBrandExitManager.getInst().onBeforeExit(false, new Runnable() {
                    public void run() {
                      BaseTitleBar.this.exitInternal();
                    }
                  });
              return;
            } 
            BaseTitleBar.this.exitInternal();
          }
        });
    List list = AppbrandContext.getInst().getTitleMenuItems();
    if (list != null && list.size() > 0 && HostDependManager.getInst().isTitlebarMoreMenuVisible()) {
      this.mTitleMenuMore.setVisibility(0);
    } else {
      this.mTitleMenuMore.setVisibility(4);
    } 
    this.mTitleMenuMore.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MenuDialog.inst(BaseTitleBar.this.getActivity()).setShowBackHome(BaseTitleBar.this.isShowBackHomeInMenuDialog()).show();
            if (AppbrandApplicationImpl.getInst().getAppInfo() != null)
              Event.builder("mp_more_btn_click").flush(); 
          }
        });
    AppBrandLogger.d("tma_BaseTitleBar", new Object[] { "initView " });
    bindView(paramString);
    SecrecyUIHelper.inst().registerView(this.mTitleMenuMore);
    this.mBackHomeButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            BaseTitleBar.this.onClickBackHomeButton();
          }
        });
    this.mInited = true;
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle() && !AppbrandContext.getInst().getCurrentActivity().isFilledUpContainer())
      TitleBarControl.showTitlebarRadius(this); 
  }
  
  protected abstract boolean isBottomPage();
  
  public boolean isOverM() {
    return (Build.VERSION.SDK_INT >= 23);
  }
  
  protected abstract boolean isShowBackHomeInMenuDialog();
  
  protected abstract boolean isShowLeftBackHomeView();
  
  public boolean isTouchPointInTitleBar(MotionEvent paramMotionEvent) {
    if (this.mReparentTitleBarForCustom) {
      ViewParent viewParent = this.mTitleView.getParent();
      if (viewParent instanceof View)
        return UIUtils.isTouchPointInView((View)viewParent, paramMotionEvent); 
    } 
    return UIUtils.isTouchPointInView(this.mTitleView, paramMotionEvent);
  }
  
  public boolean isVisible() {
    return (this.mView.getParent() != null);
  }
  
  public void makeStatusBar() {
    makeStatusBar(this.mBarTextStyle);
  }
  
  protected abstract void onClickBackHomeButton();
  
  public void onLanguageChange() {
    StringBuilder stringBuilder = new StringBuilder("onLanguageChange:RTL:");
    stringBuilder.append(UIUtils.isRTL());
    AppBrandLogger.d("tma_BaseTitleBar", new Object[] { stringBuilder.toString() });
    UIUtils.setProperLayoutDirection((View)this.mView);
  }
  
  public void performMoreButtonClick() {
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            View view = BaseTitleBar.this.mView.findViewById(2097545408);
            if (view != null) {
              view.performClick();
              return;
            } 
            DebugUtil.logOrThrow("tma_BaseTitleBar", new Object[] { "capsuleMoreButton is null" });
          }
        });
  }
  
  public void setFirstRenderData(JSONObject paramJSONObject) {
    if (paramJSONObject == null)
      return; 
    this.mStyle = paramJSONObject.optString("navigationStyle");
    this.mPageText = paramJSONObject.optString("navigationBarTitleText");
    this.mBarTextStyle = paramJSONObject.optString("navigationBarTextStyle");
    this.mBackgroundColor = paramJSONObject.optString("navigationBarBackgroundColor");
    this.mTransparentTitle = paramJSONObject.optString("transparentTitle");
  }
  
  public void setNavigationBarColor(String paramString1, String paramString2) {
    if (TextUtils.equals(paramString1, "#000000")) {
      this.mBarTextStyle = "black";
    } else if (TextUtils.equals(paramString1, "#ffffff")) {
      this.mBarTextStyle = "white";
    } 
    if (!this.mIsCustomNavigation) {
      this.mTitleBackgroundColor = UIUtils.parseColor(paramString2, "#000000");
      this.mTitleView.setBackgroundColor(this.mTitleBackgroundColor);
      this.mTitleView.getBackground().setAlpha(this.mAlpha);
    } 
    boolean bool = isBlackStyle();
    TextView textView = this.mPageTitle;
    byte b = -1;
    if (bool) {
      i = -16777216;
    } else {
      i = -1;
    } 
    textView.setTextColor(i);
    ImageView imageView = this.mPageCloseButton;
    if (bool) {
      i = -14540254;
    } else {
      i = -1;
    } 
    imageView.setColorFilter(i);
    imageView = this.mTitlebarDot;
    if (bool) {
      i = -14540254;
    } else {
      i = -1;
    } 
    imageView.setColorFilter(i);
    imageView = this.mTitleMenuMore;
    int i = b;
    if (bool)
      i = -14540254; 
    imageView.setColorFilter(i);
    makeStatusBar();
  }
  
  public void setNavigationBarTitleText(String paramString) {
    if (!TextUtils.isEmpty(paramString))
      this.settingPageTitle = paramString; 
    if (!UIUtils.isViewVisible((View)this.mPageTitle))
      UIUtils.setViewVisibility((View)this.mPageTitle, 0); 
    this.mPageTitle.setText(paramString);
  }
  
  public void setPageCloseButtonVisible(boolean paramBoolean) {
    Iterator<WeakReference<BaseTitleBar>> iterator = TitleBarControl.sTitleBarList.iterator();
    while (iterator.hasNext()) {
      BaseTitleBar baseTitleBar = ((WeakReference<BaseTitleBar>)iterator.next()).get();
      if (baseTitleBar != null) {
        ImageView imageView = baseTitleBar.mPageCloseButton;
        byte b = 0;
        if (imageView != null) {
          if (!paramBoolean)
            b = 4; 
          imageView.setVisibility(b);
          imageView.requestLayout();
          continue;
        } 
        DebugUtil.logOrThrow("tma_BaseTitleBar", new Object[] { "appbrandTitleBar.mPageCloseButton is null" });
      } 
    } 
  }
  
  public void setTitleBarCapsuleVisible(boolean paramBoolean) {
    TitleBarControl.getInst().updateCapsuleButtonState(paramBoolean);
  }
  
  public void setTitleBarLoading(boolean paramBoolean) {
    if (this.mIsCustomNavigation)
      return; 
    if (paramBoolean) {
      this.mTitleBarProgressBar.showLoading(isBlackStyle());
      return;
    } 
    this.mTitleBarProgressBar.hideLoading();
  }
  
  public void setTitleBarRadius(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    GradientDrawable gradientDrawable;
    Drawable drawable = this.mTitleView.getBackground();
    if (drawable == null) {
      gradientDrawable = new GradientDrawable();
    } else if (drawable instanceof ColorDrawable) {
      gradientDrawable = new GradientDrawable();
      gradientDrawable.setColor(((ColorDrawable)drawable).getColor());
    } else if (drawable instanceof GradientDrawable) {
      gradientDrawable = (GradientDrawable)drawable;
    } else {
      gradientDrawable = new GradientDrawable();
    } 
    gradientDrawable.setShape(0);
    gradientDrawable.setCornerRadii(new float[] { paramFloat1, paramFloat1, paramFloat2, paramFloat2, paramFloat3, paramFloat3, paramFloat4, paramFloat4 });
    this.mTitleView.setBackground((Drawable)gradientDrawable);
  }
  
  public void setVisibility(boolean paramBoolean) {
    byte b;
    View view = this.mTitleView;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 8;
    } 
    view.setVisibility(b);
  }
  
  public void setWebViewPageBackView(boolean paramBoolean) {
    this.mWebViewShowBack = paramBoolean;
    updateLeftViewVisibility();
  }
  
  public void updateCapsuleButtonVisible(boolean paramBoolean) {
    LinearLayout linearLayout = this.mTitlebarCapsule;
    byte b = 0;
    if (linearLayout != null) {
      if (!paramBoolean)
        b = 4; 
      linearLayout.setVisibility(b);
      linearLayout.requestLayout();
      return;
    } 
    DebugUtil.logOrThrow("tma_BaseTitleBar", new Object[] { "appbrandTitleBar.mTitlebarCapsule is null" });
  }
  
  public void updateLeftViewVisibility() {
    if (this.mIsCustomNavigation)
      return; 
    if (!isBottomPage() || this.mWebViewShowBack) {
      this.mPageCloseButton.setVisibility(0);
      hideBackHomeButton();
      return;
    } 
    if (isShowLeftBackHomeView()) {
      this.mPageCloseButton.setVisibility(8);
      showLeftHomeView();
      return;
    } 
    this.mPageCloseButton.setVisibility(8);
    hideBackHomeButton();
  }
  
  public void updateStatusBarVisible(boolean paramBoolean) {
    if (paramBoolean) {
      UIUtils.configTitleBarWithHeight(this.mContext, this.mTitleView);
      this.mTitleView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            public WindowInsets onApplyWindowInsets(View param1View, WindowInsets param1WindowInsets) {
              UIUtils.configTitleBarWithHeight(BaseTitleBar.this.mContext, BaseTitleBar.this.mTitleView);
              return param1View.onApplyWindowInsets(param1WindowInsets);
            }
          });
      return;
    } 
    UIUtils.configTitleBarWithOriHeight(this.mContext, this.mTitleView);
    this.mTitleView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
          public WindowInsets onApplyWindowInsets(View param1View, WindowInsets param1WindowInsets) {
            UIUtils.configTitleBarWithOriHeight(BaseTitleBar.this.mContext, BaseTitleBar.this.mTitleView);
            return param1View.onApplyWindowInsets(param1WindowInsets);
          }
        });
  }
  
  public void updateWebTitle(String paramString, boolean paramBoolean) {
    if (this.mIsCustomNavigation) {
      AppBrandLogger.d("tma_BaseTitleBar", new Object[] { "custom title bar, h5 title invalid" });
      return;
    } 
    if (this.settingPageTitle != null && !paramBoolean) {
      AppBrandLogger.d("tma_BaseTitleBar", new Object[] { "page has title, h5 title invalid" });
      paramString = this.settingPageTitle;
      if (paramString != null) {
        this.mPageTitle.setText(paramString);
        return;
      } 
      this.mPageTitle.setText(getGlobalTitleText());
      return;
    } 
    if (!UIUtils.isViewVisible((View)this.mPageTitle))
      UIUtils.setViewVisibility((View)this.mPageTitle, 0); 
    this.mPageTitle.setText(paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlebar\BaseTitleBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */