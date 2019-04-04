package com.jf.service.system;

import com.jf.convert.Convert;
import com.jf.mapper.ConfigMapper;
import com.jf.database.model.manage.Config;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-08-06
 * Time: 11:52
 */
@Service
public class ConfigService {

    @Resource
    private ConfigMapper configMapper;

    /**
     * @param key
     * @return int
     */
    public Integer getInt(String key) {
        return Integer.parseInt(configMapper.findByKey(key));
    }

    /**
     * @param key
     * @return string
     */
    public String getString(String key) {
        return configMapper.findByKey(key);
    }

    /**
     * @param key
     * @return bool
     */
    public Boolean getBool(String key) {
        return Convert.stringToBoolean(configMapper.findByKey(key));
    }

    /**
     * 查询所有配置
     *
     * @return list
     */
    public List<Config> findAllConfigList() {
        return configMapper.findAll();
    }

    /**
     * 多个key获取val
     *
     * @param keys new String[]{""}
     * @return
     */
    public Map<String, String> findConfigsByKey(String[] keys) {
        Map<String, String> map = new HashMap<String, String>();
        List<Config> list = configMapper.findByKeys(keys);
        for (Config config : list) {
            map.put(config.getKey(), config.getVal());
        }
        return map;
    }

    /**
     * 更新配置
     *
     * @param map
     * @return
     */
    public int updateConfig(Map map) {
        if (configMapper.delete() > 0) {
            return configMapper.insertBatch(map);
        }
        return 0;
    }

}
