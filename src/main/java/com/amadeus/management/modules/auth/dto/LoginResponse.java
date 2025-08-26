package com.amadeus.management.modules.auth.dto;

public record LoginResponse(String token, String tokenType, long expiresInSeconds) {}
