package com.example.pavel.bookkeeping.root.bookkeeping.newaccount;

import android.support.annotation.Nullable;

import com.example.pavel.bookkeeping.Model.Account;
import com.uber.autodispose.ObservableScoper;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import com.uber.rib.core.Presenter;
import com.uber.rib.core.Router;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

@RibInteractor
public class NewAccountInteractor
    extends Interactor<NewAccountInteractor.NewAccountPresenter, NewAccountRouter> {

  @Inject NewAccountPresenter presenter;
  @Inject Listener listener;

  private Account newAcount;

  @Override
  protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
    super.didBecomeActive(savedInstanceState);

    presenter.newAccount()
            .to(new ObservableScoper<>(this))
            .subscribe(account -> newAcount = account);

    presenter.confirm()
            .to(new ObservableScoper<>(this))
            .subscribe(aBoolean -> {
              if (aBoolean) {
                  listener.newAccountCreated(newAcount);
              } else {
                  listener.newAccountCancel();
              }
            });
  }

  @Override
  protected void willResignActive() {
    super.willResignActive();
  }


    public interface Listener {

        void newAccountCancel();
        void newAccountCreated(Account newAccount);
    }

  interface NewAccountPresenter {
    Observable<Account> newAccount();
    Observable<Boolean> confirm();
  }
}
