package com.secret.santa.database.dao;

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
public class Participant {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "sequence_participant")
    @SequenceGenerator(name = "sequence_participant", sequenceName = "sequence_participant", allocationSize = 1)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    Group group;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "wish")
    String wish;

    @OneToOne
    @JoinColumn(name = "recipient_id")
    Participant recipient;

}
