package com.example.pavel.bookkeeping.root.bookkeeping.newoperation;

import com.example.pavel.bookkeeping.Model.Account;
import com.example.pavel.bookkeeping.Model.DataStream;
import com.example.pavel.bookkeeping.Model.Operation;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.InteractorHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewOperationInteractorTest extends RibTestBasePlaceholder {

  @Mock NewOperationInteractor.NewOperationPresenter presenter;
  @Mock NewOperationRouter router;
  @Mock DataStream dataStream;
  @Mock NewOperationInteractor.Listener listener;

  private NewOperationInteractor interactor;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    interactor = TestNewOperationInteractor.create(presenter, dataStream, listener);
  }

  @Test
  public void addNewOperation() {
    List<Account> accounts = new ArrayList<>();
    Account account = new Account();
    accounts.add(account);
    BehaviorRelay<Boolean> confirm = BehaviorRelay.create();
    BehaviorRelay<Operation> operation = BehaviorRelay.create();
    when(dataStream.accounts()).thenReturn(Observable.just(accounts));
    when(presenter.confirm()).thenReturn(confirm.hide());
    when(presenter.newOperation()).thenReturn(operation.hide());

    InteractorHelper.attach(interactor, presenter, router, null);

    operation.accept(new Operation());
    confirm.accept(true);
    verify(listener).newOperationCreated(any(Operation.class));

    InteractorHelper.detach(interactor);
  }
}
