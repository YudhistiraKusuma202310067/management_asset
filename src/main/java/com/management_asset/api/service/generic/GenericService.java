package com.management_asset.api.service.generic;

import java.util.List;

public interface GenericService <T,K>{
    public List<T> findAll();
    public T findById(K id);
}