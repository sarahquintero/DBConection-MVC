/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconection.mvc;

import CONTROLADOR.SQLQueryController;
import MODELO.DBConexion;
import MODELO.QueryModel;
import VISTA.SQLQueryJFrame;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class DBConectionMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Conectar a la base de datos
            Connection connection = DBConexion.getConnection();

            // Crear el modelo, la vista y el controlador
            QueryModel model = new QueryModel(connection);
            SQLQueryJFrame view = new SQLQueryJFrame();
            new SQLQueryController(view, model);

            // Mostrar la vista
            view.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to database: " + e.getMessage());
        }
    }

}
