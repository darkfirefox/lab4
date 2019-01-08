package com.example.pavel.bookkeeping.root.bookkeeping.newaccount;

import com.example.pavel.bookkeeping.Model.Account;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.InteractorHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.Mockito.when;

public class NewAccountInteractorTest extends RibTestBasePlaceholder {

  @Mock NewAccountInteractor.NewAccountPresenter presenter;
  @Mock NewAccountRouter router;
  @Mock NewAccountInteractor.Listener listener;

  private NewAccountInteractor interactor;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    interactor = TestNewAccountInteractor.create(presenter, listener);
  }

  @Test
  public void anExampleTest_withSomeConditions_shouldPass() {
    when(presenter.newAccount()).thenReturn(Observable.just(new Account()));
    when(presenter.confirm()).thenReturn(Observable.just(false));
    InteractorHelper.attach(interactor, presenter, router, null);
    InteractorHelper.detach(interactor);
  }
}
