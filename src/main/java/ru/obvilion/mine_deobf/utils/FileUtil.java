package ru.obvilion.mine_deobf.utils;

import ru.obvilion.mine_deobf.Deobfuscator;

import java.io.*;

public class FileUtil {
    public static void replaceInFile(File file, File out_file) {
        String originalFileContent = "";

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(file));

            String currentReadingLine = reader.readLine();
            while (currentReadingLine != null) {
                originalFileContent += currentReadingLine + System.lineSeparator();
                currentReadingLine = reader.readLine();
            }

            for (int i = 0; i < Deobfuscator.names.size(); i++) {
                originalFileContent = originalFileContent.replaceAll(Deobfuscator.names.get(i),
                        Deobfuscator.values.get(i));
            }

            out_file.getParentFile().mkdirs();

            writer = new BufferedWriter(new FileWriter(out_file));
            writer.write(originalFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
