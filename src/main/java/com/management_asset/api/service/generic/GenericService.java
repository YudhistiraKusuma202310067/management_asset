package com.management_asset.api.service.generic;

import java.util.List;

public interface GenericService <T, K> { //T = model, K = tipe data
    public List<T> findAll();
    public T findById(K id);
    public T save(T model);  
}
