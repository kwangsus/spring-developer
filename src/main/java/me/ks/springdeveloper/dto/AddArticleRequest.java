package me.ks.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.ks.springdeveloper.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity(String author) {
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
