package com.framgia.websocket.utils;

import com.framgia.websocket.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import static com.framgia.websocket.constants.SecurityConstants.EXPIRATION_TIME;
import static com.framgia.websocket.constants.SecurityConstants.SECRET;

public class Jwt {

    public String signDefault(String payload) {
        return Jwts.builder()
                .setSubject(payload)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();


    }

    public String parseDefault(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
