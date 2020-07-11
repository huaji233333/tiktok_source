package com.facebook.react.bridge;

import com.a;
import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Deque;

public class JsonWriter implements Closeable {
  private final Deque<Scope> mScopes;
  
  private final Writer mWriter;
  
  public JsonWriter(Writer paramWriter) {
    this.mWriter = paramWriter;
    this.mScopes = new ArrayDeque<Scope>();
  }
  
  private void beforeName() throws IOException {
    Scope scope = this.mScopes.peek();
    int i = null.$SwitchMap$com$facebook$react$bridge$JsonWriter$Scope[scope.ordinal()];
    if (i != 1)
      if (i != 2) {
        if (i != 3) {
          if (i == 4) {
            this.mWriter.write(44);
            return;
          } 
          StringBuilder stringBuilder = new StringBuilder("Unknown scope: ");
          stringBuilder.append(scope);
          throw new IllegalStateException(stringBuilder.toString());
        } 
      } else {
        replace(Scope.OBJECT);
        return;
      }  
    throw new IllegalStateException("name not allowed in array");
  }
  
  private void beforeValue() throws IOException {
    Scope scope = this.mScopes.peek();
    int i = null.$SwitchMap$com$facebook$react$bridge$JsonWriter$Scope[scope.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i == 4)
            return; 
          StringBuilder stringBuilder = new StringBuilder("Unknown scope: ");
          stringBuilder.append(scope);
          throw new IllegalStateException(stringBuilder.toString());
        } 
        this.mWriter.write(44);
        return;
      } 
      throw new IllegalArgumentException(Scope.EMPTY_OBJECT.name());
    } 
    replace(Scope.ARRAY);
  }
  
  private void close(char paramChar) throws IOException {
    this.mScopes.pop();
    this.mWriter.write(paramChar);
  }
  
  private void open(Scope paramScope, char paramChar) throws IOException {
    this.mScopes.push(paramScope);
    this.mWriter.write(paramChar);
  }
  
  private void replace(Scope paramScope) {
    this.mScopes.pop();
    this.mScopes.push(paramScope);
  }
  
  private void string(String paramString) throws IOException {
    this.mWriter.write(34);
    int j = paramString.length();
    for (int i = 0; i < j; i++) {
      char c = paramString.charAt(i);
      if (c != '\f') {
        if (c != '\r') {
          if (c != '"' && c != '\\') {
            if (c != ' ' && c != ' ') {
              switch (c) {
                default:
                  if (c <= '\037') {
                    this.mWriter.write(a.a("\\u%04x", new Object[] { Integer.valueOf(c) }));
                    break;
                  } 
                  this.mWriter.write(c);
                case '\n':
                  this.mWriter.write("\\n");
                  break;
                case '\t':
                  this.mWriter.write("\\t");
                  break;
                case '\b':
                  this.mWriter.write("\\b");
                  break;
              } 
            } else {
              this.mWriter.write(a.a("\\u%04x", new Object[] { Integer.valueOf(c) }));
            } 
          } else {
            this.mWriter.write(92);
            this.mWriter.write(c);
          } 
        } else {
          this.mWriter.write("\\r");
        } 
      } else {
        this.mWriter.write("\\f");
      } 
    } 
    this.mWriter.write(34);
  }
  
  public JsonWriter beginArray() throws IOException {
    open(Scope.EMPTY_ARRAY, '[');
    return this;
  }
  
  public JsonWriter beginObject() throws IOException {
    open(Scope.EMPTY_OBJECT, '{');
    return this;
  }
  
  public void close() throws IOException {
    this.mWriter.close();
  }
  
  public JsonWriter endArray() throws IOException {
    close(']');
    return this;
  }
  
  public JsonWriter endObject() throws IOException {
    close('}');
    return this;
  }
  
  public JsonWriter name(String paramString) throws IOException {
    if (paramString != null) {
      beforeName();
      string(paramString);
      this.mWriter.write(58);
      return this;
    } 
    throw new NullPointerException("name can not be null");
  }
  
  public JsonWriter nullValue() throws IOException {
    beforeValue();
    this.mWriter.write("null");
    return this;
  }
  
  public JsonWriter rawValue(String paramString) throws IOException {
    beforeValue();
    this.mWriter.write(paramString);
    return this;
  }
  
  public JsonWriter value(double paramDouble) throws IOException {
    beforeValue();
    this.mWriter.append(Double.toString(paramDouble));
    return this;
  }
  
  public JsonWriter value(long paramLong) throws IOException {
    beforeValue();
    this.mWriter.write(Long.toString(paramLong));
    return this;
  }
  
  public JsonWriter value(Number paramNumber) throws IOException {
    if (paramNumber == null)
      return nullValue(); 
    beforeValue();
    this.mWriter.append(paramNumber.toString());
    return this;
  }
  
  public JsonWriter value(String paramString) throws IOException {
    if (paramString == null)
      return nullValue(); 
    beforeValue();
    string(paramString);
    return this;
  }
  
  public JsonWriter value(boolean paramBoolean) throws IOException {
    String str;
    beforeValue();
    Writer writer = this.mWriter;
    if (paramBoolean) {
      str = "true";
    } else {
      str = "false";
    } 
    writer.write(str);
    return this;
  }
  
  enum Scope {
    ARRAY, EMPTY_ARRAY, EMPTY_OBJECT, OBJECT;
    
    static {
      ARRAY = new Scope("ARRAY", 3);
      $VALUES = new Scope[] { EMPTY_OBJECT, OBJECT, EMPTY_ARRAY, ARRAY };
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JsonWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */