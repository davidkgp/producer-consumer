CREATE TABLE IF NOT EXISTS `path`.dump (
	id INT auto_increment NOT NULL,
	keyname varchar(100) NULL,
	keyvalue LONG VARCHAR NULL,
	CONSTRAINT NewTable_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `path`.dump1 (
	id INT auto_increment NOT NULL,
	keyvalue LONG VARCHAR NULL,
	CONSTRAINT NewTable_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;