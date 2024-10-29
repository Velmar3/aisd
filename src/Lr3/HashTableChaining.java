package Lr3;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;

public class HashTableChaining {
    private LinkedList<Integer>[] hashTable;
    private static final int TABLE_SIZE = 10;
    private boolean isTableFilled = false; // Флаг для проверки наполненности таблицы

    // Создать хеш-таблицу с заданным размером.
    public HashTableChaining() {
        hashTable = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            hashTable[i] = new LinkedList<>();
        }
    }

    // Хеш-функция вычисляет индекс для заданного ключа.
    private int hashFunction(int key) {
        return key % TABLE_SIZE;
    }

    // Вставляет ключ в хеш-таблицу.
    public void insert(int key) {
        int hashIndex = hashFunction(key);
        hashTable[hashIndex].add(key);
    }

    // Поиск ключа в хеш-таблице.
    public boolean search(int key) {
        int hashIndex = hashFunction(key);
        return hashTable[hashIndex].contains(key);
    }

    // Отображает содержимое хеш-таблицы.
    public void displayHashTable() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            System.out.print("Index " + i + ": ");
            for (Integer key : hashTable[i]) {
                System.out.print(key + " -> ");
            }
            System.out.println("null");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        HashTableChaining hashTable = new HashTableChaining();
        int[] array = new int[8];
        int choice = 0;

        do {
            System.out.println("\nМеню:");
            System.out.println("1. Ввод данных пользователем");
            System.out.println("2. Автоматический ввод");
            System.out.println("3. Поиск элемента");
            System.out.println("4. Выход");

            // Проверка корректного ввода числа для выбора пункта меню
            do {
                System.out.print("Выберите пункт меню: ");
                String input = scanner.nextLine();

                // Проверяем, что введённое значение состоит только из одной цифры от 1 до 4
                if (input.matches("[1-4]")) {
                    choice = Integer.parseInt(input);
                } else {
                    System.out.println("Ошибка: введите число от 1 до 4.");
                    choice = 0; // Сбрасываем choice, чтобы повторить ввод
                }
            } while (choice < 1 || choice > 4);

            switch (choice) {
                case 1:
                    System.out.println("Введите 8 целых чисел в диапазоне от 53000 до 78000:");
                    for (int i = 0; i < array.length; i++) {
                        do {
                            while (!scanner.hasNextInt()) {
                                System.out.println("Ошибка: введите целое число.");
                                scanner.next();
                            }
                            array[i] = scanner.nextInt();
                            if (array[i] < 53000 || array[i] > 78000) {
                                System.out.println("Число вне диапазона. Попробуйте снова.");
                            }
                        } while (array[i] < 53000 || array[i] > 78000);
                        hashTable.insert(array[i]);
                    }
                    hashTable.isTableFilled = true;  // Отметить, что таблица заполнена
                    scanner.nextLine(); // Очистка буфера
                    break;

                case 2:
                    for (int i = 0; i < array.length; i++) {
                        array[i] = 53000 + random.nextInt(25001);
                        hashTable.insert(array[i]);
                    }
                    hashTable.isTableFilled = true;
                    break;

                case 3:
                    // Проверка, заполнена ли таблица перед поиском
                    if (!hashTable.isTableFilled) {
                        System.out.println("Сначала необходимо заполнить хеш-таблицу.");
                    } else {
                        int searchKey;
                        do {
                            System.out.println("Введите число для поиска (в диапазоне от 53000 до 78000):");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Ошибка: введите целое число.");
                                scanner.next();
                            }
                            searchKey = scanner.nextInt();
                            if (searchKey < 53000 || searchKey > 78000) {
                                System.out.println("Число вне диапазона. Попробуйте снова.");
                            }
                        } while (searchKey < 53000 || searchKey > 78000);
                        boolean found = hashTable.search(searchKey);
                        System.out.println("Результат поиска: " + (found ? "Найдено" : "Не найдено"));
                    }
                    scanner.nextLine(); // Очистка буфера
                    break;

                case 4:
                    System.out.println("Выход из программы.");
                    return;
            }

            // Выводим исходный массив и хеш-таблицу только если не было поиска
            if (choice != 3) {
                System.out.print("\nИсходный массив: ");
                for (int num : array) {
                    System.out.print(num + " ");
                }
                System.out.println("\n\nХеш-таблица:");
                hashTable.displayHashTable();
            }

        } while (choice != 4);
    }
}