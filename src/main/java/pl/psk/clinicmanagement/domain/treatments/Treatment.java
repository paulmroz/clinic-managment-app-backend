package pl.psk.clinicmanagement.domain.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "treatments")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Treatment {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "cost")
    private String cost;

}