package com.sanjay.redis.performance;

import java.util.HashMap;
import java.util.Map;

public class MemoryPerfTest {
  public static void main(String[] args) {
      Map<String,Map<String, Double>> map = new HashMap<>();

      long startTime = System.currentTimeMillis();
      for (int i = 0; i < 10000; i++) {
            System.out.println("Running batch " + i);
            for (int j = 0; j < 10000; j++) {
                map.putIfAbsent("map"+i, new HashMap<>());
                Map<String, Double> subMap = map.get("map"+i);
                subMap.put("key"+j, Double.valueOf(5.045d + i*j));
            }
        }
        System.out.println("Time taken = " + (System.currentTimeMillis() - startTime));
   }
}
