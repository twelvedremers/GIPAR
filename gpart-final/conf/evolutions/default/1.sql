# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table area_interes (
  area                      VARCHAR(30) not null,
  miembro_id                integer,
  constraint pk_area_interes primary key (area, miembro_id))
;

create table area_articulo (
  area                      VARCHAR(20) not null,
  cod_articulo              integer,
  constraint pk_area_articulo primary key (area, cod_articulo))
;

create table articulo (
  cod_articulo              integer not null,
  titulo                    VARCHAR(45) not null,
  contenido                 TEXT not null,
  fecha                     DateTime not null,
  proyecto_id               integer,
  constraint pk_articulo primary key (cod_articulo))
;

create table asistencia_evento (
  miembro_id                integer,
  evento_id                 integer,
  comision                  VARCHAR(45) not null,
  constraint pk_asistencia_evento primary key (miembro_id, evento_id))
;

create table asistencia_proyecto (
  miembro_id                integer,
  proyecto_id               integer,
  estado_m                  boolean not null,
  constraint pk_asistencia_proyecto primary key (miembro_id, proyecto_id))
;

create table cordinadores (
  cod_g                     integer not null,
  fecha_ingreso             DateTime not null,
  fecha_salida              DateTime,
  constraint pk_cordinadores primary key (cod_g))
;

create table disponibilidad (
  cod_G                     integer,
  dia                       varchar(3),
  hora                      int,
  constraint ck_disponibilidad_dia check (dia in ('Jue','Mar','Lun','Vie','Mie')),
  constraint pk_disponibilidad primary key (cod_G, dia, hora))
;

create table egresados (
  cod_g                     integer not null,
  ocupacion                 VARCHAR(25) not null,
  area                      VARCHAR(25) not null,
  constraint pk_egresados primary key (cod_g))
;

create table estudiante (
  cod_g                     integer not null,
  semestre                  integer not null,
  constraint pk_estudiante primary key (cod_g))
;

create table evento (
  id_evento                 integer not null,
  costo_total               float,
  ubicacion                 VARCHAR(80) not null,
  constraint pk_evento primary key (id_evento))
;

create table miembro (
  cod_g                     integer not null,
  username                  VARCHAR(30) not null,
  password                  VARCHAR(30) not null,
  foto_url                  VARCHAR(100),
  nivel_a                   varchar(1) not null,
  cedula                    VARCHAR(11) not null,
  nombre                    VARCHAR(45) not null,
  apellidos                 VARCHAR(45) not null,
  correo                    VARCHAR(50) not null,
  sexo                      varchar(1) not null,
  fecha_ingreso             DateTime not null,
  estado                    boolean not null,
  constraint ck_miembro_nivel_a check (nivel_a in ('C','N','A')),
  constraint ck_miembro_sexo check (sexo in ('F','M')),
  constraint uq_miembro_cedula unique (cedula),
  constraint uq_miembro_correo unique (correo),
  constraint pk_miembro primary key (cod_g))
;

create table profesor (
  cod_g                     integer not null,
  area                      VARCHAR(25),
  constraint pk_profesor primary key (cod_g))
;

create table programacion (
  id_programacion           integer,
  proyecto_id               integer,
  fecha_programada_inicial  DateTime not null,
  fecha_programada_final    DateTime,
  fecha_real                DateTime,
  actividad                 VARCHAR(80) not null,
  encargado_cod_g           integer,
  constraint pk_programacion primary key (id_programacion, proyecto_id))
;

create table proyecto (
  id                        integer not null,
  area                      VARCHAR(20) not null,
  descripcion               VARCHAR(200) not null,
  nombre                    VARCHAR(45) not null,
  tiempo_estimado           timestamp,
  estado                    varchar(1) not null,
  coordinador_cod_g         integer,
  constraint ck_proyecto_estado check (estado in ('P','A','F')),
  constraint pk_proyecto primary key (id))
;

create table reconocimiento (
  premio                    VARCHAR(80) not null,
  proyecto_id               integer,
  constraint pk_reconocimiento primary key (premio))
;

create table recursos (
  recurso                   VARCHAR(50),
  proyecto_id               integer,
  constraint pk_recursos primary key (recurso, proyecto_id))
;

create table reporte (
  n_reporte                 integer not null,
  fecha                     DateTime not null,
  contenido                 TEXT not null,
  proyecto_id               integer,
  tipo_reporte              VARCHAR(30) not null,
  constraint pk_reporte primary key (n_reporte))
;

create table reunion (
  numero_Reunion            integer not null,
  fecha_reunion             DateTime not null,
  moderador                 integer,
  constraint uq_reunion_fecha_reunion unique (fecha_reunion),
  constraint pk_reunion primary key (numero_Reunion))
;

create table telefonos (
  telefono                  varchar(255),
  cod_g                     integer,
  constraint pk_telefonos primary key (telefono, cod_g))
;

create table puntos_fijos (
  punto                     varchar(255),
  reunion_id                integer,
  constraint pk_puntos_fijos primary key (punto, reunion_id))
;

create table puntos_varios (
  punto                     varchar(255),
  reunion_id                integer,
  constraint pk_puntos_varios primary key (punto, reunion_id))
;


create table miembro_reunion (
  miembro_cod_g                  integer not null,
  reunion_numero_Reunion         integer not null,
  constraint pk_miembro_reunion primary key (miembro_cod_g, reunion_numero_Reunion))
;

create table miembro_articulo (
  miembro_cod_g                  integer not null,
  articulo_cod_articulo          integer not null,
  constraint pk_miembro_articulo primary key (miembro_cod_g, articulo_cod_articulo))
;
create sequence area_interes_seq;

create sequence area_articulo_seq;

create sequence articulo_seq;

create sequence asistencia_evento_seq;

create sequence asistencia_proyecto_seq;

create sequence cordinadores_seq;

create sequence disponibilidad_seq;

create sequence egresados_seq;

create sequence estudiante_seq;

create sequence evento_seq;

create sequence miembro_seq;

create sequence profesor_seq;

create sequence programacion_seq;

create sequence proyecto_seq;

create sequence reconocimiento_seq;

create sequence recursos_seq;

create sequence reporte_seq;

create sequence reunion_seq;

create sequence telefonos_seq;

create sequence puntos_fijos_seq;

create sequence puntos_varios_seq;

alter table area_interes add constraint fk_area_interes_miembro_1 foreign key (miembro_id) references miembro (cod_g) on delete restrict on update restrict;
create index ix_area_interes_miembro_1 on area_interes (miembro_id);
alter table area_articulo add constraint fk_area_articulo_articulo_2 foreign key (cod_Articulo) references articulo (cod_articulo) on delete restrict on update restrict;
create index ix_area_articulo_articulo_2 on area_articulo (cod_Articulo);
alter table articulo add constraint fk_articulo_proyecto_3 foreign key (proyecto_id) references proyecto (id) on delete restrict on update restrict;
create index ix_articulo_proyecto_3 on articulo (proyecto_id);
alter table asistencia_evento add constraint fk_asistencia_evento_miembro_4 foreign key (miembro_id) references miembro (cod_g) on delete restrict on update restrict;
create index ix_asistencia_evento_miembro_4 on asistencia_evento (miembro_id);
alter table asistencia_evento add constraint fk_asistencia_evento_evento_5 foreign key (evento_id) references evento (id_evento) on delete restrict on update restrict;
create index ix_asistencia_evento_evento_5 on asistencia_evento (evento_id);
alter table asistencia_proyecto add constraint fk_asistencia_proyecto_miembro_6 foreign key (miembro_id) references miembro (cod_g) on delete restrict on update restrict;
create index ix_asistencia_proyecto_miembro_6 on asistencia_proyecto (miembro_id);
alter table asistencia_proyecto add constraint fk_asistencia_proyecto_proyect_7 foreign key (proyecto_id) references proyecto (id) on delete restrict on update restrict;
create index ix_asistencia_proyecto_proyect_7 on asistencia_proyecto (proyecto_id);
alter table cordinadores add constraint fk_cordinadores_miembro_8 foreign key (cod_G) references miembro (cod_g) on delete restrict on update restrict;
create index ix_cordinadores_miembro_8 on cordinadores (cod_G);
alter table disponibilidad add constraint fk_disponibilidad_miembro_9 foreign key (cod_G) references miembro (cod_g) on delete restrict on update restrict;
create index ix_disponibilidad_miembro_9 on disponibilidad (cod_G);
alter table egresados add constraint fk_egresados_miembro_10 foreign key (cod_G) references miembro (cod_g) on delete restrict on update restrict;
create index ix_egresados_miembro_10 on egresados (cod_G);
alter table estudiante add constraint fk_estudiante_miembro_11 foreign key (cod_G) references miembro (cod_g) on delete restrict on update restrict;
create index ix_estudiante_miembro_11 on estudiante (cod_G);
alter table evento add constraint fk_evento_proyecto_12 foreign key (id_Evento) references proyecto (id) on delete restrict on update restrict;
create index ix_evento_proyecto_12 on evento (id_Evento);
alter table profesor add constraint fk_profesor_miembro_13 foreign key (cod_G) references miembro (cod_g) on delete restrict on update restrict;
create index ix_profesor_miembro_13 on profesor (cod_G);
alter table programacion add constraint fk_programacion_proyecto_14 foreign key (proyecto_id) references proyecto (id) on delete restrict on update restrict;
create index ix_programacion_proyecto_14 on programacion (proyecto_id);
alter table programacion add constraint fk_programacion_encargado_15 foreign key (encargado_cod_g) references miembro (cod_g) on delete restrict on update restrict;
create index ix_programacion_encargado_15 on programacion (encargado_cod_g);
alter table proyecto add constraint fk_proyecto_coordinador_16 foreign key (coordinador_cod_g) references miembro (cod_g) on delete restrict on update restrict;
create index ix_proyecto_coordinador_16 on proyecto (coordinador_cod_g);
alter table reconocimiento add constraint fk_reconocimiento_proyecto_17 foreign key (proyecto_id) references proyecto (id) on delete restrict on update restrict;
create index ix_reconocimiento_proyecto_17 on reconocimiento (proyecto_id);
alter table recursos add constraint fk_recursos_proyecto_18 foreign key (proyecto_id) references proyecto (id) on delete restrict on update restrict;
create index ix_recursos_proyecto_18 on recursos (proyecto_id);
alter table reporte add constraint fk_reporte_proyecto_19 foreign key (proyecto_id) references proyecto (id) on delete restrict on update restrict;
create index ix_reporte_proyecto_19 on reporte (proyecto_id);
alter table reunion add constraint fk_reunion_miembro_20 foreign key (moderador) references miembro (cod_g) on delete restrict on update restrict;
create index ix_reunion_miembro_20 on reunion (moderador);
alter table telefonos add constraint fk_telefonos_miembro_21 foreign key (cod_G) references miembro (cod_g) on delete restrict on update restrict;
create index ix_telefonos_miembro_21 on telefonos (cod_G);
alter table puntos_fijos add constraint fk_puntos_fijos_reunion_22 foreign key (reunion_id) references reunion (numero_Reunion) on delete restrict on update restrict;
create index ix_puntos_fijos_reunion_22 on puntos_fijos (reunion_id);
alter table puntos_varios add constraint fk_puntos_varios_reunion_23 foreign key (reunion_id) references reunion (numero_Reunion) on delete restrict on update restrict;
create index ix_puntos_varios_reunion_23 on puntos_varios (reunion_id);



alter table miembro_reunion add constraint fk_miembro_reunion_miembro_01 foreign key (miembro_cod_g) references miembro (cod_g) on delete restrict on update restrict;

alter table miembro_reunion add constraint fk_miembro_reunion_reunion_02 foreign key (reunion_numero_Reunion) references reunion (numero_Reunion) on delete restrict on update restrict;

alter table miembro_articulo add constraint fk_miembro_articulo_miembro_01 foreign key (miembro_cod_g) references miembro (cod_g) on delete restrict on update restrict;

alter table miembro_articulo add constraint fk_miembro_articulo_articulo_02 foreign key (articulo_cod_articulo) references articulo (cod_articulo) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists area_interes;

drop table if exists area_articulo;

drop table if exists articulo;

drop table if exists miembro_articulo;

drop table if exists asistencia_evento;

drop table if exists asistencia_proyecto;

drop table if exists cordinadores;

drop table if exists disponibilidad;

drop table if exists egresados;

drop table if exists estudiante;

drop table if exists evento;

drop table if exists miembro;

drop table if exists miembro_reunion;

drop table if exists profesor;

drop table if exists programacion;

drop table if exists proyecto;

drop table if exists reconocimiento;

drop table if exists recursos;

drop table if exists reporte;

drop table if exists reunion;

drop table if exists telefonos;

drop table if exists puntos_fijos;

drop table if exists puntos_varios;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists area_interes_seq;

drop sequence if exists area_articulo_seq;

drop sequence if exists articulo_seq;

drop sequence if exists asistencia_evento_seq;

drop sequence if exists asistencia_proyecto_seq;

drop sequence if exists cordinadores_seq;

drop sequence if exists disponibilidad_seq;

drop sequence if exists egresados_seq;

drop sequence if exists estudiante_seq;

drop sequence if exists evento_seq;

drop sequence if exists miembro_seq;

drop sequence if exists profesor_seq;

drop sequence if exists programacion_seq;

drop sequence if exists proyecto_seq;

drop sequence if exists reconocimiento_seq;

drop sequence if exists recursos_seq;

drop sequence if exists reporte_seq;

drop sequence if exists reunion_seq;

drop sequence if exists telefonos_seq;

drop sequence if exists puntos_fijos_seq;

drop sequence if exists puntos_varios_seq;

