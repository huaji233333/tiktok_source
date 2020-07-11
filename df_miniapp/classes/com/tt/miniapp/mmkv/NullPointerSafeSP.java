package com.tt.miniapp.mmkv;

import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NullPointerSafeSP implements SharedPreferences {
  public boolean contains(String paramString) {
    return false;
  }
  
  public SharedPreferences.Editor edit() {
    return new EditorImpl();
  }
  
  public Map<String, ?> getAll() {
    return new HashMap<String, Object>();
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean) {
    return paramBoolean;
  }
  
  public float getFloat(String paramString, float paramFloat) {
    return paramFloat;
  }
  
  public int getInt(String paramString, int paramInt) {
    return paramInt;
  }
  
  public long getLong(String paramString, long paramLong) {
    return paramLong;
  }
  
  public String getString(String paramString1, String paramString2) {
    return paramString2;
  }
  
  public Set<String> getStringSet(String paramString, Set<String> paramSet) {
    return paramSet;
  }
  
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {}
  
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {}
  
  class EditorImpl implements SharedPreferences.Editor {
    public void apply() {}
    
    public SharedPreferences.Editor clear() {
      return this;
    }
    
    public boolean commit() {
      return false;
    }
    
    public SharedPreferences.Editor putBoolean(String param1String, boolean param1Boolean) {
      return this;
    }
    
    public SharedPreferences.Editor putFloat(String param1String, float param1Float) {
      return this;
    }
    
    public SharedPreferences.Editor putInt(String param1String, int param1Int) {
      return this;
    }
    
    public SharedPreferences.Editor putLong(String param1String, long param1Long) {
      return this;
    }
    
    public SharedPreferences.Editor putString(String param1String1, String param1String2) {
      return this;
    }
    
    public SharedPreferences.Editor putStringSet(String param1String, Set<String> param1Set) {
      return this;
    }
    
    public SharedPreferences.Editor remove(String param1String) {
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\mmkv\NullPointerSafeSP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */