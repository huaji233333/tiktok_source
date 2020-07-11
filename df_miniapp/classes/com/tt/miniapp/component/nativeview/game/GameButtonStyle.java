package com.tt.miniapp.component.nativeview.game;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapphost.util.UIUtils;
import java.util.Objects;
import org.json.JSONObject;

public class GameButtonStyle implements Cloneable {
  public String backgroundColor = "#000000";
  
  public String borderColor = "#000000";
  
  public int borderRadius;
  
  public int borderWidth;
  
  public String content;
  
  public int fontSize = 16;
  
  public int height;
  
  public int left;
  
  public int lineHeight = 40;
  
  public String textAlign = "center";
  
  public String textColor = "#000000";
  
  public int top;
  
  public int width;
  
  public static GameButtonStyle parse(Context paramContext, String paramString, JSONObject paramJSONObject) {
    return parse(null, paramContext, paramString, paramJSONObject);
  }
  
  public static GameButtonStyle parse(GameButtonStyle paramGameButtonStyle, Context paramContext, String paramString, JSONObject paramJSONObject) {
    GameButtonStyle gameButtonStyle = paramGameButtonStyle;
    if (paramGameButtonStyle == null)
      gameButtonStyle = new GameButtonStyle(); 
    if (!TextUtils.isEmpty(paramString))
      gameButtonStyle.content = paramString; 
    if (paramJSONObject == null)
      return gameButtonStyle; 
    gameButtonStyle.left = (int)UIUtils.dip2Px(paramContext, paramJSONObject.optInt("left", gameButtonStyle.left));
    gameButtonStyle.top = (int)UIUtils.dip2Px(paramContext, paramJSONObject.optInt("top", gameButtonStyle.top));
    gameButtonStyle.width = (int)UIUtils.dip2Px(paramContext, paramJSONObject.optInt("width", gameButtonStyle.width));
    gameButtonStyle.height = (int)UIUtils.dip2Px(paramContext, paramJSONObject.optInt("height", gameButtonStyle.height));
    gameButtonStyle.backgroundColor = paramJSONObject.optString("backgroundColor", gameButtonStyle.backgroundColor);
    gameButtonStyle.borderColor = paramJSONObject.optString("borderColor", gameButtonStyle.borderColor);
    gameButtonStyle.borderWidth = (int)UIUtils.dip2Px(paramContext, paramJSONObject.optInt("borderWidth", gameButtonStyle.borderWidth));
    gameButtonStyle.borderRadius = (int)UIUtils.dip2Px(paramContext, paramJSONObject.optInt("borderRadius", gameButtonStyle.borderRadius));
    gameButtonStyle.textAlign = paramJSONObject.optString("textAlign", gameButtonStyle.textAlign);
    gameButtonStyle.fontSize = paramJSONObject.optInt("fontSize", gameButtonStyle.fontSize);
    gameButtonStyle.lineHeight = (int)UIUtils.dip2Px(paramContext, paramJSONObject.optInt("lineHeight", gameButtonStyle.lineHeight));
    gameButtonStyle.textColor = paramJSONObject.optString("textColor", gameButtonStyle.textColor);
    return gameButtonStyle;
  }
  
  public GameButtonStyle clone() {
    GameButtonStyle gameButtonStyle;
    try {
      gameButtonStyle = (GameButtonStyle)super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      gameButtonStyle = new GameButtonStyle();
    } 
    gameButtonStyle.content = this.content;
    gameButtonStyle.left = this.left;
    gameButtonStyle.top = this.top;
    gameButtonStyle.width = this.width;
    gameButtonStyle.height = this.height;
    gameButtonStyle.backgroundColor = this.backgroundColor;
    gameButtonStyle.borderColor = this.borderColor;
    gameButtonStyle.textAlign = this.textAlign;
    gameButtonStyle.textColor = this.textColor;
    gameButtonStyle.borderWidth = this.borderWidth;
    gameButtonStyle.borderRadius = this.borderRadius;
    gameButtonStyle.fontSize = this.fontSize;
    gameButtonStyle.lineHeight = this.lineHeight;
    return gameButtonStyle;
  }
  
  public byte compare(GameButtonStyle paramGameButtonStyle) {
    boolean bool1;
    boolean bool = TextUtils.equals(this.content, paramGameButtonStyle.content);
    boolean bool2 = true;
    int j = bool ^ true;
    if (this.left == paramGameButtonStyle.left && this.top == paramGameButtonStyle.top && this.width == paramGameButtonStyle.width && this.height == paramGameButtonStyle.height) {
      k = 1;
    } else {
      k = 0;
    } 
    int i = j;
    if (!k)
      i = (byte)(j | 0x2); 
    String str1 = this.backgroundColor;
    String str2 = this.borderColor;
    String str3 = this.textAlign;
    String str4 = this.textColor;
    int m = this.borderWidth;
    int n = this.borderRadius;
    int i1 = this.fontSize;
    int i2 = this.lineHeight;
    String str5 = paramGameButtonStyle.backgroundColor;
    String str6 = paramGameButtonStyle.borderColor;
    String str7 = paramGameButtonStyle.textAlign;
    String str8 = paramGameButtonStyle.textColor;
    int i3 = paramGameButtonStyle.borderWidth;
    int i4 = paramGameButtonStyle.borderRadius;
    int i5 = paramGameButtonStyle.fontSize;
    int i6 = paramGameButtonStyle.lineHeight;
    int k = 0;
    while (true) {
      bool1 = bool2;
      if (k < 8) {
        (new Object[8])[0] = str1;
        (new Object[8])[1] = str2;
        (new Object[8])[2] = str3;
        (new Object[8])[3] = str4;
        (new Object[8])[4] = Integer.valueOf(m);
        (new Object[8])[5] = Integer.valueOf(n);
        (new Object[8])[6] = Integer.valueOf(i1);
        (new Object[8])[7] = Integer.valueOf(i2);
        (new Object[8])[0] = str5;
        (new Object[8])[1] = str6;
        (new Object[8])[2] = str7;
        (new Object[8])[3] = str8;
        (new Object[8])[4] = Integer.valueOf(i3);
        (new Object[8])[5] = Integer.valueOf(i4);
        (new Object[8])[6] = Integer.valueOf(i5);
        (new Object[8])[7] = Integer.valueOf(i6);
        if (!Objects.equals((new Object[8])[k], (new Object[8])[k])) {
          bool1 = false;
          break;
        } 
        k++;
        continue;
      } 
      break;
    } 
    j = i;
    if (!bool1)
      j = (byte)(i | 0x4); 
    return j;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\GameButtonStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */