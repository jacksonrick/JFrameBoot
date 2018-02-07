package com.jf.file;

import com.jf.convert.Convert;
import com.jf.date.DateUtil;
import com.jf.system.PathUtil;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 文件处理工具类
 *
 * @author rick
 * @version 3.0
 */
public class FileUtil {

    /**
     * 获取文件目录
     *
     * @param path
     * @return
     */
    public static List<Directory> getDirectory(String path, String parent) {
        parent += path;
        List<Directory> list = new ArrayList<Directory>();
        File f = new File(parent);
        File[] files = f.listFiles();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            Directory directory = new Directory();
            // 统一正斜杠 /
            String p = file.getPath().replaceAll("\\\\", "/");
            if (file.isDirectory()) {
                directory.setType("folder");
            } else if (file.isFile()) {
                String ft = file.getName();
                if (ft.endsWith(".jpg") || ft.endsWith(".jpeg") || ft.endsWith(".png") || ft.endsWith(".gif") || ft
                        .endsWith(".ico")) {
                    directory.setType("picture");
                } else if (ft.endsWith(".txt") || ft.endsWith(".html")) {
                    directory.setType("text");
                } else if (ft.endsWith(".avi") || ft.endsWith(".mp4") || ft.endsWith(".swf")) {
                    directory.setType("video");
                } else if (ft.endsWith(".rar") || ft.endsWith(".zip")) {
                    directory.setType("rar");
                } else {
                    directory.setType("unknow");
                }
            }
            directory.setName(file.getName());
            directory.setPath(p.substring(p.lastIndexOf("static") + 6, p.length()));
            list.add(directory);
        }
        return list;
    }

    /**
     * 获取日志文件
     *
     * @return
     */
    public static List<Map<String, String>> getLogFileList(String logPath) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        File file = new File(logPath);
        File[] fs = file.listFiles();
        if (fs == null) {
            return null;
        }
        Arrays.sort(fs, new CompratorByLastModified());
        int count = 1;
        for (int i = 0; i < fs.length; i++) {
            if (count > 30) {
                break;
            }
            if (fs[i].getName().startsWith("jframe")) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("fileName", fs[i].getName());
                map.put("date", DateUtil.dateToStr(new Date(fs[i].lastModified())));
                map.put("size", convertFileSize(fs[i].length()));
                list.add(map);
                count++;
            }
        }
        return list;
    }

    static class CompratorByLastModified implements Comparator<File> {
        public int compare(File f1, File f2) {
            long diff = f2.lastModified() - f1.lastModified();
            if (diff > 0)
                return 1;
            else if (diff == 0)
                return 0;
            else
                return -1;
        }

        public boolean equals(Object obj) {
            return true;
        }
    }

    /**
     * 将long转换成标准的文件大小形式
     *
     * @param size
     * @return
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    public static double convertFileSizeGB(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return Convert.doubleFormat(Double.parseDouble(String.format("%.1f", (float) size / gb)));
        } else
            return 0;
    }

    /**
     * 解压文件
     *
     * @param srcFile
     * @param desFile
     */
    public static void unzip(File srcFile, File desFile) {
        FileInputStream is;
        FileOutputStream os;
        InputStream gzis;
        final int MAX_BYTE = 1024 * 1000;
        int len = 0;
        byte[] b = new byte[MAX_BYTE];
        try {
            is = new FileInputStream(srcFile);
            os = new FileOutputStream(desFile);
            try {
                gzis = new GZIPInputStream(is);
                while ((len = gzis.read(b)) != -1)
                    os.write(b, 0, len);
                os.flush();
                gzis.close();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件
     *
     * @param srcFile
     * @param desFile
     */
    public static void zip(File srcFile, File desFile) {
        FileInputStream fis;
        FileOutputStream fos;
        GZIPOutputStream gzos;

        final int MAX_BYTE = 1024 * 1000;
        int len = 0;
        byte[] b = new byte[MAX_BYTE];
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(desFile);
            gzos = new GZIPOutputStream(fos);
            while ((len = fis.read(b)) != -1)
                gzos.write(b, 0, len);
            gzos.flush();
            gzos.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压文件到指定目录[ant]
     *
     * @param zipFile
     * @param descDir
     */
    @SuppressWarnings("rawtypes")
    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile, "GBK");
        for (Enumeration entries = zip.getEntries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + "/" + zipEntryName).replaceAll("\\\\", "/");
            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
            // System.out.println(outPath);

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
    }

    /**
     * 工具类关闭流 可变参数：... 只能形参最后一个位置,处理方式与数组一致
     *
     * @param io
     */

    public static void close(Closeable... io) {
        for (Closeable temp : io) {
            if (temp != null) {
                try {
                    temp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 使用泛型方法
     *
     * @param io
     */
    public static <T extends Closeable> void closeAll(T... io) {
        for (Closeable temp : io) {
            if (temp != null) {
                try {
                    temp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载文件
     *
     * @param urlStr   远程地址
     * @param fileName 另存为文件名
     * @param savePath 保存路径
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(5 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 5.0; Windows NT;)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
