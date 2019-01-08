package com.example.pavel.bookkeeping.root.bookkeeping.newoperation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel.bookkeeping.Model.Account;
import com.example.pavel.bookkeeping.Model.Category;
import com.example.pavel.bookkeeping.Model.Operation;
import com.example.pavel.bookkeeping.Model.OperationType;
import com.example.pavel.bookkeeping.R;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import io.reactivex.Observable;

class NewOperationView extends ConstraintLayout implements NewOperationInteractor.NewOperationPresenter {
  private Spinner accountFrom;
  private Spinner accountTo;
  private Spinner type;
  private Spinner categories;
  private EditText value;
  private EditText description;
  private Button cancel;
  private Button accept;
  private BehaviorRelay<Operation> operationBehaviorRelay = BehaviorRelay.create();
  private BehaviorRelay<Boolean> confirmBehaviorRelay = BehaviorRelay.create();
  private List<Account> accounts;


  public NewOperationView(Context context) {
    this(context, null);
  }

  public NewOperationView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public NewOperationView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    operationBehaviorRelay.accept(new Operation());

    accountFrom = findViewById(R.id.operationAccountFromSpinner);
    accountFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Operation was = operationBehaviorRelay.getValue();
        was.setAccountFrom(accounts.get(position));
        operationBehaviorRelay.accept(was);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    accountTo = findViewById(R.id.operationAccountToSpinner);
    accountTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Operation was = operationBehaviorRelay.getValue();
        was.setAccountTo(accounts.get(position));
        operationBehaviorRelay.accept(was);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    categories = findViewById(R.id.operationCategorySpinner);
    categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Operation was = operationBehaviorRelay.getValue();
        switch (position) {
          case 0:
            was.setCategory(Category.arrival);
            operationBehaviorRelay.accept(was);
            break;
          case 1:
            was.setCategory(Category.transfer);
            operationBehaviorRelay.accept(was);
            break;
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    type = findViewById(R.id.operationTypeSpinner);
    type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Operation was = operationBehaviorRelay.getValue();
        switch (position){
          case 0:
            was.setType(OperationType.arrival);
            operationBehaviorRelay.accept(was);
            break;
          case 1:
            was.setType(OperationType.expense);
            operationBehaviorRelay.accept(was);
            break;
          case 2:
            was.setType(OperationType.transfer);
            operationBehaviorRelay.accept(was);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    value = findViewById(R.id.operationValueEdit);
    value.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        try {
          Operation was = operationBehaviorRelay.getValue();
          Double valueEdit = new Double(s.toString());
          if (valueEdit < 0){
            valueEdit = 0.0;
          }
          was.setValue(valueEdit);
          operationBehaviorRelay.accept(was);
        } catch (Exception e) {
          value.setText("0", TextView.BufferType.EDITABLE);
        }
      }
    });
    description = findViewById(R.id.operationDescEdit);
    description.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        Operation was = operationBehaviorRelay.getValue();
        was.setDescription(s.toString());
        operationBehaviorRelay.accept(was);
      }
    });

    cancel = findViewById(R.id.cancel);
    cancel.setOnClickListener(v -> confirmBehaviorRelay.accept(false));
    accept = findViewById(R.id.accept);
    accept.setOnClickListener(v -> {
      if (checkOperation()) {
        confirmBehaviorRelay.accept(true);
      } else {
        Toast.makeText(getContext(),R.string.error,Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public Observable<Operation> newOperation() {
    return operationBehaviorRelay.hide();
  }

  @Override
  public Observable<Boolean> confirm() {
    return confirmBehaviorRelay.hide();
  }

  @Override
  public void setAccounts(List<String> accountsName, List<Account> accounts) {
    ArrayAdapter adapterAccounts = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, accountsName);
    accountTo.setAdapter(adapterAccounts);
    accountFrom.setAdapter(adapterAccounts);
    accountTo.setSelection(0);
    accountFrom.setSelection(0);
    if (!accounts.isEmpty()) {
      operationBehaviorRelay.getValue().setAccountFrom(accounts.get(0));
      operationBehaviorRelay.getValue().setAccountTo(accounts.get(0));
    }
    this.accounts = accounts;
  }

  @Override
  public void setCategories(List<String> categories) {
    ArrayAdapter adapterCategory = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
    this.categories.setAdapter(adapterCategory);
    this.categories.setSelection(0);
    if (!categories.isEmpty()) {
      operationBehaviorRelay.getValue().setCategory(Category.arrival);
    }
  }

  @Override
  public void setType(List<String> types) {
    ArrayAdapter adapterType= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, types);
    type.setAdapter(adapterType);
    type.setSelection(0);
    if (!types.isEmpty()) {
      operationBehaviorRelay.getValue().setType(OperationType.arrival);
    }
  }

  private boolean checkOperation() {
    return accountTo.getSelectedItem()!=null &&
            accountFrom.getSelectedItem()!=null &&
            type.getSelectedItem()!=null &&
            categories.getSelectedItem()!=null &&
            !value.getText().toString().isEmpty() &&
            !description.getText().toString().isEmpty();
  }
}
