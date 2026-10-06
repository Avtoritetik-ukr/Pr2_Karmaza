package org.example;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class TransactionReportGenerator {

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("\n10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printPeriodExpensesReport(Optional<Transaction> maxExpense, Optional<Transaction> minExpense, String period) {
        System.out.println("\n--- Аналіз витрат за період (" + period + ") ---");
        maxExpense.ifPresentOrElse(
                t -> System.out.println("Найбільша витрата: " + t.getDescription() + " (" + t.getAmount() + " грн, " + t.getDate() + ")"),
                () -> System.out.println("Витрат за вказаний період не знайдено")
        );
        minExpense.ifPresent(
                t -> System.out.println("Найменша витрата: " + t.getDescription() + " (" + t.getAmount() + " грн, " + t.getDate() + ")")
        );
    }

    public static void printCategoryExpensesReport(Map<String, Double> categoryExpenses) {
        System.out.println("\n--- Витрати по категоріях (1 '*' = 1000 грн) ---");
        for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
            int starsCount = (int) (entry.getValue() / 1000);
            String stars = "*".repeat(Math.max(0, starsCount));
            System.out.printf("%-20s: %10.2f грн | %s%n", entry.getKey(), entry.getValue(), stars);
        }
    }

    public static void printMonthlyExpensesReport(Map<String, Double> monthlyExpenses) {
        System.out.println("\n--- Витрати по місяцях (1 '*' = 1000 грн) ---");
        for (Map.Entry<String, Double> entry : monthlyExpenses.entrySet()) {
            int starsCount = (int) (entry.getValue() / 1000);
            String stars = "*".repeat(Math.max(0, starsCount));
            System.out.printf("%-10s: %10.2f грн | %s%n", entry.getKey(), entry.getValue(), stars);
        }
    }
}