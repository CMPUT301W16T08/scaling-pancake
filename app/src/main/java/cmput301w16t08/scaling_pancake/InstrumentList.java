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
        for (int i = 0; i < this.instruments.size(); i++) {
            if (this.instruments.get(i).getId().equals(instrument.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean containsInstrument(String id) {
        for (int i = 0; i < this.instruments.size(); i++) {
            if (this.instruments.get(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void addInstrument(Instrument instrument) {
        this.instruments.add(instrument);
    }

    public void removeInstrument(Instrument instrument) {
        for (int i = 0; i < this.instruments.size(); i++) {
            if (instrument.getId().equals(this.instruments.get(i).getId())) {
                this.instruments.remove(i);
                return;
            }
        }
    }

    public void removeInstrument(String id) {
        for (int i = 0; i < this.instruments.size(); i++) {
            if (id.equals(this.instruments.get(i).getId())) {
                this.instruments.remove(i);
                return;
            }
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

    public Instrument getInstrument(String id) {
        for (int i = 0; i < this.instruments.size(); i++) {
            if (id.equals(this.instruments.get(i).getId())) {
                return this.instruments.get(i);
            }
        }
        throw new RuntimeException();
    }

    public void clearInstruments() {
        this.instruments.clear();
    }
}
