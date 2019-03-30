package clientside;

import org.junit.Assert;
import org.junit.Test;

public class RegisterCheckTest {

    @Test
    public void checkUserTestFail() {
        Assert.assertFalse(RegisterCheck.checkUser(""));
    }

    @Test
    public void checkUserTestSuccess() {
        Assert.assertTrue(RegisterCheck.checkUser("testUser"));
    }

    @Test
    public void checkEmailFail() {
        Assert.assertFalse(RegisterCheck.checkEmail("failingTestEmail"));
    }

    @Test
    public void checkEmptyEmail() {
        Assert.assertFalse(RegisterCheck.checkEmail(""));
    }

    @Test
    public void checkEmailSuccess() {
        Assert.assertTrue(RegisterCheck.checkEmail("lol@lol.lol"));
    }

    @Test
    public void checkDifferentPasswordsFail() {
        Assert.assertFalse(RegisterCheck.checkPassword("12345678", "13456789"));
    }

    @Test
    public void confPasswLongerThan8() {
        Assert.assertTrue(RegisterCheck.checkPassword("123456789", "123456789"));
    }

    @Test
    public void passG8confPL8(){
        Assert.assertFalse(RegisterCheck.checkPassword("123456789", "123"));
    }

    @Test
    public void tooSmallPassword() {
        Assert.assertFalse(RegisterCheck.checkPassword("123", "46512645"));
    }

    @Test
    public void validationFail() {
        Assert.assertFalse(RegisterCheck.validate("wrongEmail"));
    }

    @Test
    public void validationSuccess() {
        Assert.assertTrue(RegisterCheck.validate("test@testemail.test"));
    }
}
