package com.facebook.react.common;

public class LongArray {
  private long[] mArray;
  
  private int mLength;
  
  private LongArray(int paramInt) {
    this.mArray = new long[paramInt];
  }
  
  public static LongArray createWithInitialCapacity(int paramInt) {
    return new LongArray(paramInt);
  }
  
  private void growArrayIfNeeded() {
    int i = this.mLength;
    if (i == this.mArray.length) {
      double d = i;
      Double.isNaN(d);
      long[] arrayOfLong = new long[Math.max(i + 1, (int)(d * 1.8D))];
      System.arraycopy(this.mArray, 0, arrayOfLong, 0, this.mLength);
      this.mArray = arrayOfLong;
    } 
  }
  
  public void add(long paramLong) {
    growArrayIfNeeded();
    long[] arrayOfLong = this.mArray;
    int i = this.mLength;
    this.mLength = i + 1;
    arrayOfLong[i] = paramLong;
  }
  
  public void dropTail(int paramInt) {
    int i = this.mLength;
    if (paramInt <= i) {
      this.mLength = i - paramInt;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Trying to drop ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" items from array of length ");
    stringBuilder.append(this.mLength);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public long get(int paramInt) {
    if (paramInt < this.mLength)
      return this.mArray[paramInt]; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt);
    stringBuilder.append(" >= ");
    stringBuilder.append(this.mLength);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public boolean isEmpty() {
    return (this.mLength == 0);
  }
  
  public void set(int paramInt, long paramLong) {
    if (paramInt < this.mLength) {
      this.mArray[paramInt] = paramLong;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt);
    stringBuilder.append(" >= ");
    stringBuilder.append(this.mLength);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public int size() {
    return this.mLength;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\LongArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */