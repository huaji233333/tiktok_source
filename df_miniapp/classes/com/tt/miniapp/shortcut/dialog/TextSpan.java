package com.tt.miniapp.shortcut.dialog;

public class TextSpan {
  public Builder builder;
  
  public String text;
  
  public DialogConfig.TextClickListener textClickListener;
  
  public int textColor;
  
  public float textSize;
  
  public int typeFace;
  
  public Builder getBuilder() {
    return this.builder;
  }
  
  public String getText() {
    return this.text;
  }
  
  public DialogConfig.TextClickListener getTextClickListener() {
    return this.textClickListener;
  }
  
  public int getTextColor() {
    return this.textColor;
  }
  
  public float getTextSize() {
    return this.textSize;
  }
  
  public int getTypeFace() {
    return this.typeFace;
  }
  
  public static class Builder {
    private TextSpan mTextSpan = new TextSpan();
    
    public TextSpan build() {
      TextSpan textSpan = this.mTextSpan;
      textSpan.builder = this;
      return textSpan;
    }
    
    public Builder setClickCallback(DialogConfig.TextClickListener param1TextClickListener) {
      this.mTextSpan.textClickListener = param1TextClickListener;
      return this;
    }
    
    public Builder setText(String param1String) {
      this.mTextSpan.text = param1String;
      return this;
    }
    
    public Builder setTextColor(int param1Int) {
      this.mTextSpan.textColor = param1Int;
      return this;
    }
    
    public Builder setTextSize(float param1Float) {
      this.mTextSpan.textSize = param1Float;
      return this;
    }
    
    public Builder setTypeFace(int param1Int) {
      this.mTextSpan.typeFace = param1Int;
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\dialog\TextSpan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */