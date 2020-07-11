package com.facebook.react.modules.network;

import android.content.Context;
import android.net.Uri;
import com.facebook.common.e.a;
import g.g;
import g.i;
import g.q;
import g.z;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.GZIPOutputStream;
import okhttp3.ad;
import okhttp3.internal.c;
import okhttp3.w;

class RequestBodyUtil {
  public static ad create(final w mediaType, final InputStream inputStream) {
    return new ad() {
        public final long contentLength() {
          try {
            int i = inputStream.available();
            return i;
          } catch (IOException iOException) {
            return 0L;
          } 
        }
        
        public final w contentType() {
          return mediaType;
        }
        
        public final void writeTo(g param1g) throws IOException {
          z z = null;
          try {
            z z1 = q.a(inputStream);
            z = z1;
            param1g.a(z1);
            return;
          } finally {
            c.a((Closeable)z);
          } 
        }
      };
  }
  
  public static ad createGzip(w paramw, String paramString) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
      gZIPOutputStream.write(paramString.getBytes());
      gZIPOutputStream.close();
      return ad.create(paramw, byteArrayOutputStream.toByteArray());
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  public static ProgressRequestBody createProgressRequest(ad paramad, ProgressListener paramProgressListener) {
    return new ProgressRequestBody(paramad, paramProgressListener);
  }
  
  private static InputStream getDownloadFileInputStream(Context paramContext, Uri paramUri) throws IOException {
    File file = File.createTempFile("RequestBodyUtil", "temp", paramContext.getApplicationContext().getCacheDir());
    file.deleteOnExit();
    InputStream inputStream = (new URL(paramUri.toString())).openStream();
    try {
      ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
    } finally {
      inputStream.close();
    } 
  }
  
  public static ad getEmptyBody(String paramString) {
    return (paramString.equals("POST") || paramString.equals("PUT") || paramString.equals("PATCH")) ? ad.create(null, i.EMPTY) : null;
  }
  
  public static InputStream getFileInputStream(Context paramContext, String paramString) {
    try {
      Uri uri = Uri.parse(paramString);
      return uri.getScheme().startsWith("http") ? getDownloadFileInputStream(paramContext, uri) : paramContext.getContentResolver().openInputStream(uri);
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder("Could not retrieve file for contentUri ");
      stringBuilder.append(paramString);
      a.c("ReactNative", stringBuilder.toString(), exception);
      return null;
    } 
  }
  
  public static boolean isGzipEncoding(String paramString) {
    return "gzip".equalsIgnoreCase(paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\RequestBodyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */