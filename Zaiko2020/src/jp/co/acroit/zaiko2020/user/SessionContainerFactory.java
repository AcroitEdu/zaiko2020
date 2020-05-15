package jp.co.acroit.zaiko2020.user;

/**
 * @author k-oishi
 *
 */
public class SessionContainerFactory implements ISessionContainerFactory {

    @Override
    public ISessionContainer create(IUser user) {
        return new SessionContainer(user);
    }

}
