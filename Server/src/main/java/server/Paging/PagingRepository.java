package server.Paging;

import common.Domain.BaseEntity;
import server.Repository.RepositoryInterface;

import java.io.Serializable;

/**
 * author: radu
 */
public interface PagingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends RepositoryInterface<T> {

    Page<T> findAll(Pageable pageable);

}
