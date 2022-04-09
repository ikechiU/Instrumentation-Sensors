package com.example.sensors.service.impl;

import com.cloudinary.Cloudinary;
import com.example.sensors.exceptions.SensorsServiceException;
import com.example.sensors.io.entity.SensorsEntity;
import com.example.sensors.io.repository.SensorsRepository;
import com.example.sensors.service.SensorsService;
import com.example.sensors.shared.CloudinaryUploader;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class SensorsServiceImpl implements SensorsService {

    private final Cloudinary cloudinaryConfig;

    public SensorsServiceImpl(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Autowired
    SensorsRepository sensorsRepository;

    @Autowired
    Utils utils;

    @Autowired
    CloudinaryUploader cloudinaryUploader;

    @Override
    public SensorsDto addSensor(SensorsDto sensorsDto, MultipartFile file) {
        if (sensorsRepository.findSensorsEntitiesByTitle(sensorsDto.getTitle()) != null) {
            throw new SensorsServiceException(ErrorMessages.TITLE_ALREADY_EXISTS.getErrorMessage());
        }

        if (sensorsRepository.findSensorsEntitiesByDescription(sensorsDto.getDescription()) != null) {
            throw new SensorsServiceException(ErrorMessages.DESCRIPTION_ALREADY_EXISTS.getErrorMessage());
        }

        String sensorId = utils.generateSensorId(15);

        String path = file.getOriginalFilename();
        if (path != null && !path.equals("")) {
            String url = cloudinaryUploader.uploadFile(cloudinaryConfig, file, sensorId);
            sensorsDto.setImageUrl(url);
        } else {
            throw new SensorsServiceException(ErrorMessages.NO_IMAGE_FOUND.getErrorMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        SensorsEntity sensorsEntity = modelMapper.map(sensorsDto, SensorsEntity.class);

        sensorsEntity.setSensorId(sensorId);

        SensorsEntity addedSensor = sensorsRepository.save(sensorsEntity);

        return modelMapper.map(addedSensor, SensorsDto.class);
    }

    @Override
    public SensorsDto getSensor(String queryParam) {
        SensorsEntity sensorsEntity = getSensorsEntity(queryParam);

        if (sensorsEntity == null) {
            throw new SensorsServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorsEntity, SensorsDto.class);
    }

    @Override
    public SensorsDto updateSensor(String queryParam, SensorsDto sensorsDto, MultipartFile file) throws IOException {

        SensorsEntity sensorsEntity = getSensorsEntity(queryParam);

        if (sensorsEntity == null) {
            throw new SensorsServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        if (sensorsRepository.findSensorsEntitiesByTitle(sensorsDto.getTitle()) != null) {
            throw new SensorsServiceException(ErrorMessages.TITLE_ALREADY_EXISTS.getErrorMessage());
        }

        if (sensorsRepository.findSensorsEntitiesByDescription(sensorsDto.getDescription()) != null) {
            throw new SensorsServiceException(ErrorMessages.DESCRIPTION_ALREADY_EXISTS.getErrorMessage());
        }

        String path = file.getOriginalFilename();
        if (path != null && !path.equals("")) {
            String url = cloudinaryUploader.updateFile(cloudinaryConfig, file, sensorsEntity.getSensorId());
            sensorsDto.setImageUrl(url);
        } else {
            throw new SensorsServiceException(ErrorMessages.NO_IMAGE_FOUND.getErrorMessage());
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

        int currentPage = (page == 0) ? 1 : page;

        List<SensorsDto> sensorsDtos = getSensorsDto(sensorsPage);
        int totalPages = sensorsPage.getTotalPages();

        if (page > 0)
            page = page - 1;

        String previousPage = "";
        String nextPage = "";

        if(page < totalPages){
            if(currentPage == totalPages)
                nextPage = null;
            else
                nextPage = String.valueOf((currentPage + 1));
        } else if(page == totalPages)
            nextPage = null;
        else
            nextPage = null;

        if(currentPage == 1 || page == 0 || totalPages == 1)
            previousPage = null;
        else{
            if(totalPages > currentPage || totalPages == currentPage)
                previousPage = String.valueOf(currentPage - 1);
            else
                previousPage = String.valueOf(totalPages);
        }

        int pageListCount = sensorsDtos.size();
        long totalListCount = sensorsPage.getTotalElements();

        return new SensorsDtoExtra(sensorsDtos, pageListCount, currentPage, previousPage, nextPage, totalListCount, totalPages);
    }

    @Override
    public String deleteSensor(String queryParam) throws IOException {
        SensorsEntity sensorsEntity = getSensorsEntity(queryParam);

        if (sensorsEntity == null) {
            throw new SensorsServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        String result = cloudinaryUploader.deleteFile(cloudinaryConfig, sensorsEntity.getSensorId());

        if (result.equals("ok")) {
            sensorsRepository.delete(sensorsEntity);
        }
        return result;
    }

    private SensorsEntity getSensorsEntity(String queryParam) {
        SensorsEntity sensorsEntity = new SensorsEntity();

        if (queryParam.length() == 15) {
            sensorsEntity = sensorsRepository.findSensorsEntitiesBySensorId(queryParam);

            if (sensorsEntity == null)
                sensorsEntity = sensorsRepository.findSensorsEntitiesByTitle(queryParam);
            else
                return sensorsEntity;

        } else {
            sensorsEntity = sensorsRepository.findSensorsEntitiesByTitle(queryParam);
        }

        return sensorsEntity;
    }

    private Page<SensorsEntity> getSensorsPage(int page, int limit) {
        if (page > 0)
            page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("id").descending());
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
