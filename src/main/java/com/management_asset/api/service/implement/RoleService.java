package com.management_asset.api.service.implement;

import java.util.List;
import org.springframework.stereotype.Service;
import com.management_asset.api.model.Role;
import com.management_asset.api.repository.RoleRepository;
import com.management_asset.api.service.IRoleService;


@Service
public class RoleService implements IRoleService{
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    
    @Override
    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Integer id){
        return roleRepository.findById(id).orElse(null);
    }
    
    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

}
