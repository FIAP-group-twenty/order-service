Feature: Fetch payment status
  As a user
  I want to retrieve the payment status for an order
  So that I can check if the payment is completed

  Scenario: Fetch payment by order ID
    Given I have an order ID "123"
    When I request the payment status
    Then I should receive a payment with status "paid"