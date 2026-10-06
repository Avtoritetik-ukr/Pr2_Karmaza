package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TransactionAnalyzerTest {

    @Test
    public void testCalculateTotalBalance() {
        Transaction transaction1 = new Transaction("2023-01-01", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("2023-01-02", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("2023-01-03", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        double result = TransactionAnalyzer.calculateTotalBalance(transactions);

        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    @Test
    public void testCountTransactionsByMonth() {
        Transaction transaction1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        int countFeb = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023");

        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
    }

    @Test
    public void testFindTopExpenses() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2024", -100.0, "Кава"),
                new Transaction("02-01-2024", -5000.0, "Оренда"),
                new Transaction("03-01-2024", 8000.0, "Зарплата"),
                new Transaction("04-01-2024", -1200.0, "Сільпо"),
                new Transaction("05-01-2024", -300.0, "Аптека")
        );

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(4, topExpenses.size(), "Має бути відібрано 4 витрати");
        Assertions.assertEquals(-5000.0, topExpenses.get(0).getAmount(), "Першою має бути найбільша витрата (-5000)");
        Assertions.assertEquals(-100.0, topExpenses.get(3).getAmount(), "Останньою має бути найменша (-100)");
    }

    @Test
    public void testFindMaxAndMinExpenseInPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("14-12-2023", -3200.0, "Оренда"),
                new Transaction("20-12-2023", -750.0, "Подарунки"),
                new Transaction("05-01-2024", -2000.0, "Січень")
        );

        LocalDate start = LocalDate.of(2023, 12, 1);
        LocalDate end = LocalDate.of(2023, 12, 31);

        Optional<Transaction> maxExp = TransactionAnalyzer.findMaxExpenseInPeriod(transactions, start, end);
        Optional<Transaction> minExp = TransactionAnalyzer.findMinExpenseInPeriod(transactions, start, end);

        Assertions.assertTrue(maxExp.isPresent());
        Assertions.assertEquals(-3200.0, maxExp.get().getAmount(), "Найбільша витрата за грудень має бути -3200");

        Assertions.assertTrue(minExp.isPresent());
        Assertions.assertEquals(-450.0, minExp.get().getAmount(), "Найменша витрата за грудень має бути -450");
    }

    @Test
    public void testReadTransactions() {
        List<Transaction> transactions = TransactionCSVReader.readTransactions("pr2.csv");
        Assertions.assertNotNull(transactions, "Список транзакцій не повинен бути null");
        Assertions.assertFalse(transactions.isEmpty(), "Файл повинен містити транзакції");
    }
}