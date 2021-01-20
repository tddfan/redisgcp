package com.sanjay.redis.performance;

import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

      ExecutorService service = Executors.newFixedThreadPool(10);
      List<Future<?>> futures = new ArrayList<>();
      for (int i = 0; i < 2; i++) {
          final int batch = i;
          Future<?> future = service.submit(() -> putBatch(client, batch));
          futures.add(future);
      }
      for (Future<?> future : futures) {
          try {
              Object o = future.get();
          } catch (Exception e) {
              e.printStackTrace();
          }
      }

      for (int j = 0; j < 100000; j++) {
          RList<Double> list =  client.getList("list");
          list.add(5.045d +j);
      }
      System.out.println("Time taken = " + (System.currentTimeMillis() - startTIime));
   }

    private static void putBatch(RedissonClient client, int i) {
        System.out.println("Running batch " + i);
        RMap<Object, Object> map = client.getMap("map"+i);
        for (int j = 0; j < 100000; j++) {
            map.fastPut("key"+j, 5.045d + i*j);
        }
    }
}
