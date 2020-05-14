package jp.co.acroit.zaiko2020.user;

import org.springframework.security.crypto.password.PasswordEncoder;

public class User implements IUser {

    /**
     * @param number 通し番号
     * @param id ログインID
     * @param password ハッシュ化されたパスワード
     */
    public User(long number, String id, String password) {
        this.number = number;
        this.id = id;
        this.password = password;
    }

    long number;
    String id;
    String password;



    @Override
    public long getNumber() {
        return number;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean authenticate(PasswordEncoder encoder, String password) {
        return encoder.matches(password, this.password);
    }


}
