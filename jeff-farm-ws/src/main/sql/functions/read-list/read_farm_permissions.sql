DROP FUNCTION IF EXISTS read_farm_permissions;
CREATE FUNCTION read_farm_permissions
	( IN user_id INT
	, IN farm_id INT
	)
RETURNS SETOF farm_permissions_readonly
AS
$body$
	BEGIN
		IF permission_check_farm(set_user_id(read_farm_permissions.user_id), read_farm_permissions.farm_id) THEN
			RETURN QUERY
			SELECT
				  fp.id
				, CAST(fp.farm_id AS VARCHAR(20))
				, u.user_name
				, fp.created_date
				, fp.modified_date
			FROM farm_permissions AS fp
			JOIN users AS u ON fp.user_id = u.id
			WHERE fp.farm_id = read_farm_permissions.farm_id;
		END IF;
	END
$body$
LANGUAGE plpgsql;
