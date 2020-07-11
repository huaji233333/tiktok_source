package com.tt.miniapp.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.tt.miniapphost.entity.MediaEntity;
import java.util.ArrayList;

public class Folder implements Parcelable {
  public static final Parcelable.Creator<Folder> CREATOR = new Parcelable.Creator<Folder>() {
      public final Folder createFromParcel(Parcel param1Parcel) {
        return new Folder(param1Parcel);
      }
      
      public final Folder[] newArray(int param1Int) {
        return new Folder[param1Int];
      }
    };
  
  public int count;
  
  ArrayList<MediaEntity> medias = new ArrayList<MediaEntity>();
  
  public String name;
  
  protected Folder(Parcel paramParcel) {
    this.name = paramParcel.readString();
    this.count = paramParcel.readInt();
    this.medias = paramParcel.createTypedArrayList(MediaEntity.CREATOR);
  }
  
  public Folder(String paramString) {
    this.name = paramString;
  }
  
  public void addMedias(MediaEntity paramMediaEntity) {
    this.medias.add(paramMediaEntity);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public ArrayList<MediaEntity> getMedias() {
    return this.medias;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.name);
    paramParcel.writeInt(this.count);
    paramParcel.writeTypedList(this.medias);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\entity\Folder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */