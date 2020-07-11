package com.tt.miniapphost.process.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.tt.miniapphost.util.ProcessUtil;

public class CrossProcessCallEntity implements Parcelable {
  public static final Parcelable.Creator<CrossProcessCallEntity> CREATOR = new Parcelable.Creator<CrossProcessCallEntity>() {
      public final CrossProcessCallEntity createFromParcel(Parcel param1Parcel) {
        return new CrossProcessCallEntity(param1Parcel);
      }
      
      public final CrossProcessCallEntity[] newArray(int param1Int) {
        return new CrossProcessCallEntity[param1Int];
      }
    };
  
  private final CrossProcessDataEntity mCallData;
  
  private final CrossProcessDataEntity mCallExtraData;
  
  private final String mCallProcessIdentify;
  
  private final String mCallType;
  
  private final String mCallerProcessIdentify;
  
  protected CrossProcessCallEntity(Parcel paramParcel) {
    this.mCallerProcessIdentify = paramParcel.readString();
    this.mCallProcessIdentify = paramParcel.readString();
    this.mCallType = paramParcel.readString();
    this.mCallData = (CrossProcessDataEntity)paramParcel.readParcelable(CrossProcessDataEntity.class.getClassLoader());
    this.mCallExtraData = (CrossProcessDataEntity)paramParcel.readParcelable(CrossProcessDataEntity.class.getClassLoader());
  }
  
  public CrossProcessCallEntity(String paramString1, String paramString2, CrossProcessDataEntity paramCrossProcessDataEntity) {
    this.mCallerProcessIdentify = ProcessUtil.getProcessIdentify();
    this.mCallProcessIdentify = paramString1;
    this.mCallType = paramString2;
    this.mCallData = paramCrossProcessDataEntity;
    this.mCallExtraData = null;
  }
  
  public CrossProcessCallEntity(String paramString1, String paramString2, CrossProcessDataEntity paramCrossProcessDataEntity1, CrossProcessDataEntity paramCrossProcessDataEntity2) {
    this.mCallerProcessIdentify = ProcessUtil.getProcessIdentify();
    this.mCallProcessIdentify = paramString1;
    this.mCallType = paramString2;
    this.mCallData = paramCrossProcessDataEntity1;
    this.mCallExtraData = paramCrossProcessDataEntity2;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public CrossProcessDataEntity getCallData() {
    return this.mCallData;
  }
  
  public CrossProcessDataEntity getCallExtraData() {
    return this.mCallExtraData;
  }
  
  public String getCallProcessIdentify() {
    return this.mCallProcessIdentify;
  }
  
  public String getCallType() {
    return this.mCallType;
  }
  
  public String getCallerProcessIdentify() {
    return this.mCallerProcessIdentify;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("CrossProcessCallEntity{mCallerProcessIdentify: ");
    stringBuilder.append(this.mCallerProcessIdentify);
    stringBuilder.append(",mCallType: ");
    stringBuilder.append(this.mCallType);
    stringBuilder.append(",callData: ");
    stringBuilder.append(this.mCallData);
    stringBuilder.append(",mCallExtraData: ");
    stringBuilder.append(this.mCallExtraData);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.mCallerProcessIdentify);
    paramParcel.writeString(this.mCallProcessIdentify);
    paramParcel.writeString(this.mCallType);
    paramParcel.writeParcelable(this.mCallData, paramInt);
    paramParcel.writeParcelable(this.mCallExtraData, paramInt);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\data\CrossProcessCallEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */