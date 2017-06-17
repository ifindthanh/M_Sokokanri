package vn.com.nsmv.authentication;

import java.util.*;

import io.jsonwebtoken.*;
import vn.com.nsmv.entity.*;

/**
 */
public class JwtUtil
{

	private String secret = "sokokanri_secret";
	private final static String SOKO_CD = "sokoCd";
	private final static String ROLE = "role";

	/**
	 * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
	 * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
	 *
	 * @param token the JWT token to parse
	 * @return the User object extracted from specified token or null if a token is invalid.
	 */
	public User parseToken(Claims body)
	{
		User user = new User();
		user.setEmail(body.getSubject());
		//we can set more useful information
		return user;

	}

	public Claims getClaimsFromToken(String token)
	{
		return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
	}

	/**
	 * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
	 * User object. Tokens validity is infinite.
	 *
	 * @param u the user for which the token will be generated
	 * @return the JWT token
	 */
	public String generateToken(User user)
	{
		Claims claims = Jwts.claims().setSubject(user.getEmail());
		claims.put(JwtUtil.ROLE, "A");
		claims.setExpiration(new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000));
		//expiration is 12 hours
		return Jwts.builder()
			.setClaims(claims)
			.signWith(SignatureAlgorithm.HS512, this.secret)
			.compact();
	}
}