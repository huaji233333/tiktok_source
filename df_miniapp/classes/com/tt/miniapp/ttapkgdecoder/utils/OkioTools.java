package com.tt.miniapp.ttapkgdecoder.utils;

import g.f;
import g.h;
import g.l;
import g.n;
import g.q;
import g.z;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;

public final class OkioTools {
  private static void closeQuietly(Closeable paramCloseable) {
    if (paramCloseable != null)
      try {
        paramCloseable.close();
        return;
      } catch (RuntimeException runtimeException) {
        throw runtimeException;
      } catch (Exception exception) {
        return;
      }  
  }
  
  public static String decodeGzip(byte[] paramArrayOfbyte) {
    h h2 = null;
    h h1 = null;
    try {
      h h = q.a((z)new n(q.a(new ByteArrayInputStream(paramArrayOfbyte))));
      h1 = h;
      h2 = h;
      return h.r();
    } catch (IOException iOException) {
      return "";
    } finally {
      closeQuietly((Closeable)h1);
    } 
  }
  
  public static z progressListenSource(z paramz, final OnProgressChangeListener listener) {
    return (z)((listener == null) ? paramz : new l(paramz) {
        long byteHasRead;
        
        public final long read(f param1f, long param1Long) throws IOException {
          param1Long = super.read(param1f, param1Long);
          if (param1Long > 0L) {
            this.byteHasRead += param1Long;
            listener.onProgressChange(this.byteHasRead);
          } 
          return param1Long;
        }
      });
  }
  
  public static interface OnProgressChangeListener {
    void onProgressChange(long param1Long);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecode\\utils\OkioTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */