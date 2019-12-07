package com.mca.nick.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mca.nick.domain.ClientConfiguration} entity.
 */
public class ClientConfigurationDTO implements Serializable {

    private Long id;

    private Integer clientId;

    private String attachment;

    @NotNull
    private String preTableNotificationTemplate;

    @NotNull
    private String postTableTemplate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getPreTableNotificationTemplate() {
        return preTableNotificationTemplate;
    }

    public void setPreTableNotificationTemplate(String preTableNotificationTemplate) {
        this.preTableNotificationTemplate = preTableNotificationTemplate;
    }

    public String getPostTableTemplate() {
        return postTableTemplate;
    }

    public void setPostTableTemplate(String postTableTemplate) {
        this.postTableTemplate = postTableTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientConfigurationDTO clientConfigurationDTO = (ClientConfigurationDTO) o;
        if (clientConfigurationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientConfigurationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientConfigurationDTO{" +
            "id=" + getId() +
            ", clientId=" + getClientId() +
            ", attachment='" + getAttachment() + "'" +
            ", preTableNotificationTemplate='" + getPreTableNotificationTemplate() + "'" +
            ", postTableTemplate='" + getPostTableTemplate() + "'" +
            "}";
    }
}
