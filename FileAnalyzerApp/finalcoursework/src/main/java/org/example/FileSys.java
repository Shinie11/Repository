package org.example;
import java.io.BufferedReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.io.File;

/**
 * Главный класс приложения, содержащий точку входа.
 */
public class FileSys {
    private static final Logger logger = LogManager.getLogger(FileSys.class);

    /**
     * Точка входа в приложение.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        logger.info("Запуск приложения.");

        // Запуск приложения в потоке событий Swing
        SwingUtilities.invokeLater(() -> {
            logger.info("Создание главного окна приложения.");
            // Создание экземпляра главного окна приложения
            DiskAnalyzerSwing diskAnalyzer = new DiskAnalyzerSwing();
            // Установка видимости главного окна
            diskAnalyzer.setVisible(true);

            logger.info("Создание диалогового окна выбора папки.");
            // Создание диалогового окна выбора папки
            JFileChooser fileChooser = new JFileChooser();
            // Установка режима выбора только папок
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            logger.info("Отображение диалогового окна.");
            // Отображение диалогового окна и получение результата
            int result = fileChooser.showOpenDialog(diskAnalyzer);
            // Если пользователь выбрал папку и подтвердил выбор
            if (result == JFileChooser.APPROVE_OPTION) {
                // Получение выбранной директории
                File selectedDirectory = fileChooser.getSelectedFile();
                logger.info("Выбранная директория: {}", selectedDirectory.getAbsolutePath());
                // Заполнение дерева в главном окне информацией о файловой структуре
                diskAnalyzer.populateTree(selectedDirectory);
            }
            diskAnalyzer.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    onWindowClosing(diskAnalyzer);
                }
            });

        });


    }
    /**
     * Обработка события закрытия окна.
     *
     * @param diskAnalyzer Главное окно приложения.
     */
    private static void onWindowClosing(DiskAnalyzerSwing diskAnalyzer) {
        logger.info("Закрытие приложения.");
        // Дополнительные действия перед закрытием приложения, если необходимо
        // Например, сохранение данных или другие завершающие операции
        System.exit(0);
    }
}
