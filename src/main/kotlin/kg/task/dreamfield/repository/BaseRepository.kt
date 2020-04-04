package kg.task.dreamfield.repository

import com.querydsl.core.types.Predicate
import kg.task.dreamfield.domain.paging.PageRequestBase
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T> : JpaRepository<T, Long>, QuerydslPredicateExecutor<T>

fun <T> BaseRepository<T>.findAll(predicate: Predicate, pageRequest: PageRequestBase<*>): Page<T> {
    val sort: Sort = pageRequest.sortInfo.sortBy.getSorting(pageRequest.sortInfo.sortDirection)
    val page: PageRequest? = pageRequest.pageInfo?.let { PageRequest.of(it.page - 1, it.limit, sort) }

    return page?.let { findAll(predicate, it) } ?: PageImpl(findAll(predicate, sort).toList())
}

fun <T> BaseRepository<T>.findAll(pageRequest: PageRequestBase<*>): Page<T> {
    val sort: Sort = pageRequest.sortInfo.sortBy.getSorting(pageRequest.sortInfo.sortDirection)
    val page: PageRequest? = pageRequest.pageInfo?.let { PageRequest.of(it.page - 1, it.limit, sort) }

    return page?.let { findAll(it) } ?: PageImpl(findAll(sort))
}

