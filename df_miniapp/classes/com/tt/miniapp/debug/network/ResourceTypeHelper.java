package com.tt.miniapp.debug.network;

public class ResourceTypeHelper {
  private final MimeMatcher<ResourceType> mMimeMatcher = new MimeMatcher<ResourceType>();
  
  public ResourceTypeHelper() {
    this.mMimeMatcher.addRule("text/css", ResourceType.STYLESHEET);
    this.mMimeMatcher.addRule("image/*", ResourceType.IMAGE);
    this.mMimeMatcher.addRule("application/x-javascript", ResourceType.SCRIPT);
    this.mMimeMatcher.addRule("text/javascript", ResourceType.XHR);
    this.mMimeMatcher.addRule("application/json", ResourceType.XHR);
    this.mMimeMatcher.addRule("text/*", ResourceType.DOCUMENT);
    this.mMimeMatcher.addRule("*", ResourceType.OTHER);
  }
  
  public ResourceType determineResourceType(String paramString) {
    paramString = stripContentExtras(paramString);
    return this.mMimeMatcher.match(paramString);
  }
  
  public String stripContentExtras(String paramString) {
    int i = paramString.indexOf(';');
    String str = paramString;
    if (i >= 0)
      str = paramString.substring(0, i); 
    return str;
  }
  
  public enum ResourceType {
    DOCUMENT("Document"),
    FONT("Document"),
    IMAGE("Document"),
    OTHER("Document"),
    SCRIPT("Document"),
    STYLESHEET("Stylesheet"),
    WEBSOCKET("Stylesheet"),
    XHR("Stylesheet");
    
    private final String mProtocolValue;
    
    static {
      FONT = new ResourceType("FONT", 3, "Font");
      SCRIPT = new ResourceType("SCRIPT", 4, "Script");
      XHR = new ResourceType("XHR", 5, "XHR");
      WEBSOCKET = new ResourceType("WEBSOCKET", 6, "WebSocket");
      OTHER = new ResourceType("OTHER", 7, "Other");
      $VALUES = new ResourceType[] { DOCUMENT, STYLESHEET, IMAGE, FONT, SCRIPT, XHR, WEBSOCKET, OTHER };
    }
    
    ResourceType(String param1String1) {
      this.mProtocolValue = param1String1;
    }
    
    public final String getProtocolValue() {
      return this.mProtocolValue;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\ResourceTypeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */