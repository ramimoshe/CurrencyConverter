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
    private JTextField amountTf;
    private CurrencyController cm;
    private JPanel toPanel;
    private JPanel fromPanel;
    private JComboBox fromComboBox;
    private JComboBox toComboBox;
    private JLabel resultLbl;
    private JPanel resultPanel;

    private JLabel lastUpdatedTableLbl;
    private JTable table;

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
                    fillTableData(table.getModel());
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
                    double amount = Double.parseDouble(amountTf.getText());
                    double result = cm.convert(amount, from, to);
                    resultLbl.setText(String.format("%1$,.2f", result));
                } catch (IllegalArgumentException ex) {
                    resultLbl.setText("Bad input");
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
        fromLbl.setVerticalAlignment(SwingConstants.TOP);
        fromLbl.setHorizontalAlignment(SwingConstants.CENTER);

        fromComboBox = new JComboBox();
        fromComboBox.setModel(new DefaultComboBoxModel(cm.getCurrenciesNames()));
        fromPanel.add(fromComboBox);

        centerConverterPanel.add(fromPanel);
    }

    private void addToPanel() {
        toPanel = new JPanel();

        JLabel toLbl = new JLabel("To:        ");
        toLbl.setVerticalAlignment(SwingConstants.TOP);
        toLbl.setHorizontalAlignment(SwingConstants.CENTER);
        toPanel.add(toLbl);

        toComboBox = new JComboBox();
        toComboBox.setModel(new DefaultComboBoxModel(cm.getCurrenciesNames()));
        toPanel.add(toComboBox);

        centerConverterPanel.add(toPanel);
    }

    private void addAmountPanel() {
        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel amountLbl = new JLabel("Amount: ");
        amountLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        amountPanel.add(amountLbl);

        JPanel amountInputPanel = new JPanel();
        amountInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        amountTf = new JTextField();
        amountTf.setColumns(10);
        amountInputPanel.add(amountTf);
        amountPanel.add(amountInputPanel);

        centerConverterPanel.add(amountPanel);
    }

    private void addRatesTab() {
        ratesTab = new JPanel();
        ratesTab.setLayout(new BorderLayout(0, 0));
        JPanel headPanelRates = new JPanel();
        ratesTab.add(headPanelRates, BorderLayout.NORTH);

        JLabel lblCurrencyRates = new JLabel("Currency Rates");
        lblCurrencyRates.setFont(new Font("Tahoma", Font.PLAIN, 20));
        headPanelRates.add(lblCurrencyRates);

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
        table = new JTable();
        table.setEnabled(false);
        fillTableHeader(table);
        fillTableData(table.getModel());

        return table;
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
        //Remove rows one by one from the end of the table
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
        resultPanel.setLayout(new GridLayout(0, 1, 0, 0));
        resultLbl = new JLabel("");
        resultLbl.setVerticalAlignment(SwingConstants.TOP);
        resultLbl.setHorizontalAlignment(SwingConstants.CENTER);
        resultPanel.add(resultLbl);

        centerConverterPanel.add(resultPanel);
    }
}
