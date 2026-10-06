package org.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    public static List<Transaction> readTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            InputStream inputStream;
            if (filePath.startsWith("http://") || filePath.startsWith("https://")) {
                inputStream = URI.create(filePath).toURL().openStream();
            } else {
                inputStream = new FileInputStream(filePath);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] values = line.split(",");
                    if (values.length < 3) {
                        continue;
                    }

                    if (!values[0].matches(".*\\d.*")) {
                        continue;
                    }
                    Transaction transaction = new Transaction(
                            values[0].trim(),
                            Double.parseDouble(values[1].trim()),
                            values[2].trim()
                    );
                    transactions.add(transaction);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
        return transactions;
    }
}