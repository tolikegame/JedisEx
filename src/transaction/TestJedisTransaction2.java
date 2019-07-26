package transaction;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class TestJedisTransaction2 {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		Transaction tr = jedis.multi();
		tr.set("mem", "David");
		Response<String> res = tr.get("mem");

		tr.zadd("foo", 3, "Taipei");
		tr.zadd("foo", 1, "Taoyuan");
		tr.zadd("foo", 2, "Taichung");
		Response<Set<String>> res2 = tr.zrange("foo", 0, -1); 
		tr.exec(); 

		String mem = res.get();
		Set<String> data = res2.get();

		System.out.println(mem);
		System.out.println(data);
		
		jedis.close();
	}

}
