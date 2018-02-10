import com.company.util.LoginValidator;
import org.junit.Assert;
import org.junit.Test;

public class LoginValidatorTest {

    @Test
    public void validEmail() {
        Assert.assertTrue(LoginValidator.isEmailValid("a@b.cd"));
    }

    @Test
    public void invalidEmail() {
        Assert.assertFalse(LoginValidator.isEmailValid("a"));
    }

    @Test
    public void invalidEmailWithoutAddress() {
        Assert.assertFalse(LoginValidator.isEmailValid("@b.cd"));
    }

    @Test
    public void invalidEmailWithoutDomain() {
        Assert.assertFalse(LoginValidator.isEmailValid("a@"));
    }

    @Test
    public void invalidEmailWithoutFirstDomain() {
        Assert.assertFalse(LoginValidator.isEmailValid("a@b."));
    }

    @Test
    public void invalidEmailWithoutSecondDomain() {
        Assert.assertFalse(LoginValidator.isEmailValid("a@b."));
    }

    @Test
    public void validNumber() {
        Assert.assertTrue(LoginValidator.isPhoneNumberValid("1234567890"));
    }

    @Test
    public void invalidNumberWithCountryCode() {
        Assert.assertFalse(LoginValidator.isPhoneNumberValid("71234567890"));
    }

    @Test
    public void invalidNumberWithDivideSymbol() {
        Assert.assertFalse(LoginValidator.isPhoneNumberValid("123-4567890"));
    }

    @Test
    public void invalidNumberShort() {
        Assert.assertFalse(LoginValidator.isPhoneNumberValid("123456789"));
    }
}
