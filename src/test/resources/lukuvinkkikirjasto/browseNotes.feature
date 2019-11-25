Feature: As a user I can browse my notes

    Scenario: A link can be browsed after it is added
        Given Library is initialized
        When a link named "testlink" with url "sometestlink.link" is added
        And links are listed
        Then listing contains link named "testlink" with url "sometestlink.link"

    Scenario: All added links are listed
        Given Library is initialized
        When a link named "first" with url "firstlink.link" is added
        And a link named "second" with url "secondlink.link" is added
        And links are listed
        Then listing contains links "first" "firstlink.link" and "second" "secondlink.link"

    Scenario: A link not added is not listed
        Given Library is initialized
        When links are listed
        Then listing does not contain link called "testlink" with url "sometestlink.link" that was not added

    Scenario: An added book is listed
        Given Library is initialized
        When a book named "Muumipappa ja meri" found on "muumilink.link" authored by "Tove Jansson" with isbn "1234" is added
        And links are listed
        Then listing contains book "Muumipappa ja meri" with url "muumilink.link" with author "Tove Jansson" with isbn "1234"