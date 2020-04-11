create table notification
(
	id bigint,
	nitifier bigint not null,
	receiver bigint not null,
	outerId bigint not null,
	type int not null,
	gmt_create bigint not null,
	status int default 0 not null,
	constraint natification_pk
		primary key (id)
);