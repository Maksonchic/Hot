create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values(0);

create table statuses (
    status_guid VARCHAR(50) PRIMARY KEY NOT NULL,
    status_name VARCHAR(20) NOT NULL
);

create table units (
    unit_guid VARCHAR(50) PRIMARY KEY NOT NULL,
    unit_status VARCHAR(50) NOT NULL,
    book_from DATE NOT NULL,
    book_to DATE NOT NULL,
    room_num INTEGER NOT NULL
);

create table user_role (
    user_id BIGINT not null,
    roles varchar(255) not null
);

create table usr (
    id BIGINT not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

alter table user_role add constraint user_role_user_fk foreign key (user_id) references usr (id);
