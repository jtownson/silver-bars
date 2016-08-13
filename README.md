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
For an application in which the number of orders being added or
deleted is large, it would be sensible to stream order add/remove events
to a store and incrementally update displays and statistics.

For this test, I have assumed that the number of updates is small
and can refresh the summary by re-reading the entire dataset. There is
no requirement to contradict this and it is consistent
with a 'minimum viable product' approach preferred in agile development.

A cancellation for a non-existent order is considered to be an ignorable
event. Therefore an order and cancellation arriving in the wrong order
(which can sometimes happen in concurrent systems) is not supported. I 
assume this is okay.

To satisfy the sorting requirement (sell orders by price ascending and buy 
orders by price descending) I have assumed that order summaries are 
retrieved by order type.

## Implementation notes

I have used lombok to provide reliable implementations of equals and 
hashcode and save boilerplate. If using intellij, it helps if you install
the lombok plugin.

For the OrderBoardTest I have used a property-based testing library called
junit-quickcheck. The library helps to generate order data statistically,
including edge cases like empty order lists, single order lists, 
orders with strange characters, etc. I find it saves a great deal of laborious
typing when creating test cases and provides a good 'independent eye' of
one's code, finding ways to break code that the author, with his head in
a particular mindset had not considered.

I have used the javaslang collection library, which enables
sorting and grouping operations, usually in a single line of code.

My preference is for the functional, immutable style. I find it
makes implementing data-munging easier and more reliable.
However, 'my own standards' of coding are sympathetic to the norms of
the team.

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

 