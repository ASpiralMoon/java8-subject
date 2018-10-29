package com.ts;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    static class Trader {
        private final String name;
        private final String city;

        Trader(String name, String city) {
            this.name = name;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "Trader{" +
                    "name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }

    static class Transaction {
        private final Trader trader;
        private final int year;
        private final int value;

        Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return trader;
        }

        public int getYear() {
            return year;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "trader=" + trader +
                    ", year=" + year +
                    ", value=" + value +
                    '}';
        }
    }

    public static void main(String[] args) {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        System.out.println("1. Find all transactions in the year 2011 and sort them by value (small to high)\n");
        transactions
                .stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue)/*.reversed()*/)
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println("--------------------------------------\n");

        System.out.println("2. What are all the unique cities where the traders work?\n");
        Map<String, Long> countMap =
                transactions
                        .stream()
                        .map(Transaction::getTrader)
                        .distinct()
                        .map(Trader::getCity)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        if (countMap.containsValue(1L)) {
            countMap.forEach((k, v) -> {
                if (v == 1L) {
                    System.out.println(k + "\r");
                }
            });
        } else {
            System.out.println("不存在符合条件的城市.");
        }
        System.out.println("--------------------------------------\n");

        System.out.println("3. Find all traders from Cambridge and sort them by name.\n");
        List<Trader> traders =
                transactions
                        .stream()
                        .map(Transaction::getTrader)
                        .filter(trader -> "Cambridge".equals(trader.getCity()))
                        .distinct()
                        .sorted(Comparator.comparing(Trader::getName))
                        .collect(Collectors.toList());
        traders.forEach(System.out::println);
        System.out.println("--------------------------------------\n");

        System.out.println("4. Return a string of all traders’ names sorted alphabetically.\n");
        traders.stream().map(Trader::getName).sorted(/*Comparator.reverseOrder()*/).forEach(System.out::println);
        System.out.println("--------------------------------------\n");

        System.out.println("5. Are any traders based in Milan?\n");
        System.out.println(
                transactions
                        .stream()
                        .map(Transaction::getTrader)
                        .anyMatch(trader -> "Milan".equals(trader.getCity()))
        );
        System.out.println(
                transactions
                        .stream()
                        .map(Transaction::getTrader)
                        .filter(trader -> "Milan".equals(trader.getCity()))
                        .findAny());
        System.out.println("--------------------------------------\n");

        System.out.println("6. Print all transactions’ values from the traders living in Cambridge.\n");
        transactions
                .stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);
        System.out.println("--------------------------------------\n");

        System.out.println("7. What’s the highest value of all the transactions?\n");
        System.out.println(
                transactions
                        .stream()
                        .map(Transaction::getValue)
                        .reduce(Integer::max)
                        .get());
        System.out.println("--------------------------------------\n");

        System.out.println("8. Find the transaction with the smallest value.\n");
        System.out.println(
                transactions
                        .stream()
                        .min(Comparator.comparing(Transaction::getValue)));
        System.out.println("--------------------------------------\n");
    }

}
