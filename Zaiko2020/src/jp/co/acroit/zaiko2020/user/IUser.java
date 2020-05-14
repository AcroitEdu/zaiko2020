package jp.co.acroit.zaiko2020.user;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface IUser {
    long getNumber();
    String getId();
    boolean authenticate(PasswordEncoder encoder, String password);
}
