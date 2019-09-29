package com.jf.test;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-05-06
 * Time: 17:15
 */
public class Test {

    public static void main(String[] args) {
        try {
            MyGroup group = new MyGroup();
            group.connect("192.168.40.200");
//            group.create("test");
            group.set("test", "11111", -1);
//            group.children();
            group.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

}
