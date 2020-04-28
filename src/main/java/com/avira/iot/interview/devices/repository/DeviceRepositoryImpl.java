package com.avira.iot.interview.devices.repository;

import com.avira.iot.interview.devices.model.Device;
import com.avira.iot.interview.devices.types.DeviceClassification;
import com.avira.iot.interview.devices.types.DeviceFilter;
import com.avira.iot.interview.devices.types.DeviceGroup;
import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.function.Predicate.not;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Repository
@RequiredArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepositoryCustom {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Long> totalUsers(DeviceFilter filter) {
        val operations = new ArrayList<AggregationOperation>();
        addFilters(filter, operations);
        operations.add(group("userId").count().as("totalUsers"));

        return toMono(operations);
    }

    @Override
    public Flux<DeviceClassification> classifications(DeviceFilter filter) {
        val operations = new ArrayList<AggregationOperation>();
        addFilters(filter, operations);
        operations.add(group("deviceSubCategoryId").count().as("numberOfDevices"));
        operations.add(project()
                .andExpression("_id").as("deviceSubcategoryId")
                .andExpression("numberOfDevices").as("numberOfDevices")
        );

        return toFlux(operations).log();
    }

    private Flux<DeviceClassification> toFlux(ArrayList<AggregationOperation> operations) {
        return Mono.just(operations.stream().toArray(AggregationOperation[]::new))
                .filter(aggregationOperations -> aggregationOperations.length != 0)
                .map(Aggregation::newAggregation)
                .flatMapMany(aggregation -> mongoTemplate.aggregate(aggregation, Device.class, DeviceClassification.class));
    }

    @Override
    public Flux<String> groupBy(String groupBy) {
        val group = group(groupBy);
        val projection = project()
                .andExpression("_id").as("group");
        return mongoTemplate.aggregate(newAggregation(group, projection), Device.class, Document.class)
                .map(doc -> (String) doc.get("group"));
    }

    private void addFilters(DeviceFilter filter, ArrayList<AggregationOperation> operations) {
        addFilterForString(filter, DeviceFilter::getUserId, operations, "userId");
        addFilterForString(filter, DeviceFilter::getVendor, operations, "vendor");
        addFilterForIntegers(filter, DeviceFilter::getDeviceCategoryId, operations, "deviceCategoryId");
        addFilterForIntegers(filter, DeviceFilter::getDeviceSubCategoryId, operations, "deviceSubCategoryId");
        addFilterForDatesGreaterThan(filter, DeviceFilter::getCreatedAfter, operations, "createdAt");
        addFilterForDatesLessThan(filter, DeviceFilter::getCreatedAfter, operations, "createdAt");
        addFilterForDatesGreaterThan(filter, DeviceFilter::getUpdatedAfter, operations, "updatedAt");
        addFilterForDatesLessThan(filter, DeviceFilter::getUpdatedBefore, operations, "updatedAt");
    }

    private Mono<Long> toMono(List<AggregationOperation> operations) {
        return Mono.just(operations.stream().toArray(AggregationOperation[]::new))
                .filter(aggregationOperations -> aggregationOperations.length != 0)
                .map(Aggregation::newAggregation)
                .flatMap(aggregation -> mongoTemplate.aggregate(aggregation, Device.class, Document.class).count());
    }

    @Override
    public Mono<Long> totalDevices(DeviceFilter filter) {
        val operations = new ArrayList<AggregationOperation>();
        addFilters(filter, operations);

        return toMono(operations);
    }

    private void addFilterForString(DeviceFilter filter,
                                    Function<DeviceFilter, String> function,
                                    List<AggregationOperation> operations,
                                    String key) {
        Optional.ofNullable(filter)
                .map(function)
                .filter(Objects::nonNull)
                .filter(not(String::isEmpty))
                .map(genericFilter -> new Criteria(key).is(genericFilter))
                .map(Aggregation::match)
                .ifPresent(operations::add);
    }

    private void addFilterForIntegers(DeviceFilter filter,
                                      Function<DeviceFilter, Integer> function,
                                      List<AggregationOperation> operations,
                                      String key) {
        Optional.ofNullable(filter)
                .map(function)
                .filter(Objects::nonNull)
                .map(genericFilter -> new Criteria(key).is(genericFilter))
                .map(Aggregation::match)
                .ifPresent(operations::add);
    }


    private void addFilterForDatesLessThan(DeviceFilter filter,
                                           Function<DeviceFilter, LocalDateTime> function,
                                           List<AggregationOperation> operations,
                                           String key) {
        Optional.ofNullable(filter)
                .map(function)
                .filter(Objects::nonNull)
                .map(genericFilter -> Criteria.where(key).lt(genericFilter))
                .map(Aggregation::match)
                .ifPresent(operations::add);
    }

    private void addFilterForDatesGreaterThan(DeviceFilter filter,
                                              Function<DeviceFilter, LocalDateTime> function,
                                              List<AggregationOperation> operations,
                                              String key) {
        Optional.ofNullable(filter)
                .map(function)
                .filter(Objects::nonNull)
                .map(genericFilter -> Criteria.where(key).gt(genericFilter))
                .map(Aggregation::match)
                .ifPresent(operations::add);
    }

}
