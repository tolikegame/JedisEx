package basic;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class TestJedisSet {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		
		
		jedis.sadd("post:1:tags", "Java");
		jedis.sadd("post:2:tags", "Java", "Servlet");
		jedis.sadd("post:3:tags", "Java", "Servlet", "JSP");
		jedis.sadd("tag:Java:posts", "2", "1", "3");
		jedis.sadd("tag:Servlet:posts", "3", "2");
		jedis.sadd("tag:JSP:posts", "3");
		
		for (String str : jedis.smembers("post:3:tags")) {
			System.out.println(str);
		}
		
		System.out.println(jedis.sismember("post:2:tags", "JSP"));
		
		System.out.println("============差集運算=============");
		                       //jedis.sdiff("post:1:tags", "post:2:tags");  ??
		Set<String> diffResult = jedis.sdiff("post:2:tags", "post:1:tags");
		System.out.println(diffResult);
		
		System.out.println("============交集運算=============");
		
		Set<String> interResult = jedis.sinter("tag:Java:posts", "tag:Servlet:posts", "tag:JSP:posts");
		System.out.println(interResult);
		
		System.out.println("============聯集運算=============");
		
		Set<String> unionResult = jedis.sunion("post:1:tags", "post:2:tags", "post:3:tags");
		System.out.println(unionResult);
		
		System.out.println("============其它指令測試=============");
		
		jedis.sadd("candidate", "David", "Lai", "Aliee", "Hugh", "Ping Ru", "Chung-Yu");
		System.out.println(jedis.srandmember("candidate"));
		                      // 回傳List<String>
		System.out.println(jedis.srandmember("candidate", 3));
		
		
		
		jedis.close();
	}

}
