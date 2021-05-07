package common.dataClasses;

import java.util.ArrayList;

/**
 * Represents a collection of IData classes.
 */
public class DataCollection<T extends IData> extends ArrayList<T> implements IData {

}
