package com.tt.miniapp.pgc;

public class OperateImageResult {
  public int errCode;
  
  public int fileType;
  
  public String inputFilePath;
  
  public String outputFilePath;
  
  public OperateImageResult(String paramString) {
    this.inputFilePath = paramString;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("\n{\nerrCode=");
    stringBuilder.append(this.errCode);
    stringBuilder.append(",\nfileType=");
    stringBuilder.append(this.fileType);
    stringBuilder.append(",\ninputFilePath='");
    stringBuilder.append(this.inputFilePath);
    stringBuilder.append('\'');
    stringBuilder.append(",\noutputFilePath='");
    stringBuilder.append(this.outputFilePath);
    stringBuilder.append('\'');
    stringBuilder.append("\n}\n");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\pgc\OperateImageResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */