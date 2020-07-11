package com.facebook.yoga;

public class YogaValue {
  static final YogaValue AUTO;
  
  static final YogaValue UNDEFINED = new YogaValue(1.0E21F, YogaUnit.UNDEFINED);
  
  static final YogaValue ZERO = new YogaValue(0.0F, YogaUnit.POINT);
  
  public final YogaUnit unit;
  
  public final float value;
  
  static {
    AUTO = new YogaValue(1.0E21F, YogaUnit.AUTO);
  }
  
  YogaValue(float paramFloat, int paramInt) {
    this(paramFloat, YogaUnit.fromInt(paramInt));
  }
  
  public YogaValue(float paramFloat, YogaUnit paramYogaUnit) {
    this.value = paramFloat;
    this.unit = paramYogaUnit;
  }
  
  public static YogaValue parse(String paramString) {
    return (paramString == null) ? null : ("undefined".equals(paramString) ? UNDEFINED : ("auto".equals(paramString) ? AUTO : (paramString.endsWith("%") ? new YogaValue(Float.parseFloat(paramString.substring(0, paramString.length() - 1)), YogaUnit.PERCENT) : new YogaValue(Float.parseFloat(paramString), YogaUnit.POINT))));
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof YogaValue) {
      paramObject = paramObject;
      YogaUnit yogaUnit = this.unit;
      if (yogaUnit == ((YogaValue)paramObject).unit)
        return (yogaUnit == YogaUnit.UNDEFINED || Float.compare(this.value, ((YogaValue)paramObject).value) == 0); 
    } 
    return false;
  }
  
  public int hashCode() {
    return Float.floatToIntBits(this.value) + this.unit.intValue();
  }
  
  public String toString() {
    int i = null.a[this.unit.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i == 4)
            return "auto"; 
          throw new IllegalStateException();
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.value);
        stringBuilder.append("%");
        return stringBuilder.toString();
      } 
      return Float.toString(this.value);
    } 
    return "undefined";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */