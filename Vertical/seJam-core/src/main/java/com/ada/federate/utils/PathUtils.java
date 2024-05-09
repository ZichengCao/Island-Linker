package com.ada.federate.utils;

import java.io.*;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.regex.Matcher;

public class PathUtils {

    public static String getRealPath(String folder, String fileName) {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            // windows 需要移除 路径中第一个 /
            fileName = folder + "\\" + fileName;
        } else {
            fileName = folder + "/" + fileName;
        }
        String env = Objects.requireNonNull(PathUtils.class.getResource("")).getProtocol();
        if (env.equals("file"))
            // IDE 中运行
            return PathUtils.getResourcePath(fileName);
        else if (env.equals("jar"))
            // jar 包运行
            return PathUtils.getJarPath(fileName);
        System.exit(-1);
        return "";
    }

    /**
     * According to the operating mode, get the resources path or jar path.
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getRealPath(String fileName) {
        String env = Objects.requireNonNull(PathUtils.class.getResource("")).getProtocol();
        if (env.equals("file"))
            // IDE 中运行
            return PathUtils.getResourcePath(fileName);
        else if (env.equals("jar"))
            // jar 包运行
            return PathUtils.getJarPath(fileName);
        System.exit(-1);
        return "";
    }

    /**
     * debug mode，get the resources path.
     */
    private static String getResourcePath(String fileName) {
        try {
            String path = Objects.requireNonNull(PathUtils.class.getResource("/" + fileName)).getPath();
            // 中文路径
            String decodePath = null;
            decodePath = URLDecoder.decode(path, "UTF-8");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                // windows 需要移除 路径中第一个 /
                return decodePath.substring(1);
            } else {
                return path;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.exit(-1);
        return "";
    }

    /**
     * package mode, get the jar path.
     *
     * @param fileName
     * @return
     */
    private static String getJarPath(String fileName) {
        try {
            String path = PathUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            // 中文路径
            String decodedPath = null;
            decodedPath = URLDecoder.decode(path, "UTF-8");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                // 手动替换 / 为 \
                decodedPath = decodedPath.replaceAll("/", Matcher.quoteReplacement(File.separator)).substring(1);
            }
            // File.separator 是/（斜杠）与\（反斜杠），Windows下是\（反斜杠），Linux下是/（斜杠）。
            int lastIndex = decodedPath.lastIndexOf(File.separator) + 1;
            return decodedPath.substring(0, lastIndex) + fileName;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.exit(-1);
        return "";
    }

    public static String readSQL(String queryFile) throws IOException {
        String filepath = PathUtils.getRealPath(queryFile);
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        StringBuilder sb = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append(ls);
        }
        // 删除最后一个新行分隔符
        sb.deleteCharAt(sb.length() - 1);
        reader.close();
        return sb.toString();
    }

    public static String readSQL(String folderName, String queryFile) throws IOException {
        String filepath = PathUtils.getRealPath(folderName, queryFile);
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        StringBuilder sb = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append(ls);
        }
        // 删除最后一个新行分隔符
        sb.deleteCharAt(sb.length() - 1);
        reader.close();
        return sb.toString();
    }
}
