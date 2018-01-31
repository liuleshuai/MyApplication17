package com.example.a67017.myapplication.tool;

import java.io.File;

/**
 * Created by 67017 on 2017/11/24.
 */

public class FileUtils {

    public static void deleteDir(final String path) {
        File dir = new File(path);
        deleteDirWithFile(dir);
    }

    public static void deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                // 删除文件
                file.delete();
            } else if (file.isDirectory()) {
                // 递归的方式删除文件夹
                deleteDirWithFile(file);
            }
        }
        dir.delete();// 删除目录本身
    }
}
