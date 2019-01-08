package root;

import com.example.pavel.bookkeeping.root.RootBuilder;
import com.example.pavel.bookkeeping.root.RootInteractor;
import com.example.pavel.bookkeeping.root.RootRouter;
import com.example.pavel.bookkeeping.root.RootView;
import com.example.pavel.bookkeeping.root.bookkeeping.BookKeepingBuilder;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RootRouterTest extends RibTestBasePlaceholder {

  @Mock RootBuilder.Component component;
  @Mock RootInteractor interactor;
  @Mock RootView view;
  @Mock BookKeepingBuilder bookKeepingBuilder;

  private RootRouter router;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    router = new RootRouter(view, interactor, component, bookKeepingBuilder);
  }

  @Test
  public void anExampleTest_withSomeConditions_shouldPass() {
    RouterHelper.attach(router);
    RouterHelper.detach(router);

  }

}
