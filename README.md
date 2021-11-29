# Taskitory
Dieses Repository beinhaltet die Dokumentation und das Produkt einer Klausurersatzleistung für die Vorlesung "Advanced Software Engineering" an der DHBW Karlsruhe aus dem 3. Studienjahr (2021/22) des Studiengangs TINF19 angefertigt von Fabian Schwickert.

## Thema
Es gibt eine Reihe von Aufgabenverwaltungs-Systemen wie z. B. bei Jira, Youtrack oder GitHub, die mit einem Kanban Board arbeiten. Das Ziel dieser Klausurersatzleistung ist, eine Applikation zu entwickeln, die eine solche Aufgabenverwaltung modelliert. Es wird jedoch keine Benutzeroberfläche entwickelt, sondern lediglich die notwendige Anwendungslogik programmiert und über eine REST-API verfügbar gemacht. Zur Umsetzung wird die Programmiersprache Java und das Framework Spring Boot verwendet.
Mit der Anwendung soll es möglich sein, die Aufgaben in einem Projekt gemeinsam mit dem Projekt-Team zu managen. Dazu müssen Benutzer Accounts anlegen, die in den Kontext gemeinsamer Projekte gebracht werden. Das IAM soll dabei zuerst vernachlässigt werden. Später kann durch Einsatz eines IAM-Servers wie z. B. Keycloak ein professionelles IAM eingefügt werden.
Die Projekte dienen als Haupt-Bezugspunkt für die anderen Entitäten. Kanban Boards, Aufgaben und Statistiken sind zentral einem Projekt zugeordnet. Solche Zusammenhänge und auch die Speicherung von Daten kann zuerst durch JSON-Dateien repräsentiert werden. Später muss diese Form der Datenmodellierung und -speicherung jedoch durch eine Datenbank ersetzt werden.
Für die Aufgabenverwaltung sind die Aufgaben/Tasks das zentrale Element. Diese Tasks sollen mindestens die Attribute „Bezeichnung“, „Beschreibung“, „Start- und Endzeitpunkt“, „Komplexität“ sowie „Abhängigkeiten“ und „zuständige Person“ besitzen.
Diese Aufgaben sollen automatisch in der Aufgabensammlung eines Projekts landen, von wo sie einem Kanban Board hinzugefügt werden können. Ein Kanban Board kann allgemein aus einer unterschiedlichen Anzahl Spalten bestehen, die den Status einer Aufgabe beschreiben. Für diese Anwendung wird zunächst eine feste Anzahl und Art der Status angenommen: „ToDo“, „In Progress“, „Review“, „Testing“ und „Done“. Ein Kanban Board soll einen bestimmten Zeitraum (Sprint) betreffen. Ein Kanban Board verfällt nach dem Ende dieses Zeitraums. Es soll möglich sein, ein neues Kanban Board aus dem alten zu erzeugen.
Tasks sollen einem Kanban Board hinzugefügt und im Zuge auf dem Kanban Board verschoben werden können. Über ein Kanban Board können Statistiken angelegt werden, wie z. B. wie hoch der Anteil der Aufgaben in einem bestimmten Status ist.
 
## Use Cases
1.	User anlegen
Benutzer müssen einen Account für die Applikation besitzen und einem Projekt zugeordnet sein, um die zugehörigen Informationen einsehen zu können. Ein Benutzer-Account soll durch einen Username identifizierbar sein und mit einem Passwort authentifiziert werden. Weitere Userdaten sind für diese Applikation nicht relevant. Damit ein Benutzer über die REST-API auf die Applikation zugreifen kann, werden zunächst Username und Passwort in einer HTTP-Nachricht übermittelt. Später kann mit einem IAM-Server ein Access-Token erzeugt werden.
2.	Projekt anlegen
Ein Projekt ist der gemeinsame Kontext für alle anderen Entitäten. Ein Projekt hat daher auch Informationen über alle beteiligten Objekte dieser Entitäten. Neben allgemeinen Attributen wie z. B. einer Bezeichnung kennt das Projekt sein Projekt-Team, seine Aufgaben, seine Kanban Boards und seine Statistiken. Ein Projekt wird von einem Benutzer mit Bezeichnung und Projekt-Team angelegt und enthält automatisch eine leere Aufgabensammlung.
3.	Kanban Board anlegen
Für die Verwaltung von Aufgaben, werden bei dieser Applikation Kanban Boards eingesetzt. Kanban Boards müssen in einem bestehenden Projekt angelegt werden und für einen bestimmten Zeitraum erzeugt werden. Ein Kanban Board ist bei Erzeugung leer und besitzt die oben benannten Spalten. Es sollen mehrere Kanban Boards gleichzeitig bestehen können. Daher muss ein Kanban Board über eine Bezeichnung o. Ä. eindeutig identifizierbar sein.
4.	Kanban Board migrieren
Aus einem „abgelaufenen“ Kanban Board soll ein neues Kanban Board erzeugt werden. Das ist dann interessant, wenn sich auf dem alten Kanban Board noch nicht abgeschlossene Aufgaben befinden. Diese Aufgaben sollen in den gleichen Status des neuen Kanban Boards übernommen werden.
5.	Aufgaben anlegen
Aufgaben sind das Herzstück dieser Applikation. Eine Aufgabe soll die oben genannten Attribute enthalten und im Standardfall in die Aufgabensammlung eines Projekts eingefügt werden. Es soll aber möglich sein, die Aufgabe direkt einem bestimmten Kanban Board hinzuzufügen.
6.	Aufgaben zu einem Kanban Board hinzufügen
Es soll möglich sein, eine Aufgabe nachträglich aus der Aufgabensammlung zu einem Kanban Board hinzuzufügen.
7.	Aufgabe auf dem Kanban Board verschieben
Die zentrale Funktion eines Kanban Boards ist, Aufgaben über ihren Bearbeitungs-Status zu verwalten. Diese Funktion soll auch mit der Applikation möglich sein. Die Aufgaben auf einem Kanban Board sollen ihren Status auf Anweisung eines Benutzers ändern.
8.	Aufgaben bearbeiten
Es soll möglich sein, alle Attribute einer Aufgabe zu bearbeiten.
9.	Statistik erstellen
Wenn alle oberen Use Cases implementiert wurden, ist es möglich Statistiken über ein Kanban Board zu erstellen. Mögliche Anwendungsfälle sind z. B. Verteilung von Aufgaben nach Status oder durchschnittliche Bearbeitungsdauer.

## Technologien
+ Java
+ JUnit
+ Maven
+ Spring Boot
+ Postman
+ GitHub: https://github.com/Krayaty/Taskitory
+ dMöglicherweise Docker (3 Container: App, DBMS, IAM-Server)
