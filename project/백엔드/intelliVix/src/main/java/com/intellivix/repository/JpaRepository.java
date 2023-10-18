package com.intellivix.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface JpaRepository<T, ID> extends PagingAndSortingRepository {
}
