package com.tt.miniapp.streamloader;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper extends InputStream {
  private volatile InputStream mWrappedStream;
  
  private void waitForStreamSet() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mWrappedStream : Ljava/io/InputStream;
    //   4: ifnull -> 8
    //   7: return
    //   8: aload_0
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield mWrappedStream : Ljava/io/InputStream;
    //   14: astore_1
    //   15: aload_1
    //   16: ifnonnull -> 26
    //   19: aload_0
    //   20: invokevirtual wait : ()V
    //   23: goto -> 10
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: astore_1
    //   30: aload_0
    //   31: monitorexit
    //   32: goto -> 37
    //   35: aload_1
    //   36: athrow
    //   37: goto -> 35
    //   40: astore_1
    //   41: goto -> 10
    // Exception table:
    //   from	to	target	type
    //   10	15	29	finally
    //   19	23	40	java/lang/InterruptedException
    //   19	23	29	finally
    //   26	28	29	finally
    //   30	32	29	finally
  }
  
  public int available() throws IOException {
    waitForStreamSet();
    return this.mWrappedStream.available();
  }
  
  public void close() throws IOException {
    waitForStreamSet();
    this.mWrappedStream.close();
  }
  
  public void mark(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial waitForStreamSet : ()V
    //   6: aload_0
    //   7: getfield mWrappedStream : Ljava/io/InputStream;
    //   10: iload_1
    //   11: invokevirtual mark : (I)V
    //   14: aload_0
    //   15: monitorexit
    //   16: return
    //   17: astore_2
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_2
    //   21: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	17	finally
  }
  
  public boolean markSupported() {
    waitForStreamSet();
    return this.mWrappedStream.markSupported();
  }
  
  public int read() throws IOException {
    waitForStreamSet();
    return this.mWrappedStream.read();
  }
  
  public int read(byte[] paramArrayOfbyte) throws IOException {
    waitForStreamSet();
    return this.mWrappedStream.read(paramArrayOfbyte);
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    waitForStreamSet();
    return this.mWrappedStream.read(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public void reset() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial waitForStreamSet : ()V
    //   6: aload_0
    //   7: getfield mWrappedStream : Ljava/io/InputStream;
    //   10: invokevirtual reset : ()V
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
  
  public void setInputStream(InputStream paramInputStream) {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull -> 5
    //   4: return
    //   5: aload_0
    //   6: aload_1
    //   7: putfield mWrappedStream : Ljava/io/InputStream;
    //   10: aload_0
    //   11: monitorenter
    //   12: aload_0
    //   13: invokevirtual notifyAll : ()V
    //   16: aload_0
    //   17: monitorexit
    //   18: return
    //   19: astore_1
    //   20: aload_0
    //   21: monitorexit
    //   22: aload_1
    //   23: athrow
    // Exception table:
    //   from	to	target	type
    //   12	18	19	finally
    //   20	22	19	finally
  }
  
  public long skip(long paramLong) throws IOException {
    waitForStreamSet();
    return this.mWrappedStream.skip(paramLong);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\InputStreamWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */