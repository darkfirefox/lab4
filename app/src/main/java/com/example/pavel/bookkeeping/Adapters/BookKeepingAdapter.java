package com.example.pavel.bookkeeping.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pavel.bookkeeping.Model.Account;
import com.example.pavel.bookkeeping.Model.BaseModel;
import com.example.pavel.bookkeeping.Model.Operation;
import com.example.pavel.bookkeeping.R;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class BookKeepingAdapter extends RecyclerView.Adapter<BaseViewHolder>{
    private List<? extends BaseModel> mList = new ArrayList<>();
    private LayoutInflater mInflator;
    private BehaviorRelay<BaseModel> mViewClickSubject = BehaviorRelay.create();

    public BookKeepingAdapter(Context context) {
        this.mInflator = LayoutInflater.from(context);
    }

    public Observable<BaseModel> deletingRow() {
        return mViewClickSubject.hide();
    }

    public void setList(List<? extends BaseModel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case Constants.ViewType.ACCOUNT_TYPE:
            return new AccountHolder(mInflator.inflate(R.layout.accout_holder,viewGroup,false));
            case Constants.ViewType.OPERATION_TYPE:
                return new OperationHolder(mInflator.inflate(R.layout.operation_holder,viewGroup,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.bind(mList.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class AccountHolder extends BaseViewHolder<Account>  implements  View.OnCreateContextMenuListener {
        TextView nameAccount;
        TextView balanceAccount;
        TextView descAccount;
        Context context;

        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            nameAccount = itemView.findViewById(R.id.nameAccount);
            balanceAccount = itemView.findViewById(R.id.balanceAccount);
            descAccount = itemView.findViewById(R.id.descAccount);
            context = itemView.getContext();
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void bind(Account object) {
            StringBuilder stringBuilder = new StringBuilder();
            Resources resources = itemView.getResources();
            stringBuilder.append(resources.getString(R.string.accountName)).append(object.getName());
            nameAccount.setText(stringBuilder);
            stringBuilder.setLength(0);
            stringBuilder.append(resources.getString(R.string.accountBalance)).append(object.getBalance());
            balanceAccount.setText(stringBuilder);
            stringBuilder.setLength(0);
            stringBuilder.append(resources.getString(R.string.accountDesc)).append(object.getDescription());
            descAccount.setText(stringBuilder);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater menuInflater = new MenuInflater(context);
            menuInflater.inflate(R.menu.context_menu, menu);
            for (int i = 0; i< menu.size(); i++) {
                menu.getItem(i).setOnMenuItemClickListener(menuItemClickListener);
            }
        }

        private final MenuItem.OnMenuItemClickListener menuItemClickListener = item -> {
            switch (item.getItemId()) {
                case R.id.delete:
                    mViewClickSubject.accept(mList.get(getLayoutPosition()));
                    break;
            }
            return false;
        };

    }

    public class OperationHolder extends  BaseViewHolder<Operation> implements  View.OnCreateContextMenuListener {
        TextView time;
        TextView type;
        TextView from;
        TextView to;
        TextView howMuch;
        Context context;


        public OperationHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            type = itemView.findViewById(R.id.type);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            howMuch = itemView.findViewById(R.id.howMuch);
            context = itemView.getContext();
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void bind(Operation object) {
            StringBuilder stringBuilder = new StringBuilder();
            Resources resources = itemView.getResources();
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
            stringBuilder.append(resources.getString(R.string.time)).append(object.getTimestamp().format(formatter));
            time.setText(stringBuilder);
            stringBuilder.setLength(0);
            stringBuilder.append(resources.getString(R.string.type));
            switch (object.getType()){
                case arrival:
                    stringBuilder.append(resources.getString(R.string.categoryArrival));
                    break;
                case expense:
                    stringBuilder.append(resources.getString(R.string.expense));
                    break;
                case transfer:
                    stringBuilder.append(resources.getString(R.string.categoryTransfer));
                    break;
            }
            type.setText(stringBuilder);
            stringBuilder.setLength(0);
            stringBuilder.append(resources.getString(R.string.accountFrom)).append(object.getAccountFrom().getName());
            from.setText(stringBuilder);
            stringBuilder.setLength(0);
            stringBuilder.append(resources.getString(R.string.accountTo)).append(object.getAccountFrom().getName());
            to.setText(stringBuilder);
            stringBuilder.setLength(0);
            stringBuilder.append(resources.getString(R.string.accountBalance)).append(object.getValue());
            howMuch.setText(stringBuilder);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater menuInflater = new MenuInflater(context);
            menuInflater.inflate(R.menu.context_menu, menu);
            for (int i = 0; i< menu.size(); i++) {
                menu.getItem(i).setOnMenuItemClickListener(menuItemClickListener);
            }
        }

        private final MenuItem.OnMenuItemClickListener menuItemClickListener = item -> {
            switch (item.getItemId()) {
                case R.id.delete:
                    mViewClickSubject.accept(mList.get(getLayoutPosition()));
                    break;
            }
            return false;
        };
    }

}
