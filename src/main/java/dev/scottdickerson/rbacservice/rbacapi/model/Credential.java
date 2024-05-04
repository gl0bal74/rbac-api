package dev.scottdickerson.rbacservice.rbacapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.net.URL;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  UUID id;
    private  String userName;
    private  String intentName;
    private  String serviceURL;
    private  String apiKey;

}
