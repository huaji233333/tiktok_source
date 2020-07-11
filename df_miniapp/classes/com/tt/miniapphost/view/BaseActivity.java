package com.tt.miniapphost.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.v7.app.AppCompatActivity;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.language.LanguageUtils;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.DynamicAppAssetsCompat;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity implements LanguageChangeListener {
  private static boolean hasSyncLocaleConfig;
  
  public static Context generateBaseContext(Context paramContext) {
    Context context = paramContext;
    if (Build.VERSION.SDK_INT >= 24)
      context = updateResources(paramContext); 
    return context;
  }
  
  private static Context updateResources(Context paramContext) {
    Resources resources = paramContext.getResources();
    Locale locale = LocaleManager.getInst().getCurrentHostSetLocale();
    if (locale == null)
      return paramContext; 
    Configuration configuration = resources.getConfiguration();
    configuration.setLocale(locale);
    configuration.setLayoutDirection(locale);
    configuration.setLocales(new LocaleList(new Locale[] { locale }));
    return paramContext.createConfigurationContext(configuration);
  }
  
  public void attachBaseContext(Context paramContext) {
    super.attachBaseContext(generateBaseContext(paramContext));
    DynamicAppAssetsCompat.ensureDynamicFeatureAssets((Context)this, super.getAssets());
    AppBrandLogger.d("BaseActivity", new Object[] { "registerLangChangeListener" });
    LocaleManager.getInst().registerLangChangeListener(this, true);
  }
  
  public Context getApplicationContext() {
    DynamicAppAssetsCompat.ensureDynamicFeatureAssets(super.getApplicationContext(), super.getApplicationContext().getAssets());
    return super.getApplicationContext();
  }
  
  public AssetManager getAssets() {
    DynamicAppAssetsCompat.ensureDynamicFeatureAssets((Context)this, super.getAssets());
    return super.getAssets();
  }
  
  public Resources getResources() {
    DynamicAppAssetsCompat.ensureDynamicFeatureAssets((Context)this, super.getAssets());
    return super.getResources();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
    LanguageUtils.updateResourceLocale((Context)this);
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    LanguageUtils.updateResourceLocale((Context)this);
  }
  
  public void onDestroy() {
    super.onDestroy();
    LocaleManager.getInst().unreigsterLangChangeListener(this);
  }
  
  public void onLanguageChange() {
    AppBrandLogger.d("BaseActivity", new Object[] { "onLanguageChange" });
    LanguageUtils.updateResourceLocale((Context)this);
  }
  
  public void onStart() {
    super.onStart();
    if (hasSyncLocaleConfig)
      return; 
    hasSyncLocaleConfig = true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\view\BaseActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */