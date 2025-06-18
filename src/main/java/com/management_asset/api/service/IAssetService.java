package com.management_asset.api.service;

import org.springframework.stereotype.Service;
import com.management_asset.api.model.Asset;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface IAssetService extends GenericService<Asset, Integer> {

}
