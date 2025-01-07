/* 
Projet#9 IFT2935
Date de remise : 10 avril 2024

Auteure : Joanie Birtz			       Matricule : 20244554
Auteure : Janic Fournel                Matricule : 20201900
Auteure : Karen-Fae Laurin			   Matricule : 20019033
Auteure : Cynthia-Alexandra Vargas 	   Matricule : 20030603
*/

/*
Ce fichier contient l'ensemble des déclencheurs, des procédures stockées, des fonctions
stockées et des curseurs en Transact-SQL.
*/

USE ClubVideo;
GO

------------------
-- Déclencheurs --
------------------

-- Créer un déclencheur qui empêche la mise à jour du courriel d'un compte client.

CREATE OR ALTER TRIGGER trig_modif_courriel_client
ON Client
AFTER UPDATE
AS
BEGIN
	IF (UPDATE(courriel))
	BEGIN
		RAISERROR('Interdit de modifier le courriel dun client.', 16, 1);
		ROLLBACK;
	END;
END;

GO

-- Test du déclencheur trig_modif_courriel_client

UPDATE Client SET courriel = 'testTrigger@test.ca' WHERE id = 1;
GO

SELECT * FROM Client WHERE id = 1;
GO

-------------------------------------

-- Créer un déclencheur qui empêche la mise à jour du courriel d'un compte administrateur.

CREATE OR ALTER TRIGGER trig_modif_courriel_admin
ON AdminEmp
AFTER UPDATE
AS
BEGIN
	IF (UPDATE(courriel))
	BEGIN
		RAISERROR('Interdit de modifier le courriel dun administrateur.', 16, 1);
		ROLLBACK;
	END;
END;

GO

-- Test du déclencheur trig_modif_courriel_admin

UPDATE AdminEmp SET courriel = 'testTrigger@test.ca' WHERE id = 2;
GO

SELECT * FROM AdminEmp WHERE id = 2;
GO

-------------------------------------

-- Créer un déclencheur qui empêche la mise à jour de la date de début de location d'une vidéo.

CREATE OR ALTER TRIGGER trig_modif_debut_loc_VC
ON VideoClient
AFTER UPDATE
AS
BEGIN
	IF (UPDATE(debut_location))
	BEGIN
		RAISERROR('Interdit de modifier la date de début de location dune vidéo.', 16, 1);
		ROLLBACK;
	END;
END;

GO

-- Test du déclencheur trig_modif_debut_loc_VC

UPDATE VideoClient 
SET debut_location = CONVERT(DATETIME2, GETDATE())
WHERE id = 3;
GO

SELECT * FROM VideoClient WHERE id = 4;
GO


-------------------------
-- Procédures stockées --
-------------------------

/* Créer une procédure permettant d'insérer un nouveau client dans la base de données seulement si le 
   courriel du nouveau client n'est pas déjà utilisé par une autre client ou par un administrateur de
   la base de données, sinon une erreur est lancé.
*/

CREATE OR ALTER PROCEDURE insertionClient (@pPrenom VARCHAR(255), @pNom VARCHAR(255), @pDateNaissance DATE, @pMot_de_passe VARCHAR(255), @pCourriel VARCHAR(255))
AS
BEGIN
    SELECT * FROM AdminEmp WHERE courriel = @pCourriel

    if @@ROWCOUNT > 0
        RAISERROR('Erreur, vous avez déjà un compte administrateur avec ce courriel',11,1);
    ELSE
        INSERT INTO Client(prenom, nom, dateNaissance, mot_de_passe, courriel)
        VALUES (@pPrenom, @pNom, @pDateNaissance, @pMot_de_passe, @pCourriel);
END;
GO

-- Appel de la procédure insertionClient avec un courriel qui n'existe pas dans Client ni dans AdminEMP

EXECUTE insertionClient @pPrenom='Meredith', @pNom='Grey', @pDateNaissance='1978-11-10', @pMot_de_passe='!HardPassword!', @pCourriel='meredith.grey@live.ca' ; 
GO

SELECT * FROM Client WHERE courriel = 'meredith.grey@live.ca';
GO

-- Appel de la procédure insertionClient avec un courriel qui existe dans AdminEMP

EXECUTE insertionClient @pPrenom='Alice', @pNom='Martin', @pDateNaissance='1998-07-22', @pMot_de_passe='Motdepasse123!', @pCourriel='alice.martin@gmail.com' ; 
GO

SELECT * FROM Client WHERE courriel = 'alice.martin@gmail.com';
GO

-- Appel de la procédure insertionClient avec un courriel qui existe dans Client

EXECUTE insertionClient @pPrenom='Sophie', @pNom='Lefebvre', @pDateNaissance='1985-03-12', @pMot_de_passe='Secret123!', @pCourriel='sophie.lefebvre@hotmail.com' ; 
GO

