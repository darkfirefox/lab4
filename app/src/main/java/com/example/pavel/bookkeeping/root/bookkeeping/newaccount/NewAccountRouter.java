package com.example.pavel.bookkeeping.root.bookkeeping.newaccount;

import android.support.annotation.NonNull;
import android.view.View;

import com.uber.rib.core.ViewRouter;

public class NewAccountRouter extends
    ViewRouter<NewAccountView, NewAccountInteractor, NewAccountBuilder.Component> {

  public NewAccountRouter(
      NewAccountView view,
      NewAccountInteractor interactor,
      NewAccountBuilder.Component component) {
    super(view, interactor, component);
  }
}
