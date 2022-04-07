package com.example.sensors.shared;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.sensors.exceptions.SensorsServiceException;
import com.example.sensors.ui.model.response.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryUploader {

    private final String FOLDER_PATH = "sensor/production/";

    public String uploadFile(Cloudinary cloudinaryConfig, MultipartFile file, String sensorId) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinaryConfig.uploader().upload(
                    uploadedFile,
                    ObjectUtils.asMap(
                            "folder", FOLDER_PATH,
                            "public_id", sensorId,
                            "use_filename", true,
                            "unique_filename", true
                    )
            );
            boolean isDeleted = uploadedFile.delete();

            if (isDeleted){
                System.out.println("File successfully deleted");
            }else
                System.out.println("File doesn't exist");
            return  uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String deleteFile(Cloudinary cloudinaryConfig, String public_id) throws IOException {
        Map destroyResult = cloudinaryConfig.uploader().destroy(FOLDER_PATH + public_id, ObjectUtils.emptyMap());
        return destroyResult.get("result").toString();
    }

    public String updateFile(Cloudinary cloudinaryConfig, MultipartFile file, String sensorId) throws IOException {
        String result = deleteFile(cloudinaryConfig, sensorId);

        if (result.equals("ok")) {
            return  uploadFile(cloudinaryConfig, file, sensorId);
        } else {
            throw new SensorsServiceException(ErrorMessages.ERROR_UPDATING_SENSOR.getErrorMessage());
        }
    }

}
