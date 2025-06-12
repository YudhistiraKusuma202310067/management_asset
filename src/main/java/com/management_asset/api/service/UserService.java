package com.management_asset.api.service;

import org.springframework.stereotype.Service;
import com.management_asset.api.model.User;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface UserService extends GenericService<User, Integer> {

}
