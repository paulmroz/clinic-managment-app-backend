package pl.psk.clinicmanagement.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.domain.user.Doctor;


import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{
    final public static String CLIENT_TYPE = "client";
    final public static String DOCTOR_TYPE = "doctor";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    protected String email;

    @Column
    @JsonIgnore
    protected String password;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column
    protected String city;

    @Column
    protected String facebookID;

    @Column
    protected String street;

    @Column(name = "house_number")
    protected String houseNumber;

    @Column(name = "phone_number")
    protected String phoneNumber;

    @Column
    protected String roles = "";

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("user")
    protected Client client;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("user")
    protected Doctor doctor;

}

