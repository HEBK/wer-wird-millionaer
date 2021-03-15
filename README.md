# Wer wird Millionär
### Die berühmte Spielshow als Java-Applikation

## Features
Das Spiel bietet zahlreiche Funktionen. Darunter zum Beispiel:
- Erstellen von neuen Spielständen (GamerTag & Spielname)
- Speichern des aktuellen Spielstandes zum weiterspielen an späteren Zeitpunkten
- Aufnahme eines beendeten Spiels in die Bestenliste
- Wer wird Millionär Joker (Fünfzig-Fünfzig, Telefon & Publikum)
- Möglichkeit die Fragen zu verwalten & eigene hinzuzufügen
- Datenbank als Speicher zur Synchronisation mit mehreren Geräten

## Installation
HEBK/WWM -> Wer wird Millionär ist als Windows Installer verfügbar.
Dabei werden folgende Betriebssysteme unterstützt:
- Windows 7
- Windows Server 2012 R2
- Windows 8
- Windows Server 2016
- Windows 10
- Windows Server 2019

Um das Programm nun zu installieren führen sie die folgenden Schritte aus:
1. Laden Sie den letzten Release herunter. [Zum letzten Release](https://github.com/HEBK/wer-wird-millionaer/releases/latest) | [Letzter Installer](https://cdn.kleine-vorholt.eu/software/hebk/Wer_wird_Millionaer/latest/download.php)
2. Laden Sie den Installer mithilfe des angezeigten Links herunter.
3. Führen Sie den heruntergeladenen Installationsasisstenten aus.
4. Das Programm wurde auf Ihrem Computer installiert.

Beachten Sie, dass Sie beim erstmaligen starten der Software eine (SQL-)Datenbankverbindung hinterlegen müssen.
Um komplikationen zu vermeiden sollte die Datenbank leer sein oder keine Tabellen mit dem Prefix 'wwm_' enthalten.
Wenn die Verbindung erfolgreich hergestellt wurde, werden Sie zum Hauptmenü weitergeleitet. Das Spiel ist nun einsatzbereit!

### Java Version
Sollten Sie das Programm manuell aus den Quellcode-Dateien kompilieren wollen, müssen sie die Mindestanforderungen an die Java Version und die benötigten [Drittanbieterbibliotheken](#drittanbieterbibliotheken) berücksichtigen.
Zum Ausführen und Kompilieren der Software wird mindestens **Java SE 15** benötigt. 

## Mitwirkende
- Liz R. ([@Liz1312](https://github.com/Liz1312))
- Jost V. ([@JostVanheiden](https://github.com/JostVanheiden))
- Florian K. ([@Cr4zyFl1x](https://github.com/Cr4zyFl1x))

## Lizenz
Die zugehörige Lizenz zu dieser Software kann unter dem folgenden Link eingesehen werden:
(https://github.com/HEBK/wer-wird-millionaer/blob/master/licenses/LICENSE)

### Drittanbieterbibliotheken
- [Darcula](https://github.com/bulenkov/Darcula)
- [myBatis](https://github.com/mybatis/mybatis-3) - Version v3.2.3
- [Oracle MySQL Connector](https://github.com/mysql/mysql-connector-j) - Version v8.0.23
- [JetBrains Annotations](https://github.com/JetBrains/java-annotations) - Version 20.1.0

Für Lizenzinformationen zu den einzelnen Bibliotheken besuchen Sie bitte die jeweiligen Webseiten/Repositories.

## Bugs & Feature Requests
Fehler & Feature Requests können als [Issue](https://github.com/HEBK/wer-wird-millionaer/issues) eingetragen werden und werden schnellstmöglich bearbeitet.
