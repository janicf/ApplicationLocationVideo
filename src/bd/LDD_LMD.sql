/* 
Projet#9 IFT2935
Date de remise : 10 avril 2024

Auteure : Joanie Birtz			       Matricule : 20244554
Auteure : Janic Fournel                Matricule : 20201900
Auteure : Karen-Fae Laurin			   Matricule : 20019033
Auteure : Cynthia-Alexandra Vargas 	   Matricule : 20030603
*/

-- Création de la base de données
USE master;
GO

DROP DATABASE IF EXISTS ClubVideo;
CREATE DATABASE ClubVideo;
GO

USE ClubVideo;

-- Drop des tables si nécessaire

/*DROP TABLE IF EXISTS VideoActeur;
DROP TABLE IF EXISTS VideoRealisateur;
DROP TABLE IF EXISTS Acteur;
DROP TABLE IF EXISTS Realisateur;
DROP TABLE IF EXISTS VideoGenre;
DROP TABLE IF EXISTS VideoSujet;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS Sujet;
DROP TABLE IF EXISTS VideoClient;
DROP TABLE IF EXISTS Video;
DROP TABLE IF EXISTS ClientEvenement;
DROP TABLE IF EXISTS Evenement;
DROP TABLE IF EXISTS ClientAdresse;
DROP TABLE IF EXISTS ClientCarte_credit;
DROP TABLE IF EXISTS Adresse;
DROP TABLE IF EXISTS Carte_credit;
DROP TABLE IF EXISTS Abonnement;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS AdminEmp;*/

-- Création des tables

