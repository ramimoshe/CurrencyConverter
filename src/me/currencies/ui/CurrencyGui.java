package me.currencies.ui;

import me.currencies.model.Currency;
import me.currencies.model.CurrencyController;
import scala.collection.JavaConversions;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * This is The main class of the UI
 * this class creates and holds the user interface and has access to currency controller interface
 */
public class CurrencyGui extends JFrame {

    // Variable declaration
    /**
     * Instance of the CurrencyController in the Gui
     */
    private CurrencyController cm;

    /*
    private local Swing variables
     */
    private JPanel contentPane;

    private JTabbedPane tabbedPane;
    private JPanel converterTab;
    private JPanel toPanel;
    private JPanel ratesTab;
    private JPanel centerConverterPanel;
    private JPanel headPanelConverter;
    private JPanel southPanel;
    private JPanel amountPanel;
    private JPanel lastUpdateDatePanel;
    private JPanel fromPanel;
    private JPanel resultPanel;
    private JPanel headPanelRates;

    private JTextField amountTextField;

    private JComboBox<String> fromComboBox;
    private JComboBox<String> toComboBox;

    private JTable currenciesTable;

    private JButton convertButton;

    private JLabel resultLabel;
    private JLabel lastUpdatedTableLabel;
    private JLabel currencyRatesHeader;
    private JLabel currencyConverterLabel;
    private JLabel amountLabel;
    private JLabel fromLabel;
    private JLabel toLabel;

    private JScrollPane scrollPane;

    /**
     * Primary constructor
     * creates the whole view part
     *
     * @param cm instance of the CurrencyController
     */
    public CurrencyGui(CurrencyController cm) {
        if (cm == null)
            throw new IllegalArgumentException("Bad input, controller equals null");
        this.cm = cm;

        addMainWindow();
        addTabbedPanel();
        addConverterTab();
        addRatesTab();
    }

    /**
     * Configures the main Jframe and adds a content panel to the main Jframe
     */
    private void addMainWindow() {
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    /**
     * Creates a tabbed pane and adds listener to that updates the ratesTable data every time the tab is selected
     */
    private void addTabbedPanel() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // Listener event for refreshing the ratesTable every time the ratesTable paned is selected
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int indexTabSelected = sourceTabbedPane.getSelectedIndex();
                if (indexTabSelected == 1) {
                    lastUpdatedTableLabel.setText(cm.getLastUpdate());
                    fillTableData(currenciesTable.getModel());
                }
            }
        });
    }

    /**
     * Created the main converter tab
     */
    private void addConverterTab() {
        converterTab = new JPanel();
        converterTab.setLayout(new BorderLayout(0, 0));
        centerConverterPanel = new JPanel();
        centerConverterPanel.setLayout(new GridLayout(4, 1, 0, 0));

        converterTab.add(centerConverterPanel, BorderLayout.CENTER);
        tabbedPane.addTab("Converter", null, converterTab, null);

        addHeadConverterPanel();
        addFromPanel();
        addToPanel();
        addAmountPanel();
        addResultPanel();
        addConvertButtonPanel();
    }

    /**
     * Creates the Header panel for the converter tab
     */
    private void addHeadConverterPanel() {
        headPanelConverter = new JPanel();
        currencyConverterLabel = new JLabel("Currency Converter");
        currencyConverterLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        headPanelConverter.add(currencyConverterLabel);
        converterTab.add(headPanelConverter, BorderLayout.NORTH);
    }

    /**
     * Creates the Footer of the converter tab
     * <p>adds a button that does the convert functionality</p>
     * <p>adds a listener that runs the convert from the controller</p>
     */
    private void addConvertButtonPanel() {
        southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        convertButton = new JButton("Convert");
        //add listener to the convert button that run the convert function from the controller
        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String from = fromComboBox.getSelectedItem().toString();
                String to = toComboBox.getSelectedItem().toString();
                try {
                    double amount = Double.parseDouble(amountTextField.getText());
                    double result = cm.convert(amount, from, to);
                    resultLabel.setText(String.format("%1$,.2f", result));
                } catch (IllegalArgumentException ex) {
                    resultLabel.setText("Bad input");
                }
            }
        });
        southPanel.add(convertButton);
        converterTab.add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the "From" of the converter panel
     * <p>add s "from" combo box for choosing the currency type</p>
     */
    private void addFromPanel() {
        fromPanel = new JPanel();

        fromLabel = new JLabel("From:    ");
        fromPanel.add(fromLabel);
        fromComboBox = new JComboBox<String>();
        //adding the currencies types to the combobox
        fromComboBox.setModel(new DefaultComboBoxModel<String>(cm.getCurrenciesNames()));
        fromPanel.add(fromComboBox);
        centerConverterPanel.add(fromPanel);
    }

    /**
     * Creates the "To" of the converter tab
     * <p>add s "from" combo box for choosing the currency type</p>
     */
    private void addToPanel() {
        toPanel = new JPanel();

        toLabel = new JLabel("To:        ");
        toPanel.add(toLabel);
        toComboBox = new JComboBox<String>();
        //adding the currencies types to the combobox
        toComboBox.setModel(new DefaultComboBoxModel<String>(cm.getCurrenciesNames()));
        toPanel.add(toComboBox);
        centerConverterPanel.add(toPanel);
    }


    /**
     * Creates the "Amount" panel of the converter tab
     * <p>adds a text field to the amount</p>
     */
    private void addAmountPanel() {
        amountPanel = new JPanel();
        amountLabel = new JLabel("Amount: ");
        amountPanel.add(amountLabel);

        amountTextField = new JTextField();
        amountPanel.add(amountTextField);
        amountTextField.setColumns(10);

        centerConverterPanel.add(amountPanel);
    }

    /**
     * Creates the Rates tab of the Rates ratesTable tab
     */
    private void addRatesTab() {
        ratesTab = new JPanel();
        ratesTab.setLayout(new BorderLayout(0, 0));

        tabbedPane.addTab("Currency Rates", null, ratesTab, null);

        addHeaderPanelRates();
        addRatesTableScrollPanel();
        addLastUpdateDatePanel();
    }

    /**
     * adds the ratesTable Scroll panel to the rates tab
     */
    private void addRatesTableScrollPanel() {
        currenciesTable = createRatesTable();
        scrollPane = new JScrollPane(currenciesTable);
        ratesTab.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * adds Last update footer panel
     */
    private void addLastUpdateDatePanel() {
        lastUpdateDatePanel = new JPanel();
        lastUpdateDatePanel.add(new JLabel("Last Updated: "));
        //gets last update from the controller
        lastUpdatedTableLabel = new JLabel(cm.getLastUpdate());
        lastUpdateDatePanel.add(lastUpdatedTableLabel);
        ratesTab.add(lastUpdateDatePanel, BorderLayout.SOUTH);
    }

    /**
     * add the header panel to the rates tab
     */
    private void addHeaderPanelRates() {
        headPanelRates = new JPanel();
        ratesTab.add(headPanelRates, BorderLayout.NORTH);

        currencyRatesHeader = new JLabel("Currency Rates");
        currencyRatesHeader.setFont(new Font("Tahoma", Font.PLAIN, 20));
        headPanelRates.add(currencyRatesHeader);
    }

    /**
     * Creates an instance of JTable
     *
     * @return instance if the Jtable
     */
    private JTable createRatesTable() {
        currenciesTable = new JTable();
        currenciesTable.setEnabled(false);
        fillTableHeader(currenciesTable);
        fillTableData(currenciesTable.getModel());

        return currenciesTable;
    }

    /**
     * sets headers to rates table
     *
     * @param table the table edited
     */
    private void fillTableHeader(JTable table) {
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Country", "Currency", "Unit", "Rate"
                }
        ));
    }

    /**
     * refills the rates table
     *
     * @param tableModel the table edited
     */
    private void fillTableData(TableModel tableModel) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tableModel;
        int rowCount = defaultTableModel.getRowCount();
        //Remove rows one by one from the end of the currenciesTable
        for (int i = rowCount - 1; i >= 0; i--) {
            defaultTableModel.removeRow(i);
        }
        //gets the currency map from the model and fills the ratesTable.
        final Map<String, Currency> currencies = JavaConversions.mapAsJavaMap(cm.getCurrencies());
        for (Map.Entry<String, Currency> coin : currencies.entrySet()) {
            defaultTableModel.addRow(new Object[]{coin.getValue().getCountry(), coin.getValue().getCurrencyCode(), coin.getValue().getUnit(), coin.getValue().getRate()});
        }
    }

    /**
     * adds the result label to the converter tab
     */
    private void addResultPanel() {
        resultPanel = new JPanel();
        resultLabel = new JLabel("");
        resultPanel.add(resultLabel);
        centerConverterPanel.add(resultPanel);
    }
}
