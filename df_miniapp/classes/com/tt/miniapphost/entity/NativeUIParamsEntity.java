package com.tt.miniapphost.entity;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;

public class NativeUIParamsEntity {
  private static final String TAG = NativeUIParamsEntity.class.getName();
  
  private float mAvatorAppLogoCornerRadiusRatio = 0.5F;
  
  private int mBtnCornerRadius = 4;
  
  private float mMicroAppLogoCornerRadiusRatio = 0.2F;
  
  private float mMorePanelItemCornerRadiusRatio = 0.2F;
  
  private int mMorePanelLandScapeCornerRadius = 10;
  
  private int mMorePanelPortraitCornerRadius = 4;
  
  private String mNegativeColor = "#0A000000";
  
  private String mNegativeTextColor = "#FF000000";
  
  private String mPositiveColor = "#F85959";
  
  private String mPositiveItemNegativeTextColor = "#FFFFFFFF";
  
  private String mPositiveTextColor = "#F85959";
  
  private String mTabDotColor = "#F85959";
  
  private NativeUIParamsEntity() {}
  
  public static NativeUIParamsEntity getInst() {
    return Holder.sInstance;
  }
  
  public static boolean isLegalCornerRadiusRatio(float paramFloat) {
    return (paramFloat >= 0.0F && paramFloat <= 0.5F);
  }
  
  public float getAvatorAppLogoCornerRadiusRatio() {
    return this.mAvatorAppLogoCornerRadiusRatio;
  }
  
  public int getBtnCornerRadius() {
    return this.mBtnCornerRadius;
  }
  
  public float getMicroAppLogoCornerRadiusRatio() {
    return this.mMicroAppLogoCornerRadiusRatio;
  }
  
  public float getMorePanelItemCornerRadiusRatio() {
    return this.mMorePanelItemCornerRadiusRatio;
  }
  
  public float getMorePanelLandScapeCornerRadius() {
    return this.mMorePanelLandScapeCornerRadius;
  }
  
  public float getMorePanelPortraitCornerRadius() {
    return this.mMorePanelPortraitCornerRadius;
  }
  
  public String getNegativeColor() {
    return this.mNegativeColor;
  }
  
  public String getNegativeTextColor() {
    return this.mNegativeTextColor;
  }
  
  public String getPositiveColor() {
    return this.mPositiveColor;
  }
  
  public String getPositiveItemNegativeTextColor() {
    return this.mPositiveItemNegativeTextColor;
  }
  
  public String getPositiveTextColor() {
    return this.mPositiveTextColor;
  }
  
  public String getTabDotColor() {
    return UIUtils.isColor(this.mTabDotColor) ? this.mTabDotColor : "#F85959";
  }
  
  public NativeUIParamsEntity setAvatorAppLogoCornerRadiusRatio(float paramFloat) {
    if (!isLegalCornerRadiusRatio(paramFloat)) {
      AppBrandLogger.e(TAG, new Object[] { "CornerRadiusRatio is illegal! range is [0 ~ 0.5]f" });
      return this;
    } 
    this.mAvatorAppLogoCornerRadiusRatio = paramFloat;
    return this;
  }
  
  public NativeUIParamsEntity setBtnCornerRadius(int paramInt) {
    this.mBtnCornerRadius = paramInt;
    return this;
  }
  
  public NativeUIParamsEntity setMicroAppLogoCornerRadiusRatio(float paramFloat) {
    if (!isLegalCornerRadiusRatio(paramFloat)) {
      AppBrandLogger.e(TAG, new Object[] { "CornerRadiusRatio is illegal! range is 0.0 ~ 0.5" });
      return this;
    } 
    this.mMicroAppLogoCornerRadiusRatio = paramFloat;
    return this;
  }
  
  public NativeUIParamsEntity setMorePanelItemCornerRadiusRatio(float paramFloat) {
    if (!isLegalCornerRadiusRatio(paramFloat)) {
      AppBrandLogger.e(TAG, new Object[] { "morePanelItemCornerRadiusRatio is illegal! range is [0 ~ 0.5]f" });
      return this;
    } 
    this.mMorePanelItemCornerRadiusRatio = paramFloat;
    return this;
  }
  
  public NativeUIParamsEntity setMorePanelLandScapeCornerRadius(int paramInt) {
    this.mMorePanelLandScapeCornerRadius = paramInt;
    return this;
  }
  
  public NativeUIParamsEntity setMorePanelPortraitCornerRadius(int paramInt) {
    this.mMorePanelPortraitCornerRadius = paramInt;
    return this;
  }
  
  public NativeUIParamsEntity setPositiveColor(String paramString) {
    if (paramString == null) {
      AppBrandLogger.e(TAG, new Object[] { "mPositiveColor is null" });
      return this;
    } 
    if (!UIUtils.isColor(paramString)) {
      AppBrandLogger.e(TAG, new Object[] { "mPositiveColor is illegal" });
      return this;
    } 
    this.mPositiveColor = paramString;
    return this;
  }
  
  public NativeUIParamsEntity setPositiveItemNegativeTextColor(String paramString) {
    if (paramString == null) {
      AppBrandLogger.e(TAG, new Object[] { "mPositiveItemNegativeTextColor is null" });
      return this;
    } 
    if (!UIUtils.isColor(this.mPositiveColor)) {
      AppBrandLogger.e(TAG, new Object[] { "mPositiveItemNegativeTextColor is illegal" });
      return this;
    } 
    if (!NativeUIColorEntity.getInst().isLegalColor(paramString)) {
      AppBrandLogger.e(TAG, new Object[] { "mPositiveItemNegativeTextColor is illegal" });
      return this;
    } 
    this.mPositiveItemNegativeTextColor = paramString;
    return this;
  }
  
  public NativeUIParamsEntity setPositiveTextColor(String paramString) {
    if (paramString == null) {
      AppBrandLogger.e(TAG, new Object[] { "mPositiveTextColor is null" });
      return this;
    } 
    if (!UIUtils.isColor(paramString)) {
      AppBrandLogger.e(TAG, new Object[] { "mPositiveTextColor is illegal" });
      return this;
    } 
    this.mPositiveTextColor = paramString;
    return this;
  }
  
  public NativeUIParamsEntity setTabDotColor(String paramString) {
    this.mTabDotColor = paramString;
    return this;
  }
  
  static class Holder {
    static NativeUIParamsEntity sInstance = new NativeUIParamsEntity();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\NativeUIParamsEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */