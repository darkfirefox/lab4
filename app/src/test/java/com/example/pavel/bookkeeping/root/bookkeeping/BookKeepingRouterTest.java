package com.example.pavel.bookkeeping.root.bookkeeping;

import com.example.pavel.bookkeeping.root.bookkeeping.newaccount.NewAccountBuilder;
import com.example.pavel.bookkeeping.root.bookkeeping.newoperation.NewOperationBuilder;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookKeepingRouterTest extends RibTestBasePlaceholder {

  @Mock BookKeepingBuilder.Component component;
  @Mock BookKeepingInteractor interactor;
  @Mock BookKeepingView view;
  @Mock
  NewAccountBuilder newAccountBuilder;
  @Mock
  NewOperationBuilder newOperationBuilder;

  private BookKeepingRouter router;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    router = new BookKeepingRouter(view, interactor, component, newOperationBuilder, newAccountBuilder);
  }

  @Test
  public void anExampleTest_withSomeConditions_shouldPass() {
    RouterHelper.attach(router);
    RouterHelper.detach(router);
  }

}
