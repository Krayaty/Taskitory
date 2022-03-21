# Taskitory
Dieses Repository beinhaltet die Dokumentation und das Produkt einer Klausurersatzleistung für die Vorlesung "Advanced Software Engineering" an der DHBW Karlsruhe aus dem 3. Studienjahr (2021/22) des Studiengangs TINF19 angefertigt von Fabian Schwickert.

## Thema
Es gibt eine Reihe von Aufgabenverwaltungs-Systemen wie z. B. Jira, Youtrack oder Mi-crosoft Planner, die mit einem Kanban Board arbeiten. Die hier dokumentierte Klau-surersatzleistung besteht in der Entwicklung einer Applikation, die eine solche Aufga-benverwaltung modelliert. Es wird keine graphische Benutzeroberfläche (GUI) entwickelt und lediglich die notwendige Anwendungslogik programmiert und über einen REST-Service (Representational State Transfer) verfügbar gemacht. Für die Implementierung des Rest-Services wird die Programmiersprache Java mit dem Package Manager „Maven“ und dem „Spring-Boot-Framework“ verwendet.
Mit der Anwendung soll es möglich sein, die Aufgaben in einem Projekt gemeinsam in einem Projekt-Team zu verwalten. Dazu müssen Benutzer Accounts anlegen, die in den Kontext gemeinsamer Projekte gebracht werden. Das Identity- und Accessmanagement (IAM) soll dabei durch einen Keycloak umgesetzt werden. Eine persistente Datenspei-cherung wird mit einer Postgresql-Datenbank (DB) umgesetzt. In der DB werden die Daten des Keycloaks und ein eigenes DB-Modell gehalten. Die benannten Komponenten (Keycloak, DB und REST-Service) der Applikation sollen dockerisiert werden und mit „Docker-Compose“ konfiguriert und betrieben werden.
Um die Bedienung des REST-Services zu vereinfachen, wird eine Sammlung von Anfragen und Konfigurationen für einen HTTP-Client zur Verfügung gestellt. Dazu wird eine „Collection“ für Anwendung Postman zur Verfügung gestellt.

## Use Cases
Die Anforderungen an die Applikation, die im Rahmen dieser Klausurersatzleistung entwickelt wird, sind weniger umfangreich als bei Referenz-Applikationen wie z. B. Youtrack oder Jira. Allerdings orientiert sich die die eigene Applikation an solchen Referenz-Applikationen. Nachfolgend werden allgemeine funktionale Anforderungen definiert und zentrale Use-Cases festgehalten, die implementiert werden sollen. Nicht-Funktionale Anforderungen werden nur dann betrachtet, wenn entsprechende Probleme auftreten.

**Projekte und Benutzer**

Die Projekt-Entitäten sind der Haupt-Bezugspunkt für die anderen Entitäten. Benutzer sind einem oder mehreren Projekten zugeordnet und können über eine Projekt-Entität auf die Ressourcen eines Projekts („Kanban Boards“, „Aufgaben“ und „Tags“) zugreifen. Dabei können User die Rollen eines einfachen „Users“ oder eines Projekt-Administrators („Admin“) innehaben. Es muss zu jeder Zeit mindestens einen Projekt-Administrator geben. Es gibt keine Obergrenze für die Anzahl von Projekt-Administratoren. Die Rollen beschränken den Zugriff auf die Ressourcen eines Projekts. Einfache User können die Attribute einer Projekt-Entität nicht verändern. Zudem sollen einfache User keine bestehenden User entfernen oder neue hinzufügen können. Einfache User können Datensätzen der anderen Entitäten hinzufügen, verändern und löschen. Projekt-Administratoren sollen alle Zugriffs-Rechte auf Ressourcen und Funktionen eines Projekts haben. Das schließt das Hinzufügen, Befördern und Entfernen von Benutzern ein.
Über die Zeit sollen Benutzer ein Projekt verlassen und wieder beitreten können. Es ist nicht möglich einem Projekt, ohne die Zustimmung des Projekt-Teams beizutreten. Dafür wird ein geheimer Projekt-Schlüssel verwendet, der nur von Projekt-Administratoren einsehbar ist.

**Aufgaben**

Eine zentrale Funktion der Applikation ist die Erstellung und Verwaltung von Aufgaben. Die Aufgaben-Entität ist dem entsprechend wichtig. Die Aufgaben werden neben Entitäs-Attributen auch mit Meta-Informationen in „Tags“ beschrieben. Aufgaben haben die Attribute „Bezeichnung“, „Beschreibung“, „Erstellungs-“ und „Fertigstellungs-Zeitpunkt“, „Komplexität“, „Bearbeitungs-Status“ und „zuständige Person“ besitzen. Die Bezeichnung einer Aufgabe ist in einem Projekt eindeutig. Es sollen Aufgaben-Kompositionen und Abhängigkeiten zwischen Aufgaben möglich sein. Aufgaben sind nach der Erzeugung automatisch in dem „Backlog“ eines Projekts ein-sehbar, von wo sie einem „Kanban Board“ hinzugefügt werden können.

**Kanban Board**

Ein Kanban Board betrifft einen bestimmten Zeitraum („Sprint“). Nach einem Sprint besteht das Kanban Board weiter, kann aber nicht mehr bearbeitet werden. Ein altes Kanban Board kann in ein neues Kanban Board überführt werden.
Ein Kanban Board kann im Rahmen dieser Klausurersatzleistung aus drei bis zu fünf Spalten bestehen, die den Status einer Aufgabe beschreiben. Die Spalten „ToDo“, „In Progress“, und „Done“ sind obligatorisch. Die Spalten „Review“ und „Testing“ können bei Bedarf hinzugefügt und wieder entfernt werden. Dabei sollen dort eingeordnete Aufgaben ihren Bearbeitungs-Status nicht verlieren. Die Spalte und die darin enthaltenen Aufgaben werden lediglich ausgeblendet. Aufgaben können einem Kanban Board hinzugefügt und auf dem Kanban Board in einen anderen Bearbeitungs-Status verschoben werden.
Über einen Sprint können Statistiken angelegt werden. Dafür sollen entsprechende Kanban Boards als Repräsentation eines Sprints genutzt werden. Es soll z. B. ausgewer-tet werden, welcher Anteil der Aufgaben auf einem Kanban Board in einem bestimm-ten Bearbeitungs-Status ist.

**Nachrichten**

Ein Benutzer erhält Nachrichten über bestimmte Änderungen in den Projekten, denen er angehört. Diese Nachrichten haben einen Absender, einen Adressaten und einen Inhalt. Zudem wird über ein Feld festgehalten, ob die Nachricht vom Benutzer abgerufen wurde. Nachrichten sind nicht dazu gedacht, um Konversation abzuhalten.

## Technologien
+ Java
+ JUnit
+ Maven
+ Spring Boot
+ Postman
+ GitHub: https://github.com/Krayaty/Taskitory
+ Docker Compose (3 Container: App, DBMS, IAM-Server)
