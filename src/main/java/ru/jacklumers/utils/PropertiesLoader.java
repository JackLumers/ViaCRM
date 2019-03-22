package ru.jacklumers.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс для получения файла настроек
 */
public final class PropertiesLoader {
    /**
     * Загружает выбранный по имени файл с настройками (.properties)
     *
     * @param fileName                 - имя файла
     * @param classesDirectoryRealPath - действительный путь до директории с компилированными классами
     * @return Properties - класс, представляющий настройки в формате "ключ-значение"
     * @throws IllegalStateException при неудачной загрузке файла
     * @see Properties
     */
    public static Properties loadProperties(String fileName, String classesDirectoryRealPath) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(classesDirectoryRealPath + "/" + fileName));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return properties;
    }
}
