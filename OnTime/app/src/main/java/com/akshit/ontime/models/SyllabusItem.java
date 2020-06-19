package com.akshit.ontime.models;

import java.util.List;

import lombok.Data;

@Data
public class SyllabusItem {

    /**
     * Book name or chapter name.
     */
    private String title;

    /**
     * Can be writers or topics of syllabus.
     */
    private List<String> subTopics;

}
