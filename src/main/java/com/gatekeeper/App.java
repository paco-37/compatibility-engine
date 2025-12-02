package com.gatekeeper;

import java.util.List;
import java.util.Scanner;

import com.gatekeeper.dao.QuestionDAO;
import com.gatekeeper.model.Option;
import com.gatekeeper.model.Question;

public class App 
{
    public static void main( String[] args )
    {
        // Daten laden
        QuestionDAO dao = new QuestionDAO();
        List<Question> questions = dao.getAllQuestions();

        if(questions.isEmpty()){
            System.out.println("Warnung: Keine Fragen in der Datenbank!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int userScore = 0;
        int maxPossibleScore = 0;

        System.out.println("--- WILLKOMMEN BEIM GATEKEEPER ---");
        System.out.println("Finde heraus, wie gut wir zusammenpassen!");
        System.out.println("----------------------------------------");

        // Game Loop
        for(Question q : questions){
            System.out.println("\nFRAGE: " + q.getQuestionText());
            
            // Optionen anzeigen
            List<Option> opts = q.getOptions();
            for(int i=0; i < opts.size(); i++){
                System.out.println("   [" + (i+1) + "] " + opts.get(i).getText());
            }

            // Eingabe lesen
            System.out.print("Deine Antwort: ");
            // crasht noch bei Buchstaben 
            int choice = scanner.nextInt();

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
        
        System.out.println("\n----------------------------------------");
        System.out.printf("Ergebnis: Wir passen zu %.2f %% zusammen!\n", percentage);
        System.out.println("----------------------------------------");

        scanner.close(); 
    }
}