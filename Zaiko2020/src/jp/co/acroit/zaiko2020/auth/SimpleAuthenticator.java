package jp.co.acroit.zaiko2020.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.co.acroit.zaiko2020.data.IUserDataAccess;
import jp.co.acroit.zaiko2020.user.ISessionContainer;
import jp.co.acroit.zaiko2020.user.ISessionContainerFactory;
import jp.co.acroit.zaiko2020.user.IUser;

public class SimpleAuthenticator implements IAuthenticator {
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }
    public void setUsers(IUserDataAccess users) {
        this.users = users;
    }
    public void setSessionFactory(ISessionContainerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    IUserDataAccess users;
    @Autowired
    ISessionContainerFactory sessionFactory;
    @Override
    public ISessionContainer authenticate(String id, String password) throws Exception {
        IUser user = users.findById(id);
        if(user == null) {
            throw new UserNotFoundException(String.format("User %s not found!", id));
        }
        if(user.authenticate(encoder, password)) {
            return sessionFactory.create(user);
        }else {
            throw new AuthenticationException("Authentication failed");
        }
    }

}
