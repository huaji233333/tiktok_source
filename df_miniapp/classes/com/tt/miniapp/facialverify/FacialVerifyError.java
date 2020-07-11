package com.tt.miniapp.facialverify;

public class FacialVerifyError {
  public static int wrapAiLabErrorCode(int paramInt) {
    return (paramInt == 0) ? 0 : ((paramInt == -1) ? 4001 : ((paramInt >= 1000 && paramInt < 2000) ? 2001 : ((paramInt >= 2000 && paramInt < 3000) ? 2100 : ((paramInt == 3000 || paramInt == 3001) ? 2201 : ((paramInt == 4007) ? 3001 : ((paramInt == 4103) ? 3100 : ((paramInt == 4998) ? 3200 : ((paramInt == 3002 || paramInt == 4004 || paramInt == 4005 || paramInt == 4006 || paramInt == 4008 || paramInt == 4101 || paramInt == 4999) ? 3999 : 1200))))))));
  }
  
  public static String wrapErrMsg(int paramInt) {
    return (paramInt != 1001) ? ((paramInt != 1100) ? ((paramInt != 2001) ? ((paramInt != 2100) ? ((paramInt != 2203) ? ((paramInt != 3001) ? ((paramInt != 3100) ? ((paramInt != 3200) ? ((paramInt != 3999) ? ((paramInt != 4001) ? ((paramInt != 2200) ? ((paramInt != 2201) ? "service error" : "user denied camera permission") : "user deny") : "feature is not support in app") : "facial recognition failed") : "") : "user name or id card error") : "user action error") : "user cancel login") : "network error") : "invalid params") : "run out of credit") : "no permission";
  }
  
  public static int wrapServerErrorCode(int paramInt) {
    return (paramInt != 0) ? ((paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 3) ? 1200 : 1001) : 3100) : 1100) : 0;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\facialverify\FacialVerifyError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */