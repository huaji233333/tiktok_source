package com.facebook.react.uimanager;

import com.facebook.yoga.YogaConfig;

public class ReactYogaConfigProvider {
  private static YogaConfig YOGA_CONFIG;
  
  public static YogaConfig get() {
    if (YOGA_CONFIG == null) {
      YogaConfig yogaConfig = new YogaConfig();
      YOGA_CONFIG = yogaConfig;
      yogaConfig.setPointScaleFactor(0.0F);
      YOGA_CONFIG.setUseLegacyStretchBehaviour(true);
    } 
    return YOGA_CONFIG;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ReactYogaConfigProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */