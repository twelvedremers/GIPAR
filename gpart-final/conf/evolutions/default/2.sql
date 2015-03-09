

INSERT INTO "PUBLIC"."MIEMBRO"
VALUES( 1 ,'admin','1234','fotos_perfil/defecto.png','A','24036810','carlos','marcano','dmarcano@hotmail.es','M','2013-01-01 11:21:57.566',1);
INSERT INTO "PUBLIC"."MIEMBRO"
VALUES( 2 ,'Cordi1','members','fotos_perfil/defecto.png','C','23036520','luis','coronado','lcoronado@hotmail.com','M','2013-02-01 11:21:57.566',1);
INSERT INTO "PUBLIC"."MIEMBRO"
VALUES( 3 ,'cordi2','robotica','fotos_perfil/defecto.png','C','28037820','marta','lopez','lmarta@gmail.com','F','2014-01-01 11:21:57.566',1);
INSERT INTO "PUBLIC"."MIEMBRO"
VALUES( 4 ,'meber1','123','fotos_perfil/defecto.png','N','24036750','carlose','marcane','dmaro@hotmail.com','M','2014-09-01 11:21:57.566',1);
INSERT INTO "PUBLIC"."MIEMBRO"
VALUES( 5 ,'meber2','124','fotos_perfil/defecto.png','N','22136810','michael','stefan','gatica_miau@hotmail.es','F','2014-07-01 11:21:57.566',1);
INSERT INTO "PUBLIC"."MIEMBRO"
VALUES( 6 ,'meber3','123arbol','fotos_perfil/defecto.png','N','24035410','marta','Susana','martaSusana@hotmail.es','F','2015-01-01 11:21:57.566',1);
INSERT INTO "PUBLIC"."MIEMBRO"
VALUES( 7 ,'menber4','25434','fotos_perfil/defecto.png','N','24037610','chichico','chic','chichico@hotmail.es','M','2014-02-01 11:21:57.566',1);


INSERT INTO "PUBLIC"."ESTUDIANTE"
VALUES( 4 ,6);
INSERT INTO "PUBLIC"."ESTUDIANTE"
VALUES( 5 ,7);
INSERT INTO "PUBLIC"."ESTUDIANTE"
VALUES( 7 ,4);


INSERT INTO "PUBLIC"."EGRESADOS"
VALUES( 6 ,'Director de CVG','robotica');
INSERT INTO "PUBLIC"."EGRESADOS"
VALUES( 3 ,'Programador senior','programacion');


INSERT INTO "PUBLIC"."PROFESOR"
VALUES( 1,'robotica');
INSERT INTO "PUBLIC"."PROFESOR"
VALUES( 2 ,'programacion');
INSERT INTO "PUBLIC"."PROFESOR"
VALUES( 3 ,'programacion');



INSERT INTO "PUBLIC"."CORDINADORES"
VALUES( 1,'2013-05-01',null);
INSERT INTO "PUBLIC"."CORDINADORES"
VALUES( 2 ,'2013-01-01',null);
INSERT INTO "PUBLIC"."CORDINADORES"
VALUES( 3 ,'2013-01-01',null);



INSERT INTO "PUBLIC"."AREA_INTERES"
VALUES('robotica',1),('robotica',3),('robotica',5),('robotica',6),('robotica',7);
INSERT INTO "PUBLIC"."AREA_INTERES"
VALUES('programacion',2),('programacion',3),('automatizacion',5),('automatizacion',6),('automatizacion',7),('programacion',6);
INSERT INTO "PUBLIC"."AREA_INTERES"
VALUES('direccion',4),('robotica',4),('programacion web',5),('programacion web',6),('programacion web',7);



INSERT INTO "PUBLIC"."PROYECTO"
VALUES( 1 ,'robotica','robot velocista que usa un nuevo metodo de traccion para aumentar la velocidad','guayanito 2.0','2014-04-02 11:21:57.566','A',4),
( 8 ,'robotica','lector de tension para cables electricos de alta potencia de descarga','spider_G','2014-04-20 11:21:57.566','A',3),
( 2 ,'programacion','evento de para promocionar nuevas aplicaciones android hecha por los estudiantes','inovandroid','2014-01-10 11:21:57.566','F',3),
( 3 ,'robotica','sumo ninja diseñado para golpear ladrones','sumito','2014-03-10 11:21:57.566','A',6),
( 4 ,'automatizacion','sistema de automatizacion de lineas de produccion de juguetes','cokGIPAT','2013-12-10 11:21:57.566','F',5),
( 5 ,'robotica','evento en la ULA de competencias de roborica','robot-matic 2014','2014-06-10 11:21:57.566','A',6),
( 6 ,'programacion','evento union programada (Angular y boo)','Union Prog 2014','2014-03-10 11:21:57.566','A',4),
( 7 ,'social','Evento de apoyo a a la universidad GIPAR responde',' GIPAR responde','2014-01-12 11:21:57.566','F',2);

INSERT INTO "PUBLIC"."EVENTO"
VALUES( 2 ,500.80,'UNEG-ATLANTICO'),
( 5 ,2000.00,'ULA MERIDA'),
( 6 ,300,'UCAB GUAYANA'),
( 7 ,3000,'UNEG UNEG-ATLANTICO');


INSERT INTO "PUBLIC"."RECURSOS"
VALUES( '6 laptos',1),('6 laptos',2),( 'bus universitario',6),( '6 laptos',4),( '6 laptos',3),( '6 laptos',8),( '2 arduinos',1),( '2 arduinos',2),( '2 arduinos',3),( '2 arduinos',4),( '6 laptos',5),( '6 laptos',6),( '6 laptos',7),( 'bus universitario',5),
( '50 platos comida',1),( '50 platos comida',2),( '50 platos comida',7),( '50 platos comida',6),( '50 platos comida',5),( '5 software licenciados ',4),('5 software licenciados ',3),( '1000 bs.f',4),( '500 bs.f',2),( '500 bs.f',3),( '2000 bs.f',1),( '2000 bs.f',5),( '300 bs.f',6),( '3000 bs.f',7);

INSERT INTO "PUBLIC"."TELEFONOS"
VALUES( '02865838071',1),('04243874631',1),( '02869512706',2),( '02866454321',3),( '02866454563',4),('04245632276',5),( '04245839064',6),( '04264958321',7),( '04245634980',4),( '02865424530',7),( '02865334421',3);

INSERT INTO "PUBLIC"."RECONOCIMIENTO"
VALUES( 'reconocimiento por parte de CANTV',4),('Reportaje en Nueva Prensa acerca del evento',4),( '1° lugar en competencia inter universidad guayana',2);

INSERT INTO "PUBLIC"."PROGRAMACION"
VALUES(1, 1, '2015-02-04 14:00:00.00' , '2015-02-04 20:00:00.00' , '2015-02-04 14:00:00.00' , 'reunion para planificacion de proyecto' , 2 ),(2, 1, '2015-02-12 16:00:00.00' , '2015-02-20 16:00:00.00' , '2015-02-20 16:00:00.00','desarrollo del controlador y de la arquitectura del robot' , 5),(3, 1 , '2015-03-05 12:00:00.00' , '2015-03-10 12:00:00.00' , null, 'montado de de pista para pruebas del robot' , 2),(4, 1 , '2015-03-11 16:00:00.00' , '2015-03-12 16:00:00.00'  , null , 'presentacion del prototipo del robot' ,7 ),(5, 1 , '2014-04-01 11:21:57.566' , '2014-04-02 11:21:57.566' ,null  , 'presentacion del robot en evento de promocion y finalizacion del proyecto' , 5),
(1, 2, '2014-01-8 8:21:57.566' , '2014-01-8 8:21:57.566' , '2014-01-8 8:21:57.566' , 'primer dia del evento-presentacion del SUBA' , 1 ),(2, 2, '2014-01-9 8:21:57.566' , '2014-01-9 11:21:57.566'  , '2014-01-9 8:21:57.566' , 'segundo dia del evento- presentacion de nuevas app' , 3),(3, 2 , '2014-01-10 08:21:57.566' , '2014-01-10 11:21:57.566' , '2014-01-10 8:21:57.566' , 'tercer dia del evento-presentacion de Gipar proyectos' , 5),
(1, 3, '2014-01-10 11:21:57.566' ,'2014-01-20 11:21:57.566'  , null , 'reunion de diseño de sumo usando plano viejo' , 5 ),(2, 3, '2015-01-21 16:00:00.00' ,'2015-02-20 16:00:00.00'  , null , 'desarrollo del robot ' , 4),(3, 3 , '2015-02-04 16:00:00.00' ,  '2014-03-10 11:21:57.566',null  , 'presentacion de prototipo del robot ' , 3),
(1, 5,'2014-06-8 11:21:57.566'  , '2014-06-8 11:21:57.566'  , null,'primer dia de evento: traslado incripcion y eventos de programacion' , 1),(2, 5, '2014-06-9 11:21:57.566' , '2014-06-9 11:21:57.566' , null,'2 dia de evento : competencias ' , 3),(3, 5 ,'2014-06-10 11:21:57.566'  ,  '2014-06-10 11:21:57.566', null , '3 dia de evento:entrega de premios y salida' , 4),
 (1, 7, '2014-01-11 11:21:57.566' , '2014-01-11 16:21:57.566' , null , 'primer dia de reparacion del GIPAR' ,3 ),(2, 7, '2014-01-12 16:21:57.566' , '2014-01-12 16:21:57.566' , null , 'segundo dia de reparacion del GIPAR y exteriores' , 2);



INSERT INTO "PUBLIC"."ASISTENCIA_PROYECTO" VALUES(7,1,1),(5,1,1),(2,1,1),(7,3,1),(5,3,1),(4,3,1),(3,3,1),(7,4,1),(6,4,1),(2,4,1),(1 ,2,1), (2 ,2,0),(4 ,2,1),(5 ,2,1);

insert into asistencia_evento values (1 ,2,'promocion'), (2 ,2,'promocion'),(4 ,2,'desarrollador'),(5 ,2,'desarrollador'),
( 1,5,'acompañante'), (2 ,5,'acompañante'),(4 ,5,'participante'),(5 ,5,'participante'),
( 3,6,'acompañante'), (2 ,6,'trasporte'),(7 ,6,'ponente'),(5 ,6,'ponente'),
( 7,7,'limpieza de las computadoras'), (6 ,7,'pintura'),(5 ,7,'trasporte'),(4 ,7,'pintura'),(3 ,7,'mantenimiento');
