package com.example.pavel.bookkeeping.root;

import android.support.annotation.Nullable;

import com.example.pavel.bookkeeping.root.bookkeeping.BookKeepingBuilder;
import com.example.pavel.bookkeeping.root.bookkeeping.BookKeepingRouter;
import com.uber.rib.core.ViewRouter;

public class RootRouter extends
    ViewRouter<RootView, RootInteractor, RootBuilder.Component> {
  private final BookKeepingBuilder bookKeepingBuilder;
  @Nullable
  private BookKeepingRouter bookKeepingRouter;

  public RootRouter(
          RootView view,
          RootInteractor interactor,
          RootBuilder.Component component,
          BookKeepingBuilder bookKeepingBuilder) {
    super(view, interactor, component);
    this.bookKeepingBuilder = bookKeepingBuilder;
  }

  void attachBookKeeping() {
    bookKeepingRouter = bookKeepingBuilder.build(getView());
    attachChild(bookKeepingRouter);
    getView().addView(bookKeepingRouter.getView());
  }
}
