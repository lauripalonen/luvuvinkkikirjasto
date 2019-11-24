Feature: As a user I can browse my notes

    Scenario: A link can be browsed after it is added
        Given Library is initialized
        When a link "sometestlink.link" is added
        And links are listed
        Then listing contains link "sometestlink.link"

    Scenario: All added links are listed
        Given Library is initialized
        When a link "firstlink.link" is added
        And a link "secondlink.link" is added
        And links are listed
        Then listing contains links "firstlink.link" and "secondlink.link"

    Scenario: A link not added is not listed
        Given Library is initialized
        When links are listed
        Then listing does not contain link "sometestlink.link" that was not added

    Scenario: An added book is listed
        Given Library is initialized
        When a book named "Muumipappa ja meri" found on "muumilink.link" authored by "Tove Jansson" with isbn "1234" is added
        And links are listed
        Then listing contains link "muumilink.link"