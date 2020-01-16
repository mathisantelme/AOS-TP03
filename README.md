# Architecture Orientée Service - Compte Rendu TP03

## Environement de travail

Ce TP nécessite l'installation et l'utilisation de:

- d'un conteneur de servelts. Ici on utilise *Wildfly*
- une bibliothèque implémentant le protocole **SOAP**, en l'occurrence *Axis2*
- de *TCPMon*, un outil permettant de visualiser des messages échangés via une connexion **TCP**
- comme la compilation et le fonctionnement des programmes client nécessiteront l'utilisation de nombreuses bibliothèques (et donc des fichiers **.jar**), on utilisera de préférence un environement de développement qui gère automatique la mise à jour de la variable d'environement **CLASSPATH** comme *Netbeans*
- la variable d'environement **JAVA_HOME** doit etre affectée à la valeur du chemin d'accès du *java 8*

<!-- TODO: 
    - donner la valeur par défaut de openjdk8
    - modifier cette section si on utilise une image docker
    - afficher la liste des commandes utilisée et référencer le lien utilisé pour installer wildfly
 -->

## Test d'un service Web existant

Après avoir installé *WildFly* il faut:

1. télécharger la dernière version déployable (*WAR distribution*) d'*Axis2*.
2. créer un administrateur (script **adduser.sh** dans le répertoire **bin/** de *WildFly*)
3. Puis lancer le serveur *WildFly* en effectuant la commande suivante à partir de son répertoire d'installation
   
```bash
./bin/standalone.sh
```

4. Ensuite, via un navigateur, se connecter au serveur *WildFly* qui par défaut est accessible à l'URL suivante:
   > http://127.0.0.1:8080/

5. Après s'etre connecté avec l'utilisateur créé précédement, accéder à la console de gestion puis au menu "Deployments", deployer le *.war* d'*Axis2*.

1. On teste l'accès au Service Version de Axis2 en utilisant l'addresse suivante: 

```xml
<ns:getVersionResponse xmlns:ns="http://axisversion.sample">
    <ns:return>Hi - the Axis2 version is 1.7.9</ns:return>
</ns:getVersionResponse>
```