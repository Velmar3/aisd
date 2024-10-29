package Kr2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dictionary dictionary = new Dictionary("dictionary.txt");
        int choice = 0;

        do {
            System.out.println("\nМеню:");
            System.out.println("1. Ввод данных пользователем");
            System.out.println("2. Автоматический ввод");
            System.out.println("3. Просмотреть словарь");
            System.out.println("4. Удалить ключ");
            System.out.println("5. Поиск ключа");
            System.out.println("6. Выход");
            System.out.print("Выберите метод ввода: ");
            String input = scanner.nextLine();

            if (!input.matches("[1-6]")) {
                System.out.println("Неверный выбор. Пожалуйста, введите число от 1 до 6.");
                continue;
            }

            choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    System.out.print("Введите ключ: ");
                    String userKey = scanner.nextLine();
                    System.out.print("Введите значение: ");
                    String userValue = scanner.nextLine();
                    dictionary.insert(userKey, userValue);
                    break;

                case 2:
                    for (int i = 0; i < 5; i++) {
                        String key = "key" + (i + 1);
                        String value = "value" + (i + 1);
                        dictionary.insert(key, value);
                    }
                    break;

                case 3:
                    System.out.println("Содержимое словаря:");
                    dictionary.display();
                    break;

                case 4:
                    System.out.print("Введите ключ для удаления: ");
                    String keyToDelete = scanner.nextLine();
                    boolean isDeleted = dictionary.delete(keyToDelete);
                    if (isDeleted) {
                        System.out.println("Ключ: " + keyToDelete + " удален!");
                    } else {
                        System.out.println("Ключ не найден.");
                    }
                    break;

                case 5:
                    System.out.print("Введите ключ для поиска: ");
                    String keyToSearch = scanner.nextLine();
                    String result = dictionary.search(keyToSearch);
                    if (result != null) {
                        System.out.println("Найдено: " + result);
                    } else {
                        System.out.println("Запись не найдена.");
                    }
                    break;

                case 6:
                    System.out.println("Выход из программы.");
                    break;

                default:
                    System.out.println("Неверный выбор. Пожалуйста, введите число от 1 до 6.");
                    continue;
            }
        } while (choice != 6);
        scanner.close();
    }
}