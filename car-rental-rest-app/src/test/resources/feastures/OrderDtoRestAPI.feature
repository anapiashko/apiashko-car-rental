Feature: Testing a REST API for ORDER_DTOS

  Scenario: retrieve all order_dtos from a web service
    When user send GET request to /order_dtos
    Then the requested data is returned in format list of order_dtos
    And order_dto code is "OK"