package db;

public class DbException extends RuntimeException { // Tratamento de erros
    private static final long serialVersionUID = 1L;

    public DbException(String message) {
        super(message);
    }
}
