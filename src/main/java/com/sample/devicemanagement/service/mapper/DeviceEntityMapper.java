package com.sample.devicemanagement.service.mapper;

import com.sample.devicemanagement.domain.State;
import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static com.sample.devicemanagement.domain.State.AVAILABLE;
import static com.sample.devicemanagement.domain.State.INACTIVE;
import static com.sample.devicemanagement.domain.State.IN_USE;

@Mapper(componentModel = "spring")
public interface DeviceEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deviceState", source= "deviceState", qualifiedByName = "toDeviceState")
    DeviceEntity toDeviceEntity(DeviceDto deviceDto);
    DeviceDto toDeviceDto(DeviceEntity deviceEntity);

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
