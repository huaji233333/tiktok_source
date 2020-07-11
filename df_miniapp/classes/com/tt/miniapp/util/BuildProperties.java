package com.tt.miniapp.util;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class BuildProperties {
  private static BuildProperties ourInstance;
  
  private final Properties properties = new Properties();
  
  private BuildProperties() throws IOException {
    this.properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
  }
  
  public static BuildProperties getInstance() throws IOException {
    if (ourInstance == null)
      ourInstance = new BuildProperties(); 
    return ourInstance;
  }
  
  public boolean containsKey(Object paramObject) {
    return this.properties.containsKey(paramObject);
  }
  
  public boolean containsValue(Object paramObject) {
    return this.properties.containsValue(paramObject);
  }
  
  public Set<Map.Entry<Object, Object>> entrySet() {
    return this.properties.entrySet();
  }
  
  public String getProperty(String paramString) {
    return this.properties.getProperty(paramString);
  }
  
  public String getProperty(String paramString1, String paramString2) {
    return this.properties.getProperty(paramString1, paramString2);
  }
  
  public boolean isEmpty() {
    return this.properties.isEmpty();
  }
  
  public Set keySet() {
    return this.properties.keySet();
  }
  
  public Enumeration keys() {
    return this.properties.keys();
  }
  
  public int size() {
    return this.properties.size();
  }
  
  public Collection values() {
    return this.properties.values();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\BuildProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */