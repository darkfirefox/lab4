package com.example.pavel.bookkeeping.root.bookkeeping.newaccount;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel.bookkeeping.Model.Account;
import com.example.pavel.bookkeeping.R;
import com.jakewharton.rxrelay2.BehaviorRelay;

import io.reactivex.Observable;

class NewAccountView extends ConstraintLayout implements NewAccountInteractor.NewAccountPresenter {
  private EditText nameAccount;
  private EditText balanceAccount;
  private EditText descAccount;
  private Button cancelButton;
  private Button acceptButton;

  private BehaviorRelay<Account> accountBehaviorRelay = BehaviorRelay.create();
  private BehaviorRelay<Boolean> confirmBehaviorRelay = BehaviorRelay.create();

  public NewAccountView(Context context) {
    this(context, null);
  }

  public NewAccountView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public NewAccountView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    nameAccount = findViewById(R.id.accountNameEdit);
    balanceAccount = findViewById(R.id.accountBalanceEdit);
    descAccount = findViewById(R.id.accountDescEdit);
    accountBehaviorRelay.accept(new Account());
    nameAccount.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        Account was = accountBehaviorRelay.getValue();
        was.setName(s.toString());
        accountBehaviorRelay.accept(was);
      }
    });
    balanceAccount.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        try {
          Account was = accountBehaviorRelay.getValue();
          Double balance = new Double(s.toString());
          if (balance < 0) {
            balance = 0.0;
          }
          was.setBalance(balance);
          accountBehaviorRelay.accept(was);
        } catch (Exception e) {
          balanceAccount.setText("0",TextView.BufferType.EDITABLE);
        }
      }
    });
    descAccount.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        Account was = accountBehaviorRelay.getValue();
        was.setDescription(s.toString());
        accountBehaviorRelay.accept(was);
      }
    });
    cancelButton = findViewById(R.id.cancelAccount);
    acceptButton = findViewById(R.id.acceptAccount);
    cancelButton.setOnClickListener(v -> confirmBehaviorRelay.accept(false));
    acceptButton.setOnClickListener(v -> {
      if (checkField()) {
       confirmBehaviorRelay.accept(true);
      }
    });
  }
  @Override
  public Observable<Account> newAccount() {
    return accountBehaviorRelay.hide();
  }

  @Override
  public Observable<Boolean> confirm() {
    return confirmBehaviorRelay.hide();
  }
  private boolean checkField() {
      if (nameAccount.getText().toString().isEmpty()) {
          Toast.makeText(getContext(),R.string.error,Toast.LENGTH_SHORT).show();
          return false;
      } else if (balanceAccount.getText().toString().isEmpty()) {
          Toast.makeText(getContext(),R.string.error,Toast.LENGTH_SHORT).show();
          return false;
      } else if (descAccount.getText().toString().isEmpty()) {
          Toast.makeText(getContext(),R.string.error,Toast.LENGTH_SHORT).show();
          return false;
      } else {
          return true;
      }
  }
}
