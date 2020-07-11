package com.tt.miniapp.ttapkgdecoder.reader;

import android.text.TextUtils;
import android.util.Pair;
import com.tt.miniapp.ttapkgdecoder.DecoderCallback;
import com.tt.miniapp.ttapkgdecoder.TTAPkgFile;
import com.tt.miniapp.ttapkgdecoder.TTAPkgInfo;
import com.tt.miniapp.ttapkgdecoder.source.ISource;
import com.tt.miniapp.ttapkgdecoder.utils.DecodeException;
import com.tt.miniapphost.AppBrandLogger;
import java.io.IOException;
import java.util.List;

public class TTAPkgReader {
  private static final CharSequence MAGIC_STRING = "TPKG";
  
  private long byteHasRead;
  
  private boolean isReleased;
  
  private int mFileHasRead;
  
  private final ISource mSource;
  
  private TTAPkgInfo mTTAPkgInfo;
  
  public TTAPkgReader(ISource paramISource) throws DecodeException {
    this.mSource = paramISource;
    this.mSource.start();
  }
  
  public boolean checkMagicString() throws IOException {
    String str = this.mSource.readUtf8(4L);
    this.byteHasRead += 4L;
    AppBrandLogger.d("TTAPkgReader", new Object[] { "checkMagicString" });
    return TextUtils.equals(str, MAGIC_STRING);
  }
  
  public long getByteHasRead() {
    return this.byteHasRead;
  }
  
  public long getByteSize() {
    long l1;
    ISource iSource = this.mSource;
    if (iSource != null) {
      l1 = iSource.getByteSize();
    } else {
      l1 = 0L;
    } 
    long l2 = l1;
    if (l1 <= 0L) {
      TTAPkgInfo tTAPkgInfo = this.mTTAPkgInfo;
      l2 = l1;
      if (tTAPkgInfo != null) {
        List<TTAPkgFile> list = tTAPkgInfo.getFiles();
        int i = list.size();
        l2 = l1;
        if (i > 0) {
          TTAPkgFile tTAPkgFile = list.get(i - 1);
          l2 = l1;
          if (tTAPkgFile != null)
            l2 = (tTAPkgFile.getSize() + tTAPkgFile.getOffset()); 
        } 
      } 
    } 
    return l2;
  }
  
  public boolean isReleased() {
    return this.isReleased;
  }
  
  public Pair<TTAPkgFile, byte[]> readNextFile(DecoderCallback paramDecoderCallback) throws IOException {
    TTAPkgFile tTAPkgFile;
    TTAPkgInfo tTAPkgInfo = this.mTTAPkgInfo;
    List<TTAPkgFile> list = null;
    if (tTAPkgInfo != null) {
      list = tTAPkgInfo.getFiles();
      int i = list.size();
      int j = this.mFileHasRead;
      if (j >= i)
        return null; 
      tTAPkgFile = list.get(j);
      long l1 = this.byteHasRead;
      long l2 = tTAPkgFile.getOffset();
      i = 0;
      if (l1 == l2) {
        if (paramDecoderCallback != null) {
          j = tTAPkgFile.getSize();
          byte[] arrayOfByte2 = new byte[j];
          paramDecoderCallback.onDecodeFileStart(tTAPkgFile, arrayOfByte2);
          while (i < j) {
            int k = this.mSource.read(arrayOfByte2, i, j - i);
            paramDecoderCallback.onDecodeFilePart(tTAPkgFile, arrayOfByte2, i, k);
            i += k;
          } 
          this.byteHasRead += j;
          this.mFileHasRead++;
          byte[] arrayOfByte1 = arrayOfByte2;
        } else {
          byte[] arrayOfByte = new byte[tTAPkgFile.getSize()];
          this.mSource.readFully(arrayOfByte);
          this.byteHasRead += tTAPkgFile.getSize();
          this.mFileHasRead++;
        } 
      } else {
        StringBuilder stringBuilder = new StringBuilder("invalid offset, file name = ");
        stringBuilder.append(tTAPkgFile.getFileName());
        AppBrandLogger.e("TTAPkgReader", new Object[] { stringBuilder.toString() });
        throw new DecodeException(-6);
      } 
    } else {
      paramDecoderCallback = null;
    } 
    return new Pair(tTAPkgFile, paramDecoderCallback);
  }
  
  public TTAPkgInfo readTTPkgInfo() throws IOException {
    TTAPkgInfo tTAPkgInfo2 = this.mTTAPkgInfo;
    if (tTAPkgInfo2 != null)
      return tTAPkgInfo2; 
    int i = this.mSource.readInt();
    this.byteHasRead += 4L;
    byte[] arrayOfByte = new byte[i];
    this.mSource.readFully(arrayOfByte);
    this.byteHasRead += i;
    int j = this.mSource.readInt();
    this.byteHasRead += 4L;
    TTAPkgInfo tTAPkgInfo1 = new TTAPkgInfo(arrayOfByte);
    for (i = 0; i < j; i++) {
      int k = this.mSource.readInt();
      this.byteHasRead += 4L;
      ISource iSource = this.mSource;
      long l = k;
      String str = iSource.readUtf8(l);
      this.byteHasRead += l;
      k = this.mSource.readInt();
      this.byteHasRead += 4L;
      int m = this.mSource.readInt();
      this.byteHasRead += 4L;
      tTAPkgInfo1.addFile(new TTAPkgFile(str, k, m));
    } 
    AppBrandLogger.d("TTAPkgReader", new Object[] { "readTTPkgInfo success" });
    this.mTTAPkgInfo = tTAPkgInfo1;
    return this.mTTAPkgInfo;
  }
  
  public int readVersion() throws IOException {
    int i = this.mSource.readInt();
    this.byteHasRead += 4L;
    return i;
  }
  
  public void release() {
    this.mSource.close();
    this.isReleased = true;
    AppBrandLogger.d("TTAPkgReader", new Object[] { "TTAPkgReader is release" });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\reader\TTAPkgReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */