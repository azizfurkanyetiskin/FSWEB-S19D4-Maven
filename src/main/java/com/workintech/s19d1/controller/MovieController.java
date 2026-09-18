package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.MovieRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping({"/movie", "/movies"})
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public Movie save(@RequestBody MovieRequest movieRequest) {

        Movie movie = movieRequest.getMovie();

        if (movie.getActors() == null) {
            movie.setActors(new ArrayList<>());
        }

        if (movieRequest.getActors() != null) {
            for (Actor actor : movieRequest.getActors()) {
                movie.addActor(actor);
            }
        }

        return movieService.save(movie);
    }

    @PutMapping("/{id}")
    public Movie update(
            @PathVariable Long id,
            @RequestBody Movie movie
    ) {

        movieService.findById(id);

        movie.setId(id);

        return movieService.save(movie);
    }

    @DeleteMapping("/{id}")
    public Movie delete(@PathVariable Long id) {

        Movie movie = movieService.findById(id);

        movieService.delete(movie);

        return movie;
    }
}