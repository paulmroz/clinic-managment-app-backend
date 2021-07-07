package pl.psk.clinicmanagement.domain.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.domain.user.Client;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "visits")
@Builder
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "scheduled_for")
    private LocalDateTime scheduledFor;

    @ManyToOne
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "CLIENT_ID_FK"))
    @JsonIgnoreProperties("visits")
    private Client client;

    @Column
    private String details;

    @Column
    private String isPayed;

    private String externalId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", foreignKey = @ForeignKey(name = "DOCTOR_ID_FK"))
    @JsonIgnoreProperties("visits")
    private Doctor doctor;

    public String doctorDetails() {
        return String.format("%s %s", doctor.getUser().getFirstName(), doctor.getUser().getLastName());
    }
}

