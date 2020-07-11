package com.tt.miniapp.ttapkgdecoder;

import g.g;
import g.q;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TTAPkgFile {
  private final String fileName;
  
  private int offset;
  
  private int size;
  
  public TTAPkgFile(String paramString, int paramInt1, int paramInt2) {
    this.fileName = paramString;
    this.offset = paramInt1;
    this.size = paramInt2;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public int getOffset() {
    return this.offset;
  }
  
  public int getSize() {
    return this.size;
  }
  
  public void saveTo(File paramFile, InputStream paramInputStream) {
    if (paramFile.exists()) {
      File file = new File(paramFile, this.fileName);
      g g4 = null;
      g g5 = null;
      g g6 = null;
      paramFile = null;
      g g1 = g4;
      g g2 = g5;
      g g3 = g6;
      try {
        g g;
        file.getParentFile().mkdirs();
        g1 = g4;
        g2 = g5;
        g3 = g6;
        file.delete();
        g1 = g4;
        g2 = g5;
        g3 = g6;
        if (file.createNewFile()) {
          g1 = g4;
          g2 = g5;
          g3 = g6;
          g = q.a(q.a(file));
          g1 = g;
          g2 = g;
          g3 = g;
          byte[] arrayOfByte = new byte[8192];
          while (true) {
            g1 = g;
            g2 = g;
            g3 = g;
            int i = paramInputStream.read(arrayOfByte, 0, 8192);
            if (i != -1) {
              g1 = g;
              g2 = g;
              g3 = g;
              g.c(arrayOfByte, 0, i);
              continue;
            } 
            g1 = g;
            g2 = g;
            g3 = g;
            g.flush();
            break;
          } 
        } 
      } catch (FileNotFoundException fileNotFoundException) {
      
      } catch (IOException iOException) {
      
      } finally {
        if (g1 != null)
          try {
            g1.close();
          } catch (IOException iOException) {} 
      } 
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("TTAPkgFile{fileName='");
    stringBuilder.append(this.fileName);
    stringBuilder.append('\'');
    stringBuilder.append(", offset=");
    stringBuilder.append(this.offset);
    stringBuilder.append(", size=");
    stringBuilder.append(this.size);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\TTAPkgFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */