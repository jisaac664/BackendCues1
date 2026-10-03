package SystemITR.ApiJosue2B.Departamentos.Exeptions;

public class ErrorException extends RuntimeException {
    public ErrorException(String message) {
        super(message);
    }
}
