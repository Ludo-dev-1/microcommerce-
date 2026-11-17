# Microcommerce

Projet de démonstration d'une architecture microservices réalisée avec Spring Boot.

## Architecture

Le projet est composé de 4 microservices :

| Service | Port | Responsabilité |
|----------|----------|----------|
| Gateway | 8080 | Point d'entrée unique |
| Product | 8900 | Gestion des produits |
| Inventory | 8901 | Gestion des stocks |
| Order | 8902 | Gestion des commandes |

### Schéma d'architecture

```text
                +---------+
                | Gateway |
                |  8080   |
                +----+----+
                     |
      +--------------+--------------+
      |              |              |
      v              v              v

 +-----------+  +-----------+  +-----------+
 | Product   |  | Inventory |  |  Order    |
 |   8900    |  |   8901    |  |   8902    |
 +-----------+  +-----------+  +-----------+
```

## Responsabilités des microservices

### Product Service

Le service Product permet :

- de créer un produit ;
- de consulter un produit ;
- de consulter tous les produits.

Lorsqu'un produit est créé, un inventaire associé est automatiquement créé dans le service Inventory.

### Inventory Service

Le service Inventory permet :

- de gérer le stock des produits ;
- de consulter le stock disponible ;
- de mettre à jour la quantité en stock.

Lors de la consultation d'un inventaire, il récupère les informations du produit (nom et catégorie) auprès du service Product.

### Order Service

Le service Order permet :

- de créer une commande ;
- de vérifier la disponibilité du stock avant création ;
- de décrémenter le stock après validation de la commande.

Si le stock est insuffisant, le service retourne une erreur :

```http
400 Bad Request
```

avec le message :

```text
Stock insuffisant
```

### Gateway Service

Le service Gateway centralise tous les accès aux microservices.

Les appels clients passent uniquement par :

```text
http://localhost:8080
```

## Bases de données

Chaque microservice possède sa propre base H2 :

- Product → product_db
- Inventory → inventory_db
- Order → order_db

Cela respecte le principe d'isolation des microservices.

## Prérequis

- Java 21
- Maven 3.9+
- IntelliJ IDEA (ou autre IDE)

## Installation

Cloner le projet :

```bash
git clone <url-du-repository>
cd microcommerce
```

Compiler l'ensemble des modules :

```bash
mvn clean install
```

## Lancement des applications

Démarrer les microservices dans l'ordre suivant :

### 1. Inventory

```bash
cd inventory
mvn spring-boot:run
```

### 2. Product

```bash
cd product
mvn spring-boot:run
```

### 3. Order

```bash
cd order
mvn spring-boot:run
```

### 4. Gateway

```bash
cd gateway
mvn spring-boot:run
```

## Exemples d'utilisation

### Créer un produit

```http
POST http://localhost:8080/products
```

Body :

```json
{
  "name": "Chaussure de sport",
  "category": "Loisir"
}
```

Réponse :

```json
{
  "id": "f120a987-b0f2-47f4-bc09-184587c2b529",
  "name": "Chaussure de sport",
  "category": "Loisir"
}
```

---

### Consulter l'inventaire

```http
GET http://localhost:8080/inventory/{productId}
```

Réponse :

```json
{
  "productId": "f120a987-b0f2-47f4-bc09-184587c2b529",
  "name": "Chaussure de sport",
  "category": "Loisir",
  "quantity": 0
}
```

---

### Mettre à jour le stock

```http
PUT http://localhost:8080/inventory/{productId}?newQuantity=10
```

---

### Consulter le stock

```http
GET http://localhost:8080/inventory/{productId}/stock
```

Réponse :

```json
10
```

---

### Créer une commande

```http
POST http://localhost:8080/orders?productId={productId}&quantity=3
```

Réponse :

```json
{
  "id": "c14b2972-d9f5-41f2-a376-ce856c1ed2f5",
  "productId": "f120a987-b0f2-47f4-bc09-184587c2b529",
  "quantity": 3
}
```

---

### Cas de stock insuffisant

```http
POST http://localhost:8080/orders?productId={productId}&quantity=50
```

Réponse :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Stock insuffisant"
}
```

## Technologies utilisées

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Cloud Gateway
- H2 Database
- Maven
- Lombok

## Auteur

Projet réalisé dans le cadre d'une formation sur l'architecture microservices avec Spring Boot.