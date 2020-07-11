package com.tt.miniapp.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;
import java.lang.ref.WeakReference;

public class AlertController {
  public ListAdapter mAdapter;
  
  private int mAlertDialogLayout;
  
  private final View.OnClickListener mButtonHandler = new View.OnClickListener() {
      public void onClick(View param1View) {
        Message message;
        if (param1View == AlertController.this.mButtonPositive && AlertController.this.mButtonPositiveMessage != null) {
          message = Message.obtain(AlertController.this.mButtonPositiveMessage);
        } else if (message == AlertController.this.mButtonNegative && AlertController.this.mButtonNegativeMessage != null) {
          message = Message.obtain(AlertController.this.mButtonNegativeMessage);
        } else if (message == AlertController.this.mButtonNeutral && AlertController.this.mButtonNeutralMessage != null) {
          message = Message.obtain(AlertController.this.mButtonNeutralMessage);
        } else {
          message = null;
        } 
        if (message != null)
          message.sendToTarget(); 
        AlertController.this.mHandler.obtainMessage(1, AlertController.this.mDialogInterface).sendToTarget();
      }
    };
  
  public Button mButtonNegative;
  
  public Message mButtonNegativeMessage;
  
  private CharSequence mButtonNegativeText;
  
  public Button mButtonNeutral;
  
  public Message mButtonNeutralMessage;
  
  private CharSequence mButtonNeutralText;
  
  private int mButtonPanelLayoutHint;
  
  private int mButtonPanelSideLayout;
  
  public Button mButtonPositive;
  
  public Message mButtonPositiveMessage;
  
  private CharSequence mButtonPositiveText;
  
  public int mCheckedItem = -1;
  
  public final Context mContext;
  
  private View mCustomTitleView;
  
  public final DialogInterface mDialogInterface;
  
  private boolean mForceInverseBackground;
  
  public Handler mHandler;
  
  private Drawable mIcon;
  
  private int mIconId;
  
  private ImageView mIconView;
  
  public int mListItemLayout;
  
  public int mListLayout;
  
  public ListView mListView;
  
  private CharSequence mMessage;
  
  private TextView mMessageView;
  
  public int mMultiChoiceItemLayout;
  
  public ScrollView mScrollView;
  
  public int mSingleChoiceItemLayout;
  
  private CharSequence mTitle;
  
  private TextView mTitleView;
  
  private View mView;
  
  private int mViewLayoutResId;
  
  private int mViewSpacingBottom;
  
  private int mViewSpacingLeft;
  
  private int mViewSpacingRight;
  
  private boolean mViewSpacingSpecified;
  
  private int mViewSpacingTop;
  
  private final Window mWindow;
  
  public AlertController(Context paramContext, DialogInterface paramDialogInterface, Window paramWindow) {
    this.mContext = paramContext;
    this.mDialogInterface = paramDialogInterface;
    this.mWindow = paramWindow;
    this.mHandler = new ButtonHandler(paramDialogInterface);
    this.mAlertDialogLayout = 2097676297;
    this.mButtonPanelSideLayout = 0;
    this.mListLayout = 2097676345;
    this.mMultiChoiceItemLayout = 2097676347;
    this.mSingleChoiceItemLayout = 2097676348;
    this.mListItemLayout = 2097676346;
  }
  
  static boolean canTextInput(View paramView) {
    if (paramView.onCheckIsTextEditor())
      return true; 
    if (!(paramView instanceof ViewGroup))
      return false; 
    ViewGroup viewGroup = (ViewGroup)paramView;
    int i = viewGroup.getChildCount();
    while (i > 0) {
      int j = i - 1;
      i = j;
      if (canTextInput(viewGroup.getChildAt(j)))
        return true; 
    } 
    return false;
  }
  
  private void centerButton(Button paramButton) {
    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)paramButton.getLayoutParams();
    layoutParams.gravity = 1;
    layoutParams.weight = 0.5F;
    paramButton.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    View view = this.mWindow.findViewById(2097545322);
    if (view != null)
      view.setVisibility(0); 
    view = this.mWindow.findViewById(2097545380);
    if (view != null)
      view.setVisibility(0); 
  }
  
  public static void manageScrollIndicators(View paramView1, View paramView2, View paramView3) {
    if (Build.VERSION.SDK_INT < 14)
      return; 
    boolean bool = false;
    if (paramView2 != null) {
      byte b;
      if (paramView1.canScrollVertically(-1)) {
        b = 0;
      } else {
        b = 4;
      } 
      paramView2.setVisibility(b);
    } 
    if (paramView3 != null) {
      byte b;
      if (paramView1.canScrollVertically(1)) {
        b = bool;
      } else {
        b = 4;
      } 
      paramView3.setVisibility(b);
    } 
  }
  
  private int selectContentView() {
    int i = this.mButtonPanelSideLayout;
    return (i == 0) ? this.mAlertDialogLayout : ((this.mButtonPanelLayoutHint == 1) ? i : this.mAlertDialogLayout);
  }
  
  private void setBackground(TypedArray paramTypedArray, View paramView1, View paramView2, View paramView3, View paramView4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    ListView listView = this.mListView;
    if (listView != null) {
      ListAdapter listAdapter = this.mAdapter;
      if (listAdapter != null) {
        listView.setAdapter(listAdapter);
        int i = this.mCheckedItem;
        if (i >= 0) {
          listView.setItemChecked(i, true);
          listView.setSelection(i);
        } 
      } 
    } 
  }
  
  private boolean setupButtons() {
    int i;
    this.mButtonPositive = (Button)this.mWindow.findViewById(2097545251);
    this.mButtonPositive.setOnClickListener(this.mButtonHandler);
    if (TextUtils.isEmpty(this.mButtonPositiveText)) {
      this.mButtonPositive.setVisibility(8);
      i = 0;
    } else {
      this.mButtonPositive.setText(this.mButtonPositiveText);
      this.mButtonPositive.setVisibility(0);
      i = 1;
    } 
    this.mButtonNegative = (Button)this.mWindow.findViewById(2097545252);
    this.mButtonNegative.setOnClickListener(this.mButtonHandler);
    if (TextUtils.isEmpty(this.mButtonNegativeText)) {
      this.mButtonNegative.setVisibility(8);
    } else {
      this.mButtonNegative.setText(this.mButtonNegativeText);
      this.mButtonNegative.setVisibility(0);
      i |= 0x2;
    } 
    this.mButtonNeutral = (Button)this.mWindow.findViewById(2097545253);
    this.mButtonNeutral.setOnClickListener(this.mButtonHandler);
    if (TextUtils.isEmpty(this.mButtonNeutralText)) {
      this.mButtonNeutral.setVisibility(8);
    } else {
      this.mButtonNeutral.setText(this.mButtonNeutralText);
      this.mButtonNeutral.setVisibility(0);
      i |= 0x4;
    } 
    if (shouldCenterSingleButton(this.mContext))
      if (i == 1) {
        centerButton(this.mButtonPositive);
      } else if (i == 2) {
        centerButton(this.mButtonNegative);
      } else if (i == 4) {
        centerButton(this.mButtonNeutral);
      }  
    return (i != 0);
  }
  
  private void setupContent(ViewGroup paramViewGroup) {
    this.mScrollView = (ScrollView)this.mWindow.findViewById(2097545384);
    this.mScrollView.setFocusable(false);
    this.mMessageView = (TextView)this.mWindow.findViewById(2097545350);
    TextView textView = this.mMessageView;
    if (textView == null)
      return; 
    CharSequence charSequence = this.mMessage;
    if (charSequence != null) {
      textView.setText(charSequence);
    } else {
      textView.setVisibility(8);
      this.mScrollView.removeView((View)this.mMessageView);
      if (this.mListView != null) {
        ViewGroup viewGroup = (ViewGroup)this.mScrollView.getParent();
        int i = viewGroup.indexOfChild((View)this.mScrollView);
        viewGroup.removeViewAt(i);
        viewGroup.addView((View)this.mListView, i, new ViewGroup.LayoutParams(-1, -1));
      } else {
        paramViewGroup.setVisibility(8);
      } 
    } 
    final View indicatorUp = this.mWindow.findViewById(2097545383);
    final View indicatorDown = this.mWindow.findViewById(2097545382);
    if (view1 != null || view2 != null) {
      if (this.mMessage != null) {
        this.mScrollView.post(new Runnable() {
              public void run() {
                AlertController.manageScrollIndicators((View)AlertController.this.mScrollView, indicatorUp, indicatorDown);
              }
            });
        return;
      } 
      ListView listView = this.mListView;
      if (listView != null) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
              public void onScroll(AbsListView param1AbsListView, int param1Int1, int param1Int2, int param1Int3) {
                AlertController.manageScrollIndicators((View)param1AbsListView, indicatorUp, indicatorDown);
              }
              
              public void onScrollStateChanged(AbsListView param1AbsListView, int param1Int) {}
            });
        this.mListView.post(new Runnable() {
              public void run() {
                AlertController.manageScrollIndicators((View)AlertController.this.mListView, indicatorUp, indicatorDown);
              }
            });
        return;
      } 
      if (view1 != null)
        paramViewGroup.removeView(view1); 
      if (view2 != null)
        paramViewGroup.removeView(view2); 
    } 
  }
  
  private void setupDecor() {
    if (Build.VERSION.SDK_INT < 20)
      return; 
    View view1 = this.mWindow.getDecorView();
    final View parent = this.mWindow.findViewById(2097545359);
    if (view2 != null && view1 != null)
      view1.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            public WindowInsets onApplyWindowInsets(View param1View, WindowInsets param1WindowInsets) {
              if (param1WindowInsets.isRound()) {
                AppBrandLogger.d("tma_AlertController", new Object[] { "onApplyWindowInsets" });
                int i = AlertController.this.mContext.getResources().getDimensionPixelOffset(2097414146);
                parent.setPadding(i, i, i, i);
              } 
              return param1WindowInsets.consumeSystemWindowInsets();
            }
          }); 
  }
  
  private boolean setupTitle(ViewGroup paramViewGroup) {
    if (this.mCustomTitleView != null) {
      ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
      paramViewGroup.addView(this.mCustomTitleView, 0, layoutParams);
      this.mWindow.findViewById(2097545403).setVisibility(8);
    } else {
      Drawable drawable;
      this.mIconView = (ImageView)this.mWindow.findViewById(2097545309);
      if ((TextUtils.isEmpty(this.mTitle) ^ true) != 0) {
        this.mTitleView = (TextView)this.mWindow.findViewById(2097545245);
        this.mTitleView.setText(this.mTitle);
        int i = this.mIconId;
        if (i != 0) {
          this.mIconView.setImageResource(i);
        } else {
          drawable = this.mIcon;
          if (drawable != null) {
            this.mIconView.setImageDrawable(drawable);
          } else {
            this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
            this.mIconView.setVisibility(8);
          } 
        } 
        return true;
      } 
      this.mWindow.findViewById(2097545403).setVisibility(8);
      this.mIconView.setVisibility(8);
      drawable.setVisibility(8);
      return false;
    } 
    return true;
  }
  
  private void setupView() {
    boolean bool2;
    ViewGroup viewGroup1 = (ViewGroup)this.mWindow.findViewById(2097545260);
    setupContent(viewGroup1);
    boolean bool3 = setupButtons();
    ViewGroup viewGroup2 = (ViewGroup)this.mWindow.findViewById(2097545416);
    boolean bool4 = setupTitle(viewGroup2);
    View view2 = this.mWindow.findViewById(2097545254);
    if (!bool3) {
      view2.setVisibility(8);
      if (this.mMessage == null) {
        ListView listView = this.mListView;
        if (listView != null && listView.getParent() != null) {
          listView = this.mListView;
          listView.setPadding(listView.getPaddingLeft(), this.mListView.getPaddingTop(), this.mListView.getPaddingRight(), this.mListView.getPaddingTop());
        } 
      } 
    } 
    FrameLayout frameLayout = (FrameLayout)this.mWindow.findViewById(2097545264);
    View view1 = this.mView;
    boolean bool1 = false;
    if (view1 == null)
      if (this.mViewLayoutResId != 0) {
        view1 = LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, (ViewGroup)frameLayout, false);
      } else {
        view1 = null;
      }  
    if (view1 != null) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (!bool2 || !canTextInput(view1))
      this.mWindow.setFlags(131072, 131072); 
    if (bool2) {
      FrameLayout frameLayout1 = (FrameLayout)this.mWindow.findViewById(2097545263);
      frameLayout1.addView(view1, new ViewGroup.LayoutParams(-1, -1));
      if (this.mViewSpacingSpecified)
        frameLayout1.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom); 
      if (this.mListView != null)
        ((LinearLayout.LayoutParams)frameLayout.getLayoutParams()).weight = 0.0F; 
    } else {
      frameLayout.setVisibility(8);
    } 
    if (bool4) {
      if (this.mMessage != null || view1 != null || this.mListView != null) {
        view1 = this.mWindow.findViewById(2097545401);
      } else {
        view1 = this.mWindow.findViewById(2097545402);
      } 
      if (view1 != null)
        view1.setVisibility(0); 
    } 
    if (viewGroup1.getVisibility() == 0)
      bool1 = true; 
    if (bool3 && bool4 && !bool2 && !bool1)
      UIUtils.updateLayoutMargin((View)viewGroup2, -3, -3, -3, (int)UIUtils.dip2Px(this.mContext, 16.0F)); 
    setBackground(null, (View)viewGroup2, (View)viewGroup1, (View)frameLayout, view2, bool4, bool2, bool3);
  }
  
  private static boolean shouldCenterSingleButton(Context paramContext) {
    return false;
  }
  
  public Button getButton(int paramInt) {
    return (paramInt != -3) ? ((paramInt != -2) ? ((paramInt != -1) ? null : this.mButtonPositive) : this.mButtonNegative) : this.mButtonNeutral;
  }
  
  public int getIconAttributeResId(int paramInt) {
    TypedValue typedValue = new TypedValue();
    this.mContext.getTheme().resolveAttribute(paramInt, typedValue, true);
    return typedValue.resourceId;
  }
  
  public ListView getListView() {
    return this.mListView;
  }
  
  public void installContent() {
    this.mWindow.requestFeature(1);
    int i = selectContentView();
    this.mWindow.setContentView(i);
    setupView();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    ScrollView scrollView = this.mScrollView;
    return (scrollView != null && scrollView.executeKeyEvent(paramKeyEvent));
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    ScrollView scrollView = this.mScrollView;
    return (scrollView != null && scrollView.executeKeyEvent(paramKeyEvent));
  }
  
  public void setButton(int paramInt, CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener, Message paramMessage) {
    Message message = paramMessage;
    if (paramMessage == null) {
      message = paramMessage;
      if (paramOnClickListener != null)
        message = this.mHandler.obtainMessage(paramInt, paramOnClickListener); 
    } 
    if (paramInt != -3) {
      if (paramInt != -2) {
        if (paramInt == -1) {
          this.mButtonPositiveText = paramCharSequence;
          this.mButtonPositiveMessage = message;
          return;
        } 
        throw new IllegalArgumentException("Button does not exist");
      } 
      this.mButtonNegativeText = paramCharSequence;
      this.mButtonNegativeMessage = message;
      return;
    } 
    this.mButtonNeutralText = paramCharSequence;
    this.mButtonNeutralMessage = message;
  }
  
  public void setCustomTitle(View paramView) {
    this.mCustomTitleView = paramView;
  }
  
  public void setIcon(int paramInt) {
    this.mIcon = null;
    this.mIconId = paramInt;
    ImageView imageView = this.mIconView;
    if (imageView != null) {
      if (paramInt != 0) {
        imageView.setImageResource(this.mIconId);
        return;
      } 
      imageView.setVisibility(8);
    } 
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.mIcon = paramDrawable;
    this.mIconId = 0;
    ImageView imageView = this.mIconView;
    if (imageView != null) {
      if (paramDrawable != null) {
        imageView.setImageDrawable(paramDrawable);
        return;
      } 
      imageView.setVisibility(8);
    } 
  }
  
  public void setInverseBackgroundForced(boolean paramBoolean) {
    this.mForceInverseBackground = paramBoolean;
  }
  
  public void setMessage(CharSequence paramCharSequence) {
    this.mMessage = paramCharSequence;
    TextView textView = this.mMessageView;
    if (textView != null)
      textView.setText(paramCharSequence); 
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    this.mTitle = paramCharSequence;
    TextView textView = this.mTitleView;
    if (textView != null)
      textView.setText(paramCharSequence); 
  }
  
  public void setView(int paramInt) {
    this.mView = null;
    this.mViewLayoutResId = paramInt;
    this.mViewSpacingSpecified = false;
  }
  
  public void setView(View paramView) {
    this.mView = paramView;
    this.mViewLayoutResId = 0;
    this.mViewSpacingSpecified = false;
  }
  
  public void setView(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mView = paramView;
    this.mViewLayoutResId = 0;
    this.mViewSpacingSpecified = true;
    this.mViewSpacingLeft = paramInt1;
    this.mViewSpacingTop = paramInt2;
    this.mViewSpacingRight = paramInt3;
    this.mViewSpacingBottom = paramInt4;
  }
  
  public static class AlertParams {
    public ListAdapter mAdapter;
    
    public boolean mCancelable;
    
    public int mCheckedItem = -1;
    
    public boolean[] mCheckedItems;
    
    public final Context mContext;
    
    public Cursor mCursor;
    
    public View mCustomTitleView;
    
    public boolean mForceInverseBackground;
    
    public Drawable mIcon;
    
    public int mIconAttrId;
    
    public int mIconId;
    
    public final LayoutInflater mInflater;
    
    public String mIsCheckedColumn;
    
    public boolean mIsMultiChoice;
    
    public boolean mIsSingleChoice;
    
    public CharSequence[] mItems;
    
    public String mLabelColumn;
    
    public CharSequence mMessage;
    
    public DialogInterface.OnClickListener mNegativeButtonListener;
    
    public CharSequence mNegativeButtonText;
    
    public DialogInterface.OnClickListener mNeutralButtonListener;
    
    public CharSequence mNeutralButtonText;
    
    public DialogInterface.OnCancelListener mOnCancelListener;
    
    public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
    
    public DialogInterface.OnClickListener mOnClickListener;
    
    public DialogInterface.OnDismissListener mOnDismissListener;
    
    public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    
    public DialogInterface.OnKeyListener mOnKeyListener;
    
    public OnPrepareListViewListener mOnPrepareListViewListener;
    
    public DialogInterface.OnClickListener mPositiveButtonListener;
    
    public CharSequence mPositiveButtonText;
    
    public boolean mRecycleOnMeasure = true;
    
    public CharSequence mTitle;
    
    public View mView;
    
    public int mViewLayoutResId;
    
    public int mViewSpacingBottom;
    
    public int mViewSpacingLeft;
    
    public int mViewSpacingRight;
    
    public boolean mViewSpacingSpecified;
    
    public int mViewSpacingTop;
    
    public AlertParams(Context param1Context) {
      this.mContext = param1Context;
      this.mCancelable = true;
      this.mInflater = (LayoutInflater)param1Context.getSystemService("layout_inflater");
    }
    
    private void createListView(final AlertController dialog) {
      SimpleCursorAdapter simpleCursorAdapter;
      final ListView listView = (ListView)this.mInflater.inflate(dialog.mListLayout, null);
      if (this.mIsMultiChoice) {
        ArrayAdapter<CharSequence> arrayAdapter;
        Cursor cursor = this.mCursor;
        if (cursor == null) {
          arrayAdapter = new ArrayAdapter<CharSequence>(this.mContext, dialog.mMultiChoiceItemLayout, 2097545397, this.mItems) {
              public View getView(int param2Int, View param2View, ViewGroup param2ViewGroup) {
                param2View = super.getView(param2Int, param2View, param2ViewGroup);
                if (AlertController.AlertParams.this.mCheckedItems != null && AlertController.AlertParams.this.mCheckedItems[param2Int])
                  listView.setItemChecked(param2Int, true); 
                return param2View;
              }
            };
        } else {
          CursorAdapter cursorAdapter = new CursorAdapter(this.mContext, (Cursor)arrayAdapter, false) {
              private final int mIsCheckedIndex;
              
              private final int mLabelIndex;
              
              public void bindView(View param2View, Context param2Context, Cursor param2Cursor) {
                ((CheckedTextView)param2View.findViewById(2097545397)).setText(param2Cursor.getString(this.mLabelIndex));
                ListView listView = listView;
                int i = param2Cursor.getPosition();
                int j = param2Cursor.getInt(this.mIsCheckedIndex);
                boolean bool = true;
                if (j != 1)
                  bool = false; 
                listView.setItemChecked(i, bool);
              }
              
              public View newView(Context param2Context, Cursor param2Cursor, ViewGroup param2ViewGroup) {
                return AlertController.AlertParams.this.mInflater.inflate(dialog.mMultiChoiceItemLayout, param2ViewGroup, false);
              }
            };
        } 
      } else {
        int i;
        AlertController.CheckedItemAdapter checkedItemAdapter;
        if (this.mIsSingleChoice) {
          i = dialog.mSingleChoiceItemLayout;
        } else {
          i = dialog.mListItemLayout;
        } 
        Cursor cursor = this.mCursor;
        if (cursor == null) {
          ListAdapter listAdapter = this.mAdapter;
          if (listAdapter == null)
            checkedItemAdapter = new AlertController.CheckedItemAdapter(this.mContext, i, 2097545397, this.mItems); 
        } else {
          simpleCursorAdapter = new SimpleCursorAdapter(this.mContext, i, (Cursor)checkedItemAdapter, new String[] { this.mLabelColumn }, new int[] { 2097545397 });
        } 
      } 
      OnPrepareListViewListener onPrepareListViewListener = this.mOnPrepareListViewListener;
      if (onPrepareListViewListener != null)
        onPrepareListViewListener.onPrepareListView(listView); 
      dialog.mAdapter = (ListAdapter)simpleCursorAdapter;
      dialog.mCheckedItem = this.mCheckedItem;
      if (this.mOnClickListener != null) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              public void onItemClick(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) {
                AlertController.AlertParams.this.mOnClickListener.onClick(dialog.mDialogInterface, param2Int);
                if (!AlertController.AlertParams.this.mIsSingleChoice)
                  dialog.mDialogInterface.dismiss(); 
              }
            });
      } else if (this.mOnCheckboxClickListener != null) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              public void onItemClick(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) {
                if (AlertController.AlertParams.this.mCheckedItems != null)
                  AlertController.AlertParams.this.mCheckedItems[param2Int] = listView.isItemChecked(param2Int); 
                AlertController.AlertParams.this.mOnCheckboxClickListener.onClick(dialog.mDialogInterface, param2Int, listView.isItemChecked(param2Int));
              }
            });
      } 
      AdapterView.OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
      if (onItemSelectedListener != null)
        listView.setOnItemSelectedListener(onItemSelectedListener); 
      if (this.mIsSingleChoice) {
        listView.setChoiceMode(1);
      } else if (this.mIsMultiChoice) {
        listView.setChoiceMode(2);
      } 
      dialog.mListView = listView;
    }
    
    public void apply(AlertController param1AlertController) {
      View view2 = this.mCustomTitleView;
      if (view2 != null) {
        param1AlertController.setCustomTitle(view2);
      } else {
        CharSequence charSequence1 = this.mTitle;
        if (charSequence1 != null)
          param1AlertController.setTitle(charSequence1); 
        Drawable drawable = this.mIcon;
        if (drawable != null)
          param1AlertController.setIcon(drawable); 
        int j = this.mIconId;
        if (j != 0)
          param1AlertController.setIcon(j); 
        j = this.mIconAttrId;
        if (j != 0)
          param1AlertController.setIcon(param1AlertController.getIconAttributeResId(j)); 
      } 
      CharSequence charSequence = this.mMessage;
      if (charSequence != null)
        param1AlertController.setMessage(charSequence); 
      charSequence = this.mPositiveButtonText;
      if (charSequence != null)
        param1AlertController.setButton(-1, charSequence, this.mPositiveButtonListener, null); 
      charSequence = this.mNegativeButtonText;
      if (charSequence != null)
        param1AlertController.setButton(-2, charSequence, this.mNegativeButtonListener, null); 
      charSequence = this.mNeutralButtonText;
      if (charSequence != null)
        param1AlertController.setButton(-3, charSequence, this.mNeutralButtonListener, null); 
      if (this.mForceInverseBackground)
        param1AlertController.setInverseBackgroundForced(true); 
      if (this.mItems != null || this.mCursor != null || this.mAdapter != null)
        createListView(param1AlertController); 
      View view1 = this.mView;
      if (view1 != null) {
        if (this.mViewSpacingSpecified) {
          param1AlertController.setView(view1, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
          return;
        } 
        param1AlertController.setView(view1);
        return;
      } 
      int i = this.mViewLayoutResId;
      if (i != 0)
        param1AlertController.setView(i); 
    }
    
    public static interface OnPrepareListViewListener {
      void onPrepareListView(ListView param2ListView);
    }
  }
  
  class null extends ArrayAdapter<CharSequence> {
    null(Context param1Context, int param1Int1, int param1Int2, CharSequence[] param1ArrayOfCharSequence) {
      super(param1Context, param1Int1, param1Int2, (Object[])param1ArrayOfCharSequence);
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      param1View = super.getView(param1Int, param1View, param1ViewGroup);
      if (this.this$0.mCheckedItems != null && this.this$0.mCheckedItems[param1Int])
        listView.setItemChecked(param1Int, true); 
      return param1View;
    }
  }
  
  class null extends CursorAdapter {
    private final int mIsCheckedIndex;
    
    private final int mLabelIndex;
    
    null(Context param1Context, Cursor param1Cursor, boolean param1Boolean) {
      super(param1Context, param1Cursor, param1Boolean);
      Cursor cursor = getCursor();
      this.mLabelIndex = cursor.getColumnIndexOrThrow(this.this$0.mLabelColumn);
      this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(this.this$0.mIsCheckedColumn);
    }
    
    public void bindView(View param1View, Context param1Context, Cursor param1Cursor) {
      ((CheckedTextView)param1View.findViewById(2097545397)).setText(param1Cursor.getString(this.mLabelIndex));
      ListView listView = listView;
      int i = param1Cursor.getPosition();
      int j = param1Cursor.getInt(this.mIsCheckedIndex);
      boolean bool = true;
      if (j != 1)
        bool = false; 
      listView.setItemChecked(i, bool);
    }
    
    public View newView(Context param1Context, Cursor param1Cursor, ViewGroup param1ViewGroup) {
      return this.this$0.mInflater.inflate(dialog.mMultiChoiceItemLayout, param1ViewGroup, false);
    }
  }
  
  class null implements AdapterView.OnItemClickListener {
    public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
      this.this$0.mOnClickListener.onClick(dialog.mDialogInterface, param1Int);
      if (!this.this$0.mIsSingleChoice)
        dialog.mDialogInterface.dismiss(); 
    }
  }
  
  class null implements AdapterView.OnItemClickListener {
    public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
      if (this.this$0.mCheckedItems != null)
        this.this$0.mCheckedItems[param1Int] = listView.isItemChecked(param1Int); 
      this.this$0.mOnCheckboxClickListener.onClick(dialog.mDialogInterface, param1Int, listView.isItemChecked(param1Int));
    }
  }
  
  public static interface OnPrepareListViewListener {
    void onPrepareListView(ListView param1ListView);
  }
  
  static final class ButtonHandler extends Handler {
    private WeakReference<DialogInterface> mDialog;
    
    public ButtonHandler(DialogInterface param1DialogInterface) {
      this.mDialog = new WeakReference<DialogInterface>(param1DialogInterface);
    }
    
    public final void handleMessage(Message param1Message) {
      int i = param1Message.what;
      if (i != -3 && i != -2 && i != -1) {
        if (i != 1)
          return; 
        ((DialogInterface)param1Message.obj).dismiss();
        return;
      } 
      ((DialogInterface.OnClickListener)param1Message.obj).onClick(this.mDialog.get(), param1Message.what);
    }
  }
  
  static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
    public CheckedItemAdapter(Context param1Context, int param1Int1, int param1Int2, CharSequence[] param1ArrayOfCharSequence) {
      super(param1Context, param1Int1, param1Int2, (Object[])param1ArrayOfCharSequence);
    }
    
    public long getItemId(int param1Int) {
      return param1Int;
    }
    
    public boolean hasStableIds() {
      return true;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\dialog\AlertController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */