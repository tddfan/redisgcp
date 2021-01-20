sudo git clone https://github.com/tddfan/redisgcp.git
mvn clean install
sudo java -cp  /home/redisgcp/target/redis-gcp-1.0-SNAPSHOT-jar-with-dependencies.jar  com.sanjay.redis.performance.RedisPerfTest