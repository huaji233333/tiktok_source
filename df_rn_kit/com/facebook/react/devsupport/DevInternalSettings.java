package com.facebook.react.devsupport;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import com.facebook.react.packagerconnection.PackagerConnectionSettings;

public class DevInternalSettings implements SharedPreferences.OnSharedPreferenceChangeListener, DeveloperSettings {
  private final Listener mListener;
  
  private final PackagerConnectionSettings mPackagerConnectionSettings;
  
  private final SharedPreferences mPreferences;
  
  public DevInternalSettings(Context paramContext, Listener paramListener) {
    this.mListener = paramListener;
    this.mPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.mPreferences.registerOnSharedPreferenceChangeListener(this);
    this.mPackagerConnectionSettings = new PackagerConnectionSettings(paramContext);
  }
  
  public PackagerConnectionSettings getPackagerConnectionSettings() {
    return this.mPackagerConnectionSettings;
  }
  
  public boolean isAnimationFpsDebugEnabled() {
    return this.mPreferences.getBoolean("animations_debug", false);
  }
  
  public boolean isBundleDeltasEnabled() {
    return this.mPreferences.getBoolean("js_bundle_deltas", true);
  }
  
  public boolean isElementInspectorEnabled() {
    return this.mPreferences.getBoolean("inspector_debug", false);
  }
  
  public boolean isFpsDebugEnabled() {
    return this.mPreferences.getBoolean("fps_debug", false);
  }
  
  public boolean isHotModuleReplacementEnabled() {
    return this.mPreferences.getBoolean("hot_module_replacement", false);
  }
  
  public boolean isJSDevModeEnabled() {
    return this.mPreferences.getBoolean("js_dev_mode_debug", true);
  }
  
  public boolean isJSMinifyEnabled() {
    return this.mPreferences.getBoolean("js_minify_debug", false);
  }
  
  public boolean isNuclideJSDebugEnabled() {
    return false;
  }
  
  public boolean isReloadOnJSChangeEnabled() {
    return this.mPreferences.getBoolean("reload_on_js_change", false);
  }
  
  public boolean isRemoteJSDebugEnabled() {
    return this.mPreferences.getBoolean("remote_js_debug", false);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString) {
    if (this.mListener != null && ("fps_debug".equals(paramString) || "reload_on_js_change".equals(paramString) || "js_dev_mode_debug".equals(paramString) || "js_bundle_deltas".equals(paramString) || "js_minify_debug".equals(paramString)))
      this.mListener.onInternalSettingsChanged(); 
  }
  
  public void setBundleDeltasEnabled(boolean paramBoolean) {
    this.mPreferences.edit().putBoolean("js_bundle_deltas", paramBoolean).apply();
  }
  
  public void setElementInspectorEnabled(boolean paramBoolean) {
    this.mPreferences.edit().putBoolean("inspector_debug", paramBoolean).apply();
  }
  
  public void setFpsDebugEnabled(boolean paramBoolean) {
    this.mPreferences.edit().putBoolean("fps_debug", paramBoolean).apply();
  }
  
  public void setHotModuleReplacementEnabled(boolean paramBoolean) {
    this.mPreferences.edit().putBoolean("hot_module_replacement", paramBoolean).apply();
  }
  
  public void setReloadOnJSChangeEnabled(boolean paramBoolean) {
    this.mPreferences.edit().putBoolean("reload_on_js_change", paramBoolean).apply();
  }
  
  public void setRemoteJSDebugEnabled(boolean paramBoolean) {
    this.mPreferences.edit().putBoolean("remote_js_debug", paramBoolean).apply();
  }
  
  public static interface Listener {
    void onInternalSettingsChanged();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\DevInternalSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */