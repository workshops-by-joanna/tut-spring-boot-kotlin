package com.example.blog

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.query.QueryByExampleExecutor

interface ArticleRepository : CrudRepository<Article, Long> {
	fun findBySlug(slug: String): Article?
	fun findAllByOrderByAddedAtDesc(): Iterable<Article>

	@Query(value="select a from Article a where headline like %?1%")
	fun queryArticleByHeadline(headlinePart: String): List<Article>
}

interface UserRepository : CrudRepository<User, Long>, QueryByExampleExecutor<User> {
	fun findByLogin(login: String): User?

	fun findByFirstnameContainingIgnoreCase(name: String): List<User>

	@Query(value="select u from User u where u.firstname=:firstname and u.lastname=:lastname")
	fun queryByAuthorDetails(@Param("lastname") lastname: String, @Param("firstname") firstname: String) : List<User>

	@Query(value="select u from #{#entityName} u")
	fun getAllAuthorsBySpringExpression():List<User>
}

