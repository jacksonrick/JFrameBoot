package com.jf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-05-20
 * Time: 17:23
 */
public class ProcessUtil {

    private static final int BUFFER_SIZE = 65536;

    public static String process(String cmd) {
        StringBuilder builder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()), BUFFER_SIZE);

            String temp;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return builder.toString();
    }

}
