package com.github.ants280.jeff.farm.ws.dao;

import com.github.ants280.jeff.farm.ws.JeffFarmWsException;
import com.github.ants280.jeff.farm.ws.model.User;
import com.github.ants280.jeff.farm.ws.Property;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

@Singleton
public class LoginDao
{
	private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
	private final UserDao userDao;
	private final HttpServletRequest request;

	@Inject
	public LoginDao(UserDao userDao, @Context HttpServletRequest request)
	{
		this.userDao = userDao;
		this.request = request;
	}

	public void login(User user) throws ServletException
	{
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null)
		{
			if (request.getUserPrincipal() != null
				&& request.getRemoteUser() != null
				&& request.getRemoteUser().equals(user.getUserName()))
			{
				return;
			}
			oldSession.invalidate();
		}
		
		// The session MUST be crated before the login call.
		// See org.apache.catalina.authenticator.AuthenticatorBase.register()
		HttpSession session = request.getSession(true);

		request.login(user.getUserName(), user.getPassword());

		User actualUser = userDao.read(user.getUserName());
		session.setAttribute(USER_ID_SESSION_ATTRIBUTE, actualUser.getId());

		if (!request.isUserInRole(Property.USER_ROLE.getValue()))
		{
			throw new JeffFarmWsException("No access");
		}
	}

	public void logout()
	{
		HttpSession session = request.getSession(false);

		if (session != null)
		{
			session.invalidate();
		}
	}

	public int getUserId()
	{
		HttpSession session = request.getSession(false);

		if (session == null)
		{
			throw new IllegalArgumentException("No Session");
		}

		return (int) session
				.getAttribute(USER_ID_SESSION_ATTRIBUTE);
	}
}
