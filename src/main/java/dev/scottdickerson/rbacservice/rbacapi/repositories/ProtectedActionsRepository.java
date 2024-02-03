package dev.scottdickerson.rbacservice.rbacapi.repositories;

import dev.scottdickerson.rbacservice.rbacapi.model.ProtectedAction;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProtectedActionsRepository extends CrudRepository<ProtectedAction, UUID> {}
