package com.example.pavel.bookkeeping.root.bookkeeping.newaccount;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NewAccountRouterTest extends RibTestBasePlaceholder {

  @Mock NewAccountBuilder.Component component;
  @Mock NewAccountInteractor interactor;
  @Mock NewAccountView view;

  private NewAccountRouter router;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    router = new NewAccountRouter(view, interactor, component);
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  public void anExampleTest_withSomeConditions_shouldPass() {
    // Use RouterHelper to drive your router's lifecycle.
    RouterHelper.attach(router);
    RouterHelper.detach(router);
  }

}
