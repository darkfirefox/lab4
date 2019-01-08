package com.example.pavel.bookkeeping.root.bookkeeping;

import android.app.ActionBar;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.pavel.bookkeeping.root.bookkeeping.newaccount.NewAccountBuilder;
import com.example.pavel.bookkeeping.root.bookkeeping.newaccount.NewAccountRouter;
import com.example.pavel.bookkeeping.root.bookkeeping.newoperation.NewOperationBuilder;
import com.example.pavel.bookkeeping.root.bookkeeping.newoperation.NewOperationRouter;
import com.example.pavel.bookkeeping.view.ViewExtension;
import com.uber.rib.core.ViewRouter;

public class BookKeepingRouter extends
    ViewRouter<BookKeepingView, BookKeepingInteractor, BookKeepingBuilder.Component> {

  private final NewOperationBuilder newOperationBuilder;
  private final NewAccountBuilder newAccountBuilder;

  @Nullable
  private NewOperationRouter newOperationRouter;
  private NewAccountRouter newAccountRouter;

  public BookKeepingRouter(
          BookKeepingView view,
          BookKeepingInteractor interactor,
          BookKeepingBuilder.Component component,
          NewOperationBuilder newOperationBuilder,
          NewAccountBuilder newAccountBuilder) {
    super(view, interactor, component);
    this.newAccountBuilder = newAccountBuilder;
    this.newOperationBuilder = newOperationBuilder;
  }

  void attachNewOperation() {
    newOperationRouter = newOperationBuilder.build(getView());
    getView().removeView(this.getView());
    attachChild(newOperationRouter);
    ViewExtension.replaceView(getView(), newOperationRouter.getView());
  }
  void attachNewAccount() {
    newAccountRouter = newAccountBuilder.build(getView());
    attachChild(newAccountRouter);
    ViewExtension.replaceView(getView(), newAccountRouter.getView());
  }
  void detachNewOperation() {
    if (newOperationRouter != null) {
      detachChild(newOperationRouter);
      ViewExtension.dismiss(getView(), newOperationRouter.getView());
      newOperationRouter = null;
    }
  }
  void detachNewAccount() {
    if (newAccountRouter != null) {
      detachChild(newAccountRouter);
      ViewExtension.dismiss(getView(), newAccountRouter.getView());
      newAccountRouter = null;
    }
  }

}
