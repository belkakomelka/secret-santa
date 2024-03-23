package com.secret.santa.database.dao;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "group")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "sequence_group")
    @SequenceGenerator(name = "sequence_group", sequenceName = "sequence_group", allocationSize = 1)
    Long id;

    @OneToMany(mappedBy = "group")
    List<ParticipantEntity> participant;

    @Column(name = "name", nullable=false)
    String name;

    @Column(name = "description")
    String description;
}
