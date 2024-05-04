package dev.scottdickerson.rbacservice.rbacapi.model.requests;

import java.util.UUID;

public record ActionAccessCheckRequest(UUID intentId, UUID userId, String password) {}
