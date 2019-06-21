package com.github.ants280.jeff.farm.ws.model;

public class UserPasswordReplacement extends CrudItem<UserPasswordReplacement>
{
	public static final String OLD_PASSWORD_COLUMN = "old_user_password";
	public static final String NEW_PASSWORD_COLUMN = "new_user_password";
	private String oldPassword;
	private String newPassword;

	@Override
	protected UserPasswordReplacement getThis()
	{
		return this;
	}

	public String getOldPassword()
	{
		return oldPassword;
	}

	public UserPasswordReplacement setOldPassword(String oldPassword)
	{
		this.oldPassword = oldPassword;
		return this;
	}

	public String getNewPassword()
	{
		return newPassword;
	}

	public UserPasswordReplacement setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
		return this;
	}
}
