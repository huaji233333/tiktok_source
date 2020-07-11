package com.tt.miniapp.msg.file;

import android.text.TextUtils;
import com.a;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class AbsFileCtrl<I, O> {
  protected String mApiName;
  
  protected I mArgs;
  
  protected Map<String, ValuePair> mArgumentsMap = new HashMap<String, ValuePair>();
  
  protected HashMap<String, Object> mExtraData;
  
  protected String mExtraInfo;
  
  public AbsFileCtrl(String paramString) {
    this.mApiName = paramString;
  }
  
  private boolean checkArguments() {
    Map<String, ValuePair> map = this.mArgumentsMap;
    if (map != null)
      for (Map.Entry<String, ValuePair> entry : map.entrySet()) {
        if (((ValuePair)entry.getValue()).isIllegal()) {
          this.mExtraInfo = a.b((String)entry.getKey());
          return false;
        } 
      }  
    return true;
  }
  
  protected String buildErrMsg(String paramString1, String paramString2, String paramString3) {
    StringBuilder stringBuilder1;
    if (TextUtils.isEmpty(paramString3)) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString1);
      stringBuilder1.append(":");
      stringBuilder1.append(paramString2);
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString1);
    stringBuilder2.append(":");
    stringBuilder2.append(paramString2);
    stringBuilder2.append(" ");
    stringBuilder2.append((String)stringBuilder1);
    return stringBuilder2.toString();
  }
  
  protected boolean canRead(File paramFile) {
    return FileManager.inst().canRead(paramFile);
  }
  
  protected boolean canWrite(File paramFile) {
    return FileManager.inst().canWrite(paramFile);
  }
  
  protected abstract O exception(Throwable paramThrowable);
  
  protected abstract O fail();
  
  protected String getFormatExtraInfo(String paramString, Object... paramVarArgs) {
    return a.a(paramString, paramVarArgs);
  }
  
  protected String getNoSuchFileStr(String... paramVarArgs) {
    return a.b(paramVarArgs);
  }
  
  protected String getPermissionDenyStr(String... paramVarArgs) {
    return a.a(paramVarArgs);
  }
  
  protected String getRealFilePath(String paramString) {
    return FileManager.inst().getRealFilePath(paramString);
  }
  
  protected abstract boolean handleInvoke() throws Exception;
  
  public O invoke(I paramI) {
    this.mArgs = paramI;
    try {
      parseParam(paramI);
      return handleInvoke() ? success() : fail();
    } catch (Exception exception) {
      AppBrandLogger.e("AbsFileCtrl", new Object[] { exception });
      return exception(exception);
    } 
  }
  
  protected boolean optBoolean(String paramString) {
    ValuePair<ValuePair> valuePair = this.mArgumentsMap.get(paramString);
    if (valuePair != null) {
      valuePair = valuePair.getValue();
      if (valuePair instanceof Boolean)
        return ((Boolean)valuePair).booleanValue(); 
      StringBuilder stringBuilder = new StringBuilder("key:");
      stringBuilder.append(paramString);
      throw new ClassCastException(stringBuilder.toString());
    } 
    return false;
  }
  
  protected String optString(String paramString) {
    ValuePair<ValuePair> valuePair = this.mArgumentsMap.get(paramString);
    if (valuePair != null) {
      valuePair = valuePair.getValue();
      if (valuePair instanceof String)
        return (String)valuePair; 
      StringBuilder stringBuilder = new StringBuilder("key:");
      stringBuilder.append(paramString);
      throw new ClassCastException(stringBuilder.toString());
    } 
    return "";
  }
  
  protected abstract void parseParam(I paramI) throws Exception;
  
  protected abstract O success();
  
  protected String trim(String paramString) {
    String str = paramString;
    if (!TextUtils.isEmpty(paramString))
      str = paramString.trim(); 
    return str;
  }
  
  public class ValuePair<T> {
    boolean required;
    
    T value;
    
    public ValuePair(T param1T, boolean param1Boolean) {
      this.value = param1T;
      this.required = param1Boolean;
    }
    
    public T getValue() {
      return this.value;
    }
    
    public boolean isIllegal() {
      T t = this.value;
      return (t instanceof String && this.required && TextUtils.isEmpty((String)t));
    }
    
    public boolean isRequired() {
      return this.required;
    }
    
    public void setRequired(boolean param1Boolean) {
      this.required = param1Boolean;
    }
    
    public void setValue(T param1T) {
      this.value = param1T;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\AbsFileCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */