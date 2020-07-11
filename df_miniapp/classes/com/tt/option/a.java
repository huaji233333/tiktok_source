package com.tt.option;

public abstract class a<T> {
  protected T defaultOptionDepend;
  
  protected abstract T init();
  
  protected boolean inject() {
    if (this.defaultOptionDepend != null)
      return true; 
    this.defaultOptionDepend = init();
    return (this.defaultOptionDepend != null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */