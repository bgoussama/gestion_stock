````markdown
# StockPilot - Application Web de Gestion de Stock

StockPilot est une application web développée avec **Spring Boot** permettant de gérer un stock de produits de manière simple et organisée.  
L'application permet à un administrateur de gérer les produits, les catégories, les fournisseurs, les mouvements de stock, les alertes de seuil et les documents commerciaux.

---

## Objectif du projet

L'objectif principal de ce projet est de proposer une solution web complète pour la gestion de stock.  
L'application permet de suivre les quantités disponibles, d'enregistrer les entrées et sorties, de détecter les produits proches de la rupture et de générer des documents commerciaux au format PDF.

---

## Fonctionnalités principales

- Authentification sécurisée de l'administrateur
- Tableau de bord avec indicateurs clés
- Gestion complète des produits
- Gestion des catégories
- Gestion des fournisseurs
- Enregistrement des mouvements de stock
- Suivi des entrées, sorties et livraisons
- Détection automatique des alertes de seuil
- Résolution des alertes
- Création de documents commerciaux
- Génération et téléchargement de documents PDF
- Interface web responsive avec Thymeleaf et Bootstrap

---

## Technologies utilisées

- Java
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Thymeleaf
- H2 Database / MySQL
- Apache PDFBox
- Bootstrap
- Maven

---

## Architecture du projet

Le projet suit une architecture en couches :

```text
src/main/java
 └── com.example.gestionstock
      ├── config
      ├── controller
      ├── model
      ├── repository
      └── service
````

### Rôle des packages

| Package      | Description                                             |
| ------------ | ------------------------------------------------------- |
| `config`     | Configuration de sécurité et initialisation des données |
| `controller` | Gestion des routes web                                  |
| `model`      | Entités JPA et énumérations                             |
| `repository` | Interfaces Spring Data JPA                              |
| `service`    | Logique métier et génération PDF                        |

---

## Modules de l'application

### Authentification

L'accès à l'application est protégé par une page de connexion.
Seul l'administrateur peut accéder aux fonctionnalités internes.

### Tableau de bord

Le tableau de bord affiche les informations principales :

* nombre total de produits ;
* valeur du stock ;
* nombre de fournisseurs ;
* nombre d'alertes actives ;
* derniers mouvements enregistrés ;
* produits proches de la rupture.

### Produits

Le module produits permet de :

* consulter la liste des produits ;
* ajouter un nouveau produit ;
* modifier un produit existant ;
* supprimer un produit ;
* rechercher un produit ;
* associer un produit à une catégorie et à un fournisseur.

### Mouvements de stock

Le module mouvements permet d'enregistrer :

* les entrées de stock ;
* les sorties de stock ;
* les livraisons.

Après chaque mouvement, la quantité du produit est automatiquement mise à jour.

### Alertes

Le module alertes permet de suivre les produits dont la quantité est inférieure ou égale au seuil défini.
Une alerte peut être active ou résolue.

### Documents commerciaux

Le module documents permet de créer des documents commerciaux et de les télécharger au format PDF.

---

## Captures d'écran

Les captures d'écran principales de l'application sont :

```text
01-login.png
02-dashboard.png
03-produits.png
04-formulaire-produit.png
05-mouvements.png
06-alertes.png
07-documents.png
08-categories.png
09-fournisseurs.png
```

Elles illustrent le parcours utilisateur depuis la connexion jusqu'à la gestion complète du stock.

---

## Installation et exécution

### 1. Cloner le projet

```bash
git clone https://github.com/halima200431/gestion_stock.git
cd gestion_stock
```

### 2. Lancer l'application avec Maven

Sous Windows :

```bash
.\mvnw.cmd spring-boot:run
```

Sous Linux ou macOS :

```bash
./mvnw spring-boot:run
```

### 3. Accéder à l'application

Ouvrir le navigateur puis accéder à :

```text
http://localhost:8080
```

---

## Identifiants de connexion

```text
Nom d'utilisateur : admin
Mot de passe      : admin123
```

---

## Base de données

Par défaut, l'application peut utiliser une base de données H2 pour les tests et le développement.

Exemple d'accès à la console H2 :

```text
http://localhost:8080/h2-console
```

Les paramètres exacts dépendent de la configuration définie dans le fichier :

```text
src/main/resources/application.properties
```

---

## Tests

Pour lancer les tests automatisés :

```bash
.\mvnw.cmd test
```

Les tests permettent de vérifier :

* le démarrage correct du contexte Spring ;
* le fonctionnement du service de génération PDF.

---

## Routes principales

| Route                   | Méthode  | Description                 |
| ----------------------- | -------- | --------------------------- |
| `/login`                | GET/POST | Connexion                   |
| `/`                     | GET      | Tableau de bord             |
| `/products`             | GET/POST | Liste et ajout des produits |
| `/products/{id}/edit`   | GET      | Modification d'un produit   |
| `/products/{id}/delete` | POST     | Suppression d'un produit    |
| `/movements`            | GET/POST | Gestion des mouvements      |
| `/alerts`               | GET      | Liste des alertes           |
| `/alerts/{id}/resolve`  | POST     | Résolution d'une alerte     |
| `/documents`            | GET/POST | Gestion des documents       |
| `/documents/{id}/pdf`   | GET      | Téléchargement PDF          |
| `/categories`           | GET/POST | Gestion des catégories      |
| `/suppliers`            | GET/POST | Gestion des fournisseurs    |

---

## Structure simplifiée du projet

```text
gestion_stock
├── src
│   ├── main
│   │   ├── java
│   │   │   └── ...
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       └── application.properties
│   └── test
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## Améliorations possibles

* Ajouter plusieurs rôles utilisateurs
* Ajouter un rôle magasinier
* Relier les documents commerciaux aux mouvements de stock
* Ajouter la gestion des lots
* Ajouter la gestion des emplacements de stockage
* Ajouter un système de codes-barres
* Ajouter plus de tests unitaires et fonctionnels
* Ajouter une API REST pour une future application mobile

---

## Auteur

Projet réalisé dans le cadre d'un travail académique.

**Application :** StockPilot
**Type :** Application web de gestion de stock
**Technologies :** Spring Boot, Thymeleaf, Spring Data JPA, Spring Security

```
```
