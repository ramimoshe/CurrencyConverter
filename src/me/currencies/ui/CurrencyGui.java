package me.currencies.ui;

import me.currencies.model.*;
import scala.collection.JavaConversions;

import java.util.Map;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.*;
import java.lang.Double;

public class CurrencyGui extends JFrame {

    private JPanel contentPane;
    private JTabbedPane tabbedPane;
    private JPanel converterTab;
    private JPanel ratesTab;

    private JPanel centerConverterPanel;
    private JTextField amountTextField;
    private CurrencyController cm;
    private JPanel toPanel;
    private JPanel fromPanel;
    private JComboBox fromComboBox;
    private JComboBox toComboBox;
    private JLabel resultLabel;
    private JPanel resultPanel;

    private JLabel lastUpdatedTableLbl;
    private JTable currenciesTable;

    public CurrencyGui(CurrencyController cm) {
        this.cm = cm;

        addMainWindow();
        addTabbedPanel();

        addConverterTab();
        addHeadConverterPanel();
        addConvertButtonPanel();
        addFromPanel();
        addToPanel();
        addAmountPanel();
        addResultPanel();

        addRatesTab();
    }

    private void addMainWindow() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    private void addTabbedPanel() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int indexTabSelected = sourceTabbedPane.getSelectedIndex();
                if (indexTabSelected == 1 ) {
                    lastUpdatedTableLbl.setText(cm.getLastUpdate());
                    fillTableData(currenciesTable.getModel());
                }
            }
        });
    }

    private void addConverterTab() {
        converterTab = new JPanel();
        converterTab.setLayout(new BorderLayout(0, 0));
        centerConverterPanel = new JPanel();
        centerConverterPanel.setLayout(new GridLayout(4, 1, 0, 0));
        converterTab.add(centerConverterPanel, BorderLayout.CENTER);

        tabbedPane.addTab("Converter", null, converterTab, null);
    }

    private void addHeadConverterPanel() {
        JPanel headPanelConverter = new JPanel();
        JLabel currencyConverterLbl = new JLabel("Currency Converter");
        currencyConverterLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
        headPanelConverter.add(currencyConverterLbl);

        converterTab.add(headPanelConverter, BorderLayout.NORTH);
    }

    private void addConvertButtonPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton convertBtn = new JButton("Convert");
        convertBtn.addActionListener(new ActionListener() {
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
        southPanel.add(convertBtn);
        converterTab.add(southPanel, BorderLayout.SOUTH);
    }

    private void addFromPanel() {
        fromPanel = new JPanel();

        JLabel fromLbl = new JLabel("From:    ");
        fromPanel.add(fromLbl);
        //fromLbl.setVerticalAlignment(SwingConstants.TOP);
        //fromLbl.setHorizontalAlignment(SwingConstants.CENTER);

        fromComboBox = new JComboBox();
        fromComboBox.setModel(new DefaultComboBoxModel(cm.getCurrenciesNames()));
        fromPanel.add(fromComboBox);

        centerConverterPanel.add(fromPanel);
    }

    private void addToPanel() {
        toPanel = new JPanel();

        JLabel toLbl = new JLabel("To:        ");
        //toLbl.setVerticalAlignment(SwingConstants.TOP);
        //toLbl.setHorizontalAlignment(SwingConstants.CENTER);
        toPanel.add(toLbl);

        toComboBox = new JComboBox();
        toComboBox.setModel(new DefaultComboBoxModel(cm.getCurrenciesNames()));
        toPanel.add(toComboBox);

        centerConverterPanel.add(toPanel);
    }

    private void addAmountPanel() {
        JPanel amountPanel = new JPanel();
        JLabel amountLbl = new JLabel("Amount: ");
        amountPanel.add(amountLbl);

        amountTextField = new JTextField();
        amountPanel.add(amountTextField);
        amountTextField.setColumns(10);

        centerConverterPanel.add(amountPanel);
    }

    private void addRatesTab() {
        ratesTab = new JPanel();
        ratesTab.setLayout(new BorderLayout(0, 0));
        JPanel headPanelRates = new JPanel();
        ratesTab.add(headPanelRates, BorderLayout.NORTH);

        JLabel currencyRatesHeader = new JLabel("Currency Rates");
        currencyRatesHeader.setFont(new Font("Tahoma", Font.PLAIN, 20));
        headPanelRates.add(currencyRatesHeader);

        JTable table = createRatesTable();
        JScrollPane scrollPane = new  JScrollPane(table);
        ratesTab.add(scrollPane, BorderLayout.CENTER);

        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Last Updated: "));
        lastUpdatedTableLbl = new JLabel(cm.getLastUpdate());
        datePanel.add(lastUpdatedTableLbl);
        ratesTab.add(datePanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Currency Rates", null, ratesTab, null);
    }

    private JTable createRatesTable() {
        currenciesTable = new JTable();
        currenciesTable.setEnabled(false);
        fillTableHeader(currenciesTable);
        fillTableData(currenciesTable.getModel());

        return currenciesTable;
    }

    private void fillTableHeader(JTable table) {
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "Country", "Currency", "Unit", "Rate"
                }
        ));
    }

    private void fillTableData(TableModel tableModel) {
        DefaultTableModel defaultTableModel = (DefaultTableModel)tableModel;
        int rowCount = defaultTableModel.getRowCount();
        //Remove rows one by one from the end of the currenciesTable
        for (int i = rowCount - 1; i >= 0; i--) {
            defaultTableModel.removeRow(i);
        }
        Map<String,Currency> currencies = JavaConversions.mapAsJavaMap(cm.getCurrencies());

        for (Map.Entry<String,Currency> coin: currencies.entrySet()) {
            defaultTableModel.addRow(new Object[]{coin.getValue().m_country(), coin.getValue().m_currencyCode(), coin.getValue().m_unit(), coin.getValue().m_rate()});
        }
    }

    private void addResultPanel() {
        resultPanel = new JPanel();
        resultLabel = new JLabel("");
        resultPanel.add(resultLabel);

        centerConverterPanel.add(resultPanel);
    }
}
