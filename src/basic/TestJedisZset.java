package basic;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.ZParams.Aggregate;

public class TestJedisZset {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		Map<String, Double> scores1 = new HashMap<>();
		scores1.put("David", new Double(100));
		scores1.put("James", new Double(85));
		scores1.put("Ron", new Double(67));
		scores1.put("Vincent", new Double(72));
		scores1.put("George", new Double(87));
		scores1.put("Howard", new Double(75));
		scores1.put("Peter", new Double(80));
		
		Map<String, Double> scores2 = new HashMap<>();
		scores2.put("David", new Double(90));
		scores2.put("James", new Double(77));
		scores2.put("Ron", new Double(78));
		scores2.put("Vincent", new Double(68));
		scores2.put("George", new Double(95));
		scores2.put("Howard", new Double(81));
		scores2.put("Peter", new Double(72));
		
		jedis.zadd("scores1", scores1);
		jedis.zadd("scores2", scores2);
		
		jedis.zadd("scores1", 92, "Vincent");
		System.out.println(jedis.zscore("scores1", "Vincent"));
		                       //由小到大
		System.out.println(jedis.zrange("scores1", 1, -1));
		                                                    // '('代表不包含，也可以用+inf或-inf來代表正無窮或負無窮
		System.out.println(jedis.zrangeByScore("scores1", "80", "(100"));
															// 小於等於100分的前3人
		System.out.println(jedis.zrevrangeByScore("scores1", "100", "0", 0, 3));
		
		System.out.println(jedis.zincrby("scores1", 8, "Peter"));
		
		System.out.println(jedis.zrank("scores1", "David"));
		System.out.println(jedis.zrevrank("scores1", "David"));
		
		System.out.println("============Zset運算=============");
		
		ZParams zp = new ZParams();                // Aggregate.MAX | Aggregate.MIN
		jedis.zinterstore("scoresinter", zp.aggregate(Aggregate.SUM), "scores1", "scores2");
		System.out.println(jedis.zrevrange("scoresinter", 0, -1));
		
		jedis.close();
	}

}
