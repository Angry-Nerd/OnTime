package com.akshit.ontime.models;

import java.util.Map;

import lombok.Data;

/**
 * Item for the resource material.
 */
@Data
public class ResourceMaterialItem {

    /**
     * Can be only image, pdf, docx or xlsx file.
     */
    private String typeOfMaterial;

    /**
     * Download URL of the file.
     */
    private String downloadUrl;

    /**
     * Title of the file.
     */
    private String titleOfFile;

    /**
     * Other metadata for the file.
     */
    private Map<String, String> metadata;

    /**
     * Extension for the file.
     */
    private String fileExtension;

}
