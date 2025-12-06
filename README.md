# Compatibility Engine (CLI Prototype)

Der algorithmische Prototyp für die PacoMatch Web-Applikation. Dies ist eine reine Java-Konsolenanwendung, die die Kernlogik des Kompatibilitäts-Algorithmus implementiert.

**Nachfolger ansehen:** [PacoMatch Web App](https://github.com/paco-37/gatekeeper-web)

---

## Über das Projekt

Bevor die Web-Version mit Spring Boot entwickelt wurde, diente dieses Projekt dazu, die Datenstrukturen und den Algorithmus zu testen. Es läuft komplett im Terminal und speichert Daten in einer lokalen SQL-Datenbank (via JDBC DAO Pattern).

## Features

* **CLI Interface:** Benutzerinteraktion über die Konsole (Scanner).
* **Datenbank-Anbindung:** Rohes JDBC ohne Frameworks (zum Verständnis der Grundlagen).
* **Logik:**
    * Fragen-Pool Verwaltung.
    * Gewichtete Punkteberechnung.
    * Highscore-System.

## Tech Stack

* Java (JDK 21)
* JDBC (Java Database Connectivity)
* MySQL / PostgreSQL Connector
* Maven

## Wie man es startet

1.  **Datenbank aufsetzen:**
    Führe das SQL-Skript `schema.sql` (falls vorhanden) in deiner lokalen Datenbank aus.

2.  **Kompilieren & Starten:**
    ```bash
    mvn clean install
    java -jar target/compatibility-engine.jar
    ```

---

*Dieses Projekt ist archiviert. Die Weiterentwicklung findet im Web-Repository statt.*
