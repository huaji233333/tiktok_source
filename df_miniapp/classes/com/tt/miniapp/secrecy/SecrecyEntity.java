package com.tt.miniapp.secrecy;

public class SecrecyEntity {
  public int type;
  
  public SecrecyEntity(int paramInt) {
    this.type = paramInt;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject != null) {
      if (getClass() != paramObject.getClass())
        return false; 
      paramObject = paramObject;
      if (this.type == ((SecrecyEntity)paramObject).type)
        return true; 
    } 
    return false;
  }
  
  public int hashCode() {
    return this.type;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\secrecy\SecrecyEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */