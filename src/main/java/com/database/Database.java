package com.database;

import com.model.Empl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by win on 10/10/16.
 */
public class Database {
    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private List<Empl> emplList = Collections.synchronizedList(new ArrayList<Empl>());


    private Database() {
    }

    public List<Empl> getEmplList() {
        return emplList;
    }

    public void setEmplList(List<Empl> emplList) {
        this.emplList = Collections.synchronizedList(new ArrayList<Empl>(emplList));
    }
}
