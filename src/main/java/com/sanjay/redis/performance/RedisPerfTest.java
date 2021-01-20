package com.sanjay.redis.performance;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisPerfTest {
  public static void main(String[] args) {
        String url = "redis://10.154.0.2:6379";

        Config config = new Config();
        config.useSingleServer()
                // use "rediss://" for SSL connection
                .setAddress(url);
        RedissonClient client = Redisson.create(config);
        System.out.println("Starting work");
        long startTIime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            System.out.println("Running batch " + i);
            for (int j = 0; j < 1000; j++) {
                RMap<Object, Object> map = client.getMap("map"+i);
                map.put("key"+j, 5.045d + i*j);
            }
        }
        System.out.println("Time taken = " + (System.currentTimeMillis() - startTIime));
   }
}
