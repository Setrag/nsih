# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table jeu (
  nom                       varchar(255) not null,
  constraint pk_jeu primary key (nom))
;

create table news (
  id                        integer not null,
  titre                     varchar(255),
  contenu                   varchar(255),
  type_n                    integer,
  auteur_email              varchar(255),
  constraint ck_news_type_n check (type_n in (0,1,2)),
  constraint pk_news primary key (id))
;

create table project (
  id                        bigint not null,
  name                      varchar(255),
  folder                    varchar(255),
  constraint pk_project primary key (id))
;

create table score (
  id                        integer not null,
  valeur                    float,
  auteur_email              varchar(255),
  jeu_nom                   varchar(255),
  constraint pk_score primary key (id))
;

create table task (
  id                        bigint not null,
  title                     varchar(255),
  done                      boolean,
  due_date                  timestamp,
  assigned_to_email         varchar(255),
  folder                    varchar(255),
  project_id                bigint,
  constraint pk_task primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;


create table project_user (
  project_id                     bigint not null,
  user_email                     varchar(255) not null,
  constraint pk_project_user primary key (project_id, user_email))
;
create sequence jeu_seq;

create sequence news_seq;

create sequence project_seq;

create sequence score_seq;

create sequence task_seq;

create sequence user_seq;

alter table news add constraint fk_news_auteur_1 foreign key (auteur_email) references user (email) on delete restrict on update restrict;
create index ix_news_auteur_1 on news (auteur_email);
alter table score add constraint fk_score_auteur_2 foreign key (auteur_email) references user (email) on delete restrict on update restrict;
create index ix_score_auteur_2 on score (auteur_email);
alter table score add constraint fk_score_jeu_3 foreign key (jeu_nom) references jeu (nom) on delete restrict on update restrict;
create index ix_score_jeu_3 on score (jeu_nom);
alter table task add constraint fk_task_assignedTo_4 foreign key (assigned_to_email) references user (email) on delete restrict on update restrict;
create index ix_task_assignedTo_4 on task (assigned_to_email);
alter table task add constraint fk_task_project_5 foreign key (project_id) references project (id) on delete restrict on update restrict;
create index ix_task_project_5 on task (project_id);



alter table project_user add constraint fk_project_user_project_01 foreign key (project_id) references project (id) on delete restrict on update restrict;

alter table project_user add constraint fk_project_user_user_02 foreign key (user_email) references user (email) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists jeu;

drop table if exists news;

drop table if exists project;

drop table if exists project_user;

drop table if exists score;

drop table if exists task;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists jeu_seq;

drop sequence if exists news_seq;

drop sequence if exists project_seq;

drop sequence if exists score_seq;

drop sequence if exists task_seq;

drop sequence if exists user_seq;

