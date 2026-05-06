package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.store.Store;
import com.cafe.storeservice.dto.store.StoreSearchDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class StoreSpecification {

    private StoreSpecification(){}

    public static Specification<Store> search(StoreSearchDto dto) {
        return Specification.allOf(
                nameContains(dto.getName()),
                addressContains(dto.getAddress()),
                phoneContains(dto.getPhone()),
                emailContains(dto.getEmail()),
                isActiveEquals(dto.getActive())
        );
    }

    private static Specification<Store> emailContains(String email) {
        return (root, query, cb) ->
                StringUtils.hasText(email)
                ? cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%")
                        :null;
    }

    public static Specification<Store> nameContains(String name) {
        return (root, query, cb) ->
                StringUtils.hasText(name)
                ? cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%")
                        : null;
    }

    private static Specification<Store> addressContains(String address) {
        return (root, query, cb) ->
                StringUtils.hasText(address)
                        ? cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%")
                        : null;
    }

    private static Specification<Store> phoneContains(String phone) {
        return (root, query, cb) ->
                StringUtils.hasText(phone)
                        ? cb.like(cb.lower(root.get("phone")), "%" + phone.toLowerCase() + "%")
                        : null;
    }

    private static Specification<Store> isActiveEquals(Boolean isActive) {
        return (root, query, cb) ->
                isActive != null
                        ? cb.equal(root.get("active"), isActive)
                        : null;
    }
}
