Feature: As a user I want to be able to add a new link to my library through web UI

    Scenario: Add a new link
        Given Library is initialized
        And lisää uusi muistiinpano is selected
        When a link named "Kuningas" with url "www.litti.fi" is added through web UI
        Then list all menu should list item "Kuningas"

    Scenario: Added link is not listed in booklist menu
        Given Library is initialized
        And lisää uusi muistiinpano is selected
        When a link named "Pukki" with url "www.goat.ft" is added through web UI
        Then list books menu should not list item "Pukki"