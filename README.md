# ThreadVault: Concurrency for Effortless Scalability

Welcome to ThreadVault, a Clojure command-line application redefining bank transaction efficiency. While initially centered on banking operations, ThreadVault offers versatile functionality, adaptable to domains such as payroll systems, inventory management, and messaging applications. Witness a concise yet scalable approach, emphasizing simplicity and maintainability across diverse business scenarios.


## Why ThreadVault? Why a Lisp?

Building and scaling concurrent programs pose inherent challenges, often entailing intricate management of shared state, avoiding race conditions, and ensuring scalability. Programmatically responding to such challenges requires an exuberant amount of code, leading to challanges in maintainability and one's ability to reason about the program. ThreadVault respondes to such complexity with simplicity. In less than 200 lines of code, ThreadVault maximizes Clojure's <a href="https://clojure.org/index"></a> concurent ecosystem, making the system small, maintainable, and simple to reason about, ultimately offering a simplified paradigm for concurrent programming.

1. Shared Mutable State Challenges:
Traditional concurrent systems grapple with shared mutable state, a breeding ground for bugs and complex synchronization. ThreadVault, leveraging Clojure's emphasis on immutability, mitigates these challenges by default. Immutable data structures ensure that shared state remains consistent, reducing the potential for subtle errors.

2. Race Conditions and Deadlocks:
Race conditions and deadlocks are common pitfalls in concurrent programming. ThreadVault's integration of Software Transactional Memory (STM) in Clojure provides a seamless mechanism for managing shared resources without resorting to low-level locking. This approach eliminates the risk of deadlocks and ensures the consistency of data, even in highly concurrent scenarios.

3. Scalability and Maintainability:
Scaling concurrent programs traditionally involves intricate threading models and complex synchronization strategies. ThreadVault simplifies the scalability equation by providing a lightweight yet powerful concurrency toolkit. With its focus on functional paradigms and minimalistic design, ThreadVault ensures that scaling concurrent systems becomes an achievable goal without compromising maintainability.

4. Code Simplicity in Less Than 200 Lines:
One of ThreadVault's most compelling features is its ability to deliver robust concurrent systems with remarkable simplicity. The entire program, including a CLI, is implemented in less than 200 lines of code. This brevity is not just an aesthetic choice; it reflects a commitment to reducing the cognitive load on developers, making code more readable, and facilitating easier maintenance.

In essence, ThreadVault is not just a tool; it's a response to the challenges posed by concurrent programming. By leveraging Clojure's concurrency tools and embracing simplicity, ThreadVault empowers developers to build scalable and maintainable concurrent systems without the traditional complexities that often accompany such endeavors.


-----------------------------------
## Usage Instructions

To run the application in the command line, use the following command:
```bash
lein run uberjar
```

1. Run the application using `lein run uberjar`.
2. Follow the prompts to sign in or sign up.
3. Use the main menu to perform various banking operations.



----------

# Functionality Overview


## Features

- **User Management**: The application allows users to sign up and sign in with their first and last names.
- **Account Operations**: Users can perform various banking operations, including checking account details, depositing funds, withdrawing funds, and transferring funds to other accounts.
- **Concurrency Handling**: The application uses Clojure's `ref` to manage global states, ensuring that operations are atomic and data consistency is maintained.



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
- `sign-in!`: Prompts the user to sign in and checks if the account exists in the database.
- `sign-in->main-page!`: Signs in the user and directs them to the main page.
- `sign-up->main-page!`: Signs up the user and directs them to the main page.
- `front-page!`: Displays options to sign in or sign up.


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
