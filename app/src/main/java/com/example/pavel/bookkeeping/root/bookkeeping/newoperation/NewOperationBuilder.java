package com.example.pavel.bookkeeping.root.bookkeeping.newoperation;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.pavel.bookkeeping.Model.DataStream;
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

public class NewOperationBuilder
    extends ViewBuilder<NewOperationView, NewOperationRouter, NewOperationBuilder.ParentComponent> {

  public NewOperationBuilder(ParentComponent dependency) {
    super(dependency);
  }

  public NewOperationRouter build(ViewGroup parentViewGroup) {
    NewOperationView view = createView(parentViewGroup);
    NewOperationInteractor interactor = new NewOperationInteractor();
    Component component = DaggerNewOperationBuilder_Component.builder()
        .parentComponent(getDependency())
        .view(view)
        .interactor(interactor)
        .build();
    return component.newoperationRouter();
  }

  @Override
  protected NewOperationView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
    return (NewOperationView) inflater.inflate(R.layout.new_operation, parentViewGroup, false);
  }

  public interface ParentComponent {
    NewOperationInteractor.Listener opeartionListener();
    DataStream dataStream();
  }

  @dagger.Module
  public abstract static class Module {

    @NewOperationScope
    @Binds
    abstract NewOperationInteractor.NewOperationPresenter presenter(NewOperationView view);

    @NewOperationScope
    @Provides
    static NewOperationRouter router(
      Component component,
      NewOperationView view,
      NewOperationInteractor interactor) {
      return new NewOperationRouter(view, interactor, component);
    }
  }

  @NewOperationScope
  @dagger.Component(modules = Module.class,
       dependencies = ParentComponent.class)
  interface Component extends InteractorBaseComponent<NewOperationInteractor>, BuilderComponent {

    @dagger.Component.Builder
    interface Builder {
      @BindsInstance
      Builder interactor(NewOperationInteractor interactor);
      @BindsInstance
      Builder view(NewOperationView view);
      Builder parentComponent(ParentComponent component);
      Component build();
    }
  }

  interface BuilderComponent  {
    NewOperationRouter newoperationRouter();
  }

  @Scope
  @Retention(CLASS)
  @interface NewOperationScope { }

  @Qualifier
  @Retention(CLASS)
  @interface NewOperationInternal { }
}
