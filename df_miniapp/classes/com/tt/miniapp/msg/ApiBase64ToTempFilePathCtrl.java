package com.tt.miniapp.msg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.io.File;
import java.io.FileOutputStream;
import org.json.JSONObject;

public class ApiBase64ToTempFilePathCtrl extends b {
  public ApiBase64ToTempFilePathCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      byte[] arrayOfByte = Base64.decode((new JSONObject(this.mArgs)).optString("base64Data"), 0);
      Bitmap bitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
      File file = FileManager.inst().getTempDir();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(System.currentTimeMillis());
      stringBuilder.append(".png");
      file = new File(file, stringBuilder.toString());
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
      fileOutputStream.flush();
      fileOutputStream.close();
      if (!file.exists()) {
        callbackFail("save temp file fail");
        return;
      } 
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("tempFilePath", FileManager.inst().getSchemaFilePath(file));
      callbackOk(jSONObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiBase64ToTempFilePathCtrl", new Object[] { "", exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "base64ToTempFilePath";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiBase64ToTempFilePathCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */