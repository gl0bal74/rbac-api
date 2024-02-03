package dev.scottdickerson.rbacservice.rbacapi.repositories;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AccessTierRepository extends CrudRepository<AccessTier, UUID> {


}
