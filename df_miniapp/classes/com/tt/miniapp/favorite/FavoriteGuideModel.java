package com.tt.miniapp.favorite;

public class FavoriteGuideModel {
  public String content;
  
  public String position;
  
  public String type;
  
  public FavoriteGuideModel(String paramString1, String paramString2, String paramString3) {
    this.type = paramString1;
    this.content = paramString2;
    this.position = paramString3;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("FavoriteGuideModel{type='");
    stringBuilder.append(this.type);
    stringBuilder.append('\'');
    stringBuilder.append(", content='");
    stringBuilder.append(this.content);
    stringBuilder.append('\'');
    stringBuilder.append(", position='");
    stringBuilder.append(this.position);
    stringBuilder.append('\'');
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\favorite\FavoriteGuideModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */