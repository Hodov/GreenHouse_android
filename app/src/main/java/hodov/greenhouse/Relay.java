package hodov.greenhouse;

/**
 * Created by skorokhodov_a on 03.08.2016.
 */
public class Relay {
    public String name;
    public String mode;
    public int switchPosition;
    public int sensorValue;

    public Relay(String name, String mode, int switchPosition, int sensorValue) {
        this.name = name;
        this.mode = mode;
        this.switchPosition = switchPosition;
        this.sensorValue = sensorValue;
    }
}
