package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        String filePath = "pr2.csv";

        // Статичний виклик читання даних
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        // 1. Загальний баланс
        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        // 2. Кількість транзакцій за місяць
        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        // 3. Топ-10 витрат
        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        // 4. Найбільша та найменша витрата за грудень 2023
        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Optional<Transaction> maxExpense = TransactionAnalyzer.findMaxExpenseInPeriod(transactions, startDate, endDate);
        Optional<Transaction> minExpense = TransactionAnalyzer.findMinExpenseInPeriod(transactions, startDate, endDate);
        TransactionReportGenerator.printPeriodExpensesReport(maxExpense, minExpense, "12-2023");

        // 5. Витрати за категоріями
        Map<String, Double> categoryExpenses = TransactionAnalyzer.calculateExpensesByCategory(transactions);
        TransactionReportGenerator.printCategoryExpensesReport(categoryExpenses);

        // 6. Витрати по місяцях
        Map<String, Double> monthlyExpenses = TransactionAnalyzer.calculateExpensesByMonth(transactions);
        TransactionReportGenerator.printMonthlyExpensesReport(monthlyExpenses);
    }
}