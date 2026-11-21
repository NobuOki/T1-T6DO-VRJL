package pe.edu.cibertec.vilchezrojasjoseluis.subscription;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private PaymentGatewayClient paymentGatewayClient;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    void debeSuspenderLaCuentaCuandoLaTarjetaEstaVencida() {

        Long subscriptionId = 1L;

        Subscription subscription = new Subscription(
                subscriptionId,
                "ACTIVE",
                LocalDate.of(2026, 12, 21)
        );

        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(subscription);

        when(paymentGatewayClient.isCardValid(subscriptionId))
                .thenReturn(false);

        assertThrows(
                ExpiredCardException.class,
                () -> subscriptionService.renewSubscription(
                        subscriptionId)
        );

        verify(subscriptionRepository)
                .updateStatus(subscriptionId, "SUSPENDED");

        verify(paymentGatewayClient, never())
                .chargeMonthlyFee(subscriptionId);

        verify(subscriptionRepository, never())
                .updateBillingDate(anyLong(), any(LocalDate.class));
    }

    @Test
    void debeRenovarLaSuscripcionCuandoLaTarjetaEstaVigente() {

        Long subscriptionId = 2L;

        LocalDate currentDate = LocalDate.of(2026, 11, 21);
        LocalDate expectedDate = currentDate.plusMonths(1);

        Subscription subscription = new Subscription(
                subscriptionId,
                "ACTIVE",
                currentDate
        );

        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(subscription);

        when(paymentGatewayClient.isCardValid(subscriptionId))
                .thenReturn(true);

        Subscription response =
                subscriptionService.renewSubscription(subscriptionId);

        assertEquals(subscriptionId, response.id());
        assertEquals("ACTIVE", response.status());
        assertEquals(expectedDate, response.nextBillingDate());

        verify(paymentGatewayClient)
                .chargeMonthlyFee(subscriptionId);

        verify(subscriptionRepository)
                .updateBillingDate(subscriptionId, expectedDate);
    }
}

