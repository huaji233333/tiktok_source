package com.tt.miniapp.ttapkgdecoder.source;

import com.tt.miniapp.ttapkgdecoder.utils.DecodeException;
import com.tt.miniapp.ttapkgdecoder.utils.OkioTools;
import java.io.IOException;

public interface ISource {
  void close();
  
  long getByteSize();
  
  boolean isAlive();
  
  int read(byte[] paramArrayOfbyte) throws IOException;
  
  int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  void readFully(byte[] paramArrayOfbyte) throws IOException;
  
  int readInt() throws IOException;
  
  String readUtf8(long paramLong) throws IOException;
  
  void setOnProgressChangeListener(OkioTools.OnProgressChangeListener paramOnProgressChangeListener);
  
  void skip(long paramLong) throws IOException;
  
  void start() throws DecodeException;
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\source\ISource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */