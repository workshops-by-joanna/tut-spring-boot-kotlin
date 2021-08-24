package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class BlogConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
							articleRepository: ArticleRepository) = ApplicationRunner {

        val smaldini = userRepository.save(User("smaldini", "Stéphane", "Maldini"))
        val author2 = userRepository.save(User("wojcik1", "Joanna", "Wojcik"))
        val author3 = userRepository.save(User("wojcik2", "Anna", "Wojcik"))

        articleRepository.save(Article(
				title = "Reactor Bismuth is out",
				headline = "Lorem ipsum",
				content = "dolor sit amet",
				author = smaldini
		))
		articleRepository.save(Article(
				title = "Reactor Aluminium has landed",
				headline = "Lorem ipsum",
				content = "dolor sit amet",
				author = smaldini
		))
		articleRepository.save(Article(
				title = "Brand new article1",
				headline = "Behold!",
				content = "Well, this is a test",
				author = author2
		))
		articleRepository.save(Article(
				title = "Cork wins the All Ireland Hurling championship!",
				headline = "Up the rebels!",
				content = "Yeah, they will, ya",
				author = author3
		))
    }
}
