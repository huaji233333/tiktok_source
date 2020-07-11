package com.facebook.yoga;

public enum YogaEdge {
  ALL,
  BOTTOM,
  END,
  HORIZONTAL,
  LEFT(0),
  RIGHT(0),
  START(0),
  TOP(1),
  VERTICAL(1);
  
  private final int mIntValue;
  
  static {
    RIGHT = new YogaEdge("RIGHT", 2, 2);
    BOTTOM = new YogaEdge("BOTTOM", 3, 3);
    START = new YogaEdge("START", 4, 4);
    END = new YogaEdge("END", 5, 5);
    HORIZONTAL = new YogaEdge("HORIZONTAL", 6, 6);
    VERTICAL = new YogaEdge("VERTICAL", 7, 7);
    ALL = new YogaEdge("ALL", 8, 8);
    $VALUES = new YogaEdge[] { LEFT, TOP, RIGHT, BOTTOM, START, END, HORIZONTAL, VERTICAL, ALL };
  }
  
  YogaEdge(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaEdge fromInt(int paramInt) {
    StringBuilder stringBuilder;
    switch (paramInt) {
      default:
        stringBuilder = new StringBuilder("Unknown enum value: ");
        stringBuilder.append(paramInt);
        throw new IllegalArgumentException(stringBuilder.toString());
      case 8:
        return ALL;
      case 7:
        return VERTICAL;
      case 6:
        return HORIZONTAL;
      case 5:
        return END;
      case 4:
        return START;
      case 3:
        return BOTTOM;
      case 2:
        return RIGHT;
      case 1:
        return TOP;
      case 0:
        break;
    } 
    return LEFT;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaEdge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */