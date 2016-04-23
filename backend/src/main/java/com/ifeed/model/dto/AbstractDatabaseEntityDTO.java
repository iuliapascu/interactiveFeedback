package com.ifeed.model.dto;

import com.ifeed.model.base.Identifiable;
import com.ifeed.model.base.Versioned;

public abstract class AbstractDatabaseEntityDTO implements Identifiable, Versioned {
    private Long id;
    private Integer version;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
