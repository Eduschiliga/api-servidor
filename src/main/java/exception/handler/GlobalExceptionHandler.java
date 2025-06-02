package exception.handler;

import exception.customizadas.usuario.DadosInvalidosException;
import exception.customizadas.usuario.SenhaException;
import exception.customizadas.jwt.TokenJWTException;
import exception.customizadas.usuario.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiError> handlerUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsuarioNaoEncontradoException.class)
  public ResponseEntity<ApiError> handlerUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DadosInvalidosException.class)
  public ResponseEntity<ApiError> handlerDadosInvalidosException(DadosInvalidosException ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SenhaException.class)
  public ResponseEntity<ApiError> handlerSenhaException(SenhaException ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TokenJWTException.class)
  public ResponseEntity<ApiError> handlerTokenJWTException(TokenJWTException ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());

    return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handlerGenericException(Exception ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
