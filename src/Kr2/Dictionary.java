package Kr2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class Dictionary {
    private final Map<String, String> entries; // Словарь для хранения пар ключ-значение
    private final String filename; // Имя файла для хранения данных словаря

    // Конструктор класса, принимает имя файла и загружает данные из файла
    public Dictionary(String filename) {
        this.entries = new HashMap<>();
        this.filename = filename;
        loadFromFile(); // Загрузка данных из файла
    }

    // Метод для добавления новой пары ключ-значение в словарь
    public void insert(String key, String value) {
        entries.put(key, value); // Добавляем пару в словарь
        saveToFile(); // Сохраняем изменения в файл
    }

    // Метод для поиска значения по ключу
    public String search(String key) {
        return entries.get(key); // Возвращаем значение по ключу или null, если ключ не найден
    }

    // Метод для удаления записи по ключу
    public boolean delete(String key) {
        if (entries.containsKey(key)) { // Проверяем, существует ли ключ в словаре
            entries.remove(key); // Удаляем запись
            saveToFile(); // Сохраняем изменения в файл
            return true; // Успешное удаление
        } else {
            return false; // Ключ не найден, удаление не удалось
        }
    }

    // Метод для сохранения содержимого словаря в файл
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry entry : entries.entrySet()) { // Проходим по всем записям в словаре
                writer.write(entry.getKey() + ":" + entry.getValue()); // Записываем ключ:значение
                writer.newLine(); // Перевод строки
            }
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage()); // Выводим сообщение об ошибке
        }
    }

    // Метод для загрузки данных из файла в словарь
    private void loadFromFile() {
        File file = new File(filename);
        if (!file.exists()) return; // Проверяем, существует ли файл, если нет — выходим

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) { // Читаем файл построчно
                String[] parts = line.split(":"); // Разделяем строку на ключ и значение
                if (parts.length == 2) {
                    entries.put(parts[0].trim(), parts[1].trim()); // Добавляем в словарь, обрезая пробелы
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке файла: " + e.getMessage()); // Выводим сообщение об ошибке
        }
    }

    // Метод для отображения всех записей в словаре
    public void display() {
        for (Map.Entry entry : entries.entrySet()) { // Проходим по всем записям
            System.out.println(entry); // Выводим запись
        }
    }
}