package com.tt.miniapp.ttapkgdecoder.source;

import com.tt.miniapp.ttapkgdecoder.utils.OkioTools;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiskSource extends BaseSource {
  protected int idx;
  
  RandomAccessFile mRandomAccessFile;
  
  public DiskSource(File paramFile) {
    try {
      this.mRandomAccessFile = new RandomAccessFile(paramFile, "r");
      return;
    } catch (FileNotFoundException fileNotFoundException) {
      return;
    } 
  }
  
  public void close() {
    try {
      this.mRandomAccessFile.close();
      return;
    } catch (IOException iOException) {
      return;
    } 
  }
  
  public long getByteSize() {
    try {
      return this.mRandomAccessFile.length();
    } catch (IOException iOException) {
      return 0L;
    } 
  }
  
  public boolean isAlive() {
    return true;
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    this.mRandomAccessFile.seek(this.idx);
    paramInt1 = this.mRandomAccessFile.read(paramArrayOfbyte, paramInt1, paramInt2);
    this.idx += paramInt1;
    return paramInt1;
  }
  
  public void readFully(byte[] paramArrayOfbyte) throws IOException {
    this.mRandomAccessFile.seek(this.idx);
    this.mRandomAccessFile.readFully(paramArrayOfbyte);
    this.idx += paramArrayOfbyte.length;
  }
  
  public void setOnProgressChangeListener(OkioTools.OnProgressChangeListener paramOnProgressChangeListener) {}
  
  public void skip(long paramLong) {
    this.idx = (int)(this.idx + paramLong);
  }
  
  public void start() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\source\DiskSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */