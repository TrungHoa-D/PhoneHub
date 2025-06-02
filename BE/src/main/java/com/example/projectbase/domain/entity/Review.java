package com.example.projectbase.domain.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    //Link to table User
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_REVIEW_USER"))
    private User user;

    //Link to table Phone
    @ManyToOne
    @JoinColumn(name = "phone_id", foreignKey = @ForeignKey(name = "FK_REVIEW_PHONE"))
    private Phone phone;

    @Nationalized
    @Column(nullable = false)
    private int rating;

    @Nationalized
    @Column(nullable = false)
    private String comment;

    @Nationalized
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
