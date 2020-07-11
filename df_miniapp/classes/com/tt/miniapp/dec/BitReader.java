package com.tt.miniapp.dec;

final class BitReader {
  static void bytesToNibbles(State paramState, int paramInt) {
    byte[] arrayOfByte = paramState.byteBuffer;
    int[] arrayOfInt = paramState.intBuffer;
    for (int i = 0; i < paramInt >> 2; i++) {
      int j = i * 4;
      byte b1 = arrayOfByte[j];
      byte b2 = arrayOfByte[j + 1];
      byte b3 = arrayOfByte[j + 2];
      arrayOfInt[i] = (arrayOfByte[j + 3] & 0xFF) << 24 | b1 & 0xFF | (b2 & 0xFF) << 8 | (b3 & 0xFF) << 16;
    } 
  }
  
  static void checkHealth(State paramState, int paramInt) {
    if (paramState.endOfStreamReached == 0)
      return; 
    int i = (paramState.halfOffset << 2) + (paramState.bitOffset + 7 >> 3) - 8;
    if (i <= paramState.tailBytes) {
      if (paramInt != 0) {
        if (i == paramState.tailBytes)
          return; 
        throw new BrotliRuntimeException("Unused bytes after end");
      } 
      return;
    } 
    throw new BrotliRuntimeException("Read after end");
  }
  
  static void copyBytes(State paramState, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if ((paramState.bitOffset & 0x7) == 0) {
      int i = paramInt2;
      int j;
      for (j = paramInt1; paramState.bitOffset != 64 && i != 0; j++) {
        paramArrayOfbyte[j] = (byte)peekBits(paramState);
        paramState.bitOffset += 8;
        i--;
      } 
      if (i == 0)
        return; 
      int k = Math.min(halfAvailable(paramState), i >> 2);
      paramInt1 = j;
      paramInt2 = i;
      if (k > 0) {
        paramInt1 = paramState.halfOffset;
        paramInt2 = k << 2;
        System.arraycopy(paramState.byteBuffer, paramInt1 << 2, paramArrayOfbyte, j, paramInt2);
        paramInt1 = j + paramInt2;
        paramInt2 = i - paramInt2;
        paramState.halfOffset += k;
      } 
      if (paramInt2 == 0)
        return; 
      i = paramInt1;
      j = paramInt2;
      if (halfAvailable(paramState) > 0) {
        fillBitWindow(paramState);
        while (paramInt2 != 0) {
          paramArrayOfbyte[paramInt1] = (byte)peekBits(paramState);
          paramState.bitOffset += 8;
          paramInt2--;
          paramInt1++;
        } 
        checkHealth(paramState, 0);
        return;
      } 
      while (j > 0) {
        paramInt1 = Utils.readInput(paramState.input, paramArrayOfbyte, i, j);
        if (paramInt1 != -1) {
          i += paramInt1;
          j -= paramInt1;
          continue;
        } 
        throw new BrotliRuntimeException("Unexpected end of input");
      } 
      return;
    } 
    BrotliRuntimeException brotliRuntimeException = new BrotliRuntimeException("Unaligned copyBytes");
    throw brotliRuntimeException;
  }
  
  private static void doFillBitWindow(State paramState) {
    int[] arrayOfInt = paramState.intBuffer;
    int i = paramState.halfOffset;
    paramState.halfOffset = i + 1;
    paramState.accumulator64 = arrayOfInt[i] << 32L | paramState.accumulator64 >>> 32L;
    paramState.bitOffset -= 32;
  }
  
  static void doReadMoreInput(State paramState) {
    if (paramState.endOfStreamReached != 0) {
      if (halfAvailable(paramState) >= -2)
        return; 
      throw new BrotliRuntimeException("No more input");
    } 
    int j = paramState.halfOffset << 2;
    int i = 4096 - j;
    Utils.copyBytesWithin(paramState.byteBuffer, 0, j, 4096);
    paramState.halfOffset = 0;
    while (true) {
      j = i;
      if (i < 4096) {
        j = Utils.readInput(paramState.input, paramState.byteBuffer, i, 4096 - i);
        if (j <= 0) {
          paramState.endOfStreamReached = 1;
          paramState.tailBytes = i;
          j = i + 3;
          break;
        } 
        i += j;
        continue;
      } 
      break;
    } 
    bytesToNibbles(paramState, j);
  }
  
  static void fillBitWindow(State paramState) {
    if (paramState.bitOffset >= 32) {
      int[] arrayOfInt = paramState.intBuffer;
      int i = paramState.halfOffset;
      paramState.halfOffset = i + 1;
      paramState.accumulator64 = arrayOfInt[i] << 32L | paramState.accumulator64 >>> 32L;
      paramState.bitOffset -= 32;
    } 
  }
  
  static int halfAvailable(State paramState) {
    char c;
    if (paramState.endOfStreamReached != 0) {
      c = paramState.tailBytes + 3 >> 2;
    } else {
      c = 'Ѐ';
    } 
    return c - paramState.halfOffset;
  }
  
  static void initBitReader(State paramState) {
    paramState.byteBuffer = new byte[4160];
    paramState.accumulator64 = 0L;
    paramState.intBuffer = new int[1040];
    paramState.bitOffset = 64;
    paramState.halfOffset = 1024;
    paramState.endOfStreamReached = 0;
    prepare(paramState);
  }
  
  static void jumpToByteBoundary(State paramState) {
    int i = 64 - paramState.bitOffset & 0x7;
    if (i != 0) {
      if (readFewBits(paramState, i) == 0)
        return; 
      throw new BrotliRuntimeException("Corrupted padding bits");
    } 
  }
  
  static int peekBits(State paramState) {
    return (int)(paramState.accumulator64 >>> paramState.bitOffset);
  }
  
  private static void prepare(State paramState) {
    readMoreInput(paramState);
    checkHealth(paramState, 0);
    doFillBitWindow(paramState);
    doFillBitWindow(paramState);
  }
  
  static int readBits(State paramState, int paramInt) {
    return readFewBits(paramState, paramInt);
  }
  
  static int readFewBits(State paramState, int paramInt) {
    int i = peekBits(paramState);
    paramState.bitOffset += paramInt;
    return i & (1 << paramInt) - 1;
  }
  
  private static int readManyBits(State paramState, int paramInt) {
    int i = readFewBits(paramState, 16);
    doFillBitWindow(paramState);
    return readFewBits(paramState, paramInt - 16) << 16 | i;
  }
  
  static void readMoreInput(State paramState) {
    if (paramState.halfOffset > 1015)
      doReadMoreInput(paramState); 
  }
  
  static void reload(State paramState) {
    if (paramState.bitOffset == 64)
      prepare(paramState); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dec\BitReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */