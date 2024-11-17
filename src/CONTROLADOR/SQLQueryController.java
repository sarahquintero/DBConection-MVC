package CONTROLADOR;

import MODELO.QueryModel;
import VISTA.SQLQueryJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLQueryController {

    private SQLQueryJFrame view;
    private QueryModel model;

    public SQLQueryController(SQLQueryJFrame view, QueryModel model) {
        this.view = view;
        this.model = model;

        view.setCreateTableButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable();
            }
        });

        // Asignar listeners para cada botón específico
        view.setSelectButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeSelectQuery();
            }
        });

        view.setInsertButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeUpdateQuery("insert");
            }
        });

        view.setUpdateButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeUpdateQuery("update");
            }
        });

        view.setDeleteButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeUpdateQuery("delete");
            }
        });

        view.setClearButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar el campo de texto y la tabla de resultados
                view.clearQueryAndTable();
            }
        });
    }

    private void createTable() {
        String query = view.getQuery().trim();

        if (query.toLowerCase().startsWith("create table")) {
            try {
                model.executeUpdate(query);
                JOptionPane.showMessageDialog(view, "Tabla creada exitosamente.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Error al crear la tabla: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(view, "Consulta no válida para CREATE TABLE.");
        }
    }

    // Método para ejecutar consultas SELECT
    private void executeSelectQuery() {
        String query = view.getQuery().trim();

        if (query.toLowerCase().startsWith("select")) {
            try {
                // Ejecutar la consulta SELECT y obtener la tabla de resultados
                JTable resultTable = model.executeQuery(query);
                view.updateTable(resultTable);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Error ejecutando la consulta: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error inesperado: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(view, "Consulta no válida para SELECT.");
        }
    }

    // Método para ejecutar consultas de modificación (INSERT, UPDATE, DELETE)
    private void executeUpdateQuery(String queryType) {
        String query = view.getQuery().trim();

        if (query.toLowerCase().startsWith(queryType)) {
            try {
                int rowsAffected = model.executeUpdate(query);
                JOptionPane.showMessageDialog(view, rowsAffected + " filas afectadas.");

                // Obtener el nombre de la tabla a partir de la consulta
                String tableName = getTableNameFromQuery(query);
                if (tableName != null) {
                    // Mostrar los datos actualizados después de la modificación
                    JTable resultTable = model.executeQuery("SELECT * FROM " + tableName);
                    view.updateTable(resultTable);
                } else {
                    JOptionPane.showMessageDialog(view, "No se pudo obtener el nombre de la tabla.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Error ejecutando la consulta: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error inesperado: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(view, "Consulta no válida para " + queryType.toUpperCase() + ".");
        }
    }

    // Método para extraer el nombre de la tabla de la consulta
    private String getTableNameFromQuery(String query) {
        String tableName = null;
        Pattern pattern = Pattern.compile("(?i)(?:insert into|update|delete from)\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            tableName = matcher.group(1); // Captura el primer grupo que es el nombre de la tabla
        }

        return tableName;
    }
}
