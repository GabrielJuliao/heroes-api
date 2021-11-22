package com.gabrieljuliao.heroesapi.controller;

import com.gabrieljuliao.heroesapi.model.Heroes;
import com.gabrieljuliao.heroesapi.repository.HeroesRepository;
import com.gabrieljuliao.heroesapi.service.HeroesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.gabrieljuliao.heroesapi.constants.HeroesConstant.HEROES_ENDPOINT_LOCAL;

@RestController
public class HeroesController {
  HeroesService heroesService;
  HeroesRepository heroesRepository;

  public HeroesController(HeroesService heroesService, HeroesRepository heroesRepository) {
    this.heroesService = heroesService;
    this.heroesRepository = heroesRepository;
  }

  @GetMapping(HEROES_ENDPOINT_LOCAL)
  @ResponseStatus(HttpStatus.OK)
  public Flux<Heroes> getAllItems() {
    return heroesService.findAll();

  }


  @GetMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
  public Mono<ResponseEntity<Heroes>> findByIdHero(@PathVariable String id) {
    return heroesService.findByIdHero(id)
      .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping(HEROES_ENDPOINT_LOCAL)
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
    return heroesService.save(heroes);

  }

  @DeleteMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public Mono<HttpStatus> deletebyIDHero(@PathVariable String id) {
    heroesService.deletebyIDHero(id);
    return Mono.just(HttpStatus.NO_CONTENT);
  }
}
