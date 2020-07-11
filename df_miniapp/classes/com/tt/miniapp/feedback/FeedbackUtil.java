package com.tt.miniapp.feedback;

import com.tt.miniapp.util.DateUtils;

public class FeedbackUtil {
  public static String createLog(Object... paramVarArgs) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(DateUtils.parseDateToSecond(System.currentTimeMillis()));
    stringBuilder.append(" ");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      Object object = paramVarArgs[i];
      stringBuilder.append(" ");
      if (object != null) {
        stringBuilder.append(object);
      } else {
        stringBuilder.append("null");
      } 
    } 
    stringBuilder.append("\n");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\FeedbackUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */