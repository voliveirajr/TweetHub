# README #

This is an exercise implementation for an Assessment.
This app consists in a self-contained command line that search for projects on GitHub with a given key and find related tweets in twitter

### Requirements ###

* Java 1.7
* Maven 3.x

### Configuration ###
Before run your build the properties should be configurated.
At the root folder is available a yaml file application.yaml

There is available the folowing settings:
* GitHub url api (required)
* All tokens required for twitter connection (required)
* Proxy settings if necessary (leave the settings without value for no proxy)

Use up to 10 for twitter limit for development accounts, over that twitter will block your requests. 

### How to build? ###

As a maven project, should be executed:

```
#!shell
mvn clean package
```
It will generate your snapshot version jar

### Running ###

To run the application should be executed as follow

```
#!shell
java -jar tweethub-0.0.1-SNAPSHOT.jar tetris
```
This will print on console all related tweets regarding the projects with the name given in json format.