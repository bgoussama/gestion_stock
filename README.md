# StockPilot

Application web de gestion de stock realisee avec Spring Boot, Thymeleaf, Bootstrap, Spring Security, JPA et H2.

## Fonctionnalites

- Authentification securisee avec un compte administrateur
- Tableau de bord avec indicateurs de stock
- Gestion des produits, categories et fournisseurs
- Mouvements de stock: entree, sortie et livraison
- Mise a jour automatique des quantites
- Alertes lorsque le stock atteint le seuil defini
- Documents commerciaux avec generation PDF
- Base H2 locale par defaut, avec configuration MySQL preparee

## Demarrage

```powershell
.\mvnw.cmd spring-boot:run
```

Adresse de l'application:

```text
http://localhost:8080
```

## Connexion

```text
Utilisateur: admin
Mot de passe: admin123
```

Les identifiants peuvent etre modifies dans:

```text
src/main/resources/application.properties
```

avec les proprietes:

```properties
app.security.username=admin
app.security.password=admin123
```

## Base de donnees

Par defaut, l'application utilise une base H2 locale:

```text
jdbc:h2:file:./data/gestion-stock
```

Console H2:

```text
http://localhost:8080/h2-console
```

La console H2 est protegee par le login de l'application. Il faut d'abord se connecter avec le compte administrateur, puis utiliser:

```text
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:file:./data/gestion-stock
User Name: sa
Password:
```

## Tests

```powershell
.\mvnw.cmd test
```
