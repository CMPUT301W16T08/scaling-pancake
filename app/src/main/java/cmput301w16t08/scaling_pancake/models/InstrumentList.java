package cmput301w16t08.scaling_pancake.models;

import java.util.ArrayList;

/**
 * <code>InstrumentList</code> is meant to hold a list of <code>Instrument</code> objects.
 * It is basically a wrapper around an <code>ArrayList</code>   .
 *
 * @author William
 * @see Instrument
 */
public class InstrumentList {
    private ArrayList<Instrument> instruments;

    /**
     * Creates a new list of <code>Instrument</code>s
     *
     * @see Instrument
     */
    public InstrumentList() {
        this.instruments = new ArrayList<Instrument>();
    }

    /**
     * Returns the size of the list
     *
     * @return the size
     */
    public int size() {
        return this.instruments.size();
    }

    /**
     * Checks whether the supplied <code>Instrument</code> is contained in the list
     *
     * @param instrument the instrument in question
     * @return true if instrument contained in list, else false
     */
    public boolean containsInstrument(Instrument instrument) {
        for (int i = 0; i < this.instruments.size(); i++) {
            if (this.instruments.get(i).getId().equals(instrument.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the <code>Instrument</code> with id <code>id</code> is contained in the list
     *
     * @param id the id of the instrument in question
     * @return true if instrument contained in list, else false
     */
    public boolean containsInstrument(String id) {
        for (int i = 0; i < this.instruments.size(); i++) {
            if (this.instruments.get(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an <code>Instrument</code> to the list
     *
     * @param instrument the instrument to add
     */
    public void addInstrument(Instrument instrument) {
        this.instruments.add(instrument);
    }

    /**
     * Removes an <code>Instrument</code> from the list
     *
     * @param instrument the instrument to remove
     */
    public void removeInstrument(Instrument instrument) {
        for (int i = 0; i < this.instruments.size(); i++) {
            if (instrument.getId().equals(this.instruments.get(i).getId())) {
                this.instruments.remove(i);
                return;
            }
        }
    }

    /**
     * Removes the <code>Instrument</code> with id <code>id</code> from the list
     *
     * @param id the id of the instrument to remove
     */
    public void removeInstrument(String id) {
        for (int i = 0; i < this.instruments.size(); i++) {
            if (id.equals(this.instruments.get(i).getId())) {
                this.instruments.remove(i);
                return;
            }
        }
    }

    /**
     * Removes the <code>Instrument</code> at the specified index from the list
     *
     * @param index the index of the instrument to remove
     * @throws RuntimeException
     *          if index is not contained in range <code>0</code> to <code>size()</code>
     */
    public void removeInstrument(int index) {
        if (index < this.instruments.size() && index >= 0) {
            this.instruments.remove(index);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Returns the <code>Instrument</code> at the specified index
     *
     * @param index the index of the instrument to return
     * @return the instrument
     * @throws RuntimeException
     *          if index is not contained in range <code>0</code> to <code>size()</code>
     */
    public Instrument getInstrument(int index) {
        if (index < this.instruments.size() && index >= 0) {
            return this.instruments.get(index);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Returns the list as an <code>ArrayList</code>
     *
     * @return the ArrayList
     */
    public ArrayList<Instrument> getArray() {
        /* Accessor method for the  adapters to link with the arraylist. */
        return instruments;
    }

    /**
     * Returns the <code>Instrument</code> with the specified id
     *
     * @param id the id of the instrument to return
     * @return the instrument
     * @throws RuntimeException
     *          if no instrument with that id is contained in the list
     */
    public Instrument getInstrument(String id) {
        for (int i = 0; i < this.instruments.size(); i++) {
            if (id.equals(this.instruments.get(i).getId())) {
                return this.instruments.get(i);
            }
        }
        throw new RuntimeException();
    }

    /**
     * Removes all <code>Instrument</code>s from the list
     */
    public void clearInstruments() {
        this.instruments.clear();
    }
}
