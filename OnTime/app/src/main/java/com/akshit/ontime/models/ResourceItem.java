package com.akshit.ontime.models;

import lombok.Data;

/**
 * Subject's resource item.
 */
@Data
public class ResourceItem {

    /**
     * Name of the resource
     */
    private String name;

    /**
     * Name of the logo.
     */
    private String logoUrl;

    /**
     * Type of resource.
     */
    private String typeOfResource;
}
