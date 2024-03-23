package com.secret.santa.database.dao;

import com.secret.santa.dto.Group;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "participant")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "sequence_participant")
    @SequenceGenerator(name = "sequence_participant", sequenceName = "sequence_participant", allocationSize = 1)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    GroupEntity group;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "wish")
    String wish;

    @OneToOne
    @JoinColumn(name = "recipient")
    ParticipantEntity recipient;

}
