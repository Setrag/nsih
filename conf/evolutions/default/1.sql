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
  auteur_email              varchar(255),
  constraint pk_news primary key (id))
;

create table score (
  id                        integer not null,
  valeur                    float,
  auteur_email              varchar(255),
  jeu_nom                   varchar(255),
  constraint pk_score primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  is_admin                  boolean,
  constraint pk_user primary key (email))
;

create sequence jeu_seq;

create sequence news_seq;

create sequence score_seq;

create sequence user_seq;

alter table news add constraint fk_news_auteur_1 foreign key (auteur_email) references user (email) on delete restrict on update restrict;
create index ix_news_auteur_1 on news (auteur_email);
alter table score add constraint fk_score_auteur_2 foreign key (auteur_email) references user (email) on delete restrict on update restrict;
create index ix_score_auteur_2 on score (auteur_email);
alter table score add constraint fk_score_jeu_3 foreign key (jeu_nom) references jeu (nom) on delete restrict on update restrict;
create index ix_score_jeu_3 on score (jeu_nom);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists jeu;

drop table if exists news;

drop table if exists score;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists jeu_seq;

drop sequence if exists news_seq;

drop sequence if exists score_seq;

drop sequence if exists user_seq;

