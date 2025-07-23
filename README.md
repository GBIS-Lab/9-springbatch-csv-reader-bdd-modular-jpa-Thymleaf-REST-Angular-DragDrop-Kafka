# springbatch-csv-reader-bdd-modular-jpa-Thymleaf-REST-Angular-DragDrop-Kafka

Pour visualiser dans l'interface Angular : http://localhost:4200/ ou http://localhost:4200/smartphones

glisser/déposer le fichier nommé smartphones upload.csv dans l'encart prévu à cet effet : Glissez-déposez un fichier CSV ici

Il va tout de suite reconnaitre le fichier Fichier : smartphones upload.csv

Appuyer sur le bouton "Envoyer" en dessous, cela mettra a jour la table dans la meme page avec le contenu provenant du fichier (évidemment, on peut y glisser n'importe quel fichier csv, pour peu que l'on conserve le format d'entrée : marque;modele;os;annee;tailleEcran;prix Apple;iPhone 17;iOS 19;2025;6.1;999

pour avoir la liste format json : http://localhost:8080/api/smartphones pour avoir une seule entité de la liste : http://localhost:8080/api/smartphones/1

A savoir que chaque upload de fichier est enregistré dans le répertoire uploads/
