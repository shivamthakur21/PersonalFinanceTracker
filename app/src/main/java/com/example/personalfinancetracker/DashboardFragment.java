package com.example.personalfinancetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Date;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;
public class DashboardFragment extends Fragment implements TransactionAdapter.OnTransactionLongClickListener {

    private DatabaseHelper dbHelper;
    private EditText etAmount, etCategory, etDescription;
    private Button btnAddTransaction;
    private TextView tvBalance;
    private RecyclerView rvTransactions;
    private TransactionAdapter transactionAdapter;
    private NumberFormat currencyFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        dbHelper = new DatabaseHelper(getContext());
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

        etAmount = view.findViewById(R.id.etAmount);
        etCategory = view.findViewById(R.id.etCategory);
        etDescription = view.findViewById(R.id.etDescription);
        btnAddTransaction = view.findViewById(R.id.btnAddTransaction);
        tvBalance = view.findViewById(R.id.tvBalance);
        rvTransactions = view.findViewById(R.id.rvTransactions);

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaction();
            }
        });

        setupRecyclerView();
        updateDashboard();

        return view;
    }

    private void setupRecyclerView() {
        rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionAdapter = new TransactionAdapter(getContext(), dbHelper.getAllTransactions(), this);
        rvTransactions.setAdapter(transactionAdapter);
    }

    private void addTransaction() {
        String amountStr = etAmount.getText().toString();
        String category = etCategory.getText().toString();
        String description = etDescription.getText().toString();

        if (amountStr.isEmpty() || category.isEmpty()) {
            Toast.makeText(getContext(), "Please enter amount and category", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        boolean isIncome = amount > 0; // Positive amounts are income, negative are expenses

        Transaction transaction = new Transaction(amount, category, description, new Date(), isIncome);
        long id = dbHelper.addTransaction(transaction);

        if (id != -1) {
            Toast.makeText(getContext(), "Transaction added successfully", Toast.LENGTH_SHORT).show();
            etAmount.setText("");
            etCategory.setText("");
            etDescription.setText("");
            updateDashboard();
        } else {
            Toast.makeText(getContext(), "Error adding transaction", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDashboard() {
        List<Transaction> transactions = dbHelper.getAllTransactions();
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        tvBalance.setText("Balance: " + currencyFormat.format(balance));

        transactionAdapter.setTransactions(transactions);
        transactionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTransactionLongClick(Transaction transaction) {
        dbHelper.deleteTransaction(transaction.getId());
        Toast.makeText(getContext(), "Transaction deleted", Toast.LENGTH_SHORT).show();
        updateDashboard();
    }
}
