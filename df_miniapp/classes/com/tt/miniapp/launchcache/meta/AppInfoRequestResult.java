package com.tt.miniapp.launchcache.meta;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class AppInfoRequestResult implements Parcelable {
  public static final Parcelable.Creator<AppInfoRequestResult> CREATOR = new Parcelable.Creator<AppInfoRequestResult>() {
      public final AppInfoRequestResult createFromParcel(Parcel param1Parcel) {
        return new AppInfoRequestResult(param1Parcel);
      }
      
      public final AppInfoRequestResult[] newArray(int param1Int) {
        return new AppInfoRequestResult[param1Int];
      }
    };
  
  public String appId;
  
  public String encryIV;
  
  public String encryKey;
  
  public int fromProcess;
  
  public long generateMetaParamsBegin;
  
  public long generateMetaParamsBeginCpuTime;
  
  public long generateMetaParamsEnd;
  
  public long generateMetaParamsEndCpuTime;
  
  public ArrayList<RequestMetaRecord> requestRecordList = new ArrayList<RequestMetaRecord>();
  
  public AppInfoRequestResult() {}
  
  protected AppInfoRequestResult(Parcel paramParcel) {
    this.appId = paramParcel.readString();
    this.encryKey = paramParcel.readString();
    this.encryIV = paramParcel.readString();
    this.generateMetaParamsBegin = paramParcel.readLong();
    this.generateMetaParamsEnd = paramParcel.readLong();
    this.generateMetaParamsBeginCpuTime = paramParcel.readLong();
    this.generateMetaParamsEndCpuTime = paramParcel.readLong();
    this.fromProcess = paramParcel.readInt();
    this.requestRecordList = paramParcel.createTypedArrayList(RequestMetaRecord.CREATOR);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.appId);
    paramParcel.writeString(this.encryKey);
    paramParcel.writeString(this.encryIV);
    paramParcel.writeLong(this.generateMetaParamsBegin);
    paramParcel.writeLong(this.generateMetaParamsEnd);
    paramParcel.writeLong(this.generateMetaParamsBeginCpuTime);
    paramParcel.writeLong(this.generateMetaParamsEndCpuTime);
    paramParcel.writeInt(this.fromProcess);
    paramParcel.writeTypedList(this.requestRecordList);
  }
  
  public static class RequestMetaRecord implements Parcelable {
    public static final Parcelable.Creator<RequestMetaRecord> CREATOR = new Parcelable.Creator<RequestMetaRecord>() {
        public final AppInfoRequestResult.RequestMetaRecord createFromParcel(Parcel param2Parcel) {
          return new AppInfoRequestResult.RequestMetaRecord(param2Parcel);
        }
        
        public final AppInfoRequestResult.RequestMetaRecord[] newArray(int param2Int) {
          return new AppInfoRequestResult.RequestMetaRecord[param2Int];
        }
      };
    
    public int code;
    
    public String data;
    
    public String message;
    
    public long startCpuTime;
    
    public long startTimeStamp;
    
    public long stopCpuTime;
    
    public long stopTimeStamp;
    
    public String throwable;
    
    public String url;
    
    public RequestMetaRecord() {}
    
    protected RequestMetaRecord(Parcel param1Parcel) {
      this.url = param1Parcel.readString();
      this.startTimeStamp = param1Parcel.readLong();
      this.startCpuTime = param1Parcel.readLong();
      this.stopTimeStamp = param1Parcel.readLong();
      this.stopCpuTime = param1Parcel.readLong();
      this.code = param1Parcel.readInt();
      this.data = param1Parcel.readString();
      this.message = param1Parcel.readString();
      this.throwable = param1Parcel.readString();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeString(this.url);
      param1Parcel.writeLong(this.startTimeStamp);
      param1Parcel.writeLong(this.startCpuTime);
      param1Parcel.writeLong(this.stopTimeStamp);
      param1Parcel.writeLong(this.stopCpuTime);
      param1Parcel.writeInt(this.code);
      param1Parcel.writeString(this.data);
      param1Parcel.writeString(this.message);
      param1Parcel.writeString(this.throwable);
    }
  }
  
  static final class null implements Parcelable.Creator<RequestMetaRecord> {
    public final AppInfoRequestResult.RequestMetaRecord createFromParcel(Parcel param1Parcel) {
      return new AppInfoRequestResult.RequestMetaRecord(param1Parcel);
    }
    
    public final AppInfoRequestResult.RequestMetaRecord[] newArray(int param1Int) {
      return new AppInfoRequestResult.RequestMetaRecord[param1Int];
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\AppInfoRequestResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */