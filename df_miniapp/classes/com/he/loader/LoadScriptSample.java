package com.he.loader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

public final class LoadScriptSample {
  private static final long epoch_start = System.currentTimeMillis() * 1000L - System.nanoTime() / 1000L;
  
  public final boolean cacheAccepted;
  
  public final boolean cacheHit;
  
  public final int cacheSize;
  
  public final int codeSize;
  
  public final long compileStart;
  
  public final long decodeStringStart;
  
  public final boolean eagerCompiled;
  
  public final long end;
  
  public final long executeStart;
  
  public final long loadCacheStart;
  
  public final long loadCodeStart;
  
  public final String path;
  
  public final long start;
  
  public LoadScriptSample(ByteBuffer paramByteBuffer, int paramInt) {
    paramByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    char[] arrayOfChar = new char[paramByteBuffer.getInt(paramInt)];
    CharBuffer charBuffer = paramByteBuffer.asCharBuffer();
    charBuffer.position((paramInt >> 1) + 2);
    charBuffer.get(arrayOfChar);
    this.path = new String(arrayOfChar);
    this.start = paramByteBuffer.getLong(paramInt + 256);
    this.loadCodeStart = paramByteBuffer.getLong(paramInt + 264);
    this.decodeStringStart = paramByteBuffer.getLong(paramInt + 272);
    this.loadCacheStart = paramByteBuffer.getLong(paramInt + 280);
    this.compileStart = paramByteBuffer.getLong(paramInt + 288);
    this.executeStart = paramByteBuffer.getLong(paramInt + 296);
    this.end = paramByteBuffer.getLong(paramInt + 304);
    this.codeSize = paramByteBuffer.getInt(paramInt + 312);
    paramInt = paramByteBuffer.getInt(paramInt + 316);
    this.cacheSize = 0xFFFFFFF & paramInt;
    boolean bool2 = false;
    if (paramInt != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.cacheHit = bool1;
    if ((Integer.MIN_VALUE & paramInt) == 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.cacheAccepted = bool1;
    boolean bool1 = bool2;
    if ((paramInt & 0x40000000) != 0)
      bool1 = true; 
    this.eagerCompiled = bool1;
  }
  
  public static long toEpochTime(long paramLong) {
    return (epoch_start + paramLong) / 1000L;
  }
  
  public final String toString() {
    String str;
    StringBuilder stringBuilder = new StringBuilder("LoadScriptSample {\n  path      : \"");
    stringBuilder.append(this.path);
    stringBuilder.append("\"\n  start     : ");
    stringBuilder.append(toEpochTime(this.start));
    stringBuilder.append('\n');
    stringBuilder.append("  load code : +");
    stringBuilder.append(this.decodeStringStart - this.loadCodeStart);
    stringBuilder.append('\n');
    stringBuilder.append("  decode string : +");
    stringBuilder.append(this.loadCacheStart - this.decodeStringStart);
    stringBuilder.append('\n');
    stringBuilder.append("  load cache: +");
    stringBuilder.append(this.compileStart - this.loadCacheStart);
    stringBuilder.append('\n');
    stringBuilder.append("  compile   : +");
    stringBuilder.append(this.executeStart - this.compileStart);
    stringBuilder.append('\n');
    stringBuilder.append("  execute   : +");
    stringBuilder.append(this.end - this.executeStart);
    stringBuilder.append('\n');
    stringBuilder.append("  total     : +");
    stringBuilder.append(this.end - this.start);
    stringBuilder.append('\n');
    stringBuilder.append("  code size : ");
    stringBuilder.append(this.codeSize);
    stringBuilder.append('\n');
    stringBuilder.append("  cache     : ");
    if (this.cacheHit) {
      StringBuilder stringBuilder1 = new StringBuilder();
      if (this.cacheAccepted) {
        str = "accepted";
      } else {
        str = "rejected";
      } 
      stringBuilder1.append(str);
      stringBuilder1.append(", size ");
      stringBuilder1.append(this.cacheSize);
      stringBuilder1.append(", ");
      if (this.eagerCompiled) {
        str = "eager";
      } else {
        str = "lazy";
      } 
      stringBuilder1.append(str);
      stringBuilder1.append(" compiled\n");
      str = stringBuilder1.toString();
    } else {
      str = "miss\n";
    } 
    stringBuilder.append(str);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public static interface Callback {
    void onSample(LoadScriptSample param1LoadScriptSample);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\loader\LoadScriptSample.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */