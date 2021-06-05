package common.dataClasses;

import java.util.ArrayList;

/**
 * Represents a collection of IData classes.
 */
public class DataCollection<T extends IData> extends ArrayList<T> implements IData {

    /**
     * Adds an array of IData objects to the collection
     * @param dataCollection
     */
    public void addAll(T[] dataCollection) {
        for (T data : dataCollection){
            add(data);
        }
    }

    /**
     * Indicates if some object is equal to this instance.
     * @param o The object to compare.
     * @return true if the object is equal to the instance, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
