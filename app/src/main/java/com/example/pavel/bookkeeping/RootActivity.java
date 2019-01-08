package com.example.pavel.bookkeeping;

import android.view.ViewGroup;

import com.uber.rib.core.Rib;
import com.uber.rib.core.RibActivity;
import com.uber.rib.core.ViewRouter;

import com.example.pavel.bookkeeping.root.RootBuilder;


public class RootActivity extends RibActivity {

    @SuppressWarnings("unchecked")
    @Override
    protected ViewRouter<?, ?, ?> createRouter(ViewGroup parentViewGroup) {
        RootBuilder rootBuilder = new RootBuilder(new RootBuilder.ParentComponent() {
        });
        return rootBuilder.build(parentViewGroup);
    }
}