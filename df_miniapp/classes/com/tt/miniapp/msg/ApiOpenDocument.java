package com.tt.miniapp.msg;

import android.app.Activity;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import java.io.File;
import java.util.HashSet;
import org.json.JSONObject;

public class ApiOpenDocument extends b {
  private static final HashSet<String> FILE_TYPE_SET;
  
  static {
    HashSet<String> hashSet = new HashSet();
    FILE_TYPE_SET = hashSet;
    hashSet.add("doc");
    FILE_TYPE_SET.add("docx");
    FILE_TYPE_SET.add("xls");
    FILE_TYPE_SET.add("xlsx");
    FILE_TYPE_SET.add("ppt");
    FILE_TYPE_SET.add("pptx");
    FILE_TYPE_SET.add("pdf");
  }
  
  public ApiOpenDocument(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void openDocument(String paramString1, String paramString2) {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail("activity is null");
      return;
    } 
    if (HostDependManager.getInst().openDocument((Activity)miniappHostBase, paramString1, paramString2)) {
      callbackOk();
      return;
    } 
    callbackFail("open document fail");
  }
  
  private void openNormalDocument(String paramString1, String paramString2, String paramString3) {
    File file = new File(paramString1);
    if (!file.exists()) {
      callbackFail(a.b(new String[] { paramString3 }));
      return;
    } 
    if (!FileManager.inst().canRead(file)) {
      callbackFail(a.a(new String[] { "read", paramString3 }));
      return;
    } 
    String str = renameFilePath(paramString1, paramString2);
    paramString3 = paramString1;
    if (!TextUtils.equals(str, paramString1)) {
      File file1 = new File(str);
      if (file1.exists())
        file1.delete(); 
      paramString3 = paramString1;
      if (file.renameTo(file1))
        paramString3 = str; 
    } 
    openDocument(paramString3, paramString2);
  }
  
  private void openStreamDocument(String paramString1, String paramString2, String paramString3) {
    String str = renameFilePath(paramString1, paramString2);
    try {
      String str1 = FileManager.inst().getUserDir().getCanonicalPath();
      str = (new File(str)).getName();
      if (StreamLoader.extractToFile(paramString1, str1, str)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str1);
        stringBuilder.append(File.separator);
        stringBuilder.append(str);
        openDocument(stringBuilder.toString(), paramString2);
        return;
      } 
      callbackFail(a.b(new String[] { paramString3 }));
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("LarkApiOpenDocument", new Object[] { "open stream doc", exception });
      callbackFail(exception);
      return;
    } 
  }
  
  private String renameFilePath(String paramString1, String paramString2) {
    String str = paramString1;
    if (!TextUtils.isEmpty(paramString2)) {
      if (!FILE_TYPE_SET.contains(paramString2))
        return paramString1; 
      int i = paramString1.lastIndexOf(".");
      StringBuilder stringBuilder = new StringBuilder();
      if (i > 0) {
        paramString1 = paramString1.substring(0, i + 1);
      } else {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(paramString1);
        stringBuilder1.append(".");
        paramString1 = stringBuilder1.toString();
      } 
      stringBuilder.append(paramString1);
      stringBuilder.append(paramString2);
      str = stringBuilder.toString();
    } 
    return str;
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str1 = jSONObject.optString("filePath");
      String str2 = jSONObject.optString("fileType", null);
      if (TextUtils.isEmpty(str1)) {
        callbackFail(a.b("filePath"));
        return;
      } 
      if (str2 != null && !FILE_TYPE_SET.contains(str2)) {
        callbackFail("fileType not supported");
        return;
      } 
      String str3 = FileManager.inst().getRealFilePath(str1);
      if ((new File(str3)).exists()) {
        openNormalDocument(str3, str2, str1);
        return;
      } 
      if (StreamLoader.findFile(str3) != null) {
        openStreamDocument(str3, str2, str1);
        return;
      } 
      callbackFail(a.b(new String[] { str1 }));
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("LarkApiOpenDocument", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "openDocument";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiOpenDocument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */