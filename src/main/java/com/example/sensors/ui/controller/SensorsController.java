package com.example.sensors.ui.controller;

import com.example.sensors.exceptions.SensorsServiceException;
import com.example.sensors.service.SensorsService;
import com.example.sensors.shared.dto.SensorsDto;
import com.example.sensors.shared.dto.SensorsDtoExtra;
import com.example.sensors.ui.model.request.SensorsRequest;
import com.example.sensors.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/instrumentation/sensors")
public class SensorsController {

    @Autowired
    SensorsService sensorsService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SensorsRest addSensor(@RequestBody SensorsRequest sensorsRequest) {
        SensorsRest returnValue = new SensorsRest();

        if (sensorsRequest.getTitle().isEmpty() || sensorsRequest.getDescription().isEmpty() || sensorsRequest.getSource().isEmpty()) {
            throw new SensorsServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        SensorsDto dto = modelMapper.map(sensorsRequest, SensorsDto.class);

        SensorsDto addSensor = sensorsService.addSensor(dto);
        returnValue = modelMapper.map(addSensor, SensorsRest.class);

        return returnValue;
    }

    @GetMapping(path = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SensorsRest getSensor(@PathVariable String sensorId) {

        SensorsDto sensorsDto = sensorsService.getSensor(sensorId);

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorsDto, SensorsRest.class);
    }

    @PutMapping(path = "/{sensorId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SensorsRest updateSensor(@PathVariable String sensorId, @RequestBody SensorsRequest sensorsRequest) {

        SensorsDto sensorsDto = new SensorsDto();
        sensorsDto = new ModelMapper().map(sensorsRequest, SensorsDto.class);

        SensorsDto updateSensor = sensorsService.updateSensor(sensorId, sensorsDto);

        return new ModelMapper().map(updateSensor, SensorsRest.class);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<SensorsRest> getSensors(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<SensorsRest> returnValue = new ArrayList<>();

        List<SensorsDto> sensorsDtos = sensorsService.getSensors(page, limit);

        Type listType = new TypeToken<List<SensorsRest>>() {
        }.getType();
        returnValue = new ModelMapper().map(sensorsDtos, listType);

        return returnValue;
    }

    @GetMapping(path = "/extra", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SensorsRestExtra getSensorsExtra(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit) {

        SensorsDtoExtra sensorsDtoExtra = sensorsService.getSensorsExtra(page, limit);

        List<SensorsDto> sensorsDtos = sensorsDtoExtra.getSensorsDtos();

        Type listType = new TypeToken<List<SensorsRest>>() {
        }.getType();

        List<SensorsRest> sensorsRests = new ModelMapper().map(sensorsDtos, listType);

        int totalPages = sensorsDtoExtra.getTotalPages();

        return  new SensorsRestExtra(sensorsRests, totalPages);
    }

    @DeleteMapping(path = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteSensor(@PathVariable String sensorId) {
        OperationStatusModel returnValue = new OperationStatusModel();

        sensorsService.deleteSensor(sensorId);

        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }
}
