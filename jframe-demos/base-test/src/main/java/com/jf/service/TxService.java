package com.jf.service;

import com.jf.database.mapper.TestMapper;
import com.jf.database.model.User;
import com.jf.string.IdGen;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description: 事务测试
 * User: xujunfei
 * Date: 2018-06-26
 * Time: 14:30
 */
@Service
public class TxService {

    @Resource
    private TestMapper testMapper;

    @Transactional
    public void tx1() {
        User user1 = new User(IdGen.get().nextId());
        user1.setNickname("tx1");
        user1.setPassword("1111");
        testMapper.insert(user1);

        // 解决异步方法同步调用的问题 见TaskExecutePool -> @EnableAspectJAutoProxy
        TxService txService = (TxService) AopContext.currentProxy();
        txService.asy();
        System.out.println("over");
    }

    @Transactional
    public void tx2() {
        User user2 = new User(IdGen.get().nextId());
        user2.setNickname("tx2");
        user2.setPassword("1111");
        testMapper.insert(user2);

        System.out.println(1 / 0);
    }

    @Async
    public void asy() {
        User user3 = new User(IdGen.get().nextId());
        user3.setNickname("tx3");
        user3.setPassword("1111");
        testMapper.insert(user3);
    }


    /**
     * 声明式
     * insert*
     */
    public void insertTest() {
        testMapper.insert1("xu", 10);

        testMapper.insert2("fei", 20);

        System.out.println(1 / 0);
    }

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    /**
     * 手动
     */
    public void transaction2() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("transaction2");
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = platformTransactionManager.getTransaction(definition);

        try {
            testMapper.insert1("jun", 20);
            System.out.println(1 / 0);
            platformTransactionManager.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            platformTransactionManager.rollback(status);
        }
    }

}
