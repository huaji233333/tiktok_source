package com.tt.miniapp.errorcode;

public enum Flow {
  Download,
  JsCore,
  Main,
  Meta("1"),
  WebView("1");
  
  private String code;
  
  static {
    Download = new Flow("Download", 1, "2");
    Main = new Flow("Main", 2, "3");
    WebView = new Flow("WebView", 3, "4");
    JsCore = new Flow("JsCore", 4, "5");
    $VALUES = new Flow[] { Meta, Download, Main, WebView, JsCore };
  }
  
  Flow(String paramString1) {
    this.code = paramString1;
  }
  
  public final String getCode() {
    return this.code;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\errorcode\Flow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */