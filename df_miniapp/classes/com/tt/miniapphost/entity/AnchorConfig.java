package com.tt.miniapphost.entity;

import android.app.Application;
import android.graphics.Color;
import android.text.TextUtils;
import com.tt.miniapphost.AppbrandContext;

public class AnchorConfig {
  private String action = "ADD";
  
  private String anchorExtra;
  
  private String backgroundColor;
  
  private String channel;
  
  private String replaceTitle;
  
  private String snapshotUrl;
  
  private String title;
  
  private int getDefaultColor() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    return (application != null) ? application.getResources().getColor(2097348610) : Color.parseColor("#F85959");
  }
  
  public String getAction() {
    return this.action;
  }
  
  public String getAnchorExtra() {
    return this.anchorExtra;
  }
  
  public int getBackgroundColor() {
    try {
      if (!TextUtils.isEmpty(this.backgroundColor)) {
        if (this.backgroundColor.charAt(0) != '#') {
          StringBuilder stringBuilder = new StringBuilder("#");
          stringBuilder.append(this.backgroundColor);
          this.backgroundColor = stringBuilder.toString();
        } 
        return Color.parseColor(this.backgroundColor);
      } 
      return getDefaultColor();
    } catch (Exception exception) {
      return getDefaultColor();
    } 
  }
  
  public String getChannel() {
    if (TextUtils.isEmpty(this.channel))
      this.channel = "anchor"; 
    return this.channel;
  }
  
  public String getReplaceTitle() {
    return this.replaceTitle;
  }
  
  public String getSnapshotUrl() {
    return this.snapshotUrl;
  }
  
  public String getTitle() {
    String str;
    if (TextUtils.isEmpty(this.title)) {
      Application application = AppbrandContext.getInst().getApplicationContext();
      str = getAction();
      if (application != null) {
        if ("ADD".equals(str)) {
          this.title = application.getResources().getString(2097741860);
        } else {
          if ("REMOVE".equals(str)) {
            this.title = application.getResources().getString(2097741861);
            return this.title;
          } 
          this.title = str;
        } 
        return this.title;
      } 
    } else {
      return this.title;
    } 
    this.title = str;
  }
  
  public void setAction(String paramString) {
    this.action = paramString;
  }
  
  public void setAnchorExtra(String paramString) {
    this.anchorExtra = paramString;
  }
  
  public void setBackgroundColor(String paramString) {
    this.backgroundColor = paramString;
  }
  
  public void setChannel(String paramString) {
    this.channel = paramString;
  }
  
  public void setReplaceTitle(String paramString) {
    this.replaceTitle = paramString;
  }
  
  public void setSnapshotUrl(String paramString) {
    this.snapshotUrl = paramString;
  }
  
  public void setTitle(String paramString) {
    this.title = paramString;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\AnchorConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */