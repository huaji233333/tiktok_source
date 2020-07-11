package com.tt.miniapp.launch;

import android.os.Parcel;
import android.os.Parcelable;
import d.f.b.g;
import d.f.b.l;

public final class MiniAppLaunchConfig implements Parcelable {
  public static final Parcelable.Creator<MiniAppLaunchConfig> CREATOR;
  
  public static final Companion Companion = new Companion(null);
  
  public static final MiniAppLaunchConfig DEFAULT = new MiniAppLaunchConfig();
  
  private final float FULL_SCREEN_VIEW_INIT_HEIGHT = 1.0F;
  
  private boolean closeOnTouchOutside;
  
  private float containerViewInitHeightRate = this.FULL_SCREEN_VIEW_INIT_HEIGHT;
  
  private boolean enableContainerViewScrollInContainer = true;
  
  private int floatRootViewGravity = 17;
  
  private boolean hideStatusBarWhenFloat = true;
  
  private int outsideColor;
  
  static {
    CREATOR = new MiniAppLaunchConfig$Companion$CREATOR$1();
  }
  
  public MiniAppLaunchConfig() {}
  
  public MiniAppLaunchConfig(Parcel paramParcel) {
    this();
    boolean bool1;
    byte b = paramParcel.readByte();
    boolean bool2 = true;
    if (b != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.hideStatusBarWhenFloat = bool1;
    this.containerViewInitHeightRate = paramParcel.readFloat();
    if (paramParcel.readByte() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.enableContainerViewScrollInContainer = bool1;
    this.outsideColor = paramParcel.readInt();
    if (paramParcel.readByte() != 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    this.closeOnTouchOutside = bool1;
    this.floatRootViewGravity = paramParcel.readInt();
  }
  
  public final int describeContents() {
    return 0;
  }
  
  public final boolean getCloseOnTouchOutside() {
    return this.closeOnTouchOutside;
  }
  
  public final float getContainerViewInitHeightRate() {
    return this.containerViewInitHeightRate;
  }
  
  public final boolean getEnableContainerViewScrollInContainer() {
    return this.enableContainerViewScrollInContainer;
  }
  
  public final float getFULL_SCREEN_VIEW_INIT_HEIGHT() {
    return this.FULL_SCREEN_VIEW_INIT_HEIGHT;
  }
  
  public final int getFloatRootViewGravity() {
    return this.floatRootViewGravity;
  }
  
  public final boolean getHideStatusBarWhenFloat() {
    return this.hideStatusBarWhenFloat;
  }
  
  public final int getOutsideColor() {
    return this.outsideColor;
  }
  
  public final boolean isLaunchWithFloatStyle() {
    return (this.containerViewInitHeightRate != this.FULL_SCREEN_VIEW_INIT_HEIGHT);
  }
  
  public final void setCloseOnTouchOutside(boolean paramBoolean) {
    this.closeOnTouchOutside = paramBoolean;
  }
  
  public final void setContainerViewInitHeightRate(float paramFloat) {
    this.containerViewInitHeightRate = paramFloat;
  }
  
  public final void setEnableContainerViewScrollInContainer(boolean paramBoolean) {
    this.enableContainerViewScrollInContainer = paramBoolean;
  }
  
  public final void setFloatRootViewGravity(int paramInt) {
    this.floatRootViewGravity = paramInt;
  }
  
  public final void setHideStatusBarWhenFloat(boolean paramBoolean) {
    this.hideStatusBarWhenFloat = paramBoolean;
  }
  
  public final void setOutsideColor(int paramInt) {
    this.outsideColor = paramInt;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public static final class MiniAppLaunchConfig$Companion$CREATOR$1 implements Parcelable.Creator<MiniAppLaunchConfig> {
    public final MiniAppLaunchConfig createFromParcel(Parcel param1Parcel) {
      l.b(param1Parcel, "parcel");
      return new MiniAppLaunchConfig(param1Parcel);
    }
    
    public final MiniAppLaunchConfig[] newArray(int param1Int) {
      return new MiniAppLaunchConfig[param1Int];
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launch\MiniAppLaunchConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */