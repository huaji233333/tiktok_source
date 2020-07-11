package com.facebook.react.modules.blob;

import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.network.NetworkingModule;
import com.facebook.react.modules.websocket.WebSocketModule;
import g.i;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import okhttp3.ad;
import okhttp3.af;
import okhttp3.w;

@ReactModule(name = "BlobModule")
public class BlobModule extends ReactContextBaseJavaModule {
  private final Map<String, byte[]> mBlobs = (Map)new HashMap<String, byte>();
  
  private final NetworkingModule.RequestBodyHandler mNetworkingRequestBodyHandler = new NetworkingModule.RequestBodyHandler() {
      public boolean supports(ReadableMap param1ReadableMap) {
        return param1ReadableMap.hasKey("blob");
      }
      
      public ad toRequestBody(ReadableMap param1ReadableMap, String param1String) {
        String str = param1String;
        if (param1ReadableMap.hasKey("type")) {
          str = param1String;
          if (!param1ReadableMap.getString("type").isEmpty())
            str = param1ReadableMap.getString("type"); 
        } 
        param1String = str;
        if (str == null)
          param1String = "application/octet-stream"; 
        param1ReadableMap = param1ReadableMap.getMap("blob");
        str = param1ReadableMap.getString("blobId");
        byte[] arrayOfByte = BlobModule.this.resolve(str, param1ReadableMap.getInt("offset"), param1ReadableMap.getInt("size"));
        return ad.create(w.a(param1String), arrayOfByte);
      }
    };
  
  private final NetworkingModule.ResponseHandler mNetworkingResponseHandler = new NetworkingModule.ResponseHandler() {
      public boolean supports(String param1String) {
        return param1String.equals("blob");
      }
      
      public WritableMap toResponseData(af param1af) throws IOException {
        byte[] arrayOfByte = param1af.bytes();
        WritableMap writableMap = Arguments.createMap();
        writableMap.putString("blobId", BlobModule.this.store(arrayOfByte));
        writableMap.putInt("offset", 0);
        writableMap.putInt("size", arrayOfByte.length);
        return writableMap;
      }
    };
  
  private final NetworkingModule.UriHandler mNetworkingUriHandler = new NetworkingModule.UriHandler() {
      public WritableMap fetch(Uri param1Uri) throws IOException {
        byte[] arrayOfByte = BlobModule.this.getBytesFromUri(param1Uri);
        WritableMap writableMap = Arguments.createMap();
        writableMap.putString("blobId", BlobModule.this.store(arrayOfByte));
        writableMap.putInt("offset", 0);
        writableMap.putInt("size", arrayOfByte.length);
        writableMap.putString("type", BlobModule.this.getMimeTypeFromUri(param1Uri));
        writableMap.putString("name", BlobModule.this.getNameFromUri(param1Uri));
        writableMap.putDouble("lastModified", BlobModule.this.getLastModifiedFromUri(param1Uri));
        return writableMap;
      }
      
      public boolean supports(Uri param1Uri, String param1String) {
        boolean bool;
        String str = param1Uri.getScheme();
        if ("http".equals(str) || "https".equals(str)) {
          bool = true;
        } else {
          bool = false;
        } 
        return (!bool && param1String.equals("blob"));
      }
    };
  
  private final WebSocketModule.ContentHandler mWebSocketContentHandler = new WebSocketModule.ContentHandler() {
      public void onMessage(i param1i, WritableMap param1WritableMap) {
        byte[] arrayOfByte = param1i.toByteArray();
        WritableMap writableMap = Arguments.createMap();
        writableMap.putString("blobId", BlobModule.this.store(arrayOfByte));
        writableMap.putInt("offset", 0);
        writableMap.putInt("size", arrayOfByte.length);
        param1WritableMap.putMap("data", writableMap);
        param1WritableMap.putString("type", "blob");
      }
      
      public void onMessage(String param1String, WritableMap param1WritableMap) {
        param1WritableMap.putString("data", param1String);
      }
    };
  
  public BlobModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private WebSocketModule getWebSocketModule() {
    return (WebSocketModule)getReactApplicationContext().getNativeModule(WebSocketModule.class);
  }
  
  @ReactMethod
  public void addNetworkingHandler() {
    NetworkingModule networkingModule = (NetworkingModule)getReactApplicationContext().getNativeModule(NetworkingModule.class);
    networkingModule.addUriHandler(this.mNetworkingUriHandler);
    networkingModule.addRequestBodyHandler(this.mNetworkingRequestBodyHandler);
    networkingModule.addResponseHandler(this.mNetworkingResponseHandler);
  }
  
  @ReactMethod
  public void addWebSocketHandler(int paramInt) {
    getWebSocketModule().setContentHandler(paramInt, this.mWebSocketContentHandler);
  }
  
