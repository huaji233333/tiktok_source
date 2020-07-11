package com.tt.miniapp.shortcut.dialog;

public class SingleSpanView {
  private int align = 17;
  
  private TextSpan span;
  
  private boolean visibility = true;
  
  public SingleSpanView() {}
  
  public SingleSpanView(TextSpan paramTextSpan) {
    this.span = paramTextSpan;
  }
  
  public SingleSpanView(TextSpan paramTextSpan, boolean paramBoolean, int paramInt) {
    this.span = paramTextSpan;
    this.visibility = paramBoolean;
    this.align = paramInt;
  }
  
  public int getAlign() {
    return this.align;
  }
  
  public TextSpan getSpan() {
    return this.span;
  }
  
  public boolean isVisibility() {
    return this.visibility;
  }
  
  public void setAlign(int paramInt) {
    this.align = paramInt;
  }
  
  public void setSpan(TextSpan paramTextSpan) {
    this.span = paramTextSpan;
  }
  
  public void setVisibility(boolean paramBoolean) {
    this.visibility = paramBoolean;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\dialog\SingleSpanView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */