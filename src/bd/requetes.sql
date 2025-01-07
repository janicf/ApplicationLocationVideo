/* 
Projet#9 IFT2935
Date de remise : 10 avril 2024

Auteure : Joanie Birtz			       Matricule : 20244554
Auteure : Janic Fournel                Matricule : 20201900
Auteure : Karen-Fae Laurin			   Matricule : 20019033
Auteure : Cynthia-Alexandra Vargas 	   Matricule : 20030603
*/

/*
Ce fichier contient l'ensemble des requêtes SQL.
*/

USE ClubVideo;
GO

-- Requête 1
-- Donner tout les genres des vidéos louer par un client. 

-- Avec idClient = 7

SELECT DISTINCT Nom 
FROM Genre 
JOIN (SELECT VideoGenre.idGenre 
      FROM VideoGenre 
      JOIN (SELECT idVideo 
	        FROM VideoClient 
			WHERE idClient = 7 
			GROUP BY idVideo) 
      AS VideoClientLouer 
	  ON VideoClientLouer.idVideo = VideoGenre.idVideo) 
AS idGenreLouer 
ON idGenreLouer.idGenre = Genre.id;

-- Avec courriel = camille.moreau@live.ca

SELECT DISTINCT Nom 
FROM Genre 
JOIN (SELECT VideoGenre.idGenre 
      FROM VideoGenre
      JOIN (SELECT idVideo 
            FROM VideoClient 
            WHERE idClient = (SELECT id 
			                  FROM Client 
							  WHERE courriel = 'camille.moreau@live.ca')) 
      AS VideoClientLouer 
	  ON VideoClientLouer.idVideo = VideoGenre.idVideo) 
AS idGenreLouer 
ON idGenreLouer.idGenre = Genre.id;

-- Requête 2
-- Donner les genres préférées des clients.

SELECT 
    c.id,
    c.prenom,
    c.nom,
    g.nom AS genrePrefere,
	nombreEcoutes
FROM 
    Client c
JOIN (
    SELECT 
        vc.idClient,
        vg.idGenre,
        COUNT(*) AS nombreEcoutes
    FROM 
        VideoClient vc
    JOIN 
        VideoGenre vg ON vc.idVideo = vg.idVideo
    GROUP BY 
        vc.idClient, vg.idGenre
) AS ecoutes_client_genre ON c.id = ecoutes_client_genre.idClient
JOIN 
    Genre g ON ecoutes_client_genre.idGenre = g.id
JOIN (
    SELECT 
        idClient,
        MAX(nombreEcoutes) AS maxEcoutes
    FROM 
        (
            SELECT 
                vc.idClient,
                vg.idGenre,
                COUNT(*) AS nombreEcoutes
            FROM 
                VideoClient vc
            JOIN 
                VideoGenre vg ON vc.idVideo = vg.idVideo
            GROUP BY 
                vc.idClient, vg.idGenre
        ) AS subquery
    GROUP BY 
        idClient
) AS max_ecoutes_client ON ecoutes_client_genre.idClient = max_ecoutes_client.idClient AND ecoutes_client_genre.nombreEcoutes = max_ecoutes_client.maxEcoutes;

-- Requête 3
-- Quels sont les sujets de tous les vidéos d'un certain acteur?

-- Avec acteur = Leonardo DiCaprio

SELECT nom as Sujet 
FROM VideoSujet 
INNER JOIN (SELECT id
			FROM Video
			INNER JOIN (SELECT idVideo 
						FROM VideoActeur 
						WHERE idActeur = (SELECT id 
										  FROM Acteur 
										  WHERE prenom = 'Leonardo' and nom = 'DiCaprio')
						) AS Video_LD
			ON Video.id = Video_LD.idVideo
			WHERE type_video = 'film'
			) AS Films_LD
ON VideoSujet.idVideo = Films_LD.id
INNER JOIN Sujet
ON VideoSujet.idSujet = Sujet.id;

-- Requête 4
-- Donner le nom du réalisateur et des acteurs du vidéo le plus louer.

SELECT DISTINCT Realisateur.nom AS Nom_Réalisateur, Acteur.nom AS Nom_Acteur
FROM (SELECT idVideo, COUNT(*) AS nbr_location 
      FROM VideoClient 
      GROUP BY idVideo
      HAVING COUNT(*) = (SELECT MAX(nbr_location) 
	                     FROM (SELECT COUNT(*) AS nbr_location 
						       FROM VideoClient 
							   GROUP BY idVideo)
						 AS MAXLocations)
      ) AS VideoPlusLouer
JOIN VideoRealisateur 
ON VideoPlusLouer.idVideo = VideoRealisateur.idVideo
JOIN Realisateur 
ON VideoRealisateur.idRealisateur = Realisateur.id
JOIN VideoActeur 
ON VideoActeur.idVideo = VideoPlusLouer.idVideo
JOIN Acteur 
ON VideoActeur.idActeur = Acteur.id;

-- Requête 5
-- Donner l’id, le titre, la date de sortie et l’état des vidéos ayant un sujet, un genre et un acteur en particulier.

-- Avec sujet = Science-Fiction, genre = Espionnage et acteur = Leonardo DiCaprio

SELECT 
	v.id,
    v.titre,
    v.date_sortie,
    v.etat
FROM Video v
INNER JOIN VideoActeur va ON v.id = va.idVideo
INNER JOIN Acteur a ON a.id = va.idActeur
INNER JOIN VideoGenre vg ON vg.idVideo = v.id
INNER JOIN Genre g ON g.id = vg.idGenre
INNER JOIN VideoSujet vs ON vs.idVideo = v.id
INNER JOIN Sujet s ON s.id = vs.idSujet
WHERE 
	g.Nom = 'Science-fiction'
	AND s.Nom = 'Espionnage'
    AND a.nom = 'DiCaprio'
    AND a.prenom = 'Leonardo'
	AND v.etat != 'archivé'

-- Requête 6
-- Quels sont les videos archivés qui sont sortis avant 1995?

SELECT *
FROM Video
WHERE etat = 'archivé' AND date_sortie < '1995-01-01';

-- Requête 7
-- Donner les événement qui auront lieu entre le 15 avril 2024 et le 15 mai 2024.

SELECT * 
FROM Evenement 
WHERE dateDebut BETWEEN '2024-04-15' AND '2024-05-15';

-- Requête 8
-- Donner le nom du genre , le sujet et le titre du video le moins louer (tous independant).

SELECT 
    GenreLeMoinsLoue.nomGenre AS GenreLeMoinsLoue,
    SujetLeMoinsLoue.nomSujet AS SujetLeMoinsLoue,
    VideoLaMoinsLouee.titre AS VideoLaMoinsLouee
FROM (
    SELECT TOP 1
        g.nom AS nomGenre,
        COUNT(vc.id) AS nbLocations
    FROM 
        Video v
    JOIN 
        VideoGenre vg ON v.id = vg.idVideo
    JOIN 
        Genre g ON vg.idGenre = g.id
    LEFT JOIN 
        VideoClient vc ON v.id = vc.idVideo
    GROUP BY 
        g.nom
    ORDER BY 
        COUNT(vc.id) ASC
) AS GenreLeMoinsLoue
CROSS JOIN (
    SELECT TOP 1
        s.nom AS nomSujet,
        COUNT(vc.id) AS nbLocations
    FROM 
        Video v
    JOIN 
        VideoSujet vs ON v.id = vs.idVideo
    JOIN 
        Sujet s ON vs.idSujet = s.id
    LEFT JOIN 
        VideoClient vc ON v.id = vc.idVideo
    GROUP BY 
        s.nom
    ORDER BY 
        COUNT(vc.id) ASC
) AS SujetLeMoinsLoue
CROSS JOIN (
    SELECT TOP 1
        v.titre,
        COUNT(vc.id) AS nbLocations
    FROM 
        Video v
    LEFT JOIN 
        VideoClient vc ON v.id = vc.idVideo
    GROUP BY 
        v.titre
    ORDER BY 
        COUNT(vc.id) ASC
) AS VideoLaMoinsLouee

-- Requête 9
-- Quels sont les nouveautés vidéos qui ont été ajoutés dans le dernier mois?

SELECT *
FROM Video
WHERE etat = 'nouveauté'
AND date_ajout > (SELECT DATEADD(month, -1, CONVERT(date, GETDATE())));

