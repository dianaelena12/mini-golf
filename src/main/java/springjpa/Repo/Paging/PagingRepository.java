package springjpa.Repo.Paging;

import springjpa.Domain.BaseEntity;
import springjpa.Repo.RepositoryInterface;

import java.io.Serializable;

/**
 * author: radu
 */
public interface PagingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends RepositoryInterface<ID, T> {

    Page<T> findAll(Pageable pageable);

}
