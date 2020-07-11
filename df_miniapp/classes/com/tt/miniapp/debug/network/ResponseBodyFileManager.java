package com.tt.miniapp.debug.network;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ResponseBodyFileManager {
  private final Context mContext;
  
  public ResponseBodyFileManager(Context paramContext) {
    this.mContext = paramContext;
  }
  
  public static void copy(InputStream paramInputStream, OutputStream paramOutputStream, byte[] paramArrayOfbyte) throws IOException {
    while (true) {
      int i = paramInputStream.read(paramArrayOfbyte);
      if (i != -1) {
        paramOutputStream.write(paramArrayOfbyte, 0, i);
        continue;
      } 
      break;
    } 
  }
  
  private static String getFilename(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("network-response-body-");
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  public static String readAsUTF8(InputStream paramInputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    copy(paramInputStream, byteArrayOutputStream, new byte[1024]);
    return byteArrayOutputStream.toString("UTF-8");
  }
  
  public void cleanupFiles() {
    for (File file : this.mContext.getFilesDir().listFiles()) {
      if (file.getName().startsWith("network-response-body-"))
        file.delete(); 
    } 
  }
  
  public OutputStream openResponseBodyFile(String paramString, boolean paramBoolean) throws IOException {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public ResponseBodyData readFile(String paramString) throws IOException {
    FileInputStream fileInputStream = this.mContext.openFileInput(getFilename(paramString));
    try {
      int i = fileInputStream.read();
    } finally {
      fileInputStream.close();
    } 
    boolean bool = false;
    ((ResponseBodyData)SYNTHETIC_LOCAL_VARIABLE_4).base64Encoded = bool;
    ((ResponseBodyData)SYNTHETIC_LOCAL_VARIABLE_4).data = readAsUTF8(fileInputStream);
    fileInputStream.close();
    return (ResponseBodyData)SYNTHETIC_LOCAL_VARIABLE_4;
  }
  
  public class ResponseBodyData {
    public boolean base64Encoded;
    
    public String data;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\ResponseBodyFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */