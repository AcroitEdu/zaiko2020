package jp.co.acroit.zaiko2020.user;

public interface IUserFactory {
    IUser create(long number, String id, String password);
}
