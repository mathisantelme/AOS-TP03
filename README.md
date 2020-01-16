# Architecture Orientée Service - Compte Rendu TP03

## Environement de travail

Ce TP nécessite l'installation et l'utilisation de:

- Un conteneur de servelts. Ici on utilise **Wildfly* *
- Une bibliothèque implémentant le protocole **SOAP**, en l'occurrence *Axis2*
- *TCPMon*, un outil permettant de visualiser des messages échangés via une connexion **TCP**
  
Comme la compilation et le fonctionnement des programmes client nécessiteront l'utilisation de nombreuses bibliothèques (et donc des fichiers **.jar**), on utilisera de préférence un environement de développement qui gère automatique la mise à jour de la variable d'environement **CLASSPATH** comme *Netbeans*

<!-- TODO: 
    - donner la valeur par défaut de openjdk8
    - modifier cette section si on utilise une image docker
    - afficher la liste des commandes utilisée et référencer le lien utilisé pour installer *Wildfly* 
 -->

### Installation de *Wildfly* (Ubuntu 19.04)

Pour un guide complet sur l'installation de *Wildfly*  sous Ubuntu 18.04 (fonctionne aussi sous 19.04), voici le [tutoriel](https://idroot.us/install-Wildfly-ubuntu-18-04-lts/) (en anglais).

#### Mise à jour du système

Avant toute chose on met à jour notre système.

```bash
sudo apt-get update
sudo apt-get upgrade
```

#### Installation de Java 8

On installe la version 8 de l'openjdk

```bash
sudo apt-get install openjdk-8-jdk
```

#### Création d'un utilisateur *Wildfly* 

On créé un nouvel utilisateur système pour *Wildfly*  ainsi qu'un groupe. On lui alloue un répertoire home /opt/*Wildfly* . Cet utilisateur sera utilisé pour lancer le service *Wildfly* .

```bash
sudo groupadd -r wildfly 
sudo useradd -r -g wildfly  -d /opt/wildfly  -s /sbin/nologin wildfly 
```

#### Installation de *Wildfly*

On télécharge [la dernière version de *Wildfly* ](http://wildfly.org/downloads/) et on l'extrait.

```bash
wget https://download.jboss.org/wildfly/$WILDFLY_VERSION/*Wildfly* -$WILDFLY_VERSION.tar.gz -P /tmp
sudo tar xf /tmp/wildfly-$WILDFLY_VERSION.tar.gz -C /opt/
```

Ensuite on créé un lien symbolique *Wildfly*  qui va pointer vers le dossier d'installation de *Wildfly* 

```bash
sudo ln -s /opt/wildfly$WILDFLY_VERSION /opt/wildfly 
sudo chown -RH wildfly: /opt/wildfly 
```

#### Configuration de *Wildfly* 

On créé un dossier ou sera stocké le fichier de configuration de *Wildfly* 

```bash
sudo mkdir -p /etc/wildfly 
sudo cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.conf /etc/wildfly/
```

Par défaut *Wildfly*  va se lancer en mode *standalone* et va écouter sur toutes les interfaces. On peut modifier ce fichier en fonction de nos besoins.

```bash
nano /etc/wildfly/wildfly.conf
```

```conf
# The configuration you want to run
WILDFLY_CONFIG=standalone.xml

# The mode you want to run
WILDFLY_MODE=standalone

# The address to bind to
WILDFLY_BIND=0.0.0.0
```

Ensuite on va copier le script de lancement de *Wildfly*  (*launch.sh*) dans le dossier `/opt/wildfly/bin/` .

```bash
sudo cp /opt/wildfly/docs/contrib/scripts/systemd/launch.sh /opt/wildfly/bin/
sudo sh -c 'chmod +x /opt/wildfly/bin/*.sh'
sudo cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.service /etc/systemd/system/
```

Ensuite on lance le service *Wildfly*  créé précédement grace aux commandes suivantes:

```bash
sudo systemctl daemon-reload
sudo systemctl start wildfly 
sudo systemctl enable wildfly 
```

#### Configuration du parefeu pour *Wildfly* 

On autorise le traffic sur le port `8080` avec cette commande:

```bash
sudo ufw allow 8080/tcp
```

#### Accès à l'installation de *Wildfly* 

*Wildfly*  est désormais accessible en HTTP sur le port 8080 *par défaut*. Vous devriez pouvoir accèder à *Wildfly*  à l'addresse suivante http://localhost:8080/. Suivez les instructions affichée à l'écran afin de terminer l'installation.

### Installation de *Axis2*

Une fois Wildfly installé, on va installer *Axis2* et tester le service web **Version** fournit avec.

#### Téléchargement d'*Axis2*

On télécharge tout d'abord [la dernière version déployable](https://axis.apache.org/axis2/java/core/download.html) (*WAR distribution*) d'*Axis2*.

![axis2warDownload](../AOS-TP03/img/axis2download.png)

#### Création d'un utilisateur propre à *Wildfly*

On va créer un utilisateur **administrateur** ou **Manager** (script **adduser.sh** dans le répertoire **bin/** de *Wildfly*) propre à *wildfly*. On utilisera cet utilisateur pour se connecter à la console de gestion de *wildfly*.

![création de l'utilisateur admin](https://link)

#### Lancement de *Wildfly*

Pour lancer le serveur *Wildfly* on effectue la commande suivante à partir de son répertoire d'installation (Nécessaire si *wildfly* n'as pas été défini comme un service lors de l'installation).
   
```bash
./bin/standalone.sh
```

Si *wildfly* à été défini comme un service lors de son installation, il faut utiliser les commandes suivantes:

```bash
sudo systemctl stop wildfly.service
sudo systemctl daemon-reload
sudo systemctl start wildfly.service
```

4. Ensuite, via un navigateur, se connecter au serveur *Wildfly* qui par défaut est accessible à l'URL suivante:
   > http://127.0.0.1:8080/

5. Après s'etre connecté avec l'utilisateur créé précédement, accéder à la console de gestion puis au menu "Deployments", deployer le *.war* d'*Axis2*.

1. On teste l'accès au Service Version de Axis2 en utilisant l'addresse suivante: 

```xml
<ns:getVersionResponse xmlns:ns="http://axisversion.sample">
    <ns:return>Hi - the Axis2 version is 1.7.9</ns:return>
</ns:getVersionResponse>
```