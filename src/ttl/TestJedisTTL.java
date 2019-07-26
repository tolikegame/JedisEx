package ttl;

import redis.clients.jedis.Jedis;

public class TestJedisTTL {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		jedis.set("test", "Hello");
		jedis.expire("test", 10);
		try {
			Thread.sleep(3000);

			System.out.println("First ttl: " + jedis.ttl("test"));

			Thread.sleep(3000);

			System.out.println("Second ttl: " + jedis.ttl("test"));
			
			jedis.persist("test"); // 或 jedis.set("test", "World");
			
			Thread.sleep(4000);
			
			
			System.out.println(jedis.ttl("test"));
			
			
			System.out.println(jedis.get("test"));

		} catch (Exception e) {
		}

		jedis.close();
	}

}
