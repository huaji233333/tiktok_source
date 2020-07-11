package com.tt.miniapp.follow;

public class FollowErrorMsgBuilder {
  public static String buildCodeInfo(int paramInt1, int paramInt2, String paramString) {
    StringBuilder stringBuilder = new StringBuilder("code:");
    stringBuilder.append(paramInt1);
    stringBuilder.append(",raw code:");
    stringBuilder.append(paramInt2);
    stringBuilder.append(",description:");
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  public static String buildCodeInfo(int paramInt, String paramString) {
    StringBuilder stringBuilder = new StringBuilder("code:");
    stringBuilder.append(paramInt);
    stringBuilder.append(",description:");
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  public static final String getResponseCodeDescription(int paramInt) {
    new StringBuilder();
    switch (paramInt) {
      default:
        return null;
      case 6:
        return "api reach the upper limited";
      case 5:
        return "miniapp has not bond account";
      case 4:
        return "account not exist";
      case 3:
        return "invalid params";
      case 2:
        return "session timeout";
      case 1:
        return "system inner error";
      case 0:
        break;
    } 
    return "success";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\follow\FollowErrorMsgBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */