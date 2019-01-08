package com.example.pavel.bookkeeping.root;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.pavel.bookkeeping.R;

public class RootView extends FrameLayout implements RootInteractor.RootPresenter {

  public RootView(Context context) {
    this(context, null);
  }

  public RootView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RootView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onCreateContextMenu(ContextMenu menu) {
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
  }

  @Override
  public void saveHierarchyState(SparseArray<Parcelable> container) {
    super.saveHierarchyState(container);
  }

  @Override
  public void restoreHierarchyState(SparseArray<Parcelable> container) {
    super.restoreHierarchyState(container);
  }
}
