package com.tt.option.ad;

public enum c {
  APP_BANNER("banner"),
  APP_EXCITING_VIDEO("banner"),
  APP_FEED("banner"),
  APP_VIDEO_PATCH_AD_POST("banner"),
  APP_VIDEO_PATCH_AD_PRE("banner"),
  GAME_BANNER("banner"),
  GAME_EXCITING_VIDEO("banner"),
  GAME_INTERSTITIAL("banner");
  
  private String a;
  
  static {
    APP_EXCITING_VIDEO = new c("APP_EXCITING_VIDEO", 2, "video");
    APP_VIDEO_PATCH_AD_PRE = new c("APP_VIDEO_PATCH_AD_PRE", 3, "preRollAd");
    APP_VIDEO_PATCH_AD_POST = new c("APP_VIDEO_PATCH_AD_POST", 4, "postRollAd");
    GAME_BANNER = new c("GAME_BANNER", 5, "banner");
    GAME_EXCITING_VIDEO = new c("GAME_EXCITING_VIDEO", 6, "video");
    GAME_INTERSTITIAL = new c("GAME_INTERSTITIAL", 7, "interstitial");
    b = new c[] { APP_BANNER, APP_FEED, APP_EXCITING_VIDEO, APP_VIDEO_PATCH_AD_PRE, APP_VIDEO_PATCH_AD_POST, GAME_BANNER, GAME_EXCITING_VIDEO, GAME_INTERSTITIAL };
  }
  
  c(String paramString1) {
    this.a = paramString1;
  }
  
  public final String getStrType() {
    return this.a;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\ad\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */