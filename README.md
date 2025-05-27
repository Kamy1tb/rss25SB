# RSS25SB - Application Spring Boot pour RSS

## Présentation
RSS25SB est une application Spring Boot pour la gestion des flux RSS. Elle fournit des points de terminaison REST pour récupérer et gérer les données RSS selon la spécification RSS25, avec une intégration à une base de données PostgreSQL pour la persistance. L'application est conçue comme une Single Page Application (SPA) pour une expérience utilisateur fluide.

## Fonctionnalités
- API REST pour la gestion des flux RSS
- Représentations XML et HTML des flux RSS
- Intégration avec PostgreSQL
- Architecture SPA (Single Page Application)
- Architecture MVC côté serveur
- Templates Thymeleaf pour les vues web
- Transformations XSLT pour le rendu HTML
- Validation XML selon un schéma XSD
- Navigation sans rechargement de page
- Conversion de flux RSS externes au format RSS25

## Architecture
L'application est structurée selon le modèle MVC (Modèle-Vue-Contrôleur) :
- **Modèles** : Classes Java représentant les entités de données (Feed, Item, Author, etc.)
- **Vues** : Templates Thymeleaf pour le rendu HTML
- **Contrôleurs** : Classes Java gérant les requêtes HTTP et la logique métier

L'interface utilisateur est implémentée comme une SPA (Single Page Application) utilisant JavaScript pour la navigation et les interactions sans rechargement de page.

## Prérequis
- Kit de Développement Java (JDK) 21 ou version supérieure
- Maven 3.8.0 ou version supérieure (ou utiliser le wrapper Maven inclus)
- Git (pour cloner le dépôt)
- PostgreSQL (optionnel pour le développement local)

## Démarrage

### Cloner le dépôt
```bash
git clone https://github.com/votrenomdutilisateur/rss25SB.git
cd rss25SB
```

### Compiler le projet
```bash
# Avec Maven
mvn clean package

# Avec Maven Wrapper (Windows)
mvnw.cmd clean package

# Avec Maven Wrapper (Linux/Mac)
./mvnw clean package
```

### Exécuter l'application localement
```bash
# Avec Maven
mvn spring-boot:run

# Avec Maven Wrapper (Windows)
mvnw.cmd spring-boot:run

# Avec Maven Wrapper (Linux/Mac)
./mvnw spring-boot:run
```

L'application démarrera sur le port 8080 par défaut. Vous pouvez y accéder à l'adresse http://localhost:8080

## Configuration de la base de données
Par défaut, l'application est configurée pour se connecter à une base de données PostgreSQL. Les paramètres de connexion sont définis dans le fichier `application.properties`.

Pour le développement local, vous pouvez modifier ces paramètres pour utiliser votre propre base de données :

```properties
# Configuration PostgreSQL Locale
spring.datasource.url=jdbc:postgresql://localhost:5432/rss25sb
spring.datasource.username=postgres
spring.datasource.password=votremotdepasse
```

## Structure du projet
```
src/
├── main/
│   ├── java/
│   │   └── fr/
│   │       └── univrouen/
│   │           └── rss25sb/
│   │               ├── controllers/    # Contrôleurs REST
│   │               ├── model/          # Modèles de données
│   │               ├── repository/     # Interfaces de persistance
│   │               ├── service/        # Services métier
│   │               └── util/           # Classes utilitaires
│   └── resources/
│       ├── static/                     # Ressources statiques (CSS, JS)
│       ├── templates/                  # Templates Thymeleaf
│       ├── schemas/                    # Schémas XSD
│       └── xslt/                       # Transformations XSLT
└── test/
    └── java/                           # Tests unitaires
```

## Points de terminaison API
L'application expose plusieurs points de terminaison REST :

### Gestion des flux RSS
- `GET /rss25SB/resume/xml` - Obtenir la liste de tous les flux au format XML
- `GET /rss25SB/resume/xml?format=raw` - Obtenir la liste de tous les flux au format XML brut
- `GET /rss25SB/resume/html` - Obtenir la liste de tous les flux au format HTML
- `GET /rss25SB/resume/xml/{id}` - Obtenir un article spécifique par ID au format XML
- `GET /rss25SB/html/{id}` - Obtenir un article spécifique par ID au format HTML
- `GET /rss25SB/html/guid/{guid}` - Obtenir un article spécifique par GUID au format HTML
- `POST /rss25SB/insert` - Ajouter un nouveau flux (format XML)
- `DELETE /rss25SB/delete/{id}` - Supprimer un article par ID

### Pages Web
- `GET /` - Page d'accueil
- `GET /convert` - Interface de conversion de flux
- `GET /insert` - Interface d'insertion de flux
- `GET /transfer` - Interface de transfert de fichiers
- `GET /items` - Liste des articles
- `GET /help` - Documentation de l'API

## Fonctionnalités détaillées

### Conversion de flux
L'application permet de convertir des flux RSS externes au format RSS25 :
- Conversion depuis Le Monde - International
- Conversion depuis Fonction Publique - Actualités
- Validation automatique selon le schéma XSD
- Génération de GUIDs uniques pour les articles

### Validation XML
Tous les flux XML sont validés selon le schéma XSD défini dans `src/main/resources/schemas/rss25.tp1.xsd`.

### Transformations XSLT
Les transformations XSLT sont utilisées pour convertir les données XML en HTML pour l'affichage dans le navigateur.

## Déploiement
L'application peut être déployée sur n'importe quel serveur compatible avec les applications Spring Boot :

### Création d'un fichier JAR exécutable
```bash
mvn clean package
```

### Exécution du JAR
```bash
java -jar target/rss25SB-0.0.1-SNAPSHOT.jar
```

## Tests
Pour exécuter les tests unitaires :

```bash
mvn test
```

## Contributeurs
- Kamyl Taibi
- Raid Berrahal

## Licence
Ce projet est développé dans le cadre d'un cours à l'Université de Rouen.
