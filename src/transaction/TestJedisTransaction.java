package transaction;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestJedisTransaction {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		Transaction tr = jedis.multi();
		tr.set("key", "1");
		// JedisDataException!!!
		tr.sadd("key", "2");
		tr.set("key", "3");
		List<Object> results = tr.exec();
		
		System.out.println(results);
		
		System.out.println(jedis.get("key"));
		
		jedis.close();
	}

}
