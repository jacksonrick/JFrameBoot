package com.jf.service;

import com.github.pagehelper.PageInfo;
import com.jf.database.mapper.TestMapper;
import com.jf.database.model.User;
import com.jf.database.model.custom.IdText;
import com.jf.encrypt.PasswordUtil;
import com.jf.string.StringUtil;
import com.jf.system.redisson.RedisLocker;
import com.jf.system.redisson.lock.AquiredLockWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 用户Service
 * Created by xujunfei on 2016/12/21.
 */
@Service
@Transactional
public class UserService {

    // 多数据源
    @Resource
    private TestMapper testMapper;
    //@Autowired(required = false)
    //private Test2Mapper test2Mapper;

    // RedisLocker
    @Autowired(required = false)
    private RedisLocker locker;


    /**
     * 测试 数据库JSON类型转bean
     *
     * @return
     */
    public User testTypeHandlerForFind() {
//        return testMapper.findUserById(10038);
//        return testMapper.findUserById2(10037);
        return testMapper.findUserById3(10037);
    }

    public int testTypeHandlerForInsert() {
        User user = new User();
        user.setNickname("qqq");
        /*Extend extend = new Extend();
        extend.setA("12");
        extend.setB("99");
        extend.setC("00");
        user.setExtend(extend);*/

        /*TreeMap map = new TreeMap();
        map.put("c", "1");
        map.put("d", "2");
        map.put("a", "3");
        map.put("g", "4");
        map.put("f", "5");
        map.put("z", "6");
        String json = JSONUtils.toJSONString(map);
        System.out.println(json);
        user.setParams(map);*/

        user.setArr(new String[]{"q", "w", "e", "r", "t"});

//        return testMapper.insertUser(user);
//        return testMapper.insertUser2(user);
        return testMapper.insertUser3(user);
    }


    @Resource
    private User2Service user2Service;

    // 多线程事务【陷阱】
    @Transactional(rollbackFor = Exception.class)
    public void testRollback() {
        String result = "";
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Callable<Boolean>> callableList = new ArrayList<Callable<Boolean>>();

        Integer[] ids = new Integer[]{10000, 10001, 10002, 10003, 10004, 10005, 10006, 10007};
        for (int i = 0; i < ids.length; i++) {
            Callable<Boolean> callable = new UTask(ids[i]);
            callableList.add(callable);
        }
        /*Future<Integer> submit = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("######## call");
                return 1;
            }
        });*/

        try {
            List<Future<Boolean>> futures = executorService.invokeAll(callableList);
            for (Future<Boolean> future : futures) {
                if (null == future.get() || !future.get()) {
                    result = "false";
                }
            }
            /*if (result.equals("false")) {
                throw new RuntimeException("Errors......");
            }*/

            /*List<Future<Boolean>> futures = executorService.invokeAll(callableList);
            for (Future<Boolean> future : futures) {
                if (null == future.get() || !future.get()) {
                    result = "false";
                }
            }*/
        } catch (Exception e) {
            //throw new RuntimeException("Errors......");
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        //System.out.println("######### result: " + result);
    }

    class UTask implements Callable<Boolean> {

        private Integer id;

        public UTask(Integer id) {
            this.id = id;
        }

        @Override
        public Boolean call() throws Exception {
            if (id.equals(10006l)) {
                //System.out.println("10006");
                System.out.println(1 / 0); // error
            }
            User user = testMapper.findById(id);
            user.setMoney(1000d);
            try {
                user2Service.update(user);
            } catch (Exception e) {
                System.out.println("###########call error");
                throw new RuntimeException();
            }

            return Boolean.TRUE;
        }
    }


    /**
     * 测试Redisson分布式锁
     *
     * @param userId
     */
    public void testLock(Integer userId) throws Exception {
        locker.lock("user_" + userId + "_lock", new AquiredLockWorker<Object>() {
            @Override
            public Object invokeAfterLockAquire() throws Exception {

                User user = testMapper.findSimpleById(userId);
                user.setMoney(user.getMoney() - 1);
                testMapper.update(user);

                return null;
            }
        });
    }

    @Autowired(required = false)
    private RestTemplate restTemplate;

    /**
     * 转账-分布式事务
     *
     * @param money
     * @return
     */
    public int transfer(String money) {
        String xid = "8u3ej3923ej";
        HttpHeaders headers = new HttpHeaders();
        headers.add("xid", xid);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response1 = restTemplate.exchange("http://JFRAME-SERVICE-ORDER-01/add?money={money}", HttpMethod.GET, requestEntity, String.class, money);

        ResponseEntity<String> response2 = restTemplate.exchange("http://JFRAME-SERVICE-ORDER-02/reduce?money={money}", HttpMethod.GET, requestEntity, String.class, money);

        int ret = Integer.parseInt(response2.getBody().toString());
        if (ret == -1) {
            throw new RuntimeException("账户余额不足");
        }
        return 1;
    }

    /**
     * 测试msyql cluster集群
     * <p>已安装集群环境</p>
     *
     * @return
     */
    public List<IdText> testMysqlCluster() {
//        return testMapper.findAll();
        return null;
    }

    /**
     * 测试事务回滚 testRollbackA & testRollbackB
     */
    /*@Transactional(value = "primaryTransactionManager")
    public void testRollbackA() {
        User user = testMapper.findById(10000);
        user.setNickname("primary_rollback");
        testMapper.update(user);
        System.out.println(1 / 0); // error
        user = new User(10001);
        user.setNickname("secondary_rollback");
        testMapper.update(user);
    }*/

    /*@Transactional(value = "secondaryTransactionManager")
    public void testRollbackB() {
        User user = test2Mapper.findById(10000);
        user.setNickname("secondary_rollback");
        test2Mapper.update(user);
        System.out.println(1 / 0); // error
        user = new User(10001);
        user.setNickname("primary_rollback");
        test2Mapper.update(user);
    }*/

    /**
     * 测试多数据源
     *
     * @param source
     * @return
     */
    /*public User testMutilSource(String source) {
        User user = null;
        if ("primary".equals(source)) {
            user = testMapper.findById(10000);
        }
        if ("secondary".equals(source)) {
            user = test2Mapper.findById(10000);
        }
        return user;
    }*/

    /**
     * 按id查询用户
     * <p>测试Redis缓存</p>
     *
     * @param id
     * @return
     */
    @Cacheable(value = "user", key = "'findUserById'+#id")
    public User findUserById(Integer id) {
        return testMapper.findById(id);
    }


    /**
     * 用户更新信息
     * <p>测试Redis缓存-更新会直接删除老数据</p>
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "user", key = "'findUserById'+#user.id")
    public int updateUser(User user) {
        if (StringUtil.isNotBlank(user.getPassword())) {
            user.setPassword(PasswordUtil.MD5Encode(user.getPassword()));
        }
        return testMapper.update(user);
    }

    /**
     * 用户分页查询
     *
     * @param condition
     * @return
     */
    public PageInfo findUserByPage(User condition) {
        // 需要自定义排序的，必须指定默认排序
        if (StringUtil.isBlank(condition.getPageSort())) {
            condition.setPageSort("u.id DESC");
        }
        condition.setPage(true);
        List<User> list = testMapper.findByCondition(condition);
        return new PageInfo(list);
    }

    /**
     * @param condition
     * @return
     */
    public List<User> findByCondition(User condition) {
        //condition.setPageNo(1);
        //condition.setPageSize(10);
        //condition.setPage(false);
        return testMapper.findByCondition(condition);
    }

}
