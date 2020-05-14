package jp.co.acroit.zaiko2020.user;

public class SessionContainer implements ISessionContainer {
    /**
     * @param user
     */
    public SessionContainer(IUser user) {
        this.user = user;
    }

    IUser user;

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public void destroy() {
        user = null;
    }

}
