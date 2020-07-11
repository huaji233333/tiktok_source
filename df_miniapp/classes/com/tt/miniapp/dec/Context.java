package com.tt.miniapp.dec;

final class Context {
  static final int[] LOOKUP;
  
  static {
    int[] arrayOfInt = new int[2048];
    LOOKUP = arrayOfInt;
    unpackLookupTable(arrayOfInt, "         !!  !                  \"#$##%#$&'##(#)#++++++++++((&*'##,---,---,-----,-----,-----&#'###.///.///./////./////./////&#'# ", "A/*  ':  & : $   @");
  }
  
  private static void unpackLookupTable(int[] paramArrayOfint, String paramString1, String paramString2) {
    boolean bool = false;
    int i;
    for (i = 0; i < 256; i++) {
      paramArrayOfint[i] = i & 0x3F;
      paramArrayOfint[i + 512] = i >> 2;
      paramArrayOfint[i + 1792] = (i >> 6) + 2;
    } 
    for (i = 0; i < 128; i++)
      paramArrayOfint[i + 1024] = (paramString1.charAt(i) - 32) * 4; 
    for (i = 0; i < 64; i++) {
      int k = i & 0x1;
      paramArrayOfint[i + 1152] = k;
      paramArrayOfint[i + 1216] = k + 2;
    } 
    int j = 0;
    i = 1280;
    while (j < 19) {
      char c = paramString2.charAt(j);
      int k = 0;
      while (k < c - 32) {
        paramArrayOfint[i] = j & 0x3;
        k++;
        i++;
      } 
      j++;
    } 
    for (i = 0; i < 16; i++) {
      paramArrayOfint[i + 1792] = 1;
      paramArrayOfint[i + 2032] = 6;
    } 
    paramArrayOfint[1792] = 0;
    paramArrayOfint[2047] = 7;
    for (i = bool; i < 256; i++)
      paramArrayOfint[i + 1536] = paramArrayOfint[i + 1792] << 3; 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dec\Context.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */