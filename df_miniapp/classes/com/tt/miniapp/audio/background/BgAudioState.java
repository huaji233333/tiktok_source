package com.tt.miniapp.audio.background;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class BgAudioState implements Parcelable {
  public static final Parcelable.Creator<BgAudioState> CREATOR = new Parcelable.Creator<BgAudioState>() {
      public final BgAudioState createFromParcel(Parcel param1Parcel) {
        return new BgAudioState(param1Parcel);
      }
      
      public final BgAudioState[] newArray(int param1Int) {
        return new BgAudioState[param1Int];
      }
    };
  
  public int bufferd;
  
  public int currentTime;
  
  public int duration;
  
  public boolean paused;
  
  public int volume;
  
  public BgAudioState() {}
  
  protected BgAudioState(Parcel paramParcel) {
    boolean bool;
    this.duration = paramParcel.readInt();
    this.currentTime = paramParcel.readInt();
    if (paramParcel.readByte() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.paused = bool;
    this.bufferd = paramParcel.readInt();
    this.volume = paramParcel.readInt();
  }
  
  public static BgAudioState parseFromJSONStr(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      BgAudioState bgAudioState = new BgAudioState();
      bgAudioState.duration = jSONObject.optInt("duration");
      bgAudioState.currentTime = jSONObject.optInt("currentTime");
      bgAudioState.paused = jSONObject.optBoolean("paused");
      bgAudioState.bufferd = jSONObject.optInt("bufferd");
      bgAudioState.volume = jSONObject.optInt("volume");
      return bgAudioState;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_BgAudioState", exception.getStackTrace());
      return null;
    } 
  }
  
  public int describeContents() {
    return 0;
  }
  
  public String toJSONStr() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("duration", this.duration);
      jSONObject.put("currentTime", this.currentTime);
      jSONObject.put("paused", this.paused);
      jSONObject.put("bufferd", this.bufferd);
      jSONObject.put("volume", this.volume);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_BgAudioState", new Object[] { "", jSONException });
    } 
    return jSONObject.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\background\BgAudioState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */