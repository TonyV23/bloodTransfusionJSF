create database bloodtransfusion;

use bloodtransfusion;

create table donneur(
	id int not null auto_increment primary key,
	nom_donneur varchar (100) not null,
	prenom_donneur varchar (100) not null,
	sexe_donneur varchar (100) not null,
	groupe_sanguin_donneur varchar (100)not null
);

create table receveur (
	id int not null auto_increment primary key,
	nom_receveur varchar (100) not null,
	prenom_receveur varchar (100) not null,
	sexe_receveur varchar (100) not null,
	groupe_sanguin_receveur varchar (100)not null
);

