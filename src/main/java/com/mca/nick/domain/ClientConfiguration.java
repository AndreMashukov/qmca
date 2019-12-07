package com.mca.nick.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ClientConfiguration.
 */
@Entity
@Table(name = "client_configuration")
public class ClientConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "attachment")
    private String attachment;

    @NotNull
    @Column(name = "pre_table_notification_template", nullable = false)
    private String preTableNotificationTemplate;

    @NotNull
    @Column(name = "post_table_template", nullable = false)
    private String postTableTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public ClientConfiguration clientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getAttachment() {
        return attachment;
    }

    public ClientConfiguration attachment(String attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getPreTableNotificationTemplate() {
        return preTableNotificationTemplate;
    }

    public ClientConfiguration preTableNotificationTemplate(String preTableNotificationTemplate) {
        this.preTableNotificationTemplate = preTableNotificationTemplate;
        return this;
    }

    public void setPreTableNotificationTemplate(String preTableNotificationTemplate) {
        this.preTableNotificationTemplate = preTableNotificationTemplate;
    }

    public String getPostTableTemplate() {
        return postTableTemplate;
    }

    public ClientConfiguration postTableTemplate(String postTableTemplate) {
        this.postTableTemplate = postTableTemplate;
        return this;
    }

    public void setPostTableTemplate(String postTableTemplate) {
        this.postTableTemplate = postTableTemplate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientConfiguration)) {
            return false;
        }
        return id != null && id.equals(((ClientConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClientConfiguration{" +
            "id=" + getId() +
            ", clientId=" + getClientId() +
            ", attachment='" + getAttachment() + "'" +
            ", preTableNotificationTemplate='" + getPreTableNotificationTemplate() + "'" +
            ", postTableTemplate='" + getPostTableTemplate() + "'" +
            "}";
    }
}
