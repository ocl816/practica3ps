package es.unican.ps.uc_hoteles.common.dao;

import java.util.List;

import es.unican.ps.uc_hoteles.common.domain.Hotel;
import jakarta.ejb.Local;

@Local 
public interface IHotelDAO {
    
    /**
     * Anhade un hotel a la base de datos
     * @param h el hotel a anhadir
     * @return true si se ha anhadido correctamente, false en caso contrario
     */
    public boolean anhadeHotel(Hotel h); 

    /**
     * Modifica un hotel existente en la base de datos
     * @param h el hotel a modificar
     * @return el hotel modificado
     */
    public Hotel modificaHotel(Hotel h);

    /**
     * Elimina un hotel existente en la base de datos
     * @param h el hotel a eliminar
     * @return el hotel eliminado
     */
    public Hotel eliminaHotel(Hotel h);

    /**
     * Busca un hotel segun su nombre
     * @param nombre nombre del hotel
     * @return el hotel encontrado
     */
    public Hotel buscaHotel(String nombre);

    /**
     * Obtiene todos los hoteles registrados
     * @return la lista de todos los hoteles
     */
    public List<Hotel> buscaHoteles();

    /**
     * Obtiene los hoteles de una localidad
     * @param localidad localidad donde se buscaran los hoteles
     * @return la lista con los hoteles obtenidos
     */
    public List<Hotel> buscaHotelPorLocalidad(String localidad);

}
