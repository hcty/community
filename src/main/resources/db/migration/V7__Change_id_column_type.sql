alter table QUESTION alter column ID BIGINT default NULL auto_increment;
alter table USER alter column ID BIGINT default NULL auto_increment;
ALTER TABLE QUESTION ALTER COLUMN CREATOR BIGINT DEFAULT NOT NULL ;
ALTER TABLE COMMENT ALTER COLUMN COMMENTATOR BIGINT DEFAULT NOT NULL ;