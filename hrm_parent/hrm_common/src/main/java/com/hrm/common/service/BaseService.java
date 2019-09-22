package com.hrm.common.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @ClassName BaseService
 * @Description 公共Service
 * @Author Julie
 * @Date 2019/9/19 8:16
 * @Version 1.0
 */
public class BaseService<T> {


    public Specification<T> getSpec(String companyId) {

        Specification<T> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                return cb.equal(root.get("companyId").as(String.class), companyId);
            }

        };


        return specification;
    }
}
