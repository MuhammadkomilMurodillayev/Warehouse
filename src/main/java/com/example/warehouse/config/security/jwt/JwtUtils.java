package com.example.warehouse.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtUtils {

    public static Integer expiry = 10_000_000;
    public static String secret = "ASDQW#@!$#@%$#DSFSDFRT%$#%34543terg45%^%$";

    public static Date getExpiry() {
        return new Date(System.currentTimeMillis() + expiry);
    }

    public static Algorithm getAlgorithm()   {
        return Algorithm.HMAC256(secret.getBytes());
    }

    public static JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }

}
