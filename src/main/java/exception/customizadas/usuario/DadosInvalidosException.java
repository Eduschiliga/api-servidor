package exception.customizadas.usuario;

public class DadosInvalidosException extends RuntimeException {
  public DadosInvalidosException(String message) {
    super(message);
  }
}
