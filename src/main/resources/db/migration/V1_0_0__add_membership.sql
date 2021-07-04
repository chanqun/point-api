create table membership
(
    seq               bigint       not null
        primary key,
    membership_id     varchar(255) null,
    membership_name   varchar(255) null,
    membership_status varchar(255) null,
    point             int          null,
    start_date        datetime(6)  null,
    user_id           varchar(255) null
);