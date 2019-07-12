package com.mca.mca7.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Calibration.
 */
@Entity
@Table(name = "calibration")
public class Calibration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "region_file")
    private String regionFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Calibration name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionFile() {
        return regionFile;
    }

    public Calibration regionFile(String regionFile) {
        this.regionFile = regionFile;
        return this;
    }

    public void setRegionFile(String regionFile) {
        this.regionFile = regionFile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Calibration)) {
            return false;
        }
        return id != null && id.equals(((Calibration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Calibration{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", regionFile='" + getRegionFile() + "'" +
            "}";
    }
}
