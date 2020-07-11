package com.facebook.react.packagerconnection;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import com.facebook.common.e.a;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class FileIoHandler implements Runnable {
  private static final String TAG = JSPackagerClient.class.getSimpleName();
  
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  
  private int mNextHandle = 1;
  
  public final Map<Integer, TtlFileInputStream> mOpenFiles = new HashMap<Integer, TtlFileInputStream>();
  
  private final Map<String, RequestHandler> mRequestHandlers = new HashMap<String, RequestHandler>();
  
  public FileIoHandler() {
    this.mRequestHandlers.put("fopen", new RequestOnlyHandler() {
          public void onRequest(Object param1Object, Responder param1Responder) {
            Map<Integer, FileIoHandler.TtlFileInputStream> map = FileIoHandler.this.mOpenFiles;
            /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/util/Map<[ObjectType{java/lang/Integer}, InnerObjectType{ObjectType{com/facebook/react/packagerconnection/FileIoHandler}.Lcom/facebook/react/packagerconnection/FileIoHandler$TtlFileInputStream;}]>}, name=null} */
            try {
              JSONObject jSONObject = (JSONObject)param1Object;
              if (jSONObject != null) {
                param1Object = jSONObject.optString("mode");
                if (param1Object != null) {
                  String str = jSONObject.optString("filename");
                  if (str != null) {
                    if (param1Object.equals("r")) {
                      param1Responder.respond(Integer.valueOf(FileIoHandler.this.addOpenFile(str)));
                    } else {
                      StringBuilder stringBuilder = new StringBuilder("unsupported mode: ");
                      stringBuilder.append((String)param1Object);
                      throw new IllegalArgumentException(stringBuilder.toString());
                    } 
                  } else {
                    throw new Exception("missing params.filename");
                  } 
                } else {
                  throw new Exception("missing params.mode");
                } 
              } else {
                throw new Exception("params must be an object { mode: string, filename: string }");
              } 
            } catch (Exception exception) {
              param1Responder.error(exception.toString());
            } finally {}
            /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/util/Map<[ObjectType{java/lang/Integer}, InnerObjectType{ObjectType{com/facebook/react/packagerconnection/FileIoHandler}.Lcom/facebook/react/packagerconnection/FileIoHandler$TtlFileInputStream;}]>}, name=null} */
          }
        });
    this.mRequestHandlers.put("fclose", new RequestOnlyHandler() {
          public void onRequest(Object param1Object, Responder param1Responder) {
            Map<Integer, FileIoHandler.TtlFileInputStream> map = FileIoHandler.this.mOpenFiles;
            /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/util/Map<[ObjectType{java/lang/Integer}, InnerObjectType{ObjectType{com/facebook/react/packagerconnection/FileIoHandler}.Lcom/facebook/react/packagerconnection/FileIoHandler$TtlFileInputStream;}]>}, name=null} */
            try {
              if (param1Object instanceof Number) {
                FileIoHandler.TtlFileInputStream ttlFileInputStream = FileIoHandler.this.mOpenFiles.get(Integer.valueOf(((Integer)param1Object).intValue()));
                if (ttlFileInputStream != null) {
                  FileIoHandler.this.mOpenFiles.remove(Integer.valueOf(((Integer)param1Object).intValue()));
                  ttlFileInputStream.close();
                  param1Responder.respond("");
                } else {
                  throw new Exception("invalid file handle, it might have timed out");
                } 
              } else {
                throw new Exception("params must be a file handle");
              } 
            } catch (Exception exception) {
              param1Responder.error(exception.toString());
            } finally {}
            /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/util/Map<[ObjectType{java/lang/Integer}, InnerObjectType{ObjectType{com/facebook/react/packagerconnection/FileIoHandler}.Lcom/facebook/react/packagerconnection/FileIoHandler$TtlFileInputStream;}]>}, name=null} */
          }
        });
    this.mRequestHandlers.put("fread", new RequestOnlyHandler() {
          public void onRequest(Object param1Object, Responder param1Responder) {
            Map<Integer, FileIoHandler.TtlFileInputStream> map = FileIoHandler.this.mOpenFiles;
            /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/util/Map<[ObjectType{java/lang/Integer}, InnerObjectType{ObjectType{com/facebook/react/packagerconnection/FileIoHandler}.Lcom/facebook/react/packagerconnection/FileIoHandler$TtlFileInputStream;}]>}, name=null} */
            try {
              param1Object = param1Object;
              if (param1Object != null) {
                int i = param1Object.optInt("file");
                if (i != 0) {
                  int j = param1Object.optInt("size");
                  if (j != 0) {
                    param1Object = FileIoHandler.this.mOpenFiles.get(Integer.valueOf(i));
                    if (param1Object != null) {
                      param1Responder.respond(param1Object.read(j));
                    } else {
                      throw new Exception("invalid file handle, it might have timed out");
                    } 
                  } else {
                    throw new Exception("invalid or missing read size");
                  } 
                } else {
                  throw new Exception("invalid or missing file handle");
                } 
              } else {
                throw new Exception("params must be an object { file: handle, size: number }");
              } 
            } catch (Exception exception) {
              param1Responder.error(exception.toString());
            } finally {}
            /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/util/Map<[ObjectType{java/lang/Integer}, InnerObjectType{ObjectType{com/facebook/react/packagerconnection/FileIoHandler}.Lcom/facebook/react/packagerconnection/FileIoHandler$TtlFileInputStream;}]>}, name=null} */
          }
        });
  }
  
  public int addOpenFile(String paramString) throws FileNotFoundException {
    int i = this.mNextHandle;
    this.mNextHandle = i + 1;
    this.mOpenFiles.put(Integer.valueOf(i), new TtlFileInputStream(paramString));
    if (this.mOpenFiles.size() == 1)
      this.mHandler.postDelayed(this, 30000L); 
    return i;
  }
  
  public Map<String, RequestHandler> handlers() {
    return this.mRequestHandlers;
  }
  
  public void run() {
    synchronized (this.mOpenFiles) {
      Iterator<TtlFileInputStream> iterator = this.mOpenFiles.values().iterator();
      while (iterator.hasNext()) {
        TtlFileInputStream ttlFileInputStream = iterator.next();
        if (ttlFileInputStream.expiredTtl()) {
          iterator.remove();
          try {
            ttlFileInputStream.close();
          } catch (IOException iOException) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("closing expired file failed: ");
            stringBuilder.append(iOException.toString());
            a.c(str, stringBuilder.toString());
          } 
        } 
      } 
      if (!this.mOpenFiles.isEmpty())
        this.mHandler.postDelayed(this, 30000L); 
      return;
    } 
  }
  
  static class TtlFileInputStream {
    private final FileInputStream mStream;
    
    private long mTtl;
    
    public TtlFileInputStream(String param1String) throws FileNotFoundException {
      this.mStream = new FileInputStream(param1String);
      this.mTtl = System.currentTimeMillis() + 30000L;
    }
    
    private void extendTtl() {
      this.mTtl = System.currentTimeMillis() + 30000L;
    }
    
    public void close() throws IOException {
      this.mStream.close();
    }
    
    public boolean expiredTtl() {
      return (System.currentTimeMillis() >= this.mTtl);
    }
    
    public String read(int param1Int) throws IOException {
      extendTtl();
      byte[] arrayOfByte = new byte[param1Int];
      return Base64.encodeToString(arrayOfByte, 0, this.mStream.read(arrayOfByte), 0);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\packagerconnection\FileIoHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */