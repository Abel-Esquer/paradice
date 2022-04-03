/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.paradice.entidades;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import mx.itson.paradice.persistencia.Conexion;
import java.sql.ResultSet;
import mx.itson.paradice.entidades.Usuario;
import mx.itson.paradice.enumeradores.Estado;

/**
 * Metodos y atributos de cada pregunta
 *
 * @author AbelEsquer
 */
public class Pregunta {

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
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
     * @return the respuesta
     */
    public List<Respuesta> getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(List<Respuesta> respuesta) {
        this.respuesta = respuesta;
    }

    private int id;
    private Date fecha;
    private String contenido;
    private Usuario usuario;
    private List<Respuesta> respuesta;
    private Estado estado;

    /**
     * Obtiene los registros de la tabla pregunta.
     *
     * @param filtro Filtro define el criterio a buscar por nombre y pregunta.
     * @return Regresa un listado de preguntas.
     */
    public List<Pregunta> obtener(String filtro) {
        List<Pregunta> preguntas = new ArrayList<>();
        try {
            Connection conexion = Conexion.obtener();
            PreparedStatement statement = conexion.prepareStatement("SELECT p.id, p.contenido, p.fecha, p.estado, u.id, u.nombre FROM pregunta p inner join usuario u on p.idUsuario = u.id where p.contenido like ? or u.nombre like ?");
            statement.setString(1, "%" + filtro + "%");
            statement.setString(2, "%" + filtro + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Pregunta pregunta = new Pregunta();

                pregunta.setId(resultSet.getInt(1));
                pregunta.setContenido(resultSet.getString(2));
                pregunta.setFecha(resultSet.getDate(3));

                if (resultSet.getInt(4) == 1) {
                    pregunta.setEstado(getEstado().ABIERTA);
                } else {
                    if (resultSet.getInt(4) == 2) {
                        pregunta.setEstado(getEstado().RESUELTA);
                    } else {
                        if (resultSet.getInt(4) == 3) {
                            pregunta.setEstado(getEstado().CANCELADA);
                        }
                    }
                }

                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt(5));
                usuario.setNombre(resultSet.getString(6));
                pregunta.setUsuario(usuario);
                preguntas.add(pregunta);

            }
            conexion.close();

        } catch (Exception e) {
            System.err.print("Error: " + e.getMessage());
        }
        return preguntas;

    }
    
   

}
