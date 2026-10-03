package SystemITR.ApiJosue2B.Usuarios.Exeptions;

public class ErrorException extends RuntimeException {
    public ErrorException(String message) {
        super(message);
    }
}
