Feature: As a user I want to be able to add a new link to my library

    Scenario: Add a new link
        Given Library is initialized
        When a link "sometestlink.link" is added
        Then the library should contain a link named "sometestlink.link"

    Scenario: Trying to find nonexistent link
        Given Library is initialized
        When a link "testlink.link" is added
        Then the library should not contain a link named "anothertestlink.link"