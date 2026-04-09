package fr.ToulouseMetropole.mathis.practical_work_backend.model.utils;

public record TokenResponse(String access_token, String token_type, int expires_in) {
}