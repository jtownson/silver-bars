# silver-bars

## Usage

This is a maven + jdk 8 project. 

Download as a zip or
git clone https://github.com/jtownson/silver-bars.git

mvn clean test

The entry point to the order board is via JUnit test cases. For 
requirements 1. and 2. please see OrderBoardTest. For requirement 3.
please see SummarizerTest.

## Assumptions

I have assumed the number of orders is reasonably small. 
For an application in which the number of orders being added or
deleted is large, it might be sensible to stream order add/remove events
to a store and incrementally update summaries. For this test, 
the summary is calculated by re-reading the entire dataset (using a 
group by operation). 

I have assumed orders and subsequent cancellations will arrive in the 
correct order (i.e. an order always arrives before its cancellation). 
This is sometimes an unacceptable assumption for concurrent systems 
but I wanted to keep things simple. 

To satisfy the sorting requirement (sell orders by price ascending and buy 
orders by price descending) I have assumed that order summaries are 
retrieved by order type. i.e. summarize(BUY) or summarize(SELL).

## Implementation notes

I have used lombok to provide reliable implementations of equals and 
hashcode and save boilerplate. It helps if you install
the lombok plugin in your IDE.

For the OrderBoardTest I have used a property-based testing library called
junit-quickcheck. The library helps to generate order data statistically,
including edge cases like empty order lists, single order lists, 
orders with strange characters, etc. I find it saves a great deal of laborious
typing when creating test cases and provides a good 'independent eye' of
one's code, finding ways to break code that the code's author forgets
to consider.

I have used the javaslang collection library to handle the sorting 
and grouping operations.

My preference is for the functional, immutable style as I find it
makes implementing data-munging easier and more reliable.
However, 'my own standards' of coding are flexible according 
to the norms of the team.

## Re-statement of the requirements

a. Register an order
b. Cancel an order
c. Display an order summary consistent with the test case and explanation provided.


### Orders should contain
- user id
- order quantity (e.g.: 3.5 kg)
- price per kg (e.g.: £303)
- order type: BUY or SELL

Get summary information of live orders

Example test case
a) SELL: 3.5 kg for £306 [user1]
b) SELL: 1.2 kg for £310 [user2]
c) SELL: 1.5 kg for £307 [user3]
d) SELL: 2.0 kg for £306 [user4]

Our ‘Live Order Board’ should provide us the following summary information:
- 5.5 kg for £306 // order a) + order d)
- 1.5 kg for £307 // order c)
- 1.2 kg for £310 // order b)

 