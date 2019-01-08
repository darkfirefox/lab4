package com.example.pavel.bookkeeping.root;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.pavel.bookkeeping.Model.DataStream;
import com.example.pavel.bookkeeping.Model.MutableDataStream;
import com.example.pavel.bookkeeping.R;
import com.example.pavel.bookkeeping.root.bookkeeping.BookKeepingBuilder;
import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.ViewBuilder;
import java.lang.annotation.Retention;

import javax.inject.Named;
import javax.inject.Scope;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Provides;
import dagger.Binds;
import dagger.BindsInstance;

import static java.lang.annotation.RetentionPolicy.CLASS;

public class RootBuilder
    extends ViewBuilder<RootView, RootRouter, RootBuilder.ParentComponent> {

  public RootBuilder(ParentComponent dependency) {
    super(dependency);
  }

  public RootRouter build(ViewGroup parentViewGroup) {
    RootView view = createView(parentViewGroup);
    RootInteractor interactor = new RootInteractor();
    Component component = DaggerRootBuilder_Component.builder()
        .parentComponent(getDependency())
        .view(view)
        .interactor(interactor)
        .build();
    return component.rootRouter();
  }

  @Override
  protected RootView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
    return (RootView) inflater.inflate(R.layout.activity_root, parentViewGroup, false);
  }

  public interface ParentComponent {
  }

  @dagger.Module
  public abstract static class Module {

    @RootScope
    @Binds
    abstract RootInteractor.RootPresenter presenter(RootView view);

    @RootScope
    @Provides
    static RootRouter router(
      Component component,
      RootView view,
      RootInteractor interactor) {
      return new RootRouter(view, interactor, component, new BookKeepingBuilder(component));
    }

    @RootScope
    @Provides
    static MutableDataStream mutableDataStream() {
      return new MutableDataStream();
    };

    @RootScope
    @Binds
    abstract DataStream scoreStream(@RootScope MutableDataStream mutableScoreStream);
  }

  @RootScope
  @dagger.Component(modules = Module.class,
       dependencies = ParentComponent.class)
  public interface Component extends InteractorBaseComponent<RootInteractor>, BookKeepingBuilder.ParentComponent, BuilderComponent {

    @dagger.Component.Builder
    interface Builder {
      @BindsInstance
      Builder interactor(RootInteractor interactor);
      @BindsInstance
      Builder view(RootView view);
      Builder parentComponent(ParentComponent component);
      Component build();
    }
  }

  interface BuilderComponent  {
    RootRouter rootRouter();
  }

  @Singleton
  @Scope
  @Retention(CLASS)
  @interface RootScope { }

  @Qualifier
  @Retention(CLASS)
  @interface RootInternal { }
}
