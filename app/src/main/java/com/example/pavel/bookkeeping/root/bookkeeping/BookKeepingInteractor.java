package com.example.pavel.bookkeeping.root.bookkeeping;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.pavel.bookkeeping.Model.Account;
import com.example.pavel.bookkeeping.Model.BaseModel;
import com.example.pavel.bookkeeping.Model.Category;
import com.example.pavel.bookkeeping.Model.MutableDataStream;
import com.example.pavel.bookkeeping.Model.Operation;
import com.example.pavel.bookkeeping.R;
import com.example.pavel.bookkeeping.root.bookkeeping.newaccount.NewAccountInteractor;
import com.example.pavel.bookkeeping.root.bookkeeping.newoperation.NewOperationInteractor;
import com.uber.autodispose.ObservableScoper;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;

import javax.inject.Inject;

@RibInteractor
public class BookKeepingInteractor
    extends Interactor<BookKeepingInteractor.BookKeepingPresenter, BookKeepingRouter> {

  @Inject BookKeepingPresenter presenter;
  @Inject MutableDataStream mutableDataStream;

  @Override
  protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
    super.didBecomeActive(savedInstanceState);


    subscribeCreating();
    subscribesForAccounts();
    subscribesOnOperations();
    subscribesOnDeleting();
  }

  private void subscribeCreating() {
      presenter.creating()
              .doOnError(throwable -> Log.e("Error",throwable.getMessage()))
              .to(new ObservableScoper<>(this))
              .subscribe(integer -> {
                  switch ( integer.intValue()){
                      case R.id.newAccount:
                          getRouter().attachNewAccount();
                          break;
                      case R.id.newOperation:
                          getRouter().attachNewOperation();
                          break;
                      default:
                          break;
                  }
              });
  }

  private void subscribesForAccounts() {
      Observable.combineLatest(mutableDataStream.accounts(),presenter.selectedView(), presenter.searchActivated(), presenter.search(),
              (accounts, view, activated, search)  -> new AccountsSubscribe(accounts,view == 0, activated, search))
              .doOnError(throwable -> Log.e("Error",throwable.getMessage()))
              .filter(accountsSubscribe -> accountsSubscribe.isAccounts)
              .to(new ObservableScoper<>(this))
              .subscribe(accountsSubscribe -> {
                    if (accountsSubscribe.searchActivated && accountsSubscribe.search != null) {
                        List<Account> filtered = new ArrayList<>();
                        for(Account account: accountsSubscribe.accounts) {
                            if (account.getName().toLowerCase().contains(accountsSubscribe.search.toLowerCase())) {
                                filtered.add(account);
                            }
                        }
                        presenter.showViewModel(filtered);
                    } else {
                        presenter.showViewModel(accountsSubscribe.accounts);
                    }
             });
  }

    private void subscribesOnOperations() {
        Observable.combineLatest(mutableDataStream.operations(),presenter.selectedView(), presenter.searchActivated(), presenter.search(),
                (operations, view, activated, search)  -> new OperationSubscribe(operations,view != 0, view, activated, search))
                .doOnError(throwable -> Log.e("Error",throwable.getMessage()))
                .filter(operationSubscribe -> operationSubscribe.isOperations)
                .to(new ObservableScoper<>(this))
                .subscribe(operationSubscribe -> {
                        List<Operation> operations = null;
                        if (operationSubscribe.searchActivated && operationSubscribe.search != null) {
                            switch (operationSubscribe.typeOperation){
                                case 1:
                                    operations = getOperationWithCategoryAndFiltered(operationSubscribe.operations, Category.arrival, operationSubscribe.search);
                                    break;
                                case 2:
                                    operations = getOperationWithCategoryAndFiltered(operationSubscribe.operations, Category.transfer, operationSubscribe.search);
                                    break;
                            }
                            presenter.showViewModel(operations);
                        } else {
                            switch (operationSubscribe.typeOperation) {
                                case 1:
                                    operations = getOperationWithCategory(operationSubscribe.operations, Category.arrival);
                                    break;
                                case 2:
                                    operations = getOperationWithCategory(operationSubscribe.operations, Category.transfer);
                                    break;
                            }
                            presenter.showViewModel(operations);
                        }
                });
    }

    private void subscribesOnDeleting() {
      Observable.combineLatest(presenter.selectedView(), presenter.deleteRow(), (view, model) ->
          new DeleteSubscribe(view, model))
              .doOnError(throwable -> Log.e("Error",throwable.getMessage()))
              .to(new ObservableScoper<>(this))
              .subscribe(deleteSubscribe -> {
                  switch (deleteSubscribe.deletingType){
                      case 0:
                          try {
                              //mutableDataStream.removeAccount((Account) deleteSubscribe.model);
                              deleteAccount((Account) deleteSubscribe.model);
                          }catch (Exception e) {

                          }
                          break;
                      case 1: case 2:
                          try {
                              mutableDataStream.removeOperation((Operation) deleteSubscribe.model);
                          }catch (Exception e) {

                          }
                          break;
                  }
              });
    }

    private List<Operation> getOperationWithCategory(List<Operation> operations, Category category) {
      List<Operation> withCategory = new ArrayList<>();
      for(Operation operation: operations) {
          if (operation.getCategory() == category) {
              withCategory.add(operation);
          }
      }
      return withCategory;
    }

    private List<Operation> getOperationWithCategoryAndFiltered(List<Operation> operations, Category category, String search) {
        List<Operation> withCategory = new ArrayList<>();
        for(Operation operation: operations) {
            if (operation.getCategory() == category && operation.getDescription().toLowerCase().contains(search.toLowerCase())) {
                withCategory.add(operation);
            }
        }
        return withCategory;
    }

    private void deleteAccount(Account deleting) {
      AtomicReference<Account> otherAccount = new AtomicReference<>();
      mutableDataStream.accounts()
              .elementAt(0)
              .subscribe(accounts -> {
          for(Account account: accounts) {
              if (account != deleting) {
                  otherAccount.set(account);
                  break;
              }
          }
      }).dispose();
      if (otherAccount.get() != null) {
          mutableDataStream.operations()
                  .elementAt(0)
                  .subscribe(operations -> {
                      List<Operation> newOperations = new ArrayList<>();
                      for(Operation operation: operations) {
                        if (operation.getAccountFrom() == deleting) {
                            operation.setAccountFrom(otherAccount.get());
                        }
                        if (operation.getAccountTo() == deleting) {
                            operation.setAccountTo(otherAccount.get());
                        }
                        newOperations.add(operation);
                      }
                      mutableDataStream.setOperations(operations);
                  }).dispose();
        } else {
          mutableDataStream.setOperations(new ArrayList<>());
      }
        mutableDataStream.removeAccount(deleting);
    }

    class AccountsSubscribe {
      List<Account> accounts;
      Boolean isAccounts;
      Boolean searchActivated;
      String search;

        public AccountsSubscribe(List<Account> accounts, Boolean isAccounts, Boolean searchActivated, String search) {
            this.accounts = accounts;
            this.isAccounts = isAccounts;
            this.searchActivated = searchActivated;
            this.search = search;
        }
    }

    class OperationSubscribe {
      List<Operation> operations;
      Boolean isOperations;
      Integer typeOperation;
      Boolean searchActivated;
      String search;

        public OperationSubscribe(List<Operation> operations, Boolean isOperations, Integer typeOperation, Boolean searchActivated, String search) {
            this.operations = operations;
            this.isOperations = isOperations;
            this.typeOperation = typeOperation;
            this.searchActivated = searchActivated;
            this.search = search;
        }
    }

    class DeleteSubscribe {
      Integer deletingType;
      BaseModel model;

        public DeleteSubscribe(Integer deletingType, BaseModel model) {
            this.deletingType = deletingType;
            this.model = model;
        }
    }

  interface BookKeepingPresenter {
    Observable<Integer> selectedView();
    Observable<BaseModel> deleteRow();
    Observable<Integer> creating();
    Observable<String> search();
    Observable<Boolean> searchActivated();
    void showViewModel(List<? extends BaseModel> list);
  }

  class NewAccountListener implements NewAccountInteractor.Listener {
      @Override
      public void newAccountCancel() {
          getRouter().detachNewAccount();
      }

      @Override
      public void newAccountCreated(Account newAccount) {
          getRouter().detachNewAccount();
          mutableDataStream.addAccount(newAccount);
      }
  }

  class NewOperationListener implements NewOperationInteractor.Listener {

      @Override
      public void newOperationCancel() {
          getRouter().detachNewOperation();
      }

      @Override
      public void newOperationCreated(Operation newOperation) {
        getRouter().detachNewOperation();
        mutableDataStream.addOperation(newOperation);
      }
  }

}
