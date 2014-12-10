package graph_interface;

import huffman.ArchivatorHuffman;
import interfaces.Archivator;
import lzw.ArchivatorLZW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by root on 10.12.14.
 */
public class ArchiveWindow extends JFrame {

    private JButton chooseFileButton;
    private JButton chooseSaveDirectoryButton;
    private JButton archiveButton;
    private JTextField fileNameField;
    private JTextField saveDirectoryField;
    private JLabel chooseMethodLabel;
    private JComboBox<String> methodComboBox;
//    private JCheckBox theSameDirectoryCheckBox;


    public ArchiveWindow() {

        //создание окна
        super("Archiving");
        this.setVisible(true);
        setBounds(100,100,400,300);

        //создание компонентов
        fileNameField = new JTextField(20);
        saveDirectoryField = new JTextField(20);
        chooseFileButton = new JButton("Choose file");
        chooseSaveDirectoryButton = new JButton("Choose save directory");
        archiveButton = new JButton("Archive");
        chooseMethodLabel = new JLabel("Choose method");
        String[] methodList = {
                "Huffman",
                "LWZ",
                "Элемент списка 3"
        };
        methodComboBox = new JComboBox<>(methodList);
//        theSameDirectoryCheckBox = new JCheckBox("Save archive to the same directory");

        //расположение компонентов
        JPanel panel = new JPanel(new GridLayout(3,2,10,10));
        panel.add(fileNameField);
        panel.add(chooseFileButton);
        panel.add(methodComboBox);
        panel.add(chooseMethodLabel);
        panel.add(saveDirectoryField);
        panel.add(chooseSaveDirectoryButton);
        add(panel, BorderLayout.CENTER);
        add(archiveButton, BorderLayout.SOUTH);
//        panel.add(theSameDirectoryCheckBox);
        this.pack();

        //инициализация ActionListeners
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //диалог выбора директории
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
                fileChooser.showSaveDialog(null);

                try {
                    File file = fileChooser.getSelectedFile();
                    String fileName = file.getPath();
                    fileNameField.setText(fileName);
                } catch (NullPointerException exc) {

                }
            }
        });

/*        theSameDirectoryCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean enabled = false;
                        enabled = !theSameDirectoryCheckBox.isSelected();
                chooseSaveDirectoryButton.setEnabled(enabled);
                saveDirectoryField.setEnabled(enabled);
            }
        });*/

        chooseSaveDirectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //диалог выбора директории
                JFileChooser directoryChooser = new JFileChooser();
                directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                directoryChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                directoryChooser.showSaveDialog(null);

                try {
                    File file = directoryChooser.getSelectedFile();
                    String directoryName = file.getPath();
                    saveDirectoryField.setText(directoryName+File.separator);
                } catch (NullPointerException exc) {

                }
            }
        });

        archiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = fileNameField.getText();
                String saveDirectory = saveDirectoryField.getText();
                String method = (String)methodComboBox.getSelectedItem();
                Archivator archivator;

                switch (method) {
                    case "Huffman":
                        archivator = new ArchivatorHuffman();
                        break;
                    case "LZW":
                        archivator = new ArchivatorLZW();
                        break;
                }

                try {
                    archivator = new ArchivatorHuffman();
                    archivator.archiving(fileName, saveDirectory);
                    JOptionPane.showMessageDialog(null, "Archivating is successful!");
                } catch (IOException exc){
                    JOptionPane.showMessageDialog(null, "File isn't existing!");
                }
            }
        });

        //закрытие программы
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
