package com.expensetracker.util;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthHelper {

	 private final String issuer;
	    private final long expiration;
	    private final Algorithm algorithm;
	    private final PasswordEncoder encoder;
	    private final long refreshTokenExpiration;

	    public AuthHelper(Builder builder) {
		this.issuer = builder.issuer;
		this.algorithm = builder.algorithm;
		this.encoder = builder.passwordEncoder;
		this.expiration = builder.expiration;
		this.refreshTokenExpiration = builder.refreshTokenExpiration;

	    }

	    public String encode(String password) {
		return encoder.encode(password);
	    }

	    public Boolean matches(String candidate, String hash) {
		return encoder.matches(candidate, hash);
	    }

	    public String createJWT(String id) {
		Instant now = Instant.now();
		Instant expirationTime = now
			.plusSeconds(expiration);

		return JWT.create().withIssuer(issuer)
			.withSubject(id).withIssuedAt(now)
			.withExpiresAt(expirationTime)
			.sign(algorithm);
	    }

	    public String createRefreshJWT(String id) {
		Instant now = Instant.now();
		Instant expirationTime = now
			.plusSeconds(refreshTokenExpiration);

		return JWT.create().withIssuer(issuer)
			.withSubject(id).withIssuedAt(now)
			.withExpiresAt(expirationTime)
			.sign(algorithm);
	    }

	    public String getIdFromToken(String refreshToken) {
		DecodedJWT decodedJWT = JWT.require(algorithm)
			.build().verify(refreshToken);

		return decodedJWT.getSubject();
	    }

	    public static class Builder {
		private String issuer;
		private long expiration;
		private Algorithm algorithm;
		private PasswordEncoder passwordEncoder;
		public long refreshTokenExpiration;

		public Builder() {

		}

		public Builder issuer(String issuer) {
		    this.issuer = issuer;
		    return this;
		}

		public Builder expiration(long expiration) {
		    this.expiration = expiration;
		    return this;
		}

		public Builder refreshTokenExpiration(
			long refreshTokenExpiration) {
		    this.refreshTokenExpiration = refreshTokenExpiration;
		    return this;
		}

		public Builder algorithm(Algorithm algorithm) {
		    this.algorithm = algorithm;
		    return this;
		}

		public Builder passwordEncoder(
			PasswordEncoder passwordEncoder) {
		    this.passwordEncoder = passwordEncoder;
		    return this;
		}

		public AuthHelper build() {
		    return new AuthHelper(this);
		}
	    }
	}
