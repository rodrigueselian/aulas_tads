package br.edu.ifsul.tsi.aulas_tads.api.infra.security.jwt;

import lombok.Data;

@Data
public class JwtLoginInput {
    private String username;
    private String password;
}