package com.avira.iot.interview.devices.repository;

import com.avira.iot.interview.devices.model.Device;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;

public interface DeviceRepository extends ReactiveMongoRepository<Device, String>,
        ReactiveQuerydslPredicateExecutor<Device>, DeviceRepositoryCustom {

}