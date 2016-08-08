package hodov.greenhouse;

import java.util.ArrayList;

/**
 * Created by skorokhodov_a on 08.08.2016.
 */
public class SensorController {
    String name;
    ArrayList<Relay> relays;

    public SensorController(String name) {
        this.name = name;
        this.relays = new ArrayList<Relay>();
    }
}
