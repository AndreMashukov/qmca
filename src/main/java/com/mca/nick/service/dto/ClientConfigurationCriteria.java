package com.mca.nick.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mca.nick.domain.ClientConfiguration} entity. This class is used
 * in {@link com.mca.nick.web.rest.ClientConfigurationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /client-configurations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientConfigurationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter clientId;

    private StringFilter attachment;

    private StringFilter preTableNotificationTemplate;

    private StringFilter postTableTemplate;

    public ClientConfigurationCriteria(){
    }

    public ClientConfigurationCriteria(ClientConfigurationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.attachment = other.attachment == null ? null : other.attachment.copy();
        this.preTableNotificationTemplate = other.preTableNotificationTemplate == null ? null : other.preTableNotificationTemplate.copy();
        this.postTableTemplate = other.postTableTemplate == null ? null : other.postTableTemplate.copy();
    }

    @Override
    public ClientConfigurationCriteria copy() {
        return new ClientConfigurationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getClientId() {
        return clientId;
    }

    public void setClientId(IntegerFilter clientId) {
        this.clientId = clientId;
    }

    public StringFilter getAttachment() {
        return attachment;
    }

    public void setAttachment(StringFilter attachment) {
        this.attachment = attachment;
    }

    public StringFilter getPreTableNotificationTemplate() {
        return preTableNotificationTemplate;
    }

    public void setPreTableNotificationTemplate(StringFilter preTableNotificationTemplate) {
        this.preTableNotificationTemplate = preTableNotificationTemplate;
    }

    public StringFilter getPostTableTemplate() {
        return postTableTemplate;
    }

    public void setPostTableTemplate(StringFilter postTableTemplate) {
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
        final ClientConfigurationCriteria that = (ClientConfigurationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(attachment, that.attachment) &&
            Objects.equals(preTableNotificationTemplate, that.preTableNotificationTemplate) &&
            Objects.equals(postTableTemplate, that.postTableTemplate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        clientId,
        attachment,
        preTableNotificationTemplate,
        postTableTemplate
        );
    }

    @Override
    public String toString() {
        return "ClientConfigurationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (attachment != null ? "attachment=" + attachment + ", " : "") +
                (preTableNotificationTemplate != null ? "preTableNotificationTemplate=" + preTableNotificationTemplate + ", " : "") +
                (postTableTemplate != null ? "postTableTemplate=" + postTableTemplate + ", " : "") +
            "}";
    }

}
