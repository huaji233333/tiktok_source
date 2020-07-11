package com.tt.miniapp.dec;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;

final class Utils {
  private static final byte[] BYTE_ZEROES = new byte[1024];
  
  private static final int[] INT_ZEROES = new int[1024];
  
  static void closeInput(InputStream paramInputStream) throws IOException {
    paramInputStream.close();
  }
  
  static void copyBytesWithin(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
    System.arraycopy(paramArrayOfbyte, paramInt2, paramArrayOfbyte, paramInt1, paramInt3 - paramInt2);
  }
  
  static void fillBytesWithZeroes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    while (paramInt1 < paramInt2) {
      int i = Math.min(paramInt1 + 1024, paramInt2) - paramInt1;
      System.arraycopy(BYTE_ZEROES, 0, paramArrayOfbyte, paramInt1, i);
      paramInt1 += i;
    } 
  }
  
  static void fillIntsWithZeroes(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    while (paramInt1 < paramInt2) {
      int i = Math.min(paramInt1 + 1024, paramInt2) - paramInt1;
      System.arraycopy(INT_ZEROES, 0, paramArrayOfint, paramInt1, i);
      paramInt1 += i;
    } 
  }
  
  static void flipBuffer(Buffer paramBuffer) {
    paramBuffer.flip();
  }
  
  static int readInput(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    try {
      return paramInputStream.read(paramArrayOfbyte, paramInt1, paramInt2);
    } catch (IOException iOException) {
      throw new BrotliRuntimeException("Failed to read input", iOException);
    } 
  }
  
  static byte[] toUsAsciiBytes(String paramString) {
    try {
      return paramString.getBytes("US-ASCII");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new RuntimeException(unsupportedEncodingException);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dec\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */