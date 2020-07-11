package com.tt.miniapp.ttapkgdecoder;

import com.tt.miniapp.ttapkgdecoder.utils.DecodeException;

public interface DecoderCallback {
  void onDecodeFail(int paramInt, String paramString);
  
  void onDecodeFile(TTAPkgFile paramTTAPkgFile, byte[] paramArrayOfbyte);
  
  void onDecodeFilePart(TTAPkgFile paramTTAPkgFile, byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  void onDecodeFileStart(TTAPkgFile paramTTAPkgFile, byte[] paramArrayOfbyte);
  
  void onDecodeFinish(TTAPkgInfo paramTTAPkgInfo);
  
  void onDecodeProgress(int paramInt);
  
  void onLoadHeader(int paramInt, TTAPkgInfo paramTTAPkgInfo) throws DecodeException;
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\DecoderCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */