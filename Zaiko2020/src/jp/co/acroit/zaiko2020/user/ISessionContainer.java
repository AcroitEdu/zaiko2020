package jp.co.acroit.zaiko2020.user;

public interface ISessionContainer {
    IUser getUser();
    void destroy();
}
