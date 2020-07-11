package com.tt.miniapp.dec;

import java.io.IOException;
import java.io.InputStream;

public class BrotliInputStream extends InputStream {
  private byte[] buffer;
  
  private int bufferOffset;
  
  private int remainingBufferBytes;
  
  private final State state = new State();
  
  public BrotliInputStream(InputStream paramInputStream) throws IOException {
    this(paramInputStream, 256);
  }
  
  public BrotliInputStream(InputStream paramInputStream, int paramInt) throws IOException {
    if (paramInt > 0) {
      if (paramInputStream != null) {
        this.buffer = new byte[paramInt];
        this.remainingBufferBytes = 0;
        this.bufferOffset = 0;
        try {
          Decode.initState(this.state, paramInputStream);
          return;
        } catch (BrotliRuntimeException brotliRuntimeException) {
          throw new IOException("Brotli decoder initialization failed", brotliRuntimeException);
        } 
      } 
      throw new IllegalArgumentException("source is null");
    } 
    StringBuilder stringBuilder = new StringBuilder("Bad buffer size:");
    stringBuilder.append(paramInt);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void close() throws IOException {
    Decode.close(this.state);
  }
  
  public int read() throws IOException {
    if (this.bufferOffset >= this.remainingBufferBytes) {
      byte[] arrayOfByte1 = this.buffer;
      this.remainingBufferBytes = read(arrayOfByte1, 0, arrayOfByte1.length);
      this.bufferOffset = 0;
      if (this.remainingBufferBytes == -1)
        return -1; 
    } 
    byte[] arrayOfByte = this.buffer;
    int i = this.bufferOffset;
    this.bufferOffset = i + 1;
    return arrayOfByte[i] & 0xFF;
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    if (paramInt1 >= 0) {
      if (paramInt2 >= 0) {
        int i = paramInt1 + paramInt2;
        if (i <= paramArrayOfbyte.length) {
          if (paramInt2 == 0)
            return 0; 
          int m = Math.max(this.remainingBufferBytes - this.bufferOffset, 0);
          i = m;
          int j = paramInt1;
          int k = paramInt2;
          if (m != 0) {
            m = Math.min(m, paramInt2);
            System.arraycopy(this.buffer, this.bufferOffset, paramArrayOfbyte, paramInt1, m);
            this.bufferOffset += m;
            j = paramInt1 + m;
            paramInt1 = paramInt2 - m;
            i = m;
            k = paramInt1;
            if (paramInt1 == 0)
              return m; 
          } 
          try {
            this.state.output = paramArrayOfbyte;
            this.state.outputOffset = j;
            this.state.outputLength = k;
            this.state.outputUsed = 0;
            Decode.decompress(this.state);
            if (this.state.outputUsed == 0)
              return -1; 
            paramInt1 = this.state.outputUsed;
            return paramInt1 + i;
          } catch (BrotliRuntimeException brotliRuntimeException) {
            throw new IOException("Brotli stream decoding failed", brotliRuntimeException);
          } 
        } 
        StringBuilder stringBuilder2 = new StringBuilder("Buffer overflow: ");
        stringBuilder2.append(i);
        stringBuilder2.append(" > ");
        stringBuilder2.append(brotliRuntimeException.length);
        throw new IllegalArgumentException(stringBuilder2.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder("Bad length: ");
      stringBuilder1.append(paramInt2);
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder("Bad offset: ");
    stringBuilder.append(paramInt1);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void setEager(boolean paramBoolean) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dec\BrotliInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */