--<ScriptOptions statementTerminator="!"/>

CREATE SCHEMA Schema!

CREATE TABLE Schema.Product (
		pid CHAR(5) NOT NULL,
		pname VARCHAR(32)
	)
	DATA CAPTURE NONE!

CREATE TABLE Schema.Supplier (
		sid CHAR(5) NOT NULL,
		sname VARCHAR(32)
	)
	DATA CAPTURE NONE!

CREATE TABLE Schema.Supply (
		pid CHAR(5) NOT NULL,
		sid CHAR(5) NOT NULL,
		qty SMALLINT NOT NULL
	)
	DATA CAPTURE NONE!

ALTER TABLE Schema.Product ADD CONSTRAINT PRODUCT_PK PRIMARY KEY
	(pid)!

ALTER TABLE Schema.Supplier ADD CONSTRAINT Supplier_PK PRIMARY KEY
	(sid)!

ALTER TABLE Schema.Supply ADD CONSTRAINT Supply_PK PRIMARY KEY
	(pid,
	 sid)!

