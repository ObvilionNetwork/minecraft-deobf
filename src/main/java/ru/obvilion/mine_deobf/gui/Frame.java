package ru.obvilion.mine_deobf.gui;

import ru.obvilion.mine_deobf.Decompiler;
import ru.obvilion.mine_deobf.Deobfuscator;
import ru.obvilion.mine_deobf.Vars;
import ru.obvilion.mine_deobf.utils.DualStream;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    String[] encodings = { "Default", "utf-8", "us-ascii", "windows-1250", "windows-1252", "koi8-r", "shift_jis", "iso-2022-jp", "euc-jp" };
    String[] versions = { "1.7.10" };

    public JButton select_file = new JButton("Select file...");
    public JButton start = new JButton("Decompile sources");
    public JTextField file_path = new JTextField("Path to file...");
    public JComboBox encoding_selector = new JComboBox(encodings);
    public JComboBox version_selector = new JComboBox(versions);
    public JTextArea logs = new JTextArea("[Main thread] Welcome to Oblivion Decompiler!");
    public JScrollPane logs_pane = new JScrollPane(logs);

    public JFileChooser chooser = new JFileChooser();

    public Frame() throws HeadlessException {
        super();

        setSize(Vars.WINDOW_WIDTH, Vars.WINDOW_HEIGHT);
        setTitle(Vars.WINDOW_NAME);

        setResizable(false);
        setLayout(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null);

        select_file.setBounds(360,5,100,25);
        file_path.setBounds(15, 5, 337, 26);
        start.setBounds(15, 360, 180, 30);
        logs_pane.setBounds(15, 40, 655, 305);
        encoding_selector.setBounds(470, 5, 100, 25);
        version_selector.setBounds(578, 5, 90, 25);
        logs.setEditable(false);

        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        select_file.addActionListener(e -> {
            int ret = chooser.showDialog(null, "Open .jar file or directory");
            if (ret == JFileChooser.APPROVE_OPTION) {
                Vars.SELECTED_FILE = chooser.getSelectedFile();
                System.out.println("Selected " + Vars.SELECTED_FILE.getPath() + " file for decompilation");

                file_path.setText(Vars.SELECTED_FILE.getPath());
            }
        });
        start.addActionListener(e -> {
            new Thread(() -> {
                start.setEnabled(false);

                if (Vars.SELECTED_FILE == null || !Vars.SELECTED_FILE.exists()) {
                    System.out.println("File or sources dir is not selected!");
                    start.setEnabled(true);
                    return;
                }

                System.out.println("Started decompiler...");
                System.out.println("Selected " + Vars.SELECTED_VERSION + " minecraft version");

                DualStream.prefix = "[CSV Loader] ";

                Deobfuscator.init_csv();
                System.out.println("Csv initialisation ended (total " + Deobfuscator.names.size() + " values)");

                DualStream.prefix = "[CFR Decompiler] ";

                if (Vars.SELECTED_FILE.isFile()) {
                    System.out.println("Decompiler started...");
                    Decompiler.start();
                    System.out.println("Decompiler work ended...");
                } else {
                    System.out.println("You are selected folder, not a .jar, skipping decompilation...");
                }

                DualStream.prefix = "[Deobfuscator] ";

                System.out.println("Deobfuscator started...");
                Deobfuscator.deobfuscate();
                System.out.println("Deobfuscator work ended!");

                DualStream.prefix = "[Main thread] ";

                System.out.println("Work is finished, you can find sources in the folders:");
                System.out.println("  - " + Vars.DEOBF_DIR.getPath());

                if (Vars.DECOMP_DIR != null) {
                    System.out.println("  - " + Vars.DECOMP_DIR.getPath());
                }

                System.out.println("Thanks for using \"Oblivion Forge mods decompiler\" software :З");
                System.out.println("Discord support server: https://discord.gg/cg82mjh");

                start.setEnabled(true);

                Thread.currentThread().interrupt();
            }).start();
        });

        encoding_selector.addActionListener(e -> {
            String sel = (String) encoding_selector.getSelectedItem();

            if (sel.equals("Default")) {
                Vars.SELECTED_ENCODING = null;
                return;
            }

            Vars.SELECTED_ENCODING = sel;
        });

        add(encoding_selector);
        add(version_selector);
        add(select_file);
        add(file_path);
        add(start);
        add(logs_pane);
    }
}
