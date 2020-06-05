package jp.co.acroit.zaiko2020.auth;

public class PasswordComparator {

    // ログインフォームで入力されたパスワードを、実際のパスワードと照合する
    public boolean compare(String formPass, String pass) {
        if(formPass.equals(pass)) {
            return true;
        } else {
            return false;
        }
    }

}
