package com.example.pavel.bookkeeping.root.bookkeeping;

import com.example.pavel.bookkeeping.Model.Account;
import com.example.pavel.bookkeeping.Model.BaseModel;
import com.example.pavel.bookkeeping.Model.Category;
import com.example.pavel.bookkeeping.Model.MutableDataStream;
import com.example.pavel.bookkeeping.Model.Operation;
import com.example.pavel.bookkeeping.Model.OperationType;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.uber.rib.core.InteractorHelper;
import com.uber.rib.core.RibTestBasePlaceholder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class BookKeepingInteractorTest extends RibTestBasePlaceholder {

  @Mock BookKeepingInteractor.BookKeepingPresenter presenter;
  @Mock BookKeepingRouter router;
  @Mock MutableDataStream mutableDataStream;

  private BookKeepingInteractor interactor;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    interactor = TestBookKeepingInteractor.create(presenter, mutableDataStream);
  }

  @Test
  public void deleteOperation() {
    BehaviorRelay<Integer> creating = BehaviorRelay.create();
    List<Account> accounts = new ArrayList<>();
    List<Operation> operations = new ArrayList<>();
    Account account1 = new Account("account1", "desc1", 500.0);
    Account account2 = new Account("account2", "desc2", 750.0);
    accounts.add(account1);
    accounts.add(account2);
    Operation operation1 = new Operation(LocalDateTime.now(),OperationType.arrival,250.0, account1, account1, Category.arrival,"opDesc1");
    Operation operation2 = new Operation(LocalDateTime.now(),OperationType.arrival,350.0, account2, account2, Category.arrival,"opDesc2");
    operations.add(operation1);
    operations.add(operation2);

    BehaviorRelay<List<Account>> accountBR = BehaviorRelay.create();
    BehaviorRelay<List<Operation>> operationBR = BehaviorRelay.create();
    BehaviorRelay<BaseModel> deleteRow = BehaviorRelay.create();
    BehaviorRelay<String> search = BehaviorRelay.create();
    BehaviorRelay<Boolean> searchActivated = BehaviorRelay.create();

    when(mutableDataStream.accounts()).thenReturn(accountBR.hide());
    when(mutableDataStream.operations()).thenReturn(operationBR.hide());
    when(presenter.creating()).thenReturn(creating.hide());
    when(presenter.deleteRow()).thenReturn(deleteRow.hide());
    when(presenter.selectedView()).thenReturn(Observable.just(1));
    when(presenter.search()).thenReturn(search.hide());
    when(presenter.searchActivated()).thenReturn(searchActivated.hide());

    accountBR.accept(accounts);
    operationBR.accept(operations);

    InteractorHelper.attach(interactor, presenter, router, null);

    deleteRow.accept(operation1);


    verify(mutableDataStream).removeOperation(operation1);

    InteractorHelper.detach(interactor);
  }

  @Test
  public void deleteAccount() {
    BehaviorRelay<Integer> creating = BehaviorRelay.create();
    List<Account> accounts = new ArrayList<>();
    List<Operation> operations = new ArrayList<>();
    Account account1 = new Account("account1", "desc1", 500.0);
    Account account2 = new Account("account2", "desc2", 750.0);
    accounts.add(account1);
    accounts.add(account2);
    Operation operation1 = new Operation(LocalDateTime.now(),OperationType.arrival,250.0, account1, account1, Category.arrival,"opDesc1");
    Operation operation2 = new Operation(LocalDateTime.now(),OperationType.arrival,350.0, account2, account2, Category.arrival,"opDesc2");
    operations.add(operation1);
    operations.add(operation2);

    BehaviorRelay<List<Account>> accountBR = BehaviorRelay.create();
    BehaviorRelay<List<Operation>> operationBR = BehaviorRelay.create();
    BehaviorRelay<BaseModel> deleteRow = BehaviorRelay.create();
    BehaviorRelay<String> search = BehaviorRelay.create();
    BehaviorRelay<Boolean> searchActivated = BehaviorRelay.create();

    when(mutableDataStream.accounts()).thenReturn(accountBR.hide());
    when(mutableDataStream.operations()).thenReturn(operationBR.hide());
    when(presenter.creating()).thenReturn(creating.hide());
    when(presenter.deleteRow()).thenReturn(deleteRow.hide());
    when(presenter.selectedView()).thenReturn(Observable.just(0));
    when(presenter.search()).thenReturn(search.hide());
    when(presenter.searchActivated()).thenReturn(searchActivated.hide());

    accountBR.accept(accounts);
    operationBR.accept(operations);

    InteractorHelper.attach(interactor, presenter, router, null);

    deleteRow.accept(account1);
    verify(mutableDataStream).removeAccount(account1);

    List<Operation> newOperations = mutableDataStream.operations().blockingFirst();
    Assert.assertEquals(newOperations.size(),2);
    Assert.assertEquals(newOperations.get(0).getAccountFrom(), account2);
    Assert.assertEquals(newOperations.get(0).getAccountTo(), account2);
    Assert.assertEquals(newOperations.get(1).getAccountFrom(), account2);
    Assert.assertEquals(newOperations.get(1).getAccountTo(), account2);

    InteractorHelper.detach(interactor);
  }
}
