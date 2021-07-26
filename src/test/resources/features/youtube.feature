Feature: VisionBox Exercise

#Use this scenario if you don't see any videos above the line for popular videos
  Scenario: Check trending videos
    Given the user navigates to "www.youtube.com"
    And the user clicks "Explore"
    Then exactly 50 videos are displayed
    And the user logs the 5 most viewed videos "viewcount", "name", "id"
    And the user logs the 5 longest videos "length", "name", "id"
    And the user sums the view count for all listed videos
