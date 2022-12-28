package br.edu.ifsul.tsi.aulas_tads.api.infra.security.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgError {
    public String error;
}
