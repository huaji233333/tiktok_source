package com.tt.miniapp.msg.image;

import android.graphics.BitmapFactory;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.ttapkgdecoder.TTAPkgFile;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.io.File;
import org.json.JSONObject;

public class ApiGetImageInfoCtrl extends b {
  public ApiGetImageInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private ImageInfo parseImageInfo(String paramString, Callback paramCallback) {
    AppBrandLogger.d("tma_ApiGetImageInfoCtrl", new Object[] { "parseImageInfo ", paramString });
    TTAPkgFile tTAPkgFile = StreamLoader.findFile(paramString);
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    if (tTAPkgFile != null) {
      byte[] arrayOfByte = StreamLoader.loadByteFromStream(paramString);
      if (arrayOfByte == null || arrayOfByte.length == 0) {
        if (paramCallback != null)
          paramCallback.onFail(a.b(new String[] { paramString })); 
        return null;
      } 
      BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, options);
    } else {
      File file = new File(paramString);
      if (!file.exists()) {
        if (paramCallback != null)
          paramCallback.onFail(a.b(new String[] { paramString })); 
        return null;
      } 
      if (!FileManager.inst().canRead(file)) {
        if (paramCallback != null)
          paramCallback.onFail(a.a(new String[] { "read", paramString })); 
        return null;
      } 
      BitmapFactory.decodeFile(paramString, options);
    } 
    ImageInfo imageInfo = new ImageInfo();
    imageInfo.width = options.outWidth;
    imageInfo.height = options.outHeight;
    imageInfo.path = FileManager.inst().getSchemaFilePath(paramString);
    int i = paramString.lastIndexOf('.');
    if (i >= 0 && i < paramString.length() - 1) {
      imageInfo.type = paramString.substring(i + 1);
      return imageInfo;
    } 
    imageInfo.type = "";
    return imageInfo;
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("src");
      ImageInfo imageInfo = parseImageInfo(FileManager.inst().getRealFilePath(str), new Callback() {
            public void onFail(String param1String) {
              ApiGetImageInfoCtrl.this.callbackFail(param1String);
            }
          });
      if (imageInfo == null)
        return; 
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("width", imageInfo.width);
      jSONObject.put("height", imageInfo.height);
      jSONObject.put("path", imageInfo.path);
      jSONObject.put("type", imageInfo.type);
      callbackOk(jSONObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiGetImageInfoCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "getImageInfo";
  }
  
  public static interface Callback {
    void onFail(String param1String);
  }
  
  public static class ImageInfo {
    public int height;
    
    public String path;
    
    public String type;
    
    public int width;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\image\ApiGetImageInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */