package com.example.blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.Example
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
		val entityManager: TestEntityManager,
		val userRepository: UserRepository,
		val articleRepository: ArticleRepository) {

	@Test
	fun `When findByIdOrNull then return Article`() {
		val juergen = User("springjuergen", "Juergen", "Hoeller")
		entityManager.persist(juergen)
		val article = Article("Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
		entityManager.persist(article)
		entityManager.flush()
		val found = articleRepository.findByIdOrNull(article.id!!)
		assertThat(found).isEqualTo(article)
	}

	@Test
	fun `When queryArticleByHeadline then return matching articles`() {
		setup_test_data()

		val articles = articleRepository.queryArticleByHeadline("rebels")
		assertThat(articles).hasSize(2)
	}

	@Test
	fun `When findByLogin then return User`() {
		val juergen = User("springjuergen", "Juergen", "Hoeller")
		entityManager.persist(juergen)
		entityManager.flush()
		val user = userRepository.findByLogin(juergen.login)
		assertThat(user).isEqualTo(juergen)
	}

	@Test
	fun `When findByFirstnameContainingIgnoreCase then return matching users`() {
		setup_test_data()

		val users = userRepository.findByFirstnameContainingIgnoreCase("anna")
		assertThat(users).hasSize(2)
	}

	@Test
	fun `When queryByAuthorDetails then return matching authors`() {
		setup_test_data()

		val users = userRepository.queryByAuthorDetails(lastname = "Wojcik", firstname = "Joanna")
		assertThat(users).hasSize(1)
	}

	@Test
	fun `When getAllAuthorsBySpringExpression then return authors`() {
		setup_test_data()

		val users = userRepository.getAllAuthorsBySpringExpression()
		assertThat(users).hasSize(3)
	}

	@Test
	fun `when get by example with return matching authors`() {
		setup_test_data()

		val user = User(login="wojcik1", firstname = "Joanna", lastname="Wojcik")

		val example = Example.of(user)
		val result = userRepository.findOne(example)
		assertThat(result.get().description).isEqualTo("Everything is awesome")
	}



	private fun setup_test_data() {
		val author1 = User("smaldini", "Stephane", "Maldini")
		val author2 = User("wojcik1", "Joanna", "Wojcik", description = "Everything is awesome")
		val author3 = User("wojcik2", "Anna", "Wojcik")

		val article1 = Article(
				title = "Reactor Bismuth is out",
				headline = "Lorem ipsum",
				content = "dolor sit amet",
				author = author1
		)
		val article2 = Article(
				title = "Reactor Aluminium has landed",
				headline = "Lorem ipsum",
				content = "dolor sit amet",
				author = author1
		)
		val article3 = Article(
				title = "Brand new article1",
				headline = "Behold!",
				content = "Well, this is a test",
				author = author2
		)
		val article4 = Article(
				title = "Cork wins the All Ireland Hurling championship!",
				headline = "Up the rebels!",
				content = "Yeah, they will, ya",
				author = author3
		)
		val article5 = Article(
				title = "Cork beaten!",
				headline = "rebels coming home",
				content = "Maybe next year, ya",
				author = author3
		)

		entityManager.persist(author1)
		entityManager.persist(author2)
		entityManager.persist(author3)
		entityManager.persist(article1)
		entityManager.persist(article2)
		entityManager.persist(article3)
		entityManager.persist(article4)
		entityManager.persist(article5)
		entityManager.flush()
	}
}
