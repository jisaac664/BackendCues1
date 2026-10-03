package SystemITR.ApiJosue2B.Departamentos.Exeptions;

public class NotFoundDeparment extends RuntimeException {
    public NotFoundDeparment(String message) {
        super(message);
    }
}
