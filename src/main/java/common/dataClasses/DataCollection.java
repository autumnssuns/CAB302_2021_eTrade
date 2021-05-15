package common.dataClasses;

import java.util.ArrayList;

/**
 * Represents a collection of IData classes.
 */
public class DataCollection<T extends IData> extends ArrayList<T> implements IData {

    public void addAll(T[] dataCollection) {
        for (T data : dataCollection){
            add(data);
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
