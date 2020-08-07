Feature: Testing a REST API for CARS

  Scenario Outline: retrieve available cars on given date from a web service
    Given user enter date <date> on which the order will be
    When user send GET request
    Then the requested data is returned
    And car code is "OK"

    Examples:
      | date         |
      | 01-01-2020   |
      | 11-09-2018   |

  Scenario Outline: user can add  a car.
    Given user send car data to be created with brand <brand>, register_number <register_number>, price <price>
    When the car added to list
    Then the car_id not null
    And car code is "CREATED"

    Examples:
      | brand      | register_number | price |
      | BMW        | 2000 FG-1       | 120   |
      | TOYOTA     | 8452 AH-5       | 100   |

  Scenario Outline: add a car and retrieve the car by id from a web service
    Given user send car data to be created with brand <brand>, register_number <register_number>, price <price>
    When the car added to list
    Then the car_id not null
    And car code is "CREATED"
    When user send GET request with car_id
    Then the requested data is returned
    And car code is "OK"

    Examples:
      | brand      | register_number | price |
      | HONDA      | 1164 FG-1       | 120   |

  Scenario Outline: add a car and update the car
    Given user send car data to be created with brand <brand>, register_number <register_number>, price <price>
    When the car added to list
    Then the car_id not null
    And car code is "CREATED"
    When user send PUT request with new car to be updated, with brand "HYUNDAI", register_number "1274 TH-3", price 130
    Then server should return 1 as a result
    And car code is "OK"

    Examples:
      | brand      | register_number | price |
      | HONDA      | 1100 FG-1       | 120   |

  Scenario Outline: add a car and delete the car
    Given user send car data to be created with brand <brand>, register_number <register_number>, price <price>
    When the car added to list
    Then the car_id not null
    And car code is "CREATED"
    When user send DELETE request with car_id to be deleted
    Then the car is removed
    And car code is "OK"

    Examples:
      | brand      | register_number | price |
      | AUDI       | 1064 AG-7       | 120   |
