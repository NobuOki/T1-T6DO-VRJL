package pe.edu.cibertec.vilchezrojasjoseluis.loyalty.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoyaltyServiceImplTest {

    @Mock
    private LoyaltyRepository loyaltyRepository;

    @InjectMocks
    private LoyaltyServiceImpl loyaltyService;

    @Test
    void debeLanzarExcepcionCuandoElMontoEsInvalido() {

        assertThrows(
                IllegalArgumentException.class,
                () -> loyaltyService.calculateAndAssignPoints(
                        1L,
                        BigDecimal.ZERO
                )
        );

        verify(loyaltyRepository, never()).isVipUser(1L);
        verify(loyaltyRepository, never()).addPoints(anyLong(), anyInt());
    }

    @Test
    void debeAsignarElDobleDePuntosCuandoElUsuarioEsVIP() {

        when(loyaltyRepository.isVipUser(1L))
                .thenReturn(true);

        LoyaltyResponse response =
                loyaltyService.calculateAndAssignPoints(
                        1L,
                        new BigDecimal("100.00")
                );

        assertEquals(1L, response.userId());
        assertEquals(200, response.pointsEarned());
        assertTrue(response.isVip());

        verify(loyaltyRepository).isVipUser(1L);
        verify(loyaltyRepository).addPoints(1L, 200);
    }
}
