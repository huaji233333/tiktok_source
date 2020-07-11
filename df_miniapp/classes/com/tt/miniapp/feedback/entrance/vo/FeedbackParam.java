package com.tt.miniapp.feedback.entrance.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedbackParam implements Parcelable {
  public static final Parcelable.Creator<FeedbackParam> CREATOR = new Parcelable.Creator<FeedbackParam>() {
      public final FeedbackParam createFromParcel(Parcel param1Parcel) {
        return new FeedbackParam(param1Parcel);
      }
      
      public final FeedbackParam[] newArray(int param1Int) {
        return new FeedbackParam[param1Int];
      }
    };
  
  private String aid;
  
  private String appName;
  
  private String channel;
  
  private String deviceId;
  
  private String feedbackAid;
  
  private String feedbackAppName;
  
  private String feedbackAppkey;
  
  private String hostAid;
  
  private String hostAppKey;
  
  private String hostAppName;
  
  private String hostAppUpdateVersion;
  
  private String hostAppVersion;
  
  private String iid;
  
  private String launchFrom;
  
  private String path = "";
  
  private String query = "";
  
  private String ttId;
  
  private int type;
  
  private String versionType;
  
  public FeedbackParam() {}
  
  protected FeedbackParam(Parcel paramParcel) {
    this.appName = paramParcel.readString();
    this.channel = paramParcel.readString();
    this.deviceId = paramParcel.readString();
    this.aid = paramParcel.readString();
    this.iid = paramParcel.readString();
    this.hostAppKey = paramParcel.readString();
    this.hostAid = paramParcel.readString();
    this.hostAppName = paramParcel.readString();
    this.hostAppVersion = paramParcel.readString();
    this.hostAppUpdateVersion = paramParcel.readString();
    this.feedbackAid = paramParcel.readString();
    this.feedbackAppName = paramParcel.readString();
    this.feedbackAppkey = paramParcel.readString();
    this.type = paramParcel.readInt();
    this.versionType = paramParcel.readString();
    this.ttId = paramParcel.readString();
    this.launchFrom = paramParcel.readString();
    this.path = paramParcel.readString();
    this.query = paramParcel.readString();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public String getAid() {
    return this.aid;
  }
  
  public String getAppName() {
    return this.appName;
  }
  
  public String getChannel() {
    return this.channel;
  }
  
  public String getDeviceId() {
    return this.deviceId;
  }
  
  public String getFeedbackAid() {
    return this.feedbackAid;
  }
  
  public String getFeedbackAppName() {
    return this.feedbackAppName;
  }
  
  public String getFeedbackAppkey() {
    return this.feedbackAppkey;
  }
  
  public String getHostAid() {
    return this.hostAid;
  }
  
  public String getHostAppKey() {
    return this.hostAppKey;
  }
  
  public String getHostAppName() {
    return this.hostAppName;
  }
  
  public String getHostAppUpdateVersion() {
    return this.hostAppUpdateVersion;
  }
  
  public String getHostAppVersion() {
    return this.hostAppVersion;
  }
  
  public String getIid() {
    return this.iid;
  }
  
  public String getLaunchFrom() {
    return this.launchFrom;
  }
  
  public String getPath() {
    return this.path;
  }
  
  public String getQuery() {
    return this.query;
  }
  
  public String getTtId() {
    return this.ttId;
  }
  
  public int getType() {
    return this.type;
  }
  
  public String getVersionType() {
    return this.versionType;
  }
  
  public boolean isGame() {
    return (this.type == 2);
  }
  
  public void setAid(String paramString) {
    this.aid = paramString;
  }
  
  public void setAppName(String paramString) {
    this.appName = paramString;
  }
  
  public void setChannel(String paramString) {
    this.channel = paramString;
  }
  
  public void setDeviceId(String paramString) {
    this.deviceId = paramString;
  }
  
  public void setFeedbackAid(String paramString) {
    this.feedbackAid = paramString;
  }
  
  public void setFeedbackAppName(String paramString) {
    this.feedbackAppName = paramString;
  }
  
  public void setFeedbackAppkey(String paramString) {
    this.feedbackAppkey = paramString;
  }
  
  public void setHostAid(String paramString) {
    this.hostAid = paramString;
  }
  
  public void setHostAppKey(String paramString) {
    this.hostAppKey = paramString;
  }
  
  public void setHostAppName(String paramString) {
    this.hostAppName = paramString;
  }
  
  public void setHostAppUpdateVersion(String paramString) {
    this.hostAppUpdateVersion = paramString;
  }
  
  public void setHostAppVersion(String paramString) {
    this.hostAppVersion = paramString;
  }
  
  public void setIid(String paramString) {
    this.iid = paramString;
  }
  
  public void setLaunchFrom(String paramString) {
    this.launchFrom = paramString;
  }
  
  public void setPath(String paramString) {
    this.path = paramString;
  }
  
  public void setQuery(String paramString) {
    this.query = paramString;
  }
  
  public void setTtId(String paramString) {
    this.ttId = paramString;
  }
  
  public void setType(int paramInt) {
    this.type = paramInt;
  }
  
  public void setVersionType(String paramString) {
    this.versionType = paramString;
  }
  
  public String toParamString(String paramString1, String paramString2, String paramString3) {
    StringBuilder stringBuilder = new StringBuilder("?app_name=");
    stringBuilder.append(paramString3);
    stringBuilder.append("&channel=");
    stringBuilder.append(this.channel);
    stringBuilder.append("&device_id=");
    stringBuilder.append(this.deviceId);
    stringBuilder.append("&aid=");
    stringBuilder.append(paramString2);
    stringBuilder.append("&appkey=");
    stringBuilder.append(paramString1);
    stringBuilder.append("&iid=");
    stringBuilder.append(this.iid);
    return stringBuilder.toString();
  }
  
  public String toParamString(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) {
    StringBuilder stringBuilder = new StringBuilder("?app_name=");
    stringBuilder.append(paramString3);
    stringBuilder.append("&channel=");
    stringBuilder.append(this.channel);
    stringBuilder.append("&device_id=");
    stringBuilder.append(this.deviceId);
    stringBuilder.append("&aid=");
    stringBuilder.append(paramString2);
    stringBuilder.append("&appkey=");
    stringBuilder.append(paramString1);
    stringBuilder.append("&iid=");
    stringBuilder.append(this.iid);
    stringBuilder.append("&app_version=");
    stringBuilder.append(paramString4);
    stringBuilder.append("&os_version=");
    stringBuilder.append(paramString5);
    stringBuilder.append("&device_type=");
    stringBuilder.append(paramString6);
    stringBuilder.append("&update_version_code=");
    stringBuilder.append(paramString7);
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.appName);
    paramParcel.writeString(this.channel);
    paramParcel.writeString(this.deviceId);
    paramParcel.writeString(this.aid);
    paramParcel.writeString(this.iid);
    paramParcel.writeString(this.hostAppKey);
    paramParcel.writeString(this.hostAid);
    paramParcel.writeString(this.hostAppName);
    paramParcel.writeString(this.hostAppVersion);
    paramParcel.writeString(this.hostAppUpdateVersion);
    paramParcel.writeString(this.feedbackAid);
    paramParcel.writeString(this.feedbackAppName);
    paramParcel.writeString(this.feedbackAppkey);
    paramParcel.writeInt(this.type);
    paramParcel.writeString(this.versionType);
    paramParcel.writeString(this.ttId);
    paramParcel.writeString(this.launchFrom);
    paramParcel.writeString(this.path);
    paramParcel.writeString(this.query);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\vo\FeedbackParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */