package dev.scottdickerson.rbacservice.rbacapi.model.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActionAccessCheckResponse {
  String action;
  String user;
  boolean hasPermission;
  boolean allowsSudo;

  String message;
}
