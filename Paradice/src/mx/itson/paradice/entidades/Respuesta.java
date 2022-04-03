/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.paradice.entidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.itson.paradice.persistencia.Conexion;

/**
 * Metodos y atributos de cada respuesta
 *
 * @author AbelEsquer
 */
public class Respuesta {

    /**
     * @return the pregunta
     */
    public Pregunta getPregunta() {
        return pregunta;
    }

    /**
     * @param pregunta the pregunta to set
     */
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fercha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fercha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    private int id;
    private Pregunta pregunta;
    private Date fecha;
    private Usuario usuario;
    private String contenido;

   
    
    
    public List<Respuesta> obtenerRespuestas(int  id) {
        List<Respuesta> respuestas = new ArrayList<>();

        try {
            Connection conexion = Conexion.obtener();
            PreparedStatement statement = conexion.prepareStatement("""
                                                                    SELECT p.id, p.contenido, u.id, r.id, u.nombre, r.contenido, r.fecha 
                                                                    FROM respuesta r 
                                                                    inner join pregunta p on r.idPregunta = p.id 
                                                                    inner join usuario u on r.idUsuario = u.id 
                                                                    """);
            //statement.setString(1, "%" + id + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Respuesta r = new Respuesta();

                r.setId(resultSet.getInt(4));
                r.setContenido(resultSet.getString(6));
                r.setFecha(resultSet.getDate(7));

                Usuario u = new Usuario();
                u.setId(resultSet.getInt(3));
                u.setNombre(resultSet.getString(5));
                r.setUsuario(u);

                Pregunta p = new Pregunta();
                p.setId(resultSet.getInt(1));
                p.setContenido(resultSet.getString(2));
                r.setPregunta(p);
                respuestas.add(r);

            }
            conexion.close();
            
            
            
        } catch (Exception e) {
            System.err.print("Error: " + e.getMessage());
        }
        
         List<Respuesta> respuestasFinal = new ArrayList<>();
         for (int i = 0; i < respuestas.size(); i++) {
             if (respuestas.get(i).getPregunta().getId() == id) {
                 respuestasFinal.add(respuestas.get(i));
             }
             
        }
        return respuestasFinal;

    }
    
}
