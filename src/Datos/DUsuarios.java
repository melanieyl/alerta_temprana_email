package Datos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import pgsqlconnection.SqlConnection;
import utils.DateString;

/**
 *
 * @author melan
 */
public class DUsuarios {
    private SqlConnection connection; 
     public DUsuarios() {
        connection = new SqlConnection("postgres", "root", "127.0.0.1", "5432", "db_tecno");        
    }
     public void guardar(String nombre, String apellido, String ci, String genero,
            String correo, String fechaNacimiento, String telefono) throws SQLException, ParseException {
        
        String query = "INSERT INTO usuarios(nombre,apellido,ci,genero,correo,fecha_nacimiento,telefono)"
                + " values(?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, ci);
        ps.setString(4, genero);
        ps.setString(5, correo);
        ps.setDate(6, DateString.StringToDateSQL(fechaNacimiento));
        ps.setString(7, telefono);
        
        if(ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id, String nombre, String apellido, String ci,
            String genero, String correo, String fechaNacimiento, String telefono) 
            throws SQLException, ParseException {
        
        String query = "UPDATE usuarios SET nombre=?, apellido=?, ci=?,"
                + " genero=?, correo=?, fecha_nacimiento=?, telefono=? "
                + " WHERE id=?";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, ci);
        ps.setString(4, genero);
        ps.setString(5, correo);
        ps.setDate(6, DateString.StringToDateSQL(fechaNacimiento));
        ps.setString(7, telefono);
        ps.setInt(8, id);
        
        if(ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java dice: "
                    + "Ocurrio un error al modificar un usuario modificar()");
            throw new SQLException();
        }
    }
    
    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id=?";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ps.setInt(1, id);
        if(ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java dice: "
                    + "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
        }
    }
    
    public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            usuarios.add(new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("apellido"),
                set.getString("ci"),
                set.getString("genero"),
                set.getString("correo"),
                set.getString("fecha_nacimiento"),
                set.getString("telefono")
            });
        }
        return usuarios;
    }
    
    public String[] ver(int id) throws SQLException {
        String[] usuario = null;
        String query = "SELECT * FROM usuarios WHERE id=?";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ps.setInt(1, id);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            usuario = new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("apellido"),
                set.getString("ci"),
                set.getString("genero"),
                set.getString("correo"),
                set.getString("fecha_nacimiento"),
                set.getString("telefono")
            };
        }        
        return usuario;
    }
    
    public int getIdByCorreo(String correo) throws SQLException {
        int id = -1;
        String query = "SELECT * FROM usuarios WHERE correo=?";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ps.setString(1, correo);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            id = set.getInt("id");
        } 
        return id;
    }
    
    public void desconectar() {
        if(connection != null) {
            connection.closecConnection();
        }
    }    
    
}
