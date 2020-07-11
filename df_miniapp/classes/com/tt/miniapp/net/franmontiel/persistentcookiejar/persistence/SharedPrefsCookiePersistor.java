package com.tt.miniapp.net.franmontiel.persistentcookiejar.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.appbrand.mmkv.MMKV;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.AppbrandConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.l;

public class SharedPrefsCookiePersistor implements CookiePersistor {
  private final SharedPreferences sharedPreferences;
  
  public SharedPrefsCookiePersistor(Context paramContext) {
    this(KVUtil.getSharedPreferences(paramContext, AppbrandConstants.SharePreferences.getCookieSp()));
  }
  
  public SharedPrefsCookiePersistor(SharedPreferences paramSharedPreferences) {
    this.sharedPreferences = paramSharedPreferences;
  }
  
  private static String createCookieKey(l paraml) {
    String str;
    StringBuilder stringBuilder = new StringBuilder();
    if (paraml.f) {
      str = "https";
    } else {
      str = "http";
    } 
    stringBuilder.append(str);
    stringBuilder.append("://");
    stringBuilder.append(paraml.d);
    stringBuilder.append(paraml.e);
    stringBuilder.append("|");
    stringBuilder.append(paraml.a);
    return stringBuilder.toString();
  }
  
  private List<l> loadAllByMMKV() {
    String[] arrayOfString = ((MMKV)this.sharedPreferences).allKeysMMKV();
    if (arrayOfString == null)
      return new ArrayList<l>(); 
    ArrayList<l> arrayList = new ArrayList(arrayOfString.length);
    int j = arrayOfString.length;
    for (int i = 0; i < j; i++) {
      String str = arrayOfString[i];
      str = this.sharedPreferences.getString(str, "");
      l l = (new SerializableCookie()).decode(str);
      if (l != null)
        arrayList.add(l); 
    } 
    return arrayList;
  }
  
  private List<l> loadAllBySP() {
    ArrayList<l> arrayList = new ArrayList(this.sharedPreferences.getAll().size());
    Iterator<Map.Entry> iterator = this.sharedPreferences.getAll().entrySet().iterator();
    while (iterator.hasNext()) {
      String str = (String)((Map.Entry)iterator.next()).getValue();
      l l = (new SerializableCookie()).decode(str);
      if (l != null)
        arrayList.add(l); 
    } 
    return arrayList;
  }
  
  public void clear() {
    this.sharedPreferences.edit().clear().commit();
  }
  
  public List<l> loadAll() {
    return (this.sharedPreferences instanceof MMKV) ? loadAllByMMKV() : loadAllBySP();
  }
  
  public void removeAll(Collection<l> paramCollection) {
    SharedPreferences.Editor editor = this.sharedPreferences.edit();
    Iterator<l> iterator = paramCollection.iterator();
    while (iterator.hasNext())
      editor.remove(createCookieKey(iterator.next())); 
    editor.commit();
  }
  
  public void saveAll(Collection<l> paramCollection) {
    SharedPreferences.Editor editor = this.sharedPreferences.edit();
    for (l l : paramCollection)
      editor.putString(createCookieKey(l), (new SerializableCookie()).encode(l)); 
    editor.commit();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\franmontiel\persistentcookiejar\persistence\SharedPrefsCookiePersistor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */