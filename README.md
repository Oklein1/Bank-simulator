# Bank Simulation App

This project is a simple command-line application written in Clojure that simulates basic banking functionality. The primary goal of the project is to showcase the usage of Clojure's concurrency tools, such as `ref` and `dosync`, to manage global states effectively. Additionally, it can also run in your down REPL.

## Features

- **User Management**: The application allows users to sign up and sign in with their first and last names.
- **Account Operations**: Users can perform various banking operations, including checking account details, depositing funds, withdrawing funds, and transferring funds to other accounts.
- **Concurrency Handling**: The application uses Clojure's `ref` to manage global states, ensuring that operations are atomic and data consistency is maintained.

## Usage

To run the application, use the following command:
```bash
lein run uberjar
```
----------

# Functionality Overview

## Global States and Functions

- `bank-sys`: A reference (`ref`) to a hashmap representing the bank system.
- `user-ref`: A reference to the currently signed-in user.
- `Account`: A record representing a user account with fields for ID, first name, last name, and amount.

## User Management

- `add-user!`: Adds a user to the bank system.
- `generate-user-ID`: Generates a unique user ID.
- `sign-in!`: Sets the currently signed-in user.
- `sign-out`: Clears the currently signed-in user.

## Account Operations

- `deposit!`: Deposits funds into the user's account.
- `withdraw!`: Withdraws funds from the user's account.
- `transfer!`: Transfers funds from the user's account to another account.

## Main Page Functions: User Actions

- `main-page!`: Displays a menu for account-related actions.
- `account-status-UI!`: Displays the user's account details.
- `deposit-UI!`: Handles the deposit operation.
- `wihtdraw-UI!`: Handles the withdrawal operation.
- `transfer-UI!`: Handles the fund transfer operation.
- `unregistered-key-response-UI!`: Handles unrecognized input on the main page.

## Front Page Functionality: (Starting function)

- `sign-up!`: Prompts the user to sign up and creates a new account.
- `sign-in->main-page!`: Signs in the user and directs them to the main page.
- `sign-up->main-page!`: Signs up the user and directs them to the main page.
- `front-page!`: Displays options to sign in or sign up.

## Usage Instructions

1. Run the application using `lein run uberjar`.
2. Follow the prompts to sign in or sign up.
3. Use the main menu to perform various banking operations.

**Note**: This is a simple simulation for educational purposes and may not include all the features of a real banking system.


## To do list:
1. Incorporate Database
2. Incorporate Specs lib to ensure type consistency, especially when <code>user-input</code> function is being called.
   a. Small Example: when depositing money, the input should always be a numerical type and not, by accident, a string.
3. Build front-end UI 
   


-------------------

## License

Copyright © 2024 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
