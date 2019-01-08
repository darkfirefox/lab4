package com.example.pavel.bookkeeping.view;

import android.view.View;
import android.view.ViewGroup;

public class ViewExtension {
    public static void replaceView(ViewGroup parentView, View childView) {
        for (int i=0;i< parentView.getChildCount();i++) {
            parentView.getChildAt(i).setVisibility(View.GONE);
        }
        parentView.addView(childView);
    }

    public static void dismiss(ViewGroup parentView, View childView) {
        parentView.removeView(childView);
        for (int i=0;i< parentView.getChildCount();i++) {
            parentView.getChildAt(i).setVisibility(View.VISIBLE);
        }
    }
}
