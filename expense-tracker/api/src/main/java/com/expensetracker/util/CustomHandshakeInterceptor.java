package com.expensetracker.util;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {

private final AuthHelper authHelper;

public CustomHandshakeInterceptor(AuthHelper authHelper) {
    this.authHelper = authHelper;
}

@Override
public boolean beforeHandshake(
		ServerHttpRequest request,
		ServerHttpResponse response,
        org.springframework.web.socket.WebSocketHandler wsHandler,
        Map<String, Object> attributes) {
	System.out.println("Starting WebSocket handshake...");
    
	// Log all headers
    System.out.println("Request headers: " + request.getHeaders());
    
 // Extract token from query parameters
    String token = null;
    if (request.getURI().getQuery() != null) {
        String[] queryParams = request.getURI().getQuery().split("&");
        for (String param : queryParams) {
            if (param.startsWith("token=")) {
                token = param.substring("token=".length());
                break;
            }
        }
    }
    System.out.println("Extracted token from query parameters: " + token);

        
    if (token != null ) {

        try {
            String userId = authHelper.getIdFromToken(token);
            attributes.put("userId", userId); // Add userId to WebSocket attributes
            System.out.println("Token validated for user: " + userId);
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false; 
        }
    } else {
        System.out.println("No token provided or invalid format");
        return false; 
    }

    return true;
}

@Override
public void afterHandshake(
		ServerHttpRequest request,
		ServerHttpResponse response,
        org.springframework.web.socket.WebSocketHandler wsHandler,
        Exception ex) {
    System.out.println("Handshake completed!");
}

}