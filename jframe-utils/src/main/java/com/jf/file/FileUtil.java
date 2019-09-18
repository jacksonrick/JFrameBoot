package com.jf.file;

import com.jf.convert.Convert;
import com.jf.date.DateUtil;
import com.jf.exception.SysException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 文件处理工具类
 *
 * @author rick
 * @version 4.0
 */
public class FileUtil {

    /**
     * 读取文件内容
     *
     * @param filepath
     * @return
     */
    public static String getContent(String filepath) {
        File f = new File(filepath);
        if (!f.isFile()) {
            return "";
        }
        Reader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new FileReader(f);
            char[] flush = new char[10];
            int len = 0;
            while ((len = reader.read(flush)) != -1) {
                sb.append(new String(flush, 0, len));
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return sb.toString();
    }

    /**
     * 获取文件目录
     *
     * @param path
     * @param parent
     * @return
     */
    public static List<Directory> getDirectory(String path, String parent) {
        List<Directory> list = new ArrayList<Directory>();
        File f = new File(parent + path);
        File[] files = f.listFiles();
        if (files == null) {
            return null;
        }
        Arrays.sort(files, new CompratorByName());

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
                } else if (ft.endsWith(".txt") || ft.endsWith(".html") || ft.endsWith(".log")) {
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
            directory.setPath(p.substring(parent.length() - 1, p.length()));
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

    // 最近修改时间排序
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

    // 名称排序
    static class CompratorByName implements Comparator<File> {
        public int compare(File f1, File f2) {
            if (f1.isDirectory() && f2.isFile())
                return -1;
            if (f1.isFile() && f2.isDirectory())
                return 1;
            return f2.getName().compareTo(f1.getName());
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
                throw new SysException(e.getMessage(), e);
            }
        } catch (FileNotFoundException e) {
            throw new SysException(e.getMessage(), e);
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
            throw new SysException(e.getMessage(), e);
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
     * 获取文件MD5
     *
     * @param filepath
     * @return
     */
    public static String getFileMD5(String filepath) {
        return getFileMD5(new File(filepath));
    }

    /**
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        try {
            InputStream is = new FileInputStream(file);
            return DigestUtils.md5Hex(is);
        } catch (IOException e) {
            throw new SysException(e.getMessage(), e);
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
                    throw new SysException(e.getMessage(), e);
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
                    throw new SysException(e.getMessage(), e);
                }
            }
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
