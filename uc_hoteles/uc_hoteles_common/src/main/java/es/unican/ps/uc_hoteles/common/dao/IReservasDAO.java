package es.unican.ps.uc_hoteles.common.dao;

import java.util.List;

import es.unican.ps.uc_hoteles.common.domain.Reserva;

public interface IReservasDAO {
    /**
     * Anhade una reserva a la base de datos
     * @param r reserva a anhadir
     * @return true si se ahnade correctamente, false en caso contrario
     */
    public boolean anhadeReserva(Reserva r);

    /**
     * Modifica una reserva existente en la base de datos 
     * @param r reserva a modificar
     * @return la reserva modificada
     */
    public Reserva modificaReserva(Reserva r);

    /**
     * Elimina una reserva existente en la base de datos
     * @param r reserva a eliminar
     * @return la reserva eliminada
     */
    public Reserva eliminaReserva(Reserva r);

    /**
     * Busca una reserva en la base de datos
     * @param idReserva id de la reserva
     * @return la reserva encontrada
     */
    public Reserva buscaReserva(int idReserva);

    /**
     * Obtiene todas las reservas registradas
     * @return la lista con todas las reservas
     */
    public List<Reserva> buscaReservas();

    /**
     * Obtiene todas las reservas de un hotel
     * @param nombreHotel nombre del hotel
     * @return la lista con todas las reservas del hotel
     */
    public List<Reserva> buscaReservasPorHotel(String nombreHotel);
}
