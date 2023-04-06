package ru.infoza.resourceServer;

import resources.TestResource;

public interface ResourceServerI {
    String getName();

    int getAge();

    void setTestResource(TestResource testResource);
}
