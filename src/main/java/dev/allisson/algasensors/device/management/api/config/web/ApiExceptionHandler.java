package dev.allisson.algasensors.device.management.api.config.web;

import dev.allisson.algasensors.device.management.api.client.SensorMonitoringBadGatewayException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(exception = {
            SocketTimeoutException.class,
            ConnectException.class,
            ClosedChannelException.class
    })
    public ResponseEntity<Object> handleConnectionException(
            IOException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final HttpStatus gatewayTimeoutStatus = HttpStatus.GATEWAY_TIMEOUT;
        final ProblemDetail problemDetail = ProblemDetail.forStatus(gatewayTimeoutStatus);

        problemDetail.setTitle("Gateway Timeout");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return handleExceptionInternal(ex, problemDetail, headers, gatewayTimeoutStatus, request);
    }

    @ExceptionHandler(exception = SensorMonitoringBadGatewayException.class)
    public ResponseEntity<Object> handleSensorMonitoringBadGatewayException(
            SensorMonitoringBadGatewayException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final HttpStatus gatewayTimeoutStatus = HttpStatus.BAD_GATEWAY;
        final ProblemDetail problemDetail = ProblemDetail.forStatus(gatewayTimeoutStatus);

        problemDetail.setTitle("Bad Gateway");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/bad-gateway"));

        return handleExceptionInternal(ex, problemDetail, headers, gatewayTimeoutStatus, request);
    }

}
