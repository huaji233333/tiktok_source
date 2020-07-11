package com.tt.miniapp.guide.reenter;

import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class FileConfig {
  public int appCnt;
  
  public int gameCnt;
  
  public long timestamp;
  
  private void bytes2Data(byte[] paramArrayOfbyte) {
    ByteBuffer byteBuffer = ByteBuffer.allocate(16);
    byteBuffer.put(paramArrayOfbyte, 0, 8).flip();
    this.timestamp = byteBuffer.getLong();
    byteBuffer.rewind();
    byteBuffer.put(paramArrayOfbyte, 8, 4).flip();
    this.appCnt = byteBuffer.getInt();
    byteBuffer.rewind();
    byteBuffer.put(paramArrayOfbyte, 12, 4).flip();
    this.gameCnt = byteBuffer.getInt();
  }
  
  private byte[] data2Bytes() {
    byte[] arrayOfByte = new byte[16];
    ByteBuffer byteBuffer = ByteBuffer.allocate(8);
    byteBuffer.putLong(0, this.timestamp);
    System.arraycopy(byteBuffer.array(), 0, arrayOfByte, 0, 8);
    byteBuffer.putInt(0, this.appCnt);
    System.arraycopy(byteBuffer.array(), 0, arrayOfByte, 8, 4);
    byteBuffer.putInt(0, this.gameCnt);
    System.arraycopy(byteBuffer.array(), 0, arrayOfByte, 12, 4);
    return arrayOfByte;
  }
  
  public void cfg2File(File paramFile) {
    File file2 = null;
    IOException iOException = null;
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
      try {
        fileOutputStream.write(data2Bytes());
      } catch (IOException null) {
      
      } finally {
        Exception exception1;
        exception = null;
        iOException = iOException1;
      } 
    } catch (IOException exception) {
      paramFile = file2;
    } finally {}
    File file1 = paramFile;
    AppBrandLogger.e("FileConfig", new Object[] { exception });
    if (paramFile != null)
      try {
        paramFile.close();
        return;
      } catch (IOException iOException1) {
        AppBrandLogger.e("FileConfig", new Object[] { iOException1 });
      }  
  }
  
  public void file2Cfg(File paramFile) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual length : ()J
    //   4: ldc2_w 16
    //   7: lcmp
    //   8: ifeq -> 31
    //   11: ldc 'FileConfig'
    //   13: iconst_1
    //   14: anewarray java/lang/Object
    //   17: dup
    //   18: iconst_0
    //   19: ldc 'r64091: !!!file length is not 16 !!!'
    //   21: aastore
    //   22: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   25: aload_0
    //   26: aload_1
    //   27: invokevirtual cfg2File : (Ljava/io/File;)V
    //   30: return
    //   31: bipush #16
    //   33: newarray byte
    //   35: astore #5
    //   37: aconst_null
    //   38: astore #4
    //   40: aconst_null
    //   41: astore_2
    //   42: new java/io/FileInputStream
    //   45: dup
    //   46: aload_1
    //   47: invokespecial <init> : (Ljava/io/File;)V
    //   50: astore_1
    //   51: aload_1
    //   52: aload #5
    //   54: invokevirtual read : ([B)I
    //   57: pop
    //   58: aload_1
    //   59: invokevirtual close : ()V
    //   62: goto -> 142
    //   65: astore_1
    //   66: ldc 'FileConfig'
    //   68: iconst_1
    //   69: anewarray java/lang/Object
    //   72: dup
    //   73: iconst_0
    //   74: aload_1
    //   75: aastore
    //   76: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   79: goto -> 142
    //   82: astore_3
    //   83: aload_1
    //   84: astore_2
    //   85: aload_3
    //   86: astore_1
    //   87: goto -> 149
    //   90: astore_3
    //   91: goto -> 102
    //   94: astore_1
    //   95: goto -> 149
    //   98: astore_3
    //   99: aload #4
    //   101: astore_1
    //   102: aload_1
    //   103: astore_2
    //   104: ldc 'FileConfig'
    //   106: iconst_1
    //   107: anewarray java/lang/Object
    //   110: dup
    //   111: iconst_0
    //   112: aload_3
    //   113: aastore
    //   114: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   117: aload_1
    //   118: ifnull -> 142
    //   121: aload_1
    //   122: invokevirtual close : ()V
    //   125: goto -> 142
    //   128: astore_1
    //   129: ldc 'FileConfig'
    //   131: iconst_1
    //   132: anewarray java/lang/Object
    //   135: dup
    //   136: iconst_0
    //   137: aload_1
    //   138: aastore
    //   139: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   142: aload_0
    //   143: aload #5
    //   145: invokespecial bytes2Data : ([B)V
    //   148: return
    //   149: aload_2
    //   150: ifnull -> 174
    //   153: aload_2
    //   154: invokevirtual close : ()V
    //   157: goto -> 174
    //   160: astore_2
    //   161: ldc 'FileConfig'
    //   163: iconst_1
    //   164: anewarray java/lang/Object
    //   167: dup
    //   168: iconst_0
    //   169: aload_2
    //   170: aastore
    //   171: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   174: aload_1
    //   175: athrow
    // Exception table:
    //   from	to	target	type
    //   42	51	98	java/io/IOException
    //   42	51	94	finally
    //   51	58	90	java/io/IOException
    //   51	58	82	finally
    //   58	62	65	java/io/IOException
    //   104	117	94	finally
    //   121	125	128	java/io/IOException
    //   153	157	160	java/io/IOException
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("[");
    stringBuilder.append(this.timestamp);
    stringBuilder.append(",");
    stringBuilder.append(this.appCnt);
    stringBuilder.append(",");
    stringBuilder.append(this.gameCnt);
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\guide\reenter\FileConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */