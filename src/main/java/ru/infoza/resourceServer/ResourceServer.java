package ru.infoza.resourceServer;

import resources.TestResource;

public class ResourceServer implements ResourceServerI {

    private TestResource testResource;

    public ResourceServer(TestResource testResource) {
        this.testResource = testResource;
    }

    public void setTestResource(TestResource testResource) {
        this.testResource = testResource;
    }

    @Override
    public String getName() {
        return testResource.getName();
    }

    @Override
    public int getAge() {
        return testResource.getAge();
    }
}
