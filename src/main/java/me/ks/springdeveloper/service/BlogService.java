package me.ks.springdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.ks.springdeveloper.domain.Article;
import me.ks.springdeveloper.dto.AddArticleRequest;
import me.ks.springdeveloper.dto.UpdateArticleRequest;
import me.ks.springdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class BlogService {
    private final BlogRepository blogRepository;

    // DTO를 받아와서 return으로 형변환
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

//        blogRepository.save(article); @Transactional이 있으면 따로 save할 필요 없음
        return article;

    }
}
