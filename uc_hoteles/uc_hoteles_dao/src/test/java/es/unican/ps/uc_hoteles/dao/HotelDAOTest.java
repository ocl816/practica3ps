package es.unican.ps.uc_hoteles.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import es.unican.ps.uc_hoteles.common.domain.Hotel;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

@ExtendWith(MockitoExtension.class)
public class HotelDAOTest {

    @Mock 
    private EntityManager emMock;

    @Mock 
    private Query queryMock;

    @InjectMocks 
    private HotelDAO sut;

    private Hotel hotel;

    @BeforeEach 
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        hotel = new Hotel();
        hotel.setNombre("Hotel");

        when(emMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", hotel.getNombre())).thenReturn(queryMock);
    }

    @Test 
    public void test01_anhadeHotelNuevo() {
        //Indica que al buscar si existe el hotel la resulta este vacia (que no exista)
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());
 
        boolean resultado = sut.anhadeHotel(hotel);
 
        assertTrue(resultado);
        verify(queryMock).setParameter("nombre", "Hotel");
        verify(emMock).persist(hotel);
    }

    @Test 
    public void test02_anhadeHotelYaExistenteEnBBDD() {
        Hotel existente = new Hotel();
        existente.setNombre("Hotel");
        when(queryMock.getSingleResult()).thenReturn(existente);
 
        boolean resultado = sut.anhadeHotel(hotel);
 
        assertFalse(resultado);
        verify(emMock, never()).persist(any());
    }

    @Test 
    public void test03_anhadeHotelFalloAlPersistir() {
        //Indica que al buscar si existe el hotel la resulta este vacia (que no exista)
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());

        doThrow(new EntityExistsException()).when(emMock).persist(hotel);;
 
        boolean resultado = sut.anhadeHotel(hotel);
 
        assertFalse(resultado);
        verify(emMock).persist(hotel);
    }
}
