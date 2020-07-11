package com.tt.miniapp.msg.sync;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.tt.miniapp.storage.filestorge.FileManager;
import java.io.File;
import java.io.FileOutputStream;
import org.json.JSONObject;

public class ApiBase64ToTempFilePathSyncCtrl extends SyncMsgCtrl {
  public ApiBase64ToTempFilePathSyncCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      byte[] arrayOfByte = Base64.decode((new JSONObject(this.mParams)).optString("base64Data"), 0);
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
      if (!file.exists())
        return makeFailMsg("save temp file fail"); 
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("tempFilePath", FileManager.inst().getSchemaFilePath(file));
      return makeOkMsg(jSONObject);
    } catch (Exception exception) {
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "base64ToTempFilePathSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\ApiBase64ToTempFilePathSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */