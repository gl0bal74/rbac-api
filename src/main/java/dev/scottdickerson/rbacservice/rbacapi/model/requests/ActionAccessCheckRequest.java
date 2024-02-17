package dev.scottdickerson.rbacservice.rbacapi.model.requests;

public record ActionAccessCheckRequest(
    String protectedActionName, String userName, String password) {}
