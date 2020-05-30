package com.akshit.ontime.dagger;

import com.akshit.ontime.CollegeApplication;

import dagger.Component;


@Component
public interface ApplicationComponent {

    void inject(CollegeApplication collegeApplication);

}
