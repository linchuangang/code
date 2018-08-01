package com.jeesea.codegen.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 包含不同模型类的类。
 *
 * @author Rocky
 */
public class ModelWrapper {

    private EntityModel entity;
    private DaoModel dao;
    private ServiceModel service;
    private ServiceImplModel serviceImpl;

    public ServiceImplModel getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(ServiceImplModel serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public EntityModel getEntity() {
        return entity;
    }

    public void setEntity(EntityModel entity) {
        this.entity = entity;
    }

    public DaoModel getDao() {
        return dao;
    }

    public void setDao(DaoModel dao) {
        this.dao = dao;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public ServiceModel getService() {
        return service;
    }

    public void setService(ServiceModel service) {
        this.service = service;
    }

}
