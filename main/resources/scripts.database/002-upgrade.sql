create table users
(
    id varchar(13) not null primary key,
    created datetime not null,
    name    varchar (255) not null comment 'name of the user',
    surname varchar (255) not null comment 'surname of the user'
    email   varchar (255) not null comment 'email or the user'
    password varchar (255) not null comment 'password of the user, encoded in bcrypt,
    last_login datetime null comment 'last Login of the user'
    );

    create table roles
    (
    id bigint(20) unsigned not null auto_increment,
    name varchar(255) not null comment 'name of the user',
    primary key (id)
    );

    create table users_with_roles
    (
    user_id varchar(13) not null references users (id),
    role_id bigint (20) unsigned not null references roles(id)
    );

    insert into users (id, created, name, surname,email,password)
    values('OAYZDSFS4534534', now(), 'user-name', 'user@gmail.com',
    '12345678'),
    ('OAYZDhusfhus324', now(), 'admin-name', 'adminr@gmail.com',
        '$2a$12$rYw0FXGrtJR8KpW/btwxbeuCY77fXHCyUT.EhxFDzDzlJYaLumFtS'
    );

    insert into roles (name)
    values ('USER'),
            ('ADMIN');

    insert into users_with_roles(user_id, role_id)
    values((select users.id from users where email='user@gmail.com'),(select roles.id from roles where name= 'USER')),
           ((select users.id from users where email='admin@gmail.com'),(select roles.id from roles where name= 'USER')),
           ((select users.id from users where email='admin@gmail.com'),(select roles.id from roles where name= 'ADMIN'));
