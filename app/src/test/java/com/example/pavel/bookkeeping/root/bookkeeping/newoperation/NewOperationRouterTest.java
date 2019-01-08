package com.example.pavel.bookkeeping.root.bookkeeping.newoperation;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NewOperationRouterTest extends RibTestBasePlaceholder {

  @Mock NewOperationBuilder.Component component;
  @Mock NewOperationInteractor interactor;
  @Mock NewOperationView view;

  private NewOperationRouter router;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    router = new NewOperationRouter(view, interactor, component);
  }

  @Test
  public void anExampleTest_withSomeConditions_shouldPass() {
    RouterHelper.attach(router);
    RouterHelper.detach(router);
  }

}
