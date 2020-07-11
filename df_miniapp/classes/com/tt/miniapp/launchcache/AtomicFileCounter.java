package com.tt.miniapp.launchcache;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.IOUtils;
import d.f.b.g;
import d.f.b.l;
import d.m.j;
import d.m.l;
import d.m.p;
import java.io.File;
import java.io.FilenameFilter;

public final class AtomicFileCounter {
  public static final Companion Companion = new Companion(null);
  
  private final File dir;
  
  public final l fileNameRegex;
  
  private final String suffix;
  
  public AtomicFileCounter(File paramFile, String paramString) {
    this.dir = paramFile;
    this.suffix = paramString;
    StringBuilder stringBuilder = new StringBuilder("(\\d+)\\.");
    stringBuilder.append(this.suffix);
    this.fileNameRegex = new l(stringBuilder.toString());
  }
  
  private final File getFile(boolean paramBoolean) {
    if (!this.dir.exists())
      this.dir.mkdirs(); 
    File[] arrayOfFile = this.dir.listFiles(new AtomicFileCounter$getFile$files$1());
    if (arrayOfFile == null)
      return null; 
    int j = arrayOfFile.length;
    int i = 0;
    if (j == 1)
      return arrayOfFile[0]; 
    j = arrayOfFile.length;
    while (i < j) {
      IOUtils.delete(arrayOfFile[i]);
      i++;
    } 
    File file = this.dir;
    StringBuilder stringBuilder = new StringBuilder("0.");
    stringBuilder.append(this.suffix);
    file = new File(file, stringBuilder.toString());
    return (paramBoolean && file.createNewFile()) ? file : null;
  }
  
  public final long addAndGet(long paramLong) {
    for (int i = 0; i < 5; i++) {
      try {
        File file = getFile(true);
        if (file != null) {
          String str = file.getName();
          l l1 = this.fileNameRegex;
          l.a(str, "counterName");
          j j = l1.matchEntire(str);
          if (j != null) {
            long l2 = Long.parseLong(j.d().get(1));
            long l3 = l2 + paramLong;
            str = p.b(str, String.valueOf(l2), String.valueOf(l3), false, 4, null);
            boolean bool = file.renameTo(new File(this.dir, str));
            if (bool)
              return l3; 
          } 
        } 
      } catch (Exception exception) {
        AppBrandLogger.e("AtomicFileCounter", new Object[] { exception });
      } 
    } 
    return -1L;
  }
  
  public final long get() {
    try {
      File file = getFile(false);
      if (file == null)
        return -1L; 
      String str = file.getName();
      l l1 = this.fileNameRegex;
      l.a(str, "counterName");
      j j = l1.matchEntire(str);
      if (j != null)
        return Long.parseLong(j.d().get(1)); 
    } catch (Exception exception) {
      AppBrandLogger.e("AtomicFileCounter", new Object[] { exception });
    } 
    return -1L;
  }
  
  public final void set(long paramLong) {
    for (int i = 0; i < 5; i++) {
      try {
        File file = getFile(true);
        if (file != null) {
          String str = file.getName();
          l l1 = this.fileNameRegex;
          l.a(str, "counterName");
          j j = l1.matchEntire(str);
          if (j != null) {
            str = p.b(str, String.valueOf(Long.parseLong(j.d().get(1))), String.valueOf(paramLong), false, 4, null);
            boolean bool = file.renameTo(new File(this.dir, str));
            if (bool)
              return; 
          } 
        } 
      } catch (Exception exception) {
        AppBrandLogger.e("AtomicFileCounter", new Object[] { exception });
      } 
    } 
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class AtomicFileCounter$getFile$files$1 implements FilenameFilter {
    public final boolean accept(File param1File, String param1String) {
      l l = AtomicFileCounter.this.fileNameRegex;
      l.a(param1String, "name");
      return l.matches(param1String);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\AtomicFileCounter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */