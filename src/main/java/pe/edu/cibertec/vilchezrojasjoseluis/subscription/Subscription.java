package pe.edu.cibertec.vilchezrojasjoseluis.subscription;

import java.time.LocalDate;

public record Subscription(
        Long id,
        String status,
        LocalDate nextBillingDate
) {}

