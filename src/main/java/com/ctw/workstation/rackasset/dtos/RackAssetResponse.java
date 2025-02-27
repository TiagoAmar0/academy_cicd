package com.ctw.workstation.rackasset.dtos;

import com.ctw.workstation.rackasset.entity.RackAsset;

public class RackAssetResponse {
    private Long id;
    private String assetTag;
    private String rack;

    public RackAssetResponse(RackAsset rackAsset) {
        this.id = rackAsset.getId();
        this.assetTag = rackAsset.getAssetTag();
        this.rack = rackAsset.getRack().getSerialNumber();
    }
}
