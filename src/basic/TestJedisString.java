package basic;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;
import vo.Pen;

public class TestJedisString {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		// 一般字串資料
		jedis.set("myKey", "Hello, Redis~");
		System.out.println("myKey is: " + jedis.get("myKey"));
		
		jedis.append("myKey", "安安你好");
		System.out.println("Append result: " + jedis.get("myKey"));
		System.out.println("myKey's length: " + jedis.strlen("myKey"));
		System.out.println("Get range: " + jedis.getrange("myKey", 7, 11));
		System.out.println("Get range(2): " + jedis.getrange("myKey", -18, -14));
		
		
		
		// JSON格式
		Pen myPen = new Pen(1, "SKB", 10);
		Pen yourPen = new Pen(2, "Pentel", 50);
		List<Pen> penList = new ArrayList<>();
		penList.add(myPen);
		penList.add(yourPen);
		
		String jObjStr = new JSONObject(myPen).toString();
		String jArrayStr = new JSONArray(penList).toString();
		StringBuilder sb = new StringBuilder("pen:").append(myPen.getId());
		jedis.set(sb.toString(), jObjStr);
		jedis.set("pens", jArrayStr);
		System.out.println(jedis.get(sb.toString()));
		System.out.println(jedis.get("pens"));
		
		// 多筆key處理
		jedis.mset("key1", "value1", "key2", "value2", "key3", "value3");
		List<String> data = jedis.mget("key1", "key2", "key3");
		for (String str : data)
			System.out.println(str);
		
		// 整數
		for (int i = 1; i <= 100; i++) {
			jedis.incr("num");
		}
		jedis.decr("num");
		System.out.println(jedis.get("num"));
		System.out.println(jedis.decrBy("num", 12));
		System.out.println(jedis.incr("data"));   // ??
		
		// 位元
		//       b           a            r
		//   01100010     01100001     01110010
		//   
		//                 OR運算
		//
        //       a           a            r
		//   01100001     01100001     01110010
		//   ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		//   
		//       c           a            r
		//   01100011     01100001     01110010
		
		
		jedis.set("foo", "bar");
		System.out.println(jedis.getbit("foo", 6));
		jedis.setbit("foo", 6, false);
		jedis.setbit("foo", 7, true);
		System.out.println(jedis.get("foo"));
		
		jedis.set("foo1", "bar");
		jedis.set("foo2", "aar");
		jedis.bitop(BitOP.OR, "result", "foo1", "foo2");
		System.out.println(jedis.get("result"));
		
		jedis.close();
	}

}
