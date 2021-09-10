package com.restkeeper.utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;

import java.util.UUID;


public class MD5CryptUtil
{

	public static String getSalts(String password) {
		String[] salts = password.split("\\$");
		if (salts.length < 1) {
			return "";
		}
		String mysalt = "";
		for (int i = 1; i < 3; i++) {
			mysalt += "$" + salts[i];
		}
		mysalt += "$";
		return mysalt;
	}
    
	public static void main(String[] args) {
		//摘要
		System.out.println(DigestUtils.md5Hex("admin"+System.currentTimeMillis()));
		
		System.out.println(DigestUtils.md5Hex("admin"+System.currentTimeMillis()));
		
		System.out.println("---------"+ UUID.randomUUID());
		
		System.out.println(Md5Crypt.md5Crypt("admin".getBytes()));
		System.out.println(Md5Crypt.md5Crypt("admin".getBytes()));

	}

}
