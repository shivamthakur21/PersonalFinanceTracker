package com.example.personalfinancetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.text.NumberFormat;
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactions;
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private NumberFormat currencyFormat;
    private OnTransactionLongClickListener longClickListener;

    public TransactionAdapter(Context context, List<Transaction> transactions, OnTransactionLongClickListener longClickListener) {
        this.context = context;
        this.transactions = transactions;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        this.longClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.tvAmount.setText(currencyFormat.format(transaction.getAmount()));
        holder.tvCategory.setText(transaction.getCategory());
        holder.tvDescription.setText(transaction.getDescription());
        holder.tvDate.setText(dateFormat.format(transaction.getDate()));

        if (transaction.isIncome()) {
            holder.tvAmount.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.tvAmount.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }

        // Set long click listener
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onTransactionLongClick(transaction);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmount, tvCategory, tvDescription, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    public interface OnTransactionLongClickListener {
        void onTransactionLongClick(Transaction transaction);
    }
}
