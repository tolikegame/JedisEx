package ttl;

import java.util.Scanner;

import redis.clients.jedis.Jedis;

public class TestJedisTTL2 {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		String code = returnAuthCode();
		System.out.println("Auth code is: " + code);
		
		jedis.set("Member:M0001", code);
		jedis.expire("Member:M0001", 10);
		
		Scanner sc = new Scanner(System.in);
		System.out.println("請輸入驗證碼：");
		String str = sc.next();
		
		// 假設會員點擊驗證信
		String tempAuth = jedis.get("Member:M0001");
		if (tempAuth == null) {
			System.out.println("連結信已逾時，請重新申請");
		} else if (str.equals(tempAuth)){
			System.out.println("驗證成功!");
		} else {
			System.out.println("驗證有誤，請重新申請");
		}

		sc.close();
		jedis.close();
	}

	private static String returnAuthCode() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 8; i++) {
			int condition = (int) (Math.random() * 3) + 1;
			switch (condition) {
			case 1:
				char c1 = (char)((int)(Math.random() * 26) + 65);
				sb.append(c1);
				break;
			case 2:
				char c2 = (char)((int)(Math.random() * 26) + 97);
				sb.append(c2);
				break;
			case 3:
				sb.append((int)(Math.random() * 10));
			}
		}
		return sb.toString();
	}

}
