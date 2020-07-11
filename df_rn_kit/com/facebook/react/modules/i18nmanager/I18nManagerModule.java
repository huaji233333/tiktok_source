package com.facebook.react.modules.i18nmanager;

import android.content.Context;
import com.facebook.react.bridge.ContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ReactModule(name = "I18nManager")
public class I18nManagerModule extends ContextBaseJavaModule {
  private final I18nUtil sharedI18nUtilInstance = I18nUtil.getInstance();
  
  public I18nManagerModule(Context paramContext) {
    super(paramContext);
  }
  
  @ReactMethod
  public void allowRTL(boolean paramBoolean) {
    this.sharedI18nUtilInstance.allowRTL(getContext(), paramBoolean);
  }
  
  @ReactMethod
  public void forceRTL(boolean paramBoolean) {
    this.sharedI18nUtilInstance.forceRTL(getContext(), paramBoolean);
  }
  
  public Map<String, Object> getConstants() {
    Context context = getContext();
    Locale locale = (context.getResources().getConfiguration()).locale;
    HashMap<String, Boolean> hashMap = MapBuilder.newHashMap();
    hashMap.put("isRTL", Boolean.valueOf(this.sharedI18nUtilInstance.isRTL(context)));
    hashMap.put("doLeftAndRightSwapInRTL", Boolean.valueOf(this.sharedI18nUtilInstance.doLeftAndRightSwapInRTL(context)));
    hashMap.put("localeIdentifier", locale.toString());
    return (Map)hashMap;
  }
  
  public String getName() {
    return "I18nManager";
  }
  
  @ReactMethod
  public void swapLeftAndRightInRTL(boolean paramBoolean) {
    this.sharedI18nUtilInstance.swapLeftAndRightInRTL(getContext(), paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\i18nmanager\I18nManagerModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */