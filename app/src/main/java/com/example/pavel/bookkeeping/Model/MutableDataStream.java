package com.example.pavel.bookkeeping.Model;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class MutableDataStream implements DataStream {
    private final BehaviorRelay<List<Operation>> operationsRelay = BehaviorRelay.create();
    private final BehaviorRelay<List<Account>> accountsRelay = BehaviorRelay.create();

    public MutableDataStream() {
        operationsRelay.accept(new ArrayList<Operation>());
        accountsRelay.accept(new ArrayList<Account>());
    }

    public MutableDataStream(List<Operation> operations, List<Account> accounts) {
        operationsRelay.accept(operations);
        accountsRelay.accept(accounts);
    }

    public void addOperation(Operation operation) {
        List<Operation> currentOperation = operationsRelay.getValue();
        currentOperation.add(operation);
        operationsRelay.accept(currentOperation);
    }

    public void removeOperation(Operation operation) {
        List<Operation> currentOperation = operationsRelay.getValue();
        currentOperation.remove(operation);
        operationsRelay.accept(currentOperation);
    }

    public void addAccount(Account account) {
        List<Account> currentAccount = accountsRelay.getValue();
        currentAccount.add(account);
        accountsRelay.accept(currentAccount);
    }

    public void removeAccount(Account account) {
        List<Account> currentAccount = accountsRelay.getValue();
        currentAccount.remove(account);
        accountsRelay.accept(currentAccount);
    }

    public void setOperations(List<Operation> operations) {
        operationsRelay.accept(operations);
    }



    @Override
    public Observable<List<Operation>> operations() {
        return operationsRelay.hide();
    }

    @Override
    public Observable<List<Account>> accounts() {
        return accountsRelay.hide();
    }
}

