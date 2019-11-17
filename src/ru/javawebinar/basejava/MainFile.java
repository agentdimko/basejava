package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : dir.list()) {
                System.out.println(name);
            }
        }
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printDirectoryDeeply(new File("."), 0);
    }


    private static void printDirectoryDeeply(File myFile, int depth) {
        if (myFile.isDirectory()) {
            System.out.println(getSpace(depth) + ">" + myFile.getName());
            File[] list = myFile.listFiles();
            if (list != null) {
                for (File file : list) {
                    printDirectoryDeeply(file, depth + 1);
                }
            }
        } else {
            System.out.println(getSpace(depth) + "-" + myFile.getName());
        }
    }

    private static String getSpace(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
}
