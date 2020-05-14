package jp.co.acroit.zaiko2020.user;

public class UserFactory implements IUserFactory {

    @Override
    public IUser create(long number, String id, String password) {
        return new User(number, id, password);
    }

}
