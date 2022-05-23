create table auth_user
(
    id              varchar primary key,
    username        varchar not null unique,
    password        varchar not null,
    fullName        varchar,
    firsName        varchar,
    lastName        varchar,
    gender          varchar,
    organization_id varchar references organization(id),
    superAdmin      boolean default false,
    created_at      timestamp,
    created_by      varchar default -1,
    updated_at      timestamp,
    updated_by      varchar,
    deleted         boolean default false,
    status          int2    default 0
);

create table auth_user_warehouse
(
    auth_user_id varchar references auth_user (id),
    warehouse_id varchar references warehouse (id),
    auth_role    varchar not null
);