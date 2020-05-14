/**
 *
 */
package jp.co.acroit.zaiko2020.auth;

import jp.co.acroit.zaiko2020.user.ISessionContainer;

/**
 * @author k-oishi
 *
 */
public interface IAuthenticator {
    ISessionContainer authenticate(String id, String password);
}
