package com.tt.miniapp.shortcut;

import android.content.Intent;
import android.net.Uri;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.CharacterUtils;
import java.util.HashMap;

public class ShortcutEntity {
  public String appId;
  
  public int appType;
  
  public String icon;
  
  public String label;
  
  public String query;
  
  public String shortcutMd5;
  
  private String buildLaunchSchema() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("launch_from", "desktop");
    hashMap.put("location", "short_cut");
    return (new MicroSchemaEntity.Builder()).appId(this.appId).host(getHost(this.appType)).bdpLog(hashMap).scene(HostDependManager.getInst().getScene("desktop")).build().toSchema();
  }
  
  private MicroSchemaEntity.Host getHost(int paramInt) {
    return (paramInt == 2) ? MicroSchemaEntity.Host.MICROGAME : MicroSchemaEntity.Host.MICROAPP;
  }
  
  public String getAppId() {
    return this.appId;
  }
  
  public int getAppType() {
    return this.appType;
  }
  
  public String getIcon() {
    return this.icon;
  }
  
  public String getLabel() {
    return this.label;
  }
  
  public String getQuery() {
    return this.query;
  }
  
  public String getShortcutMd5() {
    return this.shortcutMd5;
  }
  
  public void wrapIntent(Intent paramIntent) {
    if (paramIntent == null)
      return; 
    paramIntent.putExtra("key_shortcut_id", this.shortcutMd5);
    paramIntent.setData(Uri.parse(buildLaunchSchema()));
  }
  
  public static class Builder {
    private ShortcutEntity mEntity = new ShortcutEntity();
    
    private String createShortcutIdentify() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mEntity.appId);
      stringBuilder.append(this.mEntity.icon);
      stringBuilder.append(this.mEntity.label);
      return CharacterUtils.md5Hex(stringBuilder.toString());
    }
    
    public ShortcutEntity build() {
      this.mEntity.shortcutMd5 = createShortcutIdentify();
      return this.mEntity;
    }
    
    public Builder setAppId(String param1String) {
      this.mEntity.appId = param1String;
      return this;
    }
    
    public Builder setAppType(int param1Int) {
      this.mEntity.appType = param1Int;
      return this;
    }
    
    public Builder setIcon(String param1String) {
      this.mEntity.icon = param1String;
      return this;
    }
    
    public Builder setLabel(String param1String) {
      this.mEntity.label = param1String;
      return this;
    }
    
    public Builder setQuery(String param1String) {
      this.mEntity.query = param1String;
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\ShortcutEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */