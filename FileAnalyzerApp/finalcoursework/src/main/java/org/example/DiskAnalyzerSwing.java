package org.example;

import java.io.BufferedReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

/**
 * Класс {@code DiskAnalyzerSwing} представляет графический интерфейс для анализа файловой системы.
 */
public class DiskAnalyzerSwing extends JFrame {
    private static final Logger logger = LogManager.getLogger(DiskAnalyzerSwing.class);

    private final JTree tree;
    private final JTextArea fileInfoTextArea;

    /**
     * Создает новый экземпляр класса {@code DiskAnalyzerSwing}.
     */
    public DiskAnalyzerSwing() {
        super("Disk Analyzer");

        // Инициализация компонентов интерфейса
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Выберите папку");
        tree = new JTree(root);
        fileInfoTextArea = new JTextArea();
        fileInfoTextArea.setEditable(false);

        // Добавление слушателя событий выбора узла в дереве
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    File selectedFile = (File) selectedNode.getUserObject();
                    displayFileInfo(selectedFile);
                }
            }
        });

        // Создание компонентов для прокрутки дерева и текстовой области
        JScrollPane treeScrollPane = new JScrollPane(tree);
        JScrollPane textAreaScrollPane = new JScrollPane(fileInfoTextArea);

        // Создание панели разделения для размещения дерева и текстовой области
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, textAreaScrollPane);
        splitPane.setDividerLocation(250);

        // Добавление панели разделения на главное окно
        add(splitPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        logger.info("Создан экземпляр DiskAnalyzerSwing.");
    }

    /**
     * Отображает информацию о файле в текстовой области.
     *
     * @param file Файл, информацию о котором нужно отобразить.
     */
    private void displayFileInfo(File file) {
        fileInfoTextArea.setText(FileAnalyzer.getFileInfo(file));
        logger.info("Отображена информация о файле: {}", file.getAbsolutePath());
    }

    /**
     * Заполняет дерево файловой системы, начиная с указанной директории.
     *
     * @param rootDirectory Корневая директория для построения дерева.
     */
    public void populateTree(File rootDirectory) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootDirectory);
        populateNode(root);
        tree.setModel(new JTree(root).getModel());
        logger.info("Дерево файловой системы заполнено для директории: {}", rootDirectory.getAbsolutePath());
    }

    /**
     * Рекурсивно заполняет узлы дерева файловой системы.
     *
     * @param parentNode Родительский узел, в который добавляются дочерние узлы.
     */
    private void populateNode(DefaultMutableTreeNode parentNode) {
        File parentFile = (File) parentNode.getUserObject();
        File[] files = parentFile.listFiles();
        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file);
                parentNode.add(newNode);

                if (file.isDirectory()) {
                    populateNode(newNode);
                }
            }
        }
    }
}
