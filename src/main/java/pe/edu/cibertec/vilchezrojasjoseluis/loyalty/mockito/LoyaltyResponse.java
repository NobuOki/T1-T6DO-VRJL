package pe.edu.cibertec.vilchezrojasjoseluis.loyalty.mockito;

public record LoyaltyResponse(
        Long userId,
        int pointsEarned,
        boolean isVip
) {}
