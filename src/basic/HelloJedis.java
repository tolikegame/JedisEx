package basic;
import redis.clients.jedis.Jedis;

public class HelloJedis {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		System.out.println(jedis.ping());
		
		jedis.close();
	}
}
