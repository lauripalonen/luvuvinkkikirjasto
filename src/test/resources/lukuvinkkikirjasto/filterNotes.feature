Feature: As a user I want to filter my notes with tags
    
    Scenario: Notes with chosen tag are displayed
        Given Library is initialized
        And lisää uusi is selected
        When a link named "first" with url "firstlink.link" and tag "new" is added 
        And tallennetut lukuvinkit is selected
        And tag "new" is chosen
        Then list all menu should have a link named "first" 

    Scenario: Notes that do not have right tag are not listed
        Given Library is initialized
        And lisää uusi is selected
        When a link named "first" with url "firstlink.link" and tag "new" is added 
        And a link named "second" with url "secondlink.link" and tag "old" is added
        And tallennetut lukuvinkit is selected
        And tag "new" is chosen
        Then list all menu should have not a link named "second" with url "secondlink.link" 