CREATE TABLE AdminEmp (
    id       INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    prenom   VARCHAR(255) NOT NULL,
    nom      VARCHAR(255) NOT NULL,
    mot_de_passe      VARCHAR(255) NOT NULL,
    courriel VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Client (
    id         INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    prenom     VARCHAR(255) NOT NULL,
    nom        VARCHAR(255) NOT NULL,
    dateNaissance  DATE,
    mot_de_passe        VARCHAR(255) NOT NULL,
    courriel   VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Abonnement (
	idClient INT NOT NULL PRIMARY KEY,
	actif BIT DEFAULT 0,
	dateDebut DATE,
	dateFin DATE,
	CONSTRAINT FK_idClient_inAb FOREIGN KEY (idClient) REFERENCES Client(id)
)

CREATE TABLE Adresse (
	id          INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	appartement VARCHAR(10),
	no_civic    VARCHAR(10)  NOT NULL,
	rue         VARCHAR(255) NOT NULL,
	ville       VARCHAR(255) NOT NULL,
	pays        VARCHAR(255) NOT NULL,
	code_postal  VARCHAR(10)  NOT NULL,
	province_etat VARCHAR(255) 					
);

CREATE TABLE Carte_credit (
	id          INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	no_carte    BIGINT NOT NULL,			
	type_Carte  CHAR(4) NOT NULL,
	expiration  DATE NOT NULL,			 
	cvv         SMALLINT NOT NULL,
	CHECK (type_Carte = 'visa' OR type_Carte ='mc' OR type_Carte ='amex'),
	CHECK (LEN(CONVERT(VARCHAR,no_carte)) >= 8 and LEN(CONVERT(VARCHAR,no_carte)) <= 19),
	CHECK (LEN(CONVERT(VARCHAR,cvv)) >= 3 and LEN(CONVERT(VARCHAR,cvv)) <= 4)
);

CREATE TABLE ClientCarte_credit (
	idClient INT NOT NULL,
	idCarte  INT NOT NULL,
	CONSTRAINT PK_clientCarte_credit PRIMARY KEY (idClient, idCarte),
	CONSTRAINT FK_idClient_inCpc FOREIGN KEY (idClient) REFERENCES Client(id),
	CONSTRAINT FK_idCarte_inCpc FOREIGN KEY (idCarte) REFERENCES Carte_credit(id)
);

CREATE TABLE ClientAdresse (
	idClient INT NOT NULL,
	idAdresse    INT NOT NULL,
	CONSTRAINT PK_clientAdresse PRIMARY KEY (idClient, idAdresse),
	CONSTRAINT FK_idClient_inCpa FOREIGN KEY (idClient) REFERENCES Client(id),
	CONSTRAINT FK_idAdr_inCpa FOREIGN KEY (idAdresse) REFERENCES Adresse(id)
);

CREATE TABLE Evenement (
	id        INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	prix      decimal(5,2),
	titre     VARCHAR(255),
	type_event VARCHAR(10),
	description_event   VARCHAR (512),
	Lieu      VARCHAR(255),
	DateDebut DATETIME2,
	DateFin   DATETIME2,
	CHECK (type_event = 'projection' OR type_event ='exposition' OR type_event ='activité' OR type_event ='autre')
);

CREATE TABLE ClientEvenement (
	idClient INT NOT NULL,
	idEvenement  INT NOT NULL,
	CONSTRAINT PK_clientEvenement PRIMARY KEY (idClient, idEvenement),
	CONSTRAINT FK_idClient_inCpe FOREIGN KEY (idClient) REFERENCES Client(id),
	CONSTRAINT FK_idEvent_inCpe FOREIGN KEY (idEvenement) REFERENCES Evenement(id)
);

CREATE TABLE Video (
	id            INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	prix          decimal(5,2)  NOT NULL, 
	titre         VARCHAR(255)  NOT NULL,
	type_video     VARCHAR(20) NOT NULL,
	description_video      VARCHAR(512),
	duree         INT  NOT NULL,	        -- en minutes
	date_sortie    DATE,
	date_ajout     DATE  NOT NULL,
	etat          VARCHAR(10)  NOT NULL,
	gratuit_abonne BIT DEFAULT 0,
	CHECK (etat = 'nouveauté' OR etat ='archivé' OR etat ='normal'),
	CHECK (type_video = 'film' OR type_video = 'serie' OR type_video = 'vidéo amateur' OR type_video = 'autre' )
);

CREATE TABLE VideoClient (
	id       INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	idClient INT NOT NULL,
	idVideo  INT NOT NULL,
	debut_location DATETIME2 DEFAULT GETDATE(),
	CONSTRAINT FK_idClient_inClv FOREIGN KEY (idClient) REFERENCES Client(id),
	CONSTRAINT FK_idVideo_inClv FOREIGN KEY (idVideo) REFERENCES Video(id)
);

CREATE TABLE Sujet ( -- ex: zombie apocalypse, catastrophe naturelle, ...
	id      INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	Nom		VARCHAR(255)
);

CREATE TABLE Genre (	-- ex: horreur, suspense, action, ...
	id      INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	Nom		VARCHAR(255)
);

CREATE TABLE VideoSujet (
	idVideo  INT NOT NULL,
	idSujet    INT NOT NULL,
	CONSTRAINT PK_videoSujet PRIMARY KEY (idVideo, idSujet),
	CONSTRAINT FK_idVideo_inVpt FOREIGN KEY (idVideo) REFERENCES Video(id),
	CONSTRAINT FK_idTag_inVpt FOREIGN KEY (idSujet) REFERENCES Sujet(id)
);

CREATE TABLE VideoGenre (
	idVideo  INT NOT NULL,
	idGenre  INT NOT NULL,
	CONSTRAINT PK_videoGenre PRIMARY KEY (idVideo, idGenre),
	CONSTRAINT FK_idVideo_inVpg FOREIGN KEY (idVideo) REFERENCES Video(id),
	CONSTRAINT FK_idGenre_inVpg FOREIGN KEY (idGenre) REFERENCES Genre(id)
);

CREATE TABLE Realisateur (
    id         INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    prenom     VARCHAR(255) NOT NULL,
    nom        VARCHAR(255) NOT NULL,
	dateNaissance  DATE,
);

CREATE TABLE Acteur (
    id         INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    prenom     VARCHAR(255) NOT NULL,
    nom        VARCHAR(255) NOT NULL,
	dateNaissance  DATE,
);

CREATE TABLE VideoRealisateur (
	idVideo        INT NOT NULL,
	idRealisateur  INT NOT NULL,
	CONSTRAINT PK_videoRealisateur PRIMARY KEY (idVideo, idRealisateur),
	CONSTRAINT FK_idVideo_inVR FOREIGN KEY (idVideo) REFERENCES Video(id),
	CONSTRAINT FK_idRealisateur_inVR FOREIGN KEY (idRealisateur) REFERENCES Realisateur(id)
);

CREATE TABLE VideoActeur (
	idVideo  INT NOT NULL,
	idActeur INT NOT NULL,
	CONSTRAINT PK_videoActeur PRIMARY KEY (idVideo, idActeur),
	CONSTRAINT FK_idVideo_inVA FOREIGN KEY (idVideo) REFERENCES Video(id),
	CONSTRAINT FK_idActeur_inVA FOREIGN KEY (idActeur) REFERENCES Acteur(id)
);

-- Insertion de données

INSERT INTO AdminEmp (prenom, nom, mot_de_passe, courriel) 
VALUES 
('Alice', 'Martin', 'Motdepasse123!', 'alice.martin@gmail.com'),
('Bob', 'Johnson', 'Securite456&', 'bob.johnson@gmail.com'),
('Claire', 'Garcia', 'P@ssw0rd789', 'claire.garcia@gmail.com'),
('David', 'Nguyen', 'MyP@ssw0rd2022', 'david.nguyen@gmail.com'),
('Emily', 'Smith', 'StrongP@ssw0rd2024', 'emily.smith@gmail.com');

INSERT INTO Client (prenom, nom, dateNaissance, mot_de_passe, courriel) 
VALUES 
('Sophie', 'Lefebvre', '1985-03-12', 'Secret123!', 'sophie.lefebvre@hotmail.com'),
('Thomas', 'Dubois', '1990-08-25', 'Password456&', 'thomas.dubois@gmail.ca'),
('Camille', 'Moreau', '1988-11-05', 'SecureP@ss789', 'camille.moreau@live.ca'),
('Lucas', 'Lefort', '1979-06-17', 'P@ssw0rd2022', 'lucas.lefort@hotmail.com'),
('Emma', 'Dupont', '1983-09-30', 'StrongP@ssw0rd2024', 'emma.dupont@hotmail.ca'),
('Louis', 'Durand', '1975-02-14', 'SuperSecret123!', 'louis.durand@live.com'),
('Chloé', 'Martin', '1986-07-08', 'Password123!', 'chloe.martin@hotmail.com'),
('Antoine', 'Garcia', '1992-04-21', 'MyP@ssw0rd456', 'antoine.garcia@hotmail.com'),
('Manon', 'Leroy', '1980-01-03', 'P@ssw0rd789', 'manon.leroy@gmail.com'),
('Hugo', 'Rousseau', '1995-10-12', 'SecretP@ss123', 'hugo.rousseau@gmail.com'),
('Léa', 'Fournier', '1987-12-18', 'StrongPassword123', 'lea.fournier@gmail.ca'),
('Maxime', 'Girard', '1982-05-27', 'P@ssword!123', 'maxime.girard@hotmail.com'),
('Clara', 'Martinez', '1998-03-08', 'SecureP@ssw0rd123', 'clara.martinez@hotmail.com'),
('Enzo', 'Caron', '1969-09-22', 'P@ssw0rd!456', 'enzo.caron@hotmail.com'),
('Lola', 'Robin', '1974-06-11', 'SecretP@ssw0rd!789', 'lola.robin@hotmail.com');

INSERT INTO Abonnement VALUES 
(1, 1, '2023-04-10', '2024-04-10'),
(5, 1, '2024-02-05', '2025-02-05'),
(7, 0, '2023-01-22', '2024-01-22'),
(10, 1, '2022-06-14', '2025-06-14'),
(2, 0, '2021-07-03', '2023-07-03');

INSERT INTO Adresse (appartement, no_civic, rue, ville, pays, code_postal, province_etat) 
VALUES 
(NULL, '123', 'Rue de la Paix', 'Paris', 'France', '75001', NULL),
('Apt. 101', '456', 'Main Street', 'New York', 'USA', '10001', 'New York'),
(NULL, '789', 'Rua Augusta', 'São Paulo', 'Brazil', '01305-100', NULL),
('Unit 201', '321', 'Queen Street', 'Toronto', 'Canada', 'M5V 2A2', 'Ontario'),
(NULL, '987', 'Calle Gran Vía', 'Madrid', 'Spain', '28013', NULL);

INSERT INTO Carte_credit (no_carte, type_Carte, expiration, cvv) 
VALUES 
(4539485403743256, 'visa', '2025-10-01', 123),
(5184720017632076, 'mc', '2026-05-01', 456),
(371449635398431, 'amex', '2024-12-01', 789),
(4024007112843311, 'visa', '2023-08-01', 234),
(5412678345698765, 'mc', '2025-11-01', 567);

INSERT INTO ClientCarte_credit
VALUES
(1,1),
(5,2),
(7,3),
(10,4),
(2,5);

INSERT INTO ClientAdresse
VALUES
(1,2),
(2,3),
(5,4),
(7,5),
(10,1);

INSERT INTO Video (prix, titre, type_video, description_video, duree, date_sortie, date_ajout, etat, gratuit_abonne) 
VALUES 
(12.99, 'Inception', 'film', 'Un film de science-fiction réalisé par Christopher Nolan.', 148, '2010-07-16', '2023-05-10', 'normal', 0),
(9.99, 'Friends', 'serie', 'Une série comique emblématique sur un groupe d''amis à New York.', 22, '1994-09-22', '2022-11-15', 'archivé', 1),
(8.49, 'Breaking Bad', 'serie', 'Une série dramatique sur un professeur de chimie devenu fabricant de meth.', 45, '2008-01-20', '2023-03-25', 'normal', 0),
(14.99, 'Avatar', 'film', 'Un film de science-fiction épique réalisé par James Cameron.', 162, '2009-12-18', '2022-08-05', 'nouveauté', 0),
(10.99, 'Stranger Things', 'serie', 'Une série de science-fiction et d''horreur mettant en scène des phénomènes surnaturels.', 50, '2016-07-15', '2023-01-20', 'normal', 1),
(7.99, 'The Shawshank Redemption', 'film', 'Un film dramatique basé sur une nouvelle de Stephen King.', 142, '1994-09-23', '2022-10-30', 'archivé', 0),
(11.49, 'Game of Thrones', 'serie', 'Une série médiévale-fantastique basée sur les romans de George R.R. Martin.', 60, '2011-04-17', '2023-02-10', 'normal', 0),
(6.99, 'The Matrix', 'film', 'Un film de science-fiction révolutionnaire réalisé par les Wachowski.', 136, '1999-03-31', '2022-09-15', 'archivé', 1),
(13.49, 'Stranger Than Fiction', 'film', 'Une comédie dramatique sur un homme dont la vie est manipulée par un auteur.', 113, '2006-11-10', '2023-04-05', 'normal', 0),
(8.99, 'The Office', 'serie', 'Une série comique basée sur une entreprise fictive de vente de papier.', 22, '2005-03-24', '2022-12-20', 'archivé', 1);

INSERT INTO Sujet (Nom)
VALUES
('Espionnage'),
('Amitié'),
('Années 80'),
('Guerre'),
('Prison');

INSERT INTO Genre (Nom)
VALUES
('Comédie'),
('Science-fiction'),
('Drame'),
('Aventure'),
('Romance');

INSERT INTO VideoSujet
VALUES
(1,1),
(2,2),
(3,5),
(5,3),
(6,5),
(7,4);

INSERT INTO VideoGenre
VALUES
(1,2),
(2,1),
(2,5),
(3,3),
(4,2),
(4,4),
(5,2),
(6,3),
(7,3),
(8,2),
(9,1),
(9,3),
(10,1);

INSERT INTO Realisateur (prenom, nom, dateNaissance)
VALUES 
('Christopher', 'Nolan', '1970-07-30'),
('David', 'Crane', '1957-08-13'), 
('Marta', 'Kauffman', '1956-09-21'), 
('Vince', 'Gilligan', '1967-02-10'),
('James', 'Cameron', '1954-08-16'),
('Matt', 'Duffer', '1984-02-15'),
('Ross', 'Duffer', '1984-02-15'), 
('Frank', 'Darabont', '1959-01-28'),
('Mark', 'Huffam', '1955-04-13'),
('Lana', 'Wachowski', '1965-06-21'),
('Lilly', 'Wachowski', '1967-12-29'),
('Marc', 'Forster', '1969-11-30'),
('Ricky', 'Gervais', '1961-06-25'),
('Stephen', 'Merchant', '1974-11-24');

INSERT INTO Acteur (prenom, nom, dateNaissance)
VALUES 
('Leonardo', 'DiCaprio', '1974-11-11'),
('Jennifer', 'Aniston', '1969-02-11'),
('Courteney', 'Cox', '1964-06-15'),
('Lisa', 'Kudrow', '1963-07-30'),
('Matt', 'LeBlanc', '1967-07-25'),
('Matthew', 'Perry', '1969-08-19'),
('David', 'Schwimmer', '1966-11-02'),
('Bryan', 'Cranston', '1956-03-07'),
('Aaron', 'Paul', '1979-08-27'),
('Sam', 'Worthington', '1976-08-02'),
('Zoe', 'Saldana', '1978-06-19'),
('Sigourney', 'Weaver', '1949-10-08'),
('Millie Bobby', 'Brown', '2004-02-19'),
('Winona', 'Ryder', '1971-10-29'),
('David', 'Harbour', '1975-04-10'),
('Morgan', 'Freeman', '1937-06-01'),
('Emilia', 'Clarke', '1986-10-23'),
('Kit', 'Harington', '1986-12-26'),
('Keanu', 'Reeves', '1964-09-02'),
('Will', 'Ferrell', '1967-07-16'),
('Steve', 'Carell', '1962-08-16'),
('John', 'Krasinski', '1979-10-20');

INSERT INTO VideoRealisateur
VALUES
(1,1),
(2,2),
(2,3),
(3,4),
(4,5),
(5,6),
(5,7),
(6,8),
(7,9),
(8,10),
(8,11),
(9,12),
(10,13),
(10,14);

INSERT INTO VideoActeur
VALUES
(1,1),
(2,2),
(2,3),
(2,4),
(2,5),
(2,6),
(2,7),
(3,8),
(3,9),
(4,10),
(4,11),
(4,12),
(5,13),
(5,14),
(5,15),
(6,16),
(7,17),
(7,18),
(8,19),
(9,20),
(10,21),
(10,22);

INSERT INTO Evenement (prix, titre, type_event, description_event, Lieu, DateDebut, DateFin)
VALUES 
(15.00, 'Projection du film "Inception"', 'projection', 'Une projection spéciale du célèbre film "Inception" de Christopher Nolan.', 'Central Park', '2024-04-15 19:00', '2024-04-15 21:30'),
(5.00, 'Exposition d''art contemporain', 'exposition', 'Une exposition mettant en vedette des œuvres d''art contemporain de divers artistes locaux et internationaux.', '1380 Rue Sherbrooke O, Montréal, QC H3G 1J5', '2024-05-10 10:00', '2024-05-10 18:00'),
(20.00, 'Atelier de poterie', 'activité', 'Un atelier interactif où les participants apprendront les techniques de base de la poterie et pourront créer leurs propres œuvres.', 'Studio d''art La Création', '2024-06-20 14:00', '2024-06-20 17:00'),
(50.00, 'Q&A avec les acteurs de Friends', 'autre', 'Une séance de questions suivi d''une séance de photo et autographe.', '1001 Pl. Jean-Paul-Riopelle, Montréal, QC H2Z 1H5', '2024-07-05 18:30', '2024-07-05 20:00'),
(10.00, 'Visite guidée de Poudlard', 'activité', 'Une visite guidée du château où on été filmé les films de Harry Potter.', 'Alnwick NE66 1NQ, Royaume-Uni', '2024-08-12 11:00', '2024-08-12 13:00');

INSERT INTO ClientEvenement
VALUES
(3,5),
(3,4),
(4,5),
(9,1),
(6,2);

INSERT INTO VideoClient (idClient, idVideo, debut_location)
VALUES
(1, 6, '2023-06-15 12:04:10'),
(1, 2, '2023-01-30 15:30:25'),
(1, 9, DATEADD(HOUR, -40, CONVERT(DATETIME2, GETDATE()))),
(2, 2, '2022-12-25 10:32:38'),
(2, 10, DATEADD(HOUR, -15, CONVERT(DATETIME2, GETDATE()))),
(3, 2, '2023-01-30 21:42:04'),
(5, 9, DATEADD(HOUR, -37, CONVERT(DATETIME2, GETDATE()))),
(5, 10, '2023-06-15 20:00:12'),
(7, 3, DATEADD(HOUR, -39, CONVERT(DATETIME2, GETDATE()))),
(7, 5, '2023-07-15 14:55:53'),
(7, 5, '2023-07-17 15:05:48'),
(7, 8, DATEADD(HOUR, -8, CONVERT(DATETIME2, GETDATE()))),
(8, 7, '2024-02-10 9:12:29'),
(9, 2, DATEADD(HOUR, -26, CONVERT(DATETIME2, GETDATE()))),
(10, 6, '2024-02-03 21:18:15');

-- Affichage de la base de données si nécessaire

/*SELECT * FROM AdminEmp;
SELECT * FROM Client;
SELECT * FROM Abonnement;
SELECT * FROM Adresse;
SELECT * FROM ClientAdresse;
SELECT * FROM Carte_credit;
SELECT * FROM ClientCarte_credit;
SELECT * FROM Evenement;
SELECT * FROM ClientEvenement;
SELECT * FROM Video;
SELECT * FROM VideoClient;
SELECT * FROM Realisateur;
SELECT * FROM VideoRealisateur;
SELECT * FROM Acteur;
SELECT * FROM VideoActeur;
SELECT * FROM Genre;
SELECT * FROM VideoGenre;
SELECT * FROM Sujet;
SELECT * FROM VideoSujet;*/