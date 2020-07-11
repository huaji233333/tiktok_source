package com.tt.miniapp.maplocate;

import android.location.Location;
import android.os.Build;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class TMALocation extends Location implements Cloneable {
  private String address;
  
  private String city;
  
  private String country;
  
  private String district;
  
  private int mLocType;
  
  private int mRawImplStatusCode;
  
  private int mStatusCode;
  
  private String province;
  
  public TMALocation(int paramInt) {
    super("");
    this.mStatusCode = paramInt;
  }
  
  public TMALocation(Location paramLocation) {
    super(paramLocation);
  }
  
  public TMALocation(TMALocation paramTMALocation) {
    super(paramTMALocation);
    setStatusCode(paramTMALocation.getStatusCode());
    setAddress(paramTMALocation.getAddress());
    setCountry(paramTMALocation.getCountry());
    setProvider(paramTMALocation.getProvince());
    setCity(paramTMALocation.getCity());
    setDistrict(paramTMALocation.getDistrict());
    setLocType(paramTMALocation.getLocType());
  }
  
  public TMALocation(String paramString) {
    super(paramString);
  }
  
  public static TMALocation fromJson(JSONObject paramJSONObject) throws JSONException {
    if (paramJSONObject == null)
      return null; 
    TMALocation tMALocation = new TMALocation(paramJSONObject.optString("provider"));
    tMALocation.setLatitude(paramJSONObject.optDouble("latitude"));
    tMALocation.setLongitude(paramJSONObject.optDouble("longitude"));
    tMALocation.setTime(paramJSONObject.optLong("loc_time"));
    tMALocation.setSpeed((float)paramJSONObject.optDouble("speed", 0.0D));
    tMALocation.setAccuracy((float)paramJSONObject.optDouble("accuracy"));
    tMALocation.setAltitude(paramJSONObject.optDouble("altitude"));
    tMALocation.setStatusCode(paramJSONObject.optInt("statusCode"));
    tMALocation.setRawImplStatusCode(paramJSONObject.optInt("rawImplStatusCode"));
    tMALocation.setAddress(paramJSONObject.optString("address"));
    tMALocation.setCountry(paramJSONObject.optString("country"));
    tMALocation.setProvince(paramJSONObject.optString("province"));
    tMALocation.setCity(paramJSONObject.optString("city"));
    tMALocation.setDistrict(paramJSONObject.optString("district"));
    tMALocation.setLocType(paramJSONObject.optInt("loctype"));
    if (Build.VERSION.SDK_INT >= 26)
      tMALocation.setVerticalAccuracyMeters(0.0F); 
    return tMALocation;
  }
  
  public static boolean isSuccess(TMALocation paramTMALocation) {
    return (paramTMALocation != null && paramTMALocation.getStatusCode() == 0);
  }
  
  protected Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    } 
  }
  
  public String getAddress() {
    return this.address;
  }
  
  public String getCity() {
    return this.city;
  }
  
  public String getCountry() {
    return this.country;
  }
  
  public String getDistrict() {
    return this.district;
  }
  
  public float getHorizontalAccuracy() {
    return getAccuracy();
  }
  
  public int getLocType() {
    return this.mLocType;
  }
  
  public String getProvince() {
    return this.province;
  }
  
  public int getRawImplStatusCode() {
    return this.mRawImplStatusCode;
  }
  
  public int getStatusCode() {
    return this.mStatusCode;
  }
  
  public void setAddress(String paramString) {
    this.address = paramString;
  }
  
  public void setCity(String paramString) {
    this.city = paramString;
  }
  
  public void setCountry(String paramString) {
    this.country = paramString;
  }
  
  public void setDistrict(String paramString) {
    this.district = paramString;
  }
  
  public void setLocType(int paramInt) {
    this.mLocType = paramInt;
  }
  
  public void setProvince(String paramString) {
    this.province = paramString;
  }
  
  public void setRawImplStatusCode(int paramInt) {
    this.mRawImplStatusCode = paramInt;
  }
  
  public void setStatusCode(int paramInt) {
    this.mStatusCode = paramInt;
  }
  
  public JSONObject toJson() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.putOpt("provider", getProvider());
      jSONObject.putOpt("latitude", Double.valueOf(getLatitude()));
      jSONObject.putOpt("longitude", Double.valueOf(getLongitude()));
      jSONObject.putOpt("loc_time", Long.valueOf(getTime()));
      jSONObject.putOpt("speed", Float.valueOf(getSpeed()));
      jSONObject.putOpt("accuracy", Float.valueOf(getAccuracy()));
      jSONObject.putOpt("altitude", Double.valueOf(getAltitude()));
      jSONObject.putOpt("statusCode", Integer.valueOf(getStatusCode()));
      jSONObject.putOpt("rawImplStatusCode", Integer.valueOf(getRawImplStatusCode()));
      jSONObject.putOpt("address", getAddress());
      jSONObject.putOpt("country", getCountry());
      jSONObject.putOpt("province", getProvince());
      jSONObject.putOpt("city", getCity());
      jSONObject.putOpt("district", getDistrict());
      jSONObject.putOpt("loctype", Integer.valueOf(getLocType()));
      float f = 0.0F;
      if (Build.VERSION.SDK_INT >= 26)
        f = getVerticalAccuracyMeters(); 
      jSONObject.put("verticalAccuracy", f);
      return jSONObject;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("TMALocation", "tojson", (Throwable)jSONException);
      return jSONObject;
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("TMALocation{mStatusCode=");
    stringBuilder.append(this.mStatusCode);
    stringBuilder.append(", mRawImplStatusCode=");
    stringBuilder.append(this.mRawImplStatusCode);
    stringBuilder.append(", address='");
    stringBuilder.append(this.address);
    stringBuilder.append('\'');
    stringBuilder.append(", country='");
    stringBuilder.append(this.country);
    stringBuilder.append('\'');
    stringBuilder.append(", province='");
    stringBuilder.append(this.province);
    stringBuilder.append('\'');
    stringBuilder.append(", city='");
    stringBuilder.append(this.city);
    stringBuilder.append('\'');
    stringBuilder.append(", district='");
    stringBuilder.append(this.district);
    stringBuilder.append('\'');
    stringBuilder.append(", mLocType=");
    stringBuilder.append(this.mLocType);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\maplocate\TMALocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */