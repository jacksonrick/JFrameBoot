package com.jf.system;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

/**
 * 获取运行环境
 */
public class Environment {

    /**
     * 运行环境信息
     */
    private static Properties props;

    /**
     * 机器地址信息
     */
    private static InetAddress addr;

    /**
     * 机器名称
     */
    private static Map<String, String> map;

    /**
     * 初始化信息
     */
    static {
        props = System.getProperties();
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
        }
        map = System.getenv();
    }

    private Environment() {
    }

    /**
     * 获取登录用户名
     *
     * @return String
     */
    public static String getUserName() {
        if (map == null) {
            return null;
        }
        return map.get("USERNAME");
    }

    /**
     * 获取计算机名
     *
     * @return String
     */
    public static String getComputerName() {
        if (map == null) {
            return null;
        }
        return map.get("COMPUTERNAME");
    }

    /**
     * 获取计算机域名
     *
     * @return String
     */
    public static String getUserDomain() {
        if (map == null) {
            return null;
        }
        return map.get("USERDOMAIN");
    }

    /**
     * 获取本地ip地址
     *
     * @return String
     */
    public static String getHostAddress() {
        if (addr == null) {
            return null;
        }
        return addr.getHostAddress();
    }

    /**
     * 获取本地主机名
     *
     * @return String
     */
    public static String getHostName() {
        if (addr == null) {
            return null;
        }
        return addr.getHostName();
    }

    /**
     * 获取用户的主目录
     *
     * @return String
     */
    public static String getUserHome() {
        if (props == null) {
            return null;
        }
        return props.getProperty("user.home");
    }

    /**
     * 获取用户的当前工作目录
     *
     * @return String
     */
    public static String getUserDir() {
        if (props == null) {
            return null;
        }
        return props.getProperty("user.dir");
    }

    /**
     * 获取操作系统的名称
     *
     * @return String
     */
    public static String getOsName() {
        if (props == null) {
            return null;
        }
        return props.getProperty("os.name");
    }

    /**
     * 获取操作系统的构架
     *
     * @return String
     */
    public static String getOsArch() {
        if (props == null) {
            return null;
        }
        return props.getProperty("os.arch");
    }

    /**
     * 获取操作系统的版本
     *
     * @return String
     */
    public static String getOsVersion() {
        if (props == null) {
            return null;
        }
        return props.getProperty("os.version");
    }

    /**
     * 获取一个或多个扩展目录的路径
     *
     * @return String
     */
    public static String getExtDirs() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.ext.dirs");
    }

    /**
     * 获取默认的临时文件路径
     *
     * @return String
     */
    public static String getIOTmpdir() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.io.tmpdir");
    }

    /**
     * 获取加载库时搜索的路径列表
     *
     * @return String
     */
    public static String getLibraryPath() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.library.path");
    }

    /**
     * 获取文件分隔符
     *
     * @return String
     */
    public static String getFileSeparator() {
        if (props == null) {
            return null;
        }
        return props.getProperty("file.separator");
    }

    /**
     * 获取路径分隔符
     *
     * @return String
     */
    public static String getPathSeparator() {
        if (props == null) {
            return null;
        }
        return props.getProperty("path.separator");
    }

    /**
     * 获取行分隔符
     *
     * @return String
     */
    public static String getLineSeparator() {
        if (props == null) {
            return null;
        }
        return props.getProperty("line.separator");
    }

    /**
     * 获取Java的运行环境版本
     *
     * @return String
     */
    public static String getJavaVersion() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.version");
    }

    /**
     * 获取Java的运行环境供应商
     *
     * @return String
     */
    public static String getJavaVendor() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.vendor");
    }

    /**
     * 获取Java供应商的URL
     *
     * @return String
     */
    public static String getJavaVendorUrl() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.vendor.url");
    }

    /**
     * 获取Java的安装路径
     *
     * @return String
     */
    public static String getJavaHome() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.home");
    }

    /**
     * 获取Java的虚拟机规范版本
     *
     * @return String
     */
    public static String getJavaVMSFCVesion() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.vm.specification.version");
    }

    /**
     * 获取Java的虚拟机规范供应商
     *
     * @return String
     */
    public static String getJavaVMSFCVendor() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.vm.specification.vendor");
    }

    /**
     * 获取Java的虚拟机规范名称
     *
     * @return String
     */
    public static String getJavaVMSFCName() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.vm.specification.name");
    }

    /**
     * 获取Java的虚拟机实现版本
     *
     * @return String
     */
    public static String getJavaVMVersion() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.vm.version");
    }

    /**
     * 获取Java的虚拟机实现供应商
     *
     * @return String
     */
    public static String getJavaVMVendor() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.vm.vendor");
    }

    /**
     * 获取Java的虚拟机实现名称
     *
     * @return String
     */
    public static String getJavaVMName() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.vm.name");
    }

    /**
     * 获取Java运行时环境规范版本
     *
     * @return String
     */
    public static String getJavaSFCVersion() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.specification.version");
    }

    /**
     * 获取Java运行时环境规范供应商
     *
     * @return String
     */
    public static String getJavaSFCVender() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.specification.vender");
    }

    /**
     * 获取Java运行时环境规范名称
     *
     * @return String
     */
    public static String getJavaSFCName() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.specification.name");
    }

    /**
     * 获取Java的类格式版本号
     *
     * @return String
     */
    public static String getJavaClassVersion() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.class.version");
    }

    /**
     * 获取Java的类路径
     *
     * @return String
     */
    public static String getJavaClassPath() {
        if (props == null) {
            return null;
        }
        return props.getProperty("java.class.path");
    }
}
