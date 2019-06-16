DROP FUNCTION IF EXISTS permission_check_hive_inspection;
CREATE FUNCTION permission_check_hive_inspection
	( IN user_id INT
	, IN hive_inspection_id INT
	, OUT permission_check BOOLEAN
	)
AS
$body$
	BEGIN
		SELECT EXISTS
		(
			SELECT fp.user_id, hi.id
			FROM farm_permissions AS fp
			JOIN hives AS h ON fp.farm_id = h.farm_id
			JOIN hive_inspections AS hi ON h.id = hi.hive_id
			WHERE fp.user_id = permission_check_hive_inspection.user_id
				AND hi.id = permission_check_hive_inspection.hive_inspection_id
		)
		INTO permission_check;

		IF NOT permission_check THEN
			RAISE EXCEPTION 'User % does not have access to hive inspection %.', permission_check_hive_inspection.user_id, permission_check_hive_inspection.hive_inspection_id;
		END IF;
	END
$body$
LANGUAGE plpgsql;
