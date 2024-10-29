package Lr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        BTree t = new BTree(5); // Порядок дерева

        do {
            System.out.println("\nМеню:");
            System.out.println("1. Ввод данных пользователем");
            System.out.println("2. Автоматический ввод");
            System.out.println("3. Поиск элемента");
            System.out.println("4. Вставка элемента");
            System.out.println("5. Удаление элемента");
            System.out.println("6. Отображение дерева");
            System.out.println("7. Выход");
            System.out.print("Выберите операцию: ");

            String inputChoice = scanner.nextLine().trim();
            if (!inputChoice.matches("^[1-7]$")) {
                System.out.println("Ошибка: выберите пункт меню от 1 до 7.");
                continue;
            }
            choice = Integer.parseInt(inputChoice);
            List<Integer> numbers = new ArrayList<>();

            switch (choice) {
                case 1:
                    System.out.println("Введите от 10 до 15 целых чисел (завершите ввод нажатием Enter):");
                    int count = 1;
                    while (numbers.size() < 15) {
                        try {
                            System.out.print("Число " + count + ": ");
                            String input = scanner.nextLine();
                            if (input.isEmpty() && numbers.size() >= 10) {
                                break;
                            }
                            if (!input.matches("\\d+")) {
                                System.out.println("Неверный ввод. Введите только целые числа.");
                                continue;
                            }
                            int num = Integer.parseInt(input);
                            numbers.add(num);
                            count++;
                            if (numbers.size() >= 10 && numbers.size() <= 15) {
                                System.out.println("Введите ещё " + (15 - numbers.size()) + " числа или нажмите Enter для завершения.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Неверный ввод. Введите целое число.");
                        }
                    }
                    if (numbers.size() < 10) {
                        System.out.println("Ошибка: введите минимум 10 чисел.");
                        numbers.clear();
                    } else {
                        System.out.println("Исходные значения: " + numbers);
                        for (int num : numbers) {
                            t.insert(num);
                        }
                    }
                    break;

                case 2:
                    for (int i = 0; i < 12; i++) {
                        numbers.add((int) (Math.random() * 100));
                    }
                    System.out.println("Автоматический ввод значений: " + numbers);
                    for (int num : numbers) {
                        t.insert(num);
                    }
                    break;

                case 3:
                    System.out.print("Введите значение для поиска: ");
                    int searchValue = Integer.parseInt(scanner.nextLine());
                    BTreeNode result = t.search(searchValue);
                    if (result != null) {
                        System.out.println("Элемент " + searchValue + " найден.");
                    } else {
                        System.out.println("Элемент " + searchValue + " не найден.");
                    }
                    break;

                case 4:
                    System.out.print("Введите значение для вставки: ");
                    int insertValue = Integer.parseInt(scanner.nextLine());
                    t.insert(insertValue);
                    System.out.println("Элемент " + insertValue + " добавлен.");
                    break;

                case 5:
                    System.out.print("Введите значение для удаления: ");
                    int deleteValue = Integer.parseInt(scanner.nextLine());
                    if (t.delete(deleteValue)) {
                        System.out.println("Элемент " + deleteValue + " удален.");
                    } else {
                        System.out.println("Элемент " + deleteValue + " не найден.");
                    }
                    break;

                case 6:
                    System.out.print("Построенное B-дерево: ");
                    t.traverse();
                    System.out.println();
                    break;

                case 7:
                    System.out.println("Выход из программы.");
                    break;

                default:
                    System.out.println("Ошибка выбора. Повторите ввод.");
            }
        } while (choice != 7);
        scanner.close();
    }
}