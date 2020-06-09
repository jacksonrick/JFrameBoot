package com.jf.commons;

import com.jf.convert.Convert;
import com.jf.exception.SysException;
import com.jf.file.FileUtil;
import com.sun.management.OperatingSystemMXBean;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 服务器检查工具
 *
 * @author rick
 */
public class SystemUtil {

    /**
     * 获取内存、磁盘信息
     * [可用磁盘空间、已使用磁盘空间、总磁盘空间、可用内存、已使用内存、总内存]
     *
     * @return
     */
    public static double[] getSpace() {
        try {
            double freeSpace = 0;
            double usedSpace = 0;
            double totalSpace = 0;
            File[] roots = File.listRoots();
            for (File file : roots) {
                freeSpace += FileUtil.convertFileSizeGB(file.getUsableSpace());
                totalSpace += FileUtil.convertFileSizeGB(file.getTotalSpace());
            }
            usedSpace = Convert.doubleSub(totalSpace, freeSpace);

            double allMemory = Convert.doubleFormat(
                    ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() / 1024.0 / 1024.0 / 1024.0);
            double availMemory = Convert.doubleFormat(
                    ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize() / 1024.0 / 1024.0 / 1024.0);
            double usedMemory = Convert.doubleSub(allMemory, availMemory);

            double[] arr = {Convert.doubleFormat(freeSpace), usedSpace, Convert.doubleFormat(totalSpace), availMemory, usedMemory, allMemory};
            return arr;
        } catch (Exception e) {
            return new double[]{0, 0, 0, 0, 0, 0};
        }
    }

    /**
     * @param rootPath 指定的挂载路径
     * @return
     */
    public static double[] getSpace(String rootPath) {
        try {
            File file = new File(rootPath);
            double freeSpace = 0;
            double usedSpace = 0;
            double totalSpace = 0;
            if (file.exists()) {
                long free = file.getUsableSpace();
                long total = file.getTotalSpace();
                freeSpace = FileUtil.convertFileSizeGB(free);
                totalSpace = FileUtil.convertFileSizeGB(total);
                usedSpace = FileUtil.convertFileSizeGB(total - free);
            }

            double allMemory = Convert.doubleFormat(
                    ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() / 1024.0 / 1024.0 / 1024.0);
            double availMemory = Convert.doubleFormat(
                    ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize() / 1024.0 / 1024.0 / 1024.0);
            double usedMemory = Convert.doubleSub(allMemory, availMemory);

            double[] arr = {Convert.doubleFormat(freeSpace), usedSpace, Convert.doubleFormat(totalSpace), availMemory, usedMemory, allMemory};
            return arr;
        } catch (Exception e) {
            return new double[]{0, 0, 0, 0, 0, 0};
        }
    }

    public static void main(String[] args) {
        double[] info = getSpace("");
        System.out.println(Arrays.toString(info));
    }

    /**
     * 获取cpu使用率
     *
     * @return
     */
    public static int getCpuRatio() {
        String osName = System.getProperty("os.name");
        double cpuRatio = 0;
        if (osName.toLowerCase().startsWith("windows")) {
            cpuRatio = getCpuRatioForWindows();
        } else {
            cpuRatio = getCpuRateForLinux();
        }
        return (int) cpuRatio;
    }

    private static double getCpuRatioForWindows() {
        try {
            String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            // 取进程信息
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(30);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return Double.valueOf(100 * (busytime) / (busytime + idletime)).doubleValue();
            } else {
                return 0.0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
    }

    private static double getCpuRateForLinux() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader brStat = null;
        StringTokenizer tokenStat = null;
        try {
            Process process = Runtime.getRuntime().exec("top -b -n 1");
            is = process.getInputStream();
            isr = new InputStreamReader(is);
            brStat = new BufferedReader(isr);

            brStat.readLine();
            brStat.readLine();

            tokenStat = new StringTokenizer(brStat.readLine());
            tokenStat.nextToken();
            tokenStat.nextToken();
            tokenStat.nextToken();
            tokenStat.nextToken();
            tokenStat.nextToken();
            tokenStat.nextToken();
            tokenStat.nextToken();
            String cpuUsage = tokenStat.nextToken();

            Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));

            return (1 - usage.floatValue() / 100);
        } catch (IOException ioe) {
            freeResource(is, isr, brStat);
            return 0.0;
        } finally {
            freeResource(is, isr, brStat);
        }

    }

    private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br) {
        try {
            if (is != null)
                is.close();
            if (isr != null)
                isr.close();
            if (br != null)
                br.close();
        } catch (IOException ioe) {

        }
    }

    private static long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < 10) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = substring(line, capidx, cmdidx - 1).trim();
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                // log.info("line="+line);
                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    idletime += Long.valueOf(substring(line, kmtidx, rocidx - 1).trim()).longValue();
                    idletime += Long.valueOf(substring(line, umtidx, wocidx - 1).trim()).longValue();
                    continue;
                }

                kneltime += Long.valueOf(substring(line, kmtidx, rocidx - 1).trim()).longValue();
                usertime += Long.valueOf(substring(line, umtidx, wocidx - 1).trim()).longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                throw new SysException(e.getMessage(), e);
            }
        }
        return null;
    }

    public static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idx; i <= end_idx; i++) {
            tgt += (char) b[i];
        }
        return tgt;
    }

}
