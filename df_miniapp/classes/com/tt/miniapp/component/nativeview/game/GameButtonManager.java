package com.tt.miniapp.component.nativeview.game;

import android.util.SparseArray;

public class GameButtonManager {
  private static GameButtonManager sNowButtonManager;
  
  private GameAbsoluteLayout mParentLayout;
  
  private int mViewIdCount = 0;
  
  private SparseArray<GameButton> mViewMap = new SparseArray();
  
  public GameButtonManager(GameAbsoluteLayout paramGameAbsoluteLayout) {
    this.mParentLayout = paramGameAbsoluteLayout;
  }
  
  public static GameButtonManager get() {
    return sNowButtonManager;
  }
  
  public static void set(GameButtonManager paramGameButtonManager) {
    sNowButtonManager = paramGameButtonManager;
  }
  
  public int addToParentView(GameButton paramGameButton) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_0
    //   4: getfield mViewIdCount : I
    //   7: iconst_1
    //   8: iadd
    //   9: putfield mViewIdCount : I
    //   12: aload_0
    //   13: getfield mViewMap : Landroid/util/SparseArray;
    //   16: aload_0
    //   17: getfield mViewIdCount : I
    //   20: aload_1
    //   21: invokevirtual put : (ILjava/lang/Object;)V
    //   24: aload_1
    //   25: aload_0
    //   26: getfield mParentLayout : Lcom/tt/miniapp/component/nativeview/game/GameAbsoluteLayout;
    //   29: invokevirtual addToParent : (Lcom/tt/miniapp/component/nativeview/game/GameAbsoluteLayout;)V
    //   32: aload_0
    //   33: getfield mViewIdCount : I
    //   36: istore_2
    //   37: aload_0
    //   38: monitorexit
    //   39: iload_2
    //   40: ireturn
    //   41: astore_1
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_1
    //   45: athrow
    // Exception table:
    //   from	to	target	type
    //   2	37	41	finally
  }
  
  public GameButton getButton(int paramInt) {
    return (GameButton)this.mViewMap.get(paramInt);
  }
  
  public boolean removeButton(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mViewMap : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast com/tt/miniapp/component/nativeview/game/GameButton
    //   13: astore_2
    //   14: aload_2
    //   15: ifnonnull -> 22
    //   18: aload_0
    //   19: monitorexit
    //   20: iconst_0
    //   21: ireturn
    //   22: aload_0
    //   23: getfield mViewMap : Landroid/util/SparseArray;
    //   26: iload_1
    //   27: invokevirtual remove : (I)V
    //   30: aload_2
    //   31: invokevirtual removeFromParent : ()V
    //   34: aload_0
    //   35: monitorexit
    //   36: iconst_1
    //   37: ireturn
    //   38: astore_2
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_2
    //   42: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	38	finally
    //   22	34	38	finally
  }
  
  public boolean setVisible(int paramInt, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mViewMap : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast com/tt/miniapp/component/nativeview/game/GameButton
    //   13: astore_3
    //   14: aload_3
    //   15: ifnonnull -> 22
    //   18: aload_0
    //   19: monitorexit
    //   20: iconst_0
    //   21: ireturn
    //   22: aload_3
    //   23: iload_2
    //   24: invokevirtual setVisibility : (Z)V
    //   27: aload_0
    //   28: monitorexit
    //   29: iconst_1
    //   30: ireturn
    //   31: astore_3
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_3
    //   35: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	31	finally
    //   22	27	31	finally
  }
  
  public boolean updateButton(int paramInt, GameButtonStyle paramGameButtonStyle, GameBtnUpdateAnim paramGameBtnUpdateAnim) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: ifnull -> 43
    //   6: aload_0
    //   7: getfield mViewMap : Landroid/util/SparseArray;
    //   10: iload_1
    //   11: invokevirtual get : (I)Ljava/lang/Object;
    //   14: checkcast com/tt/miniapp/component/nativeview/game/GameButton
    //   17: astore #4
    //   19: aload #4
    //   21: ifnonnull -> 27
    //   24: goto -> 43
    //   27: aload #4
    //   29: aload_2
    //   30: aload_3
    //   31: invokevirtual update : (Lcom/tt/miniapp/component/nativeview/game/GameButtonStyle;Lcom/tt/miniapp/component/nativeview/game/GameBtnUpdateAnim;)V
    //   34: aload_0
    //   35: monitorexit
    //   36: iconst_1
    //   37: ireturn
    //   38: astore_2
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_2
    //   42: athrow
    //   43: aload_0
    //   44: monitorexit
    //   45: iconst_0
    //   46: ireturn
    // Exception table:
    //   from	to	target	type
    //   6	19	38	finally
    //   27	34	38	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\GameButtonManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */