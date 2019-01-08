package com.example.pavel.bookkeeping.root.bookkeeping.newaccount;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.pavel.bookkeeping.R;
import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.ViewBuilder;
import java.lang.annotation.Retention;

import javax.inject.Scope;
import javax.inject.Qualifier;

import dagger.Provides;
import dagger.Binds;
import dagger.BindsInstance;

import static java.lang.annotation.RetentionPolicy.CLASS;

public class NewAccountBuilder
    extends ViewBuilder<NewAccountView, NewAccountRouter, NewAccountBuilder.ParentComponent> {

  public NewAccountBuilder(ParentComponent dependency) {
    super(dependency);
  }

  public NewAccountRouter build(ViewGroup parentViewGroup) {
    NewAccountView view = createView(parentViewGroup);
    NewAccountInteractor interactor = new NewAccountInteractor();
    Component component = DaggerNewAccountBuilder_Component.builder()
        .parentComponent(getDependency())
        .view(view)
        .interactor(interactor)
        .build();
    return component.newaccountRouter();
  }

  @Override
  protected NewAccountView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
    return (NewAccountView) inflater.inflate(R.layout.new_account, parentViewGroup, false);
  }

  public interface ParentComponent {
    NewAccountInteractor.Listener listener();
  }

  @dagger.Module
  public abstract static class Module {

    @NewAccountScope
    @Binds
    abstract NewAccountInteractor.NewAccountPresenter presenter(NewAccountView view);

    @NewAccountScope
    @Provides
    static NewAccountRouter router(
      Component component,
      NewAccountView view,
      NewAccountInteractor interactor) {
      return new NewAccountRouter(view, interactor, component);
    }
  }

  @NewAccountScope
  @dagger.Component(modules = Module.class,
       dependencies = ParentComponent.class)
  interface Component extends InteractorBaseComponent<NewAccountInteractor>, BuilderComponent {

    @dagger.Component.Builder
    interface Builder {
      @BindsInstance
      Builder interactor(NewAccountInteractor interactor);
      @BindsInstance
      Builder view(NewAccountView view);
      Builder parentComponent(ParentComponent component);
      Component build();
    }
  }

  interface BuilderComponent  {
    NewAccountRouter newaccountRouter();
  }

  @Scope
  @Retention(CLASS)
  @interface NewAccountScope { }

  @Qualifier
  @Retention(CLASS)
  @interface NewAccountInternal { }
}
