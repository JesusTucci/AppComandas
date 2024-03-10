package org.example;

import org.example.models.Mesa;
import org.example.models.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private Connection connection;

    public DBManager () throws SQLException {
        this.connection = conexionDB();
    }

    public Connection conexionDB() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/comandas_db";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public List<Mesa> getMesas() {
        List<Mesa> mesas = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mesas");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int numero_mesa = resultSet.getInt(2);
                String estado = resultSet.getString(3);

                mesas.add(new Mesa(id, numero_mesa, estado));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mesas;
    }

    public void setEstadoMesa(int idMesa, String nuevoEstado) {
        try {
            String updateQuery = "UPDATE mesas SET estado = ? WHERE id_mesa = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, nuevoEstado);
                preparedStatement.setInt(2, idMesa);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> getProductos() {
        List<Producto> productos = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM productos");

            while (resultSet.next()) {
                int id = resultSet.getInt("id_producto");
                String nombre = resultSet.getString("nombre");
                String tipo = resultSet.getString("tipo");
                double precio = resultSet.getDouble("precio");

                productos.add(new Producto(id, nombre, tipo, precio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public Producto getProductoByNombre(String nombre) {
        Producto producto = null;

        try {
            String query = "SELECT * FROM productos WHERE nombre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nombre);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id_producto");
                    String nombreProducto = resultSet.getString("nombre");
                    String tipo = resultSet.getString("tipo");
                    double precio = resultSet.getDouble("precio");

                    producto = new Producto(id, nombreProducto, tipo, precio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    public void addProductoToComanda(int idMesa, int idProducto, int cantidad, double precioTotal) {
        try {
            String insertQuery = "INSERT INTO comandas (id_mesa, id_producto, cantidad, precio_total) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, idMesa);
                preparedStatement.setInt(2, idProducto);
                preparedStatement.setInt(3, cantidad);
                preparedStatement.setDouble(4, precioTotal);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> getProductosOnComanda(int idMesa) {
        List<Producto> productosEnComanda = new ArrayList<>();

        try {
            String selectQuery = "SELECT productos.* FROM productos " +
                    "JOIN comandas ON productos.id_producto = comandas.id_producto " +
                    "WHERE comandas.id_mesa = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, idMesa);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id_producto");
                    String nombre = resultSet.getString("nombre");
                    String tipo = resultSet.getString("tipo");
                    double precio = resultSet.getDouble("precio");

                    productosEnComanda.add(new Producto(id, nombre, tipo, precio));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productosEnComanda;
    }

    public void removeProductoComanda(int idMesa, int idProducto) {
        try {
            String deleteQuery = "DELETE FROM comandas WHERE id_mesa = ? AND id_producto = ? LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, idMesa);
                preparedStatement.setInt(2, idProducto);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarHistorialMesa(int idMesa, String estadoAnterior, String nuevoEstado) {
        try {
            String insertQuery = "INSERT INTO historial_mesas (id_mesa, estado_anterior, nuevo_estado, fecha_operacion) VALUES (?, ?, ?, NOW())";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, idMesa);
                preparedStatement.setString(2, estadoAnterior);
                preparedStatement.setString(3, nuevoEstado);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeComanda(int idMesa) {
        try {
            String deleteQuery = "DELETE FROM comandas WHERE id_mesa = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, idMesa);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
