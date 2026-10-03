package SystemITR.ApiJosue2B.Empleados.Exeptions;

public class ErrorException extends RuntimeException {
    public ErrorException(String message) {
        super(message);
    }
}
