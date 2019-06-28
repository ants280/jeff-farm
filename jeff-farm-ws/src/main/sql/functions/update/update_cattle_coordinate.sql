DROP FUNCTION IF EXISTS update_cattle_coordinate;
CREATE FUNCTION update_cattle_coordinate
	( IN user_id INT
	, IN id INT
	, IN latitude DOUBLE PRECISION
	, IN longitude DOUBLE PRECISION
	, IN display_order INT
	)
RETURNS VOID
AS
$body$
	BEGIN
		IF permission_check_cattle_coordinate(set_user_id(update_cattle_coordinate.user_id), update_cattle_coordinate.id) THEN
			UPDATE cattle_coordinates AS cc
				SET
					  latitude = update_cattle_coordinate.latitude
					, longitude = update_cattle_coordinate.longitude
					, display_order = update_cattle_coordinate.display_order
				WHERE cc.id = update_cattle_coordinate.id;
		END IF;
	END
$body$
LANGUAGE plpgsql;
