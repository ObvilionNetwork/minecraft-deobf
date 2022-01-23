package ru.obvilion.mine_deobf;

import ru.obvilion.mine_deobf.utils.FileUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class Deobfuscator {
    public static ArrayList<String> names = new ArrayList<>();
    public static ArrayList<String> values = new ArrayList<>();

    public static void load_csv(String csv_file) {
        System.out.println("Loading list of deobfuscated values from " + csv_file + "...");

        int old = names.size();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(csv_file)) {
            if (is == null) return;

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] content = line.split(",");
                names.add(content[0]);
                values.add(content[1]);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

        System.out.println("Added " + (names.size() - old) + " values");
    }

    public static void init_csv() {
        load_csv("mcp/" + Vars.SELECTED_VERSION + "/fields.csv");
        load_csv("mcp/" + Vars.SELECTED_VERSION + "/methods.csv");
    }

    public static void deobfuscate() {
        try {
            File file = Vars.DECOMP_DIR == null ? Vars.SELECTED_FILE : Vars.DECOMP_DIR;

            String main_path = file.getPath();
            File out_dir = new File(file.getParentFile(), "deobfuscated-sources");
            Vars.DEOBF_DIR = out_dir;

            Files.find(file.toPath(),
                    Integer.MAX_VALUE,
                    (filePath, fileAttr) -> fileAttr.isRegularFile())
                    .forEach(path -> {
                        if (!path.toFile().getPath().endsWith(".java")) return;

                        String ph = path.toFile().getPath().replace(main_path, "");
                        System.out.println("Processing file " + ph + "...");
                        FileUtil.replaceInFile(path.toFile(), new File(out_dir, ph));
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
