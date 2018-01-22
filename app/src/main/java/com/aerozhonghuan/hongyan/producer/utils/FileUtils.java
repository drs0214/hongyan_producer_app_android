package com.aerozhonghuan.hongyan.producer.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件辅助类
 * Created by zhangyunfei on 17/7/19.
 */

public class FileUtils {

    public static void writeAllText(File file1,String content) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file1);
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
        } finally {
            if (null != fileWriter) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static String readAllText(File file1) {
        if(!file1.isFile()){
            return "";
        }
        char[] buf = new char[1024];
        StringBuilder builder = new StringBuilder();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file1);
            while (fileReader.ready()) {
                int len = fileReader.read(buf);
                builder.append(buf, 0, len);
            }
            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != fileReader) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除该文件夹下的所有子文件和目录
     * 递归删除
     *
     * @param file
     */
    public static void deleteAllChildFiles(File file) {
        if (file == null) return;
        if (!file.exists()) return;
        File[] files = file.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteAllChildFiles(f);
                    f.delete();
                } else {
                    f.delete();
                }
            }
    }
}
