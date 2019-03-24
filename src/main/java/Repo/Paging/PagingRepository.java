package Repo.Paging;

import Domain.BaseEntity;
import Repo.RepositoryInterface;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author: radu
 */
public interface PagingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends RepositoryInterface<ID, T> {

    Page<T> findAll(Pageable pageable);

}
