-- DROP TABLE hives;
-- SELECT  * FROM hives LIMIT 0;

CREATE TABLE IF NOT EXISTS hives
(
	ID INT PRIMARY KEY AUTO_INCREMENT,
    farmID INT ,
    name VARCHAR(255) NOT NULL,
    createdDate DATETIME DEFAULT CURRENT_TIMESTAMP,
	modifiedDate DATETIME ON UPDATE CURRENT_TIMESTAMP,
	active BIT DEFAULT 1
);