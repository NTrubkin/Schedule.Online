import com.company.service.util.AccountIdentifyConverter;
import org.junit.Assert;
import org.junit.Test;

public class AccountIdentifyConverterTest {

    private final AccountIdentifyConverter converter = new AccountIdentifyConverter();

    @Test
    public void ValidNameTest() {
        int id = 123;
        Assert.assertTrue(converter.NameToId("id" + id) == id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidNameTest() {
        converter.NameToId("name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidNameWithoutNumbersTest() {
        converter.NameToId("id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidNameOnlyNumbersTest() {
        Integer id = 123;
        converter.NameToId(id.toString());
    }

    @Test
    public void ValidId() {
        Assert.assertTrue(converter.IdToName(123).equals("id123"));
    }
}
