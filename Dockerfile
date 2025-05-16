# Utiliser une image Java officielle
FROM openjdk:17-jdk-slim

# Ajouter le fichier JAR dans le conteneur
COPY target/spring-security-app-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port (Render affecte le port via la variable d’environnement PORT)
EXPOSE 8080

# Commande pour démarrer l'app
ENTRYPOINT ["java","-jar","/app.jar"]
