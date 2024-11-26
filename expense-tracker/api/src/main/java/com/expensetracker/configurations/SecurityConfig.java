package com.expensetracker.configurations;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

import com.auth0.jwt.algorithms.Algorithm;
import com.expensetracker.util.AuthHelper;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Value("${expensetracker.cors.enabled}")
	private boolean corsEnabled;
    @Value("${expensetracker.auth.rounds}")
    private int rounds;
    @Value("${expensetracker.auth.issuer}")
    private String issuer;
    @Value("${expensetracker.auth.secret}")
    private String secret;
    @Value("${expensetracker.auth.tokenExpiration}")
    private long tokenExpiration;
    @Value("${expensetracker.auth.refreshTokenExpiration}")
    private long refreshTokenExpiration;

    @Bean
    AuthHelper authHelper() {
	Algorithm algorithm = Algorithm.HMAC256(secret);
	PasswordEncoder encoder = new BCryptPasswordEncoder(
		rounds);

	return new AuthHelper.Builder().algorithm(algorithm)
		.passwordEncoder(encoder).issuer(issuer)
		.expiration(tokenExpiration)
		.refreshTokenExpiration(
			refreshTokenExpiration)
		.build();
    }
    
    //TODO
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(corsCustomizer())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
            		.requestMatchers("/account/sign-up", "/account/sign-in", "/account/refresh-token", "/swagger-ui/**", "/v3/api-docs/**").permitAll() 
            		.requestMatchers("/expenses/**", "/expense-categories").authenticated()
            		.anyRequest().authenticated()
                    )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // Enable JWT authentication
        return http.build();
    }


    private Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
        return corsEnabled ? Customizer.withDefaults() : cors -> cors.disable();
    }

    @Bean
    JwtDecoder jwtDecoder() throws Exception {
	SecretKeySpec key = new SecretKeySpec(
		secret.getBytes(), "HmacSHA256");
	return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
