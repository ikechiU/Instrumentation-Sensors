package com.example.sensors;

import com.example.sensors.ui.model.request.SensorsRequest;
import com.example.sensors.ui.model.response.OperationStatusModel;
import com.example.sensors.ui.model.response.SensorsRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    Contact contact = new Contact(
            "Ikechi Ucheagwu",
            "https://sympathomimetic-rel.000webhostapp.com/",
            "ikechiucheagwu@gmail.com"
    );

    List<VendorExtension> vendorExtensions = new ArrayList<>();

    ApiInfo apiInfo = new ApiInfo(
            "Instrumentation Sensors RESTful Web Service documentation",
            "This pages documents Instrumentation Sensors RESTful Web Service endpoints",
            "1.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            contact,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            vendorExtensions);


    @Bean
    public Docket apiDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo) //Extra documentation features
//                .ignoredParameterTypes(OperationStatusModel.class, SensorsRequest.class, SensorsRest.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.sensors"))
                .paths(PathSelectors.any())
                .build();

        return docket;

    }

}
