package com.ifeed.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class IdentifiableEntity implements Identifiable, Versioned, Comparable<IdentifiableEntity> {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof IdentifiableEntity)) {
            return false;
        }
        IdentifiableEntity i = (IdentifiableEntity) o;
        return (this.compareTo(i) == 0);
    }

    @Override
    public int compareTo(IdentifiableEntity o) {
        if (this.id == null && o.id == null) {
            return 0;
        } else if (this.id == null) {
            return -1;
        } else if (o.id == null) {
            return 1;
        } else {
            return (int) (this.id - o.id);
        }
    }
}

