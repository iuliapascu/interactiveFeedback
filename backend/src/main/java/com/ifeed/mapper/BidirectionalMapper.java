package com.ifeed.mapper;

import com.ifeed.model.IdentifiableEntity;
import org.springframework.stereotype.Service;

@Service
public interface BidirectionalMapper<ENTITY extends IdentifiableEntity, DTO> {
    public DTO map(ENTITY entity);

    public void map(DTO dto, ENTITY entity);
}
