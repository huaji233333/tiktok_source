package com.tt.miniapp.shortcut.dialog;

import java.util.List;

public class MultiSpanView {
  private int align = 17;
  
  private List<TextSpan> spans;
  
  private boolean visibility = true;
  
  public MultiSpanView() {}
  
  public MultiSpanView(List<TextSpan> paramList) {
    this.spans = paramList;
  }
  
  public MultiSpanView(List<TextSpan> paramList, boolean paramBoolean, int paramInt) {
    this.spans = paramList;
    this.visibility = paramBoolean;
    this.align = paramInt;
  }
  
  public int getAlign() {
    return this.align;
  }
  
  public List<TextSpan> getSpans() {
    return this.spans;
  }
  
  public boolean isVisibility() {
    return this.visibility;
  }
  
  public void setAlign(int paramInt) {
    this.align = paramInt;
  }
  
  public void setSpans(List<TextSpan> paramList) {
    this.spans = paramList;
  }
  
  public void setVisibility(boolean paramBoolean) {
    this.visibility = paramBoolean;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\dialog\MultiSpanView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */