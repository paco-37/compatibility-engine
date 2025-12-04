package com.gatekeeper;

import java.util.List;
import java.util.Scanner;

import com.gatekeeper.dao.ApplicantDAO;
import com.gatekeeper.dao.CategoryDAO;
import com.gatekeeper.dao.QuestionDAO;
import com.gatekeeper.model.Applicant;
import com.gatekeeper.model.Category;
import com.gatekeeper.model.Option;
import com.gatekeeper.model.Question;

public class App {

    // Daten laden
    private static Scanner scanner = new Scanner(System.in);
    private static QuestionDAO dao = new QuestionDAO();
    private static ApplicantDAO applicantDAO = new ApplicantDAO();
    private static CategoryDAO categoryDAO = new CategoryDAO();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {

            System.out.println("--- GATEKEEPER ADMIN SYSTEM ---");
            System.out.println("[1] Kompatibilität herausfinden");
            System.out.println("[2] Neue Frage hinzufügen");
            System.out.println("[3] Frage löschen");
            System.out.println("[4] Highscores anzeigen");
            System.out.println("[5] Beenden");

            int selection = readValidInt("Auswahl: ", 1, 5);

            switch (selection) {
                case 1:
                    start();
                    break;
                case 2:
                    addQuestionMode();
                    break;
                case 3:
                    deleteQuestionMode();
                    break;
                case 4:
                    showHighscores();
                    break;
                case 5:
                    System.out.println("Programm beendet. Bis bald!");
                    running = false; 
                    break;
            }
        }
        scanner.close();
    }

    public static void start() {
        List<Question> questions = dao.getAllQuestions();

        if (questions.isEmpty()) {
            System.out.println("Warnung: Keine Fragen in der Datenbank!");
            return;
        }

        int userScore = 0;
        int maxPossibleScore = 0;

        System.out.println("--- WILLKOMMEN BEIM GATEKEEPER ---");
        System.out.println("Finde heraus, wie gut wir zusammenpassen!");
        System.out.println("----------------------------------------");

        // Game Loop
        for (Question q : questions) {
            System.out.println("\nFRAGE: " + q.getQuestionText());

            // Optionen anzeigen
            List<Option> opts = q.getOptions();
            for (int i = 0; i < opts.size(); i++) {
                System.out.println("   [" + (i + 1) + "] " + opts.get(i).getText());
            }

            // Eingabe lesen
            int choice = readValidInt("Deine Antwort: ", 1, opts.size());

            if (choice > 0 && choice <= opts.size()) {
                Option option = opts.get(choice - 1);

                userScore += option.getScore() * q.getWeight();
                maxPossibleScore += 10 * q.getWeight();
            } else {
                System.out.println("Ungültige Eingabe! 0 Punkte für diese Runde.");
                maxPossibleScore += 10 * q.getWeight();
            }
        }

        double percentage = ((double) userScore / maxPossibleScore) * 100;

        int finalScore = (int) Math.round(percentage);

        System.out.println("\n----------------------------------------");
        System.out.printf("Ergebnis: Wir passen zu %d %% zusammen!\n", finalScore);
        System.out.println("----------------------------------------");

        System.out.println("Möchtest du dein Ergebnis speichern?");
        System.out.print("Gib deinen Namen ein (oder Enter zum Überspringen): ");

        String name = scanner.nextLine();

        if (!name.trim().isEmpty()) {
            Applicant app = new Applicant(name, finalScore);
            applicantDAO.saveApplicant(app);
        }

    }

    public static void addQuestionMode() {
        System.out.println("\n--- NEUE FRAGE ANLEGEN ---");

        System.out.println("Wähle eine Kategorie:");
        List<Category> cats = categoryDAO.getAllCategories();
        for (Category c : cats) {
            System.out.println(c.toString());
        }

        int catId = readValidInt("Kategorie-ID eingeben: ", 1, cats.size());

        System.out.print("Fragetext eingeben: ");
        String text = scanner.nextLine();

        int weight = readValidInt("Gewichtung (1-10): ", 1, 10);

        Question newQ = new Question(0, "General", text, weight);
        int newId = dao.addQuestion(newQ, catId);

        if (newId == -1) {
            System.out.println("Fehler beim Speichern der Frage.");
        } else {
            int count = readValidInt("Wie viele Antwortmöglichkeiten: ", 1, 10);

            for (int i = 0; i < count; i++) {
                System.out.println("--- Antwort " + (i + 1) + " ---");

                System.out.print("Text: ");
                String antwortText = scanner.nextLine();

                System.out.print("Punktzahl (0-10): ");
                int points = scanner.nextInt();
                scanner.nextLine();

                dao.addOption(newId, antwortText, points);
            }
        }
    }

    private static void deleteQuestionMode() {
        System.out.println("\n--- FRAGE LÖSCHEN ---");

        List<Question> questions = dao.getAllQuestions();
        if (questions.isEmpty()) {
            System.out.println("Nichts zum Löschen da.");
            return;
        }

        System.out.println("Verfügbare Fragen:");
        System.out.println("ID | Frage");
        System.out.println("---|--------------------------");
        for (Question q : questions) {
            System.out.println(" " + q.getID() + " | " + q.getQuestionText());
        }

        // ID abfragen
        int idToDelete = readValidInt("\nWelche ID soll gelöscht werden? ", 1, Integer.MAX_VALUE);

        boolean success = dao.deleteQuestion(idToDelete);

        if (success) {
            System.out.println("Frage (und Antworten) erfolgreich gelöscht!");
        } else {
            System.out.println("Fehler: ID wurde nicht gefunden.");
        }
    }

    // Hilfsmethode für sichere Zahleneingabe
    // min & max bestimmen, welche Zahlen erlaubt sind (z.B. 1 bis 3)
    private static int readValidInt(String prompt, int min, int max) {
        int input;
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine(); // immer Text lesen

            try {
                input = Integer.parseInt(line);

                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Bitte eine Zahl zwischen " + min + " und " + max + " eingeben.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe! Bitte eine ganze Zahl eingeben.");
            }
        }
    }

    private static void showHighscores() {
        System.out.println("\n--- TOP 10 HIGHSCORES ---");
        List<Applicant> top10 = applicantDAO.getTopApplicants();

        if (top10.isEmpty()) {
            System.out.println("Noch keine Ergebnisse vorhanden.");
        } else {
            System.out.println("Rang | Name                 | Score");
            System.out.println("-----|----------------------|-------");

            int rank = 1;
            for (Applicant a : top10) {
                System.out.printf(" #%d  | %-20s | %d%%\n", rank, a.getFirst_name(), a.getTotal_score());
                rank++;
            }
        }
        System.out.println("-------------------------");
    }
}
