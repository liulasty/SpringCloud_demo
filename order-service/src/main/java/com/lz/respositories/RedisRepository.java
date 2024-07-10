package com.lz.respositories;

import com.lz.pojo.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RedisRepository {

    private static final Logger logger = LoggerFactory.getLogger(RedisRepository.class);


    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOps;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
    }

    //刪除對應的鍵
    public boolean remove(String key) {
        Boolean delete = redisTemplate.delete(key);
        if (delete) {
            logger.info("成功删除键: {}", key);
            return true;
        } else {
            logger.warn("未能删除键: {}", key);
            return false;
        }
    }



    // 存储对象集合，使用Hash结构
    public void setObjectsByHash(String hashKey, Map<String, Order> objects) {
        hashOps.putAll(hashKey, objects);
    }

    // 根据ID获取对象，使用Hash结构
    public Object getObjectById(String hashKey, String id) {
        return hashOps.get(hashKey, id);
    }

    // 获取所有对象，使用Hash结构
    public Map<String, Object> getAllObjects(String hashKey) {
        return hashOps.entries(hashKey);
    }

    // 删除对象，使用Hash结构
    public Long removeObjectById(String hashKey, String id) {
        return hashOps.delete(hashKey, id);
    }

    /**
     * 清空指定哈希表中的所有元素。
     * <p> 
     * 本方法通过调用HashOperations的delete方法，来删除指定哈希表（由hashKey标识）中的所有数据。
     * 这是对Redis中HDEL命令的封装，用于在Java代码中方便地操作Redis的哈希数据结构。
     * 使用此方法时，需要确保hashKey对应的哈希表存在，否则删除操作无影响。
     *
     * @param hashKey 哈希表的键，用于唯一标识一个哈希表。
     */
    public void clearObjects(String hashKey) {
        // 检查 hashKey 是否为 null、空或只包含空白字符
        if (hashKey == null || hashKey.trim().isEmpty()) {
            logger.error("提供的 hashKey 无效: {}。无法继续操作。", hashKey);
            throw new IllegalArgumentException("hashKey 不得为 null 或空");
        }

        try {
            boolean remove = remove(hashKey);
            if (remove) {
                logger.info("已成功删除与 hashKey 关联的对象: {}", hashKey);
            } else {
                logger.warn("未能删除与 hashKey 关联的对象: {}", hashKey);
            }
        } catch (RedisSystemException rse) {
            logger.error("在清除 hashKey 对象时发生 Redis 系统异常: {}", hashKey, rse);
            throw rse; // 再次抛出异常以便在调用栈上层处理
        } catch (Exception e) {
            logger.error("在清除操作期间针对 hashKey 出现错误: {}", hashKey, e);
            throw new RuntimeException("清除操作时出现错误", e);
        }
    }
}