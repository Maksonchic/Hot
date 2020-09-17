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