  @ReactMethod
  public void createFromParts(ReadableArray paramReadableArray, String paramString) {
    ArrayList<byte[]> arrayList = new ArrayList(paramReadableArray.size());
    int i = 0;
    int j;
    for (j = 0; i < paramReadableArray.size(); j = k) {
      byte[] arrayOfByte;
      ReadableMap readableMap = paramReadableArray.getMap(i);
      String str = readableMap.getString("type");
      int k = -1;
      int m = str.hashCode();
      if (m != -891985903) {
        if (m == 3026845 && str.equals("blob"))
          k = 0; 
      } else if (str.equals("string")) {
        k = 1;
      } 
      if (k != 0) {
        if (k == 1) {
          arrayOfByte = readableMap.getString("data").getBytes(Charset.forName("UTF-8"));
          k = j + arrayOfByte.length;
          arrayList.add(i, arrayOfByte);
        } else {
          StringBuilder stringBuilder = new StringBuilder("Invalid type for blob: ");
          stringBuilder.append(arrayOfByte.getString("type"));
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        ReadableMap readableMap1 = arrayOfByte.getMap("data");
        k = j + readableMap1.getInt("size");
        arrayList.add(i, resolve(readableMap1));
      } 
      i++;
    } 
    ByteBuffer byteBuffer = ByteBuffer.allocate(j);
    Iterator<byte> iterator = arrayList.iterator();
    while (iterator.hasNext())
      byteBuffer.put((byte[])iterator.next()); 
    store(byteBuffer.array(), paramString);
  }
  
  public byte[] getBytesFromUri(Uri paramUri) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream;
    InputStream inputStream = getReactApplicationContext().getContentResolver().openInputStream(paramUri);
    if (inputStream != null) {
      byteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[1024];
      while (true) {
        int i = inputStream.read(arrayOfByte);
        if (i != -1) {
          byteArrayOutputStream.write(arrayOfByte, 0, i);
          continue;
        } 
        return byteArrayOutputStream.toByteArray();
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder("File not found for ");
    stringBuilder.append(byteArrayOutputStream);
    FileNotFoundException fileNotFoundException = new FileNotFoundException(stringBuilder.toString());
    throw fileNotFoundException;
  }
  
  public Map<String, Object> getConstants() {
    Resources resources = getReactApplicationContext().getResources();
    int i = resources.getIdentifier("blob_provider_authority", "string", getReactApplicationContext().getPackageName());
    return (i == 0) ? null : MapBuilder.of("BLOB_URI_SCHEME", "content", "BLOB_URI_HOST", resources.getString(i));
  }
  
  public long getLastModifiedFromUri(Uri paramUri) {
    return paramUri.getScheme().equals("file") ? (new File(paramUri.toString())).lastModified() : 0L;
  }
  
  public String getMimeTypeFromUri(Uri paramUri) {
    String str3 = getReactApplicationContext().getContentResolver().getType(paramUri);
    String str2 = str3;
    if (str3 == null) {
      String str = MimeTypeMap.getFileExtensionFromUrl(paramUri.getPath());
      str2 = str3;
      if (str != null)
        str2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str); 
    } 
    String str1 = str2;
    if (str2 == null)
      str1 = ""; 
    return str1;
  }
  
  public String getName() {
    return "BlobModule";
  }
  
  public String getNameFromUri(Uri paramUri) {
    if (paramUri.getScheme().equals("file"))
      return paramUri.getLastPathSegment(); 
    Cursor cursor = getReactApplicationContext().getContentResolver().query(paramUri, new String[] { "_display_name" }, null, null, null);
    if (cursor != null)
      try {
        if (cursor.moveToFirst())
          return cursor.getString(0); 
      } finally {
        cursor.close();
      }  
    return paramUri.getLastPathSegment();
  }
  
  @ReactMethod
  public void release(String paramString) {
    remove(paramString);
  }
  
  public void remove(String paramString) {
    this.mBlobs.remove(paramString);
  }
  
  @ReactMethod
  public void removeWebSocketHandler(int paramInt) {
    getWebSocketModule().setContentHandler(paramInt, null);
  }
  
  public byte[] resolve(Uri paramUri) {
    boolean bool;
    byte b;
    String str2 = paramUri.getLastPathSegment();
    String str3 = paramUri.getQueryParameter("offset");
    if (str3 != null) {
      bool = Integer.parseInt(str3, 10);
    } else {
      bool = false;
    } 
    String str1 = paramUri.getQueryParameter("size");
    if (str1 != null) {
      b = Integer.parseInt(str1, 10);
    } else {
      b = -1;
    } 
    return resolve(str2, bool, b);
  }
  
  public byte[] resolve(ReadableMap paramReadableMap) {
    return resolve(paramReadableMap.getString("blobId"), paramReadableMap.getInt("offset"), paramReadableMap.getInt("size"));
  }
  
  public byte[] resolve(String paramString, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = this.mBlobs.get(paramString);
    if (arrayOfByte == null)
      return null; 
    int i = paramInt2;
    if (paramInt2 == -1)
      i = arrayOfByte.length - paramInt1; 
    if (paramInt1 <= 0) {
      byte[] arrayOfByte1 = arrayOfByte;
      return (i != arrayOfByte.length) ? Arrays.copyOfRange(arrayOfByte, paramInt1, i + paramInt1) : arrayOfByte1;
    } 
    return Arrays.copyOfRange(arrayOfByte, paramInt1, i + paramInt1);
  }
  
  @ReactMethod
  public void sendOverSocket(ReadableMap paramReadableMap, int paramInt) {
    byte[] arrayOfByte = resolve(paramReadableMap.getString("blobId"), paramReadableMap.getInt("offset"), paramReadableMap.getInt("size"));
    if (arrayOfByte != null) {
      getWebSocketModule().sendBinary(i.of(arrayOfByte), paramInt);
      return;
    } 
    getWebSocketModule().sendBinary(null, paramInt);
  }
  
  public String store(byte[] paramArrayOfbyte) {
    String str = UUID.randomUUID().toString();
    store(paramArrayOfbyte, str);
    return str;
  }
  
  public void store(byte[] paramArrayOfbyte, String paramString) {
    this.mBlobs.put(paramString, paramArrayOfbyte);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\blob\BlobModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */