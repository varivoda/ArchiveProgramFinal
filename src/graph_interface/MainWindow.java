package graph_interface;

import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by root on 10.12.14.
 */
public class MainWindow extends JFrame {

    private JButton archiveButton;
    private JButton dearchiveButton;
    private JLabel label;


    public MainWindow() {

        //создание окна
        super("Archive Program");
        this.setVisible(true);
        setBounds(100,100,400,300);

        //создание компонентов
        dearchiveButton = new JButton("Dearchive");
        archiveButton = new JButton("Archive");
        label = new JLabel("What do you want to do?");

        //расположение компонентов
        setLayout(new BorderLayout(5,5));
        add(label, BorderLayout.NORTH);
        add(archiveButton, BorderLayout.WEST);
        add(dearchiveButton, BorderLayout.EAST);

        this.pack();

        //инициализация ActionListeners
        archiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArchiveWindow archiveWindow = new ArchiveWindow();
                archiveWindow.pack();
                dispose();
            }
        });

        dearchiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DearchiveWindow dearchiveWindow = new DearchiveWindow();
                dearchiveWindow.pack();
                dispose();
            }
        });

        //закрытие программы
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
