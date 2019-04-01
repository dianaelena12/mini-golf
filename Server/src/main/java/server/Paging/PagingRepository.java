package server.Paging;

import common.Domain.BaseEntity;
import server.RepositoryInterface;

import java.io.Serializable;

/**
 * author: radu
 */
public interface PagingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends RepositoryInterface<ID, T> {

    Page<T> findAll(Pageable pageable);

}
