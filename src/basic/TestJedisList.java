package basic;
import java.util.List;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;

public class TestJedisList {
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		if (jedis.exists("customers"))
			jedis.del("customers");
		
		jedis.lpush("customers", "David", "James", "Vincent", "Ben", "Ron", "George", "Howard");
		// List內容："Howard", "George", "Ron", "Ben", "Vincent", "James", "David"
		System.out.println(jedis.lpop("customers"));
		// List內容："George", "Ron", "Ben", "Vincent", "James", "David"
		jedis.rpush("customers", "Gakki", "Messi", "CR7");
		// List內容："George", "Ron", "Ben", "Vincent", "James", "David", "Gakki", "Messi", "CR7"
		
		System.out.println("============================");
		
		List<String> range1 = jedis.lrange("customers", 3, 6);
		for (String customer : range1)
			System.out.println(customer);
		
		System.out.println("共有" + jedis.llen("customers") + "位客戶");
		
		System.out.println("============================");
		
		jedis.ltrim("customers", 3, 6);
		List<String> range2 = jedis.lrange("customers", 0, jedis.llen("customers"));
		for (String customer : range2)
			System.out.println(customer);
		
		System.out.println("============================");
		
		jedis.linsert("customers", LIST_POSITION.BEFORE, "David", "Jedis");
		List<String> range3 = jedis.lrange("customers", 0, jedis.llen("customers"));
		for (String customer : range3)
			System.out.println(customer);
		
		
		jedis.close();
		
	}
	
	
}
