package com.facebook.react.bridge;

public class SoftAssertions {
  public static void assertCondition(boolean paramBoolean, String paramString) {
    if (paramBoolean)
      return; 
    throw new AssertionException(paramString);
  }
  
  public static <T> T assertNotNull(T paramT) {
    if (paramT != null)
      return paramT; 
    throw new AssertionException("Expected object to not be null!");
  }
  
  public static void assertUnreachable(String paramString) {
    throw new AssertionException(paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\SoftAssertions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */