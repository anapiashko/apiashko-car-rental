Feature: Testing a REST API for ORDERS

  Scenario Outline: user can add an order.
    Given user send order data to be created with date <date>, car_id <car_id>
    When the order added to list
    Then the order_id not null
    And order code is "CREATED"

    Examples:
      | date        | car_id |
      | 01-01-2020  | 1      |
      | 11-09-2018  | 2      |
