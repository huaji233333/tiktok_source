package com.tt.miniapp.audio.encoder;

import com.tt.miniapp.audio.AudioRecorderManager;
import com.tt.miniapphost.AppBrandLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PcmToWav {
  private static void clearFiles(List<String> paramList) {
    for (int i = 0; i < paramList.size(); i++) {
      File file = new File(paramList.get(i));
      if (file.exists())
        file.delete(); 
    } 
  }
  
  public static boolean makePCMFileToWAVFile(String paramString1, String paramString2, boolean paramBoolean, AudioRecorderManager.AudioRecorderConfig paramAudioRecorderConfig) {
    File file = new File(paramString1);
    if (!file.exists())
      return false; 
    int i = (int)file.length();
    WavHeader wavHeader = new WavHeader();
    wavHeader.mFileLength = i + 36;
    wavHeader.mFmtHdrLeth = 16;
    wavHeader.mBitsPerSample = 16;
    wavHeader.mChannels = paramAudioRecorderConfig.numberOfChannels;
    wavHeader.mFormatTag = 1;
    wavHeader.mSamplesPerSec = paramAudioRecorderConfig.sampleRate;
    wavHeader.mBlockAlign = (short)(wavHeader.mChannels * wavHeader.mBitsPerSample / 8);
    wavHeader.mAvgBytesPerSec = wavHeader.mBlockAlign * wavHeader.mSamplesPerSec;
    wavHeader.mDataHdrLeth = i;
    try {
      byte[] arrayOfByte = wavHeader.getHeaderByteArray();
      if (arrayOfByte.length != 44)
        return false; 
      File file1 = new File(paramString2);
      if (file1.exists())
        file1.delete(); 
      try {
        byte[] arrayOfByte1 = new byte[4096];
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramString2));
        bufferedOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        for (i = bufferedInputStream.read(arrayOfByte1); i != -1; i = bufferedInputStream.read(arrayOfByte1))
          bufferedOutputStream.write(arrayOfByte1); 
        bufferedInputStream.close();
        bufferedOutputStream.close();
        if (paramBoolean)
          file.delete(); 
        AppBrandLogger.i("tma_PcmToWav", new Object[] { "makePCMFileToWAVFile  success!", (new SimpleDateFormat("yyyy-MM-dd hh:mm")).format(new Date()) });
        return true;
      } catch (FileNotFoundException fileNotFoundException) {
        AppBrandLogger.e("tma_PcmToWav", new Object[] { fileNotFoundException.getMessage() });
        return false;
      } catch (IOException iOException) {
        AppBrandLogger.e("tma_PcmToWav", new Object[] { iOException.getMessage() });
        return false;
      } 
    } catch (IOException iOException) {
      AppBrandLogger.e("tma_PcmToWav", new Object[] { iOException.getMessage() });
      return false;
    } 
  }
  
  public static boolean mergePCMFilesToWAVFile(List<String> paramList, String paramString, AudioRecorderManager.AudioRecorderConfig paramAudioRecorderConfig) {
    File[] arrayOfFile = new File[paramList.size()];
    int k = paramList.size();
    int i = 0;
    int j = 0;
    while (i < k) {
      arrayOfFile[i] = new File(paramList.get(i));
      j = (int)(j + arrayOfFile[i].length());
      i++;
    } 
    WavHeader wavHeader = new WavHeader();
    wavHeader.mFileLength = j + 36;
    wavHeader.mFmtHdrLeth = 16;
    wavHeader.mBitsPerSample = (short)paramAudioRecorderConfig.encodeBitRate;
    wavHeader.mChannels = paramAudioRecorderConfig.numberOfChannels;
    wavHeader.mFormatTag = 1;
    wavHeader.mSamplesPerSec = paramAudioRecorderConfig.sampleRate;
    wavHeader.mBlockAlign = (short)(wavHeader.mChannels * wavHeader.mBitsPerSample / 8);
    wavHeader.mAvgBytesPerSec = wavHeader.mBlockAlign * wavHeader.mSamplesPerSec;
    wavHeader.mDataHdrLeth = j;
    try {
      byte[] arrayOfByte = wavHeader.getHeaderByteArray();
      if (arrayOfByte.length != 44)
        return false; 
      File file = new File(paramString);
      if (file.exists())
        file.delete(); 
      try {
        byte[] arrayOfByte1 = new byte[4096];
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramString));
        bufferedOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
        for (i = 0; i < k; i++) {
          BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(arrayOfFile[i]));
          for (j = bufferedInputStream.read(arrayOfByte1); j != -1; j = bufferedInputStream.read(arrayOfByte1))
            bufferedOutputStream.write(arrayOfByte1); 
          bufferedInputStream.close();
        } 
        bufferedOutputStream.close();
        clearFiles(paramList);
        AppBrandLogger.i("tma_PcmToWav", new Object[] { "mergePCMFilesToWAVFile  success!", (new SimpleDateFormat("yyyy-MM-dd hh:mm")).format(new Date()) });
        return true;
      } catch (FileNotFoundException fileNotFoundException) {
        AppBrandLogger.e("tma_PcmToWav", new Object[] { fileNotFoundException.getMessage() });
        return false;
      } catch (IOException iOException) {
        AppBrandLogger.e("tma_PcmToWav", new Object[] { iOException.getMessage() });
        return false;
      } 
    } catch (IOException iOException) {
      AppBrandLogger.e("tma_PcmToWav", new Object[] { iOException.getMessage() });
      return false;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\encoder\PcmToWav.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */