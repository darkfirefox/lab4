package com.example.pavel.bookkeeping.root.bookkeeping;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.pavel.bookkeeping.Model.DataStream;
import com.example.pavel.bookkeeping.Model.MutableDataStream;
import com.example.pavel.bookkeeping.R;
import com.example.pavel.bookkeeping.root.bookkeeping.newaccount.NewAccountBuilder;
import com.example.pavel.bookkeeping.root.bookkeeping.newaccount.NewAccountInteractor;
import com.example.pavel.bookkeeping.root.bookkeeping.newoperation.NewOperationBuilder;
import com.example.pavel.bookkeeping.root.bookkeeping.newoperation.NewOperationInteractor;
import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.ViewBuilder;
import java.lang.annotation.Retention;

import javax.inject.Scope;
import javax.inject.Qualifier;

import dagger.Provides;
import dagger.Binds;
import dagger.BindsInstance;

import static java.lang.annotation.RetentionPolicy.CLASS;

public class BookKeepingBuilder
    extends ViewBuilder<BookKeepingView, BookKeepingRouter, BookKeepingBuilder.ParentComponent> {

  public BookKeepingBuilder(ParentComponent dependency) {
    super(dependency);
  }


  public BookKeepingRouter build(ViewGroup parentViewGroup) {
    BookKeepingView view = createView(parentViewGroup);
    BookKeepingInteractor interactor = new BookKeepingInteractor();
    Component component = DaggerBookKeepingBuilder_Component.builder()
        .parentComponent(getDependency())
        .view(view)
        .interactor(interactor)
        .build();
    return component.bookkeepingRouter();
  }

  @Override
  protected BookKeepingView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
    return (BookKeepingView) inflater.inflate(R.layout.bookkeeping, parentViewGroup, false);
  }

  public interface ParentComponent {
    MutableDataStream mutableDataStream();
    DataStream dataStream();
  }

  @dagger.Module
  public abstract static class Module {

    @BookKeepingScope
    @Binds
    abstract BookKeepingInteractor.BookKeepingPresenter presenter(BookKeepingView view);

    @BookKeepingScope
    @Provides
    static BookKeepingRouter router(
      Component component,
      BookKeepingView view,
      BookKeepingInteractor interactor) {
      return new BookKeepingRouter(view, interactor, component, new NewOperationBuilder(component), new NewAccountBuilder(component));
    }


    @BookKeepingScope
    @Provides
    static NewAccountInteractor.Listener newAccountListener(BookKeepingInteractor bookKeepingInteractor) {
      return bookKeepingInteractor.new NewAccountListener();
    }

    @BookKeepingScope
    @Provides
    static NewOperationInteractor.Listener newOperationListener(BookKeepingInteractor bookKeepingInteractor) {
      return bookKeepingInteractor.new NewOperationListener();
    }
  }

  @BookKeepingScope
  @dagger.Component(modules = Module.class,
       dependencies = ParentComponent.class)
  interface Component extends InteractorBaseComponent<BookKeepingInteractor>, NewOperationBuilder.ParentComponent, NewAccountBuilder.ParentComponent, BuilderComponent {

    @dagger.Component.Builder
    interface Builder {
      @BindsInstance
      Builder interactor(BookKeepingInteractor interactor);
      @BindsInstance
      Builder view(BookKeepingView view);
      Builder parentComponent(ParentComponent component);
      Component build();
    }
  }

  interface BuilderComponent  {
    BookKeepingRouter bookkeepingRouter();
  }

  @Scope
  @Retention(CLASS)
  @interface BookKeepingScope { }

  @Qualifier
  @Retention(CLASS)
  @interface BookKeepingInternal { }
}
