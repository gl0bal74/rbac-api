package dev.scottdickerson.rbacservice.rbacapi.model.requests;

public record ActionAccessCheckRequest(String intent, String user, String password) {}
