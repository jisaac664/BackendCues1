package SystemITR.ApiJosue2B.Usuarios.Exeptions;

public class NotFoundUser extends RuntimeException {
    public NotFoundUser(String message) {
        super(message);
    }
}
