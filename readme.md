### README für ToDo-App

---

#### **Installationsanleitung**

1. **Datenbank vorbereiten:**
   - Stelle sicher, dass die Datenbankdatei (`dba9.db`) korrekt in den Assets-Ordner eingefügt ist.
   - Die Datenbank muss die Tabelle `ToDo` mit der korrekten Struktur enthalten:
     ```sql
     CREATE TABLE ToDo (
         id INTEGER PRIMARY KEY AUTOINCREMENT,
         name TEXT NOT NULL,
         priority INTEGER NOT NULL,
         endTime INTEGER NOT NULL,
         description TEXT NOT NULL,
         status INTEGER NOT NULL
     );
     ```

2. **Projekt importieren:**
   - Öffne Android Studio.
   - Importiere das Projekt aus dem lokalen Ordner.

3. **Abhängigkeiten sicherstellen:**
   - Vergewissere dich, dass alle in `build.gradle` angegebenen Abhängigkeiten installiert sind.
   - Nutze das **Gradle Sync**, um sicherzustellen, dass alle Bibliotheken korrekt geladen wurden.

4. **App ausführen:**
   - Starte die App auf einem Emulator oder einem physischen Android-Gerät.
   - Beim ersten Start wird die Datenbank automatisch aus den Assets geladen, falls diese nicht existiert.

---

#### **Funktionsbeschreibung**

- **ToDos erstellen:**
  - Neue ToDo-Einträge können über den Hinzufügen-Dialog erstellt werden.
  - Details wie Name, Priorität (1-3), Endzeit und Beschreibung werden angegeben.

- **ToDos anzeigen:**
  - Die App unterstützt die Anzeige von aktiven und erledigten ToDos über eine Tab-Navigation.

- **ToDos bearbeiten:**
  - Vorhandene ToDo-Einträge können bearbeitet und aktualisiert werden.

- **ToDos löschen:**
  - Nicht mehr benötigte ToDos können aus der Datenbank entfernt werden.

- **Status aktualisieren:**
  - ToDos können als erledigt markiert werden.

---

#### **Verwendete Technologien**

- **Programmiersprache:** Kotlin
- **Framework:** Android Jetpack Compose
- **Datenbank:** SQLite (lokale Datenbank mit CRUD-Operationen)
- **Build-Tools:** Gradle
- **UI:** Material Design 3 (Material You)

---

#### **Bekannte Probleme**

1. **Datenbank nicht vorhanden:**
   - Falls die Datenbankdatei nicht in den Assets vorhanden ist, wird die App einen Fehler melden.

2. **Fehlende Tabelle:**
   - Falls die Tabelle `ToDo` nicht in der Datenbank existiert, wird ein entsprechender Hinweis im Log ausgegeben.
  
3. **Datenbankzugriff:**
   - Schreib- oder Lesefehler können auftreten, wenn die App keine Berechtigungen für den Zugriff auf die lokale Datenbank hat.
