package backend.cohive.Observer;

public interface Observer {
    void notify(String message, String productName, String email);
}
