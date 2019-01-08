package com.example.pavel.bookkeeping.root.bookkeeping.newoperation;

import android.support.annotation.NonNull;
import android.view.View;

import com.uber.rib.core.ViewRouter;

public class NewOperationRouter extends
    ViewRouter<NewOperationView, NewOperationInteractor, NewOperationBuilder.Component> {

  public NewOperationRouter(
      NewOperationView view,
      NewOperationInteractor interactor,
      NewOperationBuilder.Component component) {
    super(view, interactor, component);
  }
}
