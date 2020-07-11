package com.tt.miniapp.ttapkgdecoder.source;

import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferDiskSource extends DiskSource {
  private MappedByteBuffer mMappedByteBuffer;
  
  public MappedByteBufferDiskSource(File paramFile) {
    super(paramFile);
    if (this.mRandomAccessFile != null)
      try {
        this.mMappedByteBuffer = this.mRandomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, paramFile.length());
        return;
      } catch (IOException iOException) {
        AppBrandLogger.e("MappedByteBufferDiskSource", new Object[] { iOException });
      }  
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    try {
      if (this.mMappedByteBuffer.position() != this.idx)
        this.mMappedByteBuffer.position(this.idx); 
      this.mMappedByteBuffer.get(paramArrayOfbyte, paramInt1, paramInt2);
      this.idx += paramInt2;
      return paramInt2;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("MappedByteBufferDiskSource", "read", exception);
      return super.read(paramArrayOfbyte, paramInt1, paramInt2);
    } 
  }
  
  public void readFully(byte[] paramArrayOfbyte) throws IOException {
    try {
      if (this.mMappedByteBuffer.position() != this.idx)
        this.mMappedByteBuffer.position(this.idx); 
      this.mMappedByteBuffer.get(paramArrayOfbyte);
      this.idx += paramArrayOfbyte.length;
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("MappedByteBufferDiskSource", "readFully", exception);
      super.readFully(paramArrayOfbyte);
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\source\MappedByteBufferDiskSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */