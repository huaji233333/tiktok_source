package com.tt.miniapp.debug.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.InflaterOutputStream;

public class DecompressionHelper {
  public static InputStream teeInputWithDecompression(String paramString1, InputStream paramInputStream, OutputStream paramOutputStream, String paramString2, ResponseHandler paramResponseHandler) throws IOException {
    if (paramString2 != null) {
      boolean bool1 = "gzip".equals(paramString2);
      boolean bool2 = "deflate".equals(paramString2);
      if (bool1 || bool2) {
        CountingOutputStream countingOutputStream1;
        CountingOutputStream countingOutputStream2 = new CountingOutputStream(paramOutputStream);
        if (bool1) {
          paramOutputStream = GunzippingOutputStream.create(countingOutputStream2);
          countingOutputStream1 = countingOutputStream2;
        } else {
          countingOutputStream1 = countingOutputStream2;
          if (bool2) {
            paramOutputStream = new InflaterOutputStream(countingOutputStream2);
            countingOutputStream1 = countingOutputStream2;
          } 
        } 
        return new ResponseHandlingInputStream(paramInputStream, paramString1, paramOutputStream, countingOutputStream1, paramResponseHandler);
      } 
    } 
    paramString2 = null;
    return new ResponseHandlingInputStream(paramInputStream, paramString1, paramOutputStream, (CountingOutputStream)paramString2, paramResponseHandler);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\DecompressionHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */