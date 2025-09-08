package com.sample.devicemanagement.service.mapper;

import com.sample.devicemanagement.domain.State;
import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto.DeviceDataEntry;
import com.sample.devicemanagement.dto.DeviceUpdateDto;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.sample.devicemanagement.domain.State.AVAILABLE;
import static com.sample.devicemanagement.domain.State.INACTIVE;
import static com.sample.devicemanagement.domain.State.IN_USE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DeviceEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deviceState", source= "deviceState", qualifiedByName = "toDeviceState")
    DeviceEntity toDeviceEntity(DeviceDto deviceDto);

    DeviceDto toDeviceDto(DeviceEntity deviceEntity);

    @Mapping(target = "pagination.page", source = "number")
    @Mapping(target = "pagination.size", source = "size")
    @Mapping(target = "pagination.totalPages", source = "totalPages")
    @Mapping(target = "pagination.totalResults", source = "totalElements")
    @Mapping(target = "data", source = "pageEntity", qualifiedByName = "pageToDeviceDataEntry")
    DeviceTableViewDto toDeviceTableView(Page<DeviceEntity> pageEntity);

    DeviceDataEntry toStoreDataEntry(DeviceEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deviceId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deviceState", source= "deviceState", qualifiedByName = "toDeviceState")
    DeviceEntity mergeDeviceEntity(DeviceUpdateDto deviceUpdateDto, @MappingTarget DeviceEntity deviceEntity);


    @Named("pageToDeviceDataEntry")
    default List<DeviceDataEntry> pageToDeviceDataEntry(Page<DeviceEntity> pageEntity) {
        return pageEntity.map(this::toStoreDataEntry)
                .stream().toList();
    }

    @Named("toDeviceState")
    default State toDeviceState(String state) {
        return switch (state.toLowerCase()) {
            case "available" -> AVAILABLE;
            case "in-use" -> IN_USE;
            case "inactive" -> INACTIVE;
            default -> throw new IllegalArgumentException("Invalid state type provided: " + state);
        };
    }
}
