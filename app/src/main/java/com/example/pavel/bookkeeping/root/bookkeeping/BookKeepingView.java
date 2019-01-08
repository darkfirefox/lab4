package com.example.pavel.bookkeeping.root.bookkeeping;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import com.example.pavel.bookkeeping.Adapters.BookKeepingAdapter;
import com.example.pavel.bookkeeping.Model.BaseModel;
import com.example.pavel.bookkeeping.R;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.uber.rib.core.Initializer;

import java.util.List;

import io.reactivex.Observable;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

class BookKeepingView extends ConstraintLayout implements BookKeepingInteractor.BookKeepingPresenter {
  private RecyclerView mRecycleView;
  private BookKeepingAdapter mAdapter;
  private FloatingActionButton fab;
  private SearchView searchView;
  private BehaviorRelay<Integer> tabSelected = BehaviorRelay.create();
  private BehaviorRelay<Integer> creating = BehaviorRelay.create();
  private BehaviorRelay<String> search = BehaviorRelay.create();
  private BehaviorRelay<Boolean> searhActivated = BehaviorRelay.create();

  public BookKeepingView(Context context) {
    this(context, null);
  }

  public BookKeepingView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BookKeepingView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Initializer
  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    tabSelected.accept(0);
    searhActivated.accept(false);
    search.accept("");

    mAdapter = new BookKeepingAdapter(getContext());
    TabLayout tabs = findViewById(R.id.tabs);
    tabs.removeAllTabs();
    tabs.addTab(tabs.newTab().setText(R.string.accountTab));
    tabs.addTab(tabs.newTab().setText(R.string.arrivalTab));
    tabs.addTab(tabs.newTab().setText(R.string.transferTab));
    tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        tabSelected.accept(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
      }
    });
    mRecycleView = findViewById(R.id.recycleView);
    mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecycleView.setAdapter(mAdapter);
    DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), VERTICAL);
    mRecycleView.addItemDecoration(itemDecor);
    fab = findViewById(R.id.floatingActionButton);
    fab.setOnClickListener(v -> showPopup(v));
    searchView = findViewById(R.id.searchView);
    searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> searhActivated.accept(hasFocus));
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            search.accept(s);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            search.accept(s);
            return false;
        }
    });
  }

  public void showPopup(View v) {
    PopupMenu popup = new PopupMenu(getContext(), v);
    MenuInflater inflater = popup.getMenuInflater();
    inflater.inflate(R.menu.book_keeping_menu, popup.getMenu());
    popup.setOnMenuItemClickListener(menuItem -> {
      creating.accept(menuItem.getItemId());
      return false;
    });
    popup.show();
  }
  @Override
  public Observable<Integer> selectedView() {
    return tabSelected.hide();
  }

  @Override
  public Observable<BaseModel> deleteRow() {
    return mAdapter.deletingRow();
  }

    @Override
    public Observable<Integer> creating() {
        return creating.hide();
    }

    @Override
    public Observable<String> search() {
        return search.hide();
    }

  @Override
  public Observable<Boolean> searchActivated() {
    return searhActivated.hide();
  }

  @Override
  public void showViewModel(List<? extends BaseModel> list) {
    mAdapter.setList(list);
  }
}