package com.workintech.s19d1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie", schema = "fsweb")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String directorName;

    private Integer rating;

    private LocalDate releaseDate;

    @ManyToMany(
            mappedBy = "movies",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JsonIgnoreProperties("movies")
    private List<Actor> actors = new ArrayList<>();

    public void addActor(Actor actor) {

        if (actors == null) {
            actors = new ArrayList<>();
        }

        if (actor == null) {
            return;
        }

        if (!actors.contains(actor)) {
            actors.add(actor);
        }

        if (actor.getMovies() == null) {
            actor.setMovies(new ArrayList<>());
        }

        if (!actor.getMovies().contains(this)) {
            actor.getMovies().add(this);
        }
    }
}