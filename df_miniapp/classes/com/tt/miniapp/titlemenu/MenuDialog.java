package com.tt.miniapp.titlemenu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.c;
import android.support.v4.f.a;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.adsite.AdSiteManager;
import com.tt.miniapp.secrecy.ui.SecrecyUIHelper;
import com.tt.miniapp.titlemenu.Indicator.CircleNavigator;
import com.tt.miniapp.titlemenu.Indicator.IPagerNavigator;
import com.tt.miniapp.titlemenu.Indicator.MagicIndicator;
import com.tt.miniapp.titlemenu.item.AboutMenuItem;
import com.tt.miniapp.titlemenu.item.BackHomeMenuItem;
import com.tt.miniapp.titlemenu.item.FavoriteMiniAppMenuItem;
import com.tt.miniapp.titlemenu.item.FeedbackAndHelperMenuItem;
import com.tt.miniapp.titlemenu.item.IMenuItem;
import com.tt.miniapp.titlemenu.item.ProjectModeMenuItem;
import com.tt.miniapp.titlemenu.item.RecordProblemMenuItem;
import com.tt.miniapp.titlemenu.item.RestartMiniAppMenuItem;
import com.tt.miniapp.titlemenu.item.SeeProfileMenuItem;
import com.tt.miniapp.titlemenu.item.SettingsMenuItem;
import com.tt.miniapp.titlemenu.item.ShareMenuItem;
import com.tt.miniapp.titlemenu.item.ShortcutMenuItem;
import com.tt.miniapp.titlemenu.item.TimelineGraphMenuItem;
import com.tt.miniapp.titlemenu.item.VConsonleMenuItem;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapp.titlemenu.view.MenuPagerAdapter;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.UIUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MenuDialog extends Dialog implements LanguageChangeListener {
  private static MenuDialog sInst;
  
  private Activity mActivity;
  
  private List<IMenuItem> mCurrentMenuItemsVO;
  
  private final List<IMenuItem> mDefaultMenuItemDOList = new ArrayList<IMenuItem>();
  
  private final List<Class> mEssentialMenuItemList = (List)new ArrayList<Class<?>>();
  
  private boolean mIsShowBackHome;
  
  private final List<Class> mSDKMenuItemList = (List)new ArrayList<Class<?>>();
  
  private SettingsMenuItem mSettingsMenuItem;
  
  private MenuDialog(Activity paramActivity, int paramInt) {
    super((Context)paramActivity, paramInt);
    this.mActivity = paramActivity;
    LocaleManager.getInst().registerLangChangeListener((LanguageChangeListener)this);
    initEssentialMenuItemList();
    initSDKMenuItemList();
    initDefaultMenuItemList(paramActivity);
  }
  
  private TextView generateCancelTextView(Context paramContext, int paramInt) {
    TextView textView = new TextView(paramContext);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, paramInt);
    layoutParams.addRule(12);
    textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    textView.setBackgroundColor(-1);
    textView.setTextColor(-16777216);
    textView.setTextSize(16.0F);
    textView.setText(paramContext.getString(2097741955));
    textView.setGravity(17);
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MenuDialog.this.dismiss();
          }
        });
    return textView;
  }
  
  private MagicIndicator generateIndicator(Context paramContext, int paramInt, ViewPager paramViewPager) {
    int i;
    CircleNavigator circleNavigator = new CircleNavigator(paramContext);
    circleNavigator.setSelectedColor(c.c(paramContext, 2097348616));
    circleNavigator.setUnselectedColor(Color.parseColor("#1A000000"));
    if (paramViewPager.getAdapter() == null) {
      i = 0;
    } else {
      i = paramViewPager.getAdapter().getCount();
    } 
    circleNavigator.setCircleCount(i);
    final MagicIndicator magicIndicator = new MagicIndicator(paramContext);
    magicIndicator.setNavigator((IPagerNavigator)circleNavigator);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
    layoutParams.addRule(14);
    layoutParams.addRule(12);
    layoutParams.bottomMargin = (int)(paramInt + UIUtils.dip2Px(paramContext, 20.0F));
    magicIndicator.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    paramViewPager.addOnPageChangeListener(new ViewPager.e() {
          public void onPageScrollStateChanged(int param1Int) {
            magicIndicator.onPageScrollStateChanged(param1Int);
          }
          
          public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {
            magicIndicator.onPageScrolled(param1Int1, param1Float, param1Int2);
          }
          
          public void onPageSelected(int param1Int) {
            magicIndicator.onPageSelected(param1Int);
          }
        });
    return magicIndicator;
  }
  
  private GradientDrawable generateMenuBackground(Context paramContext) {
    int i = (int)UIUtils.dip2Px(paramContext, NativeUIParamsEntity.getInst().getMorePanelLandScapeCornerRadius());
    if ((paramContext.getResources().getConfiguration()).orientation == 1)
      i = (int)UIUtils.dip2Px(paramContext, NativeUIParamsEntity.getInst().getMorePanelPortraitCornerRadius()); 
    GradientDrawable gradientDrawable = new GradientDrawable();
    float f = i;
    gradientDrawable.setCornerRadii(new float[] { f, f, f, f, 0.0F, 0.0F, 0.0F, 0.0F });
    gradientDrawable.setColor(c.c(paramContext, 2097348645));
    return gradientDrawable;
  }
  
  private void generateMenuDialogView() {
    boolean bool;
    Context context = getContext();
    List<IMenuItem> list = generateMenuItemVOList();
    int i = (int)UIUtils.dip2Px(context, 48.0F);
    RelativeLayout relativeLayout = new RelativeLayout(getContext());
    int j = View.generateViewId();
    if (this.mSettingsMenuItem != null && SecrecyUIHelper.inst().getShownEntity() != null) {
      bool = SecrecyUIHelper.inst().addSecrecyTip(relativeLayout, this.mSettingsMenuItem.getClickListener(), j);
    } else {
      bool = false;
    } 
    MenuPagerAdapter menuPagerAdapter = new MenuPagerAdapter(context, list);
    ViewPager viewPager = generateMenuPanelViewPager(context, menuPagerAdapter, i, j);
    viewPager.setAdapter((PagerAdapter)menuPagerAdapter);
    relativeLayout.addView((View)viewPager);
    MagicIndicator magicIndicator = generateIndicator(context, i, viewPager);
    if (menuPagerAdapter.getCount() > 1) {
      magicIndicator.setVisibility(0);
    } else {
      magicIndicator.setVisibility(8);
    } 
    relativeLayout.addView((View)magicIndicator);
    relativeLayout.addView((View)generateCancelTextView(context, i));
    relativeLayout.setBackground((Drawable)generateMenuBackground(context));
    setContentView((View)relativeLayout);
    setCancelable(true);
    setCanceledOnTouchOutside(true);
    setDialogLocation();
    setDialogSize(menuPagerAdapter, bool);
  }
  
  private List<IMenuItem> generateMenuItemVOList() {
    ArrayList<IMenuItem> arrayList1 = new ArrayList();
    ArrayList<Class<?>> arrayList = new ArrayList();
    ArrayList<IMenuItem> arrayList2 = new ArrayList<IMenuItem>(this.mDefaultMenuItemDOList);
    if (AdSiteManager.getInstance().isAdSiteBrowser()) {
      this.mCurrentMenuItemsVO = arrayList2;
    } else {
      this.mCurrentMenuItemsVO = HostDependManager.getInst().replacesMenuItems(arrayList2);
    } 
    for (IMenuItem iMenuItem : this.mCurrentMenuItemsVO) {
      boolean bool2 = isVisible(iMenuItem);
      boolean bool1 = bool2;
      if (isShareCategory(iMenuItem))
        bool1 = bool2 & isDisplayShareMenuItem(); 
      if (iMenuItem instanceof BackHomeMenuItem)
        bool1 = this.mIsShowBackHome; 
      if (this.mEssentialMenuItemList.contains(iMenuItem.getClass()))
        arrayList.add(iMenuItem.getClass()); 
      if (bool1) {
        if (this.mSDKMenuItemList.contains(iMenuItem.getClass()))
          iMenuItem.getView().setReportHostCustomClickEvent(false); 
        arrayList1.add(iMenuItem);
        if (iMenuItem instanceof SettingsMenuItem)
          this.mSettingsMenuItem = (SettingsMenuItem)iMenuItem; 
      } 
    } 
    arrayList2 = new ArrayList(this.mEssentialMenuItemList);
    arrayList2.removeAll(arrayList);
    Iterator<IMenuItem> iterator = arrayList2.iterator();
    while (iterator.hasNext()) {
      boolean bool;
      IMenuItem iMenuItem = makeEssentialMenuItem((Class)iterator.next());
      if (iMenuItem == null || iMenuItem.getView() == null) {
        i = 1;
      } else {
        i = 0;
      } 
      int j = 0x1 ^ i;
      int i = j;
      if (iMenuItem instanceof BackHomeMenuItem)
        bool = j & this.mIsShowBackHome; 
      if (bool)
        arrayList1.add(iMenuItem); 
    } 
    iterator = arrayList1.iterator();
    while (iterator.hasNext()) {
      MenuItemView menuItemView = ((IMenuItem)iterator.next()).getView();
      if (menuItemView.getParent() != null)
        ((ViewGroup)menuItemView.getParent()).removeView((View)menuItemView); 
    } 
    return arrayList1;
  }
  
  private ViewPager generateMenuPanelViewPager(Context paramContext, MenuPagerAdapter paramMenuPagerAdapter, int paramInt1, int paramInt2) {
    ViewPager viewPager = new ViewPager(paramContext);
    viewPager.setOverScrollMode(2);
    viewPager.setAdapter((PagerAdapter)paramMenuPagerAdapter);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
    layoutParams.addRule(12);
    layoutParams.addRule(3, paramInt2);
    layoutParams.bottomMargin = paramInt1;
    if (paramMenuPagerAdapter.getCount() > 1)
      layoutParams.bottomMargin = (int)(layoutParams.bottomMargin + UIUtils.dip2Px(paramContext, 30.0F)); 
    viewPager.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    return viewPager;
  }
  
  private static void initDebug(Context paramContext) {
    DebugUtil.updateDebugState(paramContext, null);
    if (DebugUtil.debug())
      WebView.setWebContentsDebuggingEnabled(true); 
  }
  
  private void initDefaultMenuItemList(Activity paramActivity) {
    this.mDefaultMenuItemDOList.add(new BackHomeMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new ShareMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new FavoriteMiniAppMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new ShortcutMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new RestartMiniAppMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new SettingsMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new FeedbackAndHelperMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new AboutMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new VConsonleMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new SeeProfileMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new RecordProblemMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new ProjectModeMenuItem(paramActivity));
    this.mDefaultMenuItemDOList.add(new TimelineGraphMenuItem(paramActivity));
  }
  
  private void initEssentialMenuItemList() {
    this.mEssentialMenuItemList.add(BackHomeMenuItem.class);
    this.mEssentialMenuItemList.add(RestartMiniAppMenuItem.class);
    this.mEssentialMenuItemList.add(SettingsMenuItem.class);
    this.mEssentialMenuItemList.add(AboutMenuItem.class);
  }
  
  private void initSDKMenuItemList() {
    this.mSDKMenuItemList.add(AboutMenuItem.class);
    this.mSDKMenuItemList.add(BackHomeMenuItem.class);
    this.mSDKMenuItemList.add(FavoriteMiniAppMenuItem.class);
    this.mSDKMenuItemList.add(FeedbackAndHelperMenuItem.class);
    this.mSDKMenuItemList.add(ProjectModeMenuItem.class);
    this.mSDKMenuItemList.add(RecordProblemMenuItem.class);
    this.mSDKMenuItemList.add(RestartMiniAppMenuItem.class);
    this.mSDKMenuItemList.add(SeeProfileMenuItem.class);
    this.mSDKMenuItemList.add(SettingsMenuItem.class);
    this.mSDKMenuItemList.add(ShareMenuItem.class);
    this.mSDKMenuItemList.add(ShortcutMenuItem.class);
    this.mSDKMenuItemList.add(VConsonleMenuItem.class);
    this.mSDKMenuItemList.add(TimelineGraphMenuItem.class);
  }
  
  public static MenuDialog inst(Activity paramActivity) {
    // Byte code:
    //   0: ldc com/tt/miniapp/titlemenu/MenuDialog
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/titlemenu/MenuDialog.sInst : Lcom/tt/miniapp/titlemenu/MenuDialog;
    //   6: ifnonnull -> 26
    //   9: new com/tt/miniapp/titlemenu/MenuDialog
    //   12: dup
    //   13: aload_0
    //   14: ldc_w 2132607952
    //   17: invokespecial <init> : (Landroid/app/Activity;I)V
    //   20: putstatic com/tt/miniapp/titlemenu/MenuDialog.sInst : Lcom/tt/miniapp/titlemenu/MenuDialog;
    //   23: goto -> 33
    //   26: getstatic com/tt/miniapp/titlemenu/MenuDialog.sInst : Lcom/tt/miniapp/titlemenu/MenuDialog;
    //   29: aload_0
    //   30: putfield mActivity : Landroid/app/Activity;
    //   33: getstatic com/tt/miniapp/titlemenu/MenuDialog.sInst : Lcom/tt/miniapp/titlemenu/MenuDialog;
    //   36: iconst_0
    //   37: putfield mIsShowBackHome : Z
    //   40: getstatic com/tt/miniapp/titlemenu/MenuDialog.sInst : Lcom/tt/miniapp/titlemenu/MenuDialog;
    //   43: astore_0
    //   44: ldc com/tt/miniapp/titlemenu/MenuDialog
    //   46: monitorexit
    //   47: aload_0
    //   48: areturn
    //   49: astore_0
    //   50: ldc com/tt/miniapp/titlemenu/MenuDialog
    //   52: monitorexit
    //   53: aload_0
    //   54: athrow
    // Exception table:
    //   from	to	target	type
    //   3	23	49	finally
    //   26	33	49	finally
    //   33	44	49	finally
  }
  
  private boolean isDisplayShareMenuItem() {
    String str;
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    AppInfoEntity appInfoEntity = appbrandApplicationImpl.getAppInfo();
    if (appInfoEntity != null && appInfoEntity.shareLevel == 3)
      return false; 
    if (appInfoEntity != null && appInfoEntity.isGame()) {
      str = appInfoEntity.appId;
    } else {
      str = appbrandApplicationImpl.getCurrentPageUrl();
    } 
    AppBrandLogger.d("MenuDialog", new Object[] { "currentPage ", str });
    a a = appbrandApplicationImpl.getCurrentPageHideShareMenuArrayMap();
    if (a.containsKey(str) && ((Boolean)a.get(str)).booleanValue()) {
      AppBrandLogger.d("MenuDialog", new Object[] { "not show share menu" });
      return false;
    } 
    return true;
  }
  
  private boolean isShareCategory(IMenuItem paramIMenuItem) {
    return (paramIMenuItem.getCategory() != null && paramIMenuItem.getCategory() == IMenuItem.ItemCategory.SHARE);
  }
  
  private boolean isVisible(IMenuItem paramIMenuItem) {
    return (paramIMenuItem.getView() != null && paramIMenuItem.getView().getVisibility() == 0);
  }
  
  private IMenuItem makeEssentialMenuItem(Class paramClass) {
    SettingsMenuItem settingsMenuItem;
    if (paramClass.equals(BackHomeMenuItem.class))
      return (IMenuItem)new BackHomeMenuItem((Activity)AppbrandContext.getInst().getCurrentActivity()); 
    if (paramClass.equals(RestartMiniAppMenuItem.class))
      return (IMenuItem)new RestartMiniAppMenuItem((Activity)AppbrandContext.getInst().getCurrentActivity()); 
    if (paramClass.equals(SettingsMenuItem.class)) {
      settingsMenuItem = new SettingsMenuItem((Activity)AppbrandContext.getInst().getCurrentActivity());
      this.mSettingsMenuItem = settingsMenuItem;
      return (IMenuItem)settingsMenuItem;
    } 
    return (IMenuItem)(settingsMenuItem.equals(AboutMenuItem.class) ? new AboutMenuItem((Activity)AppbrandContext.getInst().getCurrentActivity()) : null);
  }
  
  private void setDialogLocation() {
    Window window = getWindow();
    if (window != null) {
      if (TextUtils.equals(DevicesUtil.getBrand().toLowerCase(), "huawei") && AppbrandContext.getInst().isGame()) {
        window.setFlags(1024, 1024);
        window.getDecorView().setSystemUiVisibility(2822);
      } 
      window.setGravity(80);
      window.setWindowAnimations(2132607943);
      window.getDecorView().setSystemUiVisibility(2304);
    } 
  }
  
  private void setDialogSize(MenuPagerAdapter paramMenuPagerAdapter, int paramInt) {
    int i;
    Context context = getContext();
    if (getWindow() == null)
      return; 
    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
    if ((context.getResources().getConfiguration()).orientation == 1) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      i = DevicesUtil.getScreenWidth(context);
    } else {
      i = context.getResources().getDimensionPixelSize(2097414171);
    } 
    layoutParams.width = i;
    layoutParams.height = context.getResources().getDimensionPixelSize(2097414170);
    if (!paramMenuPagerAdapter.isDoubleLine()) {
      layoutParams.height = context.getResources().getDimensionPixelSize(2097414172);
    } else if (paramMenuPagerAdapter.getCount() > 1) {
      layoutParams.height = (int)(layoutParams.height + UIUtils.dip2Px(getContext(), 30.0F));
    } 
    layoutParams.height += paramInt;
    getWindow().setAttributes(layoutParams);
  }
  
  public void dismiss() {
    boolean bool;
    Activity activity = this.mActivity;
    if (activity != null && !activity.isFinishing()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (!bool)
      return; 
    List<IMenuItem> list = this.mCurrentMenuItemsVO;
    if (list != null)
      for (IMenuItem iMenuItem : list) {
        if (iMenuItem != null)
          iMenuItem.onMenuDismiss(); 
      }  
    this.mIsShowBackHome = false;
    _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss((Dialog)this);
  }
  
  public void onLanguageChange() {
    if (this.mActivity != null) {
      this.mDefaultMenuItemDOList.clear();
      initDefaultMenuItemList(this.mActivity);
      UIUtils.setProperLayoutDirection(getWindow().getDecorView());
    } 
  }
  
  public MenuDialog setShowBackHome(boolean paramBoolean) {
    this.mIsShowBackHome = paramBoolean;
    return this;
  }
  
  public void show() {
    boolean bool;
    Activity activity = this.mActivity;
    if (activity != null && !activity.isFinishing()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (!bool)
      return; 
    initDebug(getContext());
    generateMenuDialogView();
    List<IMenuItem> list = this.mCurrentMenuItemsVO;
    if (list != null)
      for (IMenuItem iMenuItem : list) {
        if (iMenuItem != null)
          iMenuItem.onMenuShow(); 
      }  
    super.show();
  }
  
  class MenuDialog {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\MenuDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */