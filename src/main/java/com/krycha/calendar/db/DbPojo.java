package com.krycha.calendar.db;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract public class DbPojo implements Serializable {

    private static final long serialVersionUID = -7289994339186082141L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public DbPojo() {

    }

    /**
     * Get the primary key for this entity.
     * 
     * @return Primary key
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the primary key for this entity. Usually, this method should never be
     * called.
     * 
     * @param id
     *            New primary key
     */
    public void setId(Long id) {
        this.id = id;
    }
}

