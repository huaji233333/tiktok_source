package com.tt.miniapp.feedback.entrance.vo;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageInfoVO implements Parcelable {
  public static final Parcelable.Creator<ImageInfoVO> CREATOR = new Parcelable.Creator<ImageInfoVO>() {
      public final ImageInfoVO createFromParcel(Parcel param1Parcel) {
        return new ImageInfoVO(param1Parcel);
      }
      
      public final ImageInfoVO[] newArray(int param1Int) {
        return new ImageInfoVO[param1Int];
      }
    };
  
  private String format;
  
  private int height;
  
  private List<String> urlList;
  
  private String webUri;
  
  private int width;
  
  public ImageInfoVO() {}
  
  protected ImageInfoVO(Parcel paramParcel) {
    this.webUri = paramParcel.readString();
    this.format = paramParcel.readString();
    this.height = paramParcel.readInt();
    this.width = paramParcel.readInt();
    this.urlList = paramParcel.createStringArrayList();
  }
  
  public static ImageInfoVO from(JSONObject paramJSONObject) throws JSONException {
    ImageInfoVO imageInfoVO = new ImageInfoVO();
    imageInfoVO.setWebUri(paramJSONObject.optString("web_uri"));
    imageInfoVO.setFormat(paramJSONObject.optString("format"));
    imageInfoVO.setHeight(paramJSONObject.optInt("height"));
    imageInfoVO.setWidth(paramJSONObject.optInt("width"));
    JSONArray jSONArray = paramJSONObject.optJSONArray("url_list");
    ArrayList<String> arrayList = new ArrayList();
    for (int i = 0; i < jSONArray.length(); i++)
      arrayList.add(jSONArray.optJSONObject(i).optString("url")); 
    imageInfoVO.setUrlList(arrayList);
    return imageInfoVO;
  }
  
  public static JSONObject toJsonObject(ImageInfoVO paramImageInfoVO) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    if (paramImageInfoVO != null && paramImageInfoVO.getUrlList() != null && !paramImageInfoVO.getUrlList().isEmpty()) {
      jSONObject.put("image_uri", paramImageInfoVO.getUrlList().get(0));
      jSONObject.put("image_width", paramImageInfoVO.getWidth());
      jSONObject.put("image_height", paramImageInfoVO.getHeight());
    } 
    return jSONObject;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public String getFormat() {
    return this.format;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public List<String> getUrlList() {
    return this.urlList;
  }
  
  public String getWebUri() {
    return this.webUri;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public void setFormat(String paramString) {
    this.format = paramString;
  }
  
  public void setHeight(int paramInt) {
    this.height = paramInt;
  }
  
  public void setUrlList(List<String> paramList) {
    this.urlList = paramList;
  }
  
  public void setWebUri(String paramString) {
    this.webUri = paramString;
  }
  
  public void setWidth(int paramInt) {
    this.width = paramInt;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.webUri);
    paramParcel.writeString(this.format);
    paramParcel.writeInt(this.height);
    paramParcel.writeInt(this.width);
    paramParcel.writeStringList(this.urlList);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\vo\ImageInfoVO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */