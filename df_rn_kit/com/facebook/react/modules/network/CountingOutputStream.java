package com.facebook.react.modules.network;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends FilterOutputStream {
  private long mCount;
  
  public CountingOutputStream(OutputStream paramOutputStream) {
    super(paramOutputStream);
  }
  
  public void close() throws IOException {
    this.out.close();
  }
  
  public long getCount() {
    return this.mCount;
  }
  
  public void write(int paramInt) throws IOException {
    this.out.write(paramInt);
    this.mCount++;
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
    this.mCount += paramInt2;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\CountingOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */