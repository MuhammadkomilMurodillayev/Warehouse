alter table auth_user drop column if exists firsname;

alter table auth_user add column if not exists firstname varchar;