package com.tt.miniapp.mmkv;

import android.content.SharedPreferences;
import android.os.Message;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

class MigrationSharedPreferences implements SharedPreferences {
  private SharedPreferences mmkvSp;
  
  private SharedPreferences newSp;
  
  MigrationSharedPreferences(SharedPreferences paramSharedPreferences1, SharedPreferences paramSharedPreferences2) {
    this.newSp = paramSharedPreferences1;
    this.mmkvSp = paramSharedPreferences2;
  }
  
  public boolean contains(String paramString) {
    return (this.mmkvSp.contains(paramString) || this.newSp.contains(paramString));
  }
  
  public SharedPreferences.Editor edit() {
    return new MigrationEditor(this.newSp.edit(), this.mmkvSp.edit());
  }
  
  public Map<String, ?> getAll() {
    return this.newSp.getAll();
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean) {
    Message message;
    if (this.newSp.contains(paramString)) {
      paramBoolean = this.newSp.getBoolean(paramString, paramBoolean);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        message = MMKVUsageMonitorHandler.getInstance().obtainMessage(1, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message);
      } 
      return paramBoolean;
    } 
    boolean bool = paramBoolean;
    if (this.mmkvSp.contains((String)message)) {
      bool = this.mmkvSp.getBoolean((String)message, paramBoolean);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        Message message1 = MMKVUsageMonitorHandler.getInstance().obtainMessage(2, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message1);
      } 
      this.newSp.edit().putBoolean((String)message, bool).apply();
    } 
    return bool;
  }
  
  public float getFloat(String paramString, float paramFloat) {
    Message message;
    if (this.newSp.contains(paramString)) {
      paramFloat = this.newSp.getFloat(paramString, paramFloat);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        message = MMKVUsageMonitorHandler.getInstance().obtainMessage(1, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message);
      } 
      return paramFloat;
    } 
    float f = paramFloat;
    if (this.mmkvSp.contains((String)message)) {
      f = this.mmkvSp.getFloat((String)message, paramFloat);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        Message message1 = MMKVUsageMonitorHandler.getInstance().obtainMessage(2, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message1);
      } 
      this.newSp.edit().putFloat((String)message, f).apply();
    } 
    return f;
  }
  
  public int getInt(String paramString, int paramInt) {
    Message message;
    if (this.newSp.contains(paramString)) {
      paramInt = this.newSp.getInt(paramString, paramInt);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        message = MMKVUsageMonitorHandler.getInstance().obtainMessage(1, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message);
      } 
      return paramInt;
    } 
    int i = paramInt;
    if (this.mmkvSp.contains((String)message)) {
      i = this.mmkvSp.getInt((String)message, paramInt);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        Message message1 = MMKVUsageMonitorHandler.getInstance().obtainMessage(2, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message1);
      } 
      this.newSp.edit().putInt((String)message, i).apply();
    } 
    return i;
  }
  
  public long getLong(String paramString, long paramLong) {
    Message message;
    if (this.newSp.contains(paramString)) {
      paramLong = this.newSp.getLong(paramString, paramLong);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        message = MMKVUsageMonitorHandler.getInstance().obtainMessage(1, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message);
      } 
      return paramLong;
    } 
    long l = paramLong;
    if (this.mmkvSp.contains((String)message)) {
      l = this.mmkvSp.getLong((String)message, paramLong);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        Message message1 = MMKVUsageMonitorHandler.getInstance().obtainMessage(2, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message1);
      } 
      this.newSp.edit().putLong((String)message, l).apply();
    } 
    return l;
  }
  
  public String getString(String paramString1, String paramString2) {
    Message message1;
    String str;
    if (this.newSp.contains(paramString1)) {
      paramString1 = this.newSp.getString(paramString1, paramString2);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        message1 = MMKVUsageMonitorHandler.getInstance().obtainMessage(1, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message1);
      } 
      return paramString1;
    } 
    Message message2 = message1;
    if (this.mmkvSp.contains(paramString1)) {
      str = this.mmkvSp.getString(paramString1, (String)message1);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        message1 = MMKVUsageMonitorHandler.getInstance().obtainMessage(2, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message1);
      } 
      this.newSp.edit().putString(paramString1, str).apply();
    } 
    return str;
  }
  
  public Set<String> getStringSet(String paramString, Set<String> paramSet) {
    Set<String> set1;
    Message message1;
    Set<String> set2;
    if (this.newSp.contains(paramString)) {
      set1 = this.newSp.getStringSet(paramString, paramSet);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        message1 = MMKVUsageMonitorHandler.getInstance().obtainMessage(1, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message1);
      } 
      return set1;
    } 
    Message message2 = message1;
    if (this.mmkvSp.contains((String)set1)) {
      set2 = this.mmkvSp.getStringSet((String)set1, (Set)message1);
      if (ThreadLocalRandom.current().nextInt(1000) == 666) {
        message1 = MMKVUsageMonitorHandler.getInstance().obtainMessage(2, null);
        MMKVUsageMonitorHandler.getInstance().sendMessage(message1);
      } 
      this.newSp.edit().putStringSet((String)set1, set2).apply();
    } 
    return set2;
  }
  
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {
    this.newSp.registerOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
  }
  
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {
    this.newSp.unregisterOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
  }
  
  public static class MigrationEditor implements SharedPreferences.Editor {
    private SharedPreferences.Editor mmkvSpEditor;
    
    private SharedPreferences.Editor newSpEditor;
    
    public MigrationEditor(SharedPreferences.Editor param1Editor1, SharedPreferences.Editor param1Editor2) {
      this.newSpEditor = param1Editor1;
      this.mmkvSpEditor = param1Editor2;
    }
    
    public void apply() {
      this.mmkvSpEditor.apply();
      this.newSpEditor.apply();
    }
    
    public SharedPreferences.Editor clear() {
      this.mmkvSpEditor.clear();
      this.newSpEditor.clear();
      return this;
    }
    
    public boolean commit() {
      boolean bool1 = this.mmkvSpEditor.commit();
      boolean bool2 = this.newSpEditor.commit();
      return (bool1 && bool2);
    }
    
    public SharedPreferences.Editor putBoolean(String param1String, boolean param1Boolean) {
      return this.newSpEditor.putBoolean(param1String, param1Boolean);
    }
    
    public SharedPreferences.Editor putFloat(String param1String, float param1Float) {
      return this.newSpEditor.putFloat(param1String, param1Float);
    }
    
    public SharedPreferences.Editor putInt(String param1String, int param1Int) {
      return this.newSpEditor.putInt(param1String, param1Int);
    }
    
    public SharedPreferences.Editor putLong(String param1String, long param1Long) {
      return this.newSpEditor.putLong(param1String, param1Long);
    }
    
    public SharedPreferences.Editor putString(String param1String1, String param1String2) {
      return this.newSpEditor.putString(param1String1, param1String2);
    }
    
    public SharedPreferences.Editor putStringSet(String param1String, Set<String> param1Set) {
      return this.newSpEditor.putStringSet(param1String, param1Set);
    }
    
    public SharedPreferences.Editor remove(String param1String) {
      this.mmkvSpEditor.remove(param1String);
      this.newSpEditor.remove(param1String);
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\mmkv\MigrationSharedPreferences.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */