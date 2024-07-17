package com.nchowf.tutorlinking.utils;

import org.springframework.data.jpa.domain.Specification;

public interface Filterable<T> {
    Specification<T> toSpecification();
}
