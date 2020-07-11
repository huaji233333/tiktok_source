package com.facebook.react.modules.camera;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64OutputStream;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@ReactModule(name = "ImageStoreManager")
public class ImageStoreManager extends ReactContextBaseJavaModule {
  public ImageStoreManager(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  public static void closeQuietly(Closeable paramCloseable) {
    try {
      paramCloseable.close();
      return;
    } catch (IOException iOException) {
      return;
    } 
  }
  
  String convertInputStreamToBase64OutputStream(InputStream paramInputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Base64OutputStream base64OutputStream = new Base64OutputStream(byteArrayOutputStream, 2);
    byte[] arrayOfByte = new byte[8192];
    try {
      while (true) {
        int i = paramInputStream.read(arrayOfByte);
        if (i >= 0) {
          base64OutputStream.write(arrayOfByte, 0, i);
          continue;
        } 
        return byteArrayOutputStream.toString();
      } 
    } finally {
      closeQuietly((Closeable)base64OutputStream);
    } 
  }
  
  @ReactMethod
  public void getBase64ForTag(String paramString, Callback paramCallback1, Callback paramCallback2) {
    (new GetBase64Task((ReactContext)getReactApplicationContext(), paramString, paramCallback1, paramCallback2)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  public String getName() {
    return "ImageStoreManager";
  }
  
  class GetBase64Task extends GuardedAsyncTask<Void, Void> {
    private final Callback mError;
    
    private final Callback mSuccess;
    
    private final String mUri;
    
    private GetBase64Task(ReactContext param1ReactContext, String param1String, Callback param1Callback1, Callback param1Callback2) {
      super(param1ReactContext);
      this.mUri = param1String;
      this.mSuccess = param1Callback1;
      this.mError = param1Callback2;
    }
    
    protected void doInBackgroundGuarded(Void... param1VarArgs) {
      try {
        InputStream inputStream = ImageStoreManager.this.getReactApplicationContext().getContentResolver().openInputStream(Uri.parse(this.mUri));
        try {
          this.mSuccess.invoke(new Object[] { this.this$0.convertInputStreamToBase64OutputStream(inputStream) });
        } catch (IOException iOException) {
        
        } finally {
          Exception exception;
        } 
        ImageStoreManager.closeQuietly(inputStream);
        return;
      } catch (FileNotFoundException fileNotFoundException) {
        this.mError.invoke(new Object[] { fileNotFoundException.getMessage() });
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\camera\ImageStoreManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */