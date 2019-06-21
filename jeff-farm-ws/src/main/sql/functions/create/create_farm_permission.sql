DROP FUNCTION IF EXISTS create_farm_permission;
CREATE FUNCTION create_farm_permission
	( IN user_id INT
	, IN farm_id INT
	, IN permission_user_id INT -- the user to ad the permission for
	, OUT id INT
	)
AS
$body$
	BEGIN
		IF permission_check_farm(set_user_id(create_farm_permission.user_id), create_farm_permission.farm_id) THEN
			INSERT INTO farm_permissions
				( farm_id
				, user_id
				)
			SELECT
				  f.id
				, u.id
			FROM farms AS f
			CROSS JOIN users AS u
			WHERE f.id = create_farm_permission.farm_id
				AND u.id = create_farm_permission.permission_user_id
			RETURNING LASTVAL()
			INTO create_farm_permission.id;
		END IF;
	END
$body$
LANGUAGE plpgsql;
