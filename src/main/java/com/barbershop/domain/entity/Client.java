package com.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
public class Client extends User {

}

