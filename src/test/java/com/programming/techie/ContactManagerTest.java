/**
 * Implementation 3
 * Conditional Executions: @EnabledOnOs and @DisabledOnOs
 * SO - Windows 11
 */

package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  Junit instantiates the Test class for each method marked with @Test. So,
 *  @BeforeAll and @AfterAll, should be marked with static. However, this behavior could be
 *  changed, by instructing Junit to create an instance of the Test class using @TestInstance.
**/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {

    private ContactManager contactManager;

    @BeforeAll
    public void setupAll() {
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setup() {
        System.out.println("\nShould Print Before Each Test");
        contactManager = new ContactManager();
    }

    @Test
    @DisplayName("Should Create Contact")
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Doe", "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", null);
        });
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        System.out.println("Should Print After Each Test");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("\nShould Print After All Tests");
    }

    @Test
    @DisplayName("Should Create Contact Only on Mac OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Should run only on MAC OS")
    public void shouldNotCreateContactOnlyOnMac() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Should Not Create Contact on Windows OS")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on WINDOWS OS")
    public void shouldNotCreateContactOnlyOnWindows() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

}

/** Result:
 *
 Should Print Before All Tests

 Disabled on operating system: Windows 11 ==> Should run only on MAC OS

 Should Print Before Each Test
 Should Print After Each Test

 Should Print Before Each Test
 Should Print After Each Test

 Should Print Before Each Test
 Should Print After Each Test

 Disabled on operating system: Windows 11 ==> Disabled on WINDOWS OS

 Should Print Before Each Test
 Should Print After Each Test

 Should Print After All Tests

 Process finished with exit code 0
 */