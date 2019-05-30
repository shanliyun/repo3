package com.wynlink.park_platform.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class DataUtils {
	
	/**
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	public final static String toStr(Object obj){
		return toStr(obj, "");
	}
	
	/**
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	public final static String toStr(Object obj, String dfv){
		return null != obj ? obj.toString() : dfv;
	}

	public static String genRandomNum(Integer size) {
		String code = "";
		Random rand = new Random();// 生成随机数
		for (int a = 0; a < size; a++) {
			code += rand.nextInt(10);
		}

		return code;
	}

	/**
	 * 微信支付签名算法sign
	 * 
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String createSign(String key, SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, Object>> es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, Object>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			String k = entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)/* && !"device_id".equals(k)*/) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		System.out.println(sb.toString());
		String sign = sha256_HMAC(sb.toString(), key).toUpperCase();
				
		return sign;
	}

	/**
	 * 将加密后的字节数组转换成字符串
	 *
	 * @param b
	 *            字节数组
	 * @return 字符串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toLowerCase();
	}

	/**
	 * sha256_HMAC加密
	 * 
	 * @param message
	 *            消息
	 * @param secret
	 *            秘钥
	 * @return 加密后字符串
	 */
	public static String sha256_HMAC(String message, String secret) {
		String hash = "";
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
			hash = byteArrayToHexString(bytes);
		} catch (Exception e) {
			System.out.println("Error HmacSHA256 ===========" + e.getMessage());
		}
		return hash;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(sha256_HMAC(
				"car_number=苏E12345&carno_color=BLUE&device_id=gz001&nonce_str=bkOizCxfukPfEiyJ&timestamp=1548037402&key=ViSXtRjcvoNkmLZizJZkd7BcWWkLpAPV",
				"ViSXtRjcvoNkmLZizJZkd7BcWWkLpAPV").toUpperCase());
	}

}
