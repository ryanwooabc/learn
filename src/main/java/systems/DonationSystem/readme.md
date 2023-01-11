# 0000-system-donation-system

## Functional Requirements
- users can view donation list
- users can choose one and donate
- users need to fill firstName, lastName, email info
- users need to fill payment info, like routing/account number or credit/debit card
- the system will call 3rd party payment service
- the system will send invoice to users' email if payment is successful
- users will receive invoice with TransactionID
- users can review their donations with TransactionID
- optional: register, login, profile

## Non-functional Requirements
- high availability
- low latency
- scalability
- TODO: strong consistency

## Capacity Estimation
- Write: 10M users * once / 30 / 24 / 3600 ~= 300k / 10^5 = 3 QPS
- Storage: 10M * 1K = 10GB
- Network < 1Mbps

## System API
- String register(lastName, firstName, email, phoneNo, birthDate)
- String login(email, password)
- String donamte(token, donationId, amount)


## Database Design

## High-level Design

## Detail Design

## Scalability

## Performance

## Enhancement