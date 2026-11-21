package pe.edu.cibertec.vilchezrojasjoseluis.loyalty.mockito;

public interface LoyaltyRepository {

    boolean isVipUser(Long userId);

    void addPoints(Long userId, int points);
}
