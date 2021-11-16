# delivery_app

//DEJA POSEE
Authentification ? NON
l'utilisateur doit demander le calcul du chemin OU EST IL AUTOMATIQUE des le chargement du fiichier pick and delivery?  Pas automatique
Pas de pick up sans delivery 
La map est-elle chargée dès le lancement de l'appli ? Non, il y a un boutton load, et une option "télécharger la map" dès le début 
Faut-il tout recalculer apres chaque modifs ? NON, interface ou toutes les modifs pourront etre faites d'un coup


//A POSER
Est-ce à l'appli d'envoyer les horaires au client ? non
Verifier les données ?
option afficher le nom des rues ?

afficher nom des rues quand on passe la souris
vue textuelle avec horaire





A FAIRE CETTE SEANCE

-Tous les CU
-Les CU de la preimere iteration + leur analyse (scenario principal)
-Identifier l'architecture
-Qui fait quoi ? Définir qui pour chaque packages (ex. : IHM)
-planning previsionnel (qui travaille sur quoi + le temps par tache / effectif et previsionnel)


//Premiere itération
-Load city map
-Load deliveries
-Compute tour
(-Si possible IHM qui sera à modifier par la suite)



IDEES

IHM : Possibilité de faire plusieurs modif en meme temps, avant de lancer le calcul du PCC.
attribut horaire de livraison pour le client (sera utile par la suite pour les modifications)


DC :
par rappor a la map:
-MAP (map(id+intesection) + segment)
-Intersection (2 coordonnées
-Segment (2 intersections + longueur)
