/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Console;

import Castle.PDF;
import Castle.QuestionPrompter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Xandron
 public class ConsoleMain extends Application {

    PDF pdf;
    QuestionPrompter qp;

    public ConsoleMain() {
        this.pdf = new PDF();
    }

    @Override
    public void start(Stage primaryStage) {
        Boolean runApp = true;
        try {
            while (runApp) {
                // User Loads the PDF from template (pre-built)
                pdf.load("");

                // User Starts the questionare
                qp = new QuestionPrompter(pdf.getQuestions());
                qp.run();
                
                // User Saves PDF
                pdf.save("");
                
                // User Quits
                System.exit(0);
            }

        } catch (IOException e) {
        } catch (ParseException ex) {
            Logger.getLogger(ConsoleMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
*/