package com.tt.miniapp.dec;

final class Huffman {
  static void buildHuffmanTable(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3) {
    int[] arrayOfInt1 = new int[paramInt3];
    int[] arrayOfInt2 = new int[16];
    int[] arrayOfInt3 = new int[16];
    int i;
    for (i = 0; i < paramInt3; i++) {
      int i4 = paramArrayOfint2[i];
      arrayOfInt2[i4] = arrayOfInt2[i4] + 1;
    } 
    arrayOfInt3[1] = 0;
    for (i = 1; i < 15; i = i4) {
      int i4 = i + 1;
      arrayOfInt3[i4] = arrayOfInt3[i] + arrayOfInt2[i];
    } 
    for (i = 0; i < paramInt3; i++) {
      if (paramArrayOfint2[i] != 0) {
        int i4 = paramArrayOfint2[i];
        int i5 = arrayOfInt3[i4];
        arrayOfInt3[i4] = i5 + 1;
        arrayOfInt1[i5] = i;
      } 
    } 
    int i3 = 1 << paramInt2;
    if (arrayOfInt3[15] == 1) {
      for (paramInt2 = 0; paramInt2 < i3; paramInt2++)
        paramArrayOfint1[paramInt1 + paramInt2] = arrayOfInt1[0]; 
      return;
    } 
    int i1 = 2;
    int k = 1;
    paramInt3 = 0;
    i = 0;
    int j;
    for (j = 2; k <= paramInt2; j <<= 1) {
      while (arrayOfInt2[k] > 0) {
        replicateValue(paramArrayOfint1, paramInt1 + paramInt3, j, i3, arrayOfInt1[i] | k << 16);
        paramInt3 = getNextKey(paramInt3, k);
        arrayOfInt2[k] = arrayOfInt2[k] - 1;
        i++;
      } 
      k++;
    } 
    int m = -1;
    k = paramInt2 + 1;
    int n = i3;
    j = paramInt1;
    int i2 = i;
    i = k;
    k = paramInt3;
    paramInt3 = i1;
    i1 = j;
    while (i <= 15) {
      j = i2;
      while (arrayOfInt2[i] > 0) {
        int i6 = k & i3 - 1;
        int i5 = i1;
        int i4 = n;
        i2 = m;
        if (i6 != m) {
          i5 = i1 + n;
          m = nextTableBitSize(arrayOfInt2, i, paramInt2);
          paramArrayOfint1[paramInt1 + i6] = m + paramInt2 << 16 | i5 - paramInt1 - i6;
          i4 = 1 << m;
          i2 = i6;
        } 
        replicateValue(paramArrayOfint1, (k >> paramInt2) + i5, paramInt3, i4, i - paramInt2 << 16 | arrayOfInt1[j]);
        k = getNextKey(k, i);
        arrayOfInt2[i] = arrayOfInt2[i] - 1;
        j++;
        i1 = i5;
        n = i4;
        m = i2;
      } 
      i++;
      paramInt3 <<= 1;
      i2 = j;
    } 
  }
  
  private static int getNextKey(int paramInt1, int paramInt2) {
    for (paramInt2 = 1 << paramInt2 - 1; (paramInt1 & paramInt2) != 0; paramInt2 >>= 1);
    return (paramInt1 & paramInt2 - 1) + paramInt2;
  }
  
  private static int nextTableBitSize(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    int i = 1 << paramInt1 - paramInt2;
    while (paramInt1 < 15) {
      i -= paramArrayOfint[paramInt1];
      if (i > 0) {
        paramInt1++;
        i <<= 1;
      } 
    } 
    return paramInt1 - paramInt2;
  }
  
  private static void replicateValue(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i;
    do {
      i = paramInt3 - paramInt2;
      paramArrayOfint[paramInt1 + i] = paramInt4;
      paramInt3 = i;
    } while (i > 0);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dec\Huffman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */