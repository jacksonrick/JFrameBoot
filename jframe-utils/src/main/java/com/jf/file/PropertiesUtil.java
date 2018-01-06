package com.jf.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Properties文件读取及修改工具
 */
public class PropertiesUtil {

    private PropertiesUtil() {
    }

    /**
     * 读取 property 文件
     *
     * @param propertyURI 文件地址
     * @return Properties
     */
    public static Properties readProperty(String propertyURI) {
        if (propertyURI == null || !propertyURI.endsWith(".properties")) {
            return null;
        }

        //文件读取流
        InputStream in = null;

        try {
            //读取文件
            in = new FileInputStream(propertyURI);

            //生成 Properties
            Properties result = new Properties();

            //加载文件信息
            result.load(in);

            return result;
        } catch (IOException e) {
            System.out.println("读取文件[" + propertyURI + "]信息异常：");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {

                }
                in = null;
            }
        }
        return null;
    }

    /**
     * 向配置信息中写入数据或修改数据<br/>
     * 如果文件不存在则不修改
     *
     * @param propertyURI 文件地址
     * @param properties  要写入的信息
     */
    public static void writeProperty(String propertyURI, Map<String, String> properties) {
        if (properties == null || properties.size() <= 0) {
            return;
        }

        Properties prop = readProperty(propertyURI);

        if (prop == null) {
            return;
        }

        //修改信息
        for (String key : properties.keySet()) {
            prop.setProperty(key, properties.get(key));
        }

        //文件输出流
        OutputStream out = null;

        try {
            out = new FileOutputStream(propertyURI);

            prop.store(out, "the last time to change this file:");
        } catch (FileNotFoundException e) {
            System.out.println("修改文件[" + propertyURI + "]信息异常：");
        } catch (IOException e) {
            System.out.println("修改文件[" + propertyURI + "]信息异常：");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {

                }
                out = null;
            }
        }
    }

    /**
     * 文件存在则替换文件，不存在则替换文件
     *
     * @param propertyURI 文件路径
     * @param properties  要更新成为的内容
     */
    public static void writeProperty(String propertyURI, Properties properties) {
        //文件输出流
        OutputStream out = null;

        try {
            out = new FileOutputStream(propertyURI);

            properties.store(out, "the last time to change this file:");
        } catch (FileNotFoundException e) {
            System.out.println("修改文件[" + propertyURI + "]信息异常：");
        } catch (IOException e) {
            System.out.println("修改文件[" + propertyURI + "]信息异常：");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {

                }
                out = null;
            }
        }
    }

    /**
     * 删除配置信息中数据
     *
     * @param propertyURI 文件地址
     * @param keyList     要删除的键集合
     */
    public static void removePropertyKey(String propertyURI, List<String> keyList) {
        if (keyList == null || keyList.size() <= 0) {
            return;
        }

        Properties prop = readProperty(propertyURI);

        if (prop == null) {
            return;
        }

        //修改信息
        for (String key : keyList) {
            prop.remove(key);
        }

        //文件输出流
        OutputStream out = null;

        try {
            out = new FileOutputStream(propertyURI);

            prop.store(out, "the last time to change this file:");
        } catch (FileNotFoundException e) {
            System.out.println("从文件[" + propertyURI + "]中移除信息异常：");
        } catch (IOException e) {
            System.out.println("从文件[" + propertyURI + "]中移除信息异常：");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {

                }
                out = null;
            }
        }
    }
}
