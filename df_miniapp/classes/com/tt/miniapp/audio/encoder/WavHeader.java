package com.tt.miniapp.audio.encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WavHeader {
  private static final char[] DATA_HDR_ID;
  
  private static final char[] FIELD_ID = new char[] { 'R', 'I', 'F', 'F' };
  
  private static final char[] FMT_HDR_ID;
  
  private static final char[] WAVE_TAG = new char[] { 'W', 'A', 'V', 'E' };
  
  public int mAvgBytesPerSec;
  
  public short mBitsPerSample;
  
  public short mBlockAlign;
  
  public short mChannels;
  
  public int mDataHdrLeth;
  
  public int mFileLength;
  
  public int mFmtHdrLeth;
  
  public short mFormatTag;
  
  public int mSamplesPerSec;
  
  static {
    FMT_HDR_ID = new char[] { 'f', 'm', 't', ' ' };
    DATA_HDR_ID = new char[] { 'd', 'a', 't', 'a' };
  }
  
  private void WriteCharToByte(ByteArrayOutputStream paramByteArrayOutputStream, char[] paramArrayOfchar) {
    for (int i = 0; i < paramArrayOfchar.length; i++)
      paramByteArrayOutputStream.write(paramArrayOfchar[i]); 
  }
  
  private void WriteIntToByte(ByteArrayOutputStream paramByteArrayOutputStream, int paramInt) throws IOException {
    paramByteArrayOutputStream.write(new byte[] { (byte)(paramInt << 24 >> 24), (byte)(paramInt << 16 >> 24), (byte)(paramInt << 8 >> 24), (byte)(paramInt >> 24) });
  }
  
  private void WriteShortToByte(ByteArrayOutputStream paramByteArrayOutputStream, int paramInt) throws IOException {
    byte b = (byte)(paramInt << 16 >> 24);
    paramByteArrayOutputStream.write(new byte[] { (byte)(paramInt << 24 >> 24), b });
  }
  
  public byte[] getHeaderByteArray() throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    WriteCharToByte(byteArrayOutputStream, FIELD_ID);
    WriteIntToByte(byteArrayOutputStream, this.mFileLength);
    WriteCharToByte(byteArrayOutputStream, WAVE_TAG);
    WriteCharToByte(byteArrayOutputStream, FMT_HDR_ID);
    WriteIntToByte(byteArrayOutputStream, this.mFmtHdrLeth);
    WriteShortToByte(byteArrayOutputStream, this.mFormatTag);
    WriteShortToByte(byteArrayOutputStream, this.mChannels);
    WriteIntToByte(byteArrayOutputStream, this.mSamplesPerSec);
    WriteIntToByte(byteArrayOutputStream, this.mAvgBytesPerSec);
    WriteShortToByte(byteArrayOutputStream, this.mBlockAlign);
    WriteShortToByte(byteArrayOutputStream, this.mBitsPerSample);
    WriteCharToByte(byteArrayOutputStream, DATA_HDR_ID);
    WriteIntToByte(byteArrayOutputStream, this.mDataHdrLeth);
    byteArrayOutputStream.flush();
    byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
    byteArrayOutputStream.close();
    return arrayOfByte;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\encoder\WavHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */