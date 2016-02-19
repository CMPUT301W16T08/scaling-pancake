package cmput301w16t08.scaling_pancake;

import java.util.ArrayList;

/**
 * Created by William on 2016-02-18.
 */
public class InstrumentList {
    private ArrayList<Instrument> instruments;

    public InstrumentList() {
        this.instruments = new ArrayList<Instrument>();
    }

    public int size() {
        return this.instruments.size();
    }

    public boolean containsInstrument(Instrument instrument) {
        return this.instruments.contains(instrument);
    }

    public void addInstrument(Instrument instrument) {
        this.instruments.add(instrument);
    }

    public void removeInstrument(Instrument instrument) {
        if (this.instruments.contains(instrument)) {
            this.instruments.remove(instrument);
        } else {
            throw new RuntimeException();
        }
    }

    public void removeInstrument(int index) {
        if (index < this.instruments.size()) {
            this.instruments.remove(index);
        } else {
            throw new RuntimeException();
        }
    }

    public Instrument getInstrument(int index) {
        if (index < this.instruments.size()) {
            return this.instruments.get(index);
        } else {
            throw new RuntimeException();
        }
    }

    public void clearInstruments() {
        this.instruments.clear();
    }
}
