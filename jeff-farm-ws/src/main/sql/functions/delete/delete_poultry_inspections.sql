DROP FUNCTION IF EXISTS delete_poultry_inspections;
CREATE FUNCTION delete_poultry_inspections
	( IN group_id INT
	, IN user_id INT
	)
RETURNS VOID
AS
$body$
	DELETE
	FROM poultry_inspections AS pi
	WHERE permission_check_poultry_inspection(set_user_id(user_id), id)
		AND pi.group_id = delete_poultry_inspections.group_id;
$body$
LANGUAGE SQL;
