package me.ks.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.ks.springdeveloper.domain.Article;
import me.ks.springdeveloper.dto.AddArticleRequest;
import me.ks.springdeveloper.dto.ArticleResponse;
import me.ks.springdeveloper.dto.UpdateArticleRequest;
import me.ks.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());

        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(article -> new ArticleResponse(article))
                .toList();

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable("id") long id) {
        Article article = blogService.findById(id);
        return new ResponseEntity<>(new ArticleResponse(article), HttpStatus.OK);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id) {
        log.info(id);
        blogService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }
}
