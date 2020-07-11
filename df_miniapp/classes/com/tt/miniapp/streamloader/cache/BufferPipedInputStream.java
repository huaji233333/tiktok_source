package com.tt.miniapp.streamloader.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

public class BufferPipedInputStream extends InputStream {
  protected byte[] buffer;
  
  volatile boolean closedByReader;
  
  boolean closedByWriter;
  
  boolean hasConnected;
  
  protected int in = -1;
  
  protected int out;
  
  Thread readSide;
  
  Thread writeSide;
  
  private void checkStateForReceive() throws IOException {
    if (this.hasConnected) {
      if (!this.closedByWriter && !this.closedByReader) {
        Thread thread = this.readSide;
        if (thread != null) {
          if (thread.isAlive())
            return; 
          throw new IOException("Read end dead");
        } 
        return;
      } 
      throw new IOException("Pipe closed");
    } 
    throw new IOException("Pipe not connected");
  }
  
  private static void throwInterruptedIoException() throws InterruptedIOException {
    Thread.currentThread().interrupt();
    throw new InterruptedIOException();
  }
  
  public int available() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield in : I
    //   6: istore_1
    //   7: iload_1
    //   8: ifge -> 15
    //   11: aload_0
    //   12: monitorexit
    //   13: iconst_0
    //   14: ireturn
    //   15: aload_0
    //   16: getfield in : I
    //   19: istore_1
    //   20: aload_0
    //   21: getfield out : I
    //   24: istore_2
    //   25: aload_0
    //   26: monitorexit
    //   27: iload_1
    //   28: iload_2
    //   29: isub
    //   30: ireturn
    //   31: astore_3
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_3
    //   35: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	31	finally
    //   15	25	31	finally
  }
  
  public void close() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield closedByReader : Z
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: iconst_m1
    //   9: putfield in : I
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   7	14	15	finally
    //   16	18	15	finally
  }
  
  public void connect(BufferPipedOutputStream paramBufferPipedOutputStream) throws IOException {
    paramBufferPipedOutputStream.makeConnect(this);
  }
  
  public int read() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield hasConnected : Z
    //   6: ifeq -> 240
    //   9: aload_0
    //   10: getfield closedByReader : Z
    //   13: ifne -> 230
    //   16: aload_0
    //   17: getfield writeSide : Ljava/lang/Thread;
    //   20: ifnull -> 60
    //   23: aload_0
    //   24: getfield writeSide : Ljava/lang/Thread;
    //   27: invokevirtual isAlive : ()Z
    //   30: ifne -> 60
    //   33: aload_0
    //   34: getfield closedByWriter : Z
    //   37: ifne -> 60
    //   40: aload_0
    //   41: getfield in : I
    //   44: iflt -> 50
    //   47: goto -> 60
    //   50: new java/io/IOException
    //   53: dup
    //   54: ldc 'Write end dead'
    //   56: invokespecial <init> : (Ljava/lang/String;)V
    //   59: athrow
    //   60: aload_0
    //   61: invokestatic currentThread : ()Ljava/lang/Thread;
    //   64: putfield readSide : Ljava/lang/Thread;
    //   67: aload_0
    //   68: getfield buffer : [B
    //   71: ifnull -> 94
    //   74: aload_0
    //   75: getfield out : I
    //   78: istore_1
    //   79: aload_0
    //   80: getfield buffer : [B
    //   83: arraylength
    //   84: istore_2
    //   85: iload_1
    //   86: iload_2
    //   87: if_icmplt -> 94
    //   90: aload_0
    //   91: monitorexit
    //   92: iconst_m1
    //   93: ireturn
    //   94: iconst_2
    //   95: istore_1
    //   96: aload_0
    //   97: getfield in : I
    //   100: iflt -> 155
    //   103: aload_0
    //   104: getfield out : I
    //   107: aload_0
    //   108: getfield in : I
    //   111: if_icmpge -> 155
    //   114: aload_0
    //   115: getfield buffer : [B
    //   118: ifnonnull -> 124
    //   121: goto -> 155
    //   124: aload_0
    //   125: getfield buffer : [B
    //   128: astore #4
    //   130: aload_0
    //   131: getfield out : I
    //   134: istore_1
    //   135: aload_0
    //   136: iload_1
    //   137: iconst_1
    //   138: iadd
    //   139: putfield out : I
    //   142: aload #4
    //   144: iload_1
    //   145: baload
    //   146: istore_1
    //   147: aload_0
    //   148: monitorexit
    //   149: iload_1
    //   150: sipush #255
    //   153: iand
    //   154: ireturn
    //   155: aload_0
    //   156: getfield closedByWriter : Z
    //   159: istore_3
    //   160: iload_3
    //   161: ifeq -> 168
    //   164: aload_0
    //   165: monitorexit
    //   166: iconst_m1
    //   167: ireturn
    //   168: iload_1
    //   169: istore_2
    //   170: aload_0
    //   171: getfield writeSide : Ljava/lang/Thread;
    //   174: ifnull -> 210
    //   177: iload_1
    //   178: istore_2
    //   179: aload_0
    //   180: getfield writeSide : Ljava/lang/Thread;
    //   183: invokevirtual isAlive : ()Z
    //   186: ifne -> 210
    //   189: iload_1
    //   190: iconst_1
    //   191: isub
    //   192: istore_2
    //   193: iload_2
    //   194: iflt -> 200
    //   197: goto -> 210
    //   200: new java/io/IOException
    //   203: dup
    //   204: ldc 'Pipe broken'
    //   206: invokespecial <init> : (Ljava/lang/String;)V
    //   209: athrow
    //   210: aload_0
    //   211: ldc2_w 1000
    //   214: invokevirtual wait : (J)V
    //   217: iload_2
    //   218: istore_1
    //   219: goto -> 96
    //   222: invokestatic throwInterruptedIoException : ()V
    //   225: iload_2
    //   226: istore_1
    //   227: goto -> 96
    //   230: new java/io/IOException
    //   233: dup
    //   234: ldc 'Pipe closed'
    //   236: invokespecial <init> : (Ljava/lang/String;)V
    //   239: athrow
    //   240: new java/io/IOException
    //   243: dup
    //   244: ldc 'Pipe not connected'
    //   246: invokespecial <init> : (Ljava/lang/String;)V
    //   249: athrow
    //   250: astore #4
    //   252: aload_0
    //   253: monitorexit
    //   254: goto -> 260
    //   257: aload #4
    //   259: athrow
    //   260: goto -> 257
    //   263: astore #4
    //   265: goto -> 222
    // Exception table:
    //   from	to	target	type
    //   2	47	250	finally
    //   50	60	250	finally
    //   60	85	250	finally
    //   96	121	250	finally
    //   124	142	250	finally
    //   155	160	250	finally
    //   170	177	250	finally
    //   179	189	250	finally
    //   200	210	250	finally
    //   210	217	263	java/lang/InterruptedException
    //   210	217	250	finally
    //   222	225	250	finally
    //   230	240	250	finally
    //   240	250	250	finally
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 161
    //   6: iload_2
    //   7: iflt -> 153
    //   10: iload_3
    //   11: iflt -> 153
    //   14: aload_1
    //   15: arraylength
    //   16: istore #5
    //   18: iload_3
    //   19: iload #5
    //   21: iload_2
    //   22: isub
    //   23: if_icmpgt -> 153
    //   26: iload_3
    //   27: ifne -> 34
    //   30: aload_0
    //   31: monitorexit
    //   32: iconst_0
    //   33: ireturn
    //   34: aload_0
    //   35: invokevirtual read : ()I
    //   38: istore #5
    //   40: iload #5
    //   42: ifge -> 49
    //   45: aload_0
    //   46: monitorexit
    //   47: iconst_m1
    //   48: ireturn
    //   49: iload #5
    //   51: i2b
    //   52: istore #4
    //   54: aload_1
    //   55: iload_2
    //   56: iload #4
    //   58: bastore
    //   59: iload_3
    //   60: iconst_1
    //   61: isub
    //   62: istore #5
    //   64: aload_0
    //   65: getfield in : I
    //   68: istore #7
    //   70: aload_0
    //   71: getfield out : I
    //   74: istore #8
    //   76: iconst_1
    //   77: istore #6
    //   79: iload #6
    //   81: istore_3
    //   82: iload #7
    //   84: iload #8
    //   86: if_icmple -> 149
    //   89: iload #6
    //   91: istore_3
    //   92: iload #5
    //   94: ifle -> 149
    //   97: aload_0
    //   98: getfield in : I
    //   101: aload_0
    //   102: getfield out : I
    //   105: isub
    //   106: istore_3
    //   107: iload_3
    //   108: iload #5
    //   110: if_icmple -> 174
    //   113: iload #5
    //   115: istore_3
    //   116: goto -> 119
    //   119: aload_0
    //   120: getfield buffer : [B
    //   123: aload_0
    //   124: getfield out : I
    //   127: aload_1
    //   128: iload_2
    //   129: iconst_1
    //   130: iadd
    //   131: iload_3
    //   132: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   135: aload_0
    //   136: aload_0
    //   137: getfield out : I
    //   140: iload_3
    //   141: iadd
    //   142: putfield out : I
    //   145: iconst_1
    //   146: iload_3
    //   147: iadd
    //   148: istore_3
    //   149: aload_0
    //   150: monitorexit
    //   151: iload_3
    //   152: ireturn
    //   153: new java/lang/IndexOutOfBoundsException
    //   156: dup
    //   157: invokespecial <init> : ()V
    //   160: athrow
    //   161: new java/lang/NullPointerException
    //   164: dup
    //   165: invokespecial <init> : ()V
    //   168: athrow
    //   169: astore_1
    //   170: aload_0
    //   171: monitorexit
    //   172: aload_1
    //   173: athrow
    //   174: goto -> 119
    // Exception table:
    //   from	to	target	type
    //   14	18	169	finally
    //   34	40	169	finally
    //   64	76	169	finally
    //   97	107	169	finally
    //   119	145	169	finally
    //   153	161	169	finally
    //   161	169	169	finally
  }
  
  protected void receive(int paramInt) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial checkStateForReceive : ()V
    //   6: aload_0
    //   7: invokestatic currentThread : ()Ljava/lang/Thread;
    //   10: putfield writeSide : Ljava/lang/Thread;
    //   13: aload_0
    //   14: getfield in : I
    //   17: ifge -> 30
    //   20: aload_0
    //   21: iconst_0
    //   22: putfield in : I
    //   25: aload_0
    //   26: iconst_0
    //   27: putfield out : I
    //   30: aload_0
    //   31: aload_0
    //   32: getfield in : I
    //   35: iconst_1
    //   36: iadd
    //   37: putfield in : I
    //   40: aload_0
    //   41: invokevirtual notifyAll : ()V
    //   44: aload_0
    //   45: monitorexit
    //   46: return
    //   47: astore_2
    //   48: aload_0
    //   49: monitorexit
    //   50: aload_2
    //   51: athrow
    // Exception table:
    //   from	to	target	type
    //   2	30	47	finally
    //   30	44	47	finally
  }
  
  void receive(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial checkStateForReceive : ()V
    //   6: aload_0
    //   7: invokestatic currentThread : ()Ljava/lang/Thread;
    //   10: putfield writeSide : Ljava/lang/Thread;
    //   13: aload_1
    //   14: ifnull -> 22
    //   17: aload_0
    //   18: aload_1
    //   19: putfield buffer : [B
    //   22: iload_2
    //   23: iload_3
    //   24: iadd
    //   25: istore_2
    //   26: iload_2
    //   27: aload_0
    //   28: getfield in : I
    //   31: if_icmple -> 43
    //   34: aload_0
    //   35: iload_2
    //   36: putfield in : I
    //   39: aload_0
    //   40: invokevirtual notifyAll : ()V
    //   43: aload_0
    //   44: monitorexit
    //   45: return
    //   46: astore_1
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_1
    //   50: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	46	finally
    //   17	22	46	finally
    //   26	43	46	finally
  }
  
  void receivedLast() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_1
    //   4: putfield closedByWriter : Z
    //   7: aload_0
    //   8: invokevirtual notifyAll : ()V
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: astore_1
    //   15: aload_0
    //   16: monitorexit
    //   17: aload_1
    //   18: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	14	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\cache\BufferPipedInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */