package com.tt.miniapphost.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaEntity implements Parcelable {
  public static final Parcelable.Creator<MediaEntity> CREATOR = new Parcelable.Creator<MediaEntity>() {
      public final MediaEntity createFromParcel(Parcel param1Parcel) {
        return new MediaEntity(param1Parcel);
      }
      
      public final MediaEntity[] newArray(int param1Int) {
        return new MediaEntity[param1Int];
      }
    };
  
  public int id;
  
  public int mediaType;
  
  public String name;
  
  public String parentDir;
  
  public String path;
  
  public long size;
  
  public long time;
  
  protected MediaEntity(Parcel paramParcel) {
    this.path = paramParcel.readString();
    this.name = paramParcel.readString();
    this.time = paramParcel.readLong();
    this.mediaType = paramParcel.readInt();
    this.size = paramParcel.readLong();
    this.id = paramParcel.readInt();
    this.parentDir = paramParcel.readString();
  }
  
  public MediaEntity(String paramString1, String paramString2, long paramLong1, int paramInt1, long paramLong2, int paramInt2, String paramString3) {
    this.path = paramString1;
    this.name = paramString2;
    this.time = paramLong1;
    this.mediaType = paramInt1;
    this.size = paramLong2;
    this.id = paramInt2;
    this.parentDir = paramString3;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.path);
    paramParcel.writeString(this.name);
    paramParcel.writeLong(this.time);
    paramParcel.writeInt(this.mediaType);
    paramParcel.writeLong(this.size);
    paramParcel.writeInt(this.id);
    paramParcel.writeString(this.parentDir);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\MediaEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */