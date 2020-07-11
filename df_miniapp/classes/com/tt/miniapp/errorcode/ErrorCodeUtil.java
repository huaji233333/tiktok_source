package com.tt.miniapp.errorcode;

public class ErrorCodeUtil {
  public static String getNetCode(Flow paramFlow, String paramString) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(paramFlow.getCode());
    stringBuffer.append("-");
    stringBuffer.append(paramString);
    return stringBuffer.toString();
  }
  
  public static String mappingStreamDownloadCode(int paramInt) {
    String str = ErrorCode.DOWNLOAD.UNKNOWN.getCode();
    switch (paramInt) {
      default:
        return str;
      case -1:
        return ErrorCode.DOWNLOAD.FILE_NOT_FOUND.getCode();
      case -2:
        return ErrorCode.DOWNLOAD.NETWORK_ERROR.getCode();
      case -3:
        return ErrorCode.DOWNLOAD.MAGIC_STRING_ERROR.getCode();
      case -4:
        return ErrorCode.DOWNLOAD.UNKNOWN.getCode();
      case -5:
        return ErrorCode.DOWNLOAD.INVALID_URL.getCode();
      case -6:
        return ErrorCode.DOWNLOAD.PKG_FILE_OFFSET_WRONG.getCode();
      case -7:
        break;
    } 
    return ErrorCode.DOWNLOAD.UNSUPPORT_TTAPKG_VERSION.getCode();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\errorcode\ErrorCodeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */