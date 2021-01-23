import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToolVendorTest {

    @Test
    void loadToolsTest() throws IOException {
        List<Tool> expectedAvailableTools = new ArrayList<Tool>();
        expectedAvailableTools.add(new Tool("Ladder", "Werner", "LADW"));
        expectedAvailableTools.add(new Tool("Chainsaw", "Stihl", "CHNS"));
        expectedAvailableTools.add(new Tool("Jackhammer", "Ridgid", "JAKR"));
        expectedAvailableTools.add(new Tool("Jackhammer", "DeWalt", "JAKD"));


        ToolVendor toolVendor = ToolVendor.getInstance();
        List<Tool> actualAvailableTools = toolVendor.getAvailableTools();

        assertEquals(actualAvailableTools.size(), 4);

        assertArrayEquals(actualAvailableTools.toArray(), expectedAvailableTools.toArray());
    }
}
