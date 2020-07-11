package com.tt.miniapp.ttapkgdecoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class TTAPkgInfo {
  private final byte[] mExtraInfo;
  
  private final List<TTAPkgFile> mFileList;
  
  private final HashMap<String, TTAPkgFile> mFileMap;
  
  public TTAPkgInfo(byte[] paramArrayOfbyte) {
    this.mExtraInfo = paramArrayOfbyte;
    this.mFileMap = new HashMap<String, TTAPkgFile>();
    this.mFileList = new ArrayList<TTAPkgFile>();
  }
  
  public void addFile(TTAPkgFile paramTTAPkgFile) {
    this.mFileMap.put(paramTTAPkgFile.getFileName(), paramTTAPkgFile);
    this.mFileList.add(paramTTAPkgFile);
  }
  
  public TTAPkgFile findFile(String paramString) {
    String str;
    if (paramString.startsWith("./")) {
      str = paramString.substring(2);
    } else {
      str = paramString;
      if (paramString.startsWith("/"))
        str = paramString.substring(1); 
    } 
    return this.mFileMap.get(str);
  }
  
  public byte[] getExtraInfo() {
    return this.mExtraInfo;
  }
  
  public Collection<String> getFileNames() {
    return this.mFileMap.keySet();
  }
  
  public List<TTAPkgFile> getFiles() {
    return this.mFileList;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("TTAPkgInfo{mFile=");
    stringBuilder.append(this.mFileList);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\TTAPkgInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */