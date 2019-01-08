package root;

import com.example.pavel.bookkeeping.root.RootInteractor;
import com.example.pavel.bookkeeping.root.RootRouter;
import com.example.pavel.bookkeeping.root.TestRootInteractor;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.InteractorHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RootInteractorTest extends RibTestBasePlaceholder {

  @Mock RootInteractor.RootPresenter presenter;
  @Mock RootRouter router;

  private RootInteractor interactor;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    interactor = TestRootInteractor.create(presenter);
  }

  @Test
  public void anExampleTest_withSomeConditions_shouldPass() {
    InteractorHelper.attach(interactor, presenter, router, null);
    InteractorHelper.detach(interactor);

  }

}
