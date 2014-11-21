/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Console;

import Castle.PDF;
import Castle.QuestionBuilder;
import Castle.QuestionPrompter;
import java.io.IOException;

/**
 *
 * @author Xandron
 */
public class ConsoleMain implements Runnable{
    PDF pdf;
    QuestionBuilder qb;
    QuestionPrompter qp;
    public static void main(String [] args){
        ConsoleMain cm = new ConsoleMain();
        cm.run();
    }

    @Override
    public void run() {
        try {
            pdf.load("src\\resources\\6dot1.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
