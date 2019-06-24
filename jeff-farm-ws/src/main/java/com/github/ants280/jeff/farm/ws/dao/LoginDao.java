package com.github.ants280.jeff.farm.ws.dao;

import com.github.ants280.jeff.farm.ws.JeffFarmWsException;
import com.github.ants280.jeff.farm.ws.model.User;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Singleton
public class LoginDao
{
	private final UserDao userDao;
	private final UserIdDao userIdDao;
	private final HttpServletRequest request;

	@Inject
	public LoginDao(
		UserDao userDao, UserIdDao userIdDao, HttpServletRequest request)
	{
		this.userDao = userDao;
		this.userIdDao = userIdDao;
		this.request = request;
	}

	public String login(User user) throws ServletException
	{
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null)
		{
			if (request.getUserPrincipal() != null
				&& request.getRemoteUser() != null
				&& request.getRemoteUser().equals(user.getUserName()))
			{
				return oldSession.getId();
			}
			oldSession.invalidate();
		}
		
		// The session MUST be crated before the login call.
		// See org.apache.catalina.authenticator.AuthenticatorBase.register()
		String jsessionId = request.getSession(true).getId();

		request.login(user.getUserName(), user.getPassword());

		User actualUser = userDao.read(user.getUserName());
		userIdDao.setUserId(actualUser.getId());

		if (!userIdDao.hasUserRole())
		{
			throw new JeffFarmWsException("No access");
		}

		return jsessionId;
	}

	public void logout()
	{
		HttpSession session = request.getSession(false);

		if (session != null)
		{
			session.invalidate();
		}
	}
}
