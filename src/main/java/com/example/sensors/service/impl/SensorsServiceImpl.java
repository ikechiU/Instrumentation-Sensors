package com.example.sensors.service.impl;

import com.example.sensors.exceptions.SensorsServiceException;
import com.example.sensors.io.entity.SensorsEntity;
import com.example.sensors.io.repository.SensorsRepository;
import com.example.sensors.service.SensorsService;
import com.example.sensors.shared.Utils;
import com.example.sensors.shared.dto.SensorsDto;
import com.example.sensors.shared.dto.SensorsDtoExtra;
import com.example.sensors.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class SensorsServiceImpl implements SensorsService {

    @Autowired
    SensorsRepository sensorsRepository;

    @Autowired
    Utils utils;

    @Override
    public SensorsDto addSensor(SensorsDto sensorsDto) {
        if (sensorsRepository.findSensorsEntitiesByTitle(sensorsDto.getTitle()) != null) {
            throw new SensorsServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        if (sensorsRepository.findSensorsEntitiesByDescription(sensorsDto.getDescription()) != null) {
            throw new SensorsServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        SensorsEntity sensorsEntity = modelMapper.map(sensorsDto, SensorsEntity.class);

        String sensorId = utils.generateSensorId(15);
        sensorsEntity.setSensorId(sensorId);

        SensorsEntity addedSensor = sensorsRepository.save(sensorsEntity);

        return modelMapper.map(addedSensor, SensorsDto.class);
    }

    @Override
    public SensorsDto getSensor(String info) {
        SensorsEntity sensorsEntity = getSensorsEntity(info);

        if (sensorsEntity == null) {
            throw new SensorsServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorsEntity, SensorsDto.class);
    }

    @Override
    public SensorsDto updateSensor(String info, SensorsDto sensorsDto) {
        SensorsEntity sensorsEntity = getSensorsEntity(info);

        if (sensorsEntity == null) {
            throw new SensorsServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        sensorsEntity.setTitle(sensorsDto.getTitle());
        sensorsEntity.setDescription(sensorsDto.getDescription());
        sensorsEntity.setSource(sensorsDto.getSource());
        sensorsEntity.setMoreInfo(sensorsDto.getMoreInfo());
        sensorsEntity.setImageUrl(sensorsDto.getImageUrl());

        SensorsEntity updateSensor = sensorsRepository.save(sensorsEntity);

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(updateSensor, SensorsDto.class);
    }

    @Override
    public List<SensorsDto> getSensors(int page, int limit) {
        Page<SensorsEntity> sensorsPage = getSensorsPage(page, limit);
        return getSensorsDto(sensorsPage);
    }

    @Override
    public SensorsDtoExtra getSensorsExtra(int page, int limit) {
        Page<SensorsEntity> sensorsPage = getSensorsPage(page, limit);

        List<SensorsDto> sensorsDtos = getSensorsDto(sensorsPage);
        int totalPages = sensorsPage.getTotalPages();

       return new SensorsDtoExtra(sensorsDtos, totalPages);
    }

    @Override
    public void deleteSensor(String sensorId) {
        SensorsEntity sensorsEntity = getSensorsEntity(sensorId);

        if (sensorsEntity == null) {
            throw new SensorsServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        sensorsRepository.delete(sensorsEntity);
    }

    private SensorsEntity getSensorsEntity(String info) {
        SensorsEntity sensorsEntity = new SensorsEntity();

        if (info.length() == 15) {
            sensorsEntity = sensorsRepository.findSensorsEntitiesBySensorId(info);

            if (sensorsEntity == null)
                sensorsEntity = sensorsRepository.findSensorsEntitiesByTitle(info);

        } else {
            sensorsEntity = sensorsRepository.findSensorsEntitiesByTitle(info);
        }

        return sensorsEntity;
    }

    private Page<SensorsEntity> getSensorsPage(int page, int limit) {
        if (page > 0)
            page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);
        return sensorsRepository.findAll(pageableRequest);
    }

    private List<SensorsDto> getSensorsDto(Page<SensorsEntity> sensorsPage) {
        List<SensorsDto> returnValue;

        List<SensorsEntity> sensors = sensorsPage.getContent();

        Type listType = new TypeToken<List<SensorsDto>>() {
        }.getType();
        returnValue = new ModelMapper().map(sensors, listType);

        return returnValue;
    }
}
