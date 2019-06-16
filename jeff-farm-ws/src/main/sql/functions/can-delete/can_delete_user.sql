DROP FUNCTION IF EXISTS can_delete_user;
CREATE FUNCTION can_delete_user
    ( IN user_id INT -- the user requesting the delete
    , IN id INT -- the user to delete
    , OUT can_delete BOOLEAN
	)
AS
$body$
	SELECT permission_check_user(set_user_id(can_delete_user.user_id), can_delete_user.id)
	AND
	NOT EXISTS
	( -- cannot delete user if they are tho only one with access to a farm
		SELECT fp.farm_id
		FROM farm_permissions AS fp
		JOIN farm_permissions AS fp2 ON fp.farm_id = fp2.farm_id
		WHERE fp.user_id = can_delete_user.id
		GROUP BY fp.farm_id
		HAVING COUNT(*) = 1
	);
$body$
LANGUAGE SQL;
