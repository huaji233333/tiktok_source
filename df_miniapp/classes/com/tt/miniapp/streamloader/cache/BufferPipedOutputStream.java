package com.tt.miniapp.streamloader.cache;

import java.io.IOException;
import java.io.OutputStream;

public class BufferPipedOutputStream extends OutputStream {
  private BufferPipedInputStream mInputStream;
  
  public BufferPipedOutputStream(BufferPipedInputStream paramBufferPipedInputStream) throws IOException {
    makeConnect(paramBufferPipedInputStream);
  }
  
  public void close() throws IOException {
    BufferPipedInputStream bufferPipedInputStream = this.mInputStream;
    if (bufferPipedInputStream == null)
      return; 
    bufferPipedInputStream.receivedLast();
  }
  
  public void flush() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mInputStream : Lcom/tt/miniapp/streamloader/cache/BufferPipedInputStream;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnonnull -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: getfield mInputStream : Lcom/tt/miniapp/streamloader/cache/BufferPipedInputStream;
    //   18: astore_1
    //   19: aload_1
    //   20: monitorenter
    //   21: aload_0
    //   22: getfield mInputStream : Lcom/tt/miniapp/streamloader/cache/BufferPipedInputStream;
    //   25: invokevirtual notifyAll : ()V
    //   28: aload_1
    //   29: monitorexit
    //   30: aload_0
    //   31: monitorexit
    //   32: return
    //   33: astore_2
    //   34: aload_1
    //   35: monitorexit
    //   36: aload_2
    //   37: athrow
    //   38: astore_1
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_1
    //   42: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	38	finally
    //   14	21	38	finally
    //   21	30	33	finally
    //   34	36	33	finally
    //   36	38	38	finally
  }
  
  public void makeConnect(BufferPipedInputStream paramBufferPipedInputStream) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 53
    //   6: aload_0
    //   7: getfield mInputStream : Lcom/tt/miniapp/streamloader/cache/BufferPipedInputStream;
    //   10: ifnonnull -> 43
    //   13: aload_1
    //   14: getfield hasConnected : Z
    //   17: ifne -> 43
    //   20: aload_0
    //   21: aload_1
    //   22: putfield mInputStream : Lcom/tt/miniapp/streamloader/cache/BufferPipedInputStream;
    //   25: aload_1
    //   26: iconst_0
    //   27: putfield out : I
    //   30: aload_1
    //   31: iconst_m1
    //   32: putfield in : I
    //   35: aload_1
    //   36: iconst_1
    //   37: putfield hasConnected : Z
    //   40: aload_0
    //   41: monitorexit
    //   42: return
    //   43: new java/io/IOException
    //   46: dup
    //   47: ldc 'Already connected'
    //   49: invokespecial <init> : (Ljava/lang/String;)V
    //   52: athrow
    //   53: new java/lang/IllegalArgumentException
    //   56: dup
    //   57: invokespecial <init> : ()V
    //   60: athrow
    //   61: aload_0
    //   62: monitorexit
    //   63: aload_1
    //   64: athrow
    //   65: astore_1
    //   66: goto -> 61
    // Exception table:
    //   from	to	target	type
    //   6	40	65	finally
    //   43	53	65	finally
    //   53	61	65	finally
  }
  
  public void write(int paramInt) throws IOException {
    BufferPipedInputStream bufferPipedInputStream = this.mInputStream;
    if (bufferPipedInputStream != null) {
      bufferPipedInputStream.receive(paramInt);
      return;
    } 
    throw new IOException("not connect pipe");
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    if (paramArrayOfbyte != null) {
      BufferPipedInputStream bufferPipedInputStream = this.mInputStream;
      if (bufferPipedInputStream != null) {
        if (paramInt2 >= 0 && paramInt1 >= 0 && paramInt1 <= paramArrayOfbyte.length) {
          int i = paramInt1 + paramInt2;
          if (i <= paramArrayOfbyte.length && i >= 0) {
            if (paramInt2 == 0)
              return; 
            bufferPipedInputStream.receive(paramArrayOfbyte, paramInt1, paramInt2);
            return;
          } 
        } 
        throw new IndexOutOfBoundsException();
      } 
      throw new IOException("not connect pipe");
    } 
    throw new IllegalArgumentException();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\cache\BufferPipedOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */