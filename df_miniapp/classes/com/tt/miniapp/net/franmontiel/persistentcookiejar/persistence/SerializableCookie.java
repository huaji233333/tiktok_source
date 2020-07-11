package com.tt.miniapp.net.franmontiel.persistentcookiejar.persistence;

import android.util.Log;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import okhttp3.l;

public class SerializableCookie implements Serializable {
  private static long NON_VALID_EXPIRES_AT = -1L;
  
  private static final long serialVersionUID = -8594045714036645534L;
  
  private transient l cookie;
  
  private static String byteArrayToHexString(byte[] paramArrayOfbyte) {
    StringBuilder stringBuilder = new StringBuilder(paramArrayOfbyte.length * 2);
    int j = paramArrayOfbyte.length;
    for (int i = 0; i < j; i++) {
      int k = paramArrayOfbyte[i] & 0xFF;
      if (k < 16)
        stringBuilder.append('0'); 
      stringBuilder.append(Integer.toHexString(k));
    } 
    return stringBuilder.toString();
  }
  
  private static byte[] hexStringToByteArray(String paramString) {
    try {
      int i;
      return arrayOfByte;
    } finally {
      Exception exception = null;
      AppBrandLogger.eWithThrowable("tma_SerializableCookie", paramString, exception);
      AppBrandMonitor.reportError("cookie_hexStringToByteArray", Log.getStackTraceString(exception), paramString);
    } 
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    l.a a = new l.a();
    a.a((String)paramObjectInputStream.readObject());
    a.b((String)paramObjectInputStream.readObject());
    long l1 = paramObjectInputStream.readLong();
    if (l1 != NON_VALID_EXPIRES_AT) {
      long l2 = l1;
      if (l1 <= 0L)
        l2 = Long.MIN_VALUE; 
      l1 = l2;
      if (l2 > 253402300799999L)
        l1 = 253402300799999L; 
      a.c = l1;
      a.h = true;
    } 
    String str1 = (String)paramObjectInputStream.readObject();
    a.c(str1);
    String str2 = (String)paramObjectInputStream.readObject();
    if (str2.startsWith("/")) {
      a.e = str2;
      if (paramObjectInputStream.readBoolean())
        a.f = true; 
      if (paramObjectInputStream.readBoolean())
        a.g = true; 
      if (paramObjectInputStream.readBoolean())
        a.a(str1, true); 
      this.cookie = a.a();
      return;
    } 
    throw new IllegalArgumentException("path must start with '/'");
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    long l1;
    paramObjectOutputStream.writeObject(this.cookie.a);
    paramObjectOutputStream.writeObject(this.cookie.b);
    if (this.cookie.h) {
      l1 = this.cookie.c;
    } else {
      l1 = NON_VALID_EXPIRES_AT;
    } 
    paramObjectOutputStream.writeLong(l1);
    paramObjectOutputStream.writeObject(this.cookie.d);
    paramObjectOutputStream.writeObject(this.cookie.e);
    paramObjectOutputStream.writeBoolean(this.cookie.f);
    paramObjectOutputStream.writeBoolean(this.cookie.g);
    paramObjectOutputStream.writeBoolean(this.cookie.i);
  }
  
  public l decode(String paramString) {
    byte[] arrayOfByte = hexStringToByteArray(paramString);
    if (arrayOfByte != null) {
      Exception exception2;
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      try {
        ObjectInputStream objectInputStream2 = new ObjectInputStream(byteArrayInputStream);
        ObjectInputStream objectInputStream1 = objectInputStream2;
      } catch (IOException iOException) {
      
      } catch (ClassNotFoundException classNotFoundException) {
        ByteArrayInputStream byteArrayInputStream1 = null;
        byteArrayInputStream = byteArrayInputStream1;
        AppBrandLogger.e("tma_SerializableCookie", new Object[] { "ClassNotFoundException in decodeCookie", classNotFoundException });
      } finally {
        exception2 = null;
      } 
      Exception exception1 = exception2;
      AppBrandLogger.e("tma_SerializableCookie", new Object[] { "IOException in decodeCookie", classNotFoundException });
      if (exception2 != null)
        try {
          exception2.close();
          return null;
        } catch (IOException iOException) {
          AppBrandLogger.e("tma_SerializableCookie", new Object[] { "Stream not closed in decodeCookie", iOException });
          return null;
        }  
    } 
    return null;
  }
  
  public String encode(l paraml) {
    this.cookie = paraml;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    iOException = null;
    try {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    } catch (IOException iOException1) {
    
    } finally {
      if (iOException != null)
        try {
          iOException.close();
        } catch (IOException iOException1) {} 
    } 
    if (paraml != null)
      try {
        paraml.close();
        return null;
      } catch (IOException iOException1) {
        return null;
      }  
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\franmontiel\persistentcookiejar\persistence\SerializableCookie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */