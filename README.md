# Rapport de Projet : Système Microservices E-Banking & Intégration IA

## 1. Introduction
Ce projet s'inscrit dans le cadre du développement d'une architecture logicielle moderne basée sur des **microservices**. L'objectif principal est de concevoir un système bancaire distribué, résilient, tolérant aux pannes et interconnecté, tout en intégrant des fonctionnalités avancées d'intelligence artificielle (Chatbot conversationnel).

Les principales réalisations de cette première partie comprennent :
* Le développement des services métiers (`Customer-Service` et `EBank-Service`).
* La mise en place de l'infrastructure des microservices avec l'annuaire de services (`Discovery Service` / Eureka) et la passerelle d'API (`Spring Cloud Gateway`).
* L'établissement d'une communication inter-services déclarative et sécurisée via **OpenFeign**.
* L'implémentation de la tolérance aux pannes et de la résilience à l'aide de **Resilience4J (Circuit Breaker)**.
* Les tests et la validation des API à l'aide de Postman et de l'interface interactive Swagger-UI.

---

## 2. Architecture du Projet
L'architecture globale du système repose sur un écosystème de microservices Spring Boot communiquant entre eux et gérés par une infrastructure centralisée :
![Architecture du système microservices](./images/img1.png)
---

## 3. Implémentation et Validation des Services

### A. Discovery Service (Eureka Server)
Le serveur d'enregistrement et de découverte permet à l'ensemble des microservices de s'enregistrer dynamiquement et d'être découverts sans configuration d'adresses IP statiques.

* **Tableau de bord Eureka (Port 8761)** :
  La capture ci-dessous montre l'enregistrement réussi de tous nos services (`CUSTOMER-SERVICE`, `EBANK-SERVICE`, et `GATEWAY-SERVICE`) avec le statut **UP**.

  ![Tableau de bord Eureka](./images/img2.png)
 
---

### B. Customer-Service (Port 8056)
Ce service gère les opérations CRUD relatives aux clients de la banque.

* **Liste des clients (GET /customers)** :
  Accès direct à la liste des clients enregistrés au format JSON.

  ![Liste des clients](images/img3.png)

* **Recherche d'un client par ID (GET /customers/1)** :
  Récupération des détails d'un client spécifique (ex: ID 1 - Wissal).

  ![Client par ID](./images/img4.png)

* **Tests via Swagger-UI (Customer Service)** :
    * **GET - Récupération de tous les clients via Swagger** :
      ![Swagger GET Customers](./images/img5.png)

    * **POST - Ajout d'un nouveau client via Swagger** :
      ![Swagger POST Customer](./images/img6.png)

---

### C. EBank-Service & Communication OpenFeign (Port 8057)
Ce service gère les comptes bancaires et communique avec le `Customer-Service` via **OpenFeign** pour lier chaque compte à son propriétaire, tout en étant protégé par **Resilience4J** contre les pannes distantes.

* **Liste de tous les comptes bancaires (GET /accounts)** :
  ![Liste des comptes](./images/img7.png)

* **Création d'un compte bancaire (POST /accounts)** :
  ![Ajout de compte via Swagger](./images/img8.png)

* **Recherche d'un compte par ID (GET /accounts/{id})** :
  ![Compte par ID](./images/img9.png)

---

### D. Spring Cloud Gateway & Routage Dynamique (Port 9999)
La passerelle d'API sert de point d'entrée unique pour toutes les requêtes clients, assurant le routage dynamique vers les microservices sous-jacents enregistrés dans Eureka en utilisant leurs noms en majuscules.

* **Routage Gateway vers Customer-Service (`http://localhost:9999/CUSTOMER-SERVICE/customers`)** :
  ![Test Gateway Customers](images/img11.png)

* **Routage Gateway vers EBank-Service (`http://localhost:9999/EBANK-SERVICE/accounts`)** :
  ![Test Gateway Accounts](./images/img12.png)

* **Routage Gateway vers un compte spécifique (`http://localhost:9999/EBANK-SERVICE/accounts/{id}`)** :
    * *Cas normal (Service client joignable)*:
      ![Compte avec client valide](./images/img13.png)
    * *Cas de tolérance aux pannes (Fallback Resilience4J)* :
      Lorsque le `Customer-Service` est injoignable, le Circuit Breaker se déclenche et renvoie gracieusement `"Not available"` au lieu de lever une erreur critique 500.
      ![Fallback Resilience4J](./images/img14.png)

---

## 4. Conclusion
Cette première étape a permis de valider la mise en place d'une architecture microservices robuste avec Spring Boot et Spring Cloud. La communication inter-services via OpenFeign, la centralisation du routage par la Gateway et la tolérance aux pannes avec Resilience4J sont pleinement fonctionnelles.