-- Requête 10
-- Donner tous les informations d'un client selon son courriel.

-- Avec courriel = lea.fournier@gmail.ca

SELECT * 
FROM Client 
WHERE courriel = 'lea.fournier@gmail.ca';

-- Requête 11
-- Donner tous les informations d'un administrateur selon son courriel.

-- Avec courriel = bob.johnson@gmail.com

SELECT * FROM AdminEmp WHERE courriel = 'bob.johnson@gmail.com';

-- Requête 12
-- Donner le nombre de client qui ont un abonnement.

SELECT COUNT(*) AS nombreAbonnes
FROM Abonnement a
Where a.actif='true';


-- Quelques requetes utiliser dans l'application

-- Titre video pour client

SELECT titre 
FROM Video
WHERE etat != 'archivé';

-- Obtenir tous les genres pour un video specifique
-- Avec idVideo = 1

SELECT nom, G.id FROM VideoGenre VG INNER JOIN Genre G
ON VG.idGenre = G.id WHERE VG.idVideo = 1;

-- Obtenir tous les sujets pour un video specifique
-- Avec idVideo = 3

SELECT nom, S.id FROM VideoSujet VS INNER JOIN Sujet S 
ON VS.idSujet = S.id WHERE VS.idVideo = 3;

-- Obtenir tous les acteurs pour un video specifique
-- Avec idVideo = 5

SELECT A.id, nom, prenom, dateNaissance 
FROM VideoActeur VA INNER JOIN Acteur A 
ON VA.idActeur = A.id WHERE VA.idVideo = 5;

-- Obtenir tous les realisateurs pour un video specifique
-- Avec idVideo = 7

SELECT R.id, nom, prenom, dateNaissance 
FROM VideoRealisateur VR INNER JOIN Realisateur R
ON VR.idRealisateur = R.id WHERE VR.idVideo = 7;

-- Obtenir les locations encore actives (moins de 48h) d'un client 
-- Avec idClient = 1

SELECT * FROM Video V LEFT JOIN VideoClient VC on V.id = VC.idVideo WHERE VC.idClient = 1
AND VC.debut_location > DATEADD(HOUR, -48, CONVERT(DATETIME2, GETDATE()));

-- Obtenir tous les videos gratuit avec abonnement (vue client)

SELECT * FROM Video V WHERE V.gratuit_abonne = 1
AND etat != 'archivé';

-- Obtenir les nouveautes

SELECT * FROM Video WHERE etat = 'nouveauté';

-- Verification validite de l'abonnement
-- Avec idClient = 2

SELECT actif, dateFin FROM Abonnement WHERE idClient = 2;

-- Verification validite de la location (moins de 48h) pour un video specifique
-- Avec idClient = 5 et idVideo = 9

SELECT idVideo FROM VideoClient WHERE idClient = 5 AND idVideo = 9
AND debut_location > DATEADD(HOUR, -48, CONVERT(DATETIME2, GETDATE()));

-- Verification participation d'un client a un evenement
-- Avec idClient = 3 et idEvenement = 5

SELECT idEvenement FROM ClientEvenement WHERE idClient = 3 AND idEvenement = 5;

-- Obtenir les evenements d'un client
-- Avec idClient = 3

SELECT E.id, E.prix, E.titre, E.type_event, E.Lieu, E.description_event, E.DateDebut, E.DateFin 
FROM Evenement E LEFT JOIN ClientEvenement CE
ON E.id = CE.idEvenement WHERE CE.idClient = 3;

-- Obtenir le nom et prenom d'un client selon son id
-- Avec idClient = 10

SELECT prenom, nom FROM Client WHERE id = 10;

-- Verifier si courriel deja utiliser pour un compte client (retourne 1 si compte existe deja)
-- Avec courriel = enzo.caron@hotmail.com

SELECT COUNT('enzo.caron@hotmail.com') AS count FROM Client WHERE courriel = 'enzo.caron@hotmail.com';

-- Avec courriel = meredith.gray@hotmail.ca

SELECT COUNT('meredith.gray@hotmail.ca') AS count FROM Client WHERE courriel = 'meredith.gray@hotmail.ca';

