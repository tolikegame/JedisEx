package sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

public class TestJedisSort {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		jedis.flushDB();

		System.out.println("========List Sort==========");
		jedis.lpush("mylist", "2", "8", "9", "7", "5", "1");
		System.out.println(jedis.sort("mylist"));
		jedis.lpush("mylist2", "Z", "a", "c", "f", "G", "X");

		SortingParams params = new SortingParams();
		System.out.println(jedis.sort("mylist2", params.alpha()));
		System.out.println(jedis.sort("mylist2", params.desc()));
		System.out.println(jedis.sort("mylist2", params.desc().limit(2, 2)));

		System.out.println("========Zset Sort==========");
		Map<String, Double> data = new HashMap<>();
		data.put("50", new Double(2));
		data.put("40", new Double(3));
		data.put("20", new Double(1));
		data.put("60", new Double(5));
		jedis.zadd("myZset", data);
		// sort對Zset只做元素排序，忽略分數
		System.out.println(jedis.sort("myZset"));

		System.out.println("========Hash Sort==========");
		jedis.hset("pen:1", "id", "1");
		jedis.hset("pen:1", "brand", "SKB");
		jedis.hset("pen:1", "price", "10");

		jedis.hset("pen:2", "id", "2");
		jedis.hset("pen:2", "brand", "Pentel");
		jedis.hset("pen:2", "price", "50");

		jedis.hset("pen:3", "id", "3");
		jedis.hset("pen:3", "brand", "Lion");
		jedis.hset("pen:3", "price", "20");

		jedis.hset("pen:4", "id", "4");
		jedis.hset("pen:4", "brand", "MontBlanc");
		jedis.hset("pen:4", "price", "15000");

		jedis.lpush("all:pens", "1", "2", "3", "4");
		System.out.println(jedis.sort("all:pens", new SortingParams().by("pen:*->price").desc()));
		// 排序後取出某個指定的key-value
		List<String> result = jedis.sort("all:pens", new SortingParams().by("pen:*->price").get("pen:*->brand").desc());
		System.out.println(result);
		// 排序後取出元素本身的值，使用'#'
		List<String> result2 = jedis.sort("all:pens", new SortingParams().by("pen:*->price").get("pen:*->brand", "#").desc());
		System.out.println(result2);
		
		
		jedis.close();
	}

}
