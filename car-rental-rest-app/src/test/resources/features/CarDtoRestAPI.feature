Feature: Testing a REST API for CAR_DTOS

  Scenario Outline: retrieve car_dtos by dateTo and dateFrom from a web service
    Given user enter dateFrom <dateFrom> and dateTo <dateTo>
    When user send GET request to /car_dtos
    Then the requested data is returned in format list of car_dtos
    And car_dto code is "OK"

    Examples:
      | dateFrom     | dateTo     |
      | 01-01-2020   | 01-12-2020 |
      | 11-09-2018   | 20-11-2018 |