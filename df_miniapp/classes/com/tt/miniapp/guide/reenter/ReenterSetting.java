package com.tt.miniapp.guide.reenter;

import com.tt.miniapphost.util.CharacterUtils;
import java.util.ArrayList;

public class ReenterSetting {
  public ArrayList<String> blackList = new ArrayList<String>();
  
  public String buttonColor = CharacterUtils.empty();
  
  public String buttonText = CharacterUtils.empty();
  
  public int count = 0;
  
  public String image = CharacterUtils.empty();
  
  public int nowCnt = -1;
  
  public String title = CharacterUtils.empty();
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("{image: ");
    stringBuilder.append(this.image);
    stringBuilder.append(",count: ");
    stringBuilder.append(this.count);
    stringBuilder.append(",title: ");
    stringBuilder.append(this.title);
    stringBuilder.append(",buttonColor: ");
    stringBuilder.append(this.buttonColor);
    stringBuilder.append(",buttonText: ");
    stringBuilder.append(this.buttonText);
    stringBuilder.append("blackList: ");
    stringBuilder.append(this.blackList.toString());
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\guide\reenter\ReenterSetting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */