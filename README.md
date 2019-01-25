# test-bank-application

To run project please follow these steps: 

    1. Run follow command to pull postgres docker image:
        > docker pull postgres
    2. Run follow command to run docker image:
        > docker run --name pf -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=bank -d -p 35432:5432 postgres
    3. Import gradle project into Intellij
    4. Run the Application (application will be run on port: 8080)
    
> Note: Please make sure that public ip of the docker image is 35432

    We have 4 enpoints: 
    
    1. GET localhost:8080/accounts
        - Response model:
            {
                "accountNumber": "",
                "firstName": "",
                "lastName": "",
                "email": "",
                "balance": "",
                "transactions":
            }
    2. GET localhost:8080/accounts/{accountNumber}
        - Response model:
            {
                "accountNumber": "",
                "firstName": "",
                "lastName": "",
                "email": "",
                "balance": "",
                "transactions":
            }
    3. POST localhost:8080/accounts
        - RequestBody model:
             {
                  "accountNumber": "",
                  "firstName": "",
                  "lastName": "",
                  "email": "",
                  "balance": ""
              }
        - Response model:
             {
                 "accountNumber": "",
                 "firstName": "",
                 "lastName": "",
                 "email": "",
                 "balance": "",
                 "transactions":
             }
    4. POST localhost:8080/transactions
        - RequestBody model:
            {
                "senderAccountNumber": "",
                "receiverAccountNumber": "",
                "amount": ""
            }
        - Response model:
             {
                 "message"
             }
        - possible status code: 404, 403, 200