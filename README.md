# springbatch-csv-reader-bdd-modular-jpa-Thymleaf-REST-Angular-DragDrop-Kafka

Lancer un docker kafka (exemple docker.desktop)
docker run -d --name redpanda `
  -p 9092:9092 `
  -p 9644:9644 `
  docker.redpanda.com/redpandadata/redpanda:latest `
  redpanda start `
  --overprovisioned `
  --smp 1 `
  --memory 1G `
  --reserve-memory 0M `
  --node-id 0 `
  --check=false `
  --kafka-addr PLAINTEXT://0.0.0.0:9092 `
  --advertise-kafka-addr PLAINTEXT://localhost:9092





Pour visualiser dans l'interface Angular : http://localhost:4200/ ou http://localhost:4200/smartphones

glisser/déposer le fichier nommé smartphones upload.csv dans l'encart prévu à cet effet : Glissez-déposez un fichier CSV ici

Il va tout de suite reconnaitre le fichier Fichier : smartphones upload.csv

Appuyer sur le bouton "Envoyer" en dessous, cela mettra a jour la table dans la meme page avec le contenu provenant du fichier (évidemment, on peut y glisser n'importe quel fichier csv, pour peu que l'on conserve le format d'entrée : marque;modele;os;annee;tailleEcran;prix Apple;iPhone 17;iOS 19;2025;6.1;999

pour avoir la liste format json : http://localhost:8080/api/smartphones pour avoir une seule entité de la liste : http://localhost:8080/api/smartphones/1

A savoir que chaque upload de fichier est enregistré dans le répertoire uploads/


vérifier la création du topic smartphones-topic avec :
rpk topic list
NAME               PARTITIONS  REPLICAS
smartphones-topic  1           1



et lancer un outil de post/get (exemple Postman) : http://localhost:8080/api/smartphones/publish
ou apparait alors : 📤 11 smartphones publiés dans Kafka.

et les logs font apparaitre :
[PUBLISHER] Envoi du smartphone : Smartphone(id=1, marque=Apple, modele=iPhone 17, os=iOS 19, annee=2025, tailleEcran=6.1, prix=999.0)
📤 Envoi du smartphone au topic Kafka : Smartphone(id=1, marque=Apple, modele=iPhone 17, os=iOS 19, annee=2025, tailleEcran=6.1, prix=999.0)

le listener consumer kafka en ecoute consomme le message :
 [LISTENER] Démarrage méthode listen() pour le topic 'smartphones-topic'
 📥 [CONSUMER] Message reçu depuis Kafka : Smartphone(id=1, marque=Apple, modele=iPhone 17, os=iOS 19, annee=2025, tailleEcran=6.1, prix=999.0)
 📥 Message consommé : Smartphone(id=1, marque=Apple, modele=iPhone 17, os=iOS 19, annee=2025, tailleEcran=6.1, prix=999.0)


Toujours via l'outil de post/get (exemple Postman) : http://localhost:8080/api/smartphones/consumed
apparait alors la liste de tous les messages consommés sous format json :

[
    {
        "id": 1,
        "marque": "Apple",
        "modele": "iPhone 17",
        "os": "iOS 19",
        "annee": 2025,
        "tailleEcran": 6.1,
        "prix": 999.0
    },
    {
        "id": 2,
        "marque": "Apple",
        "modele": "iPhone 17 Pro",
        "os": "iOS 19",
        "annee": 2025,
        "tailleEcran": 6.1,
        "prix": 1299.0
    },
    {
        "id": 3,
        "marque": "Apple",
        "modele": "iPhone 17 Pro Max",
        "os": "iOS 19",
        "annee": 2025,
        "tailleEcran": 6.1,
        "prix": 1399.0
    },
    {
        "id": 4,
        "marque": "Apple",
        "modele": "iPhone 16",
        "os": "iOS 18",
        "annee": 2024,
        "tailleEcran": 6.1,
        "prix": 799.0
    },
    {
        "id": 5,
        "marque": "Apple",
        "modele": "iPhone 16 Max",
        "os": "iOS 18",
        "annee": 2024,
        "tailleEcran": 6.7,
        "prix": 899.0
    },
    {
        "id": 6,
        "marque": "Apple",
        "modele": "iPhone 16 Pro",
        "os": "iOS 18",
        "annee": 2024,
        "tailleEcran": 6.1,
        "prix": 1099.0
    },
    {
        "id": 7,
        "marque": "Apple",
        "modele": "iPhone 16 Pro Max",
        "os": "iOS 18",
        "annee": 2024,
        "tailleEcran": 6.7,
        "prix": 1199.0
    },
    {
        "id": 8,
        "marque": "Samsung",
        "modele": "Galaxy S24",
        "os": "Android",
        "annee": 2024,
        "tailleEcran": 6.8,
        "prix": 999.0
    },
    {
        "id": 9,
        "marque": "Google",
        "modele": "Pixel 9",
        "os": "Android",
        "annee": 2024,
        "tailleEcran": 6.2,
        "prix": 899.0
    },
    {
        "id": 10,
        "marque": "Xiaomi",
        "modele": "Mi 14",
        "os": "Android",
        "annee": 2023,
        "tailleEcran": 6.5,
        "prix": 699.0
    },
    {
        "id": 11,
        "marque": "Huawei",
        "modele": "P60",
        "os": "Android",
        "annee": 2022,
        "tailleEcran": 6.6,
        "prix": 584.1
    }
]



