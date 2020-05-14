package jp.co.acroit.zaiko2020.data;

import jp.co.acroit.zaiko2020.user.IUser;

public interface IUserDataAccess extends IDataAccess {
    IUser findById(String id);
}
