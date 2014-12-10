package graph_interface;

import huffman.ArchivatorHuffman;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

/**
 * Created by Иван on 09.12.2014.
 */
public class ArchiveFrame extends JFrame {

    public ArchiveFrame(){

        super("Archivator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JButton archiveButton = new JButton("Archive");
        archiveButton.setAlignmentX(CENTER_ALIGNMENT);
        archiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "archive file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    ArchivatorHuffman archivator = new ArchivatorHuffman();
                    try {
                        archivator.archiving(file, file.getParent() + File.separator);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(fileopen, "Done");

                }
            }
        });

        JButton deArchButton = new JButton("Dearchive");
        deArchButton.setAlignmentX(CENTER_ALIGNMENT);
        deArchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "deArchive file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    ArchivatorHuffman archivator = new ArchivatorHuffman();
                    try {
                        archivator.deArchiving(file, file.getParent() + File.separator);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(fileopen, "Done");
                }

            }
        });
        panel.add(Box.createGlue());
        panel.add((archiveButton));
        panel.add(Box.createVerticalGlue());
        panel.add(deArchButton);
        panel.add(Box.createVerticalGlue());
        getContentPane().add(panel);

        setPreferredSize(new Dimension(200, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
