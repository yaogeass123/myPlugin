package com.polaris.check;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author polaris
 */
public class CheckValue {

    private static StringBuffer res;

    private static final String PART = "@Value[(]\"(.)*:(.)*\"[)]";

    private static final String PART_RIGHT = "@Value[(]\"\\$\\{(\\w|-)+:(.)+}\"[)]";

    private static final String PREFIX = "@Value";

    public static String checkValue(String path) {
        res = new StringBuffer();
        doCheck(path);
        return res.toString();
    }


    private static void doCheck(String path) {
        File[] files = new File(path).listFiles();
        if (Objects.isNull(files)) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                doCheck(file.getPath());
                continue;
            }
            if (file.isFile() && file.getName().endsWith("java")) {
                try {
                    checkFormat(file);
                } catch (IOException e) {
                    res.append("读取文件失败：").append(file.getName()).append("\n");
                }
            }
        }
    }

    private static void checkFormat(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader read = new BufferedReader(inputStreamReader);
        String line;
        while ((line = read.readLine()) != null) {
            if (valid(line)) {
                Matcher part = Pattern.compile(PART).matcher(line);
                if (part.find()) {
                    Matcher right = Pattern.compile(PART_RIGHT).matcher(line);
                    if (right.find()) {
                        res.append("Right: ").append(line).append("\n");
                    } else {
                        res.append("Wrong: ").append(line).append("\n");
                    }
                }
            }
        }
        read.close();
        inputStreamReader.close();
        stream.close();
    }

    private static boolean valid(String line) {
        line = line.trim();
        return line.startsWith(PREFIX);
    }
}
