DROP FUNCTION IF EXISTS create_hive;
CREATE FUNCTION create_hive
	( IN user_id INT
	, IN farm_id INT
	, IN name VARCHAR(255)
	, IN queen_color INT
	, OUT id INT
	)
AS
$body$
	INSERT INTO hives
		( farm_id
		, name
		, queen_color
		)
	SELECT
		  create_hive.farm_id
		, create_hive.name
		, create_hive.queen_color
	FROM farms AS f
	WHERE permission_check_farm(set_user_id(create_hive.user_id), create_hive.farm_id)
		AND f.id = create_hive.farm_id
	RETURNING id;
$body$
LANGUAGE SQL;
