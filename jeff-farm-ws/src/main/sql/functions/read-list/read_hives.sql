DROP FUNCTION IF EXISTS read_hives;
CREATE FUNCTION read_hives
	( IN user_id INT
	, IN farm_id INT
	)
RETURNS SETOF hives
AS
$body$
	SELECT
		  h.id
		, h.farm_id
		, h.name
		, h.queen_color
		, h.created_date
		, h.modified_date
	FROM hives AS h
	WHERE permission_check_farm(set_user_id(read_hives.user_id), read_hives.farm_id)
		AND h.farm_id = read_hives.farm_id;
$body$
LANGUAGE SQL;
