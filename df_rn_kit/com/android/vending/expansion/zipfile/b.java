package com.android.vending.expansion.zipfile;

import android.content.res.AssetFileDescriptor;
import android.os.ParcelFileDescriptor;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.zip.ZipFile;

public final class b {
  public HashMap<File, ZipFile> a = new HashMap<File, ZipFile>();
  
  ByteBuffer b = ByteBuffer.allocate(4);
  
  private HashMap<String, a> c = new HashMap<String, a>();
  
  public b(String paramString) throws IOException {
    b(paramString);
  }
  
  private static int a(RandomAccessFile paramRandomAccessFile) throws EOFException, IOException {
    int i = paramRandomAccessFile.readInt();
    return ((i & 0xFF) << 24) + ((0xFF00 & i) << 8) + ((0xFF0000 & i) >>> 8) + (i >>> 24 & 0xFF);
  }
  
  public final AssetFileDescriptor a(String paramString) {
    a a = this.c.get(paramString);
    return (a != null) ? a.a() : null;
  }
  
  final void b(String paramString) throws IOException {
    File file = new File(paramString);
    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
    long l = randomAccessFile.length();
    if (l >= 22L) {
      long l1 = 65557L;
      if (65557L > l)
        l1 = l; 
      randomAccessFile.seek(0L);
      int i = a(randomAccessFile);
      if (i != 101010256) {
        if (i == 67324752) {
          randomAccessFile.seek(l - l1);
          ByteBuffer byteBuffer = ByteBuffer.allocate((int)l1);
          byte[] arrayOfByte = byteBuffer.array();
          randomAccessFile.readFully(arrayOfByte);
          byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
          for (i = arrayOfByte.length - 22; i >= 0 && (arrayOfByte[i] != 80 || byteBuffer.getInt(i) != 101010256); i--);
          short s = byteBuffer.getShort(i + 8);
          l1 = byteBuffer.getInt(i + 12) & 0xFFFFFFFFL;
          long l2 = byteBuffer.getInt(i + 16) & 0xFFFFFFFFL;
          if (l2 + l1 <= l) {
            if (s != 0) {
              MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, l2, l1);
              mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
              byte[] arrayOfByte1 = new byte[65535];
              ByteBuffer byteBuffer1 = ByteBuffer.allocate(30);
              byteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
              i = 0;
              int j = 0;
              while (i < s) {
                if (mappedByteBuffer.getInt(j) == 33639248) {
                  int k = mappedByteBuffer.getShort(j + 28) & 0xFFFF;
                  short s1 = mappedByteBuffer.getShort(j + 30);
                  short s2 = mappedByteBuffer.getShort(j + 32);
                  mappedByteBuffer.position(j + 46);
                  mappedByteBuffer.get(arrayOfByte1, 0, k);
                  mappedByteBuffer.position(0);
                  String str = new String(arrayOfByte1, 0, k);
                  a a = new a(paramString, file, str);
                  a.e = mappedByteBuffer.getShort(j + 10) & 0xFFFF;
                  a.f = mappedByteBuffer.getInt(j + 12) & 0xFFFFFFFFL;
                  a.g = mappedByteBuffer.getLong(j + 16) & 0xFFFFFFFFL;
                  a.h = mappedByteBuffer.getLong(j + 20) & 0xFFFFFFFFL;
                  a.i = mappedByteBuffer.getLong(j + 24) & 0xFFFFFFFFL;
                  a.d = mappedByteBuffer.getInt(j + 42) & 0xFFFFFFFFL;
                  byteBuffer1.clear();
                  a.a(randomAccessFile, byteBuffer1);
                  this.c.put(str, a);
                  j += k + 46 + (s1 & 0xFFFF) + (s2 & 0xFFFF);
                  i++;
                  continue;
                } 
                throw new IOException();
              } 
              return;
            } 
            throw new IOException();
          } 
          throw new IOException();
        } 
        throw new IOException();
      } 
      throw new IOException();
    } 
    IOException iOException = new IOException();
    throw iOException;
  }
  
  public static final class a {
    public final File a;
    
    public final String b;
    
    public final String c;
    
    public long d;
    
    public int e;
    
    public long f;
    
    public long g;
    
    public long h;
    
    public long i;
    
    public long j = -1L;
    
    public a(String param1String1, File param1File, String param1String2) {
      this.b = param1String2;
      this.c = param1String1;
      this.a = param1File;
    }
    
    public final AssetFileDescriptor a() {
      if (this.e == 0)
        try {
          return new AssetFileDescriptor(ParcelFileDescriptor.open(this.a, 268435456), this.j, this.i);
        } catch (FileNotFoundException fileNotFoundException) {
          fileNotFoundException.printStackTrace();
        }  
      return null;
    }
    
    public final void a(RandomAccessFile param1RandomAccessFile, ByteBuffer param1ByteBuffer) throws IOException {
      long l = this.d;
      try {
        param1RandomAccessFile.seek(l);
        param1RandomAccessFile.readFully(param1ByteBuffer.array());
        if (param1ByteBuffer.getInt(0) == 67324752) {
          short s1 = param1ByteBuffer.getShort(26);
          short s2 = param1ByteBuffer.getShort(28);
          this.j = l + 30L + (s1 & 0xFFFF) + (s2 & 0xFFFF);
          return;
        } 
        throw new IOException();
      } catch (FileNotFoundException fileNotFoundException) {
        fileNotFoundException.printStackTrace();
        return;
      } catch (IOException iOException) {
        iOException.printStackTrace();
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\android\vending\expansion\zipfile\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */