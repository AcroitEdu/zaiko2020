package jp.co.acroit.zaiko2020.user;

public interface ISessionContainerFactory {
    ISessionContainer create(IUser user);
}
