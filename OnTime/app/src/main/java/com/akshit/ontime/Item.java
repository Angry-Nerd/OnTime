package com.akshit.ontime;

public class Item {
    private String object,ref;
    public Item(String object,String ref)
    {
        this.object=object;
        this.ref=ref;
    }

    public String getObject() {
        return object;
    }

    public String getRef() {
        return ref;
    }
}
