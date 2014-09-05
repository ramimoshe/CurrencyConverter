package me.currencies;

import me.currencies.model.CurrencyController;
import me.currencies.model.CurrencyManager;
import me.currencies.model.LogHelper;
import me.currencies.ui.CurrencyGui;

import java.awt.*;


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
