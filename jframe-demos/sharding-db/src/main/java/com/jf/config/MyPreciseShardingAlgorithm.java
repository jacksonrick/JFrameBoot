package com.jf.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Description: 根据id取模(3)来获取表名
 * User: xujunfei
 * Date: 2019-11-14
 * Time: 11:05
 */
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        for (String tableName : availableTargetNames) {
            if (tableName.endsWith(shardingValue.getValue() % 3 + "")) {
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }
}
