package fr.simplon.microcommerce.inventory.repository;

import fr.simplon.microcommerce.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    public Optional<Inventory> findByProductId(UUID productId);
}
