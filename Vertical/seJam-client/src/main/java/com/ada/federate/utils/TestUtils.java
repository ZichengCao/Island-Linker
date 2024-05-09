package com.ada.federate.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestUtils {
    public static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static float ping(String ipAddress, int times) throws ExecutionException, InterruptedException {
        Callable<Float> task = () -> TestUtils.pingCommand(ipAddress, times);
        Future<Float> future = executorService.submit(task);
        Float rtt = future.get();
        return rtt;
    }

    public static void shutdown() {
        executorService.shutdown();
    }

    public static float pingCommand(String ipAddress, int times) throws IOException {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();   //  将要执行的ping命令,此命令是windows格式的命令
        // String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
        String pingCommand = "ping " + ipAddress;
        //  执行命令并获取输出
        System.out.println(pingCommand);
        Process p = r.exec(pingCommand);
        if (p == null) {
            return -1f;
        }
        in = new BufferedReader(new InputStreamReader(p.getInputStream()));    //  逐行检查输出,计算类似出现=23ms TTL=62字样的次数

        String line = null;
        // 跳过 PING 127.0.0.1 (127.0.0.1): 56 data bytes
        in.readLine();

        float connectedSeconds = 0;
        int connectedCount = 0;

        while ((line = in.readLine()) != null && times != 0) {
            times--;
            // System.out.println(line);
            float rtt = getCheckResult(line);
            if (rtt != -1f) {
                connectedSeconds += getCheckResult(line);
                connectedCount++;
            } else {
                return -1f;
            }
        }
        return connectedSeconds / connectedCount;
    }

    // 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static float getCheckResult(String line) {
        // String testStr = "64 bytes from 127.0.0.1: icmp_seq=0 ttl=64 time=0.068 ms";
        // Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("time=([\\d.]+) ms", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Float.parseFloat(matcher.group(1));
        } else return -1f;
    }


    public static void callScript(String script, String args, String... workspace) {
        try {
            String cmd = "sh " + script;
            if (args != null) cmd += " " + args;
//        	String[] cmd = {"sh", script, "4"};
            File dir = null;
            if (workspace[0] != null) {
                dir = new File(workspace[0]);
            }
            String[] evnp = {"val=2", "call=Bash Shell"};
            Process process = Runtime.getRuntime().exec(cmd, evnp, dir);
            String line = "";
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            System.out.printf("============== %s start =================%n", script);
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            System.out.printf("============== %s end =================%n", script);
            process.waitFor();
//            process = Runtime.getRuntime().exec(cmd);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void winCMD(String content) throws IOException, InterruptedException {
        // cmd /c dir 是执行完dir命令后封闭命令窗口。
        // cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会封闭。
        // cmd /k dir 是执行完dir命令后不封闭命令窗口。
        // cmd /k start dir 会打开一个新窗口后执行dir指令，原窗口不会封闭。
        //————————————————
        // 版权声明：本文为CSDN博主「北极的冰箱」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        // 原文链接：https://blog.csdn.net/jlminghui/article/details/72899623
        String[] cmdArr = {"cmd", "/c", content};
        Process process = Runtime.getRuntime().exec(cmdArr, null, new File(System.getProperty("user.dir")));
        String line = "";
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = input.readLine()) != null)
            System.out.println(line);
        process.waitFor();
        input.close();
    }

    public static void winCMD(String... contents) throws IOException, InterruptedException {
        for (String content : contents) {
            winCMD(content);
        }
    }


    public static void writeCSV(String queryName, String inputRows,
                                List<Long> consumingList) throws IOException {
        // String filepath = PathUtils.getResourcePath(fileName);
        String filepath = "D:\\OneDrive - stu.suda.edu.cn\\repository\\repository-project\\DataProcessor\\TestMy\\testResults.csv";
        // System.out.println(filepath);
        File file = new File(filepath);
        // if (!file.exists()) file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        StringBuilder sb = new StringBuilder();
        sb.append(queryName).append(",");
        // sb.append(dataSiloNumbers).append(",");
        sb.append(inputRows).append(",");
        sb.append(consumingList.stream().map(Object::toString).collect(Collectors.joining(",")));
        writer.write(sb.toString());
        writer.flush();
        writer.newLine();
        writer.close();
    }


    public static void writeCSV(List<List<String>> contentList) throws IOException {
        // String filepath = PathUtils.getResourcePath(fileName);
        String filepath = "/Users/caozicheng/Library/CloudStorage/OneDrive-stu.suda.edu" +
                ".cn/repository/repository-project/FDS4OLAP/core/src/main/resources/testResults.csv";
        // System.out.println(filepath);
        File file = new File(filepath);
        if (!file.exists()) file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        for (List<String> content : contentList) {
            writer.write(String.join(",", content));
            writer.flush();
            writer.newLine();
        }
        writer.close();
    }

    private static void writeByBR(String filepath, String content) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(content);
        writer.flush();
        writer.newLine();
        writer.close();
    }


    private static String getNowTime() {
        Date now = new Date();
        // 指定格式化格式
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        return f.format(now).toString();
    }

    public static void main(String[] args) throws Exception {
        winCMD("dir");
        // WinCMD cmd = WinCMD.getInstance();
        // cmd.execute("dir");
        // System.out.println(cmd.getOutput());
        // List<List<String>> contentList = new ArrayList<>();
        // contentList.add(Arrays.asList("test-time", "time-cost", "avg-rtt"));
        // contentList.add(Arrays.asList("1", "2", "3"));
        // contentList.add(Arrays.asList("3", "4", "5"));
        // writeCSV("testResults.csv", contentList);

    }
}
