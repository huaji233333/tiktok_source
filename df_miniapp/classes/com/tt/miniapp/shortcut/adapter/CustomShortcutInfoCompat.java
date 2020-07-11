package com.tt.miniapp.shortcut.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import java.util.Arrays;

public class CustomShortcutInfoCompat {
  public ComponentName mActivity;
  
  public PersistableBundle mBundle;
  
  public Context mContext;
  
  public CharSequence mDisabledMessage;
  
  public CustomIconCompat mIcon;
  
  public String mId;
  
  public Intent[] mIntents;
  
  public CharSequence mLabel;
  
  public CharSequence mLongLabel;
  
  private CustomShortcutInfoCompat() {}
  
  Intent addToIntent(Intent paramIntent) {
    Intent[] arrayOfIntent = this.mIntents;
    paramIntent.putExtra("android.intent.extra.shortcut.INTENT", (Parcelable)arrayOfIntent[arrayOfIntent.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
    CustomIconCompat customIconCompat = this.mIcon;
    if (customIconCompat != null)
      customIconCompat.addToShortcutIntent(paramIntent); 
    return paramIntent;
  }
  
  public ComponentName getActivity() {
    return this.mActivity;
  }
  
  public CharSequence getDisabledMessage() {
    return this.mDisabledMessage;
  }
  
  public String getId() {
    return this.mId;
  }
  
  public Intent getIntent() {
    Intent[] arrayOfIntent = this.mIntents;
    return arrayOfIntent[arrayOfIntent.length - 1];
  }
  
  public Intent[] getIntents() {
    Intent[] arrayOfIntent = this.mIntents;
    return Arrays.<Intent>copyOf(arrayOfIntent, arrayOfIntent.length);
  }
  
  public CharSequence getLongLabel() {
    return this.mLongLabel;
  }
  
  public CharSequence getShortLabel() {
    return this.mLabel;
  }
  
  public ShortcutInfo toShortcutInfo() {
    ShortcutInfo.Builder builder = (new ShortcutInfo.Builder(this.mContext, this.mId)).setShortLabel(this.mLabel).setIntents(this.mIntents);
    CustomIconCompat customIconCompat = this.mIcon;
    if (customIconCompat != null)
      builder.setIcon(customIconCompat.toIcon()); 
    if (!TextUtils.isEmpty(this.mLongLabel))
      builder.setLongLabel(this.mLongLabel); 
    if (!TextUtils.isEmpty(this.mDisabledMessage))
      builder.setDisabledMessage(this.mDisabledMessage); 
    ComponentName componentName = this.mActivity;
    if (componentName != null)
      builder.setActivity(componentName); 
    PersistableBundle persistableBundle = this.mBundle;
    if (persistableBundle != null)
      builder.setExtras(persistableBundle); 
    return builder.build();
  }
  
  public static class Builder {
    private final CustomShortcutInfoCompat mInfo = new CustomShortcutInfoCompat();
    
    public Builder(Context param1Context, String param1String) {
      CustomShortcutInfoCompat customShortcutInfoCompat = this.mInfo;
      customShortcutInfoCompat.mContext = param1Context;
      customShortcutInfoCompat.mId = param1String;
    }
    
    public CustomShortcutInfoCompat build() {
      if (!TextUtils.isEmpty(this.mInfo.mLabel)) {
        if (this.mInfo.mIntents != null && this.mInfo.mIntents.length != 0)
          return this.mInfo; 
        throw new IllegalArgumentException("Shortcut much have an intent");
      } 
      throw new IllegalArgumentException("Shortcut much have a non-empty label");
    }
    
    public Builder setActivity(ComponentName param1ComponentName) {
      this.mInfo.mActivity = param1ComponentName;
      return this;
    }
    
    public Builder setDisabledMessage(CharSequence param1CharSequence) {
      this.mInfo.mDisabledMessage = param1CharSequence;
      return this;
    }
    
    public Builder setExtra(PersistableBundle param1PersistableBundle) {
      this.mInfo.mBundle = param1PersistableBundle;
      return this;
    }
    
    public Builder setIcon(CustomIconCompat param1CustomIconCompat) {
      this.mInfo.mIcon = param1CustomIconCompat;
      return this;
    }
    
    public Builder setIntent(Intent param1Intent) {
      return setIntents(new Intent[] { param1Intent });
    }
    
    public Builder setIntents(Intent[] param1ArrayOfIntent) {
      this.mInfo.mIntents = param1ArrayOfIntent;
      return this;
    }
    
    public Builder setLongLabel(CharSequence param1CharSequence) {
      this.mInfo.mLongLabel = param1CharSequence;
      return this;
    }
    
    public Builder setShortLabel(CharSequence param1CharSequence) {
      this.mInfo.mLabel = param1CharSequence;
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\adapter\CustomShortcutInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */