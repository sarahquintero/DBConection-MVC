/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VISTA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class SQLQueryJFrame extends JFrame {
    private JTextField queryField;
    private JButton createTableButton;
    private JButton selectButton;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JTable resultTable;
    private JScrollPane scrollPane;

    public SQLQueryJFrame() {
        setTitle("SQL Query Executor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Maximizar ventana al iniciar
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel para ingresar la consulta
        JPanel queryPanel = new JPanel();
        queryField = new JTextField(40); // Campo de texto para la consulta SQL

        // Crear botones específicos para cada tipo de consulta
        createTableButton = new JButton("CREATE");
        selectButton = new JButton("SELECT");
        insertButton = new JButton("INSERT");
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");
        clearButton = new JButton("Limpiar");

        // Añadir componentes al panel
        queryPanel.add(new JLabel("SQL Query:"));
        queryPanel.add(createTableButton);
        queryPanel.add(queryField);
        queryPanel.add(selectButton);
        queryPanel.add(insertButton);
        queryPanel.add(updateButton);
        queryPanel.add(deleteButton);
        queryPanel.add(clearButton);

        // Panel para mostrar los resultados
        resultTable = new JTable();
        scrollPane = new JScrollPane(resultTable);

        // Agregar los paneles a la ventana principal
        add(queryPanel, BorderLayout.NORTH); // Panel superior para la consulta
        add(scrollPane, BorderLayout.CENTER); // Panel central para la tabla de resultados
    }

    // Métodos para acceder a los componentes
    public String getQuery() {
        return queryField.getText();
    }

    public void setCreateTableButtonListener(ActionListener listener) {
        createTableButton.addActionListener(listener);
    }

    // Métodos para asignar ActionListeners a cada botón
    public void setSelectButtonListener(ActionListener listener) {
        selectButton.addActionListener(listener);
    }

    public void setInsertButtonListener(ActionListener listener) {
        insertButton.addActionListener(listener);
    }

    public void setUpdateButtonListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void setDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void setClearButtonListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    public void updateTable(JTable newTable) {
        resultTable.setModel(newTable.getModel());
    }

    // Método para limpiar el campo de texto y la tabla
    public void clearQueryAndTable() {
        queryField.setText(""); // Limpiar el campo de texto
        resultTable.setModel(new DefaultTableModel()); // Limpiar la tabla
    }
}
