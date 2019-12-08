package com.bootTest.Utils;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtils {
	private static final String SECRET = "jwtsecretdemo";  //密钥
	private static final long EXPIRATION = 3600L;          //过期时间1天
	private static final String ISS = "jtcs";              //签发者
	
	//创建token
	public static String createToken(String username){
		return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET) //签名算法
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())                    //签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();

	}
	
	//解析token
	private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
	}
	
	// 是否已过期
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }
    
    // 从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

}
