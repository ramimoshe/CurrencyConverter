


package me.currencies;

import me.currencies.common.CommonVariables;
import me.currencies.model.CurrencyController;
import me.currencies.model.CurrencyLogger;
import me.currencies.model.CurrencyManager;
import me.currencies.ui.CurrencyGui;

import java.awt.*;

import static java.lang.System.exit;

/**
 * Main class - responsible to start the application
 */
public class App {
    /**
     * main function
     *
     * @param args env argumets
     */
    public static void main(String[] args) {
        /**
         * instance of the logger
         */
        final CurrencyLogger logger = new CurrencyLogger();

        /**
         * instance of the model
         */
        final CurrencyController cm = new CurrencyManager(logger, true, CommonVariables.LOCAL_FILE_PATH.toString());

        //start the gui using edt thread
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CurrencyGui frame = new CurrencyGui(cm);
                    frame.setVisible(true);
                } catch (Exception e) {
                    logger.application().fatal("Cant start the App");
                    exit(1);
                }
            }
        });
    }
}
