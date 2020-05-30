package com.akshit.ontime.managers;

public class SemesterDetailsManager {





    /**
     * Private constructor.
     */
    private SemesterDetailsManager() {}


    /**
     * Get manager instance.
     * @return manager instance.
     */
    public static SemesterDetailsManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Class to create object of the class.
     */
    private static class InstanceHolder {
        private final static SemesterDetailsManager INSTANCE = new SemesterDetailsManager();
    }
}
