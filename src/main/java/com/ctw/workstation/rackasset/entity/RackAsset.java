package com.ctw.workstation.rackasset.entity;

import com.ctw.workstation.rack.entity.Rack;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;

@Entity
@Table(name = "T_RACK_ASSET")
public class RackAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rackAssetIdGenerator")
    @SequenceGenerator(name = "rackAssetIdGenerator", sequenceName = "SEQ_RACK_ASSET_ID")
    private Long id;

    @Column(name = "asset_tag", nullable = false, length = 10)
    private String assetTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rack_id")
    private Rack rack;

    public RackAsset(){}

    public RackAsset(String assetTag, Rack rack) {
        this.assetTag = assetTag;
        this.rack = rack;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public Long getId() {
        return id;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public Rack getRack() {
        return rack;
    }


}
