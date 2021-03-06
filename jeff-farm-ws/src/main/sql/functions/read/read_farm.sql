DROP FUNCTION IF EXISTS read_farm;
CREATE FUNCTION read_farm
	( IN user_id INT
	, IN id INT
	)
RETURNS SETOF farms
AS
$body$
	BEGIN
		IF permission_check_farm(set_user_id(read_farm.user_id), read_farm.id) THEN
			RETURN QUERY
			SELECT
				  f.id
				, f.name
				, f.location
				, f.created_date
				, f.modified_date
			FROM farms AS f
			WHERE f.id = read_farm.id;
		END IF;
	END
$body$
LANGUAGE plpgsql;
