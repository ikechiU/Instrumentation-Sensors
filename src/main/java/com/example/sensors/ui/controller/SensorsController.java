package com.example.sensors.ui.controller;

import com.example.sensors.exceptions.SensorsServiceException;
import com.example.sensors.service.SensorsService;
import com.example.sensors.shared.dto.SensorsDto;
import com.example.sensors.shared.dto.SensorsDtoExtra;
import com.example.sensors.ui.model.request.SensorsRequest;
import com.example.sensors.ui.model.response.*;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/instrumentation/sensors")
public class SensorsController {

    @Autowired
    SensorsService sensorsService;

    @Operation(summary = "Get all sensors.")
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

    @Operation(summary = "Add a sensor. All fields required, file must be an image not more than 1Mb size.")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SensorsRest addSensor(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "source") String source,
            @RequestParam(name = "moreInfo") String moreInfo,
            @RequestPart("file") MultipartFile multipartFile
    ) {

        SensorsRest returnValue = new SensorsRest();

        SensorsDto dto = getSensorDto(title, description, source, moreInfo);

        SensorsDto addSensor = sensorsService.addSensor(dto, multipartFile);
        returnValue = new ModelMapper().map(addSensor, SensorsRest.class);

        return returnValue;
    }

    @Operation(summary = "Get a sensor by sensorId or title as a path variable.")
    @GetMapping(path = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SensorsRest getSensor(@PathVariable String sensorId) {

        SensorsDto sensorsDto = sensorsService.getSensor(sensorId);

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorsDto, SensorsRest.class);
    }

    @Operation(summary = "Update a sensor. All fields required, file must be an image not more than 1Mb size.")
    @PutMapping(path = "/{sensorId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SensorsRest updateSensor (
            @PathVariable String sensorId,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "source") String source,
            @RequestParam(name = "moreInfo") String moreInfo,
            @RequestParam(name = "file", required = false) MultipartFile multipartFile
    ) throws IOException {

        SensorsRest returnValue = new SensorsRest();

        SensorsDto dto = getSensorDto(title, description, source, moreInfo);

        SensorsDto updateSensor = sensorsService.updateSensor(sensorId, dto, multipartFile);
        returnValue = new ModelMapper().map(updateSensor, SensorsRest.class);

        return returnValue;
    }

    @Operation(summary = "Delete a sensor by sensorId or title as a path variable.")
    @DeleteMapping(path = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteSensor(@PathVariable String sensorId) throws IOException {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        String result = sensorsService.deleteSensor(sensorId);

        if (result.equals("ok")) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.getMessage());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR_ONLY_AN_ADMIN_CAN_DELETE_THIS_SENSOR.getMessage());
        }
        return returnValue;
    }

    @Operation(summary = "Get all sensor with some extra details.")
    @GetMapping(path = "/extra", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SensorsRestExtra getSensorsExtra(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        SensorsDtoExtra sensorsDtoExtra = sensorsService.getSensorsExtra(page, limit);

        List<SensorsDto> sensorsDtos = sensorsDtoExtra.getSensorsDtos();

        Type listType = new TypeToken<List<SensorsRest>>() {
        }.getType();

        List<SensorsRest> sensorsRests = new ModelMapper().map(sensorsDtos, listType);
        int pageListCount = sensorsDtoExtra.getPageListCount();
        int currentPage = sensorsDtoExtra.getCurrentPage();
        String previousPage = sensorsDtoExtra.getPreviousPage();
        String nextPage = sensorsDtoExtra.getNextPage();
        long totalListCount = sensorsDtoExtra.getTotalListCount();
        int totalPages = sensorsDtoExtra.getTotalPages();

        return  new SensorsRestExtra(sensorsRests, pageListCount, currentPage, previousPage, nextPage, totalListCount, totalPages);
    }

    private SensorsDto getSensorDto(String title, String description, String source, String moreInfo) {
        SensorsRequest sensorsRequest = new SensorsRequest();
        sensorsRequest.setTitle(title);
        sensorsRequest.setDescription(description);
        sensorsRequest.setSource(source);
        sensorsRequest.setMoreInfo(moreInfo);
        sensorsRequest.setImageUrl("");

        if (sensorsRequest.getTitle().isEmpty() || sensorsRequest.getDescription().isEmpty() || sensorsRequest.getSource().isEmpty()) {
            throw new SensorsServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorsRequest, SensorsDto.class);
    }

}
