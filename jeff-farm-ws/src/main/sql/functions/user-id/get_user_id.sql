DROP FUNCTION IF EXISTS get_user_id;
CREATE OR REPLACE FUNCTION get_user_id
	(
	)
RETURNS INT
AS
$body$
	SELECT CAST(current_setting('jeff_farm_ws.user_id') AS INT);
$body$
LANGUAGE SQL;
