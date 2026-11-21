package pe.edu.cibertec.vilchezrojasjoseluis.subscription;

public interface PaymentGatewayClient {

    boolean isCardValid(Long id);

    void chargeMonthlyFee(Long id);
}
