package com.example.pavel.bookkeeping.Model;

import java.util.List;

import io.reactivex.Observable;

public interface  DataStream {
    Observable<List<Operation>> operations();
    Observable<List<Account>> accounts();
}
