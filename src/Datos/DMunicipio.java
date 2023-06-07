/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import pgsqlconnection.SqlConnection;

/**
 *
 * @author melan
 */
public class DMunicipio {
    private final SqlConnection connection; 

    public DMunicipio() {
        connection = new SqlConnection("postgres", "root", "127.0.0.1", "5432", "db_tecno");
    }
    
    public void guardar(String nombre, String provincia) throws SQLException, ParseException{
        String query = "INSERT INTO municipio(nombre,provincia)"+ "values(?,?)";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, provincia);
        if(ps.executeUpdate() == 0) {
            System.err.println("Class DMunicipio.java dice: "
                    + "Ocurrio un error al insertar un Municipio guardar()");
            throw new SQLException();
        }
        
    }
        public void modificar(int id, String nombre, String provincia) 
            throws SQLException, ParseException {
        
        String query = "UPDATE municipio SET nombre=?, provincia=? "
                      + " WHERE id=?";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, provincia); 
        ps.setInt(3, id);
        
        if(ps.executeUpdate() == 0) {
            System.err.println("Class DMnunicipio.java dice: "
                    + "Ocurrio un error al modificar un municipio modificar()");
            throw new SQLException();
        }
    }
        public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id=?";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ps.setInt(1, id);
        if(ps.executeUpdate() == 0) {
            System.err.println("Class DMUnicipio.java dice: "
                    + "Ocurrio un error al eliminar un municipio eliminar()");
            throw new SQLException();
        }
    }
           public List<String[]> listar() throws SQLException {
        List<String[]> municipio = new ArrayList<>();
        String query = "SELECT * FROM municipio";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            municipio.add(new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("provincia"),               
            });
        }
        return municipio;
    }
      public String[] ver(int id) throws SQLException {
        String[] municipio = null;
        String query = "SELECT * FROM municipio WHERE id=?";
        PreparedStatement ps = connection.conect().prepareStatement(query);
        ps.setInt(1, id);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            municipio = new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("provincia")
            
            };
        }        
        return municipio;
    }
       public void desconectar() {
        if(connection != null) {
            connection.closecConnection();
        }
    }  
        
    
}
