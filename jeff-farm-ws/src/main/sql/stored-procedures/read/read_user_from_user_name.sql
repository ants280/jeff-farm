--DELIMITER $$

DROP PROCEDURE IF EXISTS read_user_from_user_name$$

CREATE PROCEDURE read_user_from_user_name (
	IN user_name VARCHAR(20))

	BEGIN
		SELECT u.id
			, u.user_name
			, u.first_name
			, u.last_name
			, u.created_date
			, u.modified_date
		FROM users AS u
		WHERE u.user_name = user_name;
	END$$

-- DELIMITER ;
