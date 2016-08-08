package hodov.greenhouse;

/**
 * Created by skorokhodov_a on 03.08.2016.
 */
public class Relay {
    public String name;
    public String mode;
    public int switcher;
    public String value;
    public int lowerBoundThreshold;
    public int upperBoundThreshold;

    public Relay(String name, String mode, int switcher, String value,int lowerBoundThreshold, int upperBoundThreshold) {
        this.name = name;
        this.mode = mode;
        this.switcher = switcher;
        this.value = value;
        this.lowerBoundThreshold = lowerBoundThreshold;
        this.upperBoundThreshold = upperBoundThreshold;
    }
}
