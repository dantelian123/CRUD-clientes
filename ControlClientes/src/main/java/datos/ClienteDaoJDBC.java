package datos;

import dominio.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoJDBC {

    private static final String SQL_SELECT = "Select idcliente, nombre, apellido, email, telefono, saldo From control_clientes.cliente";
    private static final String SQL_SELECT_BY_ID = "Select idcliente, nombre, apellido, email, telefono, saldo From control_clientes.cliente WHERE idcliente = ?";
    private static final String SQL_INSERT = "Insert INTO control_clientes.cliente(nombre, apellido, email, telefono, saldo)"
            + " Values (?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE control_clientes.cliente SET nombre=?, apellido=?, "
            + "email=?, telefono=?, saldo=? WHERE idcliente = ?";
    private static final String SQL_DELETE = "DELETE FROM control_clientes.cliente WHERE idcliente=?";

    public List<Cliente> listar() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idCliente = rs.getInt("idcliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                double saldo = rs.getDouble("saldo");
                cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);
        }
        return clientes;
    }

    public Cliente buscar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, cliente.getIdCliente());
            rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    System.out.println(cliente.getIdCliente());
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String email = rs.getString("email");
                    String telefono = rs.getString("telefono");
                    double saldo = rs.getDouble("saldo");
                    cliente.setNombre(nombre);
                    cliente.setApellido(apellido);
                    cliente.setEmail(email);
                    cliente.setTelefono(telefono);
                    cliente.setSaldo(saldo);
                } while (rs.next());
            }
            //rs.absolute(1);//nos posicionamos en el primer registro            

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);
        }
        return cliente;
    }

    public int insertar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        //Resulset es para recuperar informacion        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());
            //Regresa un entero indicando cuantos registros se han modificado
            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
        }
        return rows;
    }

    public int actualizar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        //Resulset es para recuperar informacion        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());
            stmt.setInt(6, cliente.getIdCliente());
            //Regresa un entero indicando cuantos registros se han modificado
            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
        }
        return rows;
    }

    public int eliminar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        //Resulset es para recuperar informacion        
        try {
            //Solo seteamos el parametro que se necesita que es el id del client e a eliminar
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, cliente.getIdCliente());
            //Regresa un entero indicando cuantos registros se han modificado
            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
        }
        return rows;
    }
}
