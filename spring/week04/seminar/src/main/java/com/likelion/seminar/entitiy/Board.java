package com.likelion.seminar.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "board"
    )
    private List<Post> posts = new ArrayList<>();

    private Board(String name) {
        this.name = name;
    }

}
