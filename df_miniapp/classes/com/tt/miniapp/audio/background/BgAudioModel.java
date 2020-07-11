package com.tt.miniapp.audio.background;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import com.tt.miniapphost.entity.AppInfoEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class BgAudioModel implements Parcelable {
  public static final Parcelable.Creator<BgAudioModel> CREATOR = new Parcelable.Creator<BgAudioModel>() {
      public final BgAudioModel createFromParcel(Parcel param1Parcel) {
        return new BgAudioModel(param1Parcel);
      }
      
      public final BgAudioModel[] newArray(int param1Int) {
        return new BgAudioModel[param1Int];
      }
    };
  
  public boolean autoPlay;
  
  public boolean loop;
  
  public JSONObject mAudioPage;
  
  public String mCoverImgUrl;
  
  public String mSinger;
  
  public String mTitle;
  
  public String miniAppId;
  
  public boolean obeyMuteSwitch;
  
  public String src;
  
  public int startTime;
  
  public float volume;
  
  public BgAudioModel() {}
  
  protected BgAudioModel(Parcel paramParcel) {
    boolean bool1;
    this.src = paramParcel.readString();
    this.startTime = paramParcel.readInt();
    byte b = paramParcel.readByte();
    boolean bool2 = true;
    if (b != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.autoPlay = bool1;
    if (paramParcel.readByte() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.loop = bool1;
    if (paramParcel.readByte() != 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    this.obeyMuteSwitch = bool1;
    this.volume = paramParcel.readFloat();
    this.mCoverImgUrl = paramParcel.readString();
    this.mTitle = paramParcel.readString();
    this.mSinger = paramParcel.readString();
    this.miniAppId = paramParcel.readString();
  }
  
  public static BgAudioModel parse(String paramString, ApiErrorInfoEntity paramApiErrorInfoEntity) {
    if (TextUtils.isEmpty(paramString)) {
      paramApiErrorInfoEntity.append("args is null");
      return null;
    } 
    try {
      boolean bool;
      JSONObject jSONObject = new JSONObject(paramString);
      BgAudioModel bgAudioModel = new BgAudioModel();
      bgAudioModel.src = jSONObject.optString("src");
      bgAudioModel.startTime = jSONObject.optInt("startTime");
      if (jSONObject.optInt("autoplay") == 1) {
        bool = true;
      } else {
        bool = false;
      } 
      bgAudioModel.autoPlay = bool;
      bgAudioModel.loop = jSONObject.optBoolean("loop");
      bgAudioModel.mCoverImgUrl = jSONObject.optString("coverImgUrl");
      bgAudioModel.mTitle = jSONObject.optString("title");
      bgAudioModel.mSinger = jSONObject.optString("singer");
      bgAudioModel.mAudioPage = jSONObject.optJSONObject("audioPage");
      AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
      if (appInfoEntity != null) {
        if (TextUtils.isEmpty(bgAudioModel.mCoverImgUrl))
          bgAudioModel.mCoverImgUrl = appInfoEntity.icon; 
        if (TextUtils.isEmpty(bgAudioModel.mTitle))
          bgAudioModel.mTitle = appInfoEntity.appName; 
        bgAudioModel.miniAppId = appInfoEntity.appId;
      } 
      return bgAudioModel;
    } catch (Exception exception) {
      paramApiErrorInfoEntity.append("parse BgAudioModel exception");
      paramApiErrorInfoEntity.setThrowable(exception);
      AppBrandLogger.e("tma_BgAudioModel", new Object[] { "parse", exception });
      return null;
    } 
  }
  
  public static BgAudioModel parseFromJSONStr(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      BgAudioModel bgAudioModel = new BgAudioModel();
      bgAudioModel.src = jSONObject.optString("src");
      bgAudioModel.startTime = jSONObject.optInt("startTime");
      bgAudioModel.obeyMuteSwitch = jSONObject.optBoolean("obeyMuteSwitch");
      bgAudioModel.autoPlay = jSONObject.optBoolean("autoPlay");
      bgAudioModel.loop = jSONObject.optBoolean("loop");
      bgAudioModel.volume = (float)jSONObject.optDouble("volume");
      bgAudioModel.mCoverImgUrl = jSONObject.optString("coverImgUrl");
      bgAudioModel.mTitle = jSONObject.optString("title");
      bgAudioModel.mSinger = jSONObject.optString("singer");
      bgAudioModel.mAudioPage = jSONObject.optJSONObject("audioPage");
      bgAudioModel.miniAppId = jSONObject.optString("miniAppId");
      return bgAudioModel;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_BgAudioModel", exception.getStackTrace());
      return null;
    } 
  }
  
  public int describeContents() {
    return 0;
  }
  
  public String toJSONStr() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("src", this.src);
      jSONObject.put("startTime", this.startTime);
      jSONObject.put("autoPlay", this.autoPlay);
      jSONObject.put("obeyMuteSwitch", this.obeyMuteSwitch);
      jSONObject.put("loop", this.loop);
      jSONObject.put("volume", this.volume);
      jSONObject.put("coverImgUrl", this.mCoverImgUrl);
      jSONObject.put("title", this.mTitle);
      jSONObject.put("singer", this.mSinger);
      jSONObject.put("audioPage", this.mAudioPage);
      jSONObject.put("miniAppId", this.miniAppId);
      return jSONObject.toString();
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "tma_BgAudioModel", jSONException.getStackTrace());
      return null;
    } 
  }
  
  public String toString() {
    return toJSONStr();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.src);
    paramParcel.writeInt(this.startTime);
    paramParcel.writeByte((byte)this.autoPlay);
    paramParcel.writeByte((byte)this.loop);
    paramParcel.writeByte((byte)this.obeyMuteSwitch);
    paramParcel.writeFloat(this.volume);
    paramParcel.writeString(this.mCoverImgUrl);
    paramParcel.writeString(this.mTitle);
    paramParcel.writeString(this.mSinger);
    paramParcel.writeString(this.miniAppId);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\background\BgAudioModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */