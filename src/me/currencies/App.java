package me.currencies;

import java.awt.*;

import me.currencies.model.*;
import me.currencies.ui.*;

/**
 * Created by user on 30/08/2014.
 */
public class App {
    public static void main(String[] args) {

        final LogHelper logger = new LogHelper();
        final CurrencyController cm = new CurrencyManager(logger, true, "CURRENCIES.xml");


        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CurrencyGui frame = new CurrencyGui(cm);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
