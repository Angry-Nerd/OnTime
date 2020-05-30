package com.akshit.ontime.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to hold the view page in welcome intro.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenItem {

    /**
     * Title of the intro message.
     */
    private String title;

    /**
     * Description of the intro image.
     */
    private String description;

    /**
     * Image Id of the image to be shown.
     */
    int screenImg;
}
