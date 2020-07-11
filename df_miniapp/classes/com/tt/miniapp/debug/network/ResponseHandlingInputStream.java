package com.tt.miniapp.debug.network;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ResponseHandlingInputStream extends FilterInputStream {
  private boolean mClosed;
  
  private final CountingOutputStream mDecompressedCounter;
  
  private boolean mEofSeen;
  
  private long mLastDecompressedCount;
  
  private final OutputStream mOutputStream;
  
  private final String mRequestId;
  
  private final ResponseHandler mResponseHandler;
  
  private byte[] mSkipBuffer;
  
  public ResponseHandlingInputStream(InputStream paramInputStream, String paramString, OutputStream paramOutputStream, CountingOutputStream paramCountingOutputStream, ResponseHandler paramResponseHandler) {
    super(paramInputStream);
    this.mRequestId = paramString;
    this.mOutputStream = paramOutputStream;
    this.mDecompressedCounter = paramCountingOutputStream;
    this.mResponseHandler = paramResponseHandler;
  }
  
  private int checkEOF(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: iconst_m1
    //   4: if_icmpne -> 33
    //   7: aload_0
    //   8: invokespecial closeOutputStreamQuietly : ()V
    //   11: aload_0
    //   12: getfield mResponseHandler : Lcom/tt/miniapp/debug/network/ResponseHandler;
    //   15: invokeinterface onEOF : ()V
    //   20: aload_0
    //   21: iconst_1
    //   22: putfield mEofSeen : Z
    //   25: goto -> 33
    //   28: astore_2
    //   29: aload_0
    //   30: monitorexit
    //   31: aload_2
    //   32: athrow
    //   33: aload_0
    //   34: monitorexit
    //   35: iload_1
    //   36: ireturn
    // Exception table:
    //   from	to	target	type
    //   7	25	28	finally
  }
  
  private void closeOutputStreamQuietly() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mClosed : Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifne -> 46
    //   11: aload_0
    //   12: getfield mOutputStream : Ljava/io/OutputStream;
    //   15: invokevirtual close : ()V
    //   18: aload_0
    //   19: invokespecial reportDecodedSizeIfApplicable : ()V
    //   22: aload_0
    //   23: iconst_1
    //   24: putfield mClosed : Z
    //   27: aload_0
    //   28: monitorexit
    //   29: return
    //   30: astore_2
    //   31: aload_0
    //   32: iconst_1
    //   33: putfield mClosed : Z
    //   36: aload_2
    //   37: athrow
    //   38: aload_0
    //   39: iconst_1
    //   40: putfield mClosed : Z
    //   43: aload_0
    //   44: monitorexit
    //   45: return
    //   46: aload_0
    //   47: monitorexit
    //   48: return
    //   49: astore_2
    //   50: aload_0
    //   51: monitorexit
    //   52: aload_2
    //   53: athrow
    //   54: astore_2
    //   55: goto -> 38
    // Exception table:
    //   from	to	target	type
    //   2	7	49	finally
    //   11	22	54	java/io/IOException
    //   11	22	30	finally
    //   22	27	49	finally
    //   31	38	49	finally
    //   38	43	49	finally
  }
  
  private byte[] getSkipBufferLocked() {
    if (this.mSkipBuffer == null)
      this.mSkipBuffer = new byte[1024]; 
    return this.mSkipBuffer;
  }
  
  private IOException handleIOException(IOException paramIOException) {
    this.mResponseHandler.onError(paramIOException);
    return paramIOException;
  }
  
  private void handleIOExceptionWritingToStream(IOException paramIOException) {
    closeOutputStreamQuietly();
  }
  
  private void reportDecodedSizeIfApplicable() {
    CountingOutputStream countingOutputStream = this.mDecompressedCounter;
    if (countingOutputStream != null) {
      long l = countingOutputStream.getCount();
      int i = (int)(l - this.mLastDecompressedCount);
      this.mResponseHandler.onReadDecoded(i);
      this.mLastDecompressedCount = l;
    } 
  }
  
  private void writeToOutputStream(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mClosed : Z
    //   6: istore_2
    //   7: iload_2
    //   8: ifeq -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: getfield mOutputStream : Ljava/io/OutputStream;
    //   18: iload_1
    //   19: invokevirtual write : (I)V
    //   22: aload_0
    //   23: invokespecial reportDecodedSizeIfApplicable : ()V
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: astore_3
    //   30: aload_0
    //   31: aload_3
    //   32: invokespecial handleIOExceptionWritingToStream : (Ljava/io/IOException;)V
    //   35: aload_0
    //   36: monitorexit
    //   37: return
    //   38: astore_3
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_3
    //   42: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	38	finally
    //   14	26	29	java/io/IOException
    //   14	26	38	finally
    //   30	35	38	finally
  }
  
  private void writeToOutputStream(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mClosed : Z
    //   6: istore #4
    //   8: iload #4
    //   10: ifeq -> 16
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: aload_0
    //   17: getfield mOutputStream : Ljava/io/OutputStream;
    //   20: aload_1
    //   21: iload_2
    //   22: iload_3
    //   23: invokevirtual write : ([BII)V
    //   26: aload_0
    //   27: invokespecial reportDecodedSizeIfApplicable : ()V
    //   30: aload_0
    //   31: monitorexit
    //   32: return
    //   33: astore_1
    //   34: aload_0
    //   35: aload_1
    //   36: invokespecial handleIOExceptionWritingToStream : (Ljava/io/IOException;)V
    //   39: aload_0
    //   40: monitorexit
    //   41: return
    //   42: astore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: athrow
    // Exception table:
    //   from	to	target	type
    //   2	8	42	finally
    //   16	30	33	java/io/IOException
    //   16	30	42	finally
    //   34	39	42	finally
  }
  
  public final void close() throws IOException {
    try {
      if (!this.mEofSeen) {
        int i;
        byte[] arrayOfByte = new byte[1024];
        do {
          i = read(arrayOfByte);
        } while (i != -1);
      } 
      return;
    } finally {
      super.close();
      closeOutputStreamQuietly();
    } 
  }
  
  public final void mark(int paramInt) {}
  
  public final boolean markSupported() {
    return false;
  }
  
  public final int read() throws IOException {
    try {
      int i = checkEOF(this.in.read());
      if (i != -1) {
        this.mResponseHandler.onRead(1);
        writeToOutputStream(i);
      } 
      return i;
    } catch (IOException iOException) {
      throw handleIOException(iOException);
    } 
  }
  
  public final int read(byte[] paramArrayOfbyte) throws IOException {
    return read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public final int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    try {
      paramInt2 = checkEOF(this.in.read(paramArrayOfbyte, paramInt1, paramInt2));
      if (paramInt2 != -1) {
        this.mResponseHandler.onRead(paramInt2);
        writeToOutputStream(paramArrayOfbyte, paramInt1, paramInt2);
      } 
      return paramInt2;
    } catch (IOException iOException) {
      throw handleIOException(iOException);
    } 
  }
  
  public final void reset() throws IOException {
    throw new UnsupportedOperationException("Mark not supported");
  }
  
  public final long skip(long paramLong) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial getSkipBufferLocked : ()[B
    //   6: astore #6
    //   8: lconst_0
    //   9: lstore #4
    //   11: lload #4
    //   13: lload_1
    //   14: lcmp
    //   15: ifge -> 53
    //   18: aload_0
    //   19: aload #6
    //   21: iconst_0
    //   22: aload #6
    //   24: arraylength
    //   25: i2l
    //   26: lload_1
    //   27: lload #4
    //   29: lsub
    //   30: invokestatic min : (JJ)J
    //   33: l2i
    //   34: invokevirtual read : ([BII)I
    //   37: istore_3
    //   38: iload_3
    //   39: iconst_m1
    //   40: if_icmpeq -> 53
    //   43: lload #4
    //   45: iload_3
    //   46: i2l
    //   47: ladd
    //   48: lstore #4
    //   50: goto -> 11
    //   53: aload_0
    //   54: monitorexit
    //   55: lload #4
    //   57: lreturn
    //   58: astore #6
    //   60: aload_0
    //   61: monitorexit
    //   62: goto -> 68
    //   65: aload #6
    //   67: athrow
    //   68: goto -> 65
    // Exception table:
    //   from	to	target	type
    //   2	8	58	finally
    //   18	38	58	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\ResponseHandlingInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */