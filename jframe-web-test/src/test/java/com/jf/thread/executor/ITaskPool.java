package com.jf.executor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-04-10
 * Time: 14:38
 */
public interface ITaskPool {

    //加入任务
    void execute(Runnable task);

    void execute(List<Runnable> task);

    //销毁线程
    void destroy();

}
