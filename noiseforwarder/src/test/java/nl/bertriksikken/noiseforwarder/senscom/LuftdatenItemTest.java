package nl.bertriksikken.noiseforwarder.senscom;

import org.junit.Assert;
import org.junit.Test;

public final class LuftdatenItemTest {

    @Test
    public void testFormat() {
        Double d = 0.1 * 174;
        SensComItem item = new SensComItem("name", d);
        Assert.assertEquals("17.4", item.getValue());
    }
    
}
