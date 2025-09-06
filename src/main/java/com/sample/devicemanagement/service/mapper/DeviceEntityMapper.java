package com.sample.devicemanagement.service.mapper;

import com.sample.devicemanagement.dto.DeviceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceEntityMapper {

    // Rules of mapping should be applied here
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    DeviceEntity toDeviceEntity(DeviceDto deviceDto);
    DeviceDto toDeviceDto(DeviceEntity deviceEntity);

}
