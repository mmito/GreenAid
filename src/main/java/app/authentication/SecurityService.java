package app.authentication;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}

