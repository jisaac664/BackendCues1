package SystemITR.ApiJosue2B.Empleados.Exeptions;

public class NotFoundEmplead extends RuntimeException {
    public NotFoundEmplead(String message) {
        super(message);
    }
}
