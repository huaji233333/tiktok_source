package com.tencent.appbrand.mmkv;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.IOException;

public final class ParcelableMMKV implements Parcelable {
  public static final Parcelable.Creator<ParcelableMMKV> CREATOR = new Parcelable.Creator<ParcelableMMKV>() {
    
    };
  
  String a;
  
  int b = -1;
  
  int c = -1;
  
  String d;
  
  public ParcelableMMKV(MMKV paramMMKV) {
    this.a = paramMMKV.mmapID();
    this.b = paramMMKV.ashmemFD();
    this.c = paramMMKV.ashmemMetaFD();
    this.d = paramMMKV.cryptKey();
  }
  
  private ParcelableMMKV(String paramString1, int paramInt1, int paramInt2, String paramString2) {
    this.a = paramString1;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramString2;
  }
  
  public final int describeContents() {
    return 1;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt) {
    try {
      paramParcel.writeString(this.a);
      ParcelFileDescriptor parcelFileDescriptor1 = ParcelFileDescriptor.fromFd(this.b);
      ParcelFileDescriptor parcelFileDescriptor2 = ParcelFileDescriptor.fromFd(this.c);
      paramInt |= 0x1;
      parcelFileDescriptor1.writeToParcel(paramParcel, paramInt);
      parcelFileDescriptor2.writeToParcel(paramParcel, paramInt);
      if (this.d != null)
        paramParcel.writeString(this.d); 
      return;
    } catch (IOException iOException) {
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tencent\appbrand\mmkv\ParcelableMMKV.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */