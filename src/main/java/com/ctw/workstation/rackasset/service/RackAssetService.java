package com.ctw.workstation.rackasset.service;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.repository.RackRepository;
import com.ctw.workstation.rackasset.dtos.RackAssetResponse;
import com.ctw.workstation.rackasset.dtos.RackAssetRequest;
import com.ctw.workstation.rackasset.entity.RackAsset;
import com.ctw.workstation.rackasset.repository.RackAssetRepository;
import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.teammember.entity.TeamMember;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;

@Singleton
public class RackAssetService {

    @Inject
    RackAssetRepository rackAssetRepository;

    @Inject
    RackRepository rackRepository;

    public List<RackAssetResponse> index(Long rackId) {
        return rackAssetRepository.findAll()
                .stream()
                .map(this::buildResponseDTO)
                .toList();
    }

    @Transactional
    public RackAssetResponse store(Long rackId, RackAssetRequest request) {
        Rack rack = getRack(rackId);

        RackAsset rackAsset = new RackAsset(request.assetTag(), rack);
        rackAssetRepository.persistAndFlush(rackAsset);

        return buildResponseDTO(rackAsset);
    }

    private RackAsset getRackAssetFromRack(Rack rack, Long id) {
        return rack.getRackAssets()
                .stream()
                .filter(ra -> ra.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Rack asset not found"));
    }

    private Rack getRack(Long id) {
        return rackRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Rack not found"));
    }

    public RackAssetResponse show(Long rackId, Long id) {
        Rack rack = getRack(rackId);
        RackAsset rackAsset = getRackAssetFromRack(rack, id);
        return buildResponseDTO(rackAsset);
    }

    public RackAssetResponse update(Long rackId, Long id, RackAssetRequest request) {
        Rack rack = getRack(rackId);
        RackAsset rackAsset = getRackAssetFromRack(rack, id);
        rackAsset.setAssetTag(request.assetTag());
        rackAssetRepository.persistAndFlush(rackAsset);
        return buildResponseDTO(rackAsset);
    }


    public boolean delete(Long rackId, Long id) {
        Rack rack = getRack(rackId);
        RackAsset rackAsset = getRackAssetFromRack(rack, id);
        return rackAssetRepository.deleteById(rackAsset.getId());
    }

    private RackAssetResponse buildResponseDTO(RackAsset rackAsset){
        return new RackAssetResponse(rackAsset);
    }
}
