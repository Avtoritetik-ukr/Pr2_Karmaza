package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate(), DATE_FORMATTER);
            String transactionMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static Optional<Transaction> findMaxExpenseInPeriod(List<Transaction> transactions, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return !date.isBefore(startDate) && !date.isAfter(endDate);
                })
                .min(Comparator.comparing(Transaction::getAmount));
    }

    public static Optional<Transaction> findMinExpenseInPeriod(List<Transaction> transactions, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return !date.isBefore(startDate) && !date.isAfter(endDate);
                })
                .max(Comparator.comparing(Transaction::getAmount));
    }

    public static Map<String, Double> calculateExpensesByCategory(List<Transaction> transactions) {
        Map<String, Double> categoryExpenses = new LinkedHashMap<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                categoryExpenses.merge(t.getDescription(), Math.abs(t.getAmount()), Double::sum);
            }
        }
        return categoryExpenses;
    }

    public static Map<String, Double> calculateExpensesByMonth(List<Transaction> transactions) {
        Map<String, Double> monthlyExpenses = new TreeMap<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                String monthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
                monthlyExpenses.merge(monthYear, Math.abs(t.getAmount()), Double::sum);
            }
        }
        return monthlyExpenses;
    }
}