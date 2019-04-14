package springjpa.Repo.Paging.Impl;

import springjpa.Repo.Paging.Page;
import springjpa.Repo.Paging.Pageable;

import java.util.stream.Stream;

public class PageImpl<T> implements Page<T> {
    private Pageable pageable;
    private Stream<T> content;

    public PageImpl(Pageable pageable, Stream<T> content) {
        this.pageable = pageable;
        this.content = content;
    }

    @Override
    public Pageable getPageable() {
        return pageable;
    }

    @Override
    public Pageable nextPageable() {
        return PageRequest.of(pageable.getPageSize(), pageable.getPageNumber() + 1);
    }

    @Override
    public Stream<T> getContent() {
        return content;
    }
}
