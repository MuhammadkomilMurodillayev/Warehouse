create table organization
(
    id          varchar primary key,
    name        varchar not null,
    description varchar,
    logo_path   varchar,
    created_at  timestamp,
    created_by  varchar default -1,
    updated_at  timestamp,
    updated_by  varchar,
    deleted     boolean default false,
    status      int2    default 0
);
create table region
(
    id varchar primary key ,
    name varchar not null ,
    latitude        double precision,
    longitude       double precision,
    created_at      timestamp,
    created_by      varchar default -1,
    updated_at      timestamp,
    updated_by      varchar,
    deleted         boolean default false,
    status          int2    default 0
);
create table warehouse
(
    id              varchar primary key,
    name            varchar not null,
    organization_id varchar references organization (id),
    region_id       varchar references region(id),
    latitude        double precision,
    longitude       double precision,
    created_at      timestamp,
    created_by      varchar default -1,
    updated_at      timestamp,
    updated_by      varchar,
    deleted         boolean default false,
    status          int2    default 0
);
create table container
(
    id          varchar primary key,
    wareHouseId varchar references warehouse (id),
    created_at  timestamp,
    created_by  varchar default -1,
    updated_at  timestamp,
    updated_by  varchar,
    deleted     boolean default false,
    status      int2    default 0
);
create table product
(
    id varchar primary key,
    name        varchar,
    description varchar,
    imagePath   varchar,
    price       double precision,
    containerId varchar references container (id),
    created_at  timestamp,
    created_by  varchar default -1,
    updated_at  timestamp,
    updated_by  varchar,
    deleted     boolean default false,
    status      int2    default 0

);
create table product_category
(
    id varchar primary key,
    name varchar,
    code varchar,
    created_at  timestamp,
    created_by  varchar default -1,
    updated_at  timestamp,
    updated_by  varchar,
    deleted     boolean default false,
    status      int2    default 0
);
