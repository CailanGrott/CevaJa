package com.fundatec.cevaja.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;

import static org.springframework.http.ProblemDetail.forStatusAndDetail;

public class RegraDeNegocioException extends ErrorResponseException {

    public RegraDeNegocioException(HttpStatusCode status, String titulo, String detalhe) {
        super(status, construirProblemDetail(status, detalhe, titulo), null);
    }

    public static ProblemDetail construirProblemDetail(HttpStatusCode status, String detail, String titulo) {
        ProblemDetail problemDetail = forStatusAndDetail(status, detail);
        problemDetail.setTitle(titulo);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
