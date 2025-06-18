package com.management_asset.api.service;

import org.springframework.stereotype.Service;
import com.management_asset.api.model.Role;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface IRoleService extends GenericService<Role, Integer> {

}
