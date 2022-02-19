package ru.obvilion.mine_deobf.utils;

import ru.obvilion.mine_deobf.Vars;

import java.io.*;

public class Loader {
    public static void onLaunch() {
        Vars.CONFIG_FILE.getParentFile().mkdir();

        if (!Vars.CONFIG_FILE.exists()) {
            return;
        }

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = new FileInputStream(Vars.CONFIG_FILE)) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] content = line.split("=");

                if (content[0].equals("SELECTED_VERSION")) {
                    Vars.SELECTED_VERSION = content[1];
                    continue;
                }

                if (content[0].equals("SELECTED_ENCODING")) {
                    Vars.SELECTED_ENCODING = content[1];
                    continue;
                }

                if (content[0].equals("SELECTED_FILE")) {
                    Vars.SELECTED_FILE = new File(content[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    public static void onSave() {
        BufferedWriter writer = null;

        try {
            Vars.CONFIG_FILE.getParentFile().mkdirs();

            String originalFileContent =
                    (Vars.SELECTED_VERSION == null ? "" : "SELECTED_VERSION=" + Vars.SELECTED_VERSION + "\n") +
                    (Vars.SELECTED_ENCODING == null ? "" : "SELECTED_ENCODING=" + Vars.SELECTED_ENCODING + "\n") +
                    (Vars.SELECTED_FILE == null ? "" : "SELECTED_FILE=" + Vars.SELECTED_FILE.getPath() + "\n");

            writer = new BufferedWriter(new FileWriter(Vars.CONFIG_FILE));
            writer.write(originalFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
