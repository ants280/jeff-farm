DROP FUNCTION IF EXISTS create_hive_inspection_group;
CREATE FUNCTION create_hive_inspection_group
	( IN user_id INT
	, IN farm_id INT
	, IN notes VARCHAR(255)
	, OUT id INT
	)
AS
$body$
	BEGIN
		IF permission_check_farm(set_user_id(create_hive_inspection_group.user_id), create_hive_inspection_group.farm_id) THEN
			INSERT INTO hive_inspection_groups
				( farm_id
				, notes
				)
			SELECT
				  create_hive_inspection_group.farm_id
				, create_hive_inspection_group.notes
			FROM farms AS f
			WHERE f.id = create_hive_inspection_group.farm_id
			RETURNING LASTVAL()
			INTO create_hive_inspection_group.id;
		END IF;
	END
$body$
LANGUAGE plpgsql;
