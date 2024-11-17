package MODELO;

import java.sql.*;

import javax.swing.*;

public class QueryModel {
    private Connection connection;

    public QueryModel(Connection connection) {
        this.connection = connection;
    }

    // Método para ejecutar consultas SELECT y retornar una JTable
    public JTable executeQuery(String query) throws SQLException {
        if (connection == null) {
            throw new IllegalStateException("La conexión a la base de datos no está inicializada.");
        }

        // Crear un Statement con el tipo adecuado para el ResultSet
        try (Statement stmt = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear los nombres de las columnas
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            // Obtener datos de las filas
            rs.last(); // Ir a la última fila
            int rowCount = rs.getRow();
            rs.beforeFirst(); // Volver al inicio

            Object[][] data = new Object[rowCount][columnCount];
            int rowIndex = 0;
            while (rs.next()) {
                for (int colIndex = 1; colIndex <= columnCount; colIndex++) {
                    data[rowIndex][colIndex - 1] = rs.getObject(colIndex);
                }
                rowIndex++;
            }

            return new JTable(data, columnNames); // Devolver la tabla con los datos
        }
    }

    // Método para ejecutar consultas INSERT, UPDATE o DELETE
    public int executeUpdate(String query) throws SQLException {
        if (connection == null) {
            throw new IllegalStateException("La conexión a la base de datos no está inicializada.");
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query); // Devuelve el número de filas afectadas
        }
    }

    // Método para cerrar la conexión
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Maneja la excepción
            }
        }
    }
}
