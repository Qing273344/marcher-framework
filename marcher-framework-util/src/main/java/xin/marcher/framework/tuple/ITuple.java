package xin.marcher.framework.tuple;

/**
 * ITuple
 *
 * @author marcher
 */
public interface ITuple {

    /**
     * size
     *
     * @return  result
     */
    int size();

    /**
     * get
     * @param index index
     * @return  result
     */
    Object get(int index);
}