SELECT * FROM Client WHERE courriel = 'sophie.lefebvre@hotmail.com';
GO

------------------------
-- Fonctions stockées --
------------------------

/* Écrire une fonction qui permet de trouver le compte correspondant à certaines informations 
   de connexion (courriel et mot de passe). La fonction doit retournée le numéro du compte 
   trouvé et le type de ce compte (client ou administrateur). Si les informations de connexion
   sont invalides, la fonction indique une erreur comme type de compte.
*/

CREATE OR ALTER FUNCTION dbo.VerrifConnexion (@courriel VARCHAR(255), @mot_de_passe VARCHAR(255))
RETURNS @Resultat TABLE (id INT, type_utilisateur CHAR(1))
AS
BEGIN
    DECLARE @TypeUtilisateur CHAR(1);  -- Cette déclaration est inutile, car elle n'est pas utilisée
    DECLARE @ID INT;

    IF EXISTS (SELECT 1 FROM AdminEmp WHERE courriel = @courriel AND mot_de_passe = @mot_de_passe)
    BEGIN
        SELECT @ID = id FROM AdminEmp WHERE courriel = @courriel AND mot_de_passe = @mot_de_passe;
        INSERT INTO @Resultat VALUES (@ID, 'A');
    END
    ELSE IF EXISTS (SELECT 1 FROM Client WHERE courriel = @courriel AND mot_de_passe = @mot_de_passe)
    BEGIN
        SELECT @ID = id FROM Client WHERE courriel = @courriel AND mot_de_passe = @mot_de_passe;
        INSERT INTO @Resultat VALUES (@ID, 'C');
    END
    ELSE
    BEGIN
        INSERT INTO @Resultat VALUES (NULL, 'E');
    END;

    RETURN;
END;
GO

-- Appel de la fonction dbo.VerrifConnexion avec un courriel et mot de passe existants dans la table AdminEmp

SELECT * FROM dbo.VerrifConnexion ('claire.garcia@gmail.com', 'P@ssw0rd789');

-- Appel de la fonction dbo.VerrifConnexion avec un courriel et mot de passe existants dans la table Client

SELECT * FROM dbo.VerrifConnexion ('sophie.lefebvre@hotmail.com', 'Secret123!');

-- Appel de la fonction dbo.VerrifConnexion avec un courriel existant mais mot de passe incorrect

SELECT * FROM dbo.VerrifConnexion ('admin@example.com', 'mauvaismotdepasse');

GO

--------------
-- Curseurs --
--------------

/* Ajouter à la table Client une colonne appelée "nbLocationEnCours".
   Utiliser un CURSEUR pour mettre la bonne valeur dans la colonne "nbLocationEnCours" de
   tous les clients actuels selon les informations de la table VideoLocation.
   Notez que chaque location dure 48h.
*/

ALTER TABLE Client
ADD nbLocationEnCours INT NOT NULL DEFAULT 0;

GO

SELECT id, nbLocationEnCours FROM Client; -- Utiliser pour comparer avec après le traitement du curseur

DECLARE curs_loc CURSOR FOR 
	SELECT idClient, COUNT(*) AS nbLocEnCours
	FROM (SELECT *
		  FROM VideoClient
		  WHERE debut_location > (SELECT DATEADD(HOUR, -48, CONVERT(DATETIME2, GETDATE())))
		  ) AS LocationEnCours
	GROUP BY idClient;

DECLARE @id INT, @nbLocEnCours INT;

OPEN curs_loc;
FETCH NEXT FROM curs_loc INTO @id, @nbLocEnCours;

WHILE @@FETCH_STATUS = 0
BEGIN
	UPDATE Client
	SET nbLocationEnCours = @nbLocEnCours
	WHERE id = @id;

	FETCH NEXT FROM curs_loc INTO @id, @nbLocEnCours;
END;

CLOSE curs_loc;
DEALLOCATE curs_loc;

SELECT id, nbLocationEnCours FROM Client; -- Utiliser pour comparer avec avant le traitement du curseur

-----------------------------------------------------------------------------------------
-- Si besoin, suppression des déclencheurs, procédures stockées et fonctions stockées. --
-----------------------------------------------------------------------------------------

/*
DROP TRIGGER IF EXISTS dbo.trig_modif_courriel_client;
DROP TRIGGER IF EXISTS dbo.trig_modif_courriel_admin;
DROP TRIGGER IF EXISTS dbo.trig_modif_debut_loc_VC;

DROP PROCEDURE IF EXISTS dbo.insertionClient;

DROP FUNCTION IF EXISTS dbo.VerrifConnexion;
*/
