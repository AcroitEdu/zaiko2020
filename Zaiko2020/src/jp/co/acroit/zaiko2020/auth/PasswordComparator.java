package jp.co.acroit.zaiko2020.auth;

public class PasswordComparator {

    //TODO: メソッド名 comparator->compare
    //TODO: staticを取る
    // ログインフォームで入力されたパスワードを、実際のパスワードと照合する
    public static boolean comparator(String formPass, String pass) {
        if(formPass.equals(pass)) {
            return true;
        } else {
            return false;
        }
    }

}
