# SoftwareTesting
Develop a Hotel Management System using Java and develop test code to do unit testing and integration testing t the system using JUnit4.

# Hotel Room Booking System

This project is a Hotel Room Booking System developed as part of the UECS2354 Software Testing assignment. The system allows users to book hotel rooms based on their membership status (VIP member, normal member, or non-member) and includes functionality for room allocation, booking cancellation, and waiting list management.

## Project Structure

- `Booking.java`: Contains the logic for managing room bookings.
- `Printer.java`: Placeholder class for printing booking information.
- `Room.java`: Placeholder class for checking room availability.
- `User.java`: Defines the user object with attributes such as name, membership type, and exclusive reward status.
- `WaitingList.java`: Manages the waiting lists for different user types.

## Test Structure

- `BookingTest.java`: Unit tests for the `Booking` class.
- `UserTest.java`: Unit tests for the `User` class.
- `WaitingListTest.java`: Unit tests for the `WaitingList` class.
- `BookingIntegrationTest.java`: Integration tests for the booking functionality.
- `TestSuite.java`: Aggregates all test cases for easier execution.

## Features

- **VIP Member Booking**:
  - Priority booking for VIP rooms, fallback to Deluxe and Standard rooms if VIP rooms are unavailable.
  - Can book up to 3 rooms at a time.
  - Added to VIP waiting list if no rooms are available.

- **Normal Member Booking**:
  - Booking for Deluxe rooms, fallback to Standard rooms.
  - Exclusive rewards allow booking VIP rooms.
  - Can book up to 2 rooms at a time.
  - Added to member waiting list if no rooms are available.

- **Non-member Booking**:
  - Can book only one Standard room.
  - Added to normal waiting list if no rooms are available.

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Apache Maven
- JUnit and Mockito libraries


## Usage

- Implement the `Room` and `Printer` classes as needed.
- Modify and extend the `Booking`, `User`, and `WaitingList` classes based on specific project requirements.


## Acknowledgments

- JUnit and Mockito libraries for testing support.
