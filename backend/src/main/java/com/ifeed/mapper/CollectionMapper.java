package com.ifeed.mapper;

import com.ifeed.model.base.IdentifiableEntity;
import com.ifeed.model.dto.AbstractDatabaseEntityDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public abstract class CollectionMapper<ENTITY extends IdentifiableEntity, DTO extends AbstractDatabaseEntityDTO> implements BidirectionalMapper<ENTITY, DTO> {

    public abstract DTO map(ENTITY entity);

    public List<DTO> map(Collection<ENTITY> entities) {
        List<DTO> dtos;
        if (entities != null) {
            dtos = new ArrayList<>(entities.size());
            for (ENTITY e : entities) {
                dtos.add(map(e));
            }
        } else {
            dtos = new ArrayList<>();
        }
        return dtos;
    }

    public abstract void map(DTO dto, ENTITY entity);

    public ENTITY createEntity() {
        throw new UnsupportedOperationException(new NoSuchMethodException("Method must be implemented for Subclass, but isn't!"));
    }


    private boolean existsDtoWithId(Long id, Collection<DTO> dtos) {
        return dtos.stream().filter(
                dto -> Objects.equals(id, dto.getId())
        ).findAny().isPresent();
    }

    /**
     * Merges dto list to entities list by removing non-existing entities and adding newly created dto to entities list.
     * For this Method <b>createEntity() must be implemented</b>.
     *
     * @param dtos     dto list to be saved
     * @param entities entities list to be merged into
     */
    public void map(Collection<DTO> dtos, Collection<ENTITY> entities) {
        // Remove deleted Attributes from Entity

        List<ENTITY> removeEntities = entities.stream().filter(
                entity ->
                        entity != null && !existsDtoWithId(entity.getId(), dtos)

        ).collect(Collectors.toList());

        removeEntities.forEach(entity -> {
            removeParentOfEntity(entity);
            entities.remove(entity);
        });

        for (DTO dto : dtos) {
            // Add new Entities
            if (dto != null) {
                Long id = dto.getId();
                if (dto.getId() == null) {
                    ENTITY entity = createEntity();
                    map(dto, entity);
                    entities.add(entity);
                } else {
                    // map existing entities
                    mapEnities(dto, entities);
                }
            }
        }
    }

    private void mapEnities(DTO dto, Collection<ENTITY> entities) {
        for (ENTITY e : entities) {
            if (e.getId().equals(dto.getId())) {
                map(dto, e);
                break;
            }
        }
    }

    public void removeParentOfEntity(ENTITY entity) {
    }

}
