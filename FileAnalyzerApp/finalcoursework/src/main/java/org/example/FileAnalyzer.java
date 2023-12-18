package org.example;
import java.io.BufferedReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * Класс {@code FileAnalyzer} предоставляет методы для анализа информации о файлах.
 */
public class FileAnalyzer {
    private static final Logger logger = LogManager.getLogger(FileAnalyzer.class);

    /**
     * Получает информацию о заданном файле.
     *
     * @param file Файл для анализа.
     * @return Строка с отформатированной информацией о файле.
     */
    public static String getFileInfo(File file) {
        StringBuilder fileInfo = new StringBuilder();
        if (file.exists()) {
            fileInfo.append("Имя: ").append(file.getName()).append("\n");
            fileInfo.append("Путь: ").append(file.getAbsolutePath()).append("\n");
            fileInfo.append("Объем: ").append(getFormattedSize(file)).append("\n");
            fileInfo.append("Дата создания: ").append(new java.util.Date(file.lastModified())).append("\n");
            fileInfo.append("Создан: ").append(getFileOwner(file)).append("\n");

            logger.info("Информация о файле получена: {}", file.getAbsolutePath());
        } else {
            fileInfo.append("Файл не существует.");
            logger.warn("Попытка получить информацию о несуществующем файле: {}", file.getAbsolutePath());
        }

        return fileInfo.toString();
    }

    /**
     * Форматирует размер файла в удобочитаемом формате (например, КБ, МБ).
     *
     * @param file Файл, размер которого нужно отформатировать.
     * @return Строка, представляющая отформатированный размер файла.
     */
    private static String getFormattedSize(File file) {
        long bytes = file.length();
        String[] units = {"B", "KB", "MB", "GB", "TB"};

        int unitIndex = 0;
        while (bytes > 1024 && unitIndex < units.length - 1) {
            bytes /= 1024;
            unitIndex++;
        }

        return bytes + " " + units[unitIndex];
    }

    /**
     * Получает владельца файла.
     *
     * @param file Файл, владельца которого нужно получить.
     * @return Владелец файла.
     */
    private static String getFileOwner(File file) {
        return FileSystemView.getFileSystemView().getSystemDisplayName(new File(file.getParent()));
    }
}
