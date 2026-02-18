Ce projet est dans le cadre d'un cursus de l'école CY Tech afin d'apprendre le java

Prénom :    Thémis 
Nom :       Tran Tu Thien 
Classe :    ING1 GI01 
#etu :      22402595



LOG
Monde 1 :
 - Niveau 1 :   Création des entitées joueurs et d'un consctucteur
                On initialise le score à 0
                On crée une méthode updateScore pour ajouter/enlever des points
 - Niveau 2 :   On met les variables en private et le prénom en final
                On ajoute une condition pour que le score soit toujours positif
 - Niveau 3 :   Ajout d'une documentation javadoc
                Pour générer la documentation du monde 1 : make doc
                Pour effacer la documentation du : make doc_clean
 - Niveau 4 :   Ajout de la méthode toString() qui renvoit sous la forme : [name] : [score] pts 
                Le s s'accord si le score est supérieur à 1 ou non
 - Niveau 5 :   Ajout de la méthode equals()
                On vérifie le type avec instanceof
                On compare le nom avec equalsIgnoreCase() qui ignore la casse 
                On ajoute nbplayer qui s'incrémente dans la méthode constructeur
                On n'a pas de méthode pout supprimer un objet, on met juste les pointeurs de celui-ci vers null
 - Niveau 6 :   A chaque construction, on print le nombre total de joueurs
                Ajout d'une méthode Player.getNbPlayer() qui renvoit nbplayer (lecture seule)
                On utilise un constructeur sans argument qui donne comme nom PlayerN et qui se chaine

Monde 2 :
	- Niveau 1 :  Création des maps avec des structures et des murs
	- Niveau 2 :  Placement du joueur dans le niveau avec un '1'
  - Niveau 3 et 4 :   Mise en place des mouvements en continu du l'utilisateur avec zqsd
	- Niveau 5 :   Création de niveaux en fichiers de texte : création de pacMan.txt et mario.txt
	- Niveau 6 :   Création d'un executable via le make file où on peut mettre le nom du fichier



Pour compiler :
    - World_2 : make ou make w2
    - World_1 : make w1
    - Tutorial : make tuto
Pour excecuter :
    - World_2 : make run_w2
    - World_1 : make run_w1
    - Tutorial : make run_tuto
Pour nettoyer:
    - World_2 : make clean_w2
    - World_1 : make clean_w1
    - Tutorial : make clean_tuto
Pour la documentation :
    - Génération : make doc
    - Nettoyage : make doc_clean
    
POUR EXECUTER L'EXECUTABLE :
java -jar exec.jar [FILE_NAME]
avec [FILE_NAME] le fichier dans files/ lié au niveau
