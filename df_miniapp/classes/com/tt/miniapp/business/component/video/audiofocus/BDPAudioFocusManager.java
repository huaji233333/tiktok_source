package com.tt.miniapp.business.component.video.audiofocus;

import android.media.AudioManager;
import com.bytedance.sandboxapp.protocol.service.j.a.a;
import com.tt.miniapp.base.MiniAppContext;
import d.f;
import d.f.a.a;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k;
import d.k.d;
import d.k.h;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public final class BDPAudioFocusManager {
  public static final Companion Companion = new Companion(null);
  
  public static final f instance$delegate = g.a(k.SYNCHRONIZED, BDPAudioFocusManager$Companion$instance$2.INSTANCE);
  
  private AudioManager.OnAudioFocusChangeListener mAudioFocusListener;
  
  public final ConcurrentLinkedDeque<a> mAudioFocusRequests = new ConcurrentLinkedDeque<a>();
  
  public a.b mAudioFocusState = a.b.FOCUS_NONE;
  
  private AudioManager mAudioManager;
  
  private BDPAudioFocusManager() {}
  
  private final a.b gainToLoss(a.b paramb) {
    int i = BDPAudioFocusManager$WhenMappings.$EnumSwitchMapping$1[paramb.ordinal()];
    return (i != 1) ? ((i != 2 && i != 3) ? ((i != 4) ? a.b.FOCUS_NONE : a.b.FOCUS_LOSS_TRANSIENT_CAN_DUCK) : a.b.LOSS_TRANSIENT) : a.b.LOSS;
  }
  
  private final void initAudioManager(MiniAppContext paramMiniAppContext) {
    if (this.mAudioManager != null)
      return; 
    Object object = paramMiniAppContext.getApplicationContext().getSystemService("audio");
    if (object instanceof AudioManager)
      this.mAudioManager = (AudioManager)object; 
  }
  
  private final boolean isGainFocus(a.b paramb) {
    int i = BDPAudioFocusManager$WhenMappings.$EnumSwitchMapping$0[paramb.ordinal()];
    return !(i != 1 && i != 2 && i != 3 && i != 4);
  }
  
  public final void abandonAudioFocus(MiniAppContext paramMiniAppContext, a parama) {
    l.b(paramMiniAppContext, "context");
    l.b(parama, "request");
    this.mAudioFocusRequests.remove(parama);
    if (parama.c == a.e.SELF_ONLY) {
      Iterator<a> iterator = this.mAudioFocusRequests.iterator();
      while (iterator.hasNext())
        ((a)iterator.next()).d.onAudioFocusChanged(a.b.GAIN); 
    } 
    if (this.mAudioFocusRequests.size() == 0) {
      initAudioManager(paramMiniAppContext);
      AudioManager audioManager = this.mAudioManager;
      if (audioManager != null)
        audioManager.abandonAudioFocus(this.mAudioFocusListener); 
      synchronized (this.mAudioFocusRequests) {
        this.mAudioFocusState = a.b.FOCUS_NONE;
        return;
      } 
    } 
  }
  
  public final void release() {
    this.mAudioFocusRequests.clear();
    this.mAudioManager = null;
    this.mAudioFocusListener = null;
  }
  
  public final a.d requestAudioFocus(MiniAppContext paramMiniAppContext, a parama) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'context'
    //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 'request'
    //   9: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_2
    //   13: getfield c : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$e;
    //   16: getstatic com/bytedance/sandboxapp/protocol/service/j/a/a$e.SELF_ONLY : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$e;
    //   19: if_acmpne -> 70
    //   22: aload_0
    //   23: getfield mAudioFocusRequests : Ljava/util/concurrent/ConcurrentLinkedDeque;
    //   26: invokevirtual iterator : ()Ljava/util/Iterator;
    //   29: astore #6
    //   31: aload #6
    //   33: invokeinterface hasNext : ()Z
    //   38: ifeq -> 70
    //   41: aload #6
    //   43: invokeinterface next : ()Ljava/lang/Object;
    //   48: checkcast com/bytedance/sandboxapp/protocol/service/j/a/a
    //   51: getfield d : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$c;
    //   54: aload_0
    //   55: aload_2
    //   56: getfield a : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$b;
    //   59: invokespecial gainToLoss : (Lcom/bytedance/sandboxapp/protocol/service/j/a/a$b;)Lcom/bytedance/sandboxapp/protocol/service/j/a/a$b;
    //   62: invokeinterface onAudioFocusChanged : (Lcom/bytedance/sandboxapp/protocol/service/j/a/a$b;)V
    //   67: goto -> 31
    //   70: aload_0
    //   71: getfield mAudioFocusRequests : Ljava/util/concurrent/ConcurrentLinkedDeque;
    //   74: aload_2
    //   75: invokevirtual contains : (Ljava/lang/Object;)Z
    //   78: ifne -> 90
    //   81: aload_0
    //   82: getfield mAudioFocusRequests : Ljava/util/concurrent/ConcurrentLinkedDeque;
    //   85: aload_2
    //   86: invokevirtual offer : (Ljava/lang/Object;)Z
    //   89: pop
    //   90: aload_0
    //   91: aload_0
    //   92: getfield mAudioFocusState : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$b;
    //   95: invokespecial isGainFocus : (Lcom/bytedance/sandboxapp/protocol/service/j/a/a$b;)Z
    //   98: ifeq -> 105
    //   101: getstatic com/bytedance/sandboxapp/protocol/service/j/a/a$d.FOCUS_REQUEST_GRANTED : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$d;
    //   104: areturn
    //   105: aload_0
    //   106: aload_1
    //   107: invokespecial initAudioManager : (Lcom/tt/miniapp/base/MiniAppContext;)V
    //   110: aload_0
    //   111: getfield mAudioManager : Landroid/media/AudioManager;
    //   114: ifnonnull -> 121
    //   117: getstatic com/bytedance/sandboxapp/protocol/service/j/a/a$d.FOCUS_REQUEST_FAILED : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$d;
    //   120: areturn
    //   121: aload_0
    //   122: getfield mAudioFocusListener : Landroid/media/AudioManager$OnAudioFocusChangeListener;
    //   125: ifnonnull -> 143
    //   128: aload_0
    //   129: new com/tt/miniapp/business/component/video/audiofocus/BDPAudioFocusManager$requestAudioFocus$1
    //   132: dup
    //   133: aload_0
    //   134: invokespecial <init> : (Lcom/tt/miniapp/business/component/video/audiofocus/BDPAudioFocusManager;)V
    //   137: checkcast android/media/AudioManager$OnAudioFocusChangeListener
    //   140: putfield mAudioFocusListener : Landroid/media/AudioManager$OnAudioFocusChangeListener;
    //   143: aload_0
    //   144: getfield mAudioManager : Landroid/media/AudioManager;
    //   147: astore_1
    //   148: aload_1
    //   149: ifnonnull -> 155
    //   152: invokestatic a : ()V
    //   155: aload_0
    //   156: getfield mAudioFocusListener : Landroid/media/AudioManager$OnAudioFocusChangeListener;
    //   159: astore #6
    //   161: aload_2
    //   162: getfield b : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$f;
    //   165: astore #7
    //   167: aload #7
    //   169: ldc 'usage'
    //   171: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   174: getstatic com/bytedance/sandboxapp/protocol/service/j/a/a$f.USAGE_MEDIA : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$f;
    //   177: astore #8
    //   179: iconst_3
    //   180: istore_3
    //   181: aload #7
    //   183: aload #8
    //   185: if_acmpne -> 194
    //   188: iconst_3
    //   189: istore #4
    //   191: goto -> 197
    //   194: iconst_4
    //   195: istore #4
    //   197: aload_2
    //   198: getfield a : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$b;
    //   201: astore_2
    //   202: aload_2
    //   203: ldc 'focusState'
    //   205: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   208: getstatic com/bytedance/sandboxapp/protocol/service/j/a/b.a : [I
    //   211: aload_2
    //   212: invokevirtual ordinal : ()I
    //   215: iaload
    //   216: istore #5
    //   218: iload #5
    //   220: iconst_1
    //   221: if_icmpeq -> 252
    //   224: iload #5
    //   226: iconst_2
    //   227: if_icmpeq -> 242
    //   230: iload #5
    //   232: iconst_3
    //   233: if_icmpeq -> 254
    //   236: iload #5
    //   238: iconst_4
    //   239: if_icmpeq -> 247
    //   242: iconst_2
    //   243: istore_3
    //   244: goto -> 254
    //   247: iconst_4
    //   248: istore_3
    //   249: goto -> 254
    //   252: iconst_1
    //   253: istore_3
    //   254: aload_1
    //   255: aload #6
    //   257: iload #4
    //   259: iload_3
    //   260: invokevirtual requestAudioFocus : (Landroid/media/AudioManager$OnAudioFocusChangeListener;II)I
    //   263: istore_3
    //   264: iload_3
    //   265: ifeq -> 290
    //   268: iload_3
    //   269: iconst_1
    //   270: if_icmpeq -> 286
    //   273: iload_3
    //   274: iconst_2
    //   275: if_icmpeq -> 282
    //   278: getstatic com/bytedance/sandboxapp/protocol/service/j/a/a$d.FOCUS_REQUEST_FAILED : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$d;
    //   281: areturn
    //   282: getstatic com/bytedance/sandboxapp/protocol/service/j/a/a$d.FOCUS_REQUEST_DELAYED : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$d;
    //   285: areturn
    //   286: getstatic com/bytedance/sandboxapp/protocol/service/j/a/a$d.FOCUS_REQUEST_GRANTED : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$d;
    //   289: areturn
    //   290: getstatic com/bytedance/sandboxapp/protocol/service/j/a/a$d.FOCUS_REQUEST_FAILED : Lcom/bytedance/sandboxapp/protocol/service/j/a/a$d;
    //   293: areturn
  }
  
  public static final class Companion {
    private Companion() {}
    
    public final BDPAudioFocusManager getInstance() {
      return (BDPAudioFocusManager)BDPAudioFocusManager.instance$delegate.getValue();
    }
  }
  
  static final class BDPAudioFocusManager$Companion$instance$2 extends m implements a<BDPAudioFocusManager> {
    public static final BDPAudioFocusManager$Companion$instance$2 INSTANCE = new BDPAudioFocusManager$Companion$instance$2();
    
    BDPAudioFocusManager$Companion$instance$2() {
      super(0);
    }
    
    public final BDPAudioFocusManager invoke() {
      return new BDPAudioFocusManager(null);
    }
  }
  
  static final class BDPAudioFocusManager$requestAudioFocus$1 implements AudioManager.OnAudioFocusChangeListener {
    public final void onAudioFocusChange(int param1Int) {
      synchronized (BDPAudioFocusManager.this.mAudioFocusRequests) {
        a.b b;
        BDPAudioFocusManager bDPAudioFocusManager = BDPAudioFocusManager.this;
        if (param1Int != -3) {
          if (param1Int != -2) {
            if (param1Int != -1) {
              if (param1Int != 1) {
                if (param1Int != 2) {
                  if (param1Int != 3) {
                    if (param1Int != 4) {
                      b = a.b.FOCUS_NONE;
                    } else {
                      b = a.b.GAIN_TRANSIENT_EXCLUSIVE;
                    } 
                  } else {
                    b = a.b.GAIN_TRANSIENT_MAY_DUCK;
                  } 
                } else {
                  b = a.b.GAIN_TRANSIENT;
                } 
              } else {
                b = a.b.GAIN;
              } 
            } else {
              b = a.b.LOSS;
            } 
          } else {
            b = a.b.LOSS_TRANSIENT;
          } 
        } else {
          b = a.b.FOCUS_LOSS_TRANSIENT_CAN_DUCK;
        } 
        bDPAudioFocusManager.mAudioFocusState = b;
        Iterator<a> iterator = BDPAudioFocusManager.this.mAudioFocusRequests.iterator();
        while (iterator.hasNext())
          ((a)iterator.next()).d.onAudioFocusChanged(BDPAudioFocusManager.this.mAudioFocusState); 
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\component\video\audiofocus\BDPAudioFocusManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */