package com.secret.santa.database.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "groups")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "sequence_group")
    @SequenceGenerator(name = "sequence_group", sequenceName = "sequence_group", allocationSize = 1)
    Long id;

    @OneToMany(mappedBy = "group")
    List<Participant> participant;

    @Column(name = "name", nullable=false)
    String name;

    @Column(name = "description")
    String description;
}
