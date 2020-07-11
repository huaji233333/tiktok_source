package com.tt.option.ad;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public class AdModel implements Parcelable {
  public static final Parcelable.Creator<AdModel> CREATOR = new Parcelable.Creator<AdModel>() {
    
    };
  
  public String a;
  
  public String b;
  
  public int c;
  
  public AdModel() {}
  
  protected AdModel(Parcel paramParcel) {
    this.a = paramParcel.readString();
    this.b = paramParcel.readString();
    this.c = paramParcel.readInt();
  }
  
  public static AdModel a(String paramString) {
    AdModel adModel;
    if (TextUtils.isEmpty(paramString)) {
      AppBrandLogger.d("AdModel", new Object[] { "parseAppMeta json is null" });
      return null;
    } 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      adModel = new AdModel();
      try {
        adModel.a = jSONObject.optString("adid");
        adModel.b = jSONObject.optString("type");
        adModel.c = jSONObject.optInt("status");
        return adModel;
      } catch (Exception null) {}
    } catch (Exception exception) {
      adModel = null;
    } 
    AppBrandLogger.e("AdModel", new Object[] { "", exception });
    return adModel;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.a);
    paramParcel.writeString(this.b);
    paramParcel.writeInt(this.c);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\ad\AdModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */