package com.sample.devicemanagement.service.mapper;

import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto;
import com.sample.devicemanagement.dto.DeviceUpdateDto;
import com.sample.devicemanagement.dto.Pagination;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import javax.annotation.processing.Generated;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-10T22:50:59+0000",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class DeviceEntityMapperImpl implements DeviceEntityMapper {

    @Override
    public DeviceEntity toDeviceEntity(DeviceDto deviceDto) {
        if ( deviceDto == null ) {
            return null;
        }

        DeviceEntity.DeviceEntityBuilder deviceEntity = DeviceEntity.builder();

        deviceEntity.deviceState( toDeviceState( deviceDto.getDeviceState() ) );
        deviceEntity.deviceId( deviceDto.getDeviceId() );
        deviceEntity.deviceName( deviceDto.getDeviceName() );
        deviceEntity.deviceBrand( deviceDto.getDeviceBrand() );
        deviceEntity.createdAt( deviceDto.getCreatedAt() );

        return deviceEntity.build();
    }

    @Override
    public DeviceDto toDeviceDto(DeviceEntity deviceEntity) {
        if ( deviceEntity == null ) {
            return null;
        }

        DeviceDto.DeviceDtoBuilder deviceDto = DeviceDto.builder();

        deviceDto.deviceId( deviceEntity.getDeviceId() );
        deviceDto.deviceName( deviceEntity.getDeviceName() );
        deviceDto.deviceBrand( deviceEntity.getDeviceBrand() );
        if ( deviceEntity.getDeviceState() != null ) {
            deviceDto.deviceState( deviceEntity.getDeviceState().name() );
        }
        deviceDto.createdAt( deviceEntity.getCreatedAt() );

        return deviceDto.build();
    }

    @Override
    public DeviceTableViewDto toDeviceTableView(Page<DeviceEntity> pageEntity) {
        if ( pageEntity == null ) {
            return null;
        }

        DeviceTableViewDto.DeviceTableViewDtoBuilder deviceTableViewDto = DeviceTableViewDto.builder();

        deviceTableViewDto.pagination( deviceEntityPageToPagination( pageEntity ) );
        deviceTableViewDto.data( pageToDeviceDataEntry( pageEntity ) );

        return deviceTableViewDto.build();
    }

    @Override
    public DeviceTableViewDto.DeviceDataEntry toStoreDataEntry(DeviceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DeviceTableViewDto.DeviceDataEntry.DeviceDataEntryBuilder deviceDataEntry = DeviceTableViewDto.DeviceDataEntry.builder();

        deviceDataEntry.deviceId( entity.getDeviceId() );
        deviceDataEntry.deviceName( entity.getDeviceName() );
        deviceDataEntry.deviceBrand( entity.getDeviceBrand() );
        if ( entity.getDeviceState() != null ) {
            deviceDataEntry.deviceState( entity.getDeviceState().name() );
        }
        deviceDataEntry.createdAt( entity.getCreatedAt() );

        return deviceDataEntry.build();
    }

    @Override
    public DeviceEntity mergeDeviceEntity(DeviceUpdateDto deviceUpdateDto, DeviceEntity deviceEntity) {
        if ( deviceUpdateDto == null ) {
            return deviceEntity;
        }

        if ( deviceUpdateDto.getDeviceState() != null ) {
            deviceEntity.setDeviceState( toDeviceState( deviceUpdateDto.getDeviceState() ) );
        }
        if ( deviceUpdateDto.getDeviceName() != null ) {
            deviceEntity.setDeviceName( deviceUpdateDto.getDeviceName() );
        }
        if ( deviceUpdateDto.getDeviceBrand() != null ) {
            deviceEntity.setDeviceBrand( deviceUpdateDto.getDeviceBrand() );
        }

        return deviceEntity;
    }

    protected Pagination deviceEntityPageToPagination(Page<DeviceEntity> page) {
        if ( page == null ) {
            return null;
        }

        Pagination.PaginationBuilder pagination = Pagination.builder();

        pagination.page( page.getNumber() );
        pagination.size( page.getSize() );
        pagination.totalPages( page.getTotalPages() );
        pagination.totalResults( (int) page.getTotalElements() );

        return pagination.build();
    }
}
