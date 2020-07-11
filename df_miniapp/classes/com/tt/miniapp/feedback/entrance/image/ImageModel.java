package com.tt.miniapp.feedback.entrance.image;

public class ImageModel {
  private int id;
  
  private String img;
  
  private int status;
  
  public ImageModel(int paramInt1, String paramString, int paramInt2) {
    this.img = paramString;
    this.id = paramInt1;
    this.status = paramInt2;
  }
  
  public int getId() {
    return this.id;
  }
  
  public String getImg() {
    return this.img;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setId(int paramInt) {
    this.id = paramInt;
  }
  
  public void setImg(String paramString) {
    this.img = paramString;
  }
  
  public void setStatus(int paramInt) {
    this.status = paramInt;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\image\ImageModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */