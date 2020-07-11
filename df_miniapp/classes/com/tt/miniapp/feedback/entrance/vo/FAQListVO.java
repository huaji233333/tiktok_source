package com.tt.miniapp.feedback.entrance.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class FAQListVO implements Parcelable {
  public static final Parcelable.Creator<FAQListVO> CREATOR = new Parcelable.Creator<FAQListVO>() {
      public final FAQListVO createFromParcel(Parcel param1Parcel) {
        return new FAQListVO(param1Parcel);
      }
      
      public final FAQListVO[] newArray(int param1Int) {
        return new FAQListVO[param1Int];
      }
    };
  
  private String list;
  
  private int message;
  
  protected FAQListVO(Parcel paramParcel) {
    this.message = paramParcel.readInt();
    this.list = paramParcel.readString();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public String getList() {
    return this.list;
  }
  
  public int getMessage() {
    return this.message;
  }
  
  public void setList(String paramString) {
    this.list = paramString;
  }
  
  public void setMessage(int paramInt) {
    this.message = paramInt;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.message);
    paramParcel.writeString(this.list);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\vo\FAQListVO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */