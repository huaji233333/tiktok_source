package com.facebook.react.modules.blob;

import android.util.Base64;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "FileReaderModule")
public class FileReaderModule extends ReactContextBaseJavaModule {
  public FileReaderModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private BlobModule getBlobModule() {
    return (BlobModule)getReactApplicationContext().getNativeModule(BlobModule.class);
  }
  
  public String getName() {
    return "FileReaderModule";
  }
  
  @ReactMethod
  public void readAsDataURL(ReadableMap paramReadableMap, Promise paramPromise) {
    byte[] arrayOfByte = getBlobModule().resolve(paramReadableMap.getString("blobId"), paramReadableMap.getInt("offset"), paramReadableMap.getInt("size"));
    if (arrayOfByte == null) {
      paramPromise.reject("ERROR_INVALID_BLOB", "The specified blob is invalid");
      return;
    } 
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("data:");
      if (paramReadableMap.hasKey("type") && !paramReadableMap.getString("type").isEmpty()) {
        stringBuilder.append(paramReadableMap.getString("type"));
      } else {
        stringBuilder.append("application/octet-stream");
      } 
      stringBuilder.append(";base64,");
      stringBuilder.append(Base64.encodeToString(arrayOfByte, 2));
      paramPromise.resolve(stringBuilder.toString());
      return;
    } catch (Exception exception) {
      paramPromise.reject(exception);
      return;
    } 
  }
  
  @ReactMethod
  public void readAsText(ReadableMap paramReadableMap, String paramString, Promise paramPromise) {
    byte[] arrayOfByte = getBlobModule().resolve(paramReadableMap.getString("blobId"), paramReadableMap.getInt("offset"), paramReadableMap.getInt("size"));
    if (arrayOfByte == null) {
      paramPromise.reject("ERROR_INVALID_BLOB", "The specified blob is invalid");
      return;
    } 
    try {
      paramPromise.resolve(new String(arrayOfByte, paramString));
      return;
    } catch (Exception exception) {
      paramPromise.reject(exception);
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\blob\FileReaderModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */