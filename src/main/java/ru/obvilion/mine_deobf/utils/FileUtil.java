package ru.obvilion.mine_deobf.utils;

import ru.obvilion.mine_deobf.Deobfuscator;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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

    public static void copyResources(File file, File out_dir) {
        if (file.isDirectory()) {
            try {
                Files.find(file.toPath(),
                    Integer.MAX_VALUE,
                    (filePath, fileAttr) -> fileAttr.isRegularFile())
                    .forEach(path -> {
                        if (path.toFile().isDirectory()) return;
                        if (path.toFile().getPath().endsWith(".java") || path.toFile().getPath().endsWith(".class")) return;
                        if (path.toFile().getPath().contains("META-INF/")) return;

                        System.out.println("Copying resource " + path.getFileName());

                        try {
                            File targetFile = new File(out_dir, path.toFile().getPath().replace(file.getPath(), ""));
                            targetFile.getParentFile().mkdirs();
                            targetFile.createNewFile();

                            try (InputStream in = new BufferedInputStream(new FileInputStream(path.toFile()));
                                 OutputStream out = new BufferedOutputStream(new FileOutputStream(targetFile))) {

                                byte[] buffer = new byte[1024];
                                int lengthRead;
                                while ((lengthRead = in.read(buffer)) > 0) {
                                    out.write(buffer, 0, lengthRead);
                                    out.flush();
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("Error on copying resources!");
                            e.printStackTrace();
                        }
                    });
            } catch (Exception e) {
                System.err.println("Error on copying resources!");
                e.printStackTrace();
            }

            return;
        }

        try {
            ZipFile zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.endsWith(".class") || name.endsWith("/") || name.endsWith(".java")) continue;
                if (name.startsWith("META-INF/")) continue;

                System.out.println("Copying resource " + name);

                File targetFile = new File(out_dir, name);
                targetFile.getParentFile().mkdirs();
                targetFile.createNewFile();

                InputStream stream = zipFile.getInputStream(entry);
                OutputStream outStream = new FileOutputStream(targetFile);

                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = stream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                stream.close();
                outStream.close();
            }
        } catch (Exception e) {
            System.out.println("Error on copy resources from jar file!");
            e.printStackTrace();
        }
    }
}
