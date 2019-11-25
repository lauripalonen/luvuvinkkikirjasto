Feature: As a user I want to be able to add a new link to my library

    Scenario: Add a new link
        Given Library is initialized
        When a link named "testlink" with url "sometestlink.link" is added
        Then the library should contain a link named "testlink" with url "sometestlink.link"

    Scenario: Trying to find nonexistent link
        Given Library is initialized
        When a link named "something" with url "testlink.link" is added
        Then the library should not contain a link named "another" with url "anothertestlink.link"