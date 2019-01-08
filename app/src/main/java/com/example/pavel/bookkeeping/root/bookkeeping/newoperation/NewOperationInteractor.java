package com.example.pavel.bookkeeping.root.bookkeeping.newoperation;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.example.pavel.bookkeeping.Model.Account;
import com.example.pavel.bookkeeping.Model.Category;
import com.example.pavel.bookkeeping.Model.DataStream;
import com.example.pavel.bookkeeping.Model.Operation;
import com.example.pavel.bookkeeping.Model.OperationType;
import com.example.pavel.bookkeeping.R;
import com.uber.autodispose.ObservableScoper;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import com.uber.rib.core.Presenter;
import com.uber.rib.core.Router;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Coordinates Business Logic for {@link NewOperationScope}.
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
public class NewOperationInteractor
    extends Interactor<NewOperationInteractor.NewOperationPresenter, NewOperationRouter> {

  @Inject NewOperationPresenter presenter;
  @Inject DataStream dataStream;
  @Inject Listener listener;

  private Operation newOperation;

  @Override
  protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
    super.didBecomeActive(savedInstanceState);

    dataStream.accounts()
            .to(new ObservableScoper<>(this))
            .subscribe(accounts -> {
              List<String> accountsName = new ArrayList<>();
              for (Account account:accounts) {
                accountsName.add(account.getName());
              }
              presenter.setAccounts(accountsName, accounts);
            });
    presenter.newOperation()
            .to(new ObservableScoper<>(this))
            .subscribe(operation -> newOperation = operation);

    presenter.confirm()
            .to(new ObservableScoper<>(this))
            .subscribe(aBoolean -> {
              if (aBoolean) {
                listener.newOperationCreated(newOperation);
              } else {
                listener.newOperationCancel();
              }
            });

    List<String> categories = new ArrayList<>();
    /*String arrival = Resources.getSystem().getString(R.string.categoryArrival);
    String transfer = Resources.getSystem().getString(R.string.categoryTransfer);*/
    String arrival = "arrival";
    String transfer = "transfer";
    categories.add(arrival);
    categories.add(transfer);
    presenter.setCategories(categories);

    List<String> types = new ArrayList<>();
    //String expense = Resources.getSystem().getString(R.string.expense);
    String expense = "expense";
    types.add(arrival);
    types.add(expense);
    types.add(transfer);
    presenter.setType(types);
  }

  public interface Listener {

    void newOperationCancel();
    void newOperationCreated(Operation newOperation);
  }

  interface NewOperationPresenter {
    Observable<Operation> newOperation();
    Observable<Boolean> confirm();

    void setAccounts(List<String> accountsName, List<Account> accounts);
    void setCategories(List<String> categories);
    void setType(List<String> types);
  }
}
