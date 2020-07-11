package com.tt.miniapp.feedback.entrance.vo;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FAQItemVO implements Parcelable {
  public static final Parcelable.Creator<FAQItemVO> CREATOR = new Parcelable.Creator<FAQItemVO>() {
      public final FAQItemVO createFromParcel(Parcel param1Parcel) {
        return new FAQItemVO(param1Parcel);
      }
      
      public final FAQItemVO[] newArray(int param1Int) {
        return new FAQItemVO[param1Int];
      }
    };
  
  private int beta;
  
  private JSONArray children;
  
  private int deleteAccount;
  
  private List<String> faqImgs;
  
  private int hideInput;
  
  private long id;
  
  private String name;
  
  private List<FAQItemVO> relatedLabels;
  
  private String url;
  
  private String value;
  
  public FAQItemVO() {}
  
  protected FAQItemVO(Parcel paramParcel) {
    this.id = paramParcel.readLong();
    this.name = paramParcel.readString();
    this.value = paramParcel.readString();
    this.faqImgs = paramParcel.createStringArrayList();
    this.hideInput = paramParcel.readInt();
    this.deleteAccount = paramParcel.readInt();
    this.beta = paramParcel.readInt();
    this.url = paramParcel.readString();
    this.relatedLabels = paramParcel.createTypedArrayList(CREATOR);
  }
  
  public static FAQItemVO from(JSONObject paramJSONObject) {
    FAQItemVO fAQItemVO = new FAQItemVO();
    fAQItemVO.setId(paramJSONObject.optLong("id"));
    fAQItemVO.setName(paramJSONObject.optString("name"));
    fAQItemVO.setChildren(paramJSONObject.optJSONArray("children"));
    fAQItemVO.setValue(paramJSONObject.optString("value"));
    JSONArray jSONArray = paramJSONObject.optJSONArray("related_labels");
    ArrayList<FAQItemVO> arrayList = new ArrayList(jSONArray.length());
    for (int i = 0; i < jSONArray.length(); i++)
      arrayList.add(from(jSONArray.optJSONObject(i))); 
    fAQItemVO.setRelatedLabels(arrayList);
    return fAQItemVO;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public int getBeta() {
    return this.beta;
  }
  
  public JSONArray getChildren() {
    return this.children;
  }
  
  public int getDeleteAccount() {
    return this.deleteAccount;
  }
  
  public List<String> getFaqImgs() {
    return this.faqImgs;
  }
  
  public int getHideInput() {
    return this.hideInput;
  }
  
  public long getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public List<FAQItemVO> getRelatedLabels() {
    return this.relatedLabels;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public void setBeta(int paramInt) {
    this.beta = paramInt;
  }
  
  public void setChildren(JSONArray paramJSONArray) {
    this.children = paramJSONArray;
  }
  
  public void setDeleteAccount(int paramInt) {
    this.deleteAccount = paramInt;
  }
  
  public void setFaqImgs(List<String> paramList) {
    this.faqImgs = paramList;
  }
  
  public void setHideInput(int paramInt) {
    this.hideInput = paramInt;
  }
  
  public void setId(long paramLong) {
    this.id = paramLong;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public void setRelatedLabels(List<FAQItemVO> paramList) {
    this.relatedLabels = paramList;
  }
  
  public void setUrl(String paramString) {
    this.url = paramString;
  }
  
  public void setValue(String paramString) {
    this.value = paramString;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeLong(this.id);
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.value);
    paramParcel.writeStringList(this.faqImgs);
    paramParcel.writeInt(this.hideInput);
    paramParcel.writeInt(this.deleteAccount);
    paramParcel.writeInt(this.beta);
    paramParcel.writeString(this.url);
    paramParcel.writeTypedList(this.relatedLabels);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\vo\FAQItemVO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